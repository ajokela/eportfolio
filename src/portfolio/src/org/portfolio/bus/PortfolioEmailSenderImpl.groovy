/* $Name:  $ */
/* $Id: PortfolioEmailSenderImpl.groovy,v 1.7 2011/03/25 13:52:16 ajokela Exp $ */
package org.portfolio.bus

import org.portfolio.model.Person.UserType;

import java.util.Iterator;

import java.io.StringWriter
import java.util.Date
import java.util.List
import groovy.text.SimpleTemplateEngine
import org.portfolio.dao.PortfolioHome
import org.portfolio.dao.ViewerHome
import org.portfolio.model.Person
import org.portfolio.model.Portfolio
import org.portfolio.model.Viewer
import org.portfolio.model.Viewer.ViewType
import org.portfolio.util.Configuration
import org.portfolio.util.LogService
import org.portfolio.util.Mailer
import org.portfolio.util.PortfolioConstants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Sends share notification emails.
 */
@Service
public class PortfolioEmailSenderImpl implements PortfolioEmailSender {

    private static LogService logService = new LogService(PortfolioEmailSenderImpl.class)

    @Autowired
    private ViewerHome viewerHome
    @Autowired
    private Mailer mailer
    @Autowired
    private PortfolioHome shareDefinitionHome



    public void sendEmails(String shareId, boolean ccSelf, String msg) {
        Portfolio shareDef = shareDefinitionHome.findByShareId(shareId);
		
		if(shareDef != null) {
			
	        def viewers = shareDef.getViewerViewers().findAll {it.getEmailSentDate() == null}
	        sendEmails(shareId, viewers, ccSelf, msg)
		
		}
    }

    public void sendEmails(String shareId, List<Viewer> viewers, boolean ccSelf, String msg, boolean sendEmail) {
        Portfolio shareDef = shareDefinitionHome.findByShareId(shareId)
		
		if(shareDef != null) {
			
			/*
	        if (ccSelf) {
	            viewers << shareDef.getOwnerViewer()
	        }
			*/
			
	        try {
				/*
	            viewers.each { viewer ->
					
					if (viewer.getEmailSend)
					
	                sendShareNotification(viewer.getPerson(), shareDef, msg)
	                viewer.setEmailSentDate(new Date())
	                viewerHome.update(viewer)
					
	            }
	            */
				
				for(Iterator<Viewer> i = viewers.iterator(); i.hasNext();) {
					
					Viewer viewer = i.next();
					
					boolean shouldEmail = true;
					
					if( viewer.getPerson().getUsertype() != UserType.GUEST && sendEmail == false) {
						shouldEmail = false;
					}
					
					if(viewer.getEmailSentDate() == null && viewer.getPerson().getUsertype() == UserType.GUEST) {
						shouldEmail = true;
					}
	
					if(shouldEmail) {
					
						sendShareNotification(viewer.getPerson(), shareDef, msg)
						viewer.setEmailSentDate(new Date())
						viewerHome.update(viewer)
	
					}
					
				}
				
				if(ccSelf) {
					
					Viewer v = shareDef.getOwnerViewer()
					
					sendShareNotification(v.getPerson(), shareDef, msg)
					v.setEmailSentDate(new Date())
					viewerHome.update(v)
					
				}
				
	        } catch (e) {
	            logService.error("Error sending user email.", e)
	        }
		}
    }

    private void sendShareNotification(Person person, Portfolio portfolio, String ownerMsg) throws Exception {
        String subject = "A Portfolio folder has been shared with you"

        String fromEmail = portfolio.person.email        
        
        def binding = [
            "viewer": person,
            "share": portfolio,
            "url": Configuration.get("portfolio.base.url"),
            "institutionLongName": Configuration.get("institution.longName"),
            "institutionShortName": Configuration.get("institution.shortName"),
            "systemContactEmail": Configuration.get("portfolio.contact.email"),
            "ownerMessage": ownerMsg
        ]
        
        
        def engine = new SimpleTemplateEngine()
        def template = engine.createTemplate(emailText).make(binding)
        
        mailer.sendMail(template.toString(), subject, person.email, fromEmail)

    }
    
    def emailText = '''$ownerMessage

****************************************************************
$share.person.displayName has shared a Portfolio

This Portfolio's name is: $share.shareName
<% if (share.shareDesc) { %>
This Portfolio's description is: $share.shareDesc
<% } %>

You can access this portfolio at ${url}/portfolio/${share.shareId}

Before accessing the portfolio you will need to Login to Portfolio.

You will need to click the button 'Login' and use the following to access this Portfolio:

Your username is:   ${viewer.username}
Your Password is:   ${viewer.password}

Questions, comments, or concerns can be sent to $systemContactEmail.

****************************************************************
Portfolio is a secure web site at the $institutionLongName ($institutionShortName) for saving, organizing, viewing, and selectively sharing personal educational records.
'''

}
