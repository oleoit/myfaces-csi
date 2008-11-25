package org.apache.myfaces.custom.skin;

import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProvider;

public class GenericSkinStyleProvider extends SkinStyleProvider
{
    private static final String GENERIC_SHORT_CLASS_PREFIX = "g";

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
    protected GenericSkinStyleProvider(Skin skin, String targetDirectoryPath)
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
        return GENERIC_SHORT_CLASS_PREFIX;
    }
}
