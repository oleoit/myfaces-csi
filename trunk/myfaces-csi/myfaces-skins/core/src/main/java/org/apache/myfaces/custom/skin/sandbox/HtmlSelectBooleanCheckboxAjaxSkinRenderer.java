package org.apache.myfaces.custom.skin.sandbox;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.ajax.api.AjaxRenderer;
import org.apache.myfaces.custom.inputAjax.HtmlSelectBooleanCheckboxAjax;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.trinidad.context.RenderingContext;
import org.apache.myfaces.trinidad.skin.Icon;

public class HtmlSelectBooleanCheckboxAjaxSkinRenderer 
    extends AdapterSkinRenderer implements AjaxRenderer 
{
    private static final Log log = LogFactory.getLog(HtmlSelectBooleanCheckboxAjax.class);
    
    public HtmlSelectBooleanCheckboxAjaxSkinRenderer()
    {
        super("s", "selectBooleanCheckboxAjax");
    }

    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException
    {
        _addStyleDisabledReadOnlyRequired(context, component, arc);

        String baseStyleClass = this.getBaseStyleName(component);

        String displayValueOnlyStyleClass = baseStyleClass
                + "::displayValueOnly";

        _renderStyleClass(component, context, arc, displayValueOnlyStyleClass,
                "displayValueOnlyStyleClass");

        _setOnIcon(context, (HtmlSelectBooleanCheckboxAjax) component, arc);
        _setOffIcon(context, (HtmlSelectBooleanCheckboxAjax) component, arc);
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
    private void _setOnIcon(FacesContext context, HtmlSelectBooleanCheckboxAjax component,
            RenderingContext arc)
    {

        String oldIcon = null;
        try
        {
            oldIcon = (String) component.getOnImage();
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
                value = (String) icon.getImageURI(context, arc);
                component.setOnImage(value);
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
    private void _setOffIcon(FacesContext context, HtmlSelectBooleanCheckboxAjax component,
            RenderingContext arc)
    {

        String oldIcon = null;
        try
        {
            oldIcon = (String) component.getOffImage();
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
                value = (String) icon.getImageURI(context, arc);
                component.setOffImage(value);
            }
        }
    }

    public void encodeAjax(FacesContext context, UIComponent component) throws IOException
    {
        // Delegate to proper renderer
        if (this.getDelegate() instanceof AjaxRenderer){
            ((AjaxRenderer)this.getDelegate()).encodeAjax(context, component);
        }else{
            log.warn("Renderer does not implement AjaxRenderer! "+ 
                    this.getDelegate());   
        }
    }    
}
