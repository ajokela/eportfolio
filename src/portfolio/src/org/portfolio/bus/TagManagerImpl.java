/* $Name:  $ */
/* $Id: TagManagerImpl.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.dao.TagHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tagManager")
public class TagManagerImpl implements TagManager {

    @Autowired
    private TagHome tagHome;

    public void addTag(ElementDataObject entry, String text) {
        tagHome.insert(entry.getPersonId(), entry.getEntryId(), entry.getElementDefinition().getId(), text);
    }

    public void addTag(EntryKey entry, String text) {
        tagHome.insert(entry.getPersonId(), entry.getEntryId(), entry.getElementDefinition().getId(), text);
    }

    public void deleteTag(ElementDataObject entry, String text) {
        tagHome.delete(entry.getPersonId(), entry.getEntryId(), entry.getElementDefinition().getId(), text);
    }

    public void deleteTag(EntryKey entryKey, String tag) {
        tagHome.delete(entryKey.getPersonId(), entryKey.getEntryId(), entryKey.getElementDefinition().getId(), tag);
    }

    public void deleteTags(EntryKey entryKey) {
        tagHome.deleteAll(entryKey.getPersonId(), entryKey.getElementDefinition().getId(), entryKey.getEntryId());
    }

    public List<String> getTagList(ElementDataObject entry) {
        return tagHome.findAllByElement(entry.getPersonId(), entry.getElementDefinition().getId(), entry.getEntryId());
    }

    public List<String> getTagsByUser(String personId) {
        return tagHome.findByPersonId(personId);
    }

    public boolean matchingTagExists(ElementDataObject entry, String tagText) {
        return tagHome.exists(entry.getPersonId(), entry.getElementDefinition().getId(), entry.getEntryId(), tagText);
    }
	
	public List<String> getTagsByEntryId(BigDecimal entryId) {
		return tagHome.findAllByEntryId(entryId);
	}
}
