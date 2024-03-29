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
package org.apache.myfaces.examples.colspanexample;

import java.util.ArrayList;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

public class TestColspanBean
{
    public DataModel getLines()
    {
        ArrayList a = new ArrayList();
        a.add(new Line("1-1", "1-2", "1-3", "1-4", "1-5"));
        a.add(new Line("2-1", "2-2", "2-3", "2-4", "2-5"));
        a.add(new Line("3-1", "3-2", "3-3", "3-4", "3-5"));
        a.add(new Line("4-1", "4-2", "4-3", "4-4", "4-5"));
        DataModel testModel = new ListDataModel();
        testModel.setWrappedData(a);
        return testModel;
    }

    public DataModel getCellLines()
    {
        ArrayList a = new ArrayList();
        a.add(new CellLineBean());
        a.add(new CellLineBean());
        a.add(new CellLineBean());
        a.add(new CellLineBean());
        DataModel testModel = new ListDataModel();
        testModel.setWrappedData(a);
        return testModel;
    }
}
