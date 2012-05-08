/* $Name:  $ */
/* $Id: PortfolioManager.java,v 1.23 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;
import java.util.Map;

import org.portfolio.model.EntryKey;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.Viewer;
import org.portfolio.model.Viewer.ViewType;
import org.portfolio.model.assessment.Assessment;

public interface PortfolioManager {

    List<Viewer> getViewers(Portfolio shareDefinition);

    List<Person> getViewerPersons(Portfolio portfolio);

    String getViewerRole(Portfolio portfolio, Person person);

    void setViewerList(String shareId, List<Viewer> viewerList);
    
    void savePortfolio(Portfolio portfolio);

    /**
     * Create the populated top level cats.
     */
    List<TemplateCategory> getTopLevelCategoriesByPortfolio(Portfolio share);

    /**
     * find assessments asigned to this portfolio.
     */
    List<Assessment> findItemAssessments(Portfolio share, Person currentPerson);

    /**
     * delete all portfolio elements shared through this portfolio.
     */
    void deletePortfolioElements(String shareId);
    
    void deletePortfolio(String shareId);

    void deletePortfolioElement(ShareEntry shareEntry);

    /**
     * Note: I moved this from SaveCreateShareAction.
     * 
     * @param elementMap templateElementMappingId to String array of entryIds
     */
    void addNewEntries(Portfolio share, Map<String, String[]> elementMap);

    List<ShareEntry> findShareEntriesByShareIdElementId(String shareId, String templateElementMappingId);

    List<ShareEntry> findShareEntriesByPersonIdAndAssessmentModelId(String personId, Integer id);
   
    List<Portfolio> findByPersonId(String personId);
    
    List<Portfolio> findByPersonIdAndViewType(String personId, ViewType viewType);

    Portfolio findById(String shareId);

    /**
     * Moved from ViewMan. Need to clean up.
     */
    List<TemplateCategory> getTopLevelCategoriesForCreate(Portfolio share, boolean b);
    
    boolean containsEntry(String portfolioId, EntryKey entryKey);
    
    boolean isPublic(String portfolioId);
    
    boolean isViewableBy(String portfolioId, String personId);
}
