package org.apache.myfaces.custom.skin.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlSelectManyListboxSkinRenderer extends HtmlSelectOneOrManySkinRenderer {

	public HtmlSelectManyListboxSkinRenderer() {
		super("h", "selectManyListbox");
	}

}
