/* $Name:  $ */
/* $Id: AuthenticationFilter.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.portfolio.util.PortfolioConstants;
import org.portfolio.util.RequiredInjection;

public class AuthenticationFilter implements Filter {

    public static final String LOGIN_REDIRECT_SESSION_KEY = "loginRedirect";

    private String unAuthenticatedPage;
    private List<String> excludedPages;

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        if (!isPageExcluded(request) && !isAuthenticated(request)) {
            if ("get".equalsIgnoreCase(request.getMethod())) {
                // Save this page so we can redirect to it after login.
                session.setAttribute(LOGIN_REDIRECT_SESSION_KEY, getUrl(request));
            }
            request.getRequestDispatcher(unAuthenticatedPage).forward(request, response);
            return;
        }

        String loginRedirect = (String) session.getAttribute(LOGIN_REDIRECT_SESSION_KEY);
        if (loginRedirect != null && isAuthenticated(request)) {
            session.removeAttribute(LOGIN_REDIRECT_SESSION_KEY);
            response.sendRedirect(loginRedirect);
            return;
        }

        chain.doFilter(req, res);

    }

    private boolean isPageExcluded(HttpServletRequest request) {
        String page = trimSlash(request.getRequestURI());
        return excludedPages.contains(page);
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        HttpSession session = request.getSession();
        PortfolioAuthenticator authenticator = (PortfolioAuthenticator) session.getAttribute(PortfolioConstants.AUTHENTICATOR_SESSION_KEY);
        return authenticator != null && authenticator.isAuthenticated(request);
    }

    private String trimSlash(String page) {
        if (page.startsWith("/")) {
            page = page.substring(1);
        }
        return page;
    }

    private String getUrl(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        if (request.getQueryString() != null) {
            requestURL.append("?" + request.getQueryString());
        }
        return requestURL.toString();
    }

    @RequiredInjection
    public void setUnAuthenticatedPage(String unAuthenticatedPage) {
        this.unAuthenticatedPage = unAuthenticatedPage;
    }

    @RequiredInjection
    public void setExcludedPages(List<String> excludedPages) {
        this.excludedPages = excludedPages;
    }
}
