/* $Name:  $ */
/* $Id: TemplateManagerImpl.java,v 1.18 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.template.TemplateCategoryHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.wizard.Category;
import org.portfolio.model.wizard.CollectionGuide;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Matt Sheehan
 * 
 */
@Service("templateManager")
public class TemplateManagerImpl implements TemplateManager {

    @Autowired
    private TemplateHome templateHome;
    @Autowired
    private TemplateCategoryHome templateCategoryHome;
    @Autowired
    private TemplateElementMappingHome templateElementFormHome;
    @Autowired
    private AssessmentModelAssignmentHome assessmentModelAssignmentHome;

    public Template getTemplateById(String templateId) {
        return templateHome.findTemplateById(templateId);
    }

    public void saveTemplate(Template template) {
        if (template.getId() == null || template.getId().trim().length() == 0) {
            templateHome.insert(template);
        } else {
            templateHome.update(template);
        }
    }

    public List<TemplateCategory> getCategoriesByTemplateId(String templateId) {
        List<TemplateCategory> cats = templateCategoryHome.find(templateId);
        for (TemplateCategory cat : cats) {
            List<TemplateCategory> subcats = templateCategoryHome.findChildren(cat.getId());
            for (TemplateCategory subcat : subcats) {
                subcat.setTemplateElementDefs(templateElementFormHome.findByTemplateCategoryId(subcat.getId()));
            }
            cat.setSubcategories(subcats);
        }
        return cats;
    }

    @Transactional
    public void saveCategory(TemplateCategory category) {
        if (category.getId() == null) {
            int numSiblings;
            if (category.getParentCategoryId() == null) {
                numSiblings = templateCategoryHome.findNumberOfTopLevelCategoriesByTemplateId(category.getTemplateId());
            } else {
                numSiblings = templateCategoryHome.findNumberOfChildrenByCategoryId(category.getParentCategoryId());
            }
            category.setSortOrder(numSiblings + 1);
            templateCategoryHome.insert(category);
        } else {
            templateCategoryHome.update(category);
        }
    }

    @Transactional
    public void deleteCategory(TemplateCategory category) {
        Integer parentCategoryId = category.getParentCategoryId();
        String templateId = category.getTemplateId();

        // TODO do this in database?
        for (TemplateCategory subcat : category.getSubcategories()) {
            deleteCategory(subcat);
        }
        for (TemplateElementMapping def : category.getTemplateElementDefs()) {
            deleteTemplateElementMapping(def);
        }

        templateCategoryHome.delete(category.getId());

        List<TemplateCategory> siblings;
        if (parentCategoryId == null) {
            siblings = templateCategoryHome.findTopLevelCategories(templateId);
        } else {
            siblings = templateCategoryHome.findChildren(parentCategoryId);
        }
        int order = 1;
        for (TemplateCategory sibling : siblings) {
            sibling.setSortOrder(order);
            templateCategoryHome.update(sibling);
            order++;
        }
    }

    public TemplateCategory getCategoryById(int categoryId) {
        TemplateCategory category = templateCategoryHome.findById(categoryId);
        category.setTemplateElementDefs(templateElementFormHome.findByTemplateCategoryId(category.getId()));
        return category;
    }

    @Transactional
    public void moveCategoryDown(TemplateCategory category) {
        int order = category.getSortOrder();
        Integer parentCategoryId = category.getParentCategoryId();
        TemplateCategory otherCategory;
        if (parentCategoryId == null) {
            otherCategory = templateCategoryHome.findTopLevelCategoryByTemplateIdAndSortOrder(category.getTemplateId(), order + 1);
        } else {
            otherCategory = templateCategoryHome.findByParentIdAndSortOrder(parentCategoryId, order + 1);
        }
        if (otherCategory != null) {
            switchOrder(category, otherCategory);
        }
    }

    @Transactional
    public void moveCategoryUp(TemplateCategory category) {
        int order = category.getSortOrder();
        if (order > 1) {
            Integer parentCategoryId = category.getParentCategoryId();
            TemplateCategory otherCategory;
            if (parentCategoryId == null) {
                otherCategory = templateCategoryHome.findTopLevelCategoryByTemplateIdAndSortOrder(category.getTemplateId(), order - 1);
            } else {
                otherCategory = templateCategoryHome.findByParentIdAndSortOrder(parentCategoryId, order - 1);
            }
            switchOrder(category, otherCategory);
        }
    }

    private void switchOrder(TemplateCategory o1, TemplateCategory o2) {
        int o1Order = o1.getSortOrder();
        int o2Order = o2.getSortOrder();
        o1.setSortOrder(o2Order);
        o2.setSortOrder(o1Order);
        templateCategoryHome.update(o1);
        templateCategoryHome.update(o2);
    }

    @Transactional
    public void moveTemplateElementMappingUp(TemplateElementMapping templateElementMapping) {
        int order = templateElementMapping.getSortOrder();
        if (order > 1) {
            Integer categoryId = templateElementMapping.getCategoryId();
            TemplateElementMapping otherMapping = templateElementFormHome.findByCategoryIdAndSortOrder(categoryId, order - 1);
            switchOrder(templateElementMapping, otherMapping);
        }
    }

    @Transactional
    public void moveTemplateElementMappingDown(TemplateElementMapping templateElementMapping) {
        int order = templateElementMapping.getSortOrder();
        Integer categoryId = templateElementMapping.getCategoryId();
        TemplateElementMapping otherMapping = templateElementFormHome.findByCategoryIdAndSortOrder(categoryId, order + 1);
        if (otherMapping != null) {
            switchOrder(templateElementMapping, otherMapping);
        }
    }

    @Transactional
    public void deleteTemplateElementMapping(TemplateElementMapping templateElementMapping) {
        Integer categoryId = templateElementMapping.getCategoryId();

        templateElementFormHome.delete(templateElementMapping.getId());

        List<TemplateElementMapping> siblings = templateElementFormHome.findByTemplateCategoryId(categoryId);
        int order = 1;
        for (TemplateElementMapping sibling : siblings) {
            sibling.setSortOrder(order);
            templateElementFormHome.update(sibling);
            order++;
        }
    }

    @Transactional
    public void saveTemplateElementMapping(TemplateElementMapping templateElementMapping) {
        if (templateElementMapping.getId() == null) {
            List<TemplateElementMapping> siblings = templateElementFormHome
                    .findByTemplateCategoryId(templateElementMapping.getCategoryId());
            templateElementMapping.setSortOrder(siblings.size() + 1);
            templateElementFormHome.insert(templateElementMapping);
        } else {
            templateElementFormHome.update(templateElementMapping);
        }
    }

    private void switchOrder(TemplateElementMapping o1, TemplateElementMapping o2) {
        int o1Order = o1.getSortOrder();
        int o2Order = o2.getSortOrder();
        o1.setSortOrder(o2Order);
        o2.setSortOrder(o1Order);
        templateElementFormHome.update(o1);
        templateElementFormHome.update(o2);
    }

    public TemplateElementMapping getTemplateElementMappingById(String templateElementIdParam) {
        return templateElementFormHome.findById(templateElementIdParam);
    }

    @Transactional
    public void copyCollectionGuide(Template template, CollectionGuide guide) {
        for (Category category : guide.getCategories()) {
            TemplateCategory templateCategory = new TemplateCategory();
            templateCategory.setSortOrder(category.getSortOrder());
            templateCategory.setTemplateId(template.getId());
            templateCategory.setTitle(category.getTitle());
            saveCategory(templateCategory);
            for (Category subcategory : category.getCategories()) {
                TemplateCategory templateSubcategory = new TemplateCategory();
                templateSubcategory.setSortOrder(subcategory.getSortOrder());
                templateSubcategory.setTemplateId(template.getId());
                templateSubcategory.setTitle(subcategory.getTitle());
                templateSubcategory.setParentCategoryId(templateCategory.getId());
                saveCategory(templateSubcategory);
                List<WizardElementDefinition> wizardElementDefinitions = subcategory.getWizardElementDefinitions();
                for (WizardElementDefinition wed : wizardElementDefinitions) {
                    TemplateElementMapping tem = new TemplateElementMapping();
                    tem.setCategoryId(templateSubcategory.getId());
                    tem.setSortOrder(wed.getSortOrder());
                    tem.setTemplate_id(template.getId());
                    tem.setWizardElementDefinition(wed);
                    saveTemplateElementMapping(tem);
                }
            }
        }
    }

