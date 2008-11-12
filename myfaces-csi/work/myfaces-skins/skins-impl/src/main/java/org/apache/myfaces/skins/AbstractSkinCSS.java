package org.apache.myfaces.skins;

import javax.faces.component.UIOutput;

/**
 * @JSFComponent
 *   name = "skins:skinCss"
 *   class = "org.apache.myfaces.skins.SkinsCSS"
 *   tagClass = "org.apache.myfaces.skins.SkinsCSSTag"
 */
public abstract class AbstractSkinCSS extends UIOutput
{
    public static final String COMPONENT_TYPE = "org.apache.myfaces.skins.SkinCSS";
    public static final String DEFAULT_RENDERER_TYPE = "org.apache.myfaces.skins.SkinCSSRenderer";
    public static final String COMPONENT_FAMILY = "javax.faces.Output";
}
