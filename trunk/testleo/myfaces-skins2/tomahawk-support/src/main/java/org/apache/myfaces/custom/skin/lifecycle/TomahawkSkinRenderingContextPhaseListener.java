package org.apache.myfaces.custom.skin.lifecycle;

import javax.faces.event.PhaseEvent;

import org.apache.myfaces.custom.skin.context.TomahawkSkinRenderingContextImpl;

public class TomahawkSkinRenderingContextPhaseListener
    extends SkinRenderingContextPhaseListener
{
    private static final long serialVersionUID = -5952748638699662210L;

    @Override
    public void beforePhase(PhaseEvent event)
    {
        new TomahawkSkinRenderingContextImpl(); 
    }
}
