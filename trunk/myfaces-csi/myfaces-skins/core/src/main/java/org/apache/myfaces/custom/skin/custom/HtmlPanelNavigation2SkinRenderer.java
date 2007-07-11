package org.apache.myfaces.custom.skin.custom;

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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlPanelNavigation2SkinRenderer extends AdapterSkinRenderer {
	
	public HtmlPanelNavigation2SkinRenderer() {
		super("t", "panelNavigation2");
	}

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory
			.getLog(HtmlPanelNavigation2SkinRenderer.class);

	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		String baseStyleClass = this.getBaseStyleName(component);

		String itemStyleClass = baseStyleClass + "::item";
		String openItemStyleClass = baseStyleClass + "::open";
		String activeItemStyleClass = baseStyleClass + "::active";
		String separatorStyleClass = baseStyleClass + "::separator";
		// disabledStyleClass just apply to t:panelNavigation2
		String disabledStyleClass = baseStyleClass + SkinConstants.DISABLED_CLASS_SUFFIX;
		String styleClass = baseStyleClass + "::styleClass";
		//String divStyleClass = baseStyleClass + SkinConstants.STYLE_CLASS;
		//String outerStyleClass = baseStyleClass + "::outer";

		_renderStyleClass(component, context, arc, itemStyleClass, "itemClass");
		_renderStyleClass(component, context, arc, openItemStyleClass,
				"openItemClass");
		_renderStyleClass(component, context, arc, activeItemStyleClass,
				"activeItemClass");
		_renderStyleClass(component, context, arc, separatorStyleClass,
				"separatorClass");
		_renderStyleClass(component, context, arc, disabledStyleClass,
				"disabledStyleClass");
		_renderStyleClass(component, context, arc, styleClass, "styleClass");

		/*
		Map<String, Object> requestMap = context.getExternalContext()
				.getRequestMap();

		Integer nestLevelObject = (Integer) requestMap
				.get(NAVIGATION_PANEL_NEST_LEVEL_KEY);
		int nestLevel = 0;
		if (nestLevelObject != null) {
			nestLevel = nestLevelObject.intValue() + 1;
		}
		requestMap.put(NAVIGATION_PANEL_NEST_LEVEL_KEY, nestLevel);

		if (nestLevel == 0){
			ResponseWriter writer = context.getResponseWriter();
			// I don't like have to add a t:div for use this component
			// I prefer add this two div and integrate inside the 
			// skinning features
			// It's have some drawbacks, like you can't arrange 2
			// t:panelNavigation2 to form a big panel Navigation, but in
			// many cases

			writer.startElement("div", null);
			renderDivStyleClass(component, context, arc, outerStyleClass, "class");
			writer.startElement("div", null);
			renderDivStyleClass(component, context, arc, divStyleClass, "class");
		}
		*/
	}

	static public void renderDivStyleClass(UIComponent component,
			FacesContext context, RenderingContext arc, String styleClass,
			String property) throws IOException {
		if (styleClass != null) {
			styleClass = arc.getStyleClass(styleClass);

			if (styleClass != null) {
				if (styleClass.startsWith("af_")){
					//no styleClass found
					return;
				}				
				context.getResponseWriter().writeAttribute(property, styleClass, property);
			}
		}
	}
	
	
	public void encodeEnd(FacesContext context, UIComponent component)
			throws IOException {
		
		super.encodeEnd(context, component);
		
		/*
		ResponseWriter writer = context.getResponseWriter();
	    
		Map<String, Object> requestMap = context.getExternalContext()
		.getRequestMap();

		Integer nestLevelObject = (Integer) requestMap
			.get(NAVIGATION_PANEL_NEST_LEVEL_KEY);
		int nestLevel = 0;
		if (nestLevelObject != null) {
			nestLevel = nestLevelObject.intValue();
		}
	
		// Indicate that we are leaving this level of nesting:		
		if (nestLevel == 0)
	    {
	      // delete the value altogether:
	      requestMap.remove(NAVIGATION_PANEL_NEST_LEVEL_KEY);
	      //End div contained
		  writer.endElement("div");
		  writer.endElement("div");
	    }
	    else
	    {
	      // decrement the value:
	      requestMap.put(NAVIGATION_PANEL_NEST_LEVEL_KEY, nestLevel - 1);
	    }
	    */
	}

	//private static final String NAVIGATION_PANEL_NEST_LEVEL_KEY = "org.apache.myfaces.custom.skin.NavigationPanelNestLevel";

}
