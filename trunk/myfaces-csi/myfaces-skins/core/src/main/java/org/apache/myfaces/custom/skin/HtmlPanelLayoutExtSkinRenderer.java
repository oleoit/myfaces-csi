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
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlPanelLayoutExtSkinRenderer extends GenericSkinRenderer {

	@Override
	protected void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		this.encodeGenericComponent(context, component, arc);

		String bodyStyleClass = null;
		String footerStyleClass = null;
		String headerStyleClass = null;
		String navigationStyleClass = null;
		

		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		bodyStyleClass = baseStyleClass + "::body";
		footerStyleClass = baseStyleClass + "::footer";
		headerStyleClass = baseStyleClass + "::header";
		navigationStyleClass = baseStyleClass + "::navigation";

		renderStyleClass(component, context, arc, bodyStyleClass,
			"bodyClass");		
		renderStyleClass(component, context, arc, footerStyleClass,
			"footerClass");
		renderStyleClass(component, context, arc, headerStyleClass,
			"headerClass");
		renderStyleClass(component, context, arc, navigationStyleClass,
			"navigationClass");
	}
	
}
