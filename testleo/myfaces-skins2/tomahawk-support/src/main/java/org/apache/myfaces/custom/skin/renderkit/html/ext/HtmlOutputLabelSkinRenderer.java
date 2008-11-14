package org.apache.myfaces.custom.skin.renderkit.html.ext;

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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.OutputUtils;

public class HtmlOutputLabelSkinRenderer extends AdapterSkinRenderer
{

    private static final Log log = LogFactory
            .getLog(HtmlOutputLabelSkinRenderer.class);

    public HtmlOutputLabelSkinRenderer()
    {
        super("t", "outputLabel");
    }

    /**
     * Apply the following css class style attributes:
     * 
     * styleClass required
     * 
     * @param context
     * @param component
     * @param arc
     * @throws IOException
     */
    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, SkinRenderingContext arc) throws IOException
    {
        String contentStyleClass = component.getClass().getName();

        String baseStyleClass = this.getBaseStyleName(component);

        contentStyleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;

        int otherStyles = 0;

        Map attributes = component.getAttributes();
        String styleClass = (String) attributes.get("styleClass");
        String requiredStyleClass = null;
        String disabledStyleClass = null;

        String forComponent = (String) component.getAttributes().get("for");

        if (forComponent != null)
        {
            if (!forComponent.equals(""))
            {
                try
                {
                    // Try to find the component
                    UIComponent c = component.findComponent(forComponent);

                    if (c != null)
                    {
                        Boolean o = (Boolean) c.getAttributes().get("required");
                        if (o != null)
                        {
                            if (o.equals(Boolean.TRUE))
                            {
                                // its necesary to add the attributes of
                                // required styleClass
                                requiredStyleClass = baseStyleClass
                                        + "::required";
                                otherStyles++;
                            }
                        }

                        o = (Boolean) c.getAttributes().get("disabled");
                        if (o != null)
                        {
                            if (o.equals(Boolean.TRUE))
                            {
                                disabledStyleClass = baseStyleClass
                                        + SkinConstants.DISABLED_CLASS_SUFFIX;
                                otherStyles++;
                            }
                        }

                    }
                    else
                    {
                        log.debug("Component " + forComponent + " Not found");
                    }

                }
                catch (java.lang.IllegalArgumentException e)
                {
                    // Nothing
                }

            }
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
        if (parsedStyleClasses != null)
        {
            while (i < userStyleClassCount)
            {
                styleClasses[i] = parsedStyleClasses.get(i);
                i++;
            }
        }
        else if (styleClass != null)
        {
            styleClasses[i++] = styleClass;
        }

        styleClasses[i++] = contentStyleClass;
        styleClasses[i++] = requiredStyleClass;
        styleClasses[i++] = disabledStyleClass;

        // 3. set the property styleClass, setting it.
        if (otherStyles > 0)
        {
            _renderStyleClasses(component, context, arc, styleClasses);
        }
        else
        {
            _renderStyleClass(component, context, arc, contentStyleClass);
        }
    }

}
