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
package org.apache.myfaces.trinidadinternal.ui.laf.base.pda;

import java.io.IOException;

import org.apache.myfaces.trinidadinternal.ui.UIXRenderingContext;
import org.apache.myfaces.trinidadinternal.ui.UINode;
import org.apache.myfaces.trinidadinternal.ui.beans.MarlinBean;


/**
 * @version $Name:  $ ($Revision$) $Date$
 * @deprecated This class comes from the old Java 1.2 UIX codebase and should not be used anymore.
 */
@Deprecated
public class GlobalButtonBarRenderer extends org.apache.myfaces.trinidadinternal.ui.laf.base.xhtml.GlobalButtonBarRenderer
{
  @Override
  protected void renderBetweenIndexedChildren(
    UIXRenderingContext context,
    UINode           node
    ) throws IOException
  {
    renderChild(context, _SPACER);
  }

  @Override
  protected void renderDefaultCellAttributes(
    UIXRenderingContext context,
    UINode           child) throws IOException
  {
    renderAttribute(context, "valign", "bottom");
  }

  // space to render between each child
  private static final MarlinBean _SPACER = new MarlinBean(SPACER_NAME);
  static
  {
    _SPACER.setAttributeValue(WIDTH_ATTR, "10");
    _SPACER.setAttributeValue(HEIGHT_ATTR, "1");
  }

}
