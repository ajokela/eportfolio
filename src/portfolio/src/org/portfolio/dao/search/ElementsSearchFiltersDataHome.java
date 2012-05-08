/* $Name:  $ */
/* $Id: ElementsSearchFiltersDataHome.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao.search;

import java.util.List;
import java.util.Map;

public interface ElementsSearchFiltersDataHome {

    /**
     * {communityId: Integer, communityName: String}
     * 
     * @param personId
     * @return
     */
    List<Map<String, Object>> findCommunityDataByPersonId(String personId);
}
