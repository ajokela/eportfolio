/* $Name:  $ */
/* $Id: PortfolioAuthenticatorImpl.java,v 1.16 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import java.sql.Timestamp;
import java.util.Date;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.portfolio.bus.AdvisorView;
import org.portfolio.client.RequestUtils;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;
import org.portfolio.util.RequiredInjection;

/**
 * Will authenticate an institution user through jaas LoginModules loaded from jaas.config
 * 
 * @author <a href="jbush@rsmart.com">John Bush</a>
 * @author <a href="felipeen@udel.edu">Luis F.C. Mendes</a> - University of Delaware
 * @version $Revision 1.0 $
 */
public class PortfolioAuthenticatorImpl extends BasePortfolioAuthenticator {

    private PersonHome personHome;
    private boolean checkAlways;
    private String jaasLoginContext;
    private AdvisorView advisorView;

    private LogService logService = new LogService(this.getClass());
    
    private boolean isAuthenticated = false;

    /**
     * Returns false if any of the required system session attributes are missing. if 'authentication.login.checkLoginOnEachRequest' flag is
     * set we will call login again to use the LoginModule to revalidate the login. This is useful for implementations that require the user
     * be revalidated at each request. For example, in a SSO environment where a user may have logged out from a central authentication
     * server.
     * 
     * @return
     */
    public boolean isAuthenticated(HttpServletRequest request) {
        // check for system session attributes first
        if (!super.isAuthenticated(request)) {
            return false;
        }
        if (checkAlways) {
            try {
                authenticateUser(request);
                return true;
            } catch (LoginException e) {
                return false;
            }
        }
        return this.isAuthenticated;
    }

    /**
     * Authenticates the user and returns the constructed principal.
     */
    protected PortfolioPrincipal authenticateUser(HttpServletRequest request) throws LoginException {
        PortfolioPassiveCallbackHandler cbh = new PortfolioPassiveCallbackHandler(request);
        LoginContext lc = new LoginContext(this.jaasLoginContext, cbh);
        lc.login();
        Subject subject = lc.getSubject();
        return (PortfolioPrincipal) subject.getPrincipals(PortfolioPrincipalImpl.class).toArray()[0];
    }

    /**
     * Attempts to log the user in.
     * 
     * @param request
     * @throws LoginException if login does not succeed
     */
    public void login(HttpServletRequest request) throws LoginException {
        this.isAuthenticated = false;
        PortfolioPrincipal principal = authenticateUser(request);

        HttpSession session = request.getSession();
        PortfolioPrincipal requestPrincipal = (PortfolioPrincipal) session.getAttribute(PortfolioConstants.EXTERNAL_PRINCIPAL_SESSION_KEY);

        if (checkAlways || !principal.equals(requestPrincipal)) {
            Person person = createUser(principal);
            initAfterLogin(person, session);
            isAuthenticated = true;
            session.setAttribute(PortfolioConstants.EXTERNAL_PRINCIPAL_SESSION_KEY, principal);
        }
    }

    public void switchUser(HttpServletRequest request, Person newPerson) {
        Person realPerson = RequestUtils.getPerson(request);
        if (!realPerson.isAdmin()) {
            throw new SecurityException("no access");
        }

//        this.isAuthenticated = true;
//        PortfolioPrincipal principal = authenticateUser(request);

        HttpSession session = request.getSession();
        session.invalidate();
        session = request.getSession();

        PortfolioPrincipal newPrincipal = new PortfolioPrincipalImpl(newPerson);
        session.setAttribute(PortfolioConstants.EXTERNAL_PRINCIPAL_SESSION_KEY, newPrincipal);

        session.setAttribute("realPerson", realPerson);

        initAfterLogin(newPerson, session);
    }

    public void switchBack(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Person realPerson = (Person) session.getAttribute("realPerson");
//        session.removeAttribute("realPerson");

        session.invalidate();
        session = request.getSession();

        PortfolioPrincipalImpl newPrincipal = new PortfolioPrincipalImpl(realPerson);
        session.setAttribute(PortfolioConstants.EXTERNAL_PRINCIPAL_SESSION_KEY, newPrincipal);
        @SuppressWarnings("unused")
		PortfolioAuthenticator authenticator = RequestUtils.getAuthenticator(request);

        initAfterLogin(realPerson, session);
    }

