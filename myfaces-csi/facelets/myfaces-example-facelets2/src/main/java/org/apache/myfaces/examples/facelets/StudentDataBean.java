package org.apache.myfaces.examples.facelets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

public class StudentDataBean {

	private DataModel _dataModel = null;
	
	private List<Student> _students;
	
	private static final String DEFAULT_SORT_COLUMN = "Id";
	
	public StudentDataBean(){
		
		_students = new ArrayList<Student>();
		for (int i = 0; i < 200; i++)
		{
			_students.add(new Student("1"+i,"Henry"+i,"Ford"+i,12,i+"73748899"));
			_students.add(new Student("2"+i,"John"+i,"Karmack"+i,13,i+"87394879"));
			_students.add(new Student("3"+i,"Lucius"+i,"Kennedy"+i,14,i+"2238298"));
			_students.add(new Student("4"+i,"Jay"+i,"Skywalker"+i,15,i+"2238298"));
		}
		
	}

	private DataPage<Student> getDataPage(int startRow, int pageSize) {
		// access database here, or call EJB to do so
		int datasetSize = _students.size();
		if (startRow < _students.size()){
			return new DataPage<Student>(datasetSize,startRow,
					_students.subList(startRow, 
							startRow+pageSize<datasetSize?
									startRow+pageSize
									:datasetSize));
		}else{
			return null;
		}
	}

	public Integer getRowsPerPage(){
		return 10;
	}
	
	public DataModel getDataModel() {
		if (_dataModel == null) {
			_dataModel = new LocalDataModel(getRowsPerPage(), DEFAULT_SORT_COLUMN);
		}
		return _dataModel;
	}

	private class StudentComparator implements Comparator{

		private String column;
		private boolean ascending;
		
		public StudentComparator(String column, boolean ascending){
			this.column = column;
			this.ascending = ascending;
		}
		
		public int compare(Object o1, Object o2) {
            Student c1 = (Student)o1;
            Student c2 = (Student)o2;
            if (column == null)
            {
                return 0;
            }
            if (column.equals("Id"))
            {
                return ascending ? c1.getId().compareTo(c2.getId()) : 
                	c2.getId().compareTo(c1.getId());
            }
            else return 0;
		}
	}
	
	private class LocalDataModel extends PagedSortableListDataModel {
		public LocalDataModel(int pageSize,String defaultSortColumn) {
			super(pageSize,defaultSortColumn);
		}

		public DataPage<Student> fetchPage(int startRow, int pageSize) {
			// call enclosing managed bean method to fetch the data
			sort(getSort(), isAscending());
			return getDataPage(startRow, pageSize);			
		}

		@Override
		protected boolean isDefaultAscending(String sortColumn) {
			return false;
		}

		@Override
		protected void sort(String column, boolean ascending) {
	        Comparator comparator = new StudentComparator(column,ascending);
	        Collections.sort(_students, comparator);			
		}
	}
}
