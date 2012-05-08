package org.portfolio.client.security.authentication;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.portfolio.client.security.authentication.PortfolioCredential;
import org.portfolio.client.security.authentication.PortfolioHttpRequestCallBack;
import org.portfolio.client.security.authentication.PortfolioPrincipal;
import org.portfolio.client.security.authentication.PortfolioPrincipalImpl;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class HtAccessLoginModule implements LoginModule {

	private static final String IP_ADDRESS_KEY = "IP address";
	
	private PersonHome personHome;
	private CallbackHandler callbackHandler;
    private Subject subject;
    /** the authentication status */
    private boolean success = false;
    /** The cached auth cookie value */
    //private PortfolioCredential credential;
	
    private LogService logService = new LogService(this.getClass());
    
	public boolean abort() throws LoginException {
        // Clean out state
        success = false;
        logout();
        return true;
	}

	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		
        this.callbackHandler = callbackHandler;
        this.subject = subject;
		
	}

	public boolean login() throws LoginException {
        PortfolioHttpRequestCallBack portfolioHttpRequestCallBack = new PortfolioHttpRequestCallBack();
        try {
            this.callbackHandler.handle(new Callback[] { portfolioHttpRequestCallBack });
        } catch (Exception e) {
            // throw createLoginException("exception during callback", e);
        }
        
        HttpServletRequest request = portfolioHttpRequestCallBack.getRequest();
        
        @SuppressWarnings("unchecked")
		Enumeration<String>headerNames = request.getHeaderNames();
        
		while (headerNames.hasMoreElements()) {
			String headerName = (String)headerNames.nextElement();
			String headerValue = request.getHeader(headerName);
			
			logService.debug("===> " + headerName + " => [" + headerValue + "]");
			
		}
                
        PortfolioPrincipal principal = authenticate(request);
        
        if (principal != null) {
            success = true;
            subject.getPrincipals().add(principal);
        }
		
        
        if (!success) {
            throw new LoginException("HtAccessLoginModule Authentication failed");
        }
        
        return success;

	}

	public boolean logout() throws LoginException {
        // remove the principals the login module added
		
		if (subject != null) {
			
	        Iterator<?> it = subject.getPrincipals(PortfolioPrincipalImpl.class).iterator();
	        while (it.hasNext()) {
	        	
	            PortfolioPrincipalImpl p = (PortfolioPrincipalImpl) it.next();
	            subject.getPrincipals().remove(p);
	            
	        }
	
	        // remove the credentials the login module added
	        it = subject.getPrivateCredentials(PortfolioCredential.class).iterator();
	        while (it.hasNext()) {
	            PortfolioCredential c = (PortfolioCredential) it.next();
	            subject.getPrivateCredentials().remove(c);
	        }
		}
		
		return true;
	}
	
    private PortfolioPrincipal authenticate(HttpServletRequest request) {
    	
    	/*
        HttpSession session = request.getSession();
        Cookie cookie = RequestUtils.getCookie(request, CAH_AUTH_COOKIE_NAME);
   		*/
    	
    	HttpSession session = request.getSession();
    	String username = request.getHeader("REMOTE_USER");
    	
        PortfolioPrincipal principal = null;
        
        if(username != null) {
        	
        	principal = (PortfolioPrincipal) session.getAttribute(PortfolioConstants.EXTERNAL_PRINCIPAL_SESSION_KEY);
        	
        	if(principal == null) {
        		
        		/*
        		 * At this point, we need to lookup the following information:
        		 * 
        		 * response.setIpAddress(tokens[2]);
            	 * response.setX500(tokens[3]);
            	 * response.setPersonId(tokens[4]);
            	 * response.setFirstName(tokens[6]);
            	 * response.setMiddleName(tokens[7]);
            	 * response.setLastName(tokens[8]);
            	 * response.setCampus(tokens[19]);
            	 * response.setEmail(tokens[21]);
        		 * 
        		 */
        		
        		String[] pieces = username.split("@");
        		
        		if(pieces.length > 0) {
        			username = pieces[0];
        		}
        		
        		/*
        		Person person = null; // personPSHome.findByInternetID(username);
        	
        		if(person != null) {
        			
                    Person p = personHome.findByPersonId(person.getPersonId());
                    UserType userType = p != null ? p.getUsertype() : UserType.MEMBER;
                    
                    String remote_addr = (String) request.getAttribute("REMOTE_ADDR");
                    
                    if (remote_addr == null) {
                    	remote_addr = "UNKNOWN";
                    }
                    
                    principal = new PortfolioPrincipalImpl();
                    principal.setAttribute(IP_ADDRESS_KEY, remote_addr );
                    principal.setAttribute(PortfolioAuthenticator.X500, username);
                    principal.setAttribute(PortfolioAuthenticator.PERSON_ID, person.getPersonId());
                    principal.setAttribute(PortfolioAuthenticator.FIRST_NAME, person.getFirstName());
                    principal.setAttribute(PortfolioAuthenticator.MIDDLE_NAME, person.getMiddlename());
                    principal.setAttribute(PortfolioAuthenticator.LAST_NAME, person.getLastname());
                    principal.setAttribute(PortfolioAuthenticator.EMAIL, person.getEmail());
                    
                    principal.setAttribute(PortfolioAuthenticator.USER_TYPE, userType.toString());
                   	principal.setAttribute(PortfolioAuthenticator.CAMPUS, person.getCampus());

                    principal.setName(username);
                    
        		}
        		*/
        		
        	}
        	
        }
        
        /*
        if (cookie != null) {
            String cookieValue = cookie.getValue();
            String cachedCookieValue = (String) session.getAttribute("UofMCookie");

            // get the stored principal
            principal = (PortfolioPrincipal) session.getAttribute(PortfolioConstants.EXTERNAL_PRINCIPAL_SESSION_KEY);

            // two checks that need to be done.
            // 1) The cookie value must match the cached cookie value
            // 2) The principal must exist
            if (!cookieValue.equals(cachedCookieValue) || principal == null) {
                CookieAuthenticationResponse authResponse = uofMCookieAuthenticator.authenticateCookieValue(cookieValue);
                
                principal = createPrincipal(authResponse);
                this.credential = new PortfolioCredential(cookieValue);
                session.setAttribute("UofMCookie", cookie.getValue());
            }
        }
        */
        return principal;
    }

    @Autowired
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }
    
    public PersonHome getPersonHome() {
    	return personHome;
    }
	
}