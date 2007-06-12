package org.apache.myfaces.custom.skin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlPanelLayoutExtSkinRenderer extends GenericSkinRenderer {

	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		this.encodeHtmlPanelGrid(context, component, arc);
	}

	public void encodeHtmlPanelGrid(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		this.encodeGenericComponent(context, component, arc);

		String bodyStyleClass = null;
		String footerStyleClass = null;
		String headerStyleClass = null;
		String navigationStyleClass = null;
		

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		bodyStyleClass = baseStyleClass + "::body";
		footerStyleClass = baseStyleClass + "::footer";
		headerStyleClass = baseStyleClass + "::header";
		navigationStyleClass = baseStyleClass + "::navigation";

		renderStyleClass(component, context, arc, bodyStyleClass,
			"bodyClass");		
		renderStyleClass(component, context, arc, footerStyleClass,
			"footerClass");
		renderStyleClass(component, context, arc, headerStyleClass,
			"headerClass");
		renderStyleClass(component, context, arc, navigationStyleClass,
			"navigationClass");
		

	}	
	
}
