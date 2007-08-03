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
package org.apache.myfaces.trinidadinternal.context;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.SkinRequestContext;
import org.apache.myfaces.trinidad.logging.TrinidadLogger;
import org.apache.myfaces.trinidad.util.ClassLoaderUtils;
import org.apache.myfaces.trinidadinternal.agent.AgentFactory;
import org.apache.myfaces.trinidadinternal.agent.AgentFactoryImpl;
import org.apache.myfaces.trinidadinternal.agent.TrinidadAgentImpl;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.XhtmlConstants;
import org.apache.myfaces.trinidadinternal.util.ExternalContextUtils;
import org.apache.myfaces.trinidadinternal.util.nls.LocaleUtils;

import com.sun.xml.internal.ws.client.RequestContext;

/**
 */
public class RequestContextImpl extends SkinRequestContext
{
  static public final String LAUNCH_PARAMETERS =
    "org.apache.myfaces.trinidad.PageFlowSourceParameters";

  static public final String LAUNCH_VIEW =
    "org.apache.myfaces.trinidad.PageFlowSourceView";


  public RequestContextImpl()
  {
    _partialTargets = new HashSet<String>();
  }

  public void init(ExternalContext request)
  {
    attach();
  }


  @Override
  public String getOutputMode()
  {
    //=-= Scott O'Bryan =-=
    // FIXME: Not real happy with this.  We should find a way to get this into
    // the bean.  The bean is cached by the RequestContextFactory, and the 
    // Portlet mode needs to be assigned per request since it's possible to run
    // a trinidad application from a servlet container and a portlet container
    // at the same time.  For now?  Hey, it works.
    
    if(ExternalContextUtils.isPortlet(__getFacesContext().getExternalContext()))
    {
      return XhtmlConstants.OUTPUT_MODE_PORTLET;
    }
    return (String) _bean.getProperty(RequestContextBean.OUTPUT_MODE_KEY);
  }

  @Override
  public String getSkinFamily()
  {
    return (String) _bean.getProperty(RequestContextBean.SKIN_FAMILY_KEY);
  }

  @Override
  public boolean isRightToLeft()
  {
    Boolean b = (Boolean) _bean.getProperty(RequestContextBean.RIGHT_TO_LEFT_KEY);
    if (b != null)
      return b.booleanValue();

    FacesContext fContext = __getFacesContext();
    if ((fContext != null) && (fContext.getViewRoot() != null))
    {
      Locale locale = fContext.getViewRoot().getLocale();
      return (LocaleUtils.getReadingDirectionForLocale(locale) ==
              LocaleUtils.DIRECTION_RIGHTTOLEFT);
    }

    return false;
  }

  @Override
  public Locale getFormattingLocale()
  {
    Object o = _bean.getProperty(RequestContextBean.FORMATTING_LOCALE_KEY);
    if (o == null)
      return null;

    if (o instanceof Locale)
      return (Locale) o;
    
    return LocaleUtils.getLocaleForIANAString(o.toString());
  }

  @Override
  public Agent getAgent()
  {
    if (_agent == null)
    {
      Agent agent = _agentFactory.createAgent(__getFacesContext());
      // =-=AEW In theory, this does not need to be a TrinidadAgent
      // That should only be necessary once we get to rendering...
      // However, we're gonna have to turn it into one when it comes
      // to rendering time, and our RenderingContext isn't doing this
      // today
      TrinidadAgentImpl fAgent = new TrinidadAgentImpl(__getFacesContext(),agent);
      _agent = fAgent;
    }

    return _agent;
  }

  //
  // Get the FacesContext.  We used to cache the instance, but
  // in some circumstances the RequestContext was getting reused
  // across multiple FacesServlet invocations, so the caching
  // was more trouble than it was worth.  Re-enable some sort
  // of caching if it proves a performance issue.
  //
  FacesContext __getFacesContext()
  {
    FacesContext fContext = FacesContext.getCurrentInstance();
    // If we haven't hit the FacesServlet yet, then the
    // FacesContext won't be available yet - so go
    // to the filter and ask for a pseudo-FacesContext
    if (fContext == null)
    {
      //SKINFIX:
      //fContext =  TrinidadFilterImpl.getPseudoFacesContext();
    }

    return fContext;
  }
  
  private Map<UIComponent, Set<UIComponent>> _partialListeners;
  private Set<String>         _partialTargets;
  private Agent               _agent;

  //todo: get factory from configuration (else implementations have to provide their own RequestContext)
  static private final AgentFactory _agentFactory = new AgentFactoryImpl();

  // static private final Object _GLOBAL_TRIGGER = new Object();
  static private final int    _DEFAULT_PAGE_FLOW_SCOPE_LIFETIME = 15;
  static private final String _CHANGE_MANAGER_KEY =
    "org.apache.myfaces.trinidadinternal.ChangeManager";
  static private final String _CHANGE_PERSISTENCE_INIT_PARAM =
    "org.apache.myfaces.trinidad.CHANGE_PERSISTENCE";

  // A mapping from string names (as used in the config file)
  // to accessibility objects

  static private final TrinidadLogger _LOG =
    TrinidadLogger.createTrinidadLogger(RequestContextImpl.class);
}
