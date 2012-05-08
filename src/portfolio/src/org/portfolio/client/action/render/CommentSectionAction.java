/* $Name:  $ */
/* $Id: CommentSectionAction.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.render;

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
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.Assessment;

/**
 * @author Matt Sheehan
 * 
 */
public class CommentSectionAction extends BaseAction {

    private CommentsManager commentsManager;
    private PortfolioHome shareDefinitionHome;
    private ShareEntryHome shareEntryHome;
    private AssessmentManager assessmentManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Person person = getPerson(request);
        String shareIdParam = request.getParameter("shareId");
        String shareEntryIdParam = request.getParameter("shareEntryId");
        String assessmentIdParam = request.getParameter("assessmentId");

        List<Comment> commentList;
        Object commentParent;
        boolean isAnonymous;
        String parentType;
        String entryId;
        
        Portfolio portfolio;

        if (!isEmpty(shareIdParam)) {
            portfolio = shareDefinitionHome.findByShareId(shareIdParam);
            commentList = commentsManager.getCommentList(portfolio, person.getPersonId());
            commentParent = portfolio;
            isAnonymous = false;
            parentType = "portfolio";
            entryId = portfolio.getShareId();
        } else if (!isEmpty(shareEntryIdParam)) {
            ShareEntry shareEntry = shareEntryHome.findByShareEntryId(Integer.parseInt(shareEntryIdParam));
            commentList = commentsManager.getCommentList(shareEntry, person.getPersonId());
            commentParent = shareEntry;
            portfolio = shareDefinitionHome.findByShareId(shareEntry.getShareId());
            isAnonymous = false;
            parentType = "portfolio element";
            entryId = shareEntry.getId().toString();
        } else {
            Assessment assessment = assessmentManager.findAssessmentById(Integer.parseInt(assessmentIdParam));
            commentList = commentsManager.getCommentList(assessment, person.getPersonId());
            commentParent = assessment;
            portfolio = shareDefinitionHome.findByShareId(assessment.getShareId());
            if (assessment.getAssessedItemType() == PortfolioItemType.PORTFOLIO) {
                isAnonymous = portfolio.getAssessmentModelAssignment().isAnonymous();
            } else {
                ShareEntry shareEntry = shareEntryHome.findByShareEntryId(assessment.getAssessedItemId());
                isAnonymous = shareEntry.getAssessmentModelAssignment().isAnonymous();
            }
            parentType = "assessment";
            entryId = assessment.getId().toString();
        }
        

        String ownerId = portfolio.getPersonId();

        request.setAttribute("commentList", commentList);
        request.setAttribute("commentParent", commentParent);
        request.setAttribute("isAnonymous", isAnonymous);
        request.setAttribute("parentType", parentType);
        request.setAttribute("entryId", entryId);
        request.setAttribute("ownerId", ownerId);

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
