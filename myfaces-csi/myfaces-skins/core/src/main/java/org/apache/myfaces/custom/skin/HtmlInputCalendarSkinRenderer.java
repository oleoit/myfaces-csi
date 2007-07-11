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
public class HtmlInputCalendarSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(HtmlInputCalendarSkinRenderer.class);	

	/**
	 * Apply the following css class style attributes:
	 * 
	 * currentDayCellClass
	 * dayCellClass
	 * displayValueOnlyStyleClass 
	 * monthYearRowClass
	 * popupButtonStyleClass
	 * styleClass
	 * weekRowClass
	 * 
	 * @param context
	 * @param component
	 * @param arc
	 * @throws IOException
	 */	
	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		this.encodeGenericWithRequiredComponent(context, component, arc);

		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		String currentDayCellStyleClass = baseStyleClass + "::currentDayCell";
		String dayCellStyleClass = baseStyleClass + "::dayCell";
		String displayValueOnlyStyleClass = baseStyleClass + "::displayValueOnly";
		String monthYearRowStyleClass = baseStyleClass + "::monthYearRow";
		String popupButtonStyleClass = baseStyleClass + "::popupButton";
		String weekRowStyleClass = baseStyleClass + "::weekRow";		
		
		_renderStyleClass(component, context, arc, currentDayCellStyleClass,
				"currentDayCellClass");
		_renderStyleClass(component, context, arc, dayCellStyleClass,
			"dayCellClass");		
		_renderStyleClass(component, context, arc, displayValueOnlyStyleClass,
			"displayValueOnlyStyleClass");		
		_renderStyleClass(component, context, arc, monthYearRowStyleClass,
			"monthYearRowClass");		
		_renderStyleClass(component, context, arc, popupButtonStyleClass,
			"popupButtonStyleClass");		
		_renderStyleClass(component, context, arc, weekRowStyleClass,
			"weekRowClass");				
	}

}
