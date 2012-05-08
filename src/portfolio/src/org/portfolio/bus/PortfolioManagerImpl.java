/* $Name:  $ */
/* $Id: PortfolioManagerImpl.java,v 1.65 2011/03/22 16:04:05 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.community.RoleAssignmentHome;
import org.portfolio.dao.template.TemplateCategoryHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.ElementDefinition;
import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.Viewer;
import org.portfolio.model.Viewer.ViewType;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.community.CommunityRoleType;
import org.portfolio.util.CompositeMapKey;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("portfolioManager")
public class PortfolioManagerImpl implements PortfolioManager {

    private static final LogService logService = new LogService(PortfolioManagerImpl.class);

    @Autowired
    private AssessmentModelAssignmentHome assessmentModelAssignmentHome;
    @Autowired
    private ElementDefinitionManager elementDefinitionHome;
    @Autowired
    private PortfolioHome portfolioHome;
    @Autowired
    private ShareEntryHome shareEntryHome;
    @Autowired
    private TemplateCategoryHome templateCategoryHome;
    @Autowired
    private TemplateElementMappingHome templateElementFormHome;
    @Autowired
    private ViewerHome viewerHome;
    @Autowired
    private AssessmentManager assessmentManager;
    @Autowired
    private CommentsManager commentsManager;
    @Autowired
    private AssessmentModelManager assessmentModelManager;
    @Autowired
    private TemplateHome templateHome;
    @Autowired
    private EntrySearch entrySearch;
    @Autowired
    private ElementManager elementManager;
    @Autowired
    private RoleAssignmentHome roleAssignmentHome;

    public List<TemplateCategory> getTopLevelCategoriesByPortfolio(Portfolio share) {

        if (share.getTemplateId() == null) {
        	logService.debug("share.getTemplateId() == null");
        	
            return new ArrayList<TemplateCategory>();
        }

        logService.debug(" == getTopLevelCategoriesByPortfolio() ==> share.getTemplate().getId(): " + share.getTemplate().getId());
        
        List<TemplateCategory> topLevelCats = templateCategoryHome.find(share.getTemplate().getId());

        for (TemplateCategory topLevelCat : topLevelCats) {

        	//logService.debug("\n---------------------------------------------\n ====> topLevelCat.getTitle(): " + topLevelCat.getTitle());
        	
            for (TemplateCategory subcat : topLevelCat.getSubcategories()) {

                //logService.debug(" =======> subcat.getTitle(): " + subcat.getTitle());

                List<TemplateElementMapping> templateElementDefs = new ArrayList<TemplateElementMapping>();
                List<ShareEntry> subcatShareEntries = new ArrayList<ShareEntry>();

                List<TemplateElementMapping> allTemplateElementDefs = templateElementFormHome.findByTemplateCategoryId(subcat.getId());

                for (TemplateElementMapping ted : allTemplateElementDefs) {


                    if (ted == null) {

                    } else {

                        //logService.debug("ted: " + ted);

                        if (ted.getElementDefinition() == null) {
                            logService.debug("==> ted.getElementDefinition() is null");
                        } else {

                            // logService.debug(" ==> ted.getElementTitle(): " + ted.getElementTitle());

                            ElementDefinition def = elementDefinitionHome.findByElementId(ted.getElementDefinition().getId());

                            // For public presentation, only add elements that can be
                            // displayed for public presentation
                            // Add all materials, there is no restriction on type of
                            // material that can show up in public presentation
                            if (!share.isPublicView() || def.isPubliclySharable()) {
                                templateElementDefs.add(ted);

                                List<ShareEntry> shareEntries = shareEntryHome.findByShareIdElementId(share.getShareId(), ted.getId());

                                for (ShareEntry shareEntry : shareEntries) {
                                    prepareShareEntry(share, ted, def, shareEntry);
                                    if (shareEntry.getElement() != null) {

                                        subcatShareEntries.add(shareEntry);
                                    }
                                }
                            }
                        }
                    }

                }
                Collections.sort(subcatShareEntries, ShareEntry.SORT_ORDER);
                subcat.setShareEntries(subcatShareEntries);
            }
        }

        // Remove empty categories, sub-categories and element references
        for (Iterator<TemplateCategory> iterator = topLevelCats.iterator(); iterator.hasNext();) {
            TemplateCategory cat = iterator.next();
            for (Iterator<TemplateCategory> iterator2 = cat.getSubcategories().iterator(); iterator2.hasNext();) {
                TemplateCategory subcat = iterator2.next();
                List<ShareEntry> shareEntries = subcat.getShareEntries();
                if (shareEntries == null || shareEntries.isEmpty()) {
                    iterator2.remove();
                }
            }
            if (cat.getSubcategories().isEmpty()) {
                iterator.remove();
            }
        }
        return topLevelCats;
    }

    private void prepareShareEntry(Portfolio share, TemplateElementMapping ted, ElementDefinition def, ShareEntry shareEntry) {
        shareEntry.setTemplateElementMapping(ted);
        ElementDataObject element = elementManager.findElementInstance(def.getId(), share.getPersonId(), shareEntry.getEntryIndex(), false, share.getPersonId());

        if (element != null) {

            shareEntry.setElement(element);

            AssessmentModelAssignment assessmentModelAssignment = assessmentModelAssignmentHome.findByAssessedItemIdAndPortfolioItemType(
                    shareEntry.getId(),
                    PortfolioItemType.PORTFOLIO_ELEMENT);
            shareEntry.setAssessmentModelAssignment(assessmentModelAssignment);
        }
    }

    public List<Viewer> getViewers(Portfolio portfolio) {
        return viewerHome.findByShareId(portfolio.getShareId());
    }

    public AssessmentModelAssignment getAssessmentModelAssignment(Portfolio portfolio) {
        return assessmentModelAssignmentHome.findByAssessedItemIdAndPortfolioItemType(
                Integer.parseInt(portfolio.getShareId()),
                PortfolioItemType.PORTFOLIO);
    }

    public List<Assessment> findItemAssessments(Portfolio portfolio, Person currentPerson) {
        boolean isOwner = portfolio.getPersonId().equals(currentPerson.getPersonId());
        List<Assessment> itemAssessments;
        if (isOwner) {
            itemAssessments = assessmentManager.findAssessmentsByItemIdAndType(
                    Integer.parseInt(portfolio.getShareId()),
                    PortfolioItemType.PORTFOLIO);
        } else {
            itemAssessments = assessmentManager.findEvaluatorAssessments(currentPerson.getPersonId(), Integer.parseInt(portfolio
                    .getShareId()), PortfolioItemType.PORTFOLIO);
        }
        for (Assessment assessment : itemAssessments) {
            assessment.setCommentList(commentsManager.getCommentList(assessment, currentPerson.getPersonId()));
        }
        return itemAssessments;
    }

    @Transactional
    public void deletePortfolio(String shareId) {
        viewerHome.deleteByShareId(shareId);
        assessmentModelAssignmentHome.deleteByItemIdAndItemType(shareId, PortfolioItemType.PORTFOLIO);
        shareEntryHome.deleteShareEntries(shareId);
        portfolioHome.delete(shareId);
    }

    @Transactional
    public void deletePortfolioElements(String shareId) {
        List<ShareEntry> shareEntries = shareEntryHome.findByShareId(shareId);
        shareEntryHome.deleteShareEntries(shareId);
        for (ShareEntry shareEntry : shareEntries) {
            if (shareEntry.getAssessmentModelAssignment() != null) {
                assessmentModelAssignmentHome.delete(shareEntry.getAssessmentModelAssignment().getIdentifier());
            }
        }
    }

    @Transactional
    public void deletePortfolioElement(ShareEntry shareEntry) {
        shareEntryHome.remove(shareEntry);
        if (shareEntry.getAssessmentModelAssignment() != null) {
            assessmentModelAssignmentHome.delete(shareEntry.getAssessmentModelAssignment().getIdentifier());
        }
    }

    public List<Person> getViewerPersons(Portfolio portfolio) {
        List<Viewer> viewers = viewerHome.findByShareId(portfolio.getShareId());
        List<Person> persons = new ArrayList<Person>();
        for (Viewer viewer : viewers) {
            Person person = viewer.getPerson();
            if (person != null) {
                persons.add(person);
            } else {
                logService.error("viewer person not found: " + viewer.getPersonId());
            }
        }
        return persons;
    }

    /**
     * @param elementMap templateElementMappingId to String array of entryIds
     */
    @Transactional
    public void addNewEntries(Portfolio share, Map<String, String[]> elementMap) {
        // before clearing the old entries, save a copy in memory
        // so the old sort order is not lost
        // CompositeMapKey{entryId, templateElementMappingId}
        Map<CompositeMapKey, ShareEntry> shareEntryMap = new HashMap<CompositeMapKey, ShareEntry>();

        List<ShareEntry> shareEntries = shareEntryHome.findByShareId(share.getShareId());
        for (ShareEntry shareEntry : shareEntries) {
            shareEntryMap.put(new CompositeMapKey(shareEntry.getEntryIndex(), shareEntry.getElementId()), shareEntry);
        }

        Template template = templateHome.findTemplateById(share.getTemplateId());

        if (share.getAssessmentModelAssignment() != null) {
            assessmentModelManager.deleteAssessmentModelAssignment(share.getAssessmentModelAssignment().getIdentifier());
        }

        AssessmentModelAssignment templateAssignment = template.getAssessmentModelAssignment();
        if (templateAssignment != null) {
            AssessmentModelAssignment portfolioAssignment = new AssessmentModelAssignment();
            portfolioAssignment.setAnonymous(templateAssignment.isAnonymous());
            portfolioAssignment.setAssessedItemId(Integer.parseInt(share.getShareId()));
            portfolioAssignment.setAssessmentModel(templateAssignment.getAssessmentModel());
            portfolioAssignment.setCommitteeBased(templateAssignment.isCommitteeBased());
            portfolioAssignment.setPortfolioItemType(PortfolioItemType.PORTFOLIO);
            assessmentModelManager.saveAssessmentModelAssignment(portfolioAssignment);
        }

        // remove any old entries for this share
        // portfolioManager.deletePortfolioElements(share.getShareId());

        List<TemplateCategory> topLevelCats = templateCategoryHome.find(template.getId());
        for (TemplateCategory topLevelCat : topLevelCats) {
            List<TemplateCategory> subcats = topLevelCat.getSubcategories();
            for (TemplateCategory subcat : subcats) {
                int subCatCount = 0;
                List<TemplateElementMapping> subCatElements = templateElementFormHome.findByTemplateCategoryId(subcat.getId());
                // make sure the order of elements in sub category remains
                // same in step 2 and 2a
                subcat.setTemplateElementDefs(subCatElements);

                for (TemplateElementMapping element : subcat.getTemplateElementDefs()) {
                    String[] mappings = elementMap.get(element.getId());
                    if (mappings != null) {
                        for (String mapping : mappings) {
                            subCatCount++;
                            BigDecimal entryId = new BigDecimal(mapping);
                            ShareEntry shareEntry = shareEntryMap.remove(new CompositeMapKey(entryId, element.getId()));
                            if (shareEntry == null) {
                                // new
                                int sortOrder = subCatCount;
                                shareEntry = new ShareEntry();
                                shareEntry.setShareId(share.getShareId());
                                shareEntry.setEntryIndex(entryId);
                                shareEntry.setSortOrder(sortOrder);
                                shareEntry.setTemplateElementMapping(element);
                                shareEntryHome.store(shareEntry);
                            }

                            if (shareEntry.getAssessmentModelAssignment() != null) {
                                assessmentModelManager.deleteAssessmentModelAssignment(shareEntry
                                        .getAssessmentModelAssignment()
                                        .getIdentifier());
                            }

                            AssessmentModelAssignment templateElementAssignment = element.getAssessmentModelAssignment();
                            if (templateElementAssignment != null) {
                                AssessmentModelAssignment portfolioElementAssignment = new AssessmentModelAssignment();
                                portfolioElementAssignment.setAnonymous(templateElementAssignment.isAnonymous());
                                portfolioElementAssignment.setAssessedItemId(shareEntry.getId());
                                portfolioElementAssignment.setAssessmentModel(templateElementAssignment.getAssessmentModel());
                                portfolioElementAssignment.setCommitteeBased(templateElementAssignment.isCommitteeBased());
                                portfolioElementAssignment.setPortfolioItemType(PortfolioItemType.PORTFOLIO_ELEMENT);
                                assessmentModelManager.saveAssessmentModelAssignment(portfolioElementAssignment);
                                shareEntry.setAssessmentModelAssignment(portfolioElementAssignment);
                            }
                        }
                    }
                }
            }
        }

        for (ShareEntry shareEntry : shareEntryMap.values()) {
            deletePortfolioElement(shareEntry);
        }
    }

    public List<ShareEntry> findShareEntriesByShareIdElementId(String shareId, String templateElementMappingId) {
        
    	Portfolio share = portfolioHome.findByShareId(shareId);
        
    	if(share != null) {
	    	
	        List<ShareEntry> shareEntries = shareEntryHome.findByShareIdElementId(shareId, templateElementMappingId);
	        TemplateElementMapping templateElementMapping = templateElementFormHome.findById(templateElementMappingId);
	        for (ShareEntry shareEntry : shareEntries) {
	            try {
	                ElementDataObject entry = elementManager.findElementInstance(templateElementMapping.getElementDefinition().getId(), share.getPersonId(), shareEntry.getEntryIndex(), false, share.getPersonId());
	                shareEntry.setElement(entry);
	            } catch (Exception e) {
	                
	                logService.debug(e);
	            }
	        }
	        return shareEntries;
    	}
    	
    	return new ArrayList<ShareEntry>();
    }

    public List<ShareEntry> findShareEntriesByPersonIdAndAssessmentModelId(String personId, Integer assessmentModelId) {
        List<ShareEntry> shareEntries = shareEntryHome.findByPersonIdAndAssessmentModelId(personId, assessmentModelId);
        for (ShareEntry shareEntry : shareEntries) {
            TemplateElementMapping templateElementMapping = templateElementFormHome.findById(shareEntry.getElementId());
            ElementDataObject entry = elementManager.findElementInstance(
                    templateElementMapping.getElementDefinition().getId(),
                    personId,
                    shareEntry.getEntryIndex(), false, personId);
            shareEntry.setElement(entry);
        }
        return shareEntries;
    }
    

    public void setViewerList(String shareId, List<Viewer> viewerList) {
        // Compare the viewer definitions list against the original viewer list
        // adding any new
        // ones to the database. Then delete any left in the original list that
        // do not appear in
        // the definitions list.

        // Get the original viewer list from database.
        List<Viewer> orginalList = viewerHome.findByShareId(shareId);

        // Add new viewers to the database.
        for (Viewer viewer : viewerList) {
            if (orginalList.contains(viewer)) {
                orginalList.remove(viewer);
            } else {
                viewer.setShareId(shareId);
                viewerHome.insert(viewer);
            }
        }

        // Delete removed viewers from the database.
        for (Viewer viewer : orginalList) {
            if (viewer.getViewType() != ViewType.OWNER) {
                viewerHome.remove(viewer);
            }
        }
    }

    /**
     * @throws SecurityException
     */
    public String getViewerRole(Portfolio portfolio, Person person) {
    	
        List<Person> viewerList = getViewerPersons(portfolio);
        if (person.getPersonId().equals(portfolio.getPersonId())) {
            return "owner";
        }

        if (viewerList.contains(person)) {
            
        	List<String> roles = roleAssignmentHome.findRolesByCommunityIdAndPersonId(portfolio.getTemplate().getCommunityId().toString(), person.getPersonId());
            
            if (roles.contains(CommunityRoleType.EVALUATOR.getCode())) {
                return "evaluator";
            }
            else if (roles.contains(CommunityRoleType.COMMUNITY_COORDINATOR.getCode())) {
            	return "coordinator";
            }
            
            return person.isGuest() ? "guest" : "reviewer";
        }
        throw new SecurityException("user doesnt have access");
    }

    public List<Portfolio> findByPersonId(String personId) {
        return portfolioHome.findByViewer(personId);
    }

    public List<Portfolio> findByPersonIdAndViewType(String personId, ViewType viewType) {
        return portfolioHome.findByViewerAndViewType(personId, viewType);
    }

    @Transactional
    public void savePortfolio(Portfolio portfolio) {
        if (portfolio.isNew()) {
            portfolioHome.insert(portfolio);
            Viewer viewer = new Viewer(portfolioHome);
            viewer.setViewType(ViewType.OWNER);
            viewer.setPerson(portfolio.getPerson());
            viewer.setShareId(portfolio.getShareId());
            viewer.setRead(false);
            viewer.setFlagged(false);
            viewerHome.insert(viewer);
        } else {
            portfolioHome.update(portfolio);
        }
    }

    public Portfolio findById(String portfolioId) {
        return portfolioHome.findByShareId(portfolioId);
    }

    public List<TemplateCategory> getTopLevelCategoriesForCreate(Portfolio share, boolean loadInstances) {
        String personId = share.getPersonId();
        List<TemplateCategory> topLevelCats = templateCategoryHome.find(share.getTemplateId());

        logService.debug("getTopLevelCategoriesForCreate()");

        if (topLevelCats != null) {

        	logService.debug("==> topLevelCats != null");
        	
            for (TemplateCategory topLevelCat : topLevelCats) {
                List<TemplateCategory> subcats = topLevelCat.getSubcategories();

                logService.debug("===> topLevelCat.getTitle(): " + topLevelCat.getTitle());

                for (TemplateCategory subcat : subcats) {

                    logService.debug("===> subcat.getTitle(): " + subcat.getTitle());

                    List<TemplateElementMapping> templateElementDefs = new ArrayList<TemplateElementMapping>();
                    List<TemplateElementMapping> allTemplateElementDefs = templateElementFormHome.findByTemplateCategoryId(subcat.getId());
                    for (TemplateElementMapping allTemplateElementDef : allTemplateElementDefs) {

                        if (allTemplateElementDef != null) {
                            logService.debug("======>  allTemplateElementDef.getElementTitle(): " + allTemplateElementDef.getElementTitle());

                        	if (allTemplateElementDef.getElementDefinition() != null) {
                                ElementDefinition def = elementDefinitionHome.findByElementId(allTemplateElementDef.getElementDefinition().getId());
                                // for public presentation only add elements which could
                                // be displayed for
                                // public presentation. Add all materials, there is no
                                // restriction on type of
                                // material that can show up in public presentation

                                if (!share.isPublicView() || def.isPubliclySharable()) {
                                    templateElementDefs.add(allTemplateElementDef);
                                }
                            }
                        }
                    }
                    subcat.setTemplateElementDefs(templateElementDefs);
                }
            }
            
            if (loadInstances) {
                for (TemplateCategory topLevelCat : topLevelCats) {
                    for (TemplateCategory subcat : topLevelCat.getSubcategories()) {
                        for (TemplateElementMapping templateElementDef : subcat.getTemplateElementDefs()) {
                            EntrySearchCriteria criteria = new EntrySearchCriteria(personId);
                            criteria.setWizardElementId(templateElementDef.getWizardElementDefinition().getId());
                            templateElementDef.setInstances(entrySearch.findByCriteria(criteria));
                        }
                    }
                }
            }
        }
        return topLevelCats;
    }

    public boolean containsEntry(String portfolioId, EntryKey entryKey) {
        return shareEntryHome.entryExistsInPortfolio(entryKey, portfolioId);
    }

    public boolean isViewableBy(String portfolioId, String personId) {
        return viewerHome.isViewableBy(portfolioId, personId);
    }

    public boolean isPublic(String portfolioId) {
        return portfolioHome.isPublic(portfolioId);
    }
}
