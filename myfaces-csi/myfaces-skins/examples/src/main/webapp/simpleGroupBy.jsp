<%@ page session="false" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/tomahawk" prefix="t"%>
<%@ taglib uri="http://myfaces.apache.org/trinidad/html" prefix="trh"%>
<html>

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
<f:view>
<%@include file="inc/head.inc" %>

<body>



    <f:loadBundle basename="org.apache.myfaces.examples.resource.example_messages" var="example_messages"/>

        <t:dataTable id="data"
                style="border-collapse:collapse;"
                styleClass="standardTable"
                headerClass="standardTable_Header"
                footerClass="standardTable_Header"
                rowClasses="standardTable_Row1,standardTable_Row2"
                var="demo"
                value="#{simpleGroupBy.demoList}"
                preserveDataModel="true"
                rowGroupStyle="border-bottom:blue;border-bottom-style:solid;border-bottom-width:2px;vertical-align:top"
                rowGroupStyleClass="testclass">
           <t:column groupBy="true">
               <f:facet name="header">
                  <h:outputText value="Groups" />
               </f:facet>
                    <h:outputText value="#{demo.value1}" />
           </t:column>

           <t:column>
               <f:facet name="header">
                  <h:outputText value="Items" />
               </f:facet>
               <h:outputText value="#{demo.value2}" />
           </t:column>


        </t:dataTable>

        <jsp:include page="inc/mbean_source.jsp"/>

</f:view>

<%@include file="inc/page_footer.jsp" %>

</body>

</html>
