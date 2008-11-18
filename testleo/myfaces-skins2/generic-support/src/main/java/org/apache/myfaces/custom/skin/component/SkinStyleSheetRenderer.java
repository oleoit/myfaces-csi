package org.apache.myfaces.custom.skin.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.myfaces.trinidad.context.SkinRenderingContext;
import org.apache.myfaces.trinidad.skin.Skin;
import org.apache.myfaces.trinidadinternal.renderkit.core.SkinableRenderingContext;
import org.apache.myfaces.trinidadinternal.style.StyleContext;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;

public abstract class SkinStyleSheetRenderer extends Renderer
{
    /**
     * Disables optimizations that are normally performed by the
     * Trinidad Renderers to reduce content size.
     * <p>
     * This Boolean property controls whether or not Trinidad Renderer
     * implementations should attempt to reduce the size of generated
     * content, for example, by compressing style class names.  These
     * optimizations are enabled by default.  In general,
     * users should not need to disable these optimizations.  However,
     * users who want to build custom skins for Trinidad will find this
     * setting essential.  Use Boolean.TRUE to disable compression.
     */
    static public final String DISABLE_CONTENT_COMPRESSION = 
        "org.apache.myfaces.trinidad.DISABLE_CONTENT_COMPRESSION";

    @Override
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException
    {
        SkinRenderingContext arc = SkinRenderingContext.getCurrentInstance();
        
        if (arc == null)
            throw new IllegalStateException(("NO_RENDERINGCONTEXT"));
        
        ResponseWriter writer = context.getResponseWriter();

        StyleContext sContext = ((SkinableRenderingContext) arc).getStyleContext();
        StyleProvider provider = sContext.getStyleProvider();
        if (provider != null)
        {
            List<String> uris = provider.getStyleSheetURIs(sContext);

            // If the requestMap has a skin-id, a skin's stylesheet's id and suppressStylesheet
            // is true, and the skin information matches our current skin, then it is safe
            // to not write out the css. This means that it will be written out by the external
            // source, like the portal container.
            boolean suppressStylesheet = _isSuppressStylesheet(context, arc);
            if (!suppressStylesheet)
            {
                if (uris != null && !uris.isEmpty())
                {
                    renderStyleSheets(context, component, uris, provider, sContext);
                }
                else
                {
                    if (arc.getSkin() == null)
                        writer
                                .writeComment("ERROR: Could not create stylesheet, because "
                                        + "no skin is available");
                    else
                        writer
                                .writeComment("ERROR: could not create stylesheet for "
                                        + arc.getSkin().getStyleSheetName());
                }
            }

            // Hand the Faces-major renderers the style Map for compressing.
            // Oddly enough, this code has to be after provider.getStyleSheetURI(),
            // because that call boostraps up the style provider in general.
            if (arc instanceof SkinableRenderingContext)
            {
                Map<String, String> shortStyles = arc.getSkin()
                        .getStyleClassMap(arc);
                ((SkinableRenderingContext) arc).setStyleMap(shortStyles);
            }
        }
    }
    
    protected void renderEncodedResourceURI(FacesContext context, String name,
            Object value) throws IOException
    {
        if (value != null)
        {
            value = context.getExternalContext().encodeResourceURL(
                    value.toString());
            context.getResponseWriter().writeURIAttribute(name, value, null);
        }
    }    

    // returns true if we want to suppress the stylesheet.
    // It checks for suppressStylesheet flag on the request map and
    // for the skin-id on the request map to exist identically on the server.
    //
    // This is usually called in a portal environment when we want to suppress the
    // producer (portlet)'s stylesheet and use the consumer (portal container)'s
    // instead for performance enhancements.
    private boolean _isSuppressStylesheet(FacesContext context,
            SkinRenderingContext arc)
    {

        Map<String, Object> requestMap = context.getExternalContext()
                .getRequestMap();

        boolean suppressStylesheet = "true".equals(requestMap
                .get(_SUPPRESS_STYLESHEET_ID_PARAM));
        if (suppressStylesheet)
        {
            // getRequestMapSkin --
            // See if a skin-id is requested on the requestMap. If so, then see if the Skin
            // with that id exists on the server, and if it does, and if it is an exact match
            // (styleSheetDocumentIds match), then it returns the Skin. Otherwise, it returns null
            Skin requestMapSkin = ((SkinableRenderingContext) arc)
                    .getRequestMapSkin();
            return (requestMapSkin != null) ? true : false;
        }
        return false;
    }

    /**
     * This method does the actual rendering of the stylesheets.  Override
     * this to implement your own logic for rendering the stylesheets.
     */
    abstract protected void renderStyleSheets(
            FacesContext context, UIComponent component, List<String> uris,
            StyleProvider provider, StyleContext sContext)
        throws IOException;
    
    static private final String _SUPPRESS_STYLESHEET_ID_PARAM =
      "org.apache.myfaces.trinidad.skin.suppressStylesheet";

}
