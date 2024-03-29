<%@ page session="false" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s" %>

<html>

<%@ include file="inc/head.inc" %>

<!--
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
//-->

<body>

<script type="text/javascript">
    function successful(elname, elvalue)
    {
        var statusDiv = document.getElementById("statusDiv");
        statusDiv.innerHTML = "YAY!";
    }
    function failed(elname, elvalue)
    {
        var statusDiv = document.getElementById("statusDiv");
        statusDiv.innerHTML = "DOH!";
    }
    function startPulse(el){

        new Effect.Pulsate(el);
    }
</script>
<style type="text/css">
    .bold {
        font-weight: bold;
    }

    .error {
        font-weight: bold;
        color: red;
    }
</style>

<h1>Ajax Form Components</h1>

<p>The backend data model will update without having to click the submit button. To see error handling, put in some
    random text into the Fields.<br/> It is possible to place one error message beneath each field or having one
    central place for displaying all messages. <br/> In this case it may be useful to mark with the
    help of the errorStyleClass or errorStyle attribute of the ajax fields where the error have occured. </p> <br/>

<f:view>

<t:panelLayout
        layout="classic">
<f:facet name="body">
<h:form>

<h:panelGrid>

<h:panelGrid>
    <h:outputText styleClass="standard_bold" value="Input Some Text"/>
    <h:panelGrid columns="2">
        <s:inputTextAjax value="#{inputAjaxBean.text1}" id="text1"
                         validator="#{inputAjaxBean.validateText1}"
                         errorStyle="border:1px solid red; color:red;"/>
        <t:message forceSpan="true" styleClass="errorMessage" for="text1"></t:message>
    </h:panelGrid>
    <f:verbatim>This component demonstrates ajax updating ability when you change the
        text. <br/> An error message is displayed if the given String is greater than 5 characters.</f:verbatim>
</h:panelGrid>

<f:verbatim><br/></f:verbatim>

<h:panelGrid>
    <h:outputText styleClass="standard_bold" value="Input Some Text"/>
    <h:panelGrid columns="2">
        <s:inputTextAjax value="#{inputAjaxBean.text2}"
                         id="text2"
                         showOkButton="true"
                         showCancelButton="true"
                         validator="#{inputAjaxBean.validateText2}">
            <f:valueChangeListener type="org.apache.myfaces.custom.inputAjax.SampleValueChangeListener"/>
        </s:inputTextAjax>
        <t:message forceSpan="true" for="text2" style="color:red; font-weight:bold;"/>
    </h:panelGrid>
    <f:verbatim>This component demonstrates ajax updating ability, but you must click Ok for it to send.<br/>
        Cancel will clear the text and not send an update. Will show a validation error if the string is
        less then 3 characters.</f:verbatim>
</h:panelGrid>

<f:verbatim><br/></f:verbatim>

<h:panelGrid columns="1">
    <h:outputText styleClass="standard_bold" value="Input a Date"/>
    <h:panelGrid columns="2">
        <s:inputTextAjax value="#{inputAjaxBean.date1}"
                         id="date1"
                         errorStyleClass="errorField">
            <s:convertDateTime pattern="yyyy-MM-dd"/>
        </s:inputTextAjax>
        <t:message for="date1" forceSpan="true" styleClass="errorMessage"/>
    </h:panelGrid>
    <f:verbatim>This component demonstrates error handling capabilities, enter an invalid string to see the
        error returned from the server through ajax.</f:verbatim>
</h:panelGrid>

<f:verbatim><br/></f:verbatim>

<h:panelGrid>
    <h:panelGrid style="border:1px solid #71A5A5; background-color:rgb(236, 243, 225); width:450px;height:50px;">
        <t:messages forceSpan="true" styleClass="errorMessage"></t:messages>
    </h:panelGrid>
    <f:verbatim>This is the thomahawk messages component with forceRenderSpan set to true. <br/>
        All messages which are generated by the inputTextAjax fields are displayed here.
    </f:verbatim>
</h:panelGrid>

<f:verbatim><br/><br/></f:verbatim>


<h:panelGrid columns="2">
    <h:panelGroup>
        <h:outputText styleClass="standard_bold" value="Select Some Boxes"/>
        <h:panelGrid columns="1">
            <s:selectManyCheckboxAjax
                    id="smcb"
                    value="#{inputAjaxBean.chosenValues}">
                <f:selectItems
                        value="#{inputAjaxBean.checkboxItems}"/>
            </s:selectManyCheckboxAjax>
            <f:verbatim>This component demonstrates ajax updating ability on a checkboxes.</f:verbatim>
        </h:panelGrid>
    </h:panelGroup>
    <h:panelGroup>
        <t:htmlTag value="br"/>
        <h:outputText value="Current chosen checkbox values in server model"/>
        <t:htmlTag value="br"/>
        <h:dataTable
                value="#{inputAjaxBean.chosenValues}"
                var="cvalue">
            <h:column>
                <f:facet name="header">
                    <h:outputText value="value"/>
                </f:facet>
                <h:outputText value="#{cvalue}"/>
            </h:column>
        </h:dataTable>
    </h:panelGroup>
