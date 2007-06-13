package org.apache.myfaces.custom.skin;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlSelectOneOrManySkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(HtmlSelectOneOrManySkinRenderer.class);	
		
	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		// TODO Auto-generated method stub
		this.encodeHtmlSelectOneOrMany(context, component, arc);
	}

	public void encodeHtmlSelectOneOrMany(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		this.encodeGenericComponent(context, component, arc);
		
		String disabledStyleClass = null;
		String enabledStyleClass = null;

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		disabledStyleClass = baseStyleClass + "::disabled";
		enabledStyleClass = baseStyleClass + "::enabled";

		renderStyleClass(component, context, arc, disabledStyleClass,
				"disabledClass");
		renderStyleClass(component, context, arc, enabledStyleClass,
				"enabledClass");
		
	}	
}
