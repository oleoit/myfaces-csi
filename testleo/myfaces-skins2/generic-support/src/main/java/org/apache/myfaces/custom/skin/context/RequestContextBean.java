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
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

import org.apache.commons.digester.Digester;
import org.apache.myfaces.custom.skin.util.DateUtils;
import org.apache.myfaces.trinidad.context.AccessibilityProfile;
import org.apache.myfaces.trinidad.logging.SkinLogger;
import org.apache.myfaces.trinidadinternal.config.LazyValueExpression;
import org.apache.myfaces.trinidadinternal.util.nls.LocaleUtils;

public class RequestContextBean
{
    static private final SkinLogger _LOG = SkinLogger
            .createTrinidadLogger(RequestContextBean.class);

    /**
     * output-mode
     */
    private String _outputMode;

    /**
     * look-and-feel
     */
    private String _lookAndFeel;

    /**
     * accessibility-mode
     */
    private String _accessibilityMode;

    /**
     * accessibility-profile
     */
    private AccessibilityProfile _accessibilityProfile;

    /**
     * right-to-left
     */
    private Boolean _rightToLeft;

    /**
     * formatting-locale
     */
    private Locale _formattingLocale;

    /**
     * number-grouping-separator
     */
    private Character _numberGroupingSeparator;

    /**
     * decimal-separator
     */
    private Character _decimalSeparator;

    /**
     * currency-code
     */
    private String _currencyCode;

    /**
     * time-zone
     */
    private TimeZone _timezone;

    /**
     * skin-family
     */
    private String _skinFamily;

    /**
     * animation-enabled default true
     */
    private Boolean _animationEnabled;

    public static void addXmlRules(Digester digester)
    {
        String prefix = "trinidad-config";

        digester.addObjectCreate(prefix, RequestContextBean.class);
        digester.addBeanPropertySetter(prefix + "/output-mode", "outputMode");
        digester
                .addBeanPropertySetter(prefix + "/look-and-feel", "lookAndFeel");
        digester.addBeanPropertySetter(prefix + "/accessibility-mode",
                "accessibilityMode");

        digester
                .addBeanPropertySetter(prefix + "/right-to-left", "rightToLeft");
        digester.addBeanPropertySetter(prefix + "/number-grouping-separator",
                "numberGroupingSeparator");
        digester.addBeanPropertySetter(prefix + "/decimal-separator",
                "decimalSeparator");
        digester.addBeanPropertySetter(prefix + "/currency-code",
                "currencyCode");
        digester.addBeanPropertySetter(prefix + "/skin-family", "skinFamily");
        digester.addBeanPropertySetter(prefix + "/animation-enabled",
                "animationEnabled");

        digester.addBeanPropertySetter(prefix + "/accessibility-profile",
                "accessibilityProfile");
        digester.addBeanPropertySetter(prefix + "/formatting-locale",
                "formattingLocale");
        digester.addBeanPropertySetter(prefix + "/time-zone", "timeZone");
    }

    public String getOutputMode()
    {
        if (_outputMode != null)
            return _outputMode;
        ValueExpression expression = getValueExpression("outputMode");
        return expression != null ? getStringValue(getFacesContext(),
                expression) : null;
    }

