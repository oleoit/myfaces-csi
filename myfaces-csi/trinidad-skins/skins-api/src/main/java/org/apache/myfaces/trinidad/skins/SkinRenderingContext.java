package org.apache.myfaces.trinidad.skins;

import java.util.Map;
import java.util.MissingResourceException;

import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.LocaleContext;
import org.apache.myfaces.trinidad.logging.TrinidadLogger;
import org.apache.myfaces.trinidad.skin.Icon;
import org.apache.myfaces.trinidad.skin.Skin;

abstract public class SkinRenderingContext
{
    /**
     * Retrieves the RenderingContext active for the current thread.
     */
    static public SkinRenderingContext getCurrentInstance()
    {
      return _CURRENT_CONTEXT.get();
    }
    
    public SkinRenderingContext()
    {
      attach();
    }    
    
    abstract public Agent getAgent();    
    abstract public LocaleContext getLocaleContext();    
    //
    // Skin methods.
    //

    /**
     * Get the Skin.  Icons, properties, etc. should never be retrieved directly
     * from the skin, but always through the RenderingContext so they
     * can be properly transformed.
     */
    abstract public Skin getSkin();
        
    public String getTranslatedString(String key)
    {
      if (key == null)
        return null;

      try
      {
        return getSkin().getTranslatedString(getLocaleContext(), key);
      }
      catch (MissingResourceException mre)
      {
        // Instead of halting execution, return "???<key>???",
        // just like JSF and JSTL will do, and log a severe error
        _LOG.severe("CANNOT_GET_RESOURCE_KEY", new String[]{key, getSkin().getId()});
        return "???" + key + "???";
      }
    }

    abstract public Icon getIcon(String iconName);


    abstract public String getStyleClass(String styleClass);
    abstract public void   setSkinResourceKeyMap(Map<String, String> mapping);
    abstract public Map<String, String> getSkinResourceKeyMap();
    abstract public boolean isRightToLeft();
    abstract public String getOutputMode();
    
    public void release()
    {
      Object o = _CURRENT_CONTEXT.get();
      // Clean up first...
      _CURRENT_CONTEXT.remove();

      // Then see if there's a problem, and scream if there is one
      if (o == null)
        throw new IllegalStateException(_LOG.getMessage(
          "RENDERINGCONTEXT_ALREADY_RELEASED_OR_NEVER_ATTACHED"));
      if (o != this)
        throw new IllegalStateException(_LOG.getMessage(
          "TRY_RELEASING_DIFFERENT_RENDERINGCONTEXT"));
    }    
    
    /**
     * Attaches an RenderingContext to the current thread.  This method is
     * protected, and therefore can only be called by an RenderingContext
     * object itself.
     */
    protected void attach()
    {
      Object o = _CURRENT_CONTEXT.get();
      // We want to catch two different problems:
      // (1) A failure to call release()
      // (2) An attempt to attach an instance when the thread already has one
      // For #1, anything more than a warning is dangerous, because throwing
      // an exception would permanently make the thread unusable.
      // For #2, I'd like to throw an exception, but I can't distinguish
      // this scenario from #1.
      if (o != null)
      {
        _LOG.warning("TRYING_ATTACH_RENDERERINGCONTEXT");
      }

      _CURRENT_CONTEXT.set(this);
    }
    
    
    static private final ThreadLocal<SkinRenderingContext> _CURRENT_CONTEXT = 
        new ThreadLocal<SkinRenderingContext>();
    
    static private final TrinidadLogger _LOG =
        TrinidadLogger.createTrinidadLogger(SkinRenderingContext.class);
    
}
