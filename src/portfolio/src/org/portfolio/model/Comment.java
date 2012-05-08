/* $Name:  $ */
/* $Id: Comment.java,v 1.16 2010/10/27 19:24:57 ajokela Exp $ */
/**
 *
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Comment.java,v 1.16 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.16 $
 * $Date: 2010/10/27 19:24:57 $
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
 *
 */

package org.portfolio.model;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.dao.PersonHome;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.util.PortfolioConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class Comment implements Comparable<Comment> {

    public static int VISIBILITY_PRIVATE = 1;
    public static int VISIBILITY_SHARED = 2;
    public static int VISIBILITY_PUBLIC = 3;

    public static int MATERIAL_TYPE = 1;
    public static int SHARE_TYPE = 2;
    public static int ELEMENT_TYPE = 3;
    public static int SHARE_ENTRY_TYPE = 4;
    public static int ASSESSMENT_TYPE = 5;

    public static Comparator<Comment> TITLE_COMPARATOR = new Comparator<Comment>() {
        public int compare(Comment o1, Comment o2) {
            return o1.getCommentTitle().compareToIgnoreCase(o2.getCommentTitle());
        }
    };

    @SuppressWarnings("unused")
	private static final long serialVersionUID = 6362118266947602941L;

    @Autowired
    private PersonHome personHome;

    private long commentId = 0;
    private String elementClassName;
    private Date commentDate;
    private String commentTitle;
    private String commentText;
    private Integer visibility = VISIBILITY_PUBLIC;
    private Person creator;
    private Person owner;
    private BigDecimal entryId;
    

    public BigDecimal getEntryId() {
        return entryId;
    }

    public void setEntryId(BigDecimal entryId) {
        this.entryId = entryId;
    }

    public long getCommentId() {
        return commentId;
    }

    public boolean isNew() {
        return getCommentId() == 0;
    }

    public Integer getVisibility() {
        return visibility;
    }

    public void setVisibility(Integer visibility) {
        this.visibility = visibility;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public java.lang.String getElementClassName() {
        return (elementClassName != null ? elementClassName : "");
    }

    public void setElementClassName(java.lang.String elementClassName) {
        this.elementClassName = elementClassName;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(java.util.Date commentDate) {
        this.commentDate = commentDate;
    }

    public java.lang.String getCommentTitle() {
        return (commentTitle != null ? commentTitle : "");
    }

    public void setCommentTitle(java.lang.String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public java.lang.String getCommentText() {
        return (commentText != null ? commentText : "");
    }

    public void setCommentText(java.lang.String commentText) {
        this.commentText = commentText;
    }

    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if ((commentTitle == null) || (commentTitle.trim().length() == 0)) {
            errors.add("commentTitle", new ActionMessage("error.required.missing", "Comment title"));
        }
        if ((commentText != null) && (commentText.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            commentText = commentText.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("commentText", new ActionMessage("error.lengthTooLong", "Comment", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        return errors;
    }

    /**
     * date order
     */
    public int compareTo(Comment c2) {
        return getCommentDate().compareTo(c2.getCommentDate());
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(String ownerId) {
        this.owner = personHome.findByPersonId(ownerId);
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = personHome.findByPersonId(creator);
    }

    public int getType() {
        if (getElementClassName().equals(UploadedMaterial.class.getName())) {
            return Comment.MATERIAL_TYPE;
        } else if (getElementClassName().equals(Portfolio.class.getName())) {
            return Comment.SHARE_TYPE;
        } else if (getElementClassName().equals(ShareEntry.class.getName())) {
            return Comment.SHARE_ENTRY_TYPE;
        } else if (getElementClassName().equals(Assessment.class.getName())) {
            return Comment.ASSESSMENT_TYPE;
        } else {
            return Comment.ELEMENT_TYPE;
        }
    }
}
