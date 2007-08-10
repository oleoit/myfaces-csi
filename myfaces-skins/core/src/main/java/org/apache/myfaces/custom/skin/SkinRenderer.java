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

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.skin.Icon;
import org.apache.myfaces.trinidadinternal.skin.icon.ContextImageIcon;

/**
 * This class encapsulate Renderers of other RenderKits and add trinidad
 * skinning features. It implements a Decorator pattern style
 * 
 * @author Leonardo Uribe
 */
public abstract class SkinRenderer extends Renderer
{

    /**
     * The renderer that encapsulate it
     */
    protected Renderer _delegate;

    /**
     * The log factory used to debug messages
     */
    private static final Log log = LogFactory.getLog(SkinRenderer.class);

    private String componentTag = null;

    private String namespace = null;

    /**
     * Constructor
     * 
     * Save its delegate renderer
     * 
     * @param delegate
     */
    public SkinRenderer(Renderer delegate)
    {
        _delegate = delegate;
    }

    public SkinRenderer()
    {
    }

    @Override
    public String convertClientId(FacesContext context, String clientId)
    {
        if (_delegate == null)
        {
            return super.convertClientId(context, clientId);
        }
        else
        {
            return _delegate.convertClientId(context, clientId);
        }
    }

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        if (_delegate == null)
        {
            super.decode(context, component);
        }
        else
        {
            _delegate.decode(context, component);
        }
    }

    protected abstract void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException;

    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {

        RenderingContext arc = RenderingContext.getCurrentInstance();
        if (arc == null)
            throw new IllegalStateException(("NO_RENDERINGCONTEXT"));

        this._addStyleClassesToComponent(context, component, arc);

        // Call encode of delegate or parent
        if (_delegate == null)
        {
            super.encodeBegin(context, component);
        }
        else
        {
            _delegate.encodeBegin(context, component);
        }
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException
    {
        if (_delegate == null)
        {
            super.encodeChildren(context, component);
        }
        else
        {
            _delegate.encodeChildren(context, component);
        }
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {

        if (_delegate == null)
        {
            super.encodeEnd(context, component);
        }
        else
        {
            _delegate.encodeEnd(context, component);
        }
    }

    @Override
    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException
    {
        if (_delegate == null)
        {
            return super.getConvertedValue(context, component, submittedValue);
        }
        else
        {
            return _delegate.getConvertedValue(context, component,
                    submittedValue);
        }
    }

    @Override
    public boolean getRendersChildren()
    {
        if (_delegate == null)
        {
            return super.getRendersChildren();
        }
        else
        {
            return _delegate.getRendersChildren();
        }
    }

    public Renderer getDelegate()
    {
        return this._delegate;
    }

    public void setDelegate(Renderer delegate)
    {
        this._delegate = delegate;
    }

    /**
     * Render a generic CSS styleClass (not one derived from an attribute). The
     * styleclass will be passed through the RenderingContext getStyleClass()
     * API.
     * 
     * @param context
     *            the FacesContext
     * @param styleClass
     *            the style class
     */
    protected void _renderStyleClass(UIComponent component,
            FacesContext context, RenderingContext arc, String styleClass)
            throws IOException
    {
        if (styleClass != null)
        {
            styleClass = arc.getStyleClass(styleClass);

            if (styleClass != null)
            {
                if (styleClass.startsWith((this.getNamespace() == null) ? "af_"
                        : this.getNamespace()))
                {
                    //no styleClass found
                    return;
                }
                // log.info("Writing styleClass:" + styleClass+" to
                // "+component.toString());
                String oldStyle = (String) component.getAttributes().get(
                        "styleClass");

                if (oldStyle != null)
                {
                    if (oldStyle.equals(""))
                    {
                        component.getAttributes().put("styleClass", styleClass);
                    }
                    else if (!StringUtils.contains(oldStyle, styleClass))
                    {
                        // Do not set the component, because it has another
                        // class
                        // already set

                        component.getAttributes().put("styleClass",
                                oldStyle + " " + styleClass);

                    }
                }
                else
                {
                    if (styleClass != null)
                    {
                        component.getAttributes().put("styleClass", styleClass);
                    }
                }
            }
        }
    }

    protected void _renderStyleClass(UIComponent component,
            FacesContext context, RenderingContext arc, String styleClass,
            String property) throws IOException
    {
        if (styleClass != null)
        {
            styleClass = arc.getStyleClass(styleClass);

            if (styleClass != null)
            {
                if (styleClass.startsWith((this.getNamespace() == null) ? "af_"
                        : this.getNamespace()))
                {
                    //no styleClass found
                    return;
                }
                // log.info("Writing styleClass:" + styleClass+" to
                // "+component.toString());
                String oldStyle = (String) component.getAttributes().get(
                        property);

                if (oldStyle != null)
                {
                    if (oldStyle.equals(""))
                    {
                        component.getAttributes().put(property, styleClass);
                    }
                    else if (!StringUtils.contains(oldStyle, styleClass))
                    {
                        // Do not set the component, because it has another
                        // class
                        // already set

                        component.getAttributes().put(property,
                                oldStyle + " " + styleClass);

                    }
                }
                else
                {
                    if (styleClass != null)
                    {
                        component.getAttributes().put(property, styleClass);
                    }
                }
            }
        }
    }

    protected void _renderReplaceStyleClass(UIComponent component,
            FacesContext context, RenderingContext arc, String styleClass,
            String property) throws IOException
    {
        if (styleClass != null)
        {
            styleClass = arc.getStyleClass(styleClass);

            if (styleClass != null)
            {
                if (styleClass.startsWith((this.getNamespace() == null) ? "af_"
                        : this.getNamespace()))
                {
                    //no styleClass found
                    return;
                }
                // log.info("Writing styleClass:" + styleClass+" to
                // "+component.toString());
                String oldStyle = (String) component.getAttributes().get(
                        property);

                if (oldStyle != null)
                {
                    if (oldStyle.equals(""))
                    {
                        component.getAttributes().put(property, styleClass);
                    }
                }
                else
                {
                    if (styleClass != null)
                    {
                        component.getAttributes().put(property, styleClass);
                    }
                }
            }
        }
    }

    /**
     * Render an array of CSS styleClasses as space-separated values. NOTE: the
     * array is mutated during this method, and cannot be reused! Each
     * styleclass will be passed through the RenderingContext getStyleClass()
     * API.
     * 
     * @param context
     *            the FacesContext
     * @param styleClasses
     *            the style classes
     */
    protected void _renderStyleClasses(UIComponent component,
            FacesContext context, RenderingContext arc, String[] styleClasses)
            throws IOException
    {
        int length = 0;
        for (int i = 0; i < styleClasses.length; i++)
        {
            if (styleClasses[i] != null)
            {
                String styleClass = arc.getStyleClass(styleClasses[i]);
                if (styleClass.startsWith((this.getNamespace() == null) ? "af_"
                        : this.getNamespace()))
                {
                    //No add any that startsWith af_
                    styleClass = null;
                }
                if (styleClass != null)
                    length += styleClass.length() + 1;
                styleClasses[i] = styleClass;
            }
        }

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < styleClasses.length; i++)
        {
            if (styleClasses[i] != null)
            {
                if (builder.length() != 0)
                    builder.append(' ');
                builder.append(styleClasses[i]);
            }
        }

        component.getAttributes().put("styleClass", builder.toString());
        // context.getResponseWriter().writeAttribute("class",
        // builder.toString(), null);
    }

    protected void _setIconDirection(FacesContext context,
            UIComponent component, RenderingContext arc, String styleClass,
            String getProperty, String setProperty)
    {

        //This case is something strange. The style properties that
        //i set throught component.getAttributes.put(....) works, but
        //the image properties like in HtmlTree NOT!!!!

        Method method;
        String oldIcon = null;
        // Check it has a getStyleClass property
        try
        {
            method = component.getClass()
                    .getMethod(getProperty, (Class[]) null);
            oldIcon = (String) method.invoke(component, (Object[]) null);
        }
        catch (SecurityException e)
        {
            log.debug("SecurityException" + e.getMessage());
            return;
        }
        catch (NoSuchMethodException e)
        {
            // Nothing happends
            // e.printStackTrace();
            log.debug("NoSuchMethodException" + e.getMessage());
            return;
        }
        catch (InvocationTargetException e)
        {
            // Nothing happends
            log.debug("InvocationTargetException" + e.getMessage());
            return;
        }
        catch (IllegalAccessException e)
        {
            // Nothing happends
            log.debug("IllegalAccessException" + e.getMessage());
            return;
        }

        // if the user specified a icon path for this property
        if (oldIcon == null)
        {
            //log.info("ICON NULO:"+arc.getIcon(styleClass)+" "+arc.getSkin().getIcon(styleClass));
            Icon icon = arc.getIcon(styleClass);
            if (icon != null)
            {
                String value = null;
                String baseContextPath = context.getExternalContext()
                        .getRequestContextPath() + '/';

                //For now i substract the request context path
                if (icon instanceof ContextImageIcon)
                {
                    value = (String) icon.getImageURI(context, arc);
                    if (value.startsWith(baseContextPath))
                    {
                        value = value.substring(baseContextPath.length() - 1);
                    }
                }
                else
                {
                    value = (String) icon.getImageURI(context, arc);
                }
                //log.info("ICON PATH:"+value);

                //I have to substract the base 

                // Check it has a getStyleClass property
                try
                {
                    Class[] classes = new Class[] { String.class };
                    method = component.getClass().getMethod(setProperty,
                            classes);
                    Object[] params = new Object[] { value };
                    method.invoke(component, params);
                }
                catch (SecurityException e)
                {
                    log.debug("SecurityException" + e.getMessage());
                    return;
                }
                catch (NoSuchMethodException e)
                {
                    // Nothing happends
                    // e.printStackTrace();
                    log.debug("NoSuchMethodException" + e.getMessage());
                    return;
                }
                catch (InvocationTargetException e)
                {
                    // Nothing happends
                    log.debug("InvocationTargetException" + e.getMessage());
                    return;
                }
                catch (IllegalAccessException e)
                {
                    // Nothing happends
                    log.debug("IllegalAccessException" + e.getMessage());
                    return;
                }
            }
        }
    }

    public String getComponentTag()
    {
        return componentTag;
    }

    public void setComponentTag(String componentTag)
    {
        this.componentTag = componentTag;
    }

    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

}
