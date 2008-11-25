package org.apache.myfaces.custom.skin.webapp;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StyleSheetLoader
{
    public void loadStyleSheet(HttpServletRequest req, HttpServletResponse resp, ServletContext context)
            throws IOException;
}
