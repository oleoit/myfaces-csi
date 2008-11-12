package org.apache.myfaces.trinidad.test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shale.test.mock.MockExternalContext;

public class MockTestExternalContext extends MockExternalContext
{
    ExternalContext wrapped;
    private Map requestHeaderMap;
    
    public MockTestExternalContext(ServletContext context,
            HttpServletRequest request, HttpServletResponse response)
    {
        super(context, request, response);
        requestHeaderMap = new HashMap();
    }

    public MockTestExternalContext(ExternalContext extCtx, ServletContext context,
            HttpServletRequest request, HttpServletResponse response)
    {
        this(context, request, response);
        wrapped = extCtx;
    }
    
    @Override
    public InputStream getResourceAsStream(String path)
    {
        if(path.startsWith("/"))
        {
            path = path.substring(1);
        }
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
    }
    
    /** {@inheritDoc} */
    public Map getRequestHeaderMap() {

        return requestHeaderMap;

    }
    
    @Override
    public String getRequestContextPath()
    {
        return "/contextpath";
    }
}