    @Transactional
    public Template copyTemplate(Template template) {
        Template newTemplate = new Template();
        newTemplate.setAssessable(template.isAssessable());
        newTemplate.setCommunityId(template.getCommunityId());
        newTemplate.setDescription(template.getDescription());
        newTemplate.setName(template.getName() + " (copy)");
        newTemplate.setPublished(template.isPublished());

        templateHome.insert(newTemplate);

        AssessmentModelAssignment ama = template.getAssessmentModelAssignment();
        if (ama != null) {
            AssessmentModelAssignment newAma = new AssessmentModelAssignment();
            newAma.setAnonymous(ama.isAnonymous());
            newAma.setAssessedItemId(Integer.parseInt(template.getId()));
            newAma.setAssessmentModel(ama.getAssessmentModel());
            newAma.setCommitteeBased(ama.isCommitteeBased());
            newAma.setPortfolioItemType(ama.getPortfolioItemType());
            
            assessmentModelAssignmentHome.insert(newAma);
        }

        List<TemplateCategory> cats = getCategoriesByTemplateId(template.getId());
        for (TemplateCategory cat : cats) {
            TemplateCategory newCat = new TemplateCategory();
            newCat.setSortOrder(cat.getSortOrder());
            newCat.setTemplateId(newTemplate.getId());
            newCat.setTitle(cat.getTitle());

            templateCategoryHome.insert(newCat);

            List<TemplateCategory> subcats = cat.getSubcategories();
            for (TemplateCategory subcat : subcats) {
                TemplateCategory newSubcat = new TemplateCategory();
                newSubcat.setParentCategoryId(newCat.getId());
                newSubcat.setSortOrder(subcat.getSortOrder());
                newSubcat.setTemplateId(newTemplate.getId());
                newSubcat.setTitle(subcat.getTitle());

                templateCategoryHome.insert(newSubcat);

                List<TemplateElementMapping> tems = subcat.getTemplateElementDefs();
                for (TemplateElementMapping tem : tems) {
                    TemplateElementMapping newTem = new TemplateElementMapping();
                    newTem.setCategoryId(newSubcat.getId());
                    newTem.setSortOrder(tem.getSortOrder());
                    newTem.setTemplate_id(newTemplate.getId());
                    newTem.setWizardElementDefinition(tem.getWizardElementDefinition());

                    templateElementFormHome.insert(newTem);

                    AssessmentModelAssignment temAma = tem.getAssessmentModelAssignment();
                    if (temAma != null) {
                        AssessmentModelAssignment newTemAma = new AssessmentModelAssignment();
                        newTemAma.setAnonymous(temAma.isAnonymous());
                        newTemAma.setAssessedItemId(Integer.parseInt(newTem.getId()));
                        newTemAma.setAssessmentModel(temAma.getAssessmentModel());
                        newTemAma.setCommitteeBased(temAma.isCommitteeBased());
                        newTemAma.setPortfolioItemType(temAma.getPortfolioItemType());
                        
                        assessmentModelAssignmentHome.insert(newTemAma);
                    }
                }

            }
        }
        // TODO Auto-generated method stub
        return null;
    }

    public List<TemplateElementMapping> findByCollectionGuideElementId(int collectionGuideElementId) {
        return templateElementFormHome.findByCollectionGuideElementId(collectionGuideElementId);
    }

    public void deleteTemplate(Template template) {
        template.setDeleted(true);
        templateHome.update(template);
    }
}
