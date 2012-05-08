/* $Name:  $ */
/* $Id: DataAccessException.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import java.sql.SQLException;

/**
 * This exception indicates that something has gone wrong while attempting to
 * access data. It is preferable to use this in a data access object rather than
 * declaring methods to throw {@link SQLException}.
 * 
 * @author Matt Sheehan
 * 
 */
public class DataAccessException extends RuntimeException {

	private static final long serialVersionUID = -6858042155502428360L;

	public DataAccessException() {
		super();
	}

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
