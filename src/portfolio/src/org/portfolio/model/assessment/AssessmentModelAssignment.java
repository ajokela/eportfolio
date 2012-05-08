/* $Name:  $ */
/* $Id: AssessmentModelAssignment.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.assessment;

import org.portfolio.model.PortfolioItemType;

/**
 * This class represents an assessment model. The purpose of this class is to
 * encapsulate assessment models used to define assessment criterion.
 */
public class AssessmentModelAssignment {

    private Integer identifier;
    private AssessmentModel assessmentModel;
    private Integer assessedItemId;
    private PortfolioItemType portfolioItemType;
    private boolean committeeBased;
    private boolean anonymous;

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    /**
     * Returns a unique identifier for this assessment model assignment.
     */
    public Integer getIdentifier() {
        return this.identifier;
    }

    /**
     * Sets the identifier for the assessment model assignment.
     * @param assessmentModelAssignmentId
     */
    public void setIdentifier(Integer identifier) {
        this.identifier = identifier;
    }

    /**
     * Returns the identifier for the assigned assessment model.
     */
    public AssessmentModel getAssessmentModel() {
        return this.assessmentModel;
    }

    /**
     * Sets the identifier for the assigned assessment model.
     * @param assessmentModelId
     */
    public void setAssessmentModel(AssessmentModel assessmentModel) {
        this.assessmentModel = assessmentModel;
    }

    /**
     * Returns the identifier for the item assigned an assessment model.
     */
    public Integer getAssessedItemId() {
        return this.assessedItemId;
    }

    /**
     * Sets the identifier for the item assigned an assessment model.
     * @param assessedItemlId
     */
    public void setAssessedItemId(Integer assessedItemId) {
        this.assessedItemId = assessedItemId;
    }

	public boolean isCommitteeBased() {
		return committeeBased;
	}

	public void setCommitteeBased(boolean committeeBased) {
		this.committeeBased = committeeBased;
	}

	public PortfolioItemType getPortfolioItemType() {
		return portfolioItemType;
	}

	public void setPortfolioItemType(PortfolioItemType portfolioItemType) {
		this.portfolioItemType = portfolioItemType;
	}
}
