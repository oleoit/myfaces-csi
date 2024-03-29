package org.apache.myfaces.custom.skin.html.ext;

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
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlSelectOneOrManySkinRenderer extends AdapterSkinRenderer
{

    public HtmlSelectOneOrManySkinRenderer(String namespace, String componentTag)
    {
        super(namespace, componentTag);
    }

    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException
    {
        _addStyleDisabledReadOnlyRequired(context, component, arc);

        String disabledStyleClass = null;
        String enabledStyleClass = null;
        String displayValueOnlyStyleClass = null;

        String baseStyleClass = this.getBaseStyleName(component);

        disabledStyleClass = baseStyleClass
                + SkinConstants.DISABLED_CLASS_SUFFIX;
        enabledStyleClass = baseStyleClass + "::enabled";
        displayValueOnlyStyleClass = baseStyleClass + "::displayValueOnly";

        _renderStyleClass(component, context, arc, disabledStyleClass,
                "disabledClass");
        _renderStyleClass(component, context, arc, enabledStyleClass,
                "enabledClass");
        _renderStyleClass(component, context, arc, displayValueOnlyStyleClass,
                "displayValueOnlyStyleClass");
    }
}
