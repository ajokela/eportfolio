/* $Name:  $ */
/* $Id: AccountInfoAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.account;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.BaseAction;
import org.portfolio.model.Person;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/action/account/AccountInfoAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.4 $
 * $Date: 2010/10/27 19:24:56 $
 *
 * =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
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

public class AccountInfoAction extends BaseAction {

   // constants
   /** used for action = display (the default) */
   public final static String DISPLAY = "display";
   /** action = done */
   public final static String DONE = "done";
   /** action = "password" */
   public final static String PASSWORD = "password";
   /** action = "name" */
   public final static String NAME = "name";
   /** class logger */
   private static LogService logService = null;

   /**
    * Handles the action.
    * Possible results of action.
    * <ol>
    *    <li>Display the account information (default).</li>
    *    <li>Change password (forward to changePassword.do).</li>
    *    <li>Edit name (forward to changeName.do)</li>
    *    <li>Cancel (done)</li>
    * </ol>
    *
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws IOException
    * @throws ServletException
    */
   public ActionForward execute(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
         throws IOException, ServletException {

      if (logService == null) {
         logService = new LogService(this.getClass());
      }
      // changes for bug 181

      String action = request.getParameter("action");
      String name = request.getParameter(NAME + ".x");
      String done = request.getParameter(DONE + ".x");
      String password = request.getParameter(PASSWORD + ".x");
      if (name != null) {
         action = NAME;
      } else if (done != null) {
         action = DONE;
      } else if (password != null) {
         action = PASSWORD;
      } else if ((action == null) || (action.equalsIgnoreCase(""))) {
         action = DISPLAY;
      }

      logService.debug("Action is " + action);

      if (action.equalsIgnoreCase(DONE)) {
         HttpSession session = request.getSession(false);
         Person person = (Person) session.getAttribute(PortfolioConstants.PERSON_SESSION_KEY);
         if (person.isGuest()) {
            return mapping.findForward("guestDone");
         } else {
            return mapping.findForward("memberDone");
         }
      } else if (action.equalsIgnoreCase(PASSWORD)) {
         return mapping.findForward("password");
      } else if (action.equalsIgnoreCase(NAME)) {
         logService.debug("Setting up the forward.");
         ActionForward forward = mapping.findForward("nameForward");
         logService.debug("The forward is " + forward);
         // this needs to be a redirect forward, else the ChangeNameAction is failing.
         forward = new ActionForward(forward.getPath(), true);
         logService.debug("The forward path is " + forward.getPath());
         logService.debug("The forward redirect setting is : " + forward.getRedirect());
         return forward;
      }
      // default
      return mapping.findForward("display");
   }
}
