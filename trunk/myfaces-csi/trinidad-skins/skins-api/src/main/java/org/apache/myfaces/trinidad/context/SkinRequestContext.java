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
package org.apache.myfaces.trinidad.context;

import java.awt.Color;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.myfaces.trinidad.logging.TrinidadLogger;

/**
 * Context class for all per-request and per-webapp information
 * required by Trinidad.  A <code>RequestContext</code> object can be
 * retrieved with the static {@link #getCurrentInstance} method.
 * There is one and only one <code>RequestContext</code> object
 * active in any one thread.
 * <p>
 * This class does not extend <code>FacesContext</code>;  this is intentional,
 * as extending <code>FacesContext</code> requires taking over the
 * <code>FacesContextFactory</code>.
 * <p>
 */
// TODO Refactor this class after everything gets added to it.
// TODO There's some values in here that seem to affect only output (e.g.,
//  right-to-left); that's not great, since ideally that detail would be
//  buried in something more renderer-specific.
abstract public class SkinRequestContext
{
  /**
   * Name of the EL implicit variable ("requestContext") that is used to
   * expose this context object.
   */
  static public final String VARIABLE_NAME =
    "requestContext";

  // Omitted APIs:
  //
  // LocaleContext
  // =============
  // Locale getTranslationLocale
  //
  // DateFormatContext:
  // =================
  // int getTwoDigitYearStart
  // boolean isLenient (very lame API, definitely gone)


  /**
   * Retrieves the RequestContext active for the current thread.
   */
  static public SkinRequestContext getCurrentInstance()
  {
    return _CURRENT_CONTEXT.get();
  }

  

  /**
   * Creates an RequestContext.  RequestContext is abstract
   * and may not be instantiated directly.
   * @see SkinRequestContextFactory
   */
  protected SkinRequestContext()
  {
  }

  /**
   * Returns the "output mode" - printable, etc.
   */
  public abstract String getOutputMode();

  /**
   * Returns the name of the preferred skin family.
   */
  public abstract String getSkinFamily();

  //
  //  General localization
  //

  /**
   * Returns true if the user should be shown output in right-to-left.
   */
  public abstract boolean isRightToLeft();

  /**
   * Returns the formatting locale.  Converters without an explicit locale
   * should use this to format values.  If not set, converters should
   * default to the value of FacesContext.getViewRoot().getLocale().
   * This will, by default, simply return null.
   */
  public abstract Locale getFormattingLocale();

  /**
   * Returns the Agent information for the current context
   */
  public abstract Agent getAgent();

  /**
   * Releases the RequestContext object.  This method must only
   * be called by the code that created the RequestContext.
   * @exception IllegalStateException if no RequestContext is attached
   * to the thread, or the attached context is not this object
   */
  public void release()
  {
    if (_LOG.isFinest())
    {
      _LOG.finest("RequestContext released.", 
                  new RuntimeException("This is not an error. This trace is for debugging."));
    }
    
    Object o = _CURRENT_CONTEXT.get();
    if (o == null)
      throw new IllegalStateException(
              _addHelp("RequestContext was already released or " +
                       "had never been attached."));
    if (o != this)
      throw new IllegalStateException(_LOG.getMessage(
        "RELEASE_DIFFERENT_REQUESTCONTEXT_THAN_CURRENT_ONE"));
    
    _CURRENT_CONTEXT.remove();
  }

  /**
   * Attaches a RequestContext to the current thread.  This method 
   * should only be called by a RequestContext object itself.
   * @exception IllegalStateException if an RequestContext is already
   * attached to the thread
   */
  public void attach()
  {
    if (_LOG.isFinest())
    {
      _LOG.finest("RequestContext attached.", 
                  new RuntimeException(_LOG.getMessage(
                    "DEBUGGING_TRACE_NOT_ERROR")));
    }

    Object o = _CURRENT_CONTEXT.get();
    if (o != null)
    {
      throw new IllegalStateException(
              _addHelp("Trying to attach RequestContext to a " +
                       "thread that already had one."));
    }
    _CURRENT_CONTEXT.set(this);
  }
  
  private static String _addHelp(String error)
  {
    if (!_LOG.isFinest())
    {
      error += " To enable stack traces of each RequestContext attach/release call," +
        " enable Level.FINEST logging for the "+SkinRequestContext.class;
    }
    return error;
  }

  static private final ThreadLocal<SkinRequestContext> _CURRENT_CONTEXT = 
    new ThreadLocal<SkinRequestContext>();
  static private final TrinidadLogger _LOG =
    TrinidadLogger.createTrinidadLogger(SkinRequestContext.class);
}
