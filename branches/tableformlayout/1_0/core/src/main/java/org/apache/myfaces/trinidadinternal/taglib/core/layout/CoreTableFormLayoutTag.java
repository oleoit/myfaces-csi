package org.apache.myfaces.trinidadinternal.taglib.core.layout;

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

import org.apache.myfaces.trinidad.bean.FacesBean;
import org.apache.myfaces.trinidad.component.core.layout.CoreTableFormLayout;
import org.apache.myfaces.trinidadinternal.taglib.UIXPanelTag;

public class CoreTableFormLayoutTag extends UIXPanelTag
{

    /**
     * Construct an instance of the CoreTableFormLayoutTag.
     */
    public CoreTableFormLayoutTag()
    {
    }

    @Override
    public String getComponentType()
    {
        return "org.apache.myfaces.trinidad.CoreTableFormLayout";
    }

    @Override
    public String getRendererType()
    {
        return "org.apache.myfaces.trinidad.TableLayout";
    }

    private String _inlineStyle;

    public void setInlineStyle(String inlineStyle)
    {
        _inlineStyle = inlineStyle;
    }

    private String _styleClass;

    public void setStyleClass(String styleClass)
    {
        _styleClass = styleClass;
    }

    private String _shortDesc;

    public void setShortDesc(String shortDesc)
    {
        _shortDesc = shortDesc;
    }

    private String _partialTriggers;

    public void setPartialTriggers(String partialTriggers)
    {
        _partialTriggers = partialTriggers;
    }

    private String _onclick;

    public void setOnclick(String onclick)
    {
        _onclick = onclick;
    }

    private String _ondblclick;

    public void setOndblclick(String ondblclick)
    {
        _ondblclick = ondblclick;
    }

    private String _onmousedown;

    public void setOnmousedown(String onmousedown)
    {
        _onmousedown = onmousedown;
    }

    private String _onmouseup;

    public void setOnmouseup(String onmouseup)
    {
        _onmouseup = onmouseup;
    }

    private String _onmouseover;

    public void setOnmouseover(String onmouseover)
    {
        _onmouseover = onmouseover;
    }

    private String _onmousemove;

    public void setOnmousemove(String onmousemove)
    {
        _onmousemove = onmousemove;
    }

    private String _onmouseout;

    public void setOnmouseout(String onmouseout)
    {
        _onmouseout = onmouseout;
    }

    private String _onkeypress;

    public void setOnkeypress(String onkeypress)
    {
        _onkeypress = onkeypress;
    }

    private String _onkeydown;

    public void setOnkeydown(String onkeydown)
    {
        _onkeydown = onkeydown;
    }

    private String _onkeyup;

    public void setOnkeyup(String onkeyup)
    {
        _onkeyup = onkeyup;
    }

    /*
     private String _maxColumns;
     public void setMaxColumns(String maxColumns)
     {
     _maxColumns = maxColumns;
     }

     private String _rows;
     public void setRows(String rows)
     {
     _rows = rows;
     }
     */
    //Custom properties	
    private String _rows;

    public void setRows(String rows)
    {
        _rows = rows;
    }

    private String _columns;

    public void setColumns(String columns)
    {
        _columns = columns;
    }

    private String _height;

    public void setHeight(String height)
    {
        _height = height;
    }

    private String _width;

    public void setWidth(String width)
    {
        _width = width;
    }

    private String _cellspacing;

    public void setCellspacing(String cellspacing)
    {
        _cellspacing = cellspacing;
    }

    @Override
    protected void setProperties(FacesBean bean)
    {
        super.setProperties(bean);
        setProperty(bean, CoreTableFormLayout.INLINE_STYLE_KEY, _inlineStyle);
        setProperty(bean, CoreTableFormLayout.STYLE_CLASS_KEY, _styleClass);
        setProperty(bean, CoreTableFormLayout.SHORT_DESC_KEY, _shortDesc);
        setStringArrayProperty(bean, CoreTableFormLayout.PARTIAL_TRIGGERS_KEY,
                _partialTriggers);
        setProperty(bean, CoreTableFormLayout.ONCLICK_KEY, _onclick);
        setProperty(bean, CoreTableFormLayout.ONDBLCLICK_KEY, _ondblclick);
        setProperty(bean, CoreTableFormLayout.ONMOUSEDOWN_KEY, _onmousedown);
        setProperty(bean, CoreTableFormLayout.ONMOUSEUP_KEY, _onmouseup);
        setProperty(bean, CoreTableFormLayout.ONMOUSEOVER_KEY, _onmouseover);
        setProperty(bean, CoreTableFormLayout.ONMOUSEMOVE_KEY, _onmousemove);
        setProperty(bean, CoreTableFormLayout.ONMOUSEOUT_KEY, _onmouseout);
        setProperty(bean, CoreTableFormLayout.ONKEYPRESS_KEY, _onkeypress);
        setProperty(bean, CoreTableFormLayout.ONKEYDOWN_KEY, _onkeydown);
        setProperty(bean, CoreTableFormLayout.ONKEYUP_KEY, _onkeyup);
        //setIntegerProperty(bean, CoreTableFormLayout.MAX_COLUMNS_KEY, _maxColumns);
        //setIntegerProperty(bean, CoreTableFormLayout.ROWS_KEY, _rows);
        setProperty(bean, CoreTableFormLayout.ROWS_KEY, _rows);
        setProperty(bean, CoreTableFormLayout.COLUMNS_KEY, _columns);
        setProperty(bean, CoreTableFormLayout.HEIGHT_KEY, _height);
        setProperty(bean, CoreTableFormLayout.WIDTH_KEY, _width);
        setProperty(bean, CoreTableFormLayout.CELLSPACING_KEY, _cellspacing);
    }

    @Override
    public void release()
    {
        super.release();
        _inlineStyle = null;
        _styleClass = null;
        _shortDesc = null;
        _partialTriggers = null;
        _onclick = null;
        _ondblclick = null;
        _onmousedown = null;
        _onmouseup = null;
        _onmouseover = null;
        _onmousemove = null;
        _onmouseout = null;
        _onkeypress = null;
        _onkeydown = null;
        _onkeyup = null;
        //_maxColumns = null;
        //_rows = null;
        _rows = null;
        _columns = null;
        _height = null;
        _width = null;
        _cellspacing = null;
    }
}
