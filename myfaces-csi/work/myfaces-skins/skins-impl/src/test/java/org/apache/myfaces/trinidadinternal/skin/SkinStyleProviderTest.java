package org.apache.myfaces.trinidadinternal.skin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.myfaces.trinidad.test.MockTestExternalContext;
import org.apache.myfaces.trinidadinternal.config.GlobalConfiguratorImpl;
import org.apache.myfaces.trinidadinternal.renderkit.core.SkinRenderingContext;
import org.apache.myfaces.trinidadinternal.renderkit.core.StyleContext;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;
import org.apache.shale.test.base.AbstractJsfTestCase;
import org.apache.shale.test.mock.MockServletContext;

public class SkinStyleProviderTest extends AbstractJsfTestCase
{

    public SkinStyleProviderTest(String name)
    {
        super(name);
    }
    
    public static Test suite()
    {
        return new TestSuite(SkinStyleProviderTest.class);
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
    
    public void testGetStyleSheetURIs()
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        instance.beginRequest(externalContext);
        
        SkinRenderingContext skinCtx = new SkinRenderingContext();
        StyleContext sCtx = skinCtx.getStyleContext();
        StyleProvider provider = sCtx.getStyleProvider();
        
        List<String> uris = provider.getStyleSheetURIs(sCtx);
        assertNotNull(uris);
    }
}
