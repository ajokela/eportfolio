/* $Name:  $ */
/* $Id: AdvisorView.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.util.List;

import org.portfolio.model.Person;

/**
 * Defines the API for determining if a user is an "Advisor", i.e.,
 * able to see default views of their advisees.
 * It will also define the accessor to the advisee list.
 */
public interface AdvisorView {
	
    /**
     * Checks to see if the user is an advisor
     * @param personId
     * @return true only if the user is an advisor
     */
    public boolean isAdvisor ( String personId );

    /**
     * Accessor to an advisor's advisee list.
     *
     * @param personId
     * @return a list of persons (advisees) or an empty list if none found. never null.
     */    
    public List<Person> getAdviseeList(String personId);
}
