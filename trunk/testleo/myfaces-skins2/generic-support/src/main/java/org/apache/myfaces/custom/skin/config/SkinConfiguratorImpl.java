/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.myfaces.custom.skin.config;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;

import org.apache.myfaces.commons.util.ExternalContextUtils;
import org.apache.myfaces.custom.skin.context.RequestContextFactory;
import org.apache.myfaces.custom.skin.context.RequestContextFactoryImpl;
import org.apache.myfaces.trinidad.context.SkinRequestContext;
import org.apache.myfaces.trinidad.logging.SkinLogger;
import org.apache.myfaces.trinidad.skin.SkinFactory;
import org.apache.myfaces.trinidadinternal.skin.SkinFactoryImpl;
import org.apache.myfaces.trinidadinternal.skin.SkinUtils;

/**
 * This is the implementation of the Trinidad's Global configurator. It provides the entry point for
 * all other configurators. This class is responsible for enforcing the contract outlined by the
 * Configurator abstract class, but allows a more "relaxed" implementation of the APIs called for by
 * the Configurator class, making it more convenient to use ConfiguratorServices from within the
 * Trinidad renderkit. Where appropriate, these differences will be documented for the benifit of
 * the Trindad developer.
 *
 * @see org.apache.myfaces.trinidad.config.Configurator
 * @version $Revision: 180 $ $Date: 2008-11-15 07:26:09 +0800 (Sat, 15 Nov 2008) $
 */
public final class SkinConfiguratorImpl
{
    /**
     * Returns a GlobalConfigurator instance for the current context's class loader. The
     * GlobalConfigurator is responsible for enforcing the contract on the other methods of this
     * class. This means that if {@link #init(ExternalContext)} is called multiple times, the global
     * configurator will call all subordinate configurators only once.
     *
     * Likewise, the GlobalConfigurator will return exceptions when the contract is expressly violated
     * (like if {@link #getExternalContext(ExternalContext)} is called before a {{@link #beginRequest(ExternalContext)}.
     *
     * @return a GlobalConfigurator or <code>null</code> is one was unable to be obtained.
     */
    static public final SkinConfiguratorImpl getInstance()
    {
        final ClassLoader loader = Thread.currentThread()
                .getContextClassLoader();

        if (loader == null)
        {
            _LOG.severe("CANNOT_FIND_CONTEXT_CLASS_LOADER");
        }
        else
        {
            synchronized (_CONFIGURATORS)
            {
                SkinConfiguratorImpl config = _CONFIGURATORS.get(loader);
                if (config == null)
                {
                    try
                    {
                        config = new SkinConfiguratorImpl();
                        _CONFIGURATORS.put(loader, config);
                    }
                    catch (final RuntimeException e)
                    {
                        // OC4J was not reporting these errors properly:
                        _LOG.severe(e);
                        throw e;
                    }
                    _LOG.fine("GlobalConfigurator has been created.");
                }
                return config;
            }
        }
        return null;
    }

    /**
     * Returns true if the request has not been stated for the current "virtual"
     * request.  In the servlet environment this will be true after
     * {@link #beginRequest(ExternalContext)} is executed and before
     * {@link #endRequest(ExternalContext)} is executed.  This will generally
     * happen once per request.  In the Portlet Environment, the request must be
     * be started and ended at the beginning and end of both the actionRequest
     * and the RenderRequest.
     *
     * @param ec
     * @return
     */
    static public boolean isRequestStarted(final ExternalContext ec)
    {
        return (RequestType.getType(ec) != null);
    }

    /**
     * Private default constructor. Right now this class is not serializable. If serialization is
     * required, we may wish to make this public. We really don't want people using this though.
     */
    private SkinConfiguratorImpl()
    {
    }