    public void setOutputMode(String outputMode)
    {
        String currentText = outputMode.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, String.class);
            setValueExpression("outputMode", expression);
        }
        else
        {
            this._outputMode = outputMode;
        }
    }

    public String getLookAndFeel()
    {
        if (_lookAndFeel != null)
            return _lookAndFeel;
        ValueExpression expression = getValueExpression("lookAndFeel");
        return expression != null ? getStringValue(getFacesContext(),
                expression) : null;
    }

    public void setLookAndFeel(String lookAndFeel)
    {
        String currentText = lookAndFeel.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, String.class);
            setValueExpression("lookAndFeel", expression);
        }
        else
        {
            this._lookAndFeel = lookAndFeel;
        }
    }

    public String getAccessibilityMode()
    {
        if (_accessibilityMode != null)
            return _accessibilityMode;
        ValueExpression expression = getValueExpression("accessibilityMode");
        return expression != null ? getStringValue(getFacesContext(),
                expression) : null;
    }

    public void setAccessibilityMode(String accessibilityMode)
    {
        String currentText = accessibilityMode.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, String.class);
            setValueExpression("accessibilityMode", expression);
        }
        else
        {
            this._accessibilityMode = accessibilityMode;            
        }
    }

    public AccessibilityProfile getAccessibilityProfile()
    {
        if (_accessibilityProfile != null)
            return _accessibilityProfile;
        ValueExpression expression = getValueExpression("accessibilityProfile");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                return (AccessibilityProfile) value;
            }
        }
        return null;
    }

    public void setAccessibilityProfile(String accessibilityProfile)
    {
        String currentText = accessibilityProfile.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, AccessibilityProfile.class);
            setValueExpression("accessibilityProfile", expression);
        }
        else
        {
            AccessibilityProfile.ColorContrast colorContrast = null;
            AccessibilityProfile.FontSize fontSize = null;

            // Note: we do the parsing here in the ConfigParser instead of in 
            // RequestContextImpl so that we can easily detect/log any problems 
            // once at startup.  Also nice to do this here so that we have some 
            // chance of actually logging line numbers, though at the moment it 
            // looks like our Handler doesn't implement Locator.

            StringTokenizer tokens = new StringTokenizer(accessibilityProfile);
            while (tokens.hasMoreTokens())
            {
                String token = tokens.nextToken();

                if ("high-contrast".equals(token))
                {
                    colorContrast = AccessibilityProfile.ColorContrast.HIGH;
                }
                else if ("large-fonts".equals(token))
                {
                    fontSize = AccessibilityProfile.FontSize.LARGE;
                }
                else
                {
                    _LOG.warning("INVALID_ACC_PROFILE", new Object[] { token });
                }
            }
            this._accessibilityProfile = AccessibilityProfile.getInstance(
                    colorContrast, fontSize);
        }        
    }

    public Boolean getRightToLeft()
    {
        if (_rightToLeft != null)
            return _rightToLeft;
        ValueExpression expression = getValueExpression("rightToLeft");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                return (Boolean) value;
            }            
        }
        return Boolean.FALSE;
    }

    public void setRightToLeft(String rightToLeft)
    {
        String currentText = rightToLeft.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, Boolean.class);
            setValueExpression("rightToLeft", expression);
        }
        else
        {
            this._rightToLeft = Boolean.valueOf(rightToLeft);
        }
    }

    public Locale getFormattingLocale()
    {
        if (_formattingLocale != null)
            return _formattingLocale;
        ValueExpression expression = getValueExpression("formattingLocale");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                if (value instanceof Locale)
                {
                    return (Locale) value;
                }
                else
                {
                    return LocaleUtils.getLocaleForIANAString(
                            value.toString().replace('_', '-'));
                }
            }            
        }
        return null;
    }

    public void setFormattingLocale(String formattingLocale)
    {
        String currentText = formattingLocale.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, Locale.class);
            setValueExpression("formattingLocale", expression);
        }
        else
        {
            formattingLocale = formattingLocale.replace('_', '-');
            this._formattingLocale = LocaleUtils
                    .getLocaleForIANAString(formattingLocale);
        }
    }

    public Character getNumberGroupingSeparator()
    {
        if (_numberGroupingSeparator != null)
            return _numberGroupingSeparator;
        ValueExpression expression = getValueExpression("numberGroupingSeparator");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                return (Character) value;
            }            
        }
        return null;
    }

    public void setNumberGroupingSeparator(String numberGroupingSeparator)
    {
        String currentText = numberGroupingSeparator.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, Character.class);
            setValueExpression("numberGroupingSeparator", expression);
        }
        else
        {
            this._numberGroupingSeparator = numberGroupingSeparator.charAt(0);
        }
    }

    public Character getDecimalSeparator()
    {
        if (_decimalSeparator != null)
            return _decimalSeparator;
        ValueExpression expression = getValueExpression("decimalSeparator");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                return (Character) value;
            }            
        }
        return null;
    }

    public void setDecimalSeparator(String decimalSeparator)
    {
        String currentText = decimalSeparator.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, Character.class);
            setValueExpression("decimalSeparator", expression);
        }
        else
        {
            this._decimalSeparator = decimalSeparator.charAt(0);
        }
    }

    public String getCurrencyCode()
    {
        if (_currencyCode != null)
            return _currencyCode;
        ValueExpression expression = getValueExpression("currencyCode");
        return expression != null ? getStringValue(getFacesContext(),
                expression) : null;
    }

    public void setCurrencyCode(String currencyCode)
    {
        String currentText = currencyCode.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, String.class);
            setValueExpression("currencyCode", expression);
        }
        else
        {
            this._currencyCode = currencyCode;
        }
    }

    public TimeZone getTimezone()
    {
        if (_timezone != null)
            return _timezone;
        ValueExpression expression = getValueExpression("timezone");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                if (value instanceof TimeZone)
                {
                    return DateUtils.getSupportedTimeZone(value.toString());
                }
                else
                {
                    return (TimeZone) value;
                }
            }            
        }
        return null;
    }

    public void setTimezone(String timezone)
    {
        String currentText = timezone.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, TimeZone.class);
            setValueExpression("timezone", expression);
        }
        else
        {
            this._timezone = DateUtils.getSupportedTimeZone(timezone);
            if (this._timezone == null)
            {
                _LOG.warning("INVALID_TIMEZONE_IN_CONFIG", timezone);
            }
        }
        
    }

    public String getSkinFamily()
    {
        if (_skinFamily != null)
            return _skinFamily;
        ValueExpression expression = getValueExpression("skinFamily");
        return expression != null ? getStringValue(getFacesContext(),
                expression) : null;
    }

    public void setSkinFamily(String skinFamily)
    {
        String currentText = skinFamily.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, String.class);
            setValueExpression("skinFamily", expression);
        }
        else
        {
            this._skinFamily = skinFamily;
        }
    }

    public Boolean getAnimationEnabled()
    {
        if (_animationEnabled != null)
            return _animationEnabled;
        ValueExpression expression = getValueExpression("animationEnabled");
        if (expression != null)
        {
            Object value = expression.getValue(getFacesContext().getELContext());
            if (value != null)
            {
                return (Boolean) value;
            }            
        }
        return Boolean.TRUE;
    }

    public void setAnimationEnabled(String animationEnabled)
    {
        String currentText = animationEnabled.trim();
        if (isValueExpression(currentText))
        {
            ValueExpression expression = LazyValueExpression
                    .createValueExpression(currentText, Boolean.class);
            setValueExpression("animationEnabled", expression);
        }
        else
        {
            this._animationEnabled = Boolean.valueOf(animationEnabled);
        }
    }

    // --------------------- borrowed from UIComponentBase ------------

    private Map<String , ValueExpression> _valueExpressionMap = null;

    public ValueExpression getValueExpression(String name)
    {
        if (name == null)
            throw new NullPointerException("name");
        if (_valueExpressionMap == null)
        {
            return null;
        }
        else
        {
            return (ValueExpression) _valueExpressionMap.get(name);
        }
    }

    public void setValueExpression(String name, ValueExpression binding)
    {
        if (name == null)
            throw new NullPointerException("name");
        if (_valueExpressionMap == null)
        {
            _valueExpressionMap = new HashMap<String , ValueExpression>();
        }
        _valueExpressionMap.put(name, binding);
    }

    protected FacesContext getFacesContext()
    {
        return FacesContext.getCurrentInstance();
    }

    protected String getStringValue(FacesContext context, ValueExpression vb)
    {
        Object value = vb.getValue(context.getELContext());
        if (value != null)
        {
            return value.toString();
        }
        return null;
    }

    protected boolean isValueExpression(String currentText)
    {
        if (currentText.startsWith("#{") && currentText.endsWith("}"))
        {
            return true;
        }
        return false;
    }
}
