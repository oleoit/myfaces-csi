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
package org.apache.myfaces.custom.skin.component;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.XhtmlConstants;
import org.apache.myfaces.trinidadinternal.style.StyleContext;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;

/**
 * This class is the default implementation of SkinStyleSheetRenderer.
 */
@JSFRenderer(renderKitId = "HTML_BASIC",
   family = "org.apache.myfaces.skins.StyleSheet",
   type = "org.apache.myfaces.skins.StyleSheet") 
public class DefaultSkinStyleSheetRenderer extends SkinStyleSheetRenderer
{

    /**
     * This method renders the stylesheet directly into the ResponseWriter.
     */
    @Override
    protected void renderStyleSheets(FacesContext context,
            UIComponent component, List<String> uris, StyleProvider provider,
            StyleContext sContext) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        
        ExternalContext externalContext = context
                .getExternalContext();
        String contextUri = externalContext.getRequestContextPath();
        String baseUrl = contextUri + XhtmlConstants.STYLES_CACHE_DIRECTORY;
        
        for (String uri : uris)
        {
            writer.startElement("link", null);
            renderId(context, component);
            writer.writeAttribute("rel", "stylesheet", null);
            writer.writeAttribute("charset", "UTF-8", null);
        
            String type = provider
                    .getContentStyleType(sContext);
            writer.writeAttribute("type", type, null);
        
            renderEncodedResourceURI(context, "href", baseUrl + uri);
            writer.endElement("link");                        
        }
    }

    /**
     * This method renders the component's id.
     */
    private void renderId(FacesContext context, UIComponent component) throws IOException
    {
        if (shouldRenderId(context, component))
        {
            String clientId = getClientId(context, component);
            context.getResponseWriter().writeAttribute("id", clientId, "id");
        }
    }
    
    /**
     * Checks whether the component's id should be rendered or not.
     * @param context
     * @param component
     * @return True if the id is not null and was not a generated id.
     */
    protected boolean shouldRenderId(
            FacesContext context,
            UIComponent  component)
    {
        String id = component.getId();
    
        // Otherwise, if ID isn't set, don't bother
        if (id == null)
          return false;
        
        // ... or if the ID was generated, don't bother
        if (id.startsWith(UIViewRoot.UNIQUE_ID_PREFIX))
          return false;
    
        return true;
    }
    
    /**
     * Retrieves the component's client id.
     * @param context
     * @param component
     * @return The component's client id.
     */
    protected String getClientId(
            FacesContext context,
            UIComponent  component)
    {
        return component.getClientId(context);
    }
}
