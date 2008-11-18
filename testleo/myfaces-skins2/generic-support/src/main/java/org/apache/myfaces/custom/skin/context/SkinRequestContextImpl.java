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
package org.apache.myfaces.custom.skin.context;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.myfaces.commons.util.ExternalContextUtils;
import org.apache.myfaces.trinidad.context.AccessibilityProfile;
import org.apache.myfaces.trinidad.context.Agent;
import org.apache.myfaces.trinidad.context.SkinRequestContext;
import org.apache.myfaces.trinidadinternal.agent.AgentFactory;
import org.apache.myfaces.trinidadinternal.agent.AgentFactoryImpl;
import org.apache.myfaces.trinidadinternal.agent.TrinidadAgentImpl;
import org.apache.myfaces.trinidadinternal.renderkit.core.xhtml.XhtmlConstants;
import org.apache.myfaces.trinidadinternal.util.nls.LocaleUtils;

public class SkinRequestContextImpl extends SkinRequestContext
{
    public SkinRequestContextImpl(RequestContextBean bean)
    {
        _bean = bean;
    }

    public void init(ExternalContext request)
    {
        attach();
    }

    @Override
    public Accessibility getAccessibilityMode()
    {
        return _ACCESSIBILITY_NAMES.get(_bean.getAccessibilityMode());
    }

    @Override
    public AccessibilityProfile getAccessibilityProfile()
    {
        return _bean.getAccessibilityProfile();
    }

    @Override
    public Agent getAgent()
    {
        if (_agent == null)
        {
          Agent agent = _agentFactory.createAgent(FacesContext.getCurrentInstance());
          // =-=AEW In theory, this does not need to be a TrinidadAgent
          // That should only be necessary once we get to rendering...
          // However, we're gonna have to turn it into one when it comes
          // to rendering time, and our RenderingContext isn't doing this
          // today
          TrinidadAgentImpl fAgent = new TrinidadAgentImpl(FacesContext.getCurrentInstance(),agent);
          _agent = fAgent;
        }

        return _agent;
    }

    @Override
    public char getDecimalSeparator()
    {

        char c = toChar(_bean.getDecimalSeparator());
        if (c != CHAR_UNDEFINED)
            return c;

        return (char) 0;
    }

    static private final char CHAR_UNDEFINED = (char) -1;

    static private char toChar(Object o)
    {
        if (o == null)
            return CHAR_UNDEFINED;

        char c;
        if (o instanceof Character)
        {
            c = ((Character) o).charValue();
        }
        else
        {
            // If it's not a Character object, then let's turn it into
            // a CharSequence and grab the first character
            CharSequence cs;
            if (o instanceof CharSequence)
            {
                cs = (CharSequence) o;
            }
            else
            {
                cs = o.toString();
            }

            if (cs.length() == 0)
                c = CHAR_UNDEFINED;
            else
                c = cs.charAt(0);
        }

        // Handle the occasional odd bit of code that likes
        // returning null, and treat it identically to UNDEFINED.
        if (c == '\u0000')
            c = CHAR_UNDEFINED;

        return c;
    }

    @Override
    public Locale getFormattingLocale()
    {
        Object o = _bean.getFormattingLocale();
        if (o == null)
            return null;

        if (o instanceof Locale)
            return (Locale) o;

        // Don't know how this would ever get here.  ConfigParser should have set the key if
        // formatting-locale was specified, or it is null.
        if (o instanceof String)
            o = ((String) o).replace('_', '-');
        return LocaleUtils.getLocaleForIANAString(o.toString());
    }

    @Override
    public char getNumberGroupingSeparator()
    {
        Object property = _bean.getNumberGroupingSeparator();
        char c = toChar(property);
        if (c != CHAR_UNDEFINED)
            return c;

        return (char) 0;
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
        
        if(ExternalContextUtils.getRequestType(FacesContext.getCurrentInstance().getExternalContext()).isPortlet())
        {
          return XhtmlConstants.OUTPUT_MODE_PORTLET;
        }
        return (String) _bean.getOutputMode();
    }

    @Override
    public String getSkinFamily()
    {
        return _bean.getSkinFamily();
    }

    @Override
    public TimeZone getTimeZone()
    {
        TimeZone tz = (TimeZone) _bean.getTimezone();
        if (tz != null)
          return tz;

        // see bug 4960813
        return TimeZone.getDefault();
        // see bug 4960813. we can't guess the timezone in javascript:

//        UIXCookie cookie = _getUIXCookie();
//        if (cookie != null)
//        {
//          if (cookie.getTimeZone() != null)
//            return cookie.getTimeZone();
//        }

//        return null;
    }

    @Override
    public boolean isAnimationEnabled()
    {
        return _bean.getAnimationEnabled().booleanValue();
    }

    @Override
    public boolean isRightToLeft()
    {
        Boolean b = (Boolean) _bean.getRightToLeft();
        if (b != null)
          return b.booleanValue();

        FacesContext fContext = FacesContext.getCurrentInstance();
        if ((fContext != null) && (fContext.getViewRoot() != null))
        {
          Locale locale = fContext.getViewRoot().getLocale();
          return (LocaleUtils.getReadingDirectionForLocale(locale) ==
                  LocaleUtils.DIRECTION_RIGHTTOLEFT);
        }

        return false;
    }
    
    private RequestContextBean _bean;

    private Agent               _agent;
    
    //todo: get factory from configuration (else implementations have to provide their own RequestContext)
    static private final AgentFactory _agentFactory = new AgentFactoryImpl();
    
    // A mapping from string names (as used in the config file)
    // to accessibility objects
    static private final Map<String, Accessibility> _ACCESSIBILITY_NAMES = new HashMap<String, Accessibility>();

    static
    {
        _ACCESSIBILITY_NAMES.put("default", Accessibility.DEFAULT);
        _ACCESSIBILITY_NAMES.put("inaccessible", Accessibility.INACCESSIBLE);
        _ACCESSIBILITY_NAMES.put("screenReader", Accessibility.SCREEN_READER);
    }
}
