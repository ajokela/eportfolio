/* $Name:  $ */
/* $Id: ElementFolderManager.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.element;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.ElementFolder;
import org.portfolio.model.EntryKey;

public interface ElementFolderManager {

    void moveElements(BigDecimal folderId, List<EntryKey> entryKeys);
    
    void unfileElements(List<EntryKey> entryKeys);

    void insert(ElementFolder folder);

    ElementFolder getFolderByName(String personId, String folderName);

    void deleteForEntry(EntryKey entryKey);

}
