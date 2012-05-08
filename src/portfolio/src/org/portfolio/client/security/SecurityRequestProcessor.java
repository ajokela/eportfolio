/* $Name:  $ */
/* $Id: SecurityRequestProcessor.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/SecurityRequestProcessor.java,v 1.10 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.10 $
 * $Date: 2010/11/04 21:08:53 $
 *
 * =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 *  copy of the License at http://www.theospi.org/.
 *
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Copyrights:
 *
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 *
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Portions Copyright (c) 2003 The University of Delaware.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */
package org.portfolio.client.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.model.Person;
import org.portfolio.util.PortfolioConstants;
import org.springframework.web.struts.AutowiringRequestProcessor;

@SuppressWarnings("deprecation")
public class SecurityRequestProcessor extends AutowiringRequestProcessor {
    
    protected boolean processRoles(HttpServletRequest request, HttpServletResponse response, ActionMapping mapping) throws IOException,
            ServletException {
        if (!hasAccess(request, mapping)) {
            String forwardUrl = request.getContextPath() + mapping.findForward("logonForm").getPath();
            ActionErrors errors = new ActionErrors();
            errors.add("newLogon", new ActionMessage("errors.newLogon"));
            request.setAttribute(org.apache.struts.Globals.MESSAGE_KEY, errors);
            response.sendRedirect(forwardUrl);
            return false;
        }
        return true;

    }

    private boolean hasAccess(HttpServletRequest request, ActionMapping mapping) {
        String roles[] = mapping.getRoleNames();
        if (roles == null || roles.length < 1) {
            return true;
        }

        Person person = (Person) request.getSession().getAttribute(PortfolioConstants.PERSON_SESSION_KEY);
        if (person == null) {
            return false;
        }

        boolean roleMatch = false;
        for (String role : roles) {
            if ("member".equals(role)) {
                roleMatch = roleMatch || person.isMember() || person.isAdmin();
            } else if ("guest".equals(role)) {
                roleMatch = roleMatch || person.isGuest() || person.isAdmin();
            }
        }
        return roleMatch;
    }

    @Override
    protected ActionMapping processMapping(HttpServletRequest arg0, HttpServletResponse arg1, String path) throws IOException {
        ActionMapping actionConfig = (ActionMapping) moduleConfig.findActionConfig(path);
        if (actionConfig == null) {
            actionConfig = super.processMapping(arg0, arg1, path);
        }
        return actionConfig;
    }
}
