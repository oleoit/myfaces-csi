package org.apache.myfaces.trinidadinternal.renderkit.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.trinidad.test.MockTestExternalContext;
import org.apache.myfaces.trinidadinternal.config.GlobalConfiguratorImpl;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockServletContext;

public class StyleContextTest extends AbstractJsfTestCase
{

    public StyleContextTest(String name)
    {
        super(name);
    }
    
    public static Test suite()
    {
        return new TestSuite(StyleContextTest.class);
    }
    
    @Override
    protected void setUp() throws Exception
    {
        super.setUp();
        
        MockServletContext ctx = (MockServletContext)facesContext.getExternalContext().getContext();

        MockTestExternalContext mExtCtx = new MockTestExternalContext(
                ctx, 
                (HttpServletRequest)facesContext.getExternalContext().getRequest(), 
                (HttpServletResponse)facesContext.getExternalContext().getResponse());
        
        facesContext.setExternalContext(mExtCtx);
        externalContext = mExtCtx;
    }

    public void testInit()
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        instance.beginRequest(externalContext);
        
        SkinRenderingContext skinCtx = new SkinRenderingContext();
        StyleContext sCtx = skinCtx.getStyleContext();
        StyleProvider provider = sCtx.getStyleProvider();
        
        assertNotNull(provider);
    }
}
