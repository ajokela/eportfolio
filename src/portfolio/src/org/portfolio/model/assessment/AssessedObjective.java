/* $Name:  $ */
/* $Id: AssessedObjective.java,v 1.7 2010/12/21 21:10:44 ajokela Exp $ */
package org.portfolio.model.assessment;

import java.util.ArrayList;
import java.util.List;

import org.portfolio.util.LogService;

/**
 * This class represents an assignment of an objective to an assessment Model.
 * The purpose of this class is to encapsulate assessed objectives, capturing
 * display sequence and performance descriptors used to describe assessment
 * criterion.
 */
public class AssessedObjective extends Objective {

    private Integer assessmentModelId;
    private Integer displaySequence;
    private int level; // Used for RENDERING of objectives in a hierarchy

    private boolean selected; //  Used if this objective is selected among list of objectives
    private LogService logService = new LogService(this.getClass());
    
    // Entered in step 5 of create assessment model. 
    private List<PerformanceDescriptor> performanceDescriptors;

    public Integer getAssessedObjectiveId() {
    	
    	try {
	        StringBuilder buff = new StringBuilder();
	        buff.append(assessmentModelId);
	        buff.append(id);
	        return Integer.parseInt(buff.toString());
    	}
    	catch (Exception e) {
    		logService.error(e.getLocalizedMessage());
    	}
    	
    	return -1;
    }
    
    /**
     * Returns the display sequence of this assessed objective.
     */
    public Integer getDisplaySequence() {
        return displaySequence;
    }

    /**
     * Sets the display sequence for the given assessed objective.
     * @param dislpaySequence
     */
    public void setDisplaySequence(Integer displaySequence) {
        this.displaySequence = displaySequence;
    }

    /**
     * Sets the List of PerformanceDescriptors for this AssessedObjective.
     * @return
     */
    public List<PerformanceDescriptor> getPerformanceDescriptors() {
        if(performanceDescriptors == null)
            performanceDescriptors = new ArrayList<PerformanceDescriptor>();
        
        return performanceDescriptors;
    }

    /**
     * Sets the assessment objective List with the given list of objectives
     * @param objectives
     */
    public void setPerformanceDescriptors(List<PerformanceDescriptor> performanceDescriptors) {
        this.performanceDescriptors = performanceDescriptors;
    }
    
    public PerformanceDescriptor getPerformanceDescriptor(int index){
        return this.performanceDescriptors.get(index);
    }

    /**
     * Used in hierarchial representation of List of Objectives
     * @return
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets the level of the objective among list of objectives
     * @param level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Returns if the objective is selected
     * @return
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets the selected flag of an objective
     * @param selected
     */
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * Returns a human-readable interperetation of this assessed objective.
     */
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString()).append("\n");

        // for this instance.
        buff.append("assessedObjectiveId=").append(getAssessedObjectiveId()).append(",");
        buff.append("displaySequence=").append(getDisplaySequence()).append(",");
        buff.append("assessedObjective=").append(getName()).append(",");
        buff.append("performanceDescriptors=").append(getPerformanceDescriptors().size());
        return buff.toString();
    }

    public Integer getAssessmentModelId() {
        return assessmentModelId;
    }

    public void setAssessmentModelId(Integer assessmentModelId) {
        this.assessmentModelId = assessmentModelId;
    }
}
