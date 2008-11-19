package org.apache.myfaces.custom.skin.component;

import java.io.IOException;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFRenderer;
import org.apache.myfaces.custom.skin.resource.SkinResourceHandler;
import org.apache.myfaces.renderkit.html.util.AddResource;
import org.apache.myfaces.renderkit.html.util.AddResourceFactory;
import org.apache.myfaces.trinidadinternal.style.StyleContext;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;

/**
 * This class is the an implementation of SkinStyleSheetRenderer
 * that takes advantage of ExtensionsFilter for serving resources.
 */
@JSFRenderer(renderKitId = "HTML_BASIC",
        family = "org.apache.myfaces.skins.StyleSheet",
        type = "org.apache.myfaces.skins.StyleSheet") 
public class TomahawkSkinStyleSheetRenderer extends SkinStyleSheetRenderer
{

    /**
     * This method renders the stylesheet using AddResource.
     */
    @Override
    protected void renderStyleSheets(FacesContext context,
            UIComponent component, List<String> uris, StyleProvider provider,
            StyleContext context2) throws IOException
    {
        AddResource addResource = AddResourceFactory.getInstance(context);
        
        for (String uri : uris)
        {
            SkinResourceHandler resourceHandler = new SkinResourceHandler(uri);
            addResource.addStyleSheet(context, AddResource.HEADER_BEGIN, resourceHandler);                        
        }
    }

}
