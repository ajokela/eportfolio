/* $Name:  $ */
/* $Id: PortfolioCredential.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/security/authentication/PortfolioCredential.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $
 * $Revision: 1.3 $
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

public class PortfolioCredential implements java.io.Serializable {

    private static final long serialVersionUID = 433623352693585248L;
	private Object value;

    /**
     * Create a <code>PortfolioCredential</code> with no
     * value.
     *
     */
    public PortfolioCredential() {
    }

    /**
     * Create a <code>PortfolioCredential</code> using a
     * <code>Object</code> representation of the
     * value.
     *
     * <p>
     *
     * @param value the user identification number (UID) for this user.
     *
     */
    public PortfolioCredential(Object value) {
        this.value = value;
    }

    /**
     * Compares the specified Object with this
     * <code>PortfolioCredential</code>
     * for equality.  Returns true if the given object is also a
     * <code>PortfolioCredential</code> and the two
     * PortfolioCredential have the same value.
     *
     * <p>
     *
     * @param o Object to be compared for equality with this
     *		<code>PortfolioCredential</code>.
     *
     * @return true if the specified Object is equal equal to this
     *		<code>PortfolioCredential</code>.
     */
    public boolean equals(Object o) {

        if (o == null)
            return false;

        if (this == o)
            return true;

        if (o instanceof PortfolioCredential) {
            if (((PortfolioCredential) o).getValue().equals(value))
                return true;
            else
                return false;
        } else
            return false;
    }

    /**
     * Return a hash code for this <code>PortfolioCredential</code>.
     *
     * <p>
     *
     * @return a hash code for this <code>PortfolioCredential</code>.
     */
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Return a string representation of this
     * <code>PortfolioCredential</code>.
     *
     * <p>
     *
     * @return a string representation of this
     *		<code>PortfolioCredential</code>.
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Return the user name for this
     * <code>PortfolioCredential</code>.
     *
     * <p>
     *
     * @return the valuefor this
     *		<code>PortfolioCredential</code>
     */
    public Object getValue() {
        return value;
    }
}

