/* $Name:  $ */
/* $Id: PortfolioTagHome.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;
import org.portfolio.model.PortfolioTag;

/**
 * Home Interface for Portfolio Tags
 * 
 * @author Vijay Rajagopal
 */
public interface PortfolioTagHome {

    public List<PortfolioTag> findPubTagsForPublicPortfolios();

    public List<PortfolioTag> findByPersonId(String personId);

    public List<PortfolioTag> findByPersonPortfolio(String personId, BigDecimal shareId);

    public List<PortfolioTag> findAllTagsForPortfolios(String[] shareIds);

    public PortfolioTag getTag(String personId, BigDecimal shareId, String tagText);

    public void deletePortfolioTag(PortfolioTag tag);

    public void insertPortfolioTag(PortfolioTag tag);

    /**
     * Queries all public tags on portfolios shared to a given person
     */
    public List<PortfolioTag> querySharedPortfolioTags(String personId);

    /**
     * Queries all the PortfolioTags given personId and list of pub/pri
     * tags(used in advanced Search)
     */
    public List<PortfolioTag> queryMyPortfolioTags(String personId, String[] portfolioTags);
}
