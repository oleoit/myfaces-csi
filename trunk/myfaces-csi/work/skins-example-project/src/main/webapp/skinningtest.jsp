<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/skins" prefix="skins" %>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t" %>
<html>
<body>

<f:view>

  <skins:skinCss/>

  <h:form id="mainForm">
    <h:outputText value="This is a sample page to demonstrate the new skinning framework."/>
    <h:panelGrid columns="2">
      <t:outputText value="Input Text1"/>
      <t:inputText/>
      <t:outputText value="Input Text2"/>
      <t:inputText/>
      <t:outputText value="Command Button"/>
      <t:commandButton value="Test"/>

      <t:outputText value="Panel Group"/>
      <t:panelGroup style="background-color: #9999ff">
        <t:selectManyCheckbox>
          <f:selectItem itemLabel="item1" itemValue="item1"/>
          <f:selectItem itemLabel="item2" itemValue="item2"/>
          <f:selectItem itemLabel="item3" itemValue="item3"/>
        </t:selectManyCheckbox>
        <t:selectOneRadio>
          <f:selectItem itemLabel="item1" itemValue="item1"/>
          <f:selectItem itemLabel="item2" itemValue="item2"/>
          <f:selectItem itemLabel="item3" itemValue="item3"/>
        </t:selectOneRadio>
        <t:commandButton value="Submit"/>
      </t:panelGroup>
    </h:panelGrid>
  </h:form>
</f:view>

</body>
</html>