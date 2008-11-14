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
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;

public class HtmlScheduleSkinRenderer extends AdapterSkinRenderer
{

    public HtmlScheduleSkinRenderer()
    {
        super("t", "schedule");
    }

    /**
     * The log factory used to debug messages
     */
    private static final Log log = LogFactory
            .getLog(HtmlScheduleSkinRenderer.class);

    /**
     * Apply the following css class style attributes:
     * 
     * currentDayCellClass
     * dayCellClass
     * displayValueOnlyStyleClass 
     * monthYearRowClass
     * popupButtonStyleClass
     * styleClass
     * weekRowClass
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
        String baseStyleClass = this.getBaseStyleName(component);

        String backgroundStyleClass = baseStyleClass + "::background";
        String columnStyleClass = baseStyleClass + "::column";
        String contentStyleClass = baseStyleClass + "::content";
        String dateStyleClass = baseStyleClass + "::date";
        String dayStyleClass = baseStyleClass + "::day";
        String entryStyleClass = baseStyleClass + "::entry";
        String evenStyleClass = baseStyleClass + "::even";
        String foregroundStyleClass = baseStyleClass + "::foreground";
        String freeStyleClass = baseStyleClass + "::free";
        String gutterStyleClass = baseStyleClass + "::gutter";
        String headerStyleClass = baseStyleClass + "::header";
        String holidayStyleClass = baseStyleClass + "::holiday";
        String hoursStyleClass = baseStyleClass + "::hours";
        String inactiveDayStyleClass = baseStyleClass + "::inactiveDay";
        String minutesStyleClass = baseStyleClass + "::minutes";
        String monthStyleClass = baseStyleClass + "::month";
        String selectedStyleClass = baseStyleClass + "::selected";
        String selectedEntryStyleClass = baseStyleClass + "::selectedEntry";
        String subtitleStyleClass = baseStyleClass + "::subtitle";
        String textStyleClass = baseStyleClass + "::text";
        String titleStyleClass = baseStyleClass + "::title";
        String unevenStyleClass = baseStyleClass + "::uneven";
        String weekStyleClass = baseStyleClass + "::week";

        _renderStyleClass(component, context, arc, backgroundStyleClass,
                "backgroundClass");
        _renderStyleClass(component, context, arc, columnStyleClass,
                "columnClass");
        _renderStyleClass(component, context, arc, contentStyleClass,
                "contentClass");
        _renderStyleClass(component, context, arc, dateStyleClass, "dateClass");
        _renderStyleClass(component, context, arc, dayStyleClass, "dayClass");
        _renderStyleClass(component, context, arc, entryStyleClass,
                "entryClass");
        _renderStyleClass(component, context, arc, evenStyleClass, "evenClass");
        _renderStyleClass(component, context, arc, foregroundStyleClass,
                "foregroundClass");
        _renderStyleClass(component, context, arc, freeStyleClass, "freeClass");
        _renderStyleClass(component, context, arc, gutterStyleClass,
                "gutterClass");
        _renderStyleClass(component, context, arc, headerStyleClass,
                "headerClass");
        _renderStyleClass(component, context, arc, holidayStyleClass,
                "holidayClass");
        _renderStyleClass(component, context, arc, hoursStyleClass,
                "hoursClass");
        _renderStyleClass(component, context, arc, inactiveDayStyleClass,
                "inactiveDayClass");
        _renderStyleClass(component, context, arc, minutesStyleClass,
                "minutesClass");
        _renderStyleClass(component, context, arc, monthStyleClass,
                "monthClass");
        _renderStyleClass(component, context, arc, selectedStyleClass,
                "selectedClass");
        _renderStyleClass(component, context, arc, selectedEntryStyleClass,
                "selectedEntryClass");
        _renderStyleClass(component, context, arc, subtitleStyleClass,
                "subtitleClass");
        _renderStyleClass(component, context, arc, textStyleClass, "textClass");
        _renderStyleClass(component, context, arc, titleStyleClass,
                "titleClass");
        _renderStyleClass(component, context, arc, unevenStyleClass,
                "unevenClass");
        _renderStyleClass(component, context, arc, weekStyleClass, "weekClass");
    }

}
