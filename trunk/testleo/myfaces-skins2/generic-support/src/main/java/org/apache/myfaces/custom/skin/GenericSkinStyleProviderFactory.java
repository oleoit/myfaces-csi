package org.apache.myfaces.custom.skin;

import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProvider;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProviderFactory;

public class GenericSkinStyleProviderFactory extends SkinStyleProviderFactory
{
    private static GenericSkinStyleProviderFactory instance;
    
    protected GenericSkinStyleProviderFactory()
    {
        //this is a singleton
    }
    
    /**
     * This creates a new instance of GenericSkinStyleProviderFactory.
     * @return a new instance of GenericSkinStyleProviderFactory
     */
    public static GenericSkinStyleProviderFactory getInstance()
    {
        if(instance == null)
        {
            instance = new GenericSkinStyleProviderFactory();
        }
        
        return instance;
    }
    
    /**
     * This method creates a new instance of GenericSkinStyleProvider.
     */
    @Override
    protected SkinStyleProvider createSkinStyleProvider(Skin skin,
            String targetDirectoryPath)
    {
        return new GenericSkinStyleProvider(skin, targetDirectoryPath);
    }
}
