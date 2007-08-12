package org.apache.myfaces.trinidad.component.core.layout;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import javax.faces.component.UIComponent;

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.bean.PropertyKey;
import org.apache.myfaces.trinidad.component.UIXPanel;
import org.apache.myfaces.trinidad.util.ComponentUtils;

public class CoreTableFormLayout extends UIXPanel
{
    static public final FacesBean.Type TYPE = new FacesBean.Type(UIXPanel.TYPE);
    static public final PropertyKey INLINE_STYLE_KEY = TYPE.registerKey(
            "inlineStyle", String.class);
    static public final PropertyKey STYLE_CLASS_KEY = TYPE.registerKey(
            "styleClass", String.class);
    static public final PropertyKey SHORT_DESC_KEY = TYPE.registerKey(
            "shortDesc", String.class);
    static public final PropertyKey PARTIAL_TRIGGERS_KEY = TYPE.registerKey(
            "partialTriggers", String[].class);
    static public final PropertyKey ONCLICK_KEY = TYPE.registerKey("onclick",
            String.class);
    static public final PropertyKey ONDBLCLICK_KEY = TYPE.registerKey(
            "ondblclick", String.class);
    static public final PropertyKey ONMOUSEDOWN_KEY = TYPE.registerKey(
            "onmousedown", String.class);
    static public final PropertyKey ONMOUSEUP_KEY = TYPE.registerKey(
            "onmouseup", String.class);
    static public final PropertyKey ONMOUSEOVER_KEY = TYPE.registerKey(
            "onmouseover", String.class);
    static public final PropertyKey ONMOUSEMOVE_KEY = TYPE.registerKey(
            "onmousemove", String.class);
    static public final PropertyKey ONMOUSEOUT_KEY = TYPE.registerKey(
            "onmouseout", String.class);
    static public final PropertyKey ONKEYPRESS_KEY = TYPE.registerKey(
            "onkeypress", String.class);
    static public final PropertyKey ONKEYDOWN_KEY = TYPE.registerKey(
            "onkeydown", String.class);
    static public final PropertyKey ONKEYUP_KEY = TYPE.registerKey("onkeyup",
            String.class);
    //	static public final PropertyKey MAX_COLUMNS_KEY =
    //		TYPE.registerKey("maxColumns", Integer.class);
    //	static public final PropertyKey ROWS_KEY =
    //		TYPE.registerKey("rows", Integer.class);
    static public final String FOOTER_FACET = "footer";

    //Custom properties
    static public final PropertyKey ROWS_KEY = TYPE.registerKey("rows",
            String.class);
    static public final PropertyKey COLUMNS_KEY = TYPE.registerKey("columns",
            String.class);
    static public final PropertyKey HEIGHT_KEY = TYPE.registerKey("height",
            String.class);
    static public final PropertyKey WIDTH_KEY = TYPE.registerKey("width",
            String.class);
    static public final PropertyKey CELLSPACING_KEY = TYPE.registerKey(
            "cellspacing", String.class);

    static public final String COMPONENT_FAMILY = "org.apache.myfaces.trinidad.Panel";
    static public final String COMPONENT_TYPE = "org.apache.myfaces.trinidad.CoreTableFormLayout";

    /**
     * Construct an instance of the CoreTableFormLayout.
     */
    public CoreTableFormLayout()
    {
        super("org.apache.myfaces.trinidad.TableLayout");
    }

    /**
     * This facet is rendered under the columns and usually contains a panelButtonBar.
     *              <p>
     * This facet may not always line up perfectly, especially if the labels or fields are longer than expected.  Try adjusting things with the labelWidth and fieldWidth attributes when necessary.  Also, the columns attribute on inputText may be helpful.
     *              </p>
     */
    final public UIComponent getFooter()
    {
        return getFacet(FOOTER_FACET);
    }

    /**
     * This facet is rendered under the columns and usually contains a panelButtonBar.
     *              <p>
     * This facet may not always line up perfectly, especially if the labels or fields are longer than expected.  Try adjusting things with the labelWidth and fieldWidth attributes when necessary.  Also, the columns attribute on inputText may be helpful.
     *              </p>
     */
    @SuppressWarnings("unchecked")
    final public void setFooter(UIComponent footerFacet)
    {
        getFacets().put(FOOTER_FACET, footerFacet);
    }

    /**
     * Gets the CSS styles to use for this component.
     */
    final public String getInlineStyle()
    {
        return ComponentUtils.resolveString(getProperty(INLINE_STYLE_KEY));
    }

    /**
     * Sets the CSS styles to use for this component.
     */
    final public void setInlineStyle(String inlineStyle)
    {
        setProperty(INLINE_STYLE_KEY, (inlineStyle));
    }

