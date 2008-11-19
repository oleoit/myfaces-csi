package org.apache.myfaces.custom.skin.webapp;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface StyleSheetLoader
{
    public void loadStyleSheet(HttpServletRequest req, HttpServletResponse resp, ServletConfig config)
            throws IOException;
}
