/* $Name:  $ */
/* $Id: PerformanceDescriptor.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.assessment;

/**
 * This class represents an objective.
 * 
 * The purpose of this class is to encapsulate performance descriptors used 
 * for assessment score selection.
 */
public class PerformanceDescriptor implements Comparable<PerformanceDescriptor> {

    private Integer assessmentModelId;
    private Integer objectiveId;
    private String name=""; // Initialize to empty string when the object is created
    private String description=""; // Initialize to empty string when the object is created
    private String scoreValue;
    
    /**
     * Empty Constructor
     */
    public PerformanceDescriptor() {
        super();
    }

    public PerformanceDescriptor(Integer assessmentModelId, Integer objectiveId,String name,String description, String scoreValue){
        this.assessmentModelId = assessmentModelId;
        this.objectiveId = objectiveId;
        this.name = name;
        this.description = description;
        this.scoreValue = scoreValue;
    }
    public PerformanceDescriptor(int assessmentModelId, String name, String description) {
        this.assessmentModelId = assessmentModelId;
        this.name = name;
        this.description = description;
    }

    /**
     * Returns assessmentModel id of the performance descriptor
     */
    public Integer getAssessmentModelId() {
        return this.assessmentModelId;
    }
    
    /**
     * Gets the objective id of the performance descriptor
     * @return
     */
    public Integer getObjectiveId(){
        return this.objectiveId;
    }

    /**
     * Returns the name of this performance descriptor.
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Sets the name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description for this performance descriptor.
     */
    public String getDescription() {
        return this.description;
    }
    
    /**
     * Sets the Description field
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the score value of the performance Descriptor
     * @return
     */
    public String getScoreValue() {
        return scoreValue;
    }

    /**
     * Sets the score value of the performance Descriptor
     * @param scoreValue
     */
    public void setScoreValue(String scoreValue) {
        this.scoreValue = scoreValue;
    }

    /**
     * Returns a human-readable interperetation of this performance descriptor.
     */
    public String toString() {
        return this.getName();
    }

    /**
     * Returns the comparison between to performance descriptor objects.
     */
    public int compareTo(PerformanceDescriptor object) {
        int result = getName().compareTo(object.getName());
        if (result == 0) {
            result = getDescription().compareTo(object.getDescription());
        }
        return result;
    }
}