    /**
     * Gets a CSS style class to use for this component.
     */
    final public String getStyleClass()
    {
        return ComponentUtils.resolveString(getProperty(STYLE_CLASS_KEY));
    }

    /**
     * Sets a CSS style class to use for this component.
     */
    final public void setStyleClass(String styleClass)
    {
        setProperty(STYLE_CLASS_KEY, (styleClass));
    }

    /**
     * Gets The short description of the component. This text is commonly used by user agents to display tooltip help text.
     */
    final public String getShortDesc()
    {
        return ComponentUtils.resolveString(getProperty(SHORT_DESC_KEY));
    }

    /**
     * Sets The short description of the component. This text is commonly used by user agents to display tooltip help text.
     */
    final public void setShortDesc(String shortDesc)
    {
        setProperty(SHORT_DESC_KEY, (shortDesc));
    }

    /**
     * Gets the IDs of the components that should trigger a partial update.
     *         This component will listen on the trigger components. If one of the
     *         trigger components receives an event that will cause it to update
     *         in some way, this component will request to be updated too.
     *         Identifiers are relative to the source component, and must account for
     *         NamingContainers.  If your component is already inside of a naming
     *         container, you can use a single colon to start the search from the root,
     *         or multiple colons to move up through the NamingContainers - "::" will
     *         search from the parent naming container, ":::" will search
     *         from the grandparent naming container, etc.
     */
    final public String[] getPartialTriggers()
    {
        return (String[]) getProperty(PARTIAL_TRIGGERS_KEY);
    }

    /**
     * Sets the IDs of the components that should trigger a partial update.
     *         This component will listen on the trigger components. If one of the
     *         trigger components receives an event that will cause it to update
     *         in some way, this component will request to be updated too.
     *         Identifiers are relative to the source component, and must account for
     *         NamingContainers.  If your component is already inside of a naming
     *         container, you can use a single colon to start the search from the root,
     *         or multiple colons to move up through the NamingContainers - "::" will
     *         search from the parent naming container, ":::" will search
     *         from the grandparent naming container, etc.
     */
    final public void setPartialTriggers(String[] partialTriggers)
    {
        setProperty(PARTIAL_TRIGGERS_KEY, (partialTriggers));
    }

    /**
     * Gets an onclick Javascript handler.
     */
    final public String getOnclick()
    {
        return ComponentUtils.resolveString(getProperty(ONCLICK_KEY));
    }

    /**
     * Sets an onclick Javascript handler.
     */
    final public void setOnclick(String onclick)
    {
        setProperty(ONCLICK_KEY, (onclick));
    }

    /**
     * Gets an ondblclick Javascript handler.
     */
    final public String getOndblclick()
    {
        return ComponentUtils.resolveString(getProperty(ONDBLCLICK_KEY));
    }

    /**
     * Sets an ondblclick Javascript handler.
     */
    final public void setOndblclick(String ondblclick)
    {
        setProperty(ONDBLCLICK_KEY, (ondblclick));
    }

    /**
     * Gets an onmousedown Javascript handler.
     */
    final public String getOnmousedown()
    {
        return ComponentUtils.resolveString(getProperty(ONMOUSEDOWN_KEY));
    }

    /**
     * Sets an onmousedown Javascript handler.
     */
    final public void setOnmousedown(String onmousedown)
    {
        setProperty(ONMOUSEDOWN_KEY, (onmousedown));
    }

    /**
     * Gets an onmouseup Javascript handler.
     */
    final public String getOnmouseup()
    {
        return ComponentUtils.resolveString(getProperty(ONMOUSEUP_KEY));
    }

    /**
     * Sets an onmouseup Javascript handler.
     */
    final public void setOnmouseup(String onmouseup)
    {
        setProperty(ONMOUSEUP_KEY, (onmouseup));
    }

    /**
     * Gets an onmouseover Javascript handler.
     */
    final public String getOnmouseover()
    {
        return ComponentUtils.resolveString(getProperty(ONMOUSEOVER_KEY));
    }

    /**
     * Sets an onmouseover Javascript handler.
     */
    final public void setOnmouseover(String onmouseover)
    {
        setProperty(ONMOUSEOVER_KEY, (onmouseover));
    }

    /**
     * Gets an onmousemove Javascript handler.
     */
    final public String getOnmousemove()
    {
        return ComponentUtils.resolveString(getProperty(ONMOUSEMOVE_KEY));
    }

    /**
     * Sets an onmousemove Javascript handler.
     */
    final public void setOnmousemove(String onmousemove)
    {
        setProperty(ONMOUSEMOVE_KEY, (onmousemove));
    }

