/* $Name:  $ */
/* $Id: PermissionsManager.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;

/**
 * This class is a session bean. Used to track permissions across requests.
 * 
 * @author Matt Sheehan
 * 
 */
public interface PermissionsManager {

    void grantPermission(EntryKey entryKey);
    
    boolean hasPermission(EntryKey entryKey);

    void grantPermissions(List<? extends ElementDataObject> personalElements);
}
