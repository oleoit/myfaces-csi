package org.apache.myfaces.custom.skin.lifecycle;

import javax.faces.event.PhaseEvent;

import org.apache.myfaces.custom.skin.context.GenericSkinRenderingContextImpl;

public class GenericSkinRenderingContextPhaseListener 
    extends SkinRenderingContextPhaseListener 
{

    private static final long serialVersionUID = -4726557910738568404L;

    @Override
    public void beforePhase(PhaseEvent event)
    {
        new GenericSkinRenderingContextImpl(); 
    }
}
