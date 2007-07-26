package org.apache.myfaces.examples.facelets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;

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

public class StudentDataBean
{

    private DataModel _dataModel = null;

    private List<Student> _students;

    private static final String DEFAULT_SORT_COLUMN = "Id";

    /**
     * The student that its necesary to update
     */
    private Student updateStudent;

    public StudentDataBean()
    {

        _students = new ArrayList<Student>();
        for (int i = 0; i < 200; i++)
        {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date d1 = null;
            try
            {
                d1 = sf.parse("1980-11-30");
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            _students.add(new Student("1" + i, "Henry" + i, "Ford" + i, d1, i
                    + "73748899"));
            _students.add(new Student("2" + i, "John" + i, "Karmack" + i, d1, i
                    + "87394879"));
            _students.add(new Student("3" + i, "Lucius" + i, "Kennedy" + i, d1,
                    i + "2238298"));
            _students.add(new Student("4" + i, "Jay" + i, "Skywalker" + i, d1,
                    i + "2238298"));
        }

    }

    public void createStudent(Student s)
    {
        _students.add(s);
    }

    private DataPage<Student> getDataPage(int startRow, int pageSize)
    {
        // access database here, or call EJB to do so
        int datasetSize = _students.size();
        if (startRow < _students.size())
        {
            return new DataPage<Student>(datasetSize, startRow, _students
                    .subList(startRow,
                            startRow + pageSize < datasetSize ? startRow
                                    + pageSize : datasetSize));
        }
        else
        {
            return null;
        }
    }

    public Integer getRowsPerPage()
    {
        return 10;
    }

    public DataModel getDataModel()
    {
        if (_dataModel == null)
        {
            _dataModel = new LocalDataModel(getRowsPerPage(),
                    DEFAULT_SORT_COLUMN);
        }
        return _dataModel;
    }

    public String update()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object id = map.get("student");

        for (Iterator it = _students.iterator(); it.hasNext();)
        {
            Student s = (Student) it.next();
            if (s.getId().equals(id))
            {
                this.setUpdateStudent(s);
                break;
            }
        }
        return "go_update";
    }

    public void setUpdateStudent(Student updateStudent)
    {
        this.updateStudent = updateStudent;
    }

    public Student getUpdateStudent()
    {
        return updateStudent;
    }

    public String remove()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        Object id = map.get("student");

        for (Iterator it = _students.iterator(); it.hasNext();)
        {
            Student s = (Student) it.next();
            if (s.getId().equals(id))
            {
                it.remove();
                break;
            }
        }
        return "go_list";
    }

    private class StudentComparator implements Comparator
    {

        private String column;
        private boolean ascending;

        public StudentComparator(String column, boolean ascending)
        {
            this.column = column;
            this.ascending = ascending;
        }

        public int compare(Object o1, Object o2)
        {
            Student c1 = (Student) o1;
            Student c2 = (Student) o2;
            if (column == null)
            {
                return 0;
            }
            if (column.equals("Id"))
            {
                return ascending ? c1.getId().compareTo(c2.getId()) : c2
                        .getId().compareTo(c1.getId());
            }
            if (column.equals("Name"))
            {
                return ascending ? c1.getName().compareTo(c2.getName()) : c2
                        .getName().compareTo(c1.getName());
            }
            if (column.equals("LastName"))
            {
                return ascending ? c1.getLastName().compareTo(c2.getLastName())
                        : c2.getLastName().compareTo(c1.getLastName());
            }
            if (column.equals("DateOfBirth"))
            {
                return ascending ? c1.getDateOfBirth().compareTo(
                        c2.getDateOfBirth()) : c2.getDateOfBirth().compareTo(
                        c1.getDateOfBirth());
            }
            if (column.equals("Phone"))
            {
                return ascending ? c1.getPhone().compareTo(c2.getPhone()) : c2
                        .getPhone().compareTo(c1.getPhone());
            }
            else
                return 0;
        }
    }

    private class LocalDataModel extends PagedSortableListDataModel
    {
        public LocalDataModel(int pageSize, String defaultSortColumn)
        {
            super(pageSize, defaultSortColumn);
        }

        public DataPage<Student> fetchPage(int startRow, int pageSize)
        {
            // call enclosing managed bean method to fetch the data
            sort(getSort(), isAscending());
            return getDataPage(startRow, pageSize);
        }

        @Override
        protected boolean isDefaultAscending(String sortColumn)
        {
            return false;
        }

        @Override
        protected void sort(String column, boolean ascending)
        {
            Comparator comparator = new StudentComparator(column, ascending);
            Collections.sort(_students, comparator);
        }
    }
}
