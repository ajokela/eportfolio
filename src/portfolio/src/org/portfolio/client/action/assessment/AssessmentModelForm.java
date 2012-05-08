/* $Name:  $ */
/* $Id: AssessmentModelForm.java,v 1.11 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.assessment;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.ScoringModel;

/**
 * 
 * @author Vijay Rajagopal Dec 4, 2008
 */
public class AssessmentModelForm extends ActionForm {

    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer parentAssessmentModelId;
    private String name;
    private String description;
    private String format;
    private String scoringId = "0";
    private List<AssessedObjective> communityObjectives = null;
    private List<AssessedObjective> parentCommunityObjectives = null;

    // Selected objectives in step 3 of create assessment model : Automatically
    // populated
    // based on communityObjectives using selected and order fields of Objective
    // Objectives.order is used for Display Sequence
    private List<AssessedObjective> assessedObjectives = new ArrayList<AssessedObjective>();
    private ScoringModel scoringModel;
    private Integer communityId;
    private String releaseStatus;

    /**
     * Returns a <code>Boolean</code> determining whether the asessment model
     * identifier exists.
     */
    public Boolean isNew() {
        return this.id == null;
    }

    /**
     * Returns a <code>List</code> of the <code>Objective</code>s for this
     * assessment model.
     */
    public List<AssessedObjective> getCommunityObjectives() {
        return this.communityObjectives;
    }

    /**
     * Sets the objective List with the given list of objectives
     * 
     * @param objectives
     */
    public void setCommunityObjectives(List<AssessedObjective> objectives) {
        this.communityObjectives = objectives;
    }

    /**
     * Returns a <code>List</code> of the <code>Objective</code>s for this
     * assessment model.
     */
    public List<AssessedObjective> getParentCommunityObjectives() {
        return this.parentCommunityObjectives;
    }

    /**
     * Sets the objective List with the given list of objectives
     * 
     * @param objectives
     */
    public void setParentCommunityObjectives(List<AssessedObjective> parentCommunityObjectives) {
        this.parentCommunityObjectives = parentCommunityObjectives;
    }

    /**
     * Gets the selected Objectives
     * 
     * @return
     */
    public List<AssessedObjective> getAssessedObjectives() {
        List<AssessedObjective> objectives = new ArrayList<AssessedObjective>();
        if (this.communityObjectives != null) {
            objectives.addAll(this.communityObjectives);
        }
        if (this.parentCommunityObjectives != null && this.parentCommunityObjectives.size() > 0) {
            objectives.addAll(this.parentCommunityObjectives);
        }
        for (AssessedObjective obj : objectives) {
            if (obj.isSelected()) {
                // Selected and not contained in assessmentObjectives yet - add
                // it
                if (!assessedObjectives.contains(obj)) {
                    assessedObjectives.add(obj);
                }
            } else if (assessedObjectives.contains(obj)) {// Not Selected but
                // contained in
                // assessmentObjectives
                // Implies - remove
                // it

                assessedObjectives.remove(obj);
            }
        }

        int count = 0;
        for (AssessedObjective obj : assessedObjectives) {
            // Set the order of the objective among the list of selected
            // objectives
            obj.setDisplaySequence(count++);
        }
        return assessedObjectives;
    }

    /**
     * Sets the assessed Objectives
     * 
     * @param assessedObjectives
     */
    public void setAssessedObjectives(List<AssessedObjective> assessedObjectives) {
        this.assessedObjectives = assessedObjectives;
    }

    /**
     * Returns a unique identifier for this assessment model.
     */
    public Integer getIdentifier() {
        return this.id;
    }

    /**
     * Sets the identifier for the assessment model
     * 
     * @param identifier
     */
    public void setIdentifier(Integer identifier) {
        this.id = identifier;
    }

    /**
     * Gets the Parent Identifier
     * 
     * @return
     */
    public Integer getParentAssessmentModelId() {
        return parentAssessmentModelId;
    }

    /**
     * Sets the parent Assessment Model Identifier
     * 
     * @param parentId
     */
    public void setParentAssessmentModelId(Integer parentAssessmentModelId) {
        this.parentAssessmentModelId = parentAssessmentModelId;
    }

    /**
     * Returns the name of this assessment model.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the Rubric name with the given parameter
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description for this assessment model.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description of the rubric
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the format for this assessment model.
     */
    public String getFormat() {
        return this.format;
    }

    /**
     * Sets the format of the assessment model
     * 
     * @param format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Scoring Type can be 0 for 5 level rubric 1 for 4 level rubric 2 for 4
     * level text rubric
     * 
     * @return
     */
    public String getScoringId() {
        return scoringId;
    }

    /**
     * Scoring Type can be 0 for 5 level rubric 1 for 4 level rubric 2 for 4
     * level text rubric
     */
    public void setScoringId(String scoringId) {
        this.scoringId = scoringId;

    }

    /**
     * Gets the Scoring Model Used only to convert ActionForm to Model
     * 
     * @return
     */
    public ScoringModel getScoringModel() {
        return scoringModel;
    }

    /**
     * Sets the scoring model Used only to convert ActionForm to Model
     * 
     * @param scoringModel
     */
    public void setScoringModel(ScoringModel scoringModel) {
        this.scoringModel = scoringModel;
    }

    /**
     * Convinience method that splits values in the valueSet into array
     * 
     * @return
     */
    public String[] getValues() {
        return getScoringModel().getScores();
    }

    /**
     * Gets the community Id
     * 
     * @return
     */
    public Integer getCommunityId() {
        return communityId;
    }

    /**
     * sets the community id
     * 
     * @param communityId
     */
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    /**
     * Gets the release status
     * 
     * @return
     */
    public String getReleaseStatus() {
        return releaseStatus == null ? "DRAFT" : releaseStatus; // TODO(DRAFT is
        // constant)

    }

    /**
     * Sets the release status
     * 
     * @param releaseStatus
     */
    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }
}
