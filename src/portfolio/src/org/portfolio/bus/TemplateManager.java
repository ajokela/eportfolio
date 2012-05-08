/* $Name:  $ */
/* $Id: TemplateManager.java,v 1.10 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.wizard.CollectionGuide;

/**
 * @author Matt Sheehan
 * 
 */
public interface TemplateManager {

	Template getTemplateById(String templateId);

	void saveTemplate(Template template);

	List<TemplateCategory> getCategoriesByTemplateId(String templateId);

	void saveCategory(TemplateCategory category);

	TemplateCategory getCategoryById(int parseInt);

	void deleteCategory(TemplateCategory category);

	void moveCategoryUp(TemplateCategory category);

	void moveCategoryDown(TemplateCategory category);

	void saveTemplateElementMapping(TemplateElementMapping templateElementMapping);

	void deleteTemplateElementMapping(TemplateElementMapping templateElementMapping);

	void moveTemplateElementMappingUp(TemplateElementMapping templateElementMapping);

	void moveTemplateElementMappingDown(TemplateElementMapping templateElementMapping);

	TemplateElementMapping getTemplateElementMappingById(String templateElementIdParam);

    List<TemplateElementMapping> findByCollectionGuideElementId(int collectionGuideElementId);

	/**
	 * Copies and saves the structure of the guide to the template. Assumes the
	 * template is empty.
	 */
	void copyCollectionGuide(Template template, CollectionGuide guide);

	Template copyTemplate(Template template);

    void deleteTemplate(Template template);

}
