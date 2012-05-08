/* $Name:  $ */
/* $Id: AttachmentManagerImpl.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.dao.AttachmentHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AttachmentManagerImpl implements AttachmentManager {

    @Autowired
    private AttachmentHome attachmentHome;
    @Autowired
    private ElementManager elementManager;

    @Transactional
    public void addAttachments(EntryKey entry, EntryKey[] attachments) {
        for (EntryKey attachment : attachments) {
            if (!attachmentHome.exists(entry, attachment)) {
                attachmentHome.insert(entry, attachment);
            }
        }
    }

    public List<? extends ElementDataObject> findByEntry(EntryKey entryKey) {
        List<EntryKey> keys = attachmentHome.findByEntry(entryKey);
        return elementManager.loadAll(keys);
    }

    public List<? extends ElementDataObject> findByEntryAndType(EntryKey entryKey, String elementDefId) {
        List<EntryKey> keys = attachmentHome.findByEntryAndType(entryKey, elementDefId);
        return elementManager.loadAll(keys);
    }

    public void detach(EntryKey entry, EntryKey attachment) {
        attachmentHome.remove(entry, attachment);
    }

    public void detachAll(EntryKey entryKey) {
        attachmentHome.removeAll(entryKey);
    }

    public void detachAllByAttachment(EntryKey attachmentEntryKeyId) {
        attachmentHome.removeAllByAttachment(attachmentEntryKeyId);
    }

}
