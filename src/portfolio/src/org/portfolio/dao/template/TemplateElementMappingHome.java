/* $Name:  $ */
/* $Id: TemplateElementMappingHome.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.template;

import java.util.List;

import org.portfolio.model.TemplateElementMapping;

/**
 * @author Matt Sheehan
 * 
 */
public interface TemplateElementMappingHome {

	void insert(TemplateElementMapping tem);
	
	void update(TemplateElementMapping tem);
	
	void delete(String id);

    TemplateElementMapping findById(String id);

	TemplateElementMapping findByCategoryIdAndSortOrder(Integer categoryId, int i);
    
    List<TemplateElementMapping> findByTemplateCategoryId(int categoryId);

    List<TemplateElementMapping> findByTemplateId(String templateId);
    
    List<TemplateElementMapping> findAssessableByTemplateId(String templateId);
    
    List<TemplateElementMapping> findByCollectionGuideElementId(int collectionGuideElementId);
}
