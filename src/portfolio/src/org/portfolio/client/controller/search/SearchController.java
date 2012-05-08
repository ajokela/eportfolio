/* $Name:  $ */
/* $Id: SearchController.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller.search;

import org.portfolio.client.controller.ApplicationController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SearchController extends ApplicationController {

    @RequestMapping("/search/{type}/{query}")
    public String search(@PathVariable("type") String searchType, @PathVariable("query") String query) {
        if ("portfolios".equals(searchType)) {
            return "redirect:/share/#search/" + query;
        }
        return "redirect:/collection/#search/" + query;
    }

}
