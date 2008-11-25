package org.apache.myfaces.custom.skin.context;

import org.apache.myfaces.custom.skin.GenericSkinStyleProviderFactory;
import org.apache.myfaces.trinidad.context.SkinRenderingContext;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProviderFactory;

public class GenericStyleContextImpl extends StyleContextImpl
{    
    public GenericStyleContextImpl(SkinRenderingContext arc,
            String generatedFilesPath)
    {
        super(arc, generatedFilesPath);
    }
    
    /**
     * Returns an instance of GenericSkinStyleProviderFactory.
     */
    @Override
    protected SkinStyleProviderFactory getSkinStyleProviderFactory()
    {
        return GenericSkinStyleProviderFactory.getInstance();
    }
}
