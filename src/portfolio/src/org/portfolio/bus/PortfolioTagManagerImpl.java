/* $Name:  $ */
/* $Id: PortfolioTagManagerImpl.java,v 1.16 2011/02/24 21:49:16 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.*;

import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.PortfolioTagHome;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioTag;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("portfolioTagManager")
public class PortfolioTagManagerImpl implements PortfolioTagManager {

    @Autowired
    private PortfolioTagHome portfolioTagHome;
    @Autowired
    private PortfolioHome portfolioHome;
    protected LogService logService = new LogService(this.getClass());
    
    public void insert(PortfolioTag tag) {
        portfolioTagHome.insertPortfolioTag(tag);
    }

    public void delete(PortfolioTag tag) {
        portfolioTagHome.deletePortfolioTag(tag);
    }

    public Set<String> getTagNamesForPerson(String personId) {
        List<PortfolioTag> tags = portfolioTagHome.findByPersonId(personId);
        Set<String> set = new TreeSet<String>();
        for (PortfolioTag tag : tags) {
            set.add(tag.getTag());
        }
        return set;
    }

    public Set<String> getTagNamesForPublicPortfolios() {
        List<PortfolioTag> tags = portfolioTagHome.findPubTagsForPublicPortfolios();
        Set<String> set = new TreeSet<String>();
        for (PortfolioTag tag : tags) {
            set.add(tag.getTag());
        }
        return set;
    }

    public List<PortfolioTag> getTagsForPersonPortfolio(String personId, BigDecimal shareId) {
        return portfolioTagHome.findByPersonPortfolio(personId, shareId);
    }

    public List<Portfolio> queryPubByPubTag(String tag) {
        return portfolioHome.findPublicByTag(tag);
    }

    public boolean matchingTagExists(String personId, BigDecimal shareId, String tagText) {
        return portfolioTagHome.getTag(personId, shareId, tagText) != null;
    }

    public Set<String> getSharedPortfolioTags(String personId) {
        List<PortfolioTag> tags = portfolioTagHome.querySharedPortfolioTags(personId);
        Set<String> tagNames = new TreeSet<String>();
        for (PortfolioTag tag : tags) {
            tagNames.add(tag.getTag());
        }
        return tagNames;
    }

    public Set<PortfolioTag> getMyTags(String personId, String[] portfolioTags) {
        List<PortfolioTag> tags = portfolioTagHome.queryMyPortfolioTags(personId, portfolioTags);
        Set<PortfolioTag> tagSet = new HashSet<PortfolioTag>(tags);
        return tagSet;
    }

    public void populateTags(Collection<? extends Portfolio> viewsList) {
        // Populate Tags
        HashMap<String, Portfolio> viewShareIdMap = new HashMap<String, Portfolio>();
        for (Portfolio view : viewsList) {
            viewShareIdMap.put(view.getShareId(), view);
        }

        List<String> list = new ArrayList<String>(viewShareIdMap.keySet());

        while(!list.isEmpty()) {
        	
        	//logService.debug("==> [BEFORE] list.size() = " + list.size());
        	
        	int index = Math.min(list.size(), 100);
        	
        	List<String> sublist = list.subList(0, index);
        	
        	List<PortfolioTag> tags = portfolioTagHome.findAllTagsForPortfolios(sublist.toArray(new String[0]));
        	
            for (PortfolioTag tag : tags) {
                viewShareIdMap.get(tag.getShareId().toString()).addTag(tag.getTag());
            }
        	
            list.subList(0, index).clear();
            
            //logService.debug("==> [AFTER]  list.size() = " + list.size());
        }
        
        /*
        while (!list.isEmpty()) {
        	
            int index = Math.min(list.size(), 1000);
            List<String> sublist = list.subList(0, index);
            List<PortfolioTag> tags = portfolioTagHome.findAllTagsForPortfolios(sublist.toArray(new String[0]));
            
            for (PortfolioTag tag : tags) {
                viewShareIdMap.get(tag.getShareId().toString()).addTag(tag.getTag());
            }
            
            if (list.size() > sublist.size()) {
                list = list.subList(index, list.size());
            } else {
                list = new ArrayList<String>();
            }
            
        }
		*/

    }
}
