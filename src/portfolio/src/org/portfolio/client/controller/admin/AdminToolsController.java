/* $Name:  $ */
/* $Id: AdminToolsController.java,v 1.8 2011/03/17 19:15:29 ajokela Exp $ */
package org.portfolio.client.controller.admin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.portfolio.bus.ElementTypeManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.ElementType;
import org.portfolio.model.Person;
import org.portfolio.util.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Just some random admin tools. May move them later.
 * 
 * @author Matt Sheehan
 */
@Controller
public class AdminToolsController {

    @Autowired
    private ElementTypeManager elementTypeManager;
    
    @Autowired
    private PersonHome personHome;

    @RequestMapping("/admin/listElementTypes")
    public String listElementTypes(HttpServletRequest request, Model model) {
        Person person = RequestUtils.getPerson(request);
        if (!person.isAdmin()) {
            throw new SecurityException("no access");
        }
        List<ElementType> list = elementTypeManager.findAll();
        Collections.sort(list, new Comparator<ElementType>() {
            public int compare(ElementType o1, ElementType o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
        model.addAttribute("types", list);
        return "admin/listElementTypes";
    }

    @RequestMapping("/admin/listProperties")
    public String listProperties(HttpServletRequest request, Model model) {
        Person person = RequestUtils.getPerson(request);
        if (!person.isAdmin()) {
            throw new SecurityException("no access");
        }
        model.addAttribute("sysProps", System.getProperties());
        model.addAttribute("appProps", Configuration.getMap());
        return "admin/listProperties";
    }
    
    @RequestMapping("/admin/listAttributes")
    public String listAttributes(HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);
        if (!person.isAdmin()) {
            throw new SecurityException("no access");
        }
        return "admin/listAttributes";
    }

    @RequestMapping("/admin/userNotFound")
    public String userNotFound(HttpServletRequest request) {
        Person person = RequestUtils.getPerson(request);
        if (!person.isAdmin()) {
            throw new SecurityException("no access");
        }
        return "admin/userNotFound";
    }
    
    @RequestMapping("/admin") 
    public String admin(HttpServletRequest request) {
    	
    	List<Person> peoples = personHome.findAdmins();
    	
    	request.setAttribute("admins", peoples);
    	
    	return "admin/index";
    }

}
