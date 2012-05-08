/* $Name:  $ */
/* $Id: AttachmentManager.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;

public interface AttachmentManager {

    void addAttachments(EntryKey entry, EntryKey[] attachments);

    List<? extends ElementDataObject> findByEntry(EntryKey entryKey);

    List<? extends ElementDataObject> findByEntryAndType(EntryKey entryKey, String elementDefId);

    void detach(EntryKey entry, EntryKey attachment);

    /**
     * Remove all attachments from this entry.
     */
    void detachAll(EntryKey entryKey);
    
    void detachAllByAttachment(EntryKey attachmentEntryKeyId);
}
