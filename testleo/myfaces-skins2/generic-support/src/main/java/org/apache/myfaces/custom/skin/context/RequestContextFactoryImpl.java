/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.myfaces.custom.skin.context;

import javax.faces.context.ExternalContext;

import org.apache.myfaces.custom.skin.config.ConfigParser;
import org.apache.myfaces.trinidad.context.SkinRequestContext;

/**
 */
public class RequestContextFactoryImpl extends RequestContextFactory
{
    public RequestContextFactoryImpl()
    {
    }

    @Override
    @Deprecated
    public SkinRequestContext createContext(Object context, Object request)
    {
        throw new UnsupportedOperationException(
                "public RequestContext createContext(Object context, Object request)");
    }

    @Override
    public SkinRequestContext createContext(ExternalContext externalContext)
    {
        SkinRequestContextImpl impl = new SkinRequestContextImpl(
                _getBean(externalContext));
        impl.init(externalContext);
        return impl;
    }

    private RequestContextBean _getBean(ExternalContext externalContext)
    {
        if (_bean == null)
        {
            synchronized (this)
            {
                if (externalContext != null)
                {
                    _bean = ConfigParser.parseConfigFile(externalContext);
                }
                else
                {
                    _bean = new RequestContextBean();
                }
            }
        }

        return _bean;
    }

    private volatile RequestContextBean _bean;
}
