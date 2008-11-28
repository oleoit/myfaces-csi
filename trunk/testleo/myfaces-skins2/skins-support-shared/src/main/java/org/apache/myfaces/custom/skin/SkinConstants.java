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
package org.apache.myfaces.custom.skin;

public class SkinConstants
{

    public final static String DEFAULT_NAMESPACE = "af|";
    public final static String STYLE_CLASS_SUFFIX = "::style";
    public final static String DISABLED_CLASS_SUFFIX = "::disabled";

    /**
     * Disables optimizations that are normally performed by the
     * Skin Renderers to reduce content size.
     * <p>
     * This Boolean property controls whether or not Skin Renderer
     * implementations should attempt to reduce the size of generated
     * content, for example, by compressing style class names.  These
     * optimizations are enabled by default.  In general,
     * users should not need to disable these optimizations.  However,
     * users who want to build custom skins for MyFaces will find this
     * setting essential.  Use Boolean.TRUE to disable compression.
     */
    static public final String DISABLE_CONTENT_COMPRESSION = 
        "org.apache.myfaces.trinidad.DISABLE_CONTENT_COMPRESSION";
}
