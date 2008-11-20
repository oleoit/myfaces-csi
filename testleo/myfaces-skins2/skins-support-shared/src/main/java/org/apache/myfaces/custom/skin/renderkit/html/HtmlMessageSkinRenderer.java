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
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;

public class HtmlMessageSkinRenderer extends AdapterSkinRenderer
{

    public HtmlMessageSkinRenderer()
    {
        super("h", "message");
    }

    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, SkinRenderingContext arc) throws IOException
    {
        _addStyleClass(context, component, arc);

        // now add fatal, info, error and warn StyleClass
        String fatalStyleClass = null;
        String infoStyleClass = null;
        String errorStyleClass = null;
        String warnStyleClass = null;

        String baseStyleClass = this.getBaseStyleName(component);

        fatalStyleClass = baseStyleClass + "::fatal";
        infoStyleClass = baseStyleClass + "::info";
        errorStyleClass = baseStyleClass + "::error";
        warnStyleClass = baseStyleClass + "::warn";

        _renderStyleClass(component, context, arc, fatalStyleClass,
                "fatalClass");
        _renderStyleClass(component, context, arc, infoStyleClass, "infoClass");
        _renderStyleClass(component, context, arc, errorStyleClass,
                "errorClass");
        _renderStyleClass(component, context, arc, warnStyleClass, "warnClass");
    }
}
