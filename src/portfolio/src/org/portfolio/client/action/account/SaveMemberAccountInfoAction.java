/* $Name:  $ */
/* $Id: SaveMemberAccountInfoAction.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
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

public class SaveMemberAccountInfoAction extends BaseAction {

    private PersonHome personHome;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        boolean cancel = request.getParameter(org.apache.struts.taglib.html.Constants.CANCEL_PROPERTY_X) != null;
        Person person = (Person) form;

        if (cancel) {
            return mapping.findForward(FORWARD_CANCEL);
        }

        Person sessionPerson = getPerson(request);
        if (person != null) {
            sessionPerson.setFirstName(person.getFirstName());
            sessionPerson.setMiddlename(person.getMiddlename());
            sessionPerson.setLastname(person.getLastname());
            sessionPerson.setEmail(person.getEmail());
            sessionPerson.setPassword(person.getPassword());
            sessionPerson.setLastLogin(new Date());
            personHome.store(sessionPerson);
        }
        return mapping.findForward(FORWARD_SUCCESS);
    }

    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }
}
