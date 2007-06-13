package org.apache.myfaces.custom.skin;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlDataScrollerExtSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory
			.getLog(HtmlDataScrollerExtSkinRenderer.class);

	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		// TODO Auto-generated method stub
		this.encodeHtmlSelectOneOrMany(context, component, arc);
	}

	/**
	 * Apply the following css class style attributes:
	 * 
	 * fastfStyleClass fastrStyleClass firstStyleClass lastStyleClass
	 * nextStyleClass paginatorActiveColumnClass paginatorColumnClass
	 * paginatorTableClass previousStyleClass styleClass
	 * 
	 * @param context
	 * @param component
	 * @param arc
	 * @throws IOException
	 */
	public void encodeHtmlSelectOneOrMany(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');
		
		String fastfStyleClass = baseStyleClass + "::fastf";
		String fastrStyleClass = baseStyleClass + "::fastr";
		String firstStyleClass = baseStyleClass + "::first";
		String lastStyleClass = baseStyleClass + "::last";
		String nextStyleClass = baseStyleClass + "::next";
		String paginatorActiveColumnClass = baseStyleClass + "::paginatorActiveColumn";
		String paginatorColumnClass = baseStyleClass + "::paginatorColumn";
		String paginatorTableClass = baseStyleClass + "::paginatorTable";
		String previousStyleClass = baseStyleClass + "::previous";
		String styleClass = baseStyleClass + "::class";

		renderStyleClass(component, context, arc, fastfStyleClass, "fastfStyleClass");
		renderStyleClass(component, context, arc, fastrStyleClass, "fastrStyleClass");		
		renderStyleClass(component, context, arc, firstStyleClass, "firstStyleClass");		
		renderStyleClass(component, context, arc, lastStyleClass, "lastStyleClass");
		renderStyleClass(component, context, arc, nextStyleClass, "nextStyleClass");		
		renderStyleClass(component, context, arc, paginatorActiveColumnClass, "paginatorActiveColumnClass");
		renderStyleClass(component, context, arc, paginatorColumnClass, "paginatorColumnClass");
		renderStyleClass(component, context, arc, paginatorTableClass, "paginatorTableClass");		
		renderStyleClass(component, context, arc, previousStyleClass, "previousStyleClass");
		renderStyleClass(component, context, arc, styleClass, "styleClass");
	}
}
