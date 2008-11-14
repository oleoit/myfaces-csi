/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.custom.skin.context;

import java.util.Iterator;
import java.util.Map;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;

import org.apache.myfaces.custom.skin.config.SkinConfiguratorImpl;

public class SkinFacesContextFactory extends FacesContextFactory
{
    static private final String _CONFIG_IN_CONTEXT = SkinFacesContextFactory.class
            .getName()
            + ".CONFIG_IN_CONTEXT";

    private final FacesContextFactory _delegate;

    public SkinFacesContextFactory(FacesContextFactory delegate)
    {
        this._delegate = delegate;
    }

    @Override
    public FacesContext getFacesContext(Object context, Object request,
            Object response, Lifecycle lifecycle) throws FacesException
    {
        FacesContext facesContext = _delegate.getFacesContext(context, request,
                response, lifecycle);
        SkinConfiguratorImpl config = SkinConfiguratorImpl.getInstance();
        config.beginRequest(facesContext.getExternalContext());

        if (!SkinConfiguratorImpl.isRequestStarted(facesContext
                .getExternalContext()))
        {
            Map<String, Object> requestMap = facesContext.getExternalContext()
                    .getRequestMap();
            requestMap.put(_CONFIG_IN_CONTEXT, Boolean.TRUE);
        }
        return facesContext;
    }

    //Just to call SkinConfiguratorImpl.endRequest on release() method
    static public class SkinFacesContext extends FacesContext
    {

        private final FacesContext _base;

        public SkinFacesContext(FacesContext base)
        {
            _base = base;
        }

        public void addMessage(String s, FacesMessage facesmessage)
        {
            _base.addMessage(s, facesmessage);
        }

        public Application getApplication()
        {
            return _base.getApplication();
        }

        public Iterator<String> getClientIdsWithMessages()
        {
            return _base.getClientIdsWithMessages();
        }

        public ELContext getELContext()
        {
            return _base.getELContext();
        }

        public ExternalContext getExternalContext()
        {
            return _base.getExternalContext();
        }

        public Severity getMaximumSeverity()
        {
            return _base.getMaximumSeverity();
        }

        public Iterator<FacesMessage> getMessages()
        {
            return _base.getMessages();
        }

        public Iterator<FacesMessage> getMessages(String s)
        {
            return _base.getMessages(s);
        }

        public RenderKit getRenderKit()
        {
            return _base.getRenderKit();
        }

        public boolean getRenderResponse()
        {
            return _base.getRenderResponse();
        }

        public boolean getResponseComplete()
        {
            return _base.getResponseComplete();
        }

        public ResponseStream getResponseStream()
        {
            return _base.getResponseStream();
        }

        public ResponseWriter getResponseWriter()
        {
            return _base.getResponseWriter();
        }

        public UIViewRoot getViewRoot()
        {
            return _base.getViewRoot();
        }

        public void release()
        {
            ExternalContext ec = getExternalContext();
            if (Boolean.TRUE.equals(ec.getRequestMap().get(_CONFIG_IN_CONTEXT)))
            {
                SkinConfiguratorImpl.getInstance().endRequest(ec);
            }

            _base.release();
        }

        public void renderResponse()
        {
            _base.renderResponse();
        }

        public void responseComplete()
        {
            _base.responseComplete();
        }

        public void setResponseStream(ResponseStream responsestream)
        {
            _base.setResponseStream(responsestream);
        }

        public void setResponseWriter(ResponseWriter responsewriter)
        {
            _base.setResponseWriter(responsewriter);
        }

        public void setViewRoot(UIViewRoot uiviewroot)
        {
            _base.setViewRoot(uiviewroot);
        }
    }
}
