package org.apache.myfaces.custom.skin.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Calendar;

import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.XhtmlConstants;

/**
 * This is an implementation of StyleSheetLoader that caches the stylesheet
 * into the web browser.
 */
public class CachingStyleSheetLoader implements StyleSheetLoader
{
    private static final String STYLE_TYPE_TEXT_CSS = "text/css";
    private Log log = LogFactory.getLog(CachingStyleSheetLoader.class);
    
    /**
     * Loads the stylesheet only if the file has been modified or
     * the cache has expired.
     */
    public void loadStyleSheet(HttpServletRequest req, HttpServletResponse resp, ServletConfig config)
            throws IOException
    {
        File file = getFile(req, config);
        if(file != null && file.exists())
        {
            if(isFileModified(file, req))
            {
                long lastModified = file.lastModified();
                defineCaching(req, resp, lastModified);
                writeFile(file, resp);
            }
            else
            {
                resp.setStatus(HttpURLConnection.HTTP_NOT_MODIFIED);
                return;
            }
        }
        else
        {
            String requestURI = req.getRequestURI();
            String filename = requestURI.substring(requestURI.lastIndexOf("/") + 1);
            log.error("The resource " + filename + " was not found.");
            
            resp.setStatus(HttpURLConnection.HTTP_NOT_FOUND, filename);
            return;
        }
    }

    /**
     * Retrieves the stylesheet file from the server's temporary directory.
     * @param request
     * @param config
     * @return Returns the stylesheet file.
     */
    private File getFile(HttpServletRequest request, ServletConfig config)
    {
        String uri = request.getRequestURI();
        String filename = uri.substring(uri.lastIndexOf('/')+1); 
        
        File file = null;
        File tempDir = (File)config.getServletContext().getAttribute("javax.servlet.context.tempdir");
        if(tempDir != null && tempDir.exists())
        {
            String absPath = tempDir.getAbsolutePath() + XhtmlConstants.STYLES_CACHE_DIRECTORY + filename;
            file = new File(absPath);
        }
        
        return file;
    }
    
    /**
     * Output http headers telling the browser (and possibly intermediate caches) how
     * to cache this data.
     * <p>
     * The expiry time in this header info is set to 7 days. This is not a problem as
     * the overall URI contains a "cache key" that changes whenever the webapp is
     * redeployed (see AddResource.getCacheKey), meaning that all browsers will
     * effectively reload files on webapp redeploy.
     */
    protected void defineCaching(HttpServletRequest request, HttpServletResponse response, long lastModified)
    {
        response.setDateHeader("Last-Modified", lastModified);

        Calendar expires = Calendar.getInstance();
        expires.add(Calendar.DAY_OF_YEAR, 7);
        response.setDateHeader("Expires", expires.getTimeInMillis());        

        //12 hours: 43200 = 60s * 60 * 12
        response.setHeader("Cache-Control", "max-age=43200");
        response.setHeader("Pragma", "");
    }
    
    private boolean isFileModified(File file, HttpServletRequest request)
    {
        long lastModified = file.lastModified();
        long browserDate = request.getDateHeader("If-Modified-Since");
        
        // normalize to seconds - this should work with any os
        lastModified = (lastModified / 1000) * 1000;
        browserDate = (browserDate / 1000) * 1000;

        if (lastModified == browserDate)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    /**
     * Sends the file into the OutputStream of the response. 
     * @param file The stylesheet file.
     * @param response
     * @throws IOException
     */
    private void writeFile(File file, HttpServletResponse response) throws IOException
    {
        response.setContentType(STYLE_TYPE_TEXT_CSS);
        
        InputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        
        byte[] buffer = new byte[1024];
        for (int size = in.read(buffer); size != -1; size = in.read(buffer))
        {
            out.write(buffer, 0, size);
        }
        out.flush();
        in.close();
    }
}
