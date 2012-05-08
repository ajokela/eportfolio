/* $Name:  $ */
/* $Id: SaveGuestAccountInfoAction.java,v 1.7 2011/02/17 19:59:18 rkavajec Exp $ */
package org.portfolio.client.action.account;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;

public class SaveGuestAccountInfoAction extends BaseAction {
    
    private PersonHome personHome;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        boolean cancel = request.getParameter(org.apache.struts.taglib.html.Constants.CANCEL_PROPERTY_X) != null;
        Person personForm = (Person) form;

        if (cancel) {
            return mapping.findForward(FORWARD_CANCEL);
        }

        Person sessionPerson = getPerson(request);
        if (personForm != null) {

            sessionPerson.setFirstName(personForm.getFirstName());
            sessionPerson.setMiddlename(personForm.getMiddlename());
            sessionPerson.setLastname(personForm.getLastname());
            sessionPerson.setEmail(personForm.getEmail());
            sessionPerson.setPassword(personForm.getPassword());
            sessionPerson.setLastLogin(new Date());

            personHome.store(sessionPerson);

        }
        return mapping.findForward(FORWARD_SUCCESS);
    }

    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }

}
