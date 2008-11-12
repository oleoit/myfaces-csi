package org.apache.myfaces.renderkit.html.skin;

import java.io.OutputStream;
import java.io.Writer;

import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;
import javax.faces.render.ResponseStateManager;

public class SkinRenderKit extends RenderKit
{
    private RenderKit wrapped;
    
    public SkinRenderKit(RenderKit renderkit)
    {
        wrapped = renderkit;
    }
    
    @Override
    public void addRenderer(String family, String rendererType,
            Renderer renderer)
    {
        wrapped.addRenderer(family, rendererType, renderer);
    }

    @Override
    public ResponseStream createResponseStream(OutputStream out)
    {
        return wrapped.createResponseStream(out);
    }

    @Override
    public ResponseWriter createResponseWriter(Writer writer,
            String contentTypeList, String characterEncoding)
    {
        return wrapped.createResponseWriter(writer, contentTypeList, characterEncoding);
    }

    @Override
    public Renderer getRenderer(String family, String rendererType)
    {
        Renderer renderer = wrapped.getRenderer(family, rendererType);
        
        if(renderer instanceof Skinnable)
        {
            return createWrapper(renderer);
        }
        
        return renderer;
    }

    @Override
    public ResponseStateManager getResponseStateManager()
    {
        return wrapped.getResponseStateManager();
    }
    
    
    private Renderer createWrapper(Renderer renderer)
    {
        return new SkinRendererWrapper(renderer);
    }

}
