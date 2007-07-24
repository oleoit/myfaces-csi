package org.apache.myfaces.examples.facelets;

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

import java.util.List;

//Taken from the wiki page of apache myfaces
//http://wiki.apache.org/myfaces/WorkingWithLargeTables

public class DataPage<T> {
	  private int datasetSize;
	  private int startRow;
	  private List<T> data;
	        
	  /**
	   * Create an object representing a sublist of a dataset.
	   * 
	   * @param datasetSize is the total number of matching rows
	   * available.
	   * 
	   * @param startRow is the index within the complete dataset
	   * of the first element in the data list.
	   * 
	   * @param data is a list of consecutive objects from the
	   * dataset.
	   */
	  public DataPage(int datasetSize, int startRow, List<T> data) {
	    this.datasetSize = datasetSize;
	    this.startRow = startRow;
	    this.data = data;
	  }

	  /**
	   * Return the number of items in the full dataset.
	   */
	  public int getDatasetSize() {
	    return datasetSize;
	  }

	  /**
	   * Return the offset within the full dataset of the first
	   * element in the list held by this object.
	   */
	  public int getStartRow() {
	    return startRow;
	  }

	  /**
	   * Return the list of objects held by this object, which
	   * is a continuous subset of the full dataset.
	   */
	  public List<T> getData() {
	    return data;
	  }
}
