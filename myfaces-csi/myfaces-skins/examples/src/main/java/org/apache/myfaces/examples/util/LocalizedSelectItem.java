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
package org.apache.myfaces.examples.util;

import javax.faces.model.SelectItem;

/**
 * DOCUMENT ME!
 * @author Thomas Spiegl (latest modification by $Author: grantsmith $)
 * @version $Revision: 472610 $ $Date: 2006-11-08 14:46:34 -0500 (Wed, 08 Nov 2006) $
 */
public class LocalizedSelectItem extends SelectItem
{
    /**
     * serial id for serialisation versioning
     */
    private static final long serialVersionUID = 1L;

    public LocalizedSelectItem(String key)
    {
        super(key);
        String label = GuiUtil.getMessageResource(key, null);
        setLabel(label);
    }

    public LocalizedSelectItem(Object value, String key)
    {
        super(value);

        String label = GuiUtil.getMessageResource(key, null);
        setLabel(label);
    }
}
