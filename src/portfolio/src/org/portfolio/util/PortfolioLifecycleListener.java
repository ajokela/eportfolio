/* $Name:  $ */
/* $Id: PortfolioLifecycleListener.java,v 1.17 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class PortfolioLifecycleListener implements ServletContextListener {

    protected LogService logService = new LogService(this.getClass());

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        try {
            URL url = context.getResource("/WEB-INF/portfolio.properties");
            Properties properties = new Properties();
            properties.load(url.openStream());

            Properties overrides = new Properties();
            overrides.load(new FileInputStream(System.getProperty("epf.props")));
            
            properties.putAll(overrides);
            
            Configuration.init(properties);
            
            for (Object key : properties.keySet()) {
                context.setAttribute("org.portfolio." + key.toString(), properties.get(key));
            }
            String jaasPath = context.getRealPath("/WEB-INF/jaas.config");
            System.setProperty("java.security.auth.login.config", jaasPath);
        } catch (Exception e) {
            throw new IllegalStateException("System failed to initialize", e);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
