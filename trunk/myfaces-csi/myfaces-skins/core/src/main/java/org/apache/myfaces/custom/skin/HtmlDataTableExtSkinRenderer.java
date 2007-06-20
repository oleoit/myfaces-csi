package org.apache.myfaces.custom.skin;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.myfaces.custom.column.HtmlColumn;
import org.apache.myfaces.trinidad.context.RenderingContext;


public class HtmlDataTableExtSkinRenderer extends GenericSkinRenderer {

	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		// TODO Auto-generated method stub
		this.encodeHtmlDataTable(context, component, arc);
	}

	/**
	 * Apply the following css class style attributes:
	 * 
	 * bodyStyleClass footerClass headerClass rowGroupStyleClass rowStyleClass
	 * styleClass
	 * 
	 * @param context
	 * @param component
	 * @param arc
	 * @throws IOException
	 */
	public void encodeHtmlDataTable(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		String bodyStyleClass = null;
		String footerStyleClass = null;
		String headerStyleClass = null;
		String rowGroupStyleClass = null;
		String rowStyleClass = null;
		String styleClass = null;

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		bodyStyleClass = baseStyleClass + "::body";
		footerStyleClass = baseStyleClass + "::footer";
		headerStyleClass = baseStyleClass + "::header";
		rowGroupStyleClass = baseStyleClass + "::rowGroup";
		rowStyleClass = baseStyleClass + "::row";
		styleClass = baseStyleClass + "::class";

		renderStyleClass(component, context, arc, bodyStyleClass,
				"bodyStyleClass");
		renderStyleClass(component, context, arc, footerStyleClass,
				"footerStyleClass");
		renderStyleClass(component, context, arc, headerStyleClass,
				"headerStyleClass");
		renderStyleClass(component, context, arc, rowGroupStyleClass,
				"rowGroupStyleClass");
		renderStyleClass(component, context, arc, rowStyleClass,
				"rowStyleClass");
		renderStyleClass(component, context, arc, styleClass, "styleClass");
		
		//Now set column styles
        for (Iterator iter = component.getChildren().iterator(); iter.hasNext();)
        {
            UIComponent child = (UIComponent) iter.next();
            
            if (HtmlColumn.class.isAssignableFrom(child.getClass())){
            	//Add styleClass attributes
            	HtmlColumn col = (HtmlColumn) child;
            	//footerstyleClass
            	//headerstyleClass
            	//styleClass
            	baseStyleClass = "af|"
    				+ StringUtils.replaceChars(col.getClass().getName(), '.',
    						'_');            	
            	styleClass = baseStyleClass + "::class";
            	String footerstyleClass = baseStyleClass + "::footer";
            	String headerstyleClass = baseStyleClass + "::header";            	
            	
            	renderStyleClass(child, context, arc, styleClass, "styleClass");
            	renderStyleClass(child, context, arc, footerstyleClass, "footerstyleClass");
            	renderStyleClass(child, context, arc, headerstyleClass, "headerstyleClass");
            }
        }
		
	}
}
