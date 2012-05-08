/* $Name:  $ */
/* $Id: TemplateElementMapping.java,v 1.21 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.LogService;

public class TemplateElementMapping {

	protected LogService logService = new LogService(this.getClass());
	
    public static final Comparator<TemplateElementMapping> SORT_ORDER = new Comparator<TemplateElementMapping>() {
        public int compare(TemplateElementMapping o1, TemplateElementMapping o2) {
            return new Integer(o1.getSortOrder()).compareTo(new Integer(o2.getSortOrder()));
        }
    };

    private String id;
    private String templateId;

    private List<ElementDataObject> instances = new ArrayList<ElementDataObject>();

    private WizardElementDefinition wizardElementDefinition;
    private AssessmentModelAssignment assessmentModelAssignment;
    private int categoryId;
    private Integer sortOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemplate_id() {
        return templateId;
    }

    public void setTemplate_id(String template_id) {
        this.templateId = template_id;
    }

    /** 
     * @returns never null 
     */
    public List<ElementDataObject> getInstances() {
        return this.instances;
    }

    public void setInstances(List<? extends ElementDataObject> ins) {
        instances.clear();
        if (ins != null) {
            instances.addAll(ins);
            Collections.sort(instances, ElementDataObject.NAME_ORDER);
        }
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public void addInstance(ElementDataObject dataObj) {
        this.getInstances().add(dataObj);
    }

    public final WizardElementDefinition getWizardElementDefinition() {
        return wizardElementDefinition;
    }

    public final void setWizardElementDefinition(WizardElementDefinition wizardElementDefinition) {
    	
    	// logService.debug("setWizardElementDefinition: " +  wizardElementDefinition + " - wizardElementDefinition.getId(): " + wizardElementDefinition.getId());
    	
    	this.wizardElementDefinition = wizardElementDefinition;
    	
    }

    public final AssessmentModelAssignment getAssessmentModelAssignment() {
        return assessmentModelAssignment;
    }

    public final void setAssessmentModelAssignment(AssessmentModelAssignment assessmentModelAssignment) {
        this.assessmentModelAssignment = assessmentModelAssignment;
    }

    public String getClassName() {
        return wizardElementDefinition.getElementDefinition().getClassName();
    }

    public String getElementName() {
        return wizardElementDefinition.getElementName();
    }

    public String getElementTitle() {
        return wizardElementDefinition.getTitle();
    }

    public ElementDefinition getElementDefinition() {
    	
    	if (wizardElementDefinition == null) {
    		logService.debug("wizardElementDefinition is null");
    		return null;
    	}
    	
        return wizardElementDefinition.getElementDefinition();
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
