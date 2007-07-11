package org.apache.myfaces.custom.skin.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlMessageSkinRenderer extends AdapterSkinRenderer {

	public HtmlMessageSkinRenderer() {
		super("h", "message");
	}

	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		_addStyleClass(context, component, arc);
		
		// now add fatal, info, error and warn StyleClass
		String fatalStyleClass = null;
		String infoStyleClass = null;
		String errorStyleClass = null;
		String warnStyleClass = null;

		String baseStyleClass = this.getBaseStyleName(component);

		fatalStyleClass = baseStyleClass + "::fatal";
		infoStyleClass = baseStyleClass + "::info";
		errorStyleClass = baseStyleClass + "::error";
		warnStyleClass = baseStyleClass + "::warn";

		_renderStyleClass(component, context, arc, fatalStyleClass, "fatalClass");
		_renderStyleClass(component, context, arc, infoStyleClass, "infoClass");
		_renderStyleClass(component, context, arc, errorStyleClass, "errorClass");
		_renderStyleClass(component, context, arc, warnStyleClass, "warnClass");		
	}	
}
