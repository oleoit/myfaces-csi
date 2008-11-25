package org.apache.myfaces.custom.skin;

import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProvider;

public class TomahawkSkinStyleProvider extends SkinStyleProvider
{
    private static final String TOMAHAWK_SHORT_CLASS_PREFIX = "t";

    /**
     * Creates GenericSkinStyleProvider instance.
     * Only subclasses should call this method.  All other
     * clients should use GenericSkinStyleProviderFactory.
     * @param skin The Skin which defines the
     *   look and feel-specific style information for this
     *   StyleProvider.
     * @param targetDirectoryPath The full file system path
     *   to the directory where generated CSS files will be
     *   stored.
     * @see #GenericSkinStyleProvider
     * @throws IllegalArgumentException This exception is thrown
     *         if no Skin is null, or if either of the
     *         paths are invalid.
     */
    protected TomahawkSkinStyleProvider(Skin skin, String targetDirectoryPath)
            throws IllegalArgumentException
    {
        super(skin, targetDirectoryPath);
    }

    /**
     * This method returns the prefix to be used when generating compressed
     * style class names.
     */
    @Override
    protected String getDefaultShortClassPrefix() {
        return TOMAHAWK_SHORT_CLASS_PREFIX;
    }
}
