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


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SkinRenderKitFactory extends RenderKitFactory {

	private static final Log log = LogFactory.getLog(SkinRenderKitFactory.class);
	
	private Map _renderkits = new HashMap<String,RenderKit>();
	
	/*
	 * If a renderKit is added I have to override it for implement
	 * deletage pattern.
	 *  
	 * (non-Javadoc)
	 * @see javax.faces.render.RenderKitFactory#addRenderKit(java.lang.String, javax.faces.render.RenderKit)
	 */
	@Override
	public void addRenderKit(String renderKitId, RenderKit renderKit) {
        if (renderKitId == null) throw new NullPointerException("renderKitId");
        if (renderKit == null) throw new NullPointerException("renderKit");
        if (log.isInfoEnabled())
        {
            if (_renderkits.containsKey(renderKitId))
            {
                log.info("RenderKit with renderKitId '" + renderKitId + "' was replaced.");
            }
        }
        log.info("renderKitId " + renderKitId + " added");
        //if (renderKitId.equals("HTML_BASIC")){
        //	_renderkits.put(renderKitId, new SkinRenderKit(renderKit));
        //}else{
        	//Do not encapsulate a renderer
        	//TODO: It should be better to add another behavior through xml files
        	//but for now i let this as is 
        	//_renderkits.put(renderKitId, renderKit);
        //}
        		
        _renderkits.put(renderKitId, renderKit);
	}

	/*
	 * I have to get a renderkit that acts as a delegate pattern
	 * 
	 * (non-Javadoc)
	 * @see javax.faces.render.RenderKitFactory#getRenderKit(javax.faces.context.FacesContext, java.lang.String)
	 */
	@Override
	public RenderKit getRenderKit(FacesContext context, String renderKitId) {
        if (renderKitId == null) throw new NullPointerException("renderKitId");
        RenderKit renderkit = (RenderKit)_renderkits.get(renderKitId);
        if (renderkit == null)
        {
        	
            //throw new IllegalArgumentException("Unknown RenderKit '" + renderKitId + "'.");
            //JSF Spec API Doc says:
            // "If there is no registered RenderKit for the specified identifier, return null"
            // vs "IllegalArgumentException - if no RenderKit instance can be returned for the specified identifier"
            //First sentence is more precise, so we just log a warning
            log.warn("Unknown RenderKit '" + renderKitId + "'.");
            log.warn("Kits:"+_renderkits.keySet().toString());
        }
        return renderkit;
	}

	@Override
	public Iterator getRenderKitIds() {
        return _renderkits.keySet().iterator();
	}
}
