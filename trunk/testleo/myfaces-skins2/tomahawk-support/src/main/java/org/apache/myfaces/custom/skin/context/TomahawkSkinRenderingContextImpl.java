package org.apache.myfaces.custom.skin.context;

import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidadinternal.style.StyleContext;

public class TomahawkSkinRenderingContextImpl extends SkinRenderingContextImpl
{
    private StyleContext _styleContext;
    
    /**
     * Get an interface that can be used for style lookups and generation.
     */
    @Override
    public StyleContext getStyleContext()
    {
        if (_styleContext == null)
        {
            FacesContext fContext = FacesContext.getCurrentInstance();
            _styleContext = new TomahawkStyleContextImpl(this, getTemporaryDirectory(fContext));
        }

        return _styleContext;
    }
}
