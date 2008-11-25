package org.apache.myfaces.trinidadinternal.share.config;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

public class SkinConfiguration
{
    private static final String STYLES_CACHE_DIR_PARAM = "org.apache.myfaces.skins.STYLES_CACHE_DIR";
    private static final String SHORT_CLASS_PREFIX_PARAM = "org.apache.myfaces.skins.SHORT_CLASS_PREFIX";
    private static final String DEFAULT_CACHE_DIR = "/adf/styles/cache/";
    
    private SkinConfiguration()
    {
        //no need to instantiate
    }
    
    public static String getStylesCacheDir(FacesContext context)
    {
        ExternalContext extCtx = context.getExternalContext();
        if(extCtx != null)
        {
            String stylesCacheDir = extCtx.getInitParameter(STYLES_CACHE_DIR_PARAM);
            if(stylesCacheDir != null && stylesCacheDir.length() > 0)
            {
                return formatStylesCacheDir(stylesCacheDir);
            }
            
        }
        return DEFAULT_CACHE_DIR;
    }
    
    public static String getStylesCacheDir(ServletContext context)
    {        
        String stylesCacheDir = context.getInitParameter(STYLES_CACHE_DIR_PARAM);
        if(stylesCacheDir != null && stylesCacheDir.length() > 0)
        {
            return formatStylesCacheDir(stylesCacheDir);
        }
        
        return DEFAULT_CACHE_DIR;
    }    
    
    private static String formatStylesCacheDir(String dir)
    {
        if(dir.charAt(0) != '/' && dir.charAt(0) != '\\')
        {
            dir = "/" + dir;
        }
        
        if(dir.charAt(dir.length()-1) != '/' 
            || dir.charAt(dir.length()-1) != '\\')
        {
            dir += "/";
        }
        
        return dir;
    }
    
    public static String getShortClassPrefix(FacesContext context)
    {
        ExternalContext extCtx = context.getExternalContext();
        if(extCtx != null)
        {
            String shortClassPrefix = extCtx.getInitParameter(SHORT_CLASS_PREFIX_PARAM);
            if(shortClassPrefix != null && shortClassPrefix.length() > 0)
            {
                return shortClassPrefix;
            }
        }
        return null;
    }
}
