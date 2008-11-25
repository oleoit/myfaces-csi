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

import org.apache.myfaces.trinidad.logging.SkinLogger;
import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.style.StyleContext;
import org.apache.myfaces.trinidadinternal.style.cache.FileSystemStyleCache;
import org.apache.myfaces.trinidadinternal.style.xml.StyleSheetDocumentUtils;
import org.apache.myfaces.trinidadinternal.style.xml.parse.StyleSheetDocument;


/**
 * An extension of the FileSystemStyleCache which defers to
 * a Skin for loading the StyleSheetDocument.
 *
 * @version $Name:  $ ($Revision$) $Date$
 */
public class SkinStyleProvider extends FileSystemStyleCache
{
  /**
   * Creates SkinStyleProvider instance.
   * Only subclasses should call this method.  All other
   * clients should use getSkinStyleProvider().
   * @param skin The Skin which defines the
   *   look and feel-specific style information for this
   *   StyleProvider.
   * @param targetDirectoryPath The full file system path
   *   to the directory where generated CSS files will be
   *   stored.
   * @see #SkinStyleProvider
   * @throws IllegalArgumentException This exception is thrown
   *         if no Skin is null, or if either of the
   *         paths are invalid.
   */
  protected SkinStyleProvider(
    Skin skin,
    String targetDirectoryPath
    ) throws IllegalArgumentException
  {
    super(null, targetDirectoryPath);

    if (skin == null)
      throw new IllegalArgumentException(_LOG.getMessage(
        "NO_SKIN_SPECIFIED"));

    _skin = skin;
  }

  /**
   * Override of FileSystemStyleCache.createStyleSheetDocument().
   * Merges the Skin's styles with custom styles to
   * produce a single StyleSheetDocument
   */
  @Override
  protected StyleSheetDocument createStyleSheetDocument(
    StyleContext context
    )
  {
    // First, get the StyleSheetDocument for the custom style
    // sheet from the FileSystemStyleCache.
    StyleSheetDocument customDocument = super.createStyleSheetDocument(
                                                context);

    // Now, get the Skin's StyleSheetDocument
    StyleSheetDocument skinDocument = null;

    // Synchronize access to _skinDocument
    synchronized (this)
    {
      // gets the skin's StyleSheetDocument (it creates it if needed)
      skinDocument = _skinDocument =
        ((DocumentProviderSkin) _skin).getStyleSheetDocument(context);
    }


    // Merge the two StyleSheetDocuments
    return StyleSheetDocumentUtils.mergeStyleSheetDocuments(skinDocument,
                                                            customDocument);
  }

  /**
   * Override of FileSystemStyleCache.hasSourceDocumentChanged()
   * which checks for changes to the Skin's style sheet.
   */
  @Override
  protected boolean hasSourceDocumentChanged(StyleContext context)
  {
    if (super.hasSourceDocumentChanged(context))
      return true;

    // Just check to see whether the Skin has a new
    // StyleSheetDocument.

    // Synchronize access to _skinDocument
    synchronized (this)
    {
      return (_skinDocument !=
              ((DocumentProviderSkin) _skin).getStyleSheetDocument(context));
    }
  }

  /**
   * Override of FileSystemStyleCache.getTargetStyleSheetName().
   */
  @Override
  protected String getTargetStyleSheetName(
    StyleContext       context,
    StyleSheetDocument document
    )
  {
    // Get the base name from the FileSystemStyleCache.
    String name = super.getTargetStyleSheetName(context, document);

    // Use the LAF's id as a prefix
    String id = _skin.getId();
    if (id != null)
    {
      StringBuffer buffer = new StringBuffer(id.length() + name.length() + 1);

      // We know that some LAF ids contain the '.' character.  Replace
      // this with '-' to make the file name look nicer.
      buffer.append(id.replace('.', '-'));
      buffer.append('-');
      buffer.append(name);

      return buffer.toString();
    }

    return name;
  }

  // Key that we use to retrieve a shared SkinStyleProvider
  // instance
  //SKINFIX: changed access modifier from private to protected
  protected static class ProviderKey
  {
    public ProviderKey(
      Skin skin,
      String targetDirectoryPath
      )
    {
      _skin = skin;
      _targetDirectoryPath = targetDirectoryPath;
    }

    // Test for equality
    @Override
    public boolean equals(Object o)
    {
      if (o == this)
        return true;

      if (!(o instanceof ProviderKey))
        return false;

      ProviderKey key = (ProviderKey)o;

      return (_equals(_skin, key._skin)                                   &&
              _equals(_targetDirectoryPath, key._targetDirectoryPath));
    }

    // Produce the hash code
    @Override
    public int hashCode()
    {
      int hashCode = _skin.hashCode();

      if (_targetDirectoryPath != null)
        hashCode ^= _targetDirectoryPath.hashCode();

      return hashCode;
    }

    // Tests two objects for equality, taking possible nulls
    // into account
    private boolean _equals(Object o1, Object o2)
    {
      if (o1 == null)
        return (o1 == o2);

      return o1.equals(o2);
    }

    private Skin _skin;
    private String      _targetDirectoryPath;
  }

  // The Skin which provides styles
  private Skin _skin;

  // The Skin-specific StyleSheetDocument
  private StyleSheetDocument _skinDocument;

  private static final SkinLogger _LOG = SkinLogger.createSkinLogger(
    SkinStyleProvider.class);
}