    /**
     * Executes the beginRequest methods of all of the configurator services. This method will also
     * initizlize the configurator if it has not already been initialized, so there may be no need to
     * call the {@link #init(ExternalContext)} method directly.
     *
     * This method also ensures that the requestContext is attached before the beginRequest methods
     * are called, so there is no reason to initialize the request context before calling this method.
     * In portal environments, it is important to execute this method once for each Portlet action and
     * render request so that the requestContext may be properly initialized even though the
     * underlying services will be called only once per physical request.
     *
     * @param externalContext
     *          the externalContext to use to begin the request.
     *
     * @see org.apache.myfaces.trinidad.config.Configurator#beginRequest(javax.faces.context.ExternalContext)
     */
    @SuppressWarnings("unchecked")
    // TODO: remove this for Faces 1.2
    public void beginRequest(final ExternalContext externalContext)
    {
        // asserts for debug which disappear in production
        assert externalContext != null;

        // Do per-virtual request stuff
        if (RequestType.getType(externalContext) == null)
        {
            // RequestType may change in a portal environment. Make sure it's set up to enforce the
            // contracts
            RequestType.setType(externalContext);

            // By contract, Configurators beginRequest is only called once per physical request.
            // The globalConfigurator may be called multiple times, however, so we need to enforce
            // the contract.
            // If this hasn't been initialized then please initialize
            if (!_initialized)
            {
                init(externalContext);
            }

            _attachRequestContext(externalContext);

        }
        else if (!RequestType.isCorrectType(externalContext))
        {
            // This will happen if the actionRequest was not ended before dispatching to the render
            // request
            throw new IllegalStateException(
                    "The previous action request was not ended.");
        }
        else
        {
            _LOG.fine("BeginRequest called multiple times for this request");
        }
    }

    /**
     * Cleans up the current configurator. This will execute the destroy method on all of the
     * configurator services. Generally this will be called by Trinidad's context listener when the
     * context is destroyed, but it may be used manually to allow the configurator to be
     * re-initialized.
     *
     * Calling this method while the configurator is not initialized will not re-execute the destroy
     * methods on the services.
     *
     * @see org.apache.myfaces.trinidad.config.Configurator#destroy()
     */
    public void destroy()
    {
        if (_initialized)
        {
            try
            {
                _initialized = false;
            }
            finally
            {
                //release any managed threadlocals that may have been used durring destroy
                _releaseManagedThreadLocals();
            }
        }
    }

    /**
     * Ends the currently begun request. It is important to note that this should be executed only
     * once per physical request.
     *
     * @see org.apache.myfaces.trinidad.config.Configurator#endRequest(javax.faces.context.ExternalContext)
     */
    public void endRequest(final ExternalContext externalContext)
    {
        // do per virtual-request stuff
        if (RequestType.getType(externalContext) != null)
        {
            try
            {
                final RequestType type = RequestType.getType(externalContext);
            }
            finally
            {
                _releaseRequestContext(externalContext);
                _releaseManagedThreadLocals();
            }
            RequestType.clearType(externalContext);
        }
    }

    /**
     * Returns an externalContext for this configurator and all of the configurator services. If this
     * method is executed before {@link #beginRequest(ExternalContext)} then this method will call
     * beginRequest(). It is important to note, however, that even though beginRequest does not need
     * to be explicitly called, {{@link #endRequest(ExternalContext)} does need to be called when the
     * request has completed or the contract to the configurators will be broken.
     *
     * @param externalContext
     *          the ExternalContext object that should be wrapped.
     *
     * @return a decorated ExternalContext object
     */
    public ExternalContext getExternalContext(ExternalContext externalContext)
    {
        if (RequestType.getType(externalContext) == null)
        {
            beginRequest(externalContext);
        }

        //if (!_isDisabled(externalContext))
        //{
        // Wrap ExternalContexts
        //for (final Configurator config : _services)
        //{
        //  externalContext = config.getExternalContext(externalContext);
        //}
        //}

        return externalContext;
    }

