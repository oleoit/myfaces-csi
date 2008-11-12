package org.apache.myfaces.renderkit.html.skin;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.Renderer;

public class SkinRendererWrapper extends Renderer
{
    private Renderer wrapped;
    
    public SkinRendererWrapper(Renderer renderer)
    {
        wrapped = renderer;
    }
    
    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException
    {
        if(wrapped instanceof Skinnable)
        {
            Skinnable skinnable = (Skinnable)wrapped;
            skinnable.applySkin(component);
        }
        
        wrapped.encodeBegin(context, component);
    }
    
    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        wrapped.decode(context, component);
    }
    
    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException
    {
        wrapped.encodeChildren(context, component);
    }
    
    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        wrapped.encodeEnd(context, component);
    }
    
    @Override
    public String convertClientId(FacesContext context, String clientId)
    {
        return wrapped.convertClientId(context, clientId);
    }
    
    @Override
    public boolean getRendersChildren()
    {
        return wrapped.getRendersChildren();
    }
    
    @Override
    public Object getConvertedValue(FacesContext context,
            UIComponent component, Object submittedValue)
            throws ConverterException
    {
        return wrapped.getConvertedValue(context, component, submittedValue);
    }
}
