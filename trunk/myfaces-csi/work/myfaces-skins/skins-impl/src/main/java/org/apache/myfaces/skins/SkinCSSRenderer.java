package org.apache.myfaces.skins;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

/**
 * @JSFRenderer
 *   renderKitId = "HTML_BASIC" 
 *   family = "javax.faces.Output"
 *   type = "org.apache.myfaces.skins.SkinCSSRenderer"
 */
public class SkinCSSRenderer extends Renderer
{
    private static final String CSS_FILENAME_PARAM = "org.apache.myfaces.renderkit.html.skin.CSS_URIs";
    private static final String CSS_SKIN_FILTER_PATH = "/myfaces/skins/";
    
    public void encodeEnd(FacesContext facesContext, UIComponent uiComponent) throws IOException
    {
        super.encodeEnd(facesContext, uiComponent);

        ResponseWriter writer = facesContext.getResponseWriter();
        
        List<String> uris = (List<String>)facesContext.getExternalContext().getRequestMap().get(CSS_FILENAME_PARAM);
        
        String path = facesContext.getExternalContext().getRequestContextPath() + CSS_SKIN_FILTER_PATH;
        
        StringBuilder builder = new StringBuilder();
        builder.append("<script type=\"text/javascript\">\n");        
        builder.append("    var cssNode;\n");
        
        for(Iterator<String> iter = uris.iterator(); iter.hasNext();)
        {
            String uri = iter.next();
            
            builder.append("    cssNode = document.createElement('link');\n");
            builder.append("    cssNode.type = 'text/css';\n");
            builder.append("    cssNode.rel = 'stylesheet';\n");
            builder.append("    cssNode.href = '").append(path).append(uri).append("';\n");
            builder.append("    document.getElementsByTagName(\"head\")[0].appendChild(cssNode);\n");
            builder.append("\n");
        }
        builder.append("</script>\n");
        writer.write(builder.toString());
    }
}
