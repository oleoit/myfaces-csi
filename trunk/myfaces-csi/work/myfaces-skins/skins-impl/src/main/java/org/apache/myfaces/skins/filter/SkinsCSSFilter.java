package org.apache.myfaces.skins.filter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SkinsCSSFilter implements Filter
{
    private static final String SERVLET_CONTEXT_TEMP_DIR = "javax.servlet.context.tempdir";
    private static final String CSS_CACHE_DIR = "/adf/styles/cache/";
    private FilterConfig filterConfig = null;
    
    public void init(FilterConfig filterConfig) throws ServletException
    {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain arg2) throws IOException, ServletException
    {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        
        String path = getAbsolutePath(httpRequest);
        serveResource(path, httpResponse);
    }
    
    private String getAbsolutePath(HttpServletRequest request)
    {
        String uri = request.getServletPath();
        String filename = uri.substring(uri.lastIndexOf('/')+1); 
        
        File file = (File)filterConfig.getServletContext().getAttribute(SERVLET_CONTEXT_TEMP_DIR);
        if(file != null && file.exists())
        {
            String absPath = file.getAbsolutePath() + CSS_CACHE_DIR + filename;
            return absPath;
        }
        
        return null;
    }
    
    private void serveResource(String filePath, HttpServletResponse response) throws IOException
    {
        File file = new File(filePath);
        
        FileInputStream in = new FileInputStream(file);
        ServletOutputStream out = response.getOutputStream();
        
        byte[] buffer = new byte[1024];
        for (int size = in.read(buffer); size != -1; size = in.read(buffer))
        {
            out.write(buffer, 0, size);
        }
        out.flush();
        in.close();
    }
    
    public void destroy()
    {
        // TODO Auto-generated method stub
    }
}
