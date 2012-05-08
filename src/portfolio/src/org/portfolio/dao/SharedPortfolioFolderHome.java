/* $Name:  $ */
/* $Id: SharedPortfolioFolderHome.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;
import org.portfolio.model.SharedPortfolioFolder;

/**
 * Home Interface for Shared Portfolio Folder
 * @author Vijay Rajagopal
 */
public interface SharedPortfolioFolderHome {

    public List<SharedPortfolioFolder> findByPersonId(String personId);

    public SharedPortfolioFolder findById(BigDecimal bigDecimal);

    public void insert(SharedPortfolioFolder folder);
    
    public void update(BigDecimal folderId, String newFolderName);

    public void delete(BigDecimal folderId);
}
