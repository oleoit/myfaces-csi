package org.apache.myfaces.custom.skin;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlMessageSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(HtmlMessageSkinRenderer.class);	
	
	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		log.info("HtmlMessageSkinRenderer rendered");
		this.encodeHtmlMessage(context, component, arc);
	}
	
	public void encodeHtmlMessage(FacesContext context, UIComponent component,
			RenderingContext arc) throws IOException {

		this.encodeGenericComponent(context, component, arc);

		// now add fatal, info, error and warn StyleClass
		String fatalStyleClass = null;
		String infoStyleClass = null;
		String errorStyleClass = null;
		String warnStyleClass = null;

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		fatalStyleClass = baseStyleClass + "::fatal";
		infoStyleClass = baseStyleClass + "::info";
		errorStyleClass = baseStyleClass + "::error";
		warnStyleClass = baseStyleClass + "::warn";

		renderStyleClass(component, context, arc, fatalStyleClass, "fatalClass");
		renderStyleClass(component, context, arc, infoStyleClass, "infoClass");
		renderStyleClass(component, context, arc, errorStyleClass, "errorClass");
		renderStyleClass(component, context, arc, warnStyleClass, "warnClass");
	}

}
