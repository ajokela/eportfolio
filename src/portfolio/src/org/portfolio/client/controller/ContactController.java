/* $Name:  $ */
/* $Id: ContactController.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.controller;

import javax.mail.MessagingException;

import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@NoAuthentication
public class ContactController extends ApplicationController {
    
    private static LogService logService = new LogService(ContactController.class);
    
    @Autowired
    private Mailer mailer;

    @RequestMapping("/contact")
    public String view() {
        return "contact/contact";
    }

    @RequestMapping("/contact/send")
    public String send(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("message") String message) {
        String subject = "Message from " + name;
        String systemEmail = Configuration.get("portfolio.contact.email");
        
        try {
            mailer.sendMail(message, subject, systemEmail, email);
        } catch (MessagingException e) {
            logService.error(e);
        }
        
        return "redirect:/contact/sent";
    }

    @RequestMapping("/contact/sent")
    public String sent() {
        return "contact/sent";
    }

}
