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
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlPanelTabbedSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory
			.getLog(HtmlPanelTabbedSkinRenderer.class);

	/**
	 * Apply the following css class style attributes:
	 *
	 * activeSubStyleClass
	 * activeTabStyleClass
	 * disabledTabStyleClass
	 * inactiveSubStyleClass
	 * inactiveTabStyleClass
	 * styleClass
	 * tabContentStyleClass
	 * 
	 * @param context
	 * @param component
	 * @param arc
	 * @throws IOException
	 */	
	@Override
	protected void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		String activeSubStyleClass = baseStyleClass + "::activeSub";
		String activeTabStyleClass = baseStyleClass + "::activeTab";
		String disabledTabStyleClass = baseStyleClass + "::disabledTab";
		String inactiveSubStyleClass = baseStyleClass + "::inactiveSub";
		String inactiveTabStyleClass = baseStyleClass + "::next";
		String tabContentStyleClass = baseStyleClass
				+ "::paginatorActiveColumn";
		String styleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;

		renderStyleClass(component, context, arc, activeSubStyleClass,
				"activeSubStyleClass");
		renderStyleClass(component, context, arc, activeTabStyleClass,
				"activeTabStyleClass");
		renderStyleClass(component, context, arc, disabledTabStyleClass,
				"disabledTabStyleClass");
		renderStyleClass(component, context, arc, inactiveSubStyleClass,
				"inactiveSubStyleClass");
		renderStyleClass(component, context, arc, inactiveTabStyleClass,
				"inactiveTabStyleClass");
		renderStyleClass(component, context, arc, tabContentStyleClass,
				"tabContentStyleClass");
		renderStyleClass(component, context, arc, styleClass, "styleClass");
	}

}
