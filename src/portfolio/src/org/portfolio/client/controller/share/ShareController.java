/* $Name:  $ */
/* $Id: ShareController.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller.share;

import org.portfolio.client.controller.ApplicationController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShareController extends ApplicationController {

    @RequestMapping("/share/")
    public String execute() {
        return "share/share";
    }
}
