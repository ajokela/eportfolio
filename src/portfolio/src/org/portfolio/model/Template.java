/* $Name:  $ */
/* $Id: Template.java,v 1.20 2010/12/21 21:10:44 ajokela Exp $ */
package org.portfolio.model;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class Template {

    @Autowired
    private TemplateElementMappingHome templateElementFormHome;

    public static final Comparator<Template> NAME_ORDER = new Comparator<Template>() {
        public int compare(Template o1, Template o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    private String id;
    private String name;
    private String description;
    private Date dateCreated;
    private Date dateModified;
    private boolean published;
    private List<TemplateElementMapping> elements;
    private AssessmentModelAssignment assessmentModelAssignment;
    private Integer communityId;
    private boolean assessable;
    private boolean deleted;

    public String getId() {
        return id;
    }
    
    public String getTemplateId() {
    	return getId();
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public List<TemplateElementMapping> getElements() {
        if (elements == null) {
            elements = templateElementFormHome.findByTemplateId(id);
        }
        return elements;
    }

    public AssessmentModelAssignment getAssessmentModelAssignment() {
        return assessmentModelAssignment;
    }

    public void setAssessmentModelAssignment(AssessmentModelAssignment assessmentModelAssignment) {
        this.assessmentModelAssignment = assessmentModelAssignment;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public boolean isAssessable() {
        return assessable;
    }

    public void setAssessable(boolean assessable) {
        this.assessable = assessable;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Template && id.equals(((Template) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
