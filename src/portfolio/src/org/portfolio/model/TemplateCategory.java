/* $Name:  $ */
/* $Id: TemplateCategory.java,v 1.27 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.portfolio.dao.template.TemplateCategoryHome;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * This class represents an organization category within a Template.
 */
@Configurable
public class TemplateCategory {

	private Integer id;
	private Integer parentCategoryId;
	private String templateId;
	private String title;
	private List<TemplateCategory> subcategories;
	private List<TemplateElementMapping> templateElementDefs;
	private List<ShareEntry> shareEntries;
	private Integer sortOrder;
	@SuppressWarnings("unused")
	private final LogService logService = new LogService(this.getClass());
	
	@Autowired
	private TemplateCategoryHome categoryHome;

	public List<TemplateCategory> getSubcategories() {
		if (this.subcategories == null) {
			this.subcategories = categoryHome.findChildren(id);
		}
		return this.subcategories;
	}

	public List<TemplateElementMapping> getTemplateElementDefs() {
		return this.templateElementDefs == null ? new ArrayList<TemplateElementMapping>() : templateElementDefs;
	}

	public void setTemplateElementDefs(List<TemplateElementMapping> tempList) {
		
		// logService.debug(" --> setTemplateElementDefs()");
		
		List<TemplateElementMapping> myList = new ArrayList<TemplateElementMapping>();
		if (tempList != null) {
			myList.addAll(tempList);
		}
		Collections.sort(myList, TemplateElementMapping.SORT_ORDER);
		this.templateElementDefs = myList;
	}

	public List<ShareEntry> getShareEntries() {
		return this.shareEntries;
	}

	public void setShareEntries(List<ShareEntry> shareEntries) {
		this.shareEntries = shareEntries;
	}

	/**
	 * @return true if this category or any of its subcategories has
	 *         shareEntries, false otherwise.
	 */
	public boolean isPopulated() {
		if ((shareEntries != null && !shareEntries.isEmpty())) {
			return true;
		}
		List<TemplateCategory> subcats = getSubcategories();
		if (subcats != null) {
			for (TemplateCategory subcat : subcats) {
				if (subcat.isPopulated()) {
					return true;
				}
			}
		}
		return false;
	}

	public int getNumInstances() {
		int numInstances = 0;

		List<TemplateElementMapping> myDefs = getTemplateElementDefs();
		for (TemplateElementMapping ted : myDefs) {
			Collection<ElementDataObject> instances = ted.getInstances();
			if (instances != null) {
				numInstances += instances.size();
			}
		}
		List<TemplateCategory> mySubcats = getSubcategories();
		for (TemplateCategory templateCategory : mySubcats) {
			numInstances += templateCategory.getNumInstances();
		}

		return numInstances;
	}

    public Integer getId() {
        return this.id;
    }

	public void setId(Integer identifier) {
		this.id = identifier;
	}

	public String getTitle() {
		return this.title;
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

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setSubcategories(List<TemplateCategory> subcategories) {
		this.subcategories = subcategories;
	}
}
