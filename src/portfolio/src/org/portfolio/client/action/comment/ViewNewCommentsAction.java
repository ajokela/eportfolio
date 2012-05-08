/* $Name:  $ */
/* $Id: ViewNewCommentsAction.java,v 1.18 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/action/comment/ViewNewCommentsAction.java,v 1.18 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.18 $
 * $Date: 2010/10/27 19:24:56 $
 *
 * =====================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 *
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Copyrights:
 *
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 *
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Portions Copyright (c) 2003 The University of Delaware.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */

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

public class ViewNewCommentsAction extends BaseAction {

    private CommentsManager commentsManager;
    private PortfolioHome portfolioHome;
    private ShareEntryHome shareEntryHome;
    private AssessmentManager assessmentManager;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Person person = getPerson(request);
        List<Comment> comments = commentsManager.getCommentsByOwner(person.getPersonId());
        Collections.sort(comments);
        request.setAttribute("commentList", comments);

        Hashtable<Long, ElementDataObject> elements = new Hashtable<Long, ElementDataObject>();
        Hashtable<BigDecimal, ElementDefinition> elementDefs = new Hashtable<BigDecimal, ElementDefinition>();
        Hashtable<Long, Portfolio> shares = new Hashtable<Long, Portfolio>();
        Hashtable<Long, ShareEntry> shareEntries = new Hashtable<Long, ShareEntry>();
        Hashtable<Long, UploadedMaterial> materials = new Hashtable<Long, UploadedMaterial>();
        Hashtable<Long, Assessment> assessments = new Hashtable<Long, Assessment>();

        for (Iterator<Comment> i = comments.iterator(); i.hasNext();) {
            Comment comment = (Comment) i.next();
            int commentType = comment.getType();
            if (commentType == Comment.SHARE_TYPE) {
                Portfolio share = null;
                try {
                    share = (Portfolio) portfolioHome.findByShareId(comment.getEntryId().toString());
                } catch (Exception ex) {
                    logService.error("Exception caught when attempting to find share for comment. (pid, comid)" + person.getPersonId()
                            + ", " + comment.getEntryId());
                }
                if (share != null) {
                    shares.put(comment.getCommentId(), share);
                } else {
                    // do not show orphaned comments
                    i.remove();
                }
            } else if (commentType == Comment.SHARE_ENTRY_TYPE) {
                ShareEntry shareEntry;
                Portfolio share = null;
                try {
                    shareEntry = shareEntryHome.findByShareEntryId(comment.getEntryId().intValue());
                    shareEntries.put(comment.getCommentId(), shareEntry);
                    share = (Portfolio) portfolioHome.findByShareId(shareEntry.getShareId().toString());
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
                    share = (Portfolio) portfolioHome.findByShareId(shareId);
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
        request.setAttribute("shareEntries", shareEntries);
        request.setAttribute("elementDefs", elementDefs);
        request.setAttribute("assessments", assessments);

        return mapping.findForward("success");
    }

    public void setCommentsManager(CommentsManager commentsManager) {
        this.commentsManager = commentsManager;
    }

    public void setPortfolioHome(PortfolioHome portfolioHome) {
        this.portfolioHome = portfolioHome;
    }

    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }

    public void setAssessmentManager(AssessmentManager assessmentManager) {
        this.assessmentManager = assessmentManager;
    }
}
