/* $Name:  $ */
/* $Id: CategoryHome.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.wizard;

import java.util.List;

import org.portfolio.model.wizard.Category;

/**
 * @author Matt Sheehan
 * 
 */
public interface CategoryHome {

	/**
	 * Returns a <code>List</code> of top level <code>Category</code>
	 * objects for a given <code>Wizard</code>, or <code>null</code> if the
	 * <code>Wizard</code> object was invalid.
	 */
	public List<Category> findTopLevelCategories(int wizardId);

	/**
	 * Returns a <code>List</code> of the child <code>Category</code>
	 * objects for a given <code>Category</code>, or <code>null</code> if
	 * the <code>Category</code> object was invalid.
	 */
	public List<Category> findChildrenByCategoryId(int categoryId);

	public Category findById(int categoryId);

	public int findNumberOfChildrenByCategoryId(int categoryId);

	public int findNumberOfTopLevelCategoriesByWizardId(int wizardId);

	public void insert(Category category);

	public void update(Category category);

	public void delete(int categoryId);

	public Category findByParentIdAndSortOrder(int parentCategoryId, int order);

	public Category findTopLevelCategoryByWizardIdAndSortOrder(int wizardId, int order);

}
