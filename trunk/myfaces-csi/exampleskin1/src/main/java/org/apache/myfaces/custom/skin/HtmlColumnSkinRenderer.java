package org.apache.myfaces.custom.skin;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlColumnSkinRenderer extends SkinRenderer{

	@Override
	public void addStyleClassesToComponent(FacesContext context, UIComponent component, RenderingContext arc) throws IOException {
		// TODO Auto-generated method stub
		this.encodeHtmlColumn(context, component, arc);
	}

	// For use in jsf 1.2
	public void encodeHtmlColumn(FacesContext context, UIComponent component,
			RenderingContext arc) throws IOException {

		String footerStyleClass = null;
		String headerStyleClass = null;

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		footerStyleClass = baseStyleClass + "::footer";
		headerStyleClass = baseStyleClass + "::header";

		renderStyleClass(component, context, arc, footerStyleClass,
				"footerClass");
		renderStyleClass(component, context, arc, headerStyleClass,
				"headerClass");
	}
}
