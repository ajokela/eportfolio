/* $Name:  $ */
/* $Id: PortfolioTagManager.java,v 1.10 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioTag;

/**
 * Manager Interface for Portfolio Tags
 * @author Vijay Rajagopal
 */
public interface PortfolioTagManager {


    public boolean matchingTagExists(String personId, BigDecimal shareId, String tagText);

    public void insert(PortfolioTag tag);

    public void delete(PortfolioTag tag);
    
    /**
     * Populates the tags for the given list of shareDefintions
     */
    public void populateTags(Collection<? extends Portfolio> shares);

    public Set<String> getTagNamesForPerson(String personId);

    public Set<String> getTagNamesForPublicPortfolios();

    /**
     * Gets list of pub/private tags given list of Tags and personId
     */
    public Set<PortfolioTag> getMyTags(String personId, String[] portfolioTags);

    /**
     * Gets all the public tags on Portfolios shared to given personId
     */
    public Set<String> getSharedPortfolioTags(String personId);

    public List<PortfolioTag> getTagsForPersonPortfolio(String personId, BigDecimal shareId);

    public List<Portfolio> queryPubByPubTag(String tag);
}
