package org.apache.myfaces.custom.skin.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlSelectBooleanCheckboxSkinRenderer extends AdapterSkinRenderer {

	public HtmlSelectBooleanCheckboxSkinRenderer() {
		super("h", "selectBooleanCheckbox");
	}

	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		_addStyleDisabledReadOnlyRequired(context, component, arc);
	}	
}
