/* $Name:  $ */
/* $Id: RegistrationAction.java,v 1.14 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.client.action.account;

// 3-2-2011 Not sure this file is used anywhere

import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.Person;
import org.portfolio.model.Person.UserType;
import org.portfolio.util.Configuration;
import org.portfolio.util.Mailer;
import org.portfolio.util.PortfolioConstants;
import org.portfolio.util.RequiredInjection;

/**
 * This action has been created to allow external users to create themselves an account.
 * 
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.14 $ $Date: 2011/03/14 19:37:44 $
 * @since 11 Feb 2003
 */
public class RegistrationAction extends BaseAction {
    
    private VelocityEngine velocityEngine; 

    private final String GREETING = "Welcome to Portfolio!";
    private final UserType userType = UserType.valueOf(Configuration.get("user.registration.usertype").toUpperCase());
    private final String successPage;
    private final long storageAllocation;
    private final boolean allowRegistration = Configuration.getBoolean("user.registration.allowRegistration");

    private Mailer mailer;
    private PersonHome personHome;

    public RegistrationAction() {
        if (userType == UserType.MEMBER) {
            successPage = FORWARD_SUCCESS + ".member";
            storageAllocation = Configuration.getLong("user.storage.default");
        } else {
            successPage = FORWARD_SUCCESS + ".guest";
            storageAllocation = Configuration.getLong("user.storage.guest");
        }
    }

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (!allowRegistration) {
            logService.info("Registration is disabled.");
        }

        // Making sure that the registration request wasn't cancelled.
        if (isCancelled(request) || !allowRegistration) {
            logService.debug("Processing request - was cancelled.");
            // cancelled the registration.
            return mapping.findForward(FORWARD_CANCEL);
        }

        String register = request.getParameter("register.x");
        if (register != null) {
            Person person = (Person) form;
            person.setPersonId(null);
            person.setEmail(person.getEmail().toLowerCase());
            person.setMaxStorageSize(storageAllocation);

            logService.debug("person is " + person);

            Person newPerson = personHome.findByUsername(person.getUsername());
            logService.debug("newPerson is " + newPerson);

            //Person oldPerson = null;
            //List<Person> extList = personHome.findByEmail(person.getEmail());
            //if ((extList != null) && (extList.size() > 0)) {
                //oldPerson = extList.get(0);
            //}
            Person oldPerson = personHome.findByEmail(person.getEmail());
            logService.debug("oldPerson is " + oldPerson);

            // user already exists
            if (newPerson != null && userType != UserType.GUEST) {
                // error, user already exists
                ActionErrors errors = new ActionErrors();
                errors.add("username", new ActionMessage("error.registration.username.userExists"));
                saveErrors(request, errors);
                return mapping.findForward("input");
            } else if ((oldPerson != null) && (!oldPerson.getPassword().equals(person.getPassword()))) {
                // person exists as a guest user, but the password doesn't
                // match.
                ActionErrors errors = new ActionErrors();
                errors.add("email", new ActionMessage("error.registration.email.userExists"));
                // use the supplied method.
                saveErrors(request, errors);
                return mapping.findForward("input");
            } else {
                // if there is no GUEST user to update, the user is new.
                if (oldPerson == null) {
                    person.setIsNew(true);
                }
                // create a new user
                person.setUsertype(userType);
                try {
                    personHome.store(person);
                    if (person != null) {
                        // send the confirmation email
                        sendConfirmationEmail(person);
                        logService.debug("The newly created user is " + person.getPersonId());
                        return mapping.findForward(successPage);
                    }/* else {
                        logService.debug("No user found.");
                        ActionErrors errors = new ActionErrors();
                        errors.add("username", new ActionMessage("error.registration.failure"));
                        saveErrors(request, errors);
                        return mapping.findForward("input");
                    }*/
                } catch (Exception e) {
                    logService.error("Exception caught when creating external user" + e.getMessage(), e);
                    return mapping.findForward("input");
                }
            }
        }
        return mapping.findForward("input");
    }

    /**
     * Sends a confirmation email to the person registering.
     * 
     * @param person the person object of the new person
     */
    private void sendConfirmationEmail(Person person) {
        StringWriter msg = new StringWriter();
        VelocityContext context = new VelocityContext();
        context.put("institutionLongName", Configuration.get("institution.longName"));
        context.put("institutionShortName", Configuration.get("institution.shortName"));
        context.put("url", Configuration.get("portfolio.base.url"));
        context.put("contactEmail", Configuration.get("system.contactEmail"));
        context.put("person", person);
        String templateName = "registration.notification.email.vm";

        // now, try and send the message
        try {
            velocityEngine.mergeTemplate(templateName, PortfolioConstants.TEMPLATE_ENCODING, context, msg);
            mailer.sendMail(msg.toString(), GREETING, person.getEmail(), Configuration.get("system.contactEmail"));
        } catch (Exception ex) {
            logService.error("User notification email failed.", ex);
        }
    }

    @RequiredInjection
    public void setMailer(Mailer mailer) {
        this.mailer = mailer;
    }

    @RequiredInjection
    public void setPersonHome(PersonHome personHome) {
        this.personHome = personHome;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