    /**
     * Initializes the global configurator and the configurator services. This method need not be
     * called directly as it will be called from {@link #beginRequest(ExternalContext)} if needed. It
     * is also possible to execute this method more then once, although if initialization has already
     * happened then a call to this method will not do anything. To re-initialize this class, call
     * {@link #destroy()} first and then call this method.
     *
     * @param externalContext
     *          the externalContext needed to initialize this class
     *
     * @see org.apache.myfaces.trinidad.config.Configurator#init(javax.faces.context.ExternalContext)
     */
    public void init(final ExternalContext externalContext)
    {
        assert externalContext != null;

        if (!_initialized)
        {
            try
            {
                // Create a new RequestContextFactory is needed
                if (RequestContextFactory.getFactory() == null)
                {
                    RequestContextFactory
                            .setFactory(new RequestContextFactoryImpl());
                }

                // Create a new SkinFactory if needed.
                if (SkinFactory.getFactory() == null)
                {
                    SkinFactory.setFactory(new SkinFactoryImpl());
                }

                // register the base skins
                SkinUtils.registerBaseSkins();

                // after the 'services' filters are initialized, then register
                // the skin extensions found in trinidad-skins.xml. This
                // gives a chance to the 'services' filters to create more base
                // skins that the skins in trinidad-skins.xml can extend.
                SkinUtils.registerSkinExtensions(externalContext);
                _initialized = true;
            }
            finally
            {

                //Do cleanup of anything which may have use the thread local manager durring
                //init.
                _releaseManagedThreadLocals();
            }
        }
        else
        {
            _LOG.warning("CONFIGURATOR_SERVICES_INITIALIZED");
        }
    }

    /**
     * Hackily called by the ThreadLocalResetter to register itself so that the
     * GlobalConfiguratorImpl can tell the ThreadLocalResetter to clean up the
     * ThreadLocals at the appropriate time.
     */
    void __setThreadLocalResetter(ThreadLocalResetter resetter)
    {
        if (resetter == null)
            throw new NullPointerException();

        _threadResetter.set(resetter);
    }

    /**
     * @param externalContext
     * @return
     */
    @SuppressWarnings("unchecked")
    private void _attachRequestContext(final ExternalContext externalContext)
    {
        // If someone didn't release the RequestContext on an earlier request,
        // then it'd still be around, and trying to create a new one
        // would trigger an exception. We don't want to take down
        // this thread for all eternity, so clean up after poorly-behaved code.
        SkinRequestContext context = SkinRequestContext.getCurrentInstance();
        if (context != null)
        {
            if (_LOG.isWarning())
            {
                _LOG.warning("REQUESTCONTEXT_NOT_PROPERLY_RELEASED");
            }
            context.release();
            _releaseManagedThreadLocals();
        }

        // See if we've got a cached RequestContext instance; if so,
        // reattach it
        final Object cachedRequestContext = externalContext.getRequestMap()
                .get(_REQUEST_CONTEXT);

        // Catch both the null scenario and the
        // RequestContext-from-a-different-classloader scenario
        if (cachedRequestContext instanceof SkinRequestContext)
        {
            context = (SkinRequestContext) cachedRequestContext;
            context.attach();
        }
        else
        {
            final RequestContextFactory factory = RequestContextFactory
                    .getFactory();
            assert factory != null;
            context = factory.createContext(externalContext);
            externalContext.getRequestMap().put(_REQUEST_CONTEXT, context);
        }

        assert SkinRequestContext.getCurrentInstance() == context;
    }

    private void _releaseRequestContext(final ExternalContext ec)
    {
        //If it's not a portal action, we should remove the cached request because
        //well want to create a new one next request
        if (RequestType.getType(ec) != RequestType.PORTAL_ACTION)
        {
            ec.getRequestMap().remove(_REQUEST_CONTEXT);
        }

        final SkinRequestContext context = SkinRequestContext.getCurrentInstance();
        if (context != null)
        {
            context.release();
            assert SkinRequestContext.getCurrentInstance() == null;

        }
    }

