/* $Name:  $ */
/* $Id: ElementFolderEntryHome.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.EntryKey;

public interface ElementFolderEntryHome {

    List<EntryKey> findByFolderId(BigDecimal folderId);
    
    void insert(BigDecimal folderId, EntryKey entryKey);

    void remove(EntryKey entryKey);
}
