/* $Name:  $ */
/* $Id: AdvisorViewImpl.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.ArrayList;
import java.util.List;

import org.portfolio.model.Person;

/**
 * Default implementation does not define an advisor.
 * 
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.4 $ $Date: 2010/10/27 19:24:56 $
 * @since x.x
 */
public class AdvisorViewImpl implements AdvisorView {
    /**
     * Checks to see if the user is an advisor
     * 
     * @param personId
     * @return true only if the user is an advisor
     */
    public boolean isAdvisor(String personId) {
        return false;
    }

    /**
     * Accessor to an advisor's advisee list.
     * 
     * @param personId
     * @return a Map of lists of Person objects encapsulating the advisees, keyed by the first letter of the name. Should return null if the
     *         user is not an advisor.
     */
    public List<Person> getAdviseeList(String personId) {
        return new ArrayList<Person>();
    }
}
