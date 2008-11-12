package org.apache.myfaces.trinidadinternal.renderkit.core;

import org.apache.myfaces.trinidad.context.AccessibilityProfile;
import org.apache.myfaces.trinidad.context.LocaleContext;
import org.apache.myfaces.trinidadinternal.agent.TrinidadAgent;
import org.apache.myfaces.trinidadinternal.style.StyleProvider;

public interface StyleContext
{
    /**
     * Returns the end user's locale.
     */
    public LocaleContext getLocaleContext();

    /**
     * Returns the end user's Agent.
     */
    public TrinidadAgent getAgent();

    public String getGeneratedFilesPath();
    public boolean checkStylesModified();

    public boolean disableStandardsMode();

    public StyleProvider getStyleProvider();
    public StyleProvider getStyleProvider(boolean recompute);
//    public StyleMap getStyleMap();
    public AccessibilityProfile getAccessibilityProfile();
}
