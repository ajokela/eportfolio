/* $Name:  $ */
/* $Id: PortfolioSearchImpl.java,v 1.15 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.BarePortfolio;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("portfolioSearch")
public class PortfolioSearchImpl implements PortfolioSearch {
    
    @Autowired
    private PortfolioHome portfolioHome;
    @Autowired
    private PortfolioTagManager portfolioTagManager;
    
    @SuppressWarnings("unused")
	private LogService logService = new LogService(this.getClass());

    public Set<Portfolio> findByCriteria(PortfolioSearchCriteria criteria) {
    	
    	criteria.setReturnType(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_OBJECTS);
    	
        LinkedHashSet<Portfolio> results = new LinkedHashSet<Portfolio>(portfolioHome.findBySearchCriteria(criteria));
        portfolioTagManager.populateTags(results);
        return results;
    }

	public int findSizeByCriteria(PortfolioSearchCriteria criteria) {
		criteria.setReturnType(PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_SIZE);
		
		int size = portfolioHome.findSizeBySearchCriteria(criteria);
		
		return size;
	}
	
	public List<BarePortfolio> findAllBarePortfoliosByCriteria(PortfolioSearchCriteria criteria) {
		
		List<BarePortfolio> list = portfolioHome.findBarePortfoliosBySearchCriteria(criteria);
		
		return list;
	}

	
	public List<BarePortfolio> findBarePortfoliosByCriteria(PortfolioSearchCriteria criteria, List<BarePortfolio> barePortfolios) {
		
		List<BarePortfolio> list = new ArrayList<BarePortfolio>();
		
		if(barePortfolios == null) {
			
			list = portfolioHome.findBarePortfoliosBySearchCriteria(criteria);
		
		}
		else {
			
			list = barePortfolios;
			
		}

		if(list != null) {
			
			if(criteria.getSortBy() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_TITLE) {
				Collections.sort(list, BarePortfolio.TITLE_ORDER);
			}
			else if(criteria.getSortBy() == PortfolioSearchCriteria.PORTFOLIOSEARCHCRITERIA_ORDER_AUTHOR) {
				Collections.sort(list, BarePortfolio.AUTHOR_ORDER);
			}
			else {
				Collections.sort(list, BarePortfolio.DATE_ORDER);
				Collections.reverse(list);
			}

			list = new ArrayList<BarePortfolio>(list.subList(criteria.getStart(), criteria.getEnd()));
			
		}
		
		return list;
		
	}

	@Override
	public List<Portfolio> findByBarePortfolioList(List<BarePortfolio> b_p) {
		
		if(b_p != null) {
			
			List<Portfolio> list = portfolioHome.findByBarePortfolioList(b_p);
		
			if(list != null) {
				portfolioTagManager.populateTags(list);
				return list;
			}
		
		}
		
		return new ArrayList<Portfolio>();
	}
	
	
}
