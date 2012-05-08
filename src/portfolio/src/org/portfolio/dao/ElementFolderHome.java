/* $Name:  $ */
/* $Id: ElementFolderHome.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.ElementFolder;

public interface ElementFolderHome {

    void insert(ElementFolder folder);

    void update(BigDecimal folderId, String newFolderName);

    void delete(BigDecimal folderId);

    ElementFolder findById(BigDecimal id);

    List<ElementFolder> findByPersonId(String personId);

    ElementFolder findByName(String personId, String folderName);
}
