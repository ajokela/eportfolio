/* $Name:  $ */
/* $Id: ViewerHome.java,v 1.9 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.Viewer;

/**
 * @author Matt Sheehan
 * 
 */
public interface ViewerHome {

    void remove(Viewer data);

    void update(Viewer viewer);

    void insert(Viewer viewer);

    void deleteByPersonIdShareId(String personId, String shareId);

    void deleteByShareId(String shareId);

    Viewer findInstance(String personId, String shareId);

    List<Viewer> findByPersonId(String personId);

    List<Viewer> findByShareId(String shareId);

    List<Viewer> findXMostRecentByPersonId(String userId, int num);
    
    List<Viewer> findEvaluatorsByShareId(String shareId);

    List<Viewer> findEvaluatorsByShareEntryId(int shareEntryId);

    boolean isViewableBy(String portfolioId, String personId);
}
