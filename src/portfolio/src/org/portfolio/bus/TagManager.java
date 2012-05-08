/* $Name:  $ */
/* $Id: TagManager.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;
import java.util.List;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;

/**
 * @author Matt Sheehan
 * 
 */
public interface TagManager {

    List<String> getTagList(ElementDataObject entry);

    List<String> getTagsByUser(String personId);
    
    List<String> getTagsByEntryId(BigDecimal entryId);

    void addTag(ElementDataObject entry, String text);

    void addTag(EntryKey entry, String text);

    void deleteTag(ElementDataObject entry, String text);

    void deleteTag(EntryKey entryKey, String tag);

    void deleteTags(EntryKey entryKey);

    boolean matchingTagExists(ElementDataObject entry, String tagText);
}
