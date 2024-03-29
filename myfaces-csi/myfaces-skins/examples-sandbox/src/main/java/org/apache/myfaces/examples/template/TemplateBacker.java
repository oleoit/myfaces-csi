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

package org.apache.myfaces.examples.template;

import java.net.URL;
import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Backing bean for template examples.  Used to demonstrate how you can provide both the
 * content and the stylesheet using value bindings.
 *
 * @author Sean Schofield
 */
public class TemplateBacker
{
    private ClassLoader loader = Thread.currentThread().getContextClassLoader();

    public TemplateBacker()
    {
        if (loader == null)
        {
            loader = TemplateBacker.class.getClassLoader();
        }
    }

    public InputStream getContentStream() throws IOException
    {
        URL url = loader.getResource("org/apache/myfaces/examples/template/foo.xml");
        return new FileInputStream(new File(url.getFile()));
    }

    public InputStream getStyleStream() throws IOException
    {
        URL url = loader.getResource("org/apache/myfaces/examples/template/foo.xsl");
        return new FileInputStream(new File(url.getFile()));
    }
}
