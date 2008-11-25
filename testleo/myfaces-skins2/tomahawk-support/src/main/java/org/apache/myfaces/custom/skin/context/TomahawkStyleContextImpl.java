package org.apache.myfaces.custom.skin.context;

import org.apache.myfaces.custom.skin.TomahawkSkinStyleProviderFactory;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProviderFactory;

public class TomahawkStyleContextImpl extends StyleContextImpl
{
    public TomahawkStyleContextImpl(SkinRenderingContext arc,
            String generatedFilesPath)
    {
        super(arc, generatedFilesPath);
    }
    
    /**
     * Returns an instance of TomahawkSkinStyleProviderFactory.
     */
    @Override
    protected SkinStyleProviderFactory getSkinStyleProviderFactory()
    {
        return TomahawkSkinStyleProviderFactory.getInstance();
    }
}
