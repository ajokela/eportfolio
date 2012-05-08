/* $Name:  $ */
/* $Id: AssessmentModel.java,v 1.15 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.assessment;

import java.util.Date;
import java.util.List;

/**
 * This class represents an assessment model.
 * 
 * The purpose of this class is to encapsulate assessment models used to define
 * assessment criterion.
 */
public class AssessmentModel implements Comparable<AssessmentModel> {

    private Integer id;
    private Integer communityId;
    private Integer parentAssesmentModelId;
    private String name;
    private String description;
    private String format;
    private String releaseStatus;
    private Date createdDate;
    private ScoringModel scoringModel;
    private List<AssessedObjective> assessedObjectives;

    public ScoringModel getScoringModel() {
        return this.scoringModel;
    }

    public void setScoringModel(ScoringModel scoringModel) {
        this.scoringModel = scoringModel;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AssessedObjective> getAssessedObjectives() {
        return this.assessedObjectives;
    }

    public void setAssessedObjectives(List<AssessedObjective> assessedObjectives) {
        this.assessedObjectives = assessedObjectives;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(String releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public Date getDateCreated() {
        return createdDate;
    }

    public void setDateCreated(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getParentAssesmentModelId() {
        return parentAssesmentModelId;
    }

    public void setParentAssessmentModelId(Integer parentAssesmentModelId) {
        this.parentAssesmentModelId = parentAssesmentModelId;
    }

    /**
     * @return the index or -1 if not found
     */
    public int getObjectiveIndex(Objective objective) {
        int index = 0;
        for (AssessedObjective assessedObjective : getAssessedObjectives()) {
            if (assessedObjective.getId().equals(objective.getId())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int compareTo(AssessmentModel object) {
        int result = getFormat().compareTo(object.getFormat());
        if (result == 0) {
            result = getDescription().compareTo(object.getDescription());
        }
        return result;
    }
}
