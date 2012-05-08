/* $Name:  $ */
/* $Id: PortfolioPrincipalImpl.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/PortfolioPrincipalImpl.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.5 $
 * $Date: 2010/10/27 19:24:56 $
 *
 * ============================================================================
 *
 * The contents of this file are subject to the OSPI License Version 1.0 (the
 * License).  You may not copy or use this file, in either source code or
 * executable form, except in compliance with the License.  You may obtain a
 * copy of the License at http://www.theospi.org/.
 *
 * Software distributed under the License is distributed on an AS IS basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied.  See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * Copyrights:
 *
 * Portions created by or assigned to The University of Minnesota are Copyright
 * (c) 2003 The University of Minnesota.  All Rights Reserved.  Contact
 * information for OSPI is available at http://www.theospi.org/.
 *
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Portions Copyright (c) 2003 The University of Delaware.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */

package org.portfolio.client.security.authentication;

/* Security & JAAS imports */

import java.util.HashMap;

import org.portfolio.model.Person;

/**
 * <p>
 * Basic implementation of the Principal class. By implementing our own
 * Principal for our application, we can more easily add and remove
 * instances of our principals in the authenticated Subject during the
 * login and logout process.
 *
 * @see java.security.Principal
 * @author  Paul Feuer and John Musser
 * @author  Jack Brown, University of Minnesota, Web Development.
 * @version $Revision: 1.5 $
 * @since 1.0.0
 */

public class PortfolioPrincipalImpl implements PortfolioPrincipal {

    private static final long serialVersionUID = -674225455509826469L;
	
    private String name = "";
    private HashMap<String, Object> attributes = new HashMap<String, Object>();

    /**
     * Create a <code>PortfolioPrincipalImpl</code> with no
     * user name.
     *
     */
    public PortfolioPrincipalImpl() {
    }

    public PortfolioPrincipalImpl(Person p) {
    	setName(p.getUsername());
    	setAttribute(PortfolioAuthenticator.PERSON_ID, p.getPersonId());
    	setAttribute(PortfolioAuthenticator.FIRST_NAME, p.getFirstName());
    	setAttribute(PortfolioAuthenticator.LAST_NAME, p.getLastname());
    	setAttribute(PortfolioAuthenticator.MIDDLE_NAME, p.getMiddlename());
    	setAttribute(PortfolioAuthenticator.EMAIL, p.getEmail());
    	setAttribute(PortfolioAuthenticator.CAMPUS, p.getCampus());
    	setAttribute(PortfolioAuthenticator.USER_TYPE, p.getUsertype().toString());
    }

    /**
     * Create a <code>PortfolioPrincipalImpl</code> using a
     * <code>String</code> representation of the
     * user name.
     *
     * <p>
     *
     * @param newName the user identification number (UID) for this user.
     *
     */
    public PortfolioPrincipalImpl(String newName) {
        name = newName;
    }

    public void setName(String newName) {
        this.name = newName;
    }
   /**
    * Stores an attribute of the principal
    * @param key of the attribute (name)
    * @param value to store under that key
    */
    public void setAttribute(String key, Object value) {
       // checking for nulls - re: bug 69
       if ( key == null ) {
           // cannot insert if key is null
           return;
       } else {
           // can handle a null
           attributes.put( key, value );
       }
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    /**
     * Compares the specified Object with this
     * <code>PortfolioPrincipalImpl</code>
     * for equality.  Returns true if the given object is also a
     * <code>PortfolioPrincipalImpl</code> and the two
     * RdbmsPrincipals have the same name.
     *
     * @param o Object to be compared for equality with this
     *		<code>PortfolioPrincipalImpl</code>.
     *
     * @return true if the specified Object is equal equal to this
     *		<code>PortfolioPrincipalImpl</code>.
     */
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (o instanceof PortfolioPrincipalImpl) {
            if (((PortfolioPrincipalImpl) o).getName().equals(name))
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * Return a hash code for this <code>PortfolioPrincipalImpl</code>.
     *
     * @return a hash code for this <code>PortfolioPrincipalImpl</code>.
     */
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Return a string representation of this
     * <code>PortfolioPrincipalImpl</code>.
     *
     * @return a string representation of this
     *		<code>PortfolioPrincipalImpl</code>.
     */
    public String toString() {
        return name;
    }

    /**
     * Return the user name for this
     * <code>PortfolioPrincipalImpl</code>.
     *
     * @return the user name for this
     *		<code>PortfolioPrincipalImpl</code>
     */
    public String getName() {
        return name;
    }
}
