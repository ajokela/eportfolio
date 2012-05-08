/* $Name:  $ */
/* $Id: AppsController.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller.services;

import org.portfolio.client.controller.ApplicationController;
import org.portfolio.client.controller.NoAuthentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoAuthentication
public class AppsController extends ApplicationController {

    @RequestMapping("/services/apps")
    public String execute() {
        return "services/apps";
    }

}
