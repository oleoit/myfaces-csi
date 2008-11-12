package org.apache.myfaces.renderkit.html.skin;

import javax.faces.component.UIComponent;

import org.apache.myfaces.renderkit.html.HtmlButtonRenderer;

public class HtmlButtonSkinRenderer extends HtmlButtonRenderer implements Skinnable
{

    public void applySkin(UIComponent component)
    {
        component.getAttributes().put("styleClass", "tr_button");
    }

}
