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
package org.apache.myfaces.custom.skin.config;

import java.io.IOException;
import java.io.InputStream;

import javax.faces.context.ExternalContext;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.custom.skin.context.RequestContextBean;
import org.apache.myfaces.trinidad.logging.SkinLogger;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 */
public class ConfigParser
{
    /**
     *
     */
    static public RequestContextBean parseConfigFile(
            ExternalContext externalContext)
    {
        RequestContextBean bean = new RequestContextBean();

        InputStream in = externalContext.getResourceAsStream(_SKINS_CONFIG_FILE);
        if (in == null)
        {
            in = externalContext.getResourceAsStream(_TRINIDAD_CONFIG_FILE);    
        }
        
        if (in != null)
        {
            try
            {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                factory.setNamespaceAware(true);
                
                Digester digester = new Digester(factory.newSAXParser());
                digester.setValidating(false);
                digester.setNamespaceAware(true);
                digester.setUseContextClassLoader(true);
                RequestContextBean.addXmlRules(digester);

                InputSource input = new InputSource();
                input.setByteStream(in);
                input.setPublicId(_SKINS_CONFIG_FILE);
                digester.parse(input);
                bean = (RequestContextBean) digester.getRoot();
            }
            catch (IOException ioe)
            {
                _LOG.warning(ioe);
            }
            catch (ParserConfigurationException pce)
            {
              _LOG.warning(pce);
            }
            catch (SAXException saxe)
            {
                _LOG.warning(saxe);
            }
            finally
            {
                try
                {
                    in.close();
                }
                catch (IOException ioe)
                {
                    // Ignore
                    ;
                }
            }
        }

        return bean;
    }

    static private final String _SKINS_CONFIG_FILE = "/WEB-INF/skins-config.xml";
    static private final String _TRINIDAD_CONFIG_FILE = "/WEB-INF/trinidad-config.xml";
    static private final SkinLogger _LOG = SkinLogger
            .createTrinidadLogger(ConfigParser.class);
}
