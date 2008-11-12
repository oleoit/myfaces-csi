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
package org.apache.myfaces.renderkit.html.skin;

import java.util.List;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.myfaces.trinidadinternal.config.GlobalConfiguratorImpl;
import org.apache.myfaces.trinidadinternal.renderkit.core.SkinRenderingContext;
import org.apache.myfaces.trinidadinternal.renderkit.core.StyleContext;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;

public class SkinPhaseListener implements PhaseListener
{
    private static final String CSS_FILENAME_PARAM = "org.apache.myfaces.renderkit.html.skin.CSS_URIs";
    private static final long serialVersionUID = 1L;

    public void afterPhase(PhaseEvent arg0)
    {
        // DO NOTHING
    }

    public void beforePhase(PhaseEvent pe)
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        instance.beginRequest(pe.getFacesContext().getExternalContext());
        
        SkinRenderingContext skinCtx = new SkinRenderingContext();
        StyleContext sCtx = skinCtx.getStyleContext();
        StyleProvider provider = sCtx.getStyleProvider();
        
        List<String> uris = provider.getStyleSheetURIs(sCtx);
        
        pe.getFacesContext().getExternalContext().getRequestMap().put(CSS_FILENAME_PARAM, uris);
    }

    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }
}
