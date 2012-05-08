/* $Name:  $ */
/* $Id: ElementFolderManagerImpl.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus.element;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.dao.ElementFolderEntryHome;
import org.portfolio.dao.ElementFolderHome;
import org.portfolio.model.ElementFolder;
import org.portfolio.model.EntryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ElementFolderManagerImpl implements ElementFolderManager {

    @Autowired
    private ElementFolderEntryHome elementFolderEntryHome;
    @Autowired
    private ElementFolderHome elementFolderHome;

    @Transactional
    @Override
    public void moveElements(BigDecimal folderId, List<EntryKey> entryKeys) {
        for (EntryKey entryKey : entryKeys) {
            elementFolderEntryHome.remove(entryKey);
            elementFolderEntryHome.insert(folderId, entryKey);
        }
    }

    @Override
    public ElementFolder getFolderByName(String personId, String folderName) {
        return elementFolderHome.findByName(personId, folderName);
    }

    @Override
    public void insert(ElementFolder folder) {
        elementFolderHome.insert(folder);
    }

    @Override
    public void deleteForEntry(EntryKey entryKey) {
        elementFolderEntryHome.remove(entryKey);
    }

    @Transactional
    @Override
    public void unfileElements(List<EntryKey> entryKeys) {
        for (EntryKey entryKey : entryKeys) {
            elementFolderEntryHome.remove(entryKey);
        }
    }
}
