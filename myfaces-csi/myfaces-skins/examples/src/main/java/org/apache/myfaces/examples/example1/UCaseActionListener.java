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
package org.apache.myfaces.examples.example1;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.VariableResolver;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

/**
 * DOCUMENT ME!
 * @author Manfred Geiler
 * @version $Revision: 472610 $ $Date: 2006-11-08 14:46:34 -0500 (Wed, 08 Nov 2006) $
 */
public class UCaseActionListener implements ActionListener
{
    public void processAction(ActionEvent event)
            throws AbortProcessingException
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UIComponent component = event.getComponent();

        VariableResolver vr = facesContext.getApplication()
                .getVariableResolver();
        UCaseForm form = (UCaseForm) vr.resolveVariable(facesContext,
                "ucaseForm");
        if (component.getId().equals("ucaseButton"))
        {
            form.uppercase();
        }
        else
        {
            form.lowercase();
        }
    }
}
