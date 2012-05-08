/* $Name:  $ */
/* $Id: SequenceGenerator.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.SQLException;

/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/dao/SequenceGenerator.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.4 $
 * $Date: 2010/10/27 19:24:57 $
 *
 * =====================================================================
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

/**
 * Abstract class defining a SequenceGenerator
 */
public interface SequenceGenerator {

    /**
     * Get the next sequence value from the named sequence.
     * @param name the name of the sequence we are referencing.
     * @return the next value.
     * @throws SQLException
     */
    public abstract int getNextSequenceNumber(String name);
}
