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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.component.UIXComponent;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.OutputUtils;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.SkinSelectors;

/**
 * This class encapsulate Renderers of other RenderKits and add trinidad
 * skinning features. It implements a Decorator pattern style
 * 
 * @author Leonardo Uribe
 */
public class GenericSkinRenderer extends SkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(GenericSkinRenderer.class);

	/**
	 * Constructor
	 * 
	 * Save its delegate renderer
	 * 
	 * @param delegate
	 */
	public GenericSkinRenderer(Renderer delegate) {
		super(delegate);
	}
	
	public GenericSkinRenderer(){		
	}

	@Override
	protected void _addStyleClassesToComponent(FacesContext context, 
			UIComponent component, RenderingContext arc)
			throws IOException {

		// The task here is first check if the component is appropiate to
		// skinning (not inherit from UIXComponent) and has a styleClass
		// property
		// to do it.

		if (UIXComponent.class.isAssignableFrom(component.getClass())) {
			// Nothing because is a trinidad component.
		} else {
			this.encodeGenericComponent(context, component, arc);
		}
	}




	public static List<String> parseStyleClassListComma(String styleClass) {
		if (styleClass == null)
			return null;

		// If there's no spaces, it's just a single class - return
		// AdamWiner: should we care about all Unicode whitspace?
		// This will catch 99.9% of cases, and this code needs to be
		// fast
		int spaceIndex = styleClass.indexOf(',');
		if (spaceIndex < 0)
			return null;

		// Iterate through the string and build up the split list
		// AdamWiner: Regex split() would be a lot less code, but
		// it doesn't automatically trim empty strings.
		int prevSpaceIndex = 0;
		List<String> styleClasses = new ArrayList<String>();
		do {
			if (spaceIndex > prevSpaceIndex)
				styleClasses.add(styleClass.substring(prevSpaceIndex,
						spaceIndex));
			prevSpaceIndex = spaceIndex + 1;
			spaceIndex = styleClass.indexOf(',', prevSpaceIndex);
		} while (spaceIndex >= 0);

		if (prevSpaceIndex < styleClass.length())
			styleClasses.add(styleClass.substring(prevSpaceIndex));

		return styleClasses;
	}

	/*
	 * This method look if the component has 3 common methods:
	 * 
	 * 1. getStyleClass
	 * 2. isReadonly
	 * 3. isDisabled
	 * 
	 * And associate to properly css classes
	 * 
	 */
	public void encodeGenericComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		// 2. the skin class for this component looks like this:
		// af|javax_faces_component_html_HtmlXXX::class

		String contentStyleClass = component.getClass().getName();

		//Map<String, String> m = arc.getSkin().getStyleClassMap(arc);

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
			// Nothing happends
			//e.printStackTrace();
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
			// Nothing happends
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Nothing happends
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Nothing happends
			//e.printStackTrace();
		}

		try {
			method = component.getClass().getMethod("isDisabled",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				disabledStyleClass = SkinSelectors.STATE_DISABLED;
				otherStyles += 1;
			}
		} catch (SecurityException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Nothing happends
			// e.printStackTrace();
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
	
	/*
	 * This method look if the component has 4 common methods:
	 * 
	 * 1. getStyleClass
	 * 2. isReadonly
	 * 3. isDisabled
	 * 4. isRequired
	 * 
	 * And associate to properly css classes
	 * 
	 */
	public void encodeGenericWithRequiredComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		log.debug("Component class " + component.getClass().getName());

		// 2. the skin class for this component looks like this:
		// af|javax_faces_component_html_HtmlXXX::class

		String contentStyleClass = component.getClass().getName();

		//Map<String, String> m = arc.getSkin().getStyleClassMap(arc);

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
			// Nothing happends
			//e.printStackTrace();
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
		String requiredStyleClass = null;

		try {
			method = component.getClass().getMethod("isReadonly",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				readOnlyStyleClass = SkinSelectors.STATE_READ_ONLY;
				otherStyles += 1;
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// Nothing happends
			// e.printStackTrace();
		} catch (InvocationTargetException e) {
			// Nothing happends
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			// Nothing happends
			//e.printStackTrace();
		}

		try {
			method = component.getClass().getMethod("isDisabled",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				disabledStyleClass = SkinSelectors.STATE_DISABLED;
				otherStyles += 1;
			}
		} catch (SecurityException e) {
			// Nothing happends
			//e.printStackTrace();
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
			method = component.getClass().getMethod("isRequired",
					(Class[]) null);
			if ((Boolean) method.invoke(component, (Object[]) null)) {
				requiredStyleClass = baseStyleClass + "::required"; 
				otherStyles += 1;
			}
		} catch (SecurityException e) {
			//e.printStackTrace();
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

		String[] styleClasses = new String[userStyleClassCount + 4];
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
		styleClasses[i++] = requiredStyleClass;

		// 3. set the property styleClass, setting it.
		if (otherStyles > 0) {
			_renderStyleClasses(component, context, arc, styleClasses);
		} else {
			_renderStyleClass(component, context, arc, contentStyleClass);
		}
	}	
	
	
}
