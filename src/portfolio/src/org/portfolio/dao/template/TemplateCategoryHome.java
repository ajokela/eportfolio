/* $Name:  $ */
/* $Id: TemplateCategoryHome.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.template;

import java.util.List;

import org.portfolio.model.TemplateCategory;

/**
 * @author Matt Sheehan
 *
 */
public interface TemplateCategoryHome {

	List<TemplateCategory> find(String templateId);

	List<TemplateCategory> findChildren(int parentCategoryId);

	void insert(TemplateCategory category);

	void update(TemplateCategory category);

	int findNumberOfTopLevelCategoriesByTemplateId(String templateId);

	int findNumberOfChildrenByCategoryId(Integer parentCategoryId);

	void delete(Integer identifier);

	List<TemplateCategory> findTopLevelCategories(String templateId);

	TemplateCategory findById(int categoryId);

	TemplateCategory findTopLevelCategoryByTemplateIdAndSortOrder(String templateId, int i);

	TemplateCategory findByParentIdAndSortOrder(Integer parentCategoryId, int i);

}
