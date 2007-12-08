<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tableformlayout" prefix="mycomp"%>

<html>

<head>
    <title>TableFormLayout Component for Trinidad</title>
</head>

<body>

<f:view>
    <h:form>

        <h:panelGrid>

            <h:outputText value="TableFormLayout Component for Trinidad" />
            <h:panelGrid style="padding-left:25px">
                <h:outputLink value="faces/example1.jspx">
                    <f:verbatim>tableFormLayout - example 1</f:verbatim>
                </h:outputLink>
                <h:outputLink value="faces/example2.jspx">
                    <f:verbatim>tableFormLayout - example 2</f:verbatim>
                </h:outputLink>                                
                <h:outputLink value="faces/example3.jspx">
                    <f:verbatim>tableFormLayout - example 3</f:verbatim>
                </h:outputLink>                                
                
            </h:panelGrid>

        </h:panelGrid>

    </h:form>
</f:view>
</body>
</html>
