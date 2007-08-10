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

public class HtmlTableSuggestAjaxSkinRenderer extends AdapterSkinRenderer
{

    public HtmlTableSuggestAjaxSkinRenderer()
    {
        super("s", "tableSuggestAjax");
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

        String popupStyleClass = null;
        String tableStyleClass = null;
        String comboBoxStyleClass = null;
        String rowStyleClass = null;
        String evenRowStyleClass = null;
        String oddRowStyleClass = null;
        String hoverStyleClass = null;
        String styleClass = null;
        String displayValueOnlyStyleClass = null;

        String baseStyleClass = this.getBaseStyleName(component);

        popupStyleClass = baseStyleClass + "::popup";
        tableStyleClass = baseStyleClass + "::table";
        comboBoxStyleClass = baseStyleClass + "::comboBox";
        rowStyleClass = baseStyleClass + "::row";
        evenRowStyleClass = baseStyleClass + "::evenRow";
        oddRowStyleClass = baseStyleClass + "::oddRow";
        hoverStyleClass = baseStyleClass + "::hoverStyle";
        styleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;
        displayValueOnlyStyleClass = baseStyleClass + "::displayValueOnly";

        _renderStyleClass(component, context, arc, popupStyleClass,
                "popupStyleClass");
        _renderStyleClass(component, context, arc, tableStyleClass,
                "tableStyleClass");
        _renderStyleClass(component, context, arc, comboBoxStyleClass,
                "comboBoxStyleClass");
        _renderStyleClass(component, context, arc, rowStyleClass,
                "rowStyleClass");
        _renderStyleClass(component, context, arc, evenRowStyleClass,
                "evenRowStyleClass");
        _renderStyleClass(component, context, arc, oddRowStyleClass,
                "oddRowStyleClass");
        _renderStyleClass(component, context, arc, hoverStyleClass,
                "hoverStyleClass");
        _renderStyleClass(component, context, arc, styleClass, "styleClass");
        _renderStyleClass(component, context, arc, displayValueOnlyStyleClass,
                "displayValueOnlyStyleClass");

        // Now set column styles
        for (Iterator iter = component.getChildren().iterator(); iter.hasNext();)
        {
            UIComponent child = (UIComponent) iter.next();

            if (HtmlColumn.class.isAssignableFrom(child.getClass()))
            {
                // Add styleClass attributes
                HtmlColumn col = (HtmlColumn) child;
                // footerstyleClass
                // headerstyleClass
                // styleClass

                if (child instanceof org.apache.myfaces.custom.crosstable.HtmlColumns)
                {
                    baseStyleClass = "t|columns";
                }
                else
                {
                    baseStyleClass = "t|column";
                }

                styleClass = baseStyleClass + SkinConstants.STYLE_CLASS_SUFFIX;
                String footerstyleClass = baseStyleClass + "::footer";
                String headerstyleClass = baseStyleClass + "::header";

                _renderStyleClass(child, context, arc, styleClass, "styleClass");
                _renderStyleClass(child, context, arc, footerstyleClass,
                        "footerstyleClass");
                _renderStyleClass(child, context, arc, headerstyleClass,
                        "headerstyleClass");
            }
        }
    }
}
