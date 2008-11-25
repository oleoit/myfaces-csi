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
package org.apache.myfaces.custom.skin.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.renderkit.html.util.ResourceLoader;
import org.apache.myfaces.trinidad.util.URLUtils;
import org.apache.myfaces.trinidadinternal.share.config.SkinConfiguration;

/**
 * This class handle resource created on temp directory.
 * 
 * @author Leonardo Uribe
 *
 */
public class SkinResourceLoader implements ResourceLoader
{
    private static final int _BUFFER_SIZE = 2048;

    /**
     * Context parameter for activating debug mode, which will disable
     * caching.
     */
    public static final String DEBUG_INIT_PARAM = "org.apache.myfaces.trinidad.resource.DEBUG";

    private Boolean _debug = null;

    // One year in milliseconds.  (Actually, just short of on year, since
    // RFC 2616 says Expires should not be more than one year out, so
    // cutting back just to be safe.)
    public static final long ONE_YEAR_MILLIS = 31363200000L;

    public void serveResource(ServletContext context,
            HttpServletRequest request, HttpServletResponse response,
            String resourceUri) throws IOException
    {
        _initDebug(context);
        
        File tempdir = (File) context
                .getAttribute("javax.servlet.context.tempdir");

        URL url = findResource(tempdir, SkinConfiguration.getStylesCacheDir(context) + resourceUri);

        // Make sure the resource is available
        if (url == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Stream the resource contents to the servlet response
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(false);

        _setHeaders(context, connection, response);

        InputStream in = connection.getInputStream();
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[_BUFFER_SIZE];

        try
        {
            _pipeBytes(in, out, buffer);
        }
        finally
        {
            try
            {
                in.close();
            }
            finally
            {
                out.close();
            }
        }

    }

    protected URL findResource(File directory, String path) throws IOException
    {
        if (path.charAt(0) == '/')
            path = path.substring(1);

        // construct the relative file under the "root" directory
        File file = new File(directory, path).getCanonicalFile();

        // file path should contain the "root" directory path, not be outside it
        boolean isContained = file.getCanonicalPath().startsWith(
                directory.getCanonicalPath());

        // return null if relative paths were used, 
        // or if the file does not exist,
        // otherwise return an URL to the file resource
        // 2006-08-01: -= Simon Lessard =-
        //             File.toURL is deprecated in JDK 6.0 because the method 
        //             does not escape invalid characters, toURI().toURL is the 
        //             preferred way as of JDK 6.0.
        return (isContained && file.exists()) ? file.toURI().toURL() : null;
    }

    /**
     * Initialize whether resource debug mode is enabled.
     */
    private void _initDebug(ServletContext context)
    {
        if (_debug == null){
            String debug = context.getInitParameter(
                    DEBUG_INIT_PARAM);
            _debug = "true".equalsIgnoreCase(debug);
        }
    }

    /**
     * Reads the specified input stream into the provided byte array storage and
     * writes it to the output stream.
     */
    private static void _pipeBytes(InputStream in, OutputStream out,
            byte[] buffer) throws IOException
    {
        int length;

        while ((length = (in.read(buffer))) >= 0)
        {
            out.write(buffer, 0, length);
        }
    }

    /**
     * Sets HTTP headers on the response which tell
     * the browser to cache the resource indefinitely.
     */
    private void _setHeaders(ServletContext context, URLConnection connection,
            HttpServletResponse response)
    {
        String contentType = connection.getContentType();
        if (contentType == null || "content/unknown".equals(contentType))
        {
            URL url = connection.getURL();
            String resourcePath = url.getPath();
            if (resourcePath.endsWith(".css"))
                contentType = "text/css";
            else if (resourcePath.endsWith(".js"))
                contentType = "application/x-javascript";
            else
                contentType = context.getMimeType(resourcePath);
        }
        response.setContentType(contentType);

        int contentLength = connection.getContentLength();
        if (contentLength >= 0)
            response.setContentLength(contentLength);

        long lastModified;
        try
        {
            lastModified = URLUtils.getLastModified(connection);
        }
        catch (IOException exception)
        {
            lastModified = -1;
        }

        if (lastModified >= 0)
            response.setDateHeader("Last-Modified", lastModified);

        // If we're not in debug mode, set cache headers
        if (!_debug)
        {
            // We set two headers: Cache-Control and Expires.
            // This combination lets browsers know that it is
            // okay to cache the resource indefinitely.

            // Set Cache-Control to "Public".
            response.setHeader("Cache-Control", "Public");

            // Set Expires to current time + one year.
            long currentTime = System.currentTimeMillis();

            response.setDateHeader("Expires", currentTime + ONE_YEAR_MILLIS);
        }
    }
}
