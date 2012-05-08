/* $Name:  $ */
/* $Id: AccountSettingsController.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller.account;

import javax.servlet.http.HttpServletRequest;

import org.portfolio.client.controller.ApplicationController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Matt Sheehan
 * 
 */
@Controller
public class AccountSettingsController extends ApplicationController {

    @RequestMapping("/account")
    public String execute(HttpServletRequest request) {
        // Person person = RequestUtils.getPerson(request);
        // return person.isGuest() ? "account/guestAccountInfo" : "account/personPreferences";
        
        return "account/guestAccountInfo";
    }
}
