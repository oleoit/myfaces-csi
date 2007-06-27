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

public class HtmlMessageSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(HtmlMessageSkinRenderer.class);	
	
	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		log.info("HtmlMessageSkinRenderer rendered");
		this.encodeHtmlMessage(context, component, arc);
	}
	
	public void encodeHtmlMessage(FacesContext context, UIComponent component,
			RenderingContext arc) throws IOException {

		this.encodeGenericComponent(context, component, arc);

		// now add fatal, info, error and warn StyleClass
		String fatalStyleClass = null;
		String infoStyleClass = null;
		String errorStyleClass = null;
		String warnStyleClass = null;

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		fatalStyleClass = baseStyleClass + "::fatal";
		infoStyleClass = baseStyleClass + "::info";
		errorStyleClass = baseStyleClass + "::error";
		warnStyleClass = baseStyleClass + "::warn";

		renderStyleClass(component, context, arc, fatalStyleClass, "fatalClass");
		renderStyleClass(component, context, arc, infoStyleClass, "infoClass");
		renderStyleClass(component, context, arc, errorStyleClass, "errorClass");
		renderStyleClass(component, context, arc, warnStyleClass, "warnClass");
	}

}
