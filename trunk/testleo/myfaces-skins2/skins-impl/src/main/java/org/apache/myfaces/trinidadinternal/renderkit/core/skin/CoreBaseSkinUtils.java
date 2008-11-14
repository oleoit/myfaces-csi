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
package org.apache.myfaces.trinidadinternal.renderkit.core.skin;

import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;
import org.apache.myfaces.trinidad.skin.Skin;
//import org.apache.myfaces.trinidadinternal.ui.UIXRenderingContext;
import org.apache.myfaces.trinidad.skin.Icon;

/**
 * Utilities for the Core Skins.
 *
 * SKINFIX: Add CoreBaseSkinUtils and make original
 * CoreSkinUtils extends from this class
 * @version $Name:  $ ($Revision$) $Date$
 */
public class CoreBaseSkinUtils 
{

  // Renders an icon as a background
  // This is more optimized than the method that takes a RenderingContext
  public static void __renderBackgroundIcon(
    FacesContext        fContext, 
    SkinRenderingContext arc, 
    Icon                icon)
    throws IOException
  {
    if (icon != null)
    {

      // Make sure that our icon has a URI
      Object uri = icon.getImageURI(fContext, arc);
      if (uri == null)
        return;

      ResponseWriter writer = fContext.getResponseWriter();

      writer.writeAttribute("background", uri, null);
    }
  }
  
  /**
   * Registers a set of org.apache.myfaces.trinidadinternal.skin.icon.Icon objects
   * on the specified Skin. The icons
   * array contains alternating pairs of icon name and Icon objects.
   */
  public static void registerIcons(Skin skin, Object[] icons)
  {
    for (int i = 0; i < icons.length; i += 2)
    {
      String iconName = (String) icons[i];
      Icon icon = (Icon) icons[i + 1];

      skin.registerIcon(iconName, icon);
    }
  }
}
