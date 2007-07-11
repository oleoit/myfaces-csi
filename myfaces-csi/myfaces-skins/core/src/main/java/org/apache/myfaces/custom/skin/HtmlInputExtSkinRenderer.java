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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.OutputUtils;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.SkinSelectors;

/**
 * 
 * @author Leonardo
 *
 */
public class HtmlInputExtSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(HtmlInputExtSkinRenderer.class);	

	/**
	 * Apply the following css class style attributes:
	 * 
	 * displayValueOnlyStyleClass
	 * styleClass
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
		String displayValueOnlyStyleClass = null;

		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		displayValueOnlyStyleClass = baseStyleClass + "::displayValueOnly";		

		_renderStyleClass(component, context, arc, displayValueOnlyStyleClass,
				"displayValueOnlyStyleClass");		
	}
	
	/**
	 * This method look if the component has 3 common methods:
	 * 
	 * 1. getStyleClass
	 * 2. isReadonly
	 * 3. isDisabled
	 * 
	 * And associate to properly css classes
	 * 
	 */
	@Override
	public void encodeGenericComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		log.info("Component class" + component.getClass().getName());

		// 2. the skin class for this component looks like this:
		// af|javax_faces_component_html_HtmlXXX::class

		String contentStyleClass = component.getClass().getName();

		Map<String, String> m = arc.getSkin().getStyleClassMap(arc);

		String baseStyleClass = SkinConstants.DEFAULT_NAMESPACE
				+ StringUtils.replaceChars(contentStyleClass, '.', '_');

		Method method;
		// Check it has a getStyleClass property
		contentStyleClass = null;
		try {
			method = component.getClass().getMethod("getStyleClass",
					(Class[]) null);
			contentStyleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		}

		int otherStyles = 0;

		// Its necesary to add other style properties like
		// p_AFReadOnly and p_AFDisabled
		Map attributes = component.getAttributes();
		String styleClass = (String) attributes.get("styleClass");
		String disabledStyleClass = null;
		String readOnlyStyleClass = null;

		try {
			method = component.getClass().getMethod("isReadonly",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				readOnlyStyleClass = SkinSelectors.STATE_READ_ONLY;
				otherStyles += 1;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Nothing happends
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Nothing happends
			e.printStackTrace();
		}

		boolean isDisabled = false;
		try {			
			method = component.getClass().getMethod("isDisabled",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				isDisabled = true;
				disabledStyleClass = SkinSelectors.STATE_DISABLED;
				otherStyles += 1;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Nothing happends
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Nothing happends
			e.printStackTrace();
		}
		
		try {			
			method = component.getClass().getMethod("isDisabledOnClientSide",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				//If already has been applied disabled style class
				if (!isDisabled){
					isDisabled = true;
					disabledStyleClass = SkinSelectors.STATE_DISABLED;
					otherStyles += 1;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Nothing happends
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Nothing happends
			e.printStackTrace();
		}

		List<String> parsedStyleClasses = OutputUtils
				.parseStyleClassList(styleClass);
		int userStyleClassCount;
		if (parsedStyleClasses == null)
			userStyleClassCount = (styleClass == null) ? 0 : 1;
		else
			userStyleClassCount = parsedStyleClasses.size();

		String[] styleClasses = new String[userStyleClassCount + 3];
		int i = 0;
		if (parsedStyleClasses != null) {
			while (i < userStyleClassCount) {
				styleClasses[i] = parsedStyleClasses.get(i);
				i++;
			}
		} else if (styleClass != null) {
			styleClasses[i++] = styleClass;
		}

		styleClasses[i++] = contentStyleClass;
		styleClasses[i++] = disabledStyleClass;
		styleClasses[i++] = readOnlyStyleClass;

		// 3. set the property styleClass, setting it.
		if (otherStyles > 0) {
			_renderStyleClasses(component, context, arc, styleClasses);
		} else {
			_renderStyleClass(component, context, arc, contentStyleClass);
		}
	}
	
}
