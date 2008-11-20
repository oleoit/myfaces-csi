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
package org.apache.myfaces.custom.skin.component;

import javax.faces.component.UIComponentBase;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;

/**
 * The styleSheet component generates the style
 * sheet link reference to a generated Apache Trinidad style sheet.
 * This is automatically included for you if you use the Trinidad
 * HtmlHead component.
 *
 */
@JSFComponent
(name = "ms:skinStyleSheet",
 tagClass = "org.apache.myfaces.custom.skin.component.SkinStyleSheetTag")
public class HtmlSkinStyleSheet extends UIComponentBase
{
    static public final String COMPONENT_FAMILY = "org.apache.myfaces.skins.StyleSheet";
    static public final String COMPONENT_TYPE = "org.apache.myfaces.skins.StyleSheet";
    static private final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.skins.StyleSheet"; 

    /**
     * Construct an instance of the CoreStyleSheet.
     */
    public HtmlSkinStyleSheet()
    {
        setRendererType(DEFAULT_RENDERER_TYPE);
    }

    @Override
    public String getFamily()
    {
        return COMPONENT_FAMILY;
    }
    
}
