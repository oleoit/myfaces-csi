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
package org.apache.myfaces.custom.skin.lifecycle;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.apache.myfaces.custom.skin.context.SkinRenderingContextImpl;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;
import org.apache.myfaces.trinidad.logging.SkinLogger;

public final class SkinRenderingContextPhaseListener implements PhaseListener
{
    
    private static final long serialVersionUID = -3326689809959287245L;
        
    static private final SkinLogger _LOG =
        SkinLogger.createTrinidadLogger(SkinRenderingContextPhaseListener.class);
    
    public void beforePhase(PhaseEvent event)
    {
        new SkinRenderingContextImpl();        
    }

    public void afterPhase(PhaseEvent event)
    {
        SkinRenderingContext arc = SkinRenderingContext.getCurrentInstance();
        if (arc != null)
        {
          arc.release();
        }
        else
        {
          _LOG.warning("RENDERINGCONTEXT_NOT_AVAILABLE");
        }
    }

    public PhaseId getPhaseId()
    {
        return PhaseId.RENDER_RESPONSE;
    }

}
