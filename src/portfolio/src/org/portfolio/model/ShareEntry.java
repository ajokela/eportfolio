/* $Name:  $ */
/* $Id: ShareEntry.java,v 1.29 2011/03/22 16:04:05 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModelAssignment;

/**
 * This class encapsulates ShareEntry element object.
 * 
 */
public class ShareEntry {

    public static final Comparator<ShareEntry> SORT_ORDER = new Comparator<ShareEntry>() {
        public int compare(ShareEntry o1, ShareEntry o2) {
            return new Integer(o1.sortOrder).compareTo(new Integer(o2.sortOrder));
        }
    };

    private Integer id;

    /* actually element.entryId */
    private BigDecimal entryIndex;

    private String shareId;
    private ElementDataObject element;
    private int sortOrder;
    private AssessmentModelAssignment assessmentModelAssignment;
    private List<Assessment> assessmentList = new ArrayList<Assessment>();
    private List<Comment> commentList;
    private TemplateElementMapping templateElementMapping;
    private boolean is_deleted = false;
    private boolean is_hidden = false;
    
    public void setIsDeleted(boolean is_deleted) {
    	this.is_deleted = is_deleted;
    }
    
    public boolean isDeleted() {
    	return is_deleted;
    }

    public String getElementName() {
        return templateElementMapping.getElementName();
    }
    
    public String getElementTitle() {
    	return templateElementMapping.getElementTitle();
    }

    public String getElementId() {
        return templateElementMapping.getId();
    }

    public String getShareId() {
        return (shareId != null ? shareId : "");
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
    }

    public java.math.BigDecimal getEntryIndex() {
        return (entryIndex != null ? entryIndex : null);
    }

    public void setEntryIndex(java.math.BigDecimal entryIndex) {
        this.entryIndex = entryIndex;
    }

    public ElementDataObject getElement() {
        return element;
    }

    public void setElement(ElementDataObject element) {
        this.element = element;
    }

    public int getCategoryId() {
        return templateElementMapping.getCategoryId();
    }

    public int getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public AssessmentModelAssignment getAssessmentModelAssignment() {
        return assessmentModelAssignment;
    }

    public void setAssessmentModelAssignment(AssessmentModelAssignment assessmentModelAssignment) {
        this.assessmentModelAssignment = assessmentModelAssignment;
    }

    public List<Assessment> getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(List<Assessment> assessmentList) {
        this.assessmentList = assessmentList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Deprecated
    public Integer getIdentifier() {
        return id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TemplateElementMapping getTemplateElementMapping() {
        return templateElementMapping;
    }

    public void setTemplateElementMapping(TemplateElementMapping templateElementMapping) {
        this.templateElementMapping = templateElementMapping;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ShareEntry && this.id.equals(((ShareEntry) obj).id);
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

	public void setIsHidden(boolean is_hidden) {
		this.is_hidden = is_hidden;
	}

	public boolean isIsHidden() {
		return is_hidden;
	}
}