</h:panelGrid>


<h:outputText styleClass="standard_bold" value="Select A Radio Button"/>
<h:panelGrid columns="1">
    <s:selectOneRadioAjax
            id="radio1"
            value="#{inputAjaxBean.radioValue}">
        <f:valueChangeListener type="org.apache.myfaces.custom.inputAjax.SampleValueChangeListener"/>
        <f:selectItems
                value="#{inputAjaxBean.radioItems}"/>
    </s:selectOneRadioAjax>
    <f:verbatim>This component demonstrates ajax updating ability on a radio buttons.</f:verbatim>
</h:panelGrid>

<h:outputText styleClass="standard_bold" value="Toggle Switch"/>
<h:panelGrid columns="1">
    <h:panelGroup>
        <s:selectBooleanCheckboxAjax
                id="toggle1"
                value="#{inputAjaxBean.toggle1}"/>
        <h:outputText value="Got Milk?"/>
    </h:panelGroup>
    <t:message for="toggle1" forceSpan="true" styleClass="errorMessage"/>
    <f:verbatim>This component demonstrates ajax updating ability based on a toggle switch.</f:verbatim>
</h:panelGrid>

<h:outputText styleClass="standard_bold" value="Toggle Switch With Images"/>
<h:panelGrid columns="1">
    <h:panelGroup>
        <s:selectBooleanCheckboxAjax
                id="toggle2"
                value="#{inputAjaxBean.toggle2}"
                onImage="images/nav-plus.gif"
                offImage="images/nav-minus.gif">
            <f:valueChangeListener type="org.apache.myfaces.custom.inputAjax.SampleValueChangeListener"/>
        </s:selectBooleanCheckboxAjax>
        <h:outputText value="Got Juice?"/>
    </h:panelGroup>
    <t:message for="toggle2" forceSpan="true" styleClass="errorMessage"/>
    <f:verbatim>This component demonstrates ajax updating ability based on a toggle switch.</f:verbatim>
</h:panelGrid>


</h:panelGrid>
<t:div id="statusDiv"></t:div>
<h:commandButton action="#{inputAjaxBean.submit}" value="Submit"/>
</h:form>
</f:facet>
</t:panelLayout>

<h:panelGrid columns="2">
    <h:form>
        <h:panelGrid columns="1">
            <h:outputText styleClass="standard_bold" value="Form Submit via AJAX"/>
            <f:verbatim>This component demonstrates submitting a form through ajax. To see validation errors, put less
            than five characters in first box, and a string with a space in it in the second box.</f:verbatim>
            <h:panelGrid columns="2">
                <t:inputText id="formText1" value="#{inputAjaxBean.formText1}"
                             validator="#{inputAjaxBean.validateFormText1}"></t:inputText>
                <t:message for="formText1" forceSpan="true" styleClass="errorMessage"/>

                <t:inputText id="formText2" value="#{inputAjaxBean.formText2}"
                             validator="#{inputAjaxBean.validateFormText2}"></t:inputText>
                <t:message for="formText2" forceSpan="true" styleClass="errorMessage"/>

                <s:commandButtonAjax action="#{inputAjaxBean.ajaxFormSubmit}" value="Submit"/>
                <h:outputText value="&nbsp;" escape="false"/>
            </h:panelGrid>
        </h:panelGrid>
    </h:form>
    <%-- Auto updating piece --%>
    <h:panelGrid columns="1">
        <h:outputText styleClass="standard_bold" value="Listening on the first textfield to the left"/>
        <s:outputText value="#{inputAjaxBean.waitingText1}">
            <s:listener on="formText1"/>
        </s:outputText>
        <h:outputText styleClass="standard_bold" value="Listening on the second textfield to the left with multiple listeners"/>
            <s:outputText value="#{inputAjaxBean.waitingText2}">
                <s:listener on="formText2"/>
                <s:listener on="formText2" action="javascript:startPulse(this);"/>
            </s:outputText>

    </h:panelGrid>
</h:panelGrid>

<h:outputLink value="inputAjax.jsf"><h:outputText value="Refresh"></h:outputText></h:outputLink>

<t:div id="logDiv" forceId="true">
    <b>Log:</b><br/>
</t:div>

</f:view>

<%@ include file="inc/page_footer.jsp" %>

</body>

</html>

