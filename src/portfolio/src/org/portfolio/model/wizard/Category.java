/* $Name:  $ */
/* $Id: Category.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.wizard;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an organization category within an entry wizard.
 */
public class Category {

    private Integer id;
    private List<Category> categories;
    private List<WizardElementDefinition> wizardElementDefinitions;
    private String title;
    private Integer wizardId;
    private Integer parentCategoryId;
    private Integer sortOrder;

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Integer getWizardId() {
        return wizardId;
    }

    public void setWizardId(Integer wizardId) {
        this.wizardId = wizardId;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    /**
     * Returns a <code>List</code> of categories contained within this category
     * or an empty list if none found. Never null.
     */
    public List<Category> getCategories() {
        return this.categories == null ? new ArrayList<Category>() : categories;
    }

    /**
     * Returns a <code>List</code> of wizard element definitions contained
     * within this category or an empty list if none found. Never null.
     */
    public List<WizardElementDefinition> getWizardElementDefinitions() {
        return this.wizardElementDefinitions == null ? new ArrayList<WizardElementDefinition>() : wizardElementDefinitions;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCategories(List<Category> childCategoryList) {
        this.categories = childCategoryList;
    }

    public void setWizardElementDefinitions(List<WizardElementDefinition> wizardElementDefinitionList) {
        this.wizardElementDefinitions = wizardElementDefinitionList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
