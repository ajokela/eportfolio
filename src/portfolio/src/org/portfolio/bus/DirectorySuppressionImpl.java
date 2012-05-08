/* $Name:  $ */
/* $Id: DirectorySuppressionImpl.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

/**
 * Default implementation. This implementation assumes no directory suppression
 * exists. Any institutions that have directory suppression will need to
 * implement their own version of ${link
 * org.portfolio.util.DirectorySuppression} in order to encapsulate their
 * business rules.
 * 
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.4 $ $Date: 2010/10/27 19:24:56 $
 * @since 1.0.0 Beta 4
 */
public class DirectorySuppressionImpl implements DirectorySuppression {

    public boolean hasDirectorySupression(String personId) {
        return false;
    }
}
