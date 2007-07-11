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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.tree.HtmlTree;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlTreeExtSkinRenderer extends SkinRenderer {
	
	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory
			.getLog(HtmlTreeExtSkinRenderer.class);

	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
	
		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		String nodeClass = baseStyleClass + "::node";
		String selectedNodeClass = baseStyleClass + "::selectedNode";
		String headerClass = baseStyleClass + "::header";
		String footerClass = baseStyleClass + "::footer";
		String iconClass = baseStyleClass + "::icon";
		String styleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;

		//In this case there already exists a icon defined in tomahawk
		//only I have to check rtl icon
		
		String iconLine = baseStyleClass + "::line-icon:rtl";
		String iconNoLine = baseStyleClass + "::noLine-icon:rtl";
		
		String iconNodeOpen = baseStyleClass + "::nodeOpen-icon:rtl";
		String iconNodeOpenFirst = baseStyleClass + "::nodeOpenFirst-icon:rtl";
		String iconNodeOpenMiddle = baseStyleClass + "::nodeOpenMiddle-icon:rtl";
		String iconNodeOpenLast = baseStyleClass + "::nodeOpenLast-icon:rtl";

		String iconNodeClose = baseStyleClass + "::nodeClose-icon:rtl";
		String iconNodeCloseFirst = baseStyleClass + "::nodeCloseFirst-icon:rtl";
		String iconNodeCloseMiddle = baseStyleClass + "::nodeCloseMiddle-icon:rtl";
		String iconNodeCloseLast = baseStyleClass + "::nodeCloseLast-icon:rtl";

		String iconChildFirst = baseStyleClass + "::ChildFirst-icon:rtl";
		String iconChildMiddle = baseStyleClass + "::ChildMiddle-icon:rtl";
		String iconChildLast = baseStyleClass + "::ChildLast-icon:rtl";
		
		
		_renderStyleClass(component, context, arc, nodeClass, "nodeClass");
		_renderStyleClass(component, context, arc, selectedNodeClass,
				"selectedNodeClass");
		_renderStyleClass(component, context, arc, headerClass, "headerClass");
		_renderStyleClass(component, context, arc, footerClass, "footerClass");
		_renderStyleClass(component, context, arc, iconClass, "iconClass");
		_renderStyleClass(component, context, arc, styleClass, "styleClass");

		_setIconDirection(context, component, arc, iconLine, "getIconLine","setIconLine");
		_setIconDirection(context, component, arc, iconNoLine, "getIconLine","setIconLine");		
		
		_setIconDirection(context, component, arc, iconNodeOpen, "getIconNodeOpen","setIconNodeOpen");
		_setIconDirection(context, component, arc, iconNodeOpenFirst, "getIconNodeOpenFirst","setIconNodeOpenFirst");
		_setIconDirection(context, component, arc, iconNodeOpenMiddle, "getIconNodeOpenMiddle","setIconNodeOpenMiddle");
		_setIconDirection(context, component, arc, iconNodeOpenLast, "getIconNodeOpenLast","setIconNodeOpenLast");
		
		_setIconDirection(context, component, arc, iconNodeClose, "getIconNodeClose","setIconNodeClose");
		_setIconDirection(context, component, arc, iconNodeCloseFirst, "getIconNodeCloseFirst","setIconNodeCloseFirst");
		_setIconDirection(context, component, arc, iconNodeCloseMiddle, "getIconNodeCloseMiddle","setIconNodeCloseMiddle");
		_setIconDirection(context, component, arc, iconNodeCloseLast, "getIconNodeCloseLast","setIconNodeCloseLast");
		
		_setIconDirection(context, component, arc, iconChildFirst, "getIconChildFirst","setIconChildFirst");
		_setIconDirection(context, component, arc, iconChildMiddle, "getIconChildMiddle","setIconChildMiddle");
		_setIconDirection(context, component, arc, iconChildLast, "getIconChildLast","setIconChildLast");
		
	}

}
