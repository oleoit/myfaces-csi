package org.apache.myfaces.custom.skin;

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

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Locale;

import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class is not necessary for skins, but I let this if some day I need it. 
 * 
 * @author Leonardo
 *
 */
public class SkinViewHandlerImpl extends ViewHandler
{

    private static final Log _LOG = LogFactory
            .getLog(SkinViewHandlerImpl.class);

    static public final String ALTERNATE_VIEW_HANDLER = "org.apache.myfaces.custom.skin.ALTERNATE_VIEW_HANDLER";

    private ViewHandler _delegate;

    private boolean _inited;

    public SkinViewHandlerImpl(ViewHandler delegate)
    {
        _delegate = delegate;
    }

    @Override
    public Locale calculateLocale(FacesContext context)
    {
        return _delegate.calculateLocale(context);
    }

    @Override
    public String calculateRenderKitId(FacesContext context)
    {
        return _delegate.calculateRenderKitId(context);
    }

    @Override
    public UIViewRoot createView(FacesContext context, String viewId)
    {
        _initIfNeeded(context);

        return _delegate.createView(context, viewId);
    }

    @Override
    public String getActionURL(FacesContext context, String viewId)
    {
        return _delegate.getActionURL(context, viewId);
    }

    @Override
    public String getResourceURL(FacesContext context, String path)
    {
        return _delegate.getResourceURL(context, path);
    }

    synchronized private void _initIfNeeded(FacesContext context)
    {
        if (!_inited)
        {
            _inited = true;
            String alternateViewHandler = context.getExternalContext()
                    .getInitParameter(ALTERNATE_VIEW_HANDLER);
            if (alternateViewHandler != null)
            {
                ViewHandler viewHandlerInstance = null;
                try
                {
                    ClassLoader loader = Thread.currentThread()
                            .getContextClassLoader();
                    Class<?> c = loader.loadClass(alternateViewHandler);
                    try
                    {
                        Constructor<?> constructor = c
                                .getConstructor(new Class[] { ViewHandler.class });
                        viewHandlerInstance = (ViewHandler) constructor
                                .newInstance(new Object[] { _delegate });
                    }
                    catch (NoSuchMethodException nsme)
                    {
                        viewHandlerInstance = (ViewHandler) c.newInstance();
                    }
                }
                catch (Exception e)
                {
                    _LOG
                            .warn("CANNOT_LOAD_VIEWHANDLER "
                                    + alternateViewHandler);
                    _LOG.warn(e);
                }

                if (viewHandlerInstance != null)
                    _delegate = viewHandlerInstance;
            }
        }
    }

    @Override
    public void renderView(FacesContext context, UIViewRoot viewToRender)
            throws IOException, FacesException
    {
        _initIfNeeded(context);

        _delegate.renderView(context, viewToRender);
    }

    @Override
    public UIViewRoot restoreView(FacesContext context, String viewId)
    {
        return _delegate.restoreView(context, viewId);
    }

    @Override
    public void writeState(FacesContext context) throws IOException
    {
        _delegate.writeState(context);
    }

}
