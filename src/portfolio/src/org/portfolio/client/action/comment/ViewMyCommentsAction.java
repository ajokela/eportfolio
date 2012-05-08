/* $Name:  $ */
/* $Id: ViewMyCommentsAction.java,v 1.18 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.comment;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.CommentsManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.model.Comment;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.UploadedMaterial;
import org.portfolio.model.assessment.Assessment;

public class ViewMyCommentsAction extends BaseAction {

    private CommentsManager commentsManager;
    private PortfolioHome shareDefinitionHome;
    private ShareEntryHome shareEntryHome;
    private AssessmentManager assessmentManager;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Person person = getPerson(request);
        List<Comment> comments = commentsManager.getCommentsByCreator(person.getPersonId());
        Collections.sort(comments, Comment.TITLE_COMPARATOR);
        request.setAttribute("commentList", comments);

        Hashtable<Long, ElementDataObject> elements = new Hashtable<Long, ElementDataObject>();
        Hashtable<BigDecimal, ElementDefinition> elementDefs = new Hashtable<BigDecimal, ElementDefinition>();
        Hashtable<Long, Portfolio> shares = new Hashtable<Long, Portfolio>();
        Hashtable<Long, UploadedMaterial> materials = new Hashtable<Long, UploadedMaterial>();
        Hashtable<Long, ShareEntry> shareEntries = new Hashtable<Long, ShareEntry>();
        Hashtable<Long, Assessment> assessments = new Hashtable<Long, Assessment>();

        for (Iterator<Comment> i = comments.iterator(); i.hasNext();) {
            Comment comment = (Comment) i.next();
            if (comment.getType() == Comment.SHARE_TYPE) {
                Portfolio share = null;
                try {
                    share = (Portfolio) shareDefinitionHome.findByShareId(comment.getEntryId().toString());
                } catch (Exception ex) {
                    logService.error("Exception caught when attempting to find share for comment. (pid, comid)" + person.getPersonId()
                            + ", " + comment.getEntryId());
                }
                if (share != null) {
                    shares.put(new Long(comment.getCommentId()), share);
                } else {
                    // do not show orphaned comments
                    i.remove();
                }
            } else if (comment.getType() == Comment.SHARE_ENTRY_TYPE) {
                ShareEntry shareEntry;
                Portfolio share = null;
                try {
                    shareEntry = shareEntryHome.findByShareEntryId(comment.getEntryId().intValue());
                    shareEntries.put(comment.getCommentId(), shareEntry);
                    share = (Portfolio) shareDefinitionHome.findByShareId(shareEntry.getShareId().toString());
                    shares.put(comment.getCommentId(), share);
                } catch (Exception ex) {
                    logService.error("Exception caught when attempting to find share for comment. (pid, comid)" + person.getPersonId()
                            + ", " + comment.getEntryId());
                }
                if (share == null) {
                    // do not show orphaned comments
                    i.remove();
                }
            } else if (comment.getType() == Comment.ASSESSMENT_TYPE) {
                Portfolio share = null;
                try {
                    Assessment assessment = assessmentManager.findAssessmentById(comment.getEntryId().intValue());
                    assessments.put(comment.getCommentId(), assessment);
                    String shareId = assessment.getShareId();
                    share = (Portfolio) shareDefinitionHome.findByShareId(shareId);
                    shares.put(comment.getCommentId(), share);
                } catch (Exception ex) {
                    logService.error("Exception caught when attempting to find share for comment. (pid, comid)" + person.getPersonId()
                            + ", " + comment.getEntryId());
                }
                if (share != null) {
                    shares.put(new Long(comment.getCommentId()), share);
                } else {
                    // do not show orphaned comments
                    i.remove();
                }
            }
        }
        request.setAttribute("elements", elements);
        request.setAttribute("materials", materials);
        request.setAttribute("shares", shares);
        request.setAttribute("elementDefs", elementDefs);
        request.setAttribute("shareEntries", shareEntries);
        request.setAttribute("assessments", assessments);
        return mapping.findForward("success");
    }

    public void setCommentsManager(CommentsManager commentsManager) {
        this.commentsManager = commentsManager;
    }

    public void setShareDefinitionHome(PortfolioHome shareDefinitionHome) {
        this.shareDefinitionHome = shareDefinitionHome;
    }

    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }

    public void setAssessmentManager(AssessmentManager assessmentManager) {
        this.assessmentManager = assessmentManager;
    }
}
