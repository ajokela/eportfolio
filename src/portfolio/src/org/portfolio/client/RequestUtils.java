/* $Name:  $ */
/* $Id: RequestUtils.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.portfolio.model.Person;
import org.portfolio.util.PortfolioConstants;

public class RequestUtils {

    public static String getPersonId(HttpSession httpSession) {
        return getPerson(httpSession).getPersonId();
    }

    public static String getPersonId(HttpServletRequest request) {
        return getPerson(request.getSession()).getPersonId();
    }

    public static Person getPerson(HttpServletRequest request) {
        return getPerson(request.getSession());
    }

    public static Person getPerson(HttpSession httpSession) {
        return (Person) httpSession.getAttribute(PortfolioConstants.PERSON_SESSION_KEY);
    }

    public static PortfolioAuthenticator getAuthenticator(HttpServletRequest request) {
        return (PortfolioAuthenticator) request.getSession().getAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY);
    }

    public static boolean isEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

    @SuppressWarnings("unchecked")
    public static List<Person> getAdviseeList(HttpServletRequest request) {
        return (List<Person>) request.getSession().getAttribute(PortfolioConstants.ADVISEE_LIST_SESSION_KEY);
    }

    /**
     * @return the Cookie or null if not found
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    public static void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("loginRedirect", getUrl(request));
        response.sendRedirect("/");
    }

    public static String getUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?" + request.getQueryString());
        }
        return requestURL.toString();
    }

}
