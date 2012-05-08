/* $Name:  $ */
/* $Id: AttachmentHome.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.util.List;

import org.portfolio.model.EntryKey;

public interface AttachmentHome {

    void insert(EntryKey entry, EntryKey attachment);
    
    void remove(EntryKey entry, EntryKey attachment);
    
    void removeAll(EntryKey entry);
    
    void removeAllByAttachment(EntryKey attachmentEntryKeyId);

    List<EntryKey> findByEntry(EntryKey entry);
    
    boolean exists(EntryKey entry, EntryKey attachment);

    List<EntryKey> findByEntryAndType(EntryKey entryKey, String elementDefId);

}
