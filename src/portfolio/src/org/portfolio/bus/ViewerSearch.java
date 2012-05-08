/* $Name:  $ */
/* $Id: ViewerSearch.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import org.portfolio.model.Person;

/**
 * Inteface defining API on how to search for a potential shared folder
 * recipient (viewer). Implementing classes need to be able to differentiate
 * between MEMBER and GUEST viewers, and if needed, create a user (especially
 * for GUEST users).
 * 
 * 
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.4 $ $Date: 2010/10/27 19:24:56 $
 * @since 1.0.0 Beta 7
 */
public interface ViewerSearch {

    /**
     * Perform a lookup to find a MEMBER user. Will create a MEMBER user,
     * returning that user, if <code>share.viewerSearch.createMemberUser</code>
     * is true. (default is false)
     * 
     * @param username the username of the MEMBER viewer to share to.
     * @return the Person object defining the user.
     */
    public Person findMemberViewer(String username);

    /**
     * Perform a lookup to find a GUEST user. Will create a GUEST user,
     * returning that user, if <code>share.viewerSearch.createGuestUser</code>
     * is true. (default is true)
     * 
     * @param email the username of the GUEST viewer to share to.
     * @return the Person object defining the user.
     */
    public Person findGuestViewer(String email);

    /**
     * Used to display viewers (that already exist in the veiwer table).
     * Implementations of this <b>should</b> call both findMemberUser() and
     * findGuestUser(), returning a single Person object for the person.
     * 
     * @param personId either a username or an email, for a user.
     * @return a Person object encapsulating the user's data.
     */
    public Person findViewerByPersonId(String personId);

}
