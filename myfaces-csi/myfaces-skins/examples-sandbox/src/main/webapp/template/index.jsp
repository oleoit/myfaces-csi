<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://myfaces.apache.org/sandbox" prefix="s"%>

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

<html>

	<%@include file="../inc/head.inc" %>

	<body>
        <f:view>
            <h:outputText value="Example 1: Source and Style Using Location"/>
            <br/>
            <s:xmlTemplate   rendered="true"
                            contentLocation="org/apache/myfaces/examples/template/foo.xml"
                            stylesheetLocation="org/apache/myfaces/examples/template/foo.xsl"/>

            <br/><br/>

            <h:outputText value="Example 2: Source and Style Using Streams and Value Binding"/>
            <br/>
            <s:xmlTemplate   rendered="true"
                             contentStream="#{templateBacker.contentStream}"
                             styleStream="#{templateBacker.styleStream}"/>

           <h:messages/>
		</f:view>
	</body>

	<%@include file="../inc/page_footer.jsp" %>

</html>