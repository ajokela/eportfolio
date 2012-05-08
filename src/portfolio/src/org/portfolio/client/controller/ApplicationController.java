/* $Name:  $ */
/* $Id: ApplicationController.java,v 1.7 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.springframework.web.bind.annotation.RequestMapping;


import nl.bitwalker.useragentutils.*;

@SuppressWarnings("unused")
public class ApplicationController {
	
	private static LogService logService = null;
	public static final String BEFORE_SHARE_PAGE = "beforeSharePage";
	
	public ApplicationController() {
		
		if (logService == null) {
            logService = new LogService(ApplicationController.class);
        }
		
	}
	
	public Map<String, List<?>> transformKeysToStrings(Map<?, List<?>> inputMap) {
	
		Map<String, List<?>> outputMap = new LinkedHashMap<String,List<?>>();
	    
		for (Object key : inputMap.keySet()) {
	      outputMap.put(key.toString(), inputMap.get(key));
	    }
	    
		return outputMap;
	    
	}
	
    public Object getSessionAttribute(HttpServletRequest request, String attribute) {
        if (request != null && request.getSession() != null)
            return request.getSession().getAttribute(attribute);
        return null;
    }

    public void setSessionAttribute(HttpServletRequest request, String name, Object value) {
        request.getSession().setAttribute(name, value);
    }
	
    public void getBackPage(HttpServletRequest request, HttpServletResponse response) {
		
    	String referer = request.getHeader("Referer");
		
		Pattern p = Pattern.compile("Share");
		Matcher m;
		
		if (referer == null) {
			referer = Configuration.get("portfolio.base.url");
		}
		else {
			
			String backPage = (String)getSessionAttribute(request, BEFORE_SHARE_PAGE);
			m = p.matcher(referer);
			
			if (m.find()) {
				if (backPage != null) {
					referer = backPage;
				}
				else {
					referer = Configuration.get("portfolio.base.url");
				}
			}
			
		}
		
		setSessionAttribute(request, BEFORE_SHARE_PAGE, referer);
		request.setAttribute(BEFORE_SHARE_PAGE, referer);
		
    }
    	

	public void checkUserAgent(HttpServletRequest request) {
		// addError(request, request.getHeader("User-Agent"));

		try {
			
			UserAgent ua = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			
			/*
			log_message("===UserAgent= " + request.getHeader("User-Agent"));
			log_message("===Browser=== " + ua.getBrowser().getName());
			*/
					
			String reverse = new StringBuffer(request.getHeader("User-Agent")).reverse().toString();
			
			String[] parts = reverse.split(" +", 2);
			
			String uaBr = ua.getBrowser().getName();
			
			if (parts.length >= 2) {
				parts = new StringBuffer(parts[0]).reverse().toString().split("/");
				
				if (parts.length >= 2) {
					// log_message("=====[0]===> " + parts[0]);
					// log_message("=====[1]===> " + parts[1]);
					
					if (parts[0].equalsIgnoreCase("Firefox")) {
						parts = parts[1].split("\\.");
						
						if (parts.length >= 3) {
							/*
							log_message("=====[0]===> " + parts[0]);
							log_message("=====[1]===> " + parts[1]);
							log_message("=====[2]===> " + parts[2]);
							*/
							
							if (Integer.valueOf(parts[0]) < 4 && Integer.valueOf(parts[1]) < 4) {
								addError(request, "Your version of Firefox is outdated; some features of ePortfolio may not function correctly.  Please upgrade to a newer browser version.");
							}
							
						}
						
					}
					
				}
				
			}
			
			if(uaBr.matches("Internet Explorer 6") || uaBr.matches("Internet Explorer 7")) {

				logService.debug(uaBr);
				
				addError(request, "There are known issues with Internet Explorer 7 (and older); you may experience difficulties using ePortfolio; please consider using a different browser - Newer versions of Firefox, as well as Safari and Chome are known to work.");

				
				request.setAttribute("isIE", true);
					
			}
			else {
				request.setAttribute("isIE", false);
			}
			
		}
		catch (Exception e) {
			logService.debug(e);
		}
		
	}
	
    @SuppressWarnings("unchecked")
	public void addError(HttpServletRequest request, String string) {
    	
    	if (string == null || request == null) {
    		log_message("Unable to add Error to Request");
    		return;
    	}
    	
        List<String> epfErrors = (List<String>) request.getSession().getAttribute("epfErrors");
        if (epfErrors == null) {
            epfErrors = new ArrayList<String>();
        }
        
        epfErrors.add(string);
        
        request.getSession().setAttribute("epfErrors", epfErrors);
    }
	
	public static void log_message(String message) {
		logService.error(message);
	}

}