    /**
     * Gets an onmouseout Javascript handler.
     */
    final public String getOnmouseout()
    {
        return ComponentUtils.resolveString(getProperty(ONMOUSEOUT_KEY));
    }

    /**
     * Sets an onmouseout Javascript handler.
     */
    final public void setOnmouseout(String onmouseout)
    {
        setProperty(ONMOUSEOUT_KEY, (onmouseout));
    }

    /**
     * Gets an onkeypress Javascript handler.
     */
    final public String getOnkeypress()
    {
        return ComponentUtils.resolveString(getProperty(ONKEYPRESS_KEY));
    }

    /**
     * Sets an onkeypress Javascript handler.
     */
    final public void setOnkeypress(String onkeypress)
    {
        setProperty(ONKEYPRESS_KEY, (onkeypress));
    }

    /**
     * Gets an onkeydown Javascript handler.
     */
    final public String getOnkeydown()
    {
        return ComponentUtils.resolveString(getProperty(ONKEYDOWN_KEY));
    }

    /**
     * Sets an onkeydown Javascript handler.
     */
    final public void setOnkeydown(String onkeydown)
    {
        setProperty(ONKEYDOWN_KEY, (onkeydown));
    }

    /**
     * Gets an onkeyup Javascript handler.
     */
    final public String getOnkeyup()
    {
        return ComponentUtils.resolveString(getProperty(ONKEYUP_KEY));
    }

    /**
     * Sets an onkeyup Javascript handler.
     */
    final public void setOnkeyup(String onkeyup)
    {
        setProperty(ONKEYUP_KEY, (onkeyup));
    }

    /**
     * Gets Sets the maximum number of columns to show.  This attribute defaults to 3 and 2 on PDAs.  If this panelFormLayout is inside of another panelFormLayout, this will always be 1.
     */
    /*
     final public int getMaxColumns()
     {
     return ComponentUtils.resolveInteger(getProperty(MAX_COLUMNS_KEY));
     }*/

    /**
     * Sets Sets the maximum number of columns to show.  This attribute defaults to 3 and 2 on PDAs.  If this panelFormLayout is inside of another panelFormLayout, this will always be 1.
     */
    /*
     final public void setMaxColumns(int maxColumns)
     {
     setProperty(MAX_COLUMNS_KEY, Integer.valueOf(maxColumns));
     }
     */

    /**
     * Gets Sets the number of rows after which to start a new column.  This attribute defaults to Integer.MAX_VALUE.  The number of rows actually rendered depends also on the "maxColumns" property.  If the children will not fit in the given number of rows and columns, the number of rows will increase to accomodate the children.
     */
    /*
     final public int getRows()
     {
     return ComponentUtils.resolveInteger(getProperty(ROWS_KEY));
     }
     */

    /**
     * Sets Sets the number of rows after which to start a new column.  This attribute defaults to Integer.MAX_VALUE.  The number of rows actually rendered depends also on the "maxColumns" property.  If the children will not fit in the given number of rows and columns, the number of rows will increase to accomodate the children.
     */
    /*
     final public void setRows(int rows)
     {
     setProperty(ROWS_KEY, Integer.valueOf(rows));
     }
     */

    final public String getRows()
    {
        return ComponentUtils.resolveString(getProperty(ROWS_KEY));
    }

    final public void setRows(String rows)
    {
        setProperty(ROWS_KEY, rows);
    }

    final public String getColumns()
    {
        return ComponentUtils.resolveString(getProperty(COLUMNS_KEY));
    }

    final public void setColumns(String columns)
    {
        setProperty(COLUMNS_KEY, columns);
    }

    final public int getHeight()
    {
        return ComponentUtils.resolveInteger(getProperty(HEIGHT_KEY));
    }

    final public void setHeight(int heigth)
    {
        setProperty(HEIGHT_KEY, Integer.valueOf(heigth));
    }

    final public int getWidth()
    {
        return ComponentUtils.resolveInteger(getProperty(WIDTH_KEY));
    }

    final public void setWidth(int width)
    {
        setProperty(WIDTH_KEY, Integer.valueOf(width));
    }

    final public int getCellspacing()
    {
        return ComponentUtils.resolveInteger(getProperty(CELLSPACING_KEY));
    }

    final public void setCellspacing(int cellspacing)
    {
        setProperty(CELLSPACING_KEY, Integer.valueOf(cellspacing));
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }

    @Override
    protected FacesBean.Type getBeanType()
    {
        return TYPE;
    }

    /**
     * Construct an instance of the CoreTableFormLayout.
     */
    protected CoreTableFormLayout(String rendererType)
    {
        super(rendererType);
    }

    static
    {
        TYPE.lockAndRegister("org.apache.myfaces.trinidad.Panel",
                "org.apache.myfaces.trinidad.TableLayout");
    }
}
