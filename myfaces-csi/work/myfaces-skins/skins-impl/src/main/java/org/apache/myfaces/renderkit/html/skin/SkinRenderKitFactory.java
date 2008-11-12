package org.apache.myfaces.renderkit.html.skin;

import java.util.Iterator;

import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

public class SkinRenderKitFactory extends RenderKitFactory
{
    private RenderKitFactory wrapped;
    
    public SkinRenderKitFactory(RenderKitFactory factory)
    {
        wrapped = factory;
    }
    
    
    @Override
    public void addRenderKit(String arg0, RenderKit arg1)
    {
        wrapped.addRenderKit(arg0, arg1);   
    }

    @Override
    public RenderKit getRenderKit(FacesContext arg0, String arg1)
    {
        RenderKit renderkit = wrapped.getRenderKit(arg0, arg1);
        return createWrapper(renderkit);
    }

    @Override
    public Iterator<String> getRenderKitIds()
    {
        return wrapped.getRenderKitIds();
    }

    private RenderKit createWrapper(RenderKit renderkit)
    {
        SkinRenderKit skinRenderKit = new SkinRenderKit(renderkit);
        return skinRenderKit;
    }
}
