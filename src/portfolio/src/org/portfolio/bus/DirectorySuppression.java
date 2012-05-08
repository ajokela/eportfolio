/* $Name:  $ */
/* $Id: DirectorySuppression.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;


/**
 * This interface defines the API for checking for directory suppression
 * for a given user (such as FERPA, etc). Implementing classes are responsible
 * for the actual business logic for an institution.
 *
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.4 $ $Date: 2010/10/27 19:24:56 $
 * @since 1.0.0 Beta 4
 */
public interface DirectorySuppression {

    /**
     * <p>This method determines whether a user has directory suppression enabled.
     * Implementation of this method will be specific to the institution's
     * business rules.
     * </p>
     *
     * <p>Also, it is up to the implementing class to determine the default behavior.
     * For safety sake, it may be best to return "true" if the method is not able
     * to determine the state of the person's suppression.
     * </p>
     *
     * @param personId of the person being checked.
     * @return true if the user has directory suppression enabled.
     */
    public boolean hasDirectorySupression (String personId );

}
