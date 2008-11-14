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
package org.apache.myfaces.custom.skin.renderkit.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.skin.Icon;
import org.apache.myfaces.trinidadinternal.skin.icon.ContextImageIcon;

public class HtmlGraphicImageSkinRenderer extends AdapterSkinRenderer
{

    public HtmlGraphicImageSkinRenderer()
    {
        super("h", "graphicImage");
    }

    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException
    {
        String baseStyleClass = getBaseStyleName(component);

        String styleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;

        _renderStyleClass(component, context, arc, styleClass, "styleClass");

        if (component instanceof HtmlGraphicImage)
        {
            _setIconWithHeightAndWidth(context, (HtmlGraphicImage) component,
                    arc);
        }
        else if (component instanceof UIGraphic)
        {
            _setIcon(context, (UIGraphic) component, arc);
        }
    }

    /**
     * This method get the value an if is the case replace the component value
     * with the path through icon mechanims of trinidad
     * 
     * @param context
     * @param component
     * @param arc
     * @param getProperty
     * @param setProperty
     */
    private void _setIcon(FacesContext context, UIGraphic component,
            RenderingContext arc)
    {

        String oldIcon = null;
        try
        {
            oldIcon = (String) component.getValue();
        }
        catch (ClassCastException e)
        {
            // do nothing, it doesn't affect the behavior
        }

        // if the user specified a icon path for this property
        if (oldIcon != null)
        {
            // Get a trinidad Icon instance
            Icon icon = arc.getIcon(oldIcon);
            if (icon != null)
            {
                String value = null;
                if (icon instanceof ContextImageIcon)
                {
                    // Correct the path
                    String baseContextPath = context.getExternalContext()
                            .getRequestContextPath() + '/';
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
                component.setValue(value);
            }
        }
    }

    /**
     * This method get the value an if is the case replace the component value
     * with the path through icon mechanims of trinidad
     * 
     * @param context
     * @param component
     * @param arc
     * @param getProperty
     * @param setProperty
     */
    private void _setIconWithHeightAndWidth(FacesContext context,
            HtmlGraphicImage component, RenderingContext arc)
    {

        String oldIcon = null;
        try
        {
            oldIcon = (String) component.getValue();
        }
        catch (ClassCastException e)
        {
            // do nothing, it doesn't affect the behavior
        }

        // if the user specified a icon path for this property
        if (oldIcon != null)
        {
            // Get a trinidad Icon instance
            Icon icon = arc.getIcon(oldIcon);
            if (icon != null)
            {
                String value = null;
                if (icon instanceof ContextImageIcon)
                {
                    // Correct the path
                    String baseContextPath = context.getExternalContext()
                            .getRequestContextPath() + '/';
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
                component.setValue(value);

                Integer height = icon.getImageHeight(arc);
                if (height != null)
                {
                    if (component.getHeight() == null)
                    {
                        component.setHeight(height.toString());
                    }
                    else if (component.getHeight() == "")
                    {
                        component.setHeight(height.toString());
                    }
                }
                Integer width = icon.getImageWidth(arc);
                if (width != null)
                {
                    if (component.getWidth() == null)
                    {
                        component.setWidth("" + width);
                    }
                    else if (component.getWidth() == "")
                    {
                        component.setWidth("" + width);
                    }
                }
            }
        }
    }
}
