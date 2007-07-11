package org.apache.myfaces.custom.skin.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlOutputFormatSkinRenderer extends AdapterSkinRenderer {

	public HtmlOutputFormatSkinRenderer() {
		super("h", "outputFormat");
	}

	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		_addStyleClass(context, component, arc);
	}	
}
