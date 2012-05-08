/* $Name:  $ */
/* $Id: CommentSaveAction.java,v 1.16 2011/03/01 20:41:39 ajokela Exp $ */
package org.portfolio.client.action.comment;

import groovy.lang.Writable;
import groovy.text.SimpleTemplateEngine;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionRedirect;
import org.portfolio.bus.CommentsManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.Comment;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.util.Configuration;
import org.portfolio.util.Mailer;
import org.portfolio.util.RequiredInjection;
import org.portfolio.util.StringEncryptor;

public class CommentSaveAction extends BaseAction {

    private CommentsManager commentManager;
    private StringEncryptor stringEncryptor;
    private Mailer mailer;
    private PersonHome personHome;
    private PortfolioHome portfolioHome;
 
    private String emailText = "" +

    	"****************************************************************\n" +
    	"Hello ${owner.firstName}:\n" +
    	"\n" +
    	"${creator.displayName} has left you a comment.\n" +
    	"\n" +
    	"\"$textComment\"\n" +
    	"\n" + 
    	"\n" +
    	"The comment is on your portfolio, titled \"${share.shareName}\"\n" +
    	"The portfolio can be viewed at $url/portfolio/${share.shareId}\n" + 
    	"Questions, comments, or concerns can be sent to $systemContactEmail.\n\n" +
    	
    	"****************************************************************\n" + 
    	"Portfolio is a secure web site at the $institutionLongName ($institutionShortName) for saving, organizing, viewing, and selectively sharing personal educational records.\n";


    
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String encryptedOwnerId = request.getParameter("ownerId");
        String ownerId = stringEncryptor.decrypt(encryptedOwnerId);

        Portfolio share;
        
        Comment comment = new Comment();
        comment.setOwner(ownerId);
        comment.setElementClassName(request.getParameter("elementClassName"));
        comment.setEntryId(new BigDecimal(request.getParameter("entryId")));
        comment.setCommentDate(new Date());
        comment.setCommentText(request.getParameter("commentText"));
        comment.setVisibility(new Integer(request.getParameter("visibility")));
        comment.setCreator(getPersonId(request));
        
        commentManager.insert(comment);
        
        share = portfolioHome.findByShareId(comment.getEntryId().toString());
        
        if(comment.getElementClassName().equals(Portfolio.class.getName()) && share != null) {
	        
	        Map<String, Object> binding = new HashMap<String, Object>();
	        
	        binding.put("creator", getPerson(request));
	        binding.put("owner", personHome.findByPersonId(ownerId));
	        binding.put("date", comment.getCommentDate());
	        binding.put("institutionLongName", Configuration.get("institution.longName"));
	        binding.put("institutionShortName", Configuration.get("institution.shortName"));
	        binding.put("systemContactEmail", Configuration.get("portfolio.contact.email"));
	        binding.put("textComment", comment.getCommentText());
	        binding.put("url", Configuration.get("portfolio.base.url"));
	        binding.put("share", share);
	        
	        
	        SimpleTemplateEngine engine = new SimpleTemplateEngine();
	        Writable template = engine.createTemplate(emailText).make(binding);
        
	        
	        mailer.sendMail(template.toString(), "New ePortfolio Comment - '" + share.getShareName() == null ? "No Portfolio Name" : share.getShareName() + "'" , personHome.findByPersonId(ownerId).getEmail(), getPerson(request).getEmail());
	        
	        logService.debug("Email sent");
        }
        
        ActionRedirect actionRedirect = new ActionRedirect(mapping.findForward("success"));
        if (comment.getElementClassName().equals(Portfolio.class.getName())) {
            actionRedirect.addParameter("shareId", comment.getEntryId().toString());
        } else if (comment.getElementClassName().equals(ShareEntry.class.getName())) {
            actionRedirect.addParameter("shareEntryId", comment.getEntryId().toString());
        } else if (comment.getElementClassName().equals(Assessment.class.getName())) {
            actionRedirect.addParameter("assessmentId", comment.getEntryId().toString());
        }
        
        actionRedirect.addParameter("wrapperId", request.getParameter("wrapperId"));
        actionRedirect.addParameter("showComments", true);
        return actionRedirect;
    }

    @RequiredInjection
    public void setCommentManager(CommentsManager commentManager) {
        this.commentManager = commentManager;
    }

    @RequiredInjection
    public void setStringEncryptor(StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }
    
    @RequiredInjection
    public void setPersonHome(PersonHome personHome) {
    	this.personHome = personHome;
    }
    
    @RequiredInjection
    public void setMailer(Mailer mailer) {
    	this.mailer = mailer;
    }
    
    @RequiredInjection
    public void setPortfolioHome(PortfolioHome portfolioHome) {
    	this.portfolioHome = portfolioHome;
    }
    
}
