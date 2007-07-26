package org.apache.myfaces.trinidadinternal.renderkit.core;

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
import java.io.OutputStream;
import java.io.Writer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.faces.FactoryFinder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.skin.SkinRenderer;
import org.apache.myfaces.trinidad.logging.TrinidadLogger;
import org.apache.myfaces.trinidad.render.DialogRenderKitService;
import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;

/**
 * 
 * The objective of this clas is decorate CoreRenderKit class, for
 * decorate with properly SkinRenderer classes the original renderers.
 * 
 * @author Leonardo
 *
 */
public class SkinRenderKit extends RenderKit implements DialogRenderKitService,
        ExtendedRenderKitService
{

    private static final Log log = LogFactory.getLog(SkinRenderKit.class);

    private CoreRenderKit _delegate;

    private Map _map;

    public SkinRenderKit()
    {
        super();
        RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        _delegate = (CoreRenderKit) factory.getRenderKit(FacesContext
                .getCurrentInstance(), CoreRenderKit.BASE_RENDER_KIT_ID);

        _map = new ConcurrentHashMap(64, 0.75f, 1);

    }

    private CoreRenderKit getDelegateRenderKit()
    {
        if (_delegate == null)
        {
            RenderKitFactory factory = (RenderKitFactory) FactoryFinder
                    .getFactory(FactoryFinder.RENDER_KIT_FACTORY);
            _delegate = (CoreRenderKit) factory.getRenderKit(FacesContext
                    .getCurrentInstance(), CoreRenderKit.BASE_RENDER_KIT_ID);
        }
        return _delegate;
    }

    public void addRenderer(String family, String rendererType,
            Renderer renderer)
    {
        // In this case, first check if this class inherits from
        // SkinRenderer class
        if (SkinRenderer.class.isAssignableFrom(renderer.getClass()))
        {
            SkinRenderer sr = (SkinRenderer) renderer;
            _map.put(family + ";" + rendererType, sr);
        }
        else
        {
            getDelegateRenderKit().addRenderer(family, rendererType, renderer);
        }
    }

    public ResponseStream createResponseStream(OutputStream out)
    {
        return getDelegateRenderKit().createResponseStream(out);
    }

    public ResponseWriter createResponseWriter(Writer writer,
            String contentTypeList, String characterEncoding)
    {
        return getDelegateRenderKit().createResponseWriter(writer,
                contentTypeList, characterEncoding);
    }

    public Renderer getRenderer(String family, String rendererType)
    {
        // First get a renderer from here
        Renderer r = (Renderer) _map.get(family + ";" + rendererType);
        if (r == null)
        {
            // For now i want to define a generic renderer wrapper
            // to components not defined. It should not harm anyone
            r = getDelegateRenderKit().getRenderer(family, rendererType);
            return r;
        }
        else
        {
            // I get a renderer
            if (SkinRenderer.class.isAssignableFrom(r.getClass()))
            {
                // check if it has a delegate renderer
                SkinRenderer sr = (SkinRenderer) r;
                if (sr.getDelegate() == null)
                {
                    // set a proper delegate
                    r = getDelegateRenderKit()
                            .getRenderer(family, rendererType);
                    if (r != null)
                    {
                        sr.setDelegate(r);
                        return sr;
                    }
                    else
                    {
                        return r;
                    }
                }
                return sr;
            }
            else
            {
                return r;
            }
        }
    }

    public ResponseStateManager getResponseStateManager()
    {
        return getDelegateRenderKit().getResponseStateManager();
    }

    // METHODS INHERITED FROM ExtendedRenderKitService
    // The idea here is to implement or delegate the methods to appropiate
    // ExtendedRenderKitService.

    // Delegate pattern to CoreRenderKit

    static private final TrinidadLogger _LOG = TrinidadLogger
            .createTrinidadLogger(SkinRenderKit.class);

    public boolean isReturning(FacesContext arg0, UIComponent arg1)
    {
        return getDelegateRenderKit().isReturning(arg0, arg1);
    }

    public boolean launchDialog(FacesContext arg0, UIViewRoot arg1,
            UIComponent arg2, Map<String, Object> arg3, boolean arg4,
            Map<String, Object> arg5)
    {
        return getDelegateRenderKit().launchDialog(arg0, arg1, arg2, arg3,
                arg4, arg5);
    }

    public boolean returnFromDialog(FacesContext arg0, Object arg1)
    {
        return getDelegateRenderKit().returnFromDialog(arg0, arg1);
    }

    public void addScript(FacesContext arg0, String arg1)
    {
        getDelegateRenderKit().addScript(arg0, arg1);
    }

    public void encodeBegin(FacesContext arg0) throws IOException
    {
        getDelegateRenderKit().encodeBegin(arg0);
    }

    public void encodeEnd(FacesContext arg0) throws IOException
    {
        getDelegateRenderKit().encodeEnd(arg0);
    }

    public void encodeFinally(FacesContext arg0)
    {
        getDelegateRenderKit().encodeFinally(arg0);
    }

    public void encodeScripts(FacesContext arg0) throws IOException
    {
        getDelegateRenderKit().encodeScripts(arg0);
    }

    public boolean isStateless(FacesContext arg0)
    {
        return getDelegateRenderKit().isStateless(arg0);
    }

    public boolean shortCircuitRenderView(FacesContext arg0) throws IOException
    {
        return getDelegateRenderKit().shortCircuitRenderView(arg0);
    }

    // END METHODS INHERITED FROM ExtendedRenderKitService
}
