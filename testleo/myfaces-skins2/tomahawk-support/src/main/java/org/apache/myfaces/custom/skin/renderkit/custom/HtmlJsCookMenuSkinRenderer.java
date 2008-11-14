package org.apache.myfaces.custom.skin.renderkit.custom;

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
import javax.faces.component.UIForm;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.trinidad.context.RenderingContext;

/**
 * 
 * @author Leonardo
 *
 */
public class HtmlJsCookMenuSkinRenderer extends AdapterSkinRenderer
{

    /**
     * The log factory used to debug messages
     */
    private static final Log log = LogFactory
            .getLog(HtmlJsCookMenuSkinRenderer.class);

    public HtmlJsCookMenuSkinRenderer()
    {
        super("t", "jscookMenu");
    }

    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException
    {

        //log.info("Rendering... javascript_location:"+(String) component.getAttributes().get(JSFAttr.JAVASCRIPT_LOCATION));

        String baseStyleClass = this.getBaseStyleName(component);

        String theme = baseStyleClass + "::theme";

        _renderReplaceStyleClass(component, context, arc, theme, "theme");
    }

    /**
     * Because this project uses Trinidad ViewHandler and StateManager 
     * Implementation, I have to write a hidden param JSCOOK_ACTION_PARAM
     * 
     */
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException
    {

        UIComponent parentForm = findNestingForm(component, context);

        if (!isAdfOrTrinidadForm(parentForm))
        {
            ResponseWriter writer = context.getResponseWriter();
            // need to add hidden input, cause MyFaces form is missing hence will not render hidden inputs
            writer.write("<input type=\"hidden\" name=\"");
            writer.write(JSCOOK_ACTION_PARAM);
            writer.write("\" />");
        }

        if (this._delegate != null)
            _delegate.encodeChildren(context, component);
    }

    //THIS METHODS ARE COPIED FROM TOMAHAWK CLASS RendererUtils

    private static final String JSCOOK_ACTION_PARAM = "jscook_action";

    /**
     * Find the enclosing form of a component
     * in the view-tree.
     * All Subclasses of <code>UIForm</code> and all known
     * form-families are searched for.
     * Currently those are the Trinidad form family,
     * and the (old) ADF Faces form family.
     * <p/>
     * There might be additional form families
     * which have to be explicitly entered here.
     *
     * @param uiComponent
     * @param facesContext
     * @return FormInfo Information about the form - the form itself and its name.
     */
    public static UIComponent findNestingForm(UIComponent uiComponent,
            FacesContext facesContext)
    {
        UIComponent parent = uiComponent.getParent();
        while (parent != null
                && (!ADF_FORM_COMPONENT_FAMILY.equals(parent.getFamily())
                        && !TRINIDAD_FORM_COMPONENT_FAMILY.equals(parent
                                .getFamily()) && !(parent instanceof UIForm)))
        {
            parent = parent.getParent();
        }

        if (parent != null)
        {
            //link is nested inside a form
            return parent;
        }

        return null;
    }

    private static final String TRINIDAD_FORM_COMPONENT_FAMILY = "org.apache.myfaces.trinidad.Form";
    private static final String ADF_FORM_COMPONENT_FAMILY = "oracle.adf.Form";

    public static boolean isAdfOrTrinidadForm(UIComponent component)
    {
        if (component == null)
            return false;
        return ADF_FORM_COMPONENT_FAMILY.equals(component.getFamily())
                || TRINIDAD_FORM_COMPONENT_FAMILY.equals(component.getFamily());
    }
}
