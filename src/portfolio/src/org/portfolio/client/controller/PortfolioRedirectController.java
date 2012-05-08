/* $Name:  $ */
/* $Id: PortfolioRedirectController.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@NoAuthentication
public class PortfolioRedirectController extends ApplicationController {

    @RequestMapping(value={"/portfolio", "/portfolio/"})
    public String redirect() {
        return "redirect:/";
    }

}
