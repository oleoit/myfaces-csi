package org.apache.myfaces.trinidadinternal.config;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.apache.shale.test.base.AbstractJsfTestCase;

public class GlobalConfiguratorImplTest extends AbstractJsfTestCase
{

    public GlobalConfiguratorImplTest(String name)
    {
        super(name);
    }

    public static Test suite()
    {
        return new TestSuite(GlobalConfiguratorImplTest.class);
    }
    
    public void testGetInstance()
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        assertNotNull(instance);
    }
    
    public void testBeginRequest()
    {
        GlobalConfiguratorImpl instance = GlobalConfiguratorImpl.getInstance();
        instance.beginRequest(externalContext);
    }
}
