/* $Name:  $ */
/* $Id: PortfolioSearch.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;
import java.util.Set;

import org.portfolio.model.BarePortfolio;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioSearchCriteria;

/**
 * @author Matt Sheehan
 *
 */
public interface PortfolioSearch {
	
	public static final String PORTFOLIOSEARCH_SORT_BY_TITLE = "title";
	public static final String PORTFOLIOSEARCH_SORT_BY_DATE  = "date";
	public static final String PORTFOLIOSEARCH_SORT_BY_AUTHOR= "author";

    Set<Portfolio> findByCriteria(PortfolioSearchCriteria criteria);

    int findSizeByCriteria(PortfolioSearchCriteria criteria);
    
    List<BarePortfolio> findAllBarePortfoliosByCriteria(PortfolioSearchCriteria criteria);
    
    List<BarePortfolio> findBarePortfoliosByCriteria(PortfolioSearchCriteria criteria, List<BarePortfolio> barePortfolios);
    
    List<Portfolio> findByBarePortfolioList(List<BarePortfolio> b_p);
    
}
