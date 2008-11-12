package org.apache.myfaces.trinidadinternal.renderkit.core;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.trinidad.test.MockTestExternalContext;
import org.apache.myfaces.trinidadinternal.config.GlobalConfiguratorImpl;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockServletContext;

public class SkinRenderingContextTest extends AbstractJsfTestCase
{
    
    public SkinRenderingContextTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(SkinRenderingContextTest.class);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        java.beans.Beans.setDesignTime(true);
        
        MockServletContext ctx = (MockServletContext)facesContext.getExternalContext().getContext();

        MockTestExternalContext mExtCtx = new MockTestExternalContext(
                ctx, 
                (HttpServletRequest)facesContext.getExternalContext().getRequest(), 
                (HttpServletResponse)facesContext.getExternalContext().getResponse());
        
        facesContext.setExternalContext(mExtCtx);
        externalContext = mExtCtx;
    }
    
    public void testGetStyleContext()
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        instance.beginRequest(externalContext);
        
        SkinRenderingContext skinCtx = new SkinRenderingContext();
        StyleContext styleCtx = skinCtx.getStyleContext();
        assertNotNull(styleCtx);
        
        String path = styleCtx.getGeneratedFilesPath();
        assertTrue(path != null && path.length() > 0);
    }
    
    public void testGetSkinFamily()
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        instance.beginRequest(externalContext);
        
        SkinRenderingContext skinCtx = new SkinRenderingContext();
        assertNotNull(skinCtx.getSkin());
    }
}
