/* $Name:  $ */
/* $Id: TagHome.java,v 1.6 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Matt Sheehan
 * 
 */
public interface TagHome {

    List<String> findByPersonId(String personId);

    List<String> findAllByElement(String personId, String elementId, BigDecimal entryId);
    
    List<String> findAllByEntryId(BigDecimal entryId);

    void insert(String personId, BigDecimal entryId, String elementId, String text);

    void delete(String personId, BigDecimal entryId, String elementId, String text);

    void deleteAll(String personId, String elementId, BigDecimal entryId);

    boolean exists(String personId, String elementId, BigDecimal entryId, String tagText);

}
