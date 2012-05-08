/* $Name:  $ */
/* $Id: PermissionsManagerImpl.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.portfolio.model.ElementDataObject;
import org.portfolio.model.EntryKey;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("session")
@Component
public class PermissionsManagerImpl implements PermissionsManager {

    private Set<EntryKey> allowed = new HashSet<EntryKey>();
    
    public void grantPermission(EntryKey entryKey) {
        allowed.add(entryKey);
    }

    public boolean hasPermission(EntryKey entryKey) {
        return allowed.contains(entryKey);
    }

    public void grantPermissions(List<? extends ElementDataObject> elements) {
        for (ElementDataObject elementDataObject : elements) {
            allowed.add(elementDataObject.getEntryKey());
        }
    }
}
