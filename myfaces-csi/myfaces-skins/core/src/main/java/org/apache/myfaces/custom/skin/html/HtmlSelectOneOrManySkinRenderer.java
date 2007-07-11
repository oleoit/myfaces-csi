package org.apache.myfaces.custom.skin.html;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.skin.AdapterSkinRenderer;
import org.apache.myfaces.custom.skin.SkinConstants;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlSelectOneOrManySkinRenderer extends AdapterSkinRenderer {

	public HtmlSelectOneOrManySkinRenderer(String namespace,String componentTag) {
		super(namespace, componentTag);
	}

	@Override
	protected void _addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		_addStyleDisabledReadOnlyRequired(context, component, arc);
		
		String disabledStyleClass = null;
		String enabledStyleClass = null;

		String baseStyleClass = getBaseStyleName(component);

		disabledStyleClass = baseStyleClass + SkinConstants.DISABLED_CLASS_SUFFIX;
		enabledStyleClass = baseStyleClass + "::enabled";

		_renderStyleClass(component, context, arc, disabledStyleClass,
				"disabledClass");
		_renderStyleClass(component, context, arc, enabledStyleClass,
				"enabledClass");
		
	}	
}
