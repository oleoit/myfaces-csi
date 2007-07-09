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

/**
 * 
 * @author Leonardo
 *
 */
public class HtmlDataScrollerExtSkinRenderer extends SkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory
			.getLog(HtmlDataScrollerExtSkinRenderer.class);

	@Override
	protected void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		String fastfStyleClass = baseStyleClass + "::fastf";
		String fastrStyleClass = baseStyleClass + "::fastr";
		String firstStyleClass = baseStyleClass + "::first";
		String lastStyleClass = baseStyleClass + "::last";
		String nextStyleClass = baseStyleClass + "::next";
		String paginatorActiveColumnClass = baseStyleClass
				+ "::paginatorActiveColumn";
		String paginatorColumnClass = baseStyleClass + "::paginatorColumn";
		String paginatorTableClass = baseStyleClass + "::paginatorTable";
		String previousStyleClass = baseStyleClass + "::previous";
		String styleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;

		renderStyleClass(component, context, arc, fastfStyleClass,
				"fastfStyleClass");
		renderStyleClass(component, context, arc, fastrStyleClass,
				"fastrStyleClass");
		renderStyleClass(component, context, arc, firstStyleClass,
				"firstStyleClass");
		renderStyleClass(component, context, arc, lastStyleClass,
				"lastStyleClass");
		renderStyleClass(component, context, arc, nextStyleClass,
				"nextStyleClass");
		renderStyleClass(component, context, arc, paginatorActiveColumnClass,
				"paginatorActiveColumnClass");
		renderStyleClass(component, context, arc, paginatorColumnClass,
				"paginatorColumnClass");
		renderStyleClass(component, context, arc, paginatorTableClass,
				"paginatorTableClass");
		renderStyleClass(component, context, arc, previousStyleClass,
				"previousStyleClass");
		renderStyleClass(component, context, arc, styleClass, "styleClass");

	}

}
