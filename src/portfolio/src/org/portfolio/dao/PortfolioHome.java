/* $Name:  $ */
/* $Id: PortfolioHome.java,v 1.10 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;
import org.portfolio.model.BarePortfolio;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioHistory;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.Viewer.ViewType;

/**
 * @author Matt Sheehan
 * 
 */
public interface PortfolioHome {

    void insert(Portfolio data);

    void update(Portfolio data);

    void delete(String shareId);
    
    boolean isPublic(String portfolioId);

    Portfolio findByShareId(String shareId);
    
    void newPortfolioHistoryEntry(PortfolioHistory history);
    
    List<PortfolioHistory> findPortfolioHistoryByShareId(String shareId);

    List<Portfolio> findPublicByTag(String tag);

    List<Portfolio> findPublicByNameOrDesc(String query);
    
    List<Portfolio> findAllPublic();
    
    List<Portfolio> findByOwnerAndTemplateId(String personId, String templateId);
    
    List<Portfolio> findByOwner(Person person);
    
    List<Portfolio> findByOwnerAndTemplateId(String personId, String templateId, String dateFrom, String dateTo);
    
    int findCountByTemplateId(String templateId);
    
    List<Portfolio> findByViewer(String personId);

    List<Portfolio> findByViewerAndViewType(String personId, ViewType viewType);

    List<Portfolio> findPublicByOwner(String personId);

    List<Portfolio> findXMostRecentSharesCreatedByUser(String userId, int x);

    List<Portfolio> findByOwnerAndAssessmentModelId(String personId, Integer id);
    
    List<Portfolio> findByAssessmentModelId(Integer id);
    
    List<Portfolio> findByAssessmentModelId(Integer id, String dateFrom, String dateTo);
    
    List<Portfolio> findByAssessmentModelAndTypeId(Integer assessment_model_id, PortfolioItemType portfolioItemType, String dateFrom, String dateTo);

    List<Portfolio> findBySearchCriteria(PortfolioSearchCriteria criteria);
    
    int findSizeBySearchCriteria(PortfolioSearchCriteria criteria);
    
    List<BarePortfolio> findBarePortfoliosBySearchCriteria(PortfolioSearchCriteria criteria);

    List<Portfolio> findByBarePortfolioList(List<BarePortfolio> b_p);
}
