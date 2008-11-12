package org.apache.myfaces.trinidad.context;

import org.apache.myfaces.trinidad.skin.Skin;

public abstract class RenderingContext
{
    public RenderingContext()
    {
      attach();
    }
    
    /**
     * Get the Skin.  Icons, properties, etc. should never be retrieved directly
     * from the skin, but always through the RenderingContext so they
     * can be properly transformed.
     */
    abstract public Skin getSkin();
    abstract public LocaleContext getLocaleContext();
    abstract public Agent getAgent();
    abstract public AccessibilityProfile getAccessibilityProfile();
    abstract public String getOutputMode();
    
    /**
     * Retrieves the RenderingContext active for the current thread.
     */
    static public RenderingContext getCurrentInstance()
    {
      return _CURRENT_CONTEXT.get();
    }
    
    static private final ThreadLocal<RenderingContext> _CURRENT_CONTEXT = 
        new ThreadLocal<RenderingContext>();
    
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
//        _LOG.warning("TRYING_ATTACH_RENDERERINGCONTEXT");
      }

      _CURRENT_CONTEXT.set(this);
    }
}
