package org.apache.myfaces.renderkit.html.skin;

import javax.faces.component.UIComponent;

import org.apache.myfaces.renderkit.html.ext.HtmlTextRenderer;

public class HtmlTextSkinRenderer 
    extends HtmlTextRenderer implements Skinnable
{

    public void applySkin(UIComponent component)
    {
        component.getAttributes().put("styleClass", "af_textfield");
    }

}
