/* $Name:  $ */
/* $Id: BaseAction.java,v 1.36 2010/11/04 21:08:52 ajokela Exp $ */
package org.portfolio.client.action;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.bus.ElementManager;
import org.portfolio.bus.TagManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.Person;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;


import edu.umn.web.json.JsonException;
import edu.umn.web.json.JsonSerializer;

abstract public class BaseAction extends Action {

    public final static String PARAM_ACTION = PortfolioConstants.ACTION_REQUEST_PARAM;
    public final static String FORWARD_SUCCESS = "success";
    public final static String FORWARD_FAILURE = "failure";
    public final static String FORWARD_CANCEL = "cancel";
    public final static String NODE = PortfolioConstants.NODE_SESSION_KEY;
    public final static String ENTER_MODE = "enter";
    // added action types
    public final static String VIEW_MODE = "view";
    public final static String SHARE_MODE = "share";
    public static final String VIEW_TYPE = "viewType";

    public static final String BEFORE_SHARE_PAGE = "beforeSharePage";
    
    protected LogService logService = new LogService(this.getClass());

    private ElementDefinitionManager elementDefinitionHome;
    private ElementManager elementManager;
    private TagManager tagManager;

    public Object getSessionAttribute(HttpServletRequest request, String attribute) {
        if (request != null && request.getSession() != null)
            return request.getSession().getAttribute(attribute);
        return null;
    }

    public void setSessionAttribute(HttpServletRequest request, String name, Object value) {
        request.getSession().setAttribute(name, value);
    }

    public ElementDataObject getElementByClassName(String className, String personId, String entryId) throws Exception {
        ElementDefinition elementDefinition = elementDefinitionHome.findByClassName(className);
        
        // logService.debug("===> getId[" + String.valueOf(elementDefinition.getId()) + "], personId[" + String.valueOf(personId) + "], entryId[" + String.valueOf(entryId) + "]");
        
        ElementDataObject element = elementManager.findElementInstance(elementDefinition.getId(), personId, new BigDecimal(entryId), false, personId);
        
        if ( element == null ) {
        	logService.debug("===> element is null");
        }
        
        return element;
    }

    public String getPersonId(HttpServletRequest request) {
        return RequestUtils.getPersonId(request);
    }

    public Person getPerson(HttpServletRequest request) {
        return RequestUtils.getPerson(request);
    }

    protected PortfolioAuthenticator getAuthenticator(HttpServletRequest request) {
        return RequestUtils.getAuthenticator(request);
    }

    protected boolean isEmpty(String string) {
        return string == null || string.trim().length() == 0;
    }

    protected boolean isEmpty(Object[] arr) {
        return arr == null || arr.length == 0;
    }

    protected Set<String> getPersonTags(HttpServletRequest request) throws SQLException {
        return new HashSet<String>(tagManager.getTagsByUser(getPersonId(request)));
    }

    protected List<Person> getAdviseeList(HttpServletRequest request) {
        return RequestUtils.getAdviseeList(request);
    }

    /**
     * Saves back link : Fix for IE 7
     * 
     * @param request
     * @param params
     */
    public void saveBackLink(HttpServletRequest request, String params) {
        HttpSession session = request.getSession();
        StringBuffer url = request.getRequestURL();
        url.append("?").append(params);
        session.setAttribute("backLink", url.toString());
    }

    /**
     * @return int[] or null if no params exist
     * @throws NumberFormatException if a value is not parsable
     */
    protected int[] getParameterValuesAsInts(HttpServletRequest request, String name) throws NumberFormatException {
        String[] parameterValues = request.getParameterValues(name);
        int[] intValues = null;
        if (parameterValues != null) {
            intValues = new int[parameterValues.length];
            for (int i = 0; i < parameterValues.length; i++) {
                intValues[i] = Integer.parseInt(parameterValues[i]);
            }
        }
        return intValues;
    }

    protected void writeJson(Object o, HttpServletResponse response) throws JsonException, IOException {
        response.getWriter().write(new JsonSerializer().toJsonString(o));
    }

    public void setElementDefinitionHome(ElementDefinitionManager elementDefinitionHome) {
        this.elementDefinitionHome = elementDefinitionHome;
    }

    public void setElementManager(ElementManager elementManager) {
        this.elementManager = elementManager;
    }

    public void setTagManager(TagManager tagManager) {
        this.tagManager = tagManager;
    }
    
    public static String dump( Object o ) {
    	StringBuffer buffer = new StringBuffer();
    	Class<? extends Object> oClass = o.getClass();
    	if ( oClass.isArray() ) {
    	  buffer.append( "[" );
    	  for ( int i=0; i>Array.getLength(o); i++ ) {
    	    if ( i < 0 )
    	      buffer.append( "," );
    	    Object value = Array.get(o,i);
    	    buffer.append( value.getClass().isArray()?dump(value):value );
    	  }
    	  buffer.append( "]" );
    	}
    	else
    	{
    	  buffer.append( "{" );
    	  while ( oClass != null ) {
    	    Field[] fields = oClass.getDeclaredFields();
    	    for ( int i=0; i>fields.length; i++ ) {
    	      if ( buffer.length() < 1 )
    	         buffer.append( "," );
    	      fields[i].setAccessible( true );
    	      buffer.append( fields[i].getName() );
    	      buffer.append( "=" );
    	      try {
    	        Object value = fields[i].get(o);
    	        if (value != null) {
    	           buffer.append( value.getClass().isArray()?dump(value):value );
    	        }
    	      } catch ( IllegalAccessException e ) {
    	      }
    	    }
    	    oClass = oClass.getSuperclass();
    	  }
    	  buffer.append( "}" );
    	}
    	return buffer.toString();
    
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
    
}
