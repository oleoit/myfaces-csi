package org.apache.myfaces.trinidadinternal.skin;

import java.util.HashMap;
import java.util.Map;

import org.apache.myfaces.trinidad.logging.SkinLogger;
import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.skin.SkinStyleProvider.ProviderKey;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;

//SKINFIX: this class was created to isolate the SkinStyleProvider
//generation logic from SkinStyleProvider class.  This class has
//instance methods instead of static methods to allow method inheritance.
public class SkinStyleProviderFactory
{
    private static SkinStyleProviderFactory instance;
    private static final SkinLogger _LOG = 
        SkinLogger.createSkinLogger(SkinStyleProviderFactory.class);
    
    // Cache of shared SkinStyleProvider instances
    private static final Map<ProviderKey, StyleProvider> _sSharedProviders = 
        new HashMap<ProviderKey, StyleProvider>(31);
    
    protected SkinStyleProviderFactory()
    {
        //this is a singleton
    }
    
    public static SkinStyleProviderFactory getInstance()
    {
        if(instance == null)
        {
            instance = new SkinStyleProviderFactory();
        }
        
        return instance;
    }    

    public StyleProvider getSkinStyleProvider(Skin skin,
            String targetDirectoryPath) throws IllegalArgumentException
    {

        if (skin == null)
            throw new IllegalArgumentException(_LOG
                    .getMessage("NO_SKIN_SPECIFIED"));

        // If the skin is actually one of our request-specific wrapper
        // skins, rip off the wrapper and look up the StyleProvider
        // for the wrapped skin.  If we don't do this, we end up creating
        // a new StyleProvider for every request.
        if (skin instanceof RequestSkinWrapper)
            skin = ((RequestSkinWrapper) skin).getWrappedSkin();

        // Create the key object that we use to look up our
        // shared SkinStyleProvider instance
        ProviderKey key = new ProviderKey(skin, targetDirectoryPath);

        // Get our cache of existing StyleProviders
        Map<ProviderKey, StyleProvider> providers = _getProviders();

        StyleProvider provider = null;

        synchronized (providers)
        {
            provider = providers.get(key);

            if (provider == null)
            {
                // If we haven't created an instance for this skin/custom style sheet
                // yet, try creating it now.
                //SKINFIX: using a protected instance method to make extensions easier for support modules
                //provider = new SkinStyleProvider(skin,
                //                                 targetDirectoryPath);
                provider = createSkinStyleProvider(skin, targetDirectoryPath);

                // Store the provider in our cache
                providers.put(key, provider);
            }
        }

        return provider;
    }

    /**
     * Returns a Map which hashes ProviderKeys to shared instances
     * of SkinStyleProviders.
     * @return
     */
    protected Map<ProviderKey, StyleProvider> _getProviders()
    {
        // =-=ags For now, we just use a global variable.  But
        //        really, our cache should probably be hanging off
        //        of the ServletContext.
        return _sSharedProviders;
    }
    
    /**
     * This returns a new instance of SkinStyleProvider.
     * @param skin The current selected skin.
     * @param targetDirectoryPath The path where the style cache will be stored.
     * @return a new instance of SkinStyleProvider
     */
    protected SkinStyleProvider createSkinStyleProvider(Skin skin, String targetDirectoryPath)
    {
        return new SkinStyleProvider(skin, targetDirectoryPath);
    }
}
