package org.apache.myfaces.custom.skin;

import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProvider;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProviderFactory;

public class TomahawkSkinStyleProviderFactory extends SkinStyleProviderFactory
{
    private static TomahawkSkinStyleProviderFactory instance;
    
    protected TomahawkSkinStyleProviderFactory()
    {
        //this is a singleton
    }
    
    /**
     * This creates a new instance of GenericSkinStyleProviderFactory.
     * @return a new instance of GenericSkinStyleProviderFactory
     */
    public static TomahawkSkinStyleProviderFactory getInstance()
    {
        if(instance == null)
        {
            instance = new TomahawkSkinStyleProviderFactory();
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
        return new TomahawkSkinStyleProvider(skin, targetDirectoryPath);
    }
}
