package org.apache.myfaces.custom.skin;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidad.context.RenderingContext;

public class HtmlScheduleSkinRenderer extends GenericSkinRenderer {

	/**
	 * The log factory used to debug messages
	 */
	private static final Log log = LogFactory.getLog(HtmlScheduleSkinRenderer.class);	
		
	@Override
	public void addStyleClassesToComponent(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {
		// TODO Auto-generated method stub
		this.encodeSchedule(context, component, arc);
	}

	/**
	 * Apply the following css class style attributes:
	 * 
	 * currentDayCellClass
	 * dayCellClass
	 * displayValueOnlyStyleClass 
	 * monthYearRowClass
	 * popupButtonStyleClass
	 * styleClass
	 * weekRowClass
	 * 
	 * @param context
	 * @param component
	 * @param arc
	 * @throws IOException
	 */
	public void encodeSchedule(FacesContext context,
			UIComponent component, RenderingContext arc) throws IOException {

		String baseStyleClass = "af|"
				+ StringUtils.replaceChars(component.getClass().getName(), '.',
						'_');

		String backgroundStyleClass = baseStyleClass + "::background";
		String columnStyleClass = baseStyleClass + "::column";
		String contentStyleClass = baseStyleClass + "::content";
		String dateStyleClass = baseStyleClass + "::date";
		String dayStyleClass = baseStyleClass + "::day";
		String entryStyleClass = baseStyleClass + "::entry";		
		String evenStyleClass = baseStyleClass + "::even";		
		String foregroundStyleClass = baseStyleClass + "::foreground";
		String freeStyleClass = baseStyleClass + "::free";		
		String gutterStyleClass = baseStyleClass + "::gutter";
		String headerStyleClass = baseStyleClass + "::header";		
		String holidayStyleClass = baseStyleClass + "::holiday";
		String hoursStyleClass = baseStyleClass + "::hours";
		String inactiveDayStyleClass = baseStyleClass + "::inactiveDay";
		String minutesStyleClass = baseStyleClass + "::minutes";
		String monthStyleClass = baseStyleClass + "::month";
		String selectedStyleClass = baseStyleClass + "::selected";
		String selectedEntryStyleClass = baseStyleClass + "::selectedEntry";
		String subtitleStyleClass = baseStyleClass + "::subtitle";
		String textStyleClass = baseStyleClass + "::text";
		String titleStyleClass = baseStyleClass + "::title";
		String unevenStyleClass = baseStyleClass + "::uneven";
		String weekStyleClass = baseStyleClass + "::week";
		
		renderStyleClass(component, context, arc, backgroundStyleClass,
				"backgroundClass");
		renderStyleClass(component, context, arc, columnStyleClass,
			"columnClass");		
		renderStyleClass(component, context, arc, contentStyleClass,
			"contentClass");		
		renderStyleClass(component, context, arc, dateStyleClass,
			"dateClass");		
		renderStyleClass(component, context, arc, dayStyleClass,
			"dayClass");		
		renderStyleClass(component, context, arc, entryStyleClass,
			"entryClass");
		renderStyleClass(component, context, arc, evenStyleClass,
			"evenClass");				
		renderStyleClass(component, context, arc, foregroundStyleClass,
			"foregroundClass");
		renderStyleClass(component, context, arc, freeStyleClass,
			"freeClass");				
		renderStyleClass(component, context, arc, gutterStyleClass,
			"gutterClass");
		renderStyleClass(component, context, arc, headerStyleClass,
			"headerClass");				
		renderStyleClass(component, context, arc, holidayStyleClass,
			"holidayClass");				
		renderStyleClass(component, context, arc, hoursStyleClass,
			"hoursClass");
		renderStyleClass(component, context, arc, inactiveDayStyleClass,
			"inactiveDayClass");				
		renderStyleClass(component, context, arc, minutesStyleClass,
			"minutesClass");				
		renderStyleClass(component, context, arc, monthStyleClass,
			"monthClass");				
		renderStyleClass(component, context, arc, selectedStyleClass,
			"selectedClass");				
		renderStyleClass(component, context, arc, selectedEntryStyleClass,
			"selectedEntryClass");				
		renderStyleClass(component, context, arc, subtitleStyleClass,
			"subtitleClass");				
		renderStyleClass(component, context, arc, textStyleClass,
			"textClass");				
		renderStyleClass(component, context, arc, titleStyleClass,
			"titleClass");				
		renderStyleClass(component, context, arc, unevenStyleClass,
			"unevenClass");
		renderStyleClass(component, context, arc, weekStyleClass,
			"weekClass");				
	}	
}
