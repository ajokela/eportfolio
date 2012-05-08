/* $Name:  $ */
/* $Id: FileSaveException.java,v 1.10 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

import java.io.IOException;

/**
 * Exception thrown on file save problems.
 * 
 * @author Jack Brown, University of Minnesota
 * @author Dan Valiga, University of Minnesota
 * @author Pete Boyson, Iowa State University
 * @version $Revision: 1.10 $ $Date: 2010/10/27 19:24:57 $
 * @since 1.0.0 Beta 3
 */
public class FileSaveException extends IOException {

    private static final long serialVersionUID = -6119453390777944570L;

    public FileSaveException() {
        super();
    }

    public FileSaveException(String message) {
        super(message);
    }

    public FileSaveException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
