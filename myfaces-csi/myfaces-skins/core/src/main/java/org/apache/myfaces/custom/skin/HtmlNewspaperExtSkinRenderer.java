package org.apache.myfaces.custom.skin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlNewspaperExtSkinRenderer extends GenericSkinRenderer {

	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		this.encodeHtmlPanelGrid(context, component, arc);
	}

	public void encodeHtmlPanelGrid(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		this.encodeGenericComponent(context, component, arc);

		String footerStyleClass = null;
		String headerStyleClass = null;
		String rowStyleClass = null;
		

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		footerStyleClass = baseStyleClass + "::footer";
		headerStyleClass = baseStyleClass + "::header";
		rowStyleClass = baseStyleClass + "::row";

		renderStyleClass(component, context, arc, footerStyleClass,
			"footerClass");
		renderStyleClass(component, context, arc, headerStyleClass,
			"headerClass");

		Map m = component.getAttributes();
		String oldRowClasses = (String) m.get("rowClasses");
		List<String> list = parseStyleClassListComma(oldRowClasses);
		if (list == null){
			renderStyleClass(component, context, arc, rowStyleClass,
			"rowClasses");			
		}else{
			String def = arc.getStyleClass(rowStyleClass);
			String [] l1 = new String[list.size()];			
			int length = 0;
			for (int i = 0; i < l1.length; i++){
				if (!list.get(i).contains(def)){
					l1[i] = list.get(i)+" "+def;
					length += l1[i].length() + 1;
				}
			}
			
			StringBuilder builder = new StringBuilder(length);
			for (int i = 0; i < l1.length; i++) {
				if (l1[i] != null) {
					if (builder.length() != 0)
						builder.append(',');
					builder.append(l1[i]);
				}
			}
			component.getAttributes().put("rowClasses", builder.toString());
		}
	}	
	
}