    private void _releaseManagedThreadLocals()
    {
        ThreadLocalResetter resetter = _threadResetter.get();

        if (resetter != null)
        {
            resetter.__removeThreadLocals();
        }
    }

    static private boolean _isSetRequestBugPresent(ExternalContext ec)
    {
        // This first check is here in order to skip synchronization until
        // absolutely necessary.
        if (!_sSetRequestBugTested)
        {
            synchronized (SkinConfiguratorImpl.class)
            {
                //This second check is here in case a couple of things enter before the
                //boolean is set.  This is only an exception case and will make it so
                //the initialization code runs only once.
                if (!_sSetRequestBugTested)
                {
                    ServletRequest orig = (ServletRequest) ec.getRequest();
                    // Call getInitParameterMap() up front
                    ec.getInitParameterMap();

                    ec.setRequest(new TestRequest(orig));

                    _sHasSetRequestBug = !TestRequest.isTestParamPresent(ec);
                    _sSetRequestBugTested = true;

                    ec.setRequest(orig);
                }
            }
        }

        return _sHasSetRequestBug;
    }

    private static volatile boolean _sSetRequestBugTested = false;
    private static boolean _sHasSetRequestBug = false;

    private boolean _initialized;
    static private final Map<ClassLoader, SkinConfiguratorImpl> _CONFIGURATORS = new HashMap<ClassLoader, SkinConfiguratorImpl>();
    static private final String _REQUEST_CONTEXT = SkinConfiguratorImpl.class
            .getName()
            + ".REQUEST_CONTEXT";

    private enum RequestType
    {
        PORTAL_ACTION, PORTAL_RENDER, SERVLET;

        public static void clearType(final ExternalContext ec)
        {
            ec.getRequestMap().remove(_REQUEST_TYPE);
        }

        public static RequestType getType(final ExternalContext ec)
        {
            return (RequestType) ec.getRequestMap().get(_REQUEST_TYPE);
        }

        public static boolean isCorrectType(final ExternalContext ec)
        {
            return _findType(ec) == getType(ec);
        }

        @SuppressWarnings("unchecked")
        public static void setType(final ExternalContext ec)
        {
            ec.getRequestMap().put(_REQUEST_TYPE, _findType(ec));
        }

        private static final RequestType _findType(final ExternalContext ec)
        {
            if (ExternalContextUtils.getRequestType(ec).isPortlet())
            {
                if (ExternalContextUtils.getRequestType(ec).equals(org.apache.myfaces.commons.util.RequestType.ACTION))
                {
                    return PORTAL_ACTION;
                }
                else
                {
                    return PORTAL_RENDER;
                }
            }
            else
            {
                return SERVLET;
            }
        }

        static private final String _REQUEST_TYPE = SkinConfiguratorImpl.class
                .getName()
                + ".REQUEST_TYPE";
    }

    static private class TestRequest extends ServletRequestWrapper
    {
        public TestRequest(ServletRequest request)
        {
            super(request);
        }

        @Override
        public String getParameter(String string)
        {
            if (_TEST_PARAM.equals(string))
            {
                return "passed";
            }

            return super.getParameter(string);
        }

        static public final boolean isTestParamPresent(ExternalContext ec)
        {
            return ec.getRequestParameterMap().get(_TEST_PARAM) != null;
        }

        static private String _TEST_PARAM = TestRequest.class.getName()
                + ".TEST_PARAM";
    }

    // hacky reference to the ThreadLocalResetter used to clean up request-scoped
    // ThreadLocals
    private AtomicReference<ThreadLocalResetter> _threadResetter = new AtomicReference<ThreadLocalResetter>();

    static private final SkinLogger _LOG = SkinLogger
            .createTrinidadLogger(SkinConfiguratorImpl.class);
}
