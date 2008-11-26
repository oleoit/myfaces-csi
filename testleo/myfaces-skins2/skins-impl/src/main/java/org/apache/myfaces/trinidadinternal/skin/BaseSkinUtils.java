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
package org.apache.myfaces.trinidadinternal.skin;

import java.util.Stack;

import org.apache.myfaces.trinidad.logging.SkinLogger;
import org.apache.myfaces.trinidad.skin.Icon;
import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.skin.icon.ReferenceIcon;

/**
 * Utility functions for creating Skin objects and SkinExtension objects 
 * from the trinidad-skins.xml file and
 * adding them to the SkinFactory. It also adds SkinAdditions to the Skin objects.
 * @version $Name:  $ ($Revision$) $Date$
 */
public class BaseSkinUtils
{
  
  /**
   * Returns the actual Icon referenced by the ReferenceIcon.
   * @param skin the Skin to use when resolving the ReferenceIcon
   * @param refIcon a ReferenceIcon instance
   * @return icon which is resolved. i.e., it is not a ReferenceIcon.
   */
   static public Icon resolveReferenceIcon(
     Skin          skin,
     ReferenceIcon refIcon)
   {
     return _resolveReferenceIcon(skin, refIcon, null);
   }

  /**
   * Helper for resolveReferenceIcon which uses a Stack of icon names
   * to detect circular dependencies.
   *
   * @param skin the Skin to use when resolving the ReferenceIcon
   * @param refIcon a ReferenceIcon instance
   * @param referencedIconStack  The stack of reference icon names which have
   *          already been visited.  Used to detect circular dependencies.
   * @return icon which is resolved. i.e., it is not a ReferenceIcon.
   */
  static private Icon _resolveReferenceIcon(
    Skin          skin,
    ReferenceIcon refIcon,
    Stack<String> referencedIconStack)
  {
    String refName = refIcon.getName();

    // make sure we don't have a circular dependency
    if ((referencedIconStack != null) && referencedIconStack.contains(refName))
    {
      if (_LOG.isWarning())
        _LOG.warning("SKIN_CIRCULAR_INCLUDE_ERROR", refName);
      return null;
    }

    if (referencedIconStack == null)
    {
      referencedIconStack = new Stack<String>();
    }

    referencedIconStack.push(refName);

    Icon icon = skin.getIcon(refName, false);

    if ((icon instanceof ReferenceIcon) && (icon != null))
    {

      return _resolveReferenceIcon(skin,
                                  (ReferenceIcon)icon,
                                  referencedIconStack);

    }

    return icon;
  }

  static private final SkinLogger _LOG = SkinLogger.createSkinLogger(BaseSkinUtils.class);

}
