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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.column.HtmlColumn;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlLayoutingTitlePaneSkinRenderer extends AdapterSkinRenderer
{

    public HtmlLayoutingTitlePaneSkinRenderer()
    {
        super("s", "layoutingTitlePane");
    }

    /**
     * Apply the following css class style attributes:
     * 
     * bodyStyleClass footerstyleClass headerstyleClass rowGroupStyleClass
     * rowStyleClass styleClass
     * 
     * @param context
     * @param component
     * @param arc
     * @throws IOException
     */
    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException
    {

        String containerNodeClass = null;
        String labelNodeClass = null;

        String baseStyleClass = this.getBaseStyleName(component);

        containerNodeClass = baseStyleClass + "::containerNode";
        labelNodeClass = baseStyleClass + "::labelNode";

        _renderStyleClass(component, context, arc, containerNodeClass,
                "containerNodeClass");
        _renderStyleClass(component, context, arc, labelNodeClass,
                "labelNodeClass");
    }
}