    /**
     * copies the information from the principal into a new Person object and stores this person in the local system.
     * 
     * @param principal
     * @return the Person object for the user.
     */
    private Person createUser(PortfolioPrincipal principal) {
        Person person = null;
        String personId = (String) principal.getAttribute(PortfolioAuthenticator.PERSON_ID);
        String userName = (String) principal.getName();

        if (personId != null) {
            person = personHome.findByPersonId(personId);
        }

        if (person == null && userName != null) {
            person = personHome.findByUsername(userName);
        }

        if (person == null) {
            person = createNewPerson(principal);
        } else {
            // update last login
            person.setLastLogin(new Date());
            // also, update the rest of the information, based on the login
            // credentials.
            person.setFirstName((String) principal.getAttribute(PortfolioAuthenticator.FIRST_NAME));
            person.setLastname((String) principal.getAttribute(PortfolioAuthenticator.LAST_NAME));
            person.setMiddlename((String) principal.getAttribute(PortfolioAuthenticator.MIDDLE_NAME));
            person.setEmail((String) principal.getAttribute(PortfolioAuthenticator.EMAIL));
            person.setCampus((String) principal.getAttribute(PortfolioAuthenticator.CAMPUS));
            // If username in database doesn't match with the one in cookie, update it.
            // x.500 could recycle username after its inactive for some time. We will have to
            // keep personId and username mapping in sync with what's in x.500. See QC bug 4948.
            if (!person.getUsername().equalsIgnoreCase(principal.getName())) {
                // set the username from the cookie
                person.setUsername(principal.getName());
            }
        }
        personHome.store(person);
        
        return person;
    }

    /**
     * create person if they do not exist in portfolio already
     */
    private Person createNewPerson(PortfolioPrincipal principal) {
        Person person;
        UserType userType = UserType.MEMBER;
        try {
            userType = UserType.valueOf((String) principal.getAttribute(PortfolioAuthenticator.USER_TYPE));
        } catch (Exception e) {
        	logService.debug(e);
        }
        person = new Person();
        person.setUsername(principal.getName());
        person.setPersonId((String) principal.getAttribute(PortfolioAuthenticator.PERSON_ID));
        person.setIsNew(true);
        person.setFirstName((String) principal.getAttribute(PortfolioAuthenticator.FIRST_NAME));
        person.setLastname((String) principal.getAttribute(PortfolioAuthenticator.LAST_NAME));
        person.setMiddlename((String) principal.getAttribute(PortfolioAuthenticator.MIDDLE_NAME));
        person.setEmail((String) principal.getAttribute(PortfolioAuthenticator.EMAIL));
        person.setCampus((String) principal.getAttribute(PortfolioAuthenticator.CAMPUS));
        person.setLastLogin(new Date());
        person.setDateCreated(new Timestamp(new Date().getTime()));
        person.setUsertype(userType);
        person.setMaxStorageSize(Configuration.getLong("user.storage.default"));
        
        logService.debug("\n\n\nreturn person object\n\n\n");
        
        return person;
    }
    
    @Override
    protected void initAfterLogin(Person person, HttpSession session) {
        super.initAfterLogin(person, session);

        // logService.debug(">> initAfterLogin()");
        
        try {
	        // TODO this should use the observer pattern?
	        session.setAttribute(PortfolioConstants.ADVISEE_LIST_SESSION_KEY, advisorView.getAdviseeList(person.getPersonId()));
	        session.setAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY, this);
        }
        catch (Exception e) {
        
        	// logService.debug(e);
        	
        	logService.debug("initAfterLogin(): " + e.getLocalizedMessage());
        	
        }
        
        // logService.debug("<< initAfterLogin()");
    }

    @RequiredInjection
    public void setCheckAlways(boolean checkAlways) {
        this.checkAlways = checkAlways;
    }

    @RequiredInjection
    public void setJaasLoginContext(String jaasLoginContext) {
        this.jaasLoginContext = jaasLoginContext;
    }

    @RequiredInjection
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }

    @RequiredInjection
    public void setAdvisorView(AdvisorView advisorView) {
        this.advisorView = advisorView;
    }

}
