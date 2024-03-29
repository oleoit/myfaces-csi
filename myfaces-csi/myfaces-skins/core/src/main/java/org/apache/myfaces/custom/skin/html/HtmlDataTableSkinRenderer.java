package org.apache.myfaces.custom.skin.html;

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

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlDataTableSkinRenderer extends AdapterSkinRenderer
{

    public HtmlDataTableSkinRenderer()
    {
        super("h", "dataTable");
    }

    @Override
    protected void _addStyleClassesToComponent(FacesContext context,
            UIComponent component, RenderingContext arc) throws IOException
    {
        _addStyleClass(context, component, arc);

        String footerStyleClass = null;
        String headerStyleClass = null;
        String rowStyleClass = null;

        String baseStyleClass = this.getBaseStyleName(component);

        footerStyleClass = baseStyleClass + "::footer";
        headerStyleClass = baseStyleClass + "::header";
        rowStyleClass = baseStyleClass + "::row";

        _renderStyleClass(component, context, arc, footerStyleClass,
                "footerClass");
        _renderStyleClass(component, context, arc, headerStyleClass,
                "headerClass");

        Map m = component.getAttributes();
        String oldRowClasses = (String) m.get("rowClasses");
        List<String> list = parseStyleClassListComma(oldRowClasses);
        if (list == null)
        {
            _renderStyleClass(component, context, arc, rowStyleClass,
                    "rowClasses");
        }
        else
        {
            String def = arc.getStyleClass(rowStyleClass);
            if (def.startsWith("h_"))
            {
                // Nothing to do
            }
            else
            {
                String[] l1 = new String[list.size()];
                int length = 0;
                for (int i = 0; i < l1.length; i++)
                {
                    if (!list.get(i).contains(def))
                    {
                        l1[i] = list.get(i) + " " + def;
                        length += l1[i].length() + 1;
                    }
                    else
                    {
                        l1[i] = list.get(i);
                        length += l1[i].length() + 1;
                    }
                }

                StringBuilder builder = new StringBuilder(length);
                for (int i = 0; i < l1.length; i++)
                {
                    if (l1[i] != null)
                    {
                        if (builder.length() != 0)
                            builder.append(',');
                        builder.append(l1[i]);
                    }
                }
                component.getAttributes().put("rowClasses", builder.toString());
            }
        }
    }
}
