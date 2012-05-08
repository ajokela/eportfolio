/* $Name:  $ */
/* $Id: StaticContentController.java,v 1.7 2010/12/06 14:26:05 ajokela Exp $ */

package org.portfolio.client.controller;

import org.apache.commons.io.IOUtils;
import org.portfolio.util.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

@Controller
@NoAuthentication
public class StaticContentController extends ApplicationController {


    private final DateFormat httpDateFormat;

    private final LogService logService = new LogService(this.getClass());

    public StaticContentController() {
        httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @RequestMapping({"/images/**", "/css/**", "/js/**", "/favicon.ico", "/portico/jars/**", "/flash/**"})
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    	setCacheExpireDate(resp, 200000);

        String resourcePath = req.getServletPath();
        if (req.getPathInfo() != null) {
            resourcePath += req.getPathInfo();
        }
        
        String realPath = req.getSession().getServletContext().getRealPath(resourcePath);
        
        boolean exists = (new File(realPath)).exists();
        
        if (exists) {
	        
	        FileInputStream fileInputStream = new FileInputStream(realPath);
	        
	        try {
	            IOUtils.copy(fileInputStream, resp.getOutputStream());
	        } finally {
	            fileInputStream.close();
	        }
        
        }
        else {
        	logService.debug("FILE NOT FOUND --> " + realPath);
        }
        
    }

    private void setCacheExpireDate(HttpServletResponse response, int seconds) {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.SECOND, seconds);
        response.setHeader("Cache-Control", "PUBLIC, max-age=" + seconds + ", must-revalidate");
        response.setHeader("Expires", httpDateFormat.format(cal.getTime()));
    }
    
}
