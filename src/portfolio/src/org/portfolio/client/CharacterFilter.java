/* $Name:  $ */
/* $Id: CharacterFilter.java,v 1.7 2010/11/04 21:08:52 ajokela Exp $ */
package org.portfolio.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * This filter replaces some of the characters in the request params. The
 * characters replaced are those that are not in our databases character set,
 * but commonly used if copying from Microsoft Word.
 * 
 * @author Matt Sheehan
 */
public class CharacterFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CharacterFilterRequestWrapper characterFilterRequestWrapper = new CharacterFilterRequestWrapper((HttpServletRequest) request);
        chain.doFilter(characterFilterRequestWrapper, response);
    }

    /**
     * Decorator for the request. We've seen that some param value characters
     * aren't in the databases character set and are being lost. This will
     * convert those characters to characters that are allowed.
     * 
     * @author Matt Sheehan
     */
    private static class CharacterFilterRequestWrapper extends HttpServletRequestWrapper {

        private static final Map<Character, String> charMap = new HashMap<Character, String>();
        static {
            // TODO: put in configuration?
            charMap.put('\u2018', "'"); // left single quote
            charMap.put('\u2019', "'"); // right single quote
            charMap.put('\u201C', "\""); // left double quote
            charMap.put('\u201D', "\""); // right double quote
            charMap.put('\u2013', "-"); // en dash
            charMap.put('\u2014', "-"); // em dash
            charMap.put('\u2002', " "); // en space
            charMap.put('\u2003', " "); // em space
            charMap.put('\u2122', "(TM)"); // trade mark sign
            charMap.put('\u2026', "..."); // horizontal ellipsis
        }

        public CharacterFilterRequestWrapper(HttpServletRequest request) {
            super(request);
            // Make sure we're interpreting the request as unicode.
            if (getCharacterEncoding() == null) {
                try {
                    setCharacterEncoding("UTF-8");
                } catch (UnsupportedEncodingException e) {
                    throw new IllegalStateException("UTF-8 not supported", e);
                }
            }
        }

        private String filterString(String string) {
            StringBuilder filteredString = new StringBuilder();
            for (char c : string.toCharArray()) {
                String replacement = charMap.get(c);
                filteredString.append(replacement != null ? replacement : c);
            }
            return filteredString.toString();
        }

        @Override
        public Map<?, ?> getParameterMap() {
            Map<String, String[]> filteredParameterMap = new HashMap<String, String[]>();
            for (@SuppressWarnings("unchecked")
			Enumeration<String> e = super.getParameterNames(); e.hasMoreElements();) {
                String name = (String) e.nextElement();
                filteredParameterMap.put(name, getParameterValues(name));
            }
            return filteredParameterMap;
        }

        @Override
        public String[] getParameterValues(String name) {
            String[] params = super.getParameterValues(name);
            String[] filteredParams = null;
            if (params != null) {
                filteredParams = new String[params.length];
                for (int i = 0; i < params.length; i++) {
                    filteredParams[i] = filterString(params[i]);
                }
            }
            return filteredParams;
        }

        @Override
        public String getParameter(String name) {
            String parameter = super.getParameter(name);
            return parameter == null ? null : filterString(parameter);
        }
    }

}
