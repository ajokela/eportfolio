/* $Name:  $ */
/* $Id: AuthenticationHandlerInterceptor.java,v 1.8 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.portfolio.client.RequestUtils;
import org.portfolio.client.security.authentication.PortfolioAuthenticator;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthenticationHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String LOGIN_REDIRECT_SESSION_KEY = "loginRedirect";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        boolean authRequired = !handler.getClass().isAnnotationPresent(NoAuthentication.class);

        if (authRequired && !isAuthenticated(request)) {
            RequestUtils.redirectToLogin(request, response);
            return false;
        }

        String loginRedirect = (String) session.getAttribute(LOGIN_REDIRECT_SESSION_KEY);
        if (isAuthenticated(request) && loginRedirect != null) {
            session.removeAttribute(LOGIN_REDIRECT_SESSION_KEY);
            response.sendRedirect(loginRedirect);
            return false;
        }
        return true;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        PortfolioAuthenticator authenticator = RequestUtils.getAuthenticator(request);
        return authenticator != null && authenticator.isAuthenticated(request);
    }

}
