/* $Name:  $ */
/* $Id: LogService.java,v 1.18 2011/03/14 19:37:44 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/util/LogService.java,v 1.18 2011/03/14 19:37:44 ajokela Exp $
 * $Revision: 1.18 $
 * $Date: 2011/03/14 19:37:44 $
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

package org.portfolio.util;

import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
import org.apache.log4j.xml.DOMConfigurator;
import java.io.StringWriter;

/**
 * Logger class to be used by all class as standard
 * 
 * @author Idea taken from uPortal by <a href="http://www.ja-sig.org/">JASIG</a>.
 *         Modified and adapted to OSPI by <a href="felipeen@udel.edu">Luis F.C.
 *         Mendes</a> - University of Delaware
 * @version $Revision: 1.18 $
 */
public class LogService implements Serializable {

	private static final long serialVersionUID = 3208621336494902089L;
	/**
	 * The FATAL level designates very severe error events that will presumably
	 * lead the application to abort.
	 */
	public final static Priority FATAL = Priority.FATAL;
	/**
	 * The ERROR level designates error events that might still allow the
	 * application to continue running.
	 */
	public final static Priority ERROR = Priority.ERROR;
	/**
	 * The WARN level designates potentially harmful situations.
	 */
	public final static Priority WARN = Priority.WARN;
	/**
	 * The INFO level designates informational messages that highlight the
	 * progress of the application at coarse-grained level.
	 */
	public final static Priority INFO = Priority.INFO;
	/**
	 * The DEBUG priority designates fine-grained informational events that are
	 * most useful to debug an application.
	 */
	public final static Priority DEBUG = Priority.DEBUG;

	private Logger logger = null;
	
	/**
	 * Default constructor. Uses the default (root) logger.
	 */
	public LogService() {
		this.logger = Logger.getRootLogger();
	}

	/**
	 * Gets a logger for the loggerClass passed.
	 * 
	 * @param loggerClass the class instantiating the LogService.
	 */
	public LogService(Class<?> loggerClass) {
		if (loggerClass != null) {
			this.logger = Logger.getLogger(loggerClass);
		} else {
			this.logger = Logger.getRootLogger();
		}
	}

	/**
	 * Gets a logger for the loggerClass passed.
	 * 
	 * @param loggerName the name of the category for instantiating the
	 *            LogService.
	 */
	public LogService(String loggerName) {
		if (loggerName != null) {
			this.logger = Logger.getLogger(loggerName);
		} else {
			this.logger = Logger.getRootLogger();
		}
	}

	public static void setLogURL(URL logurl) {
        DOMConfigurator.configure(logurl);
	}

	public void log(Priority logLevel, String sMessage) {
		// only try logging if log level is enabled
		if (this.logger.isEnabledFor(logLevel)) {
			try {
				this.logger.log(logLevel, sMessage);
			} catch (Exception e) {
				System.err.println("Problem writing to log.");
				e.printStackTrace();
			} catch (Error er) {
				System.err.println("Problem writing to log.");
				er.printStackTrace();
			}
		}
	}

	public void log(Priority logLevel, Throwable ex) {
		// only try logging if log level is enabled
		if (this.logger.isEnabledFor(logLevel)) {
			try {
				this.logger.log(logLevel, "EXCEPTION: " + ex, ex);
			} catch (Exception e) {
				System.err.println("Problem writing to log.");
				e.printStackTrace();
			} catch (Error er) {
				System.err.println("Problem writing to log.");
				er.printStackTrace();
			}
		}
	}

	public void log(Priority logLevel, String sMessage, Throwable ex) {
		// only try logging if log level is enabled
		if (this.logger.isEnabledFor(logLevel)) {
			try {
				this.logger.log(logLevel, sMessage, ex);
			} catch (Exception e) {
				System.err.println("Problem writing to log.");
				e.printStackTrace();
			} catch (Error er) {
				System.err.println("Problem writing to log.");
				er.printStackTrace();
			}
		}
	}

	public void log(String sMessage) {
		// only try logging if log level is enabled
		if (this.logger.isEnabledFor(Priority.INFO)) {
			try {
				this.logger.log(INFO, sMessage);
			} catch (Exception e) {
				System.err.println("Problem writing to log.");
				e.printStackTrace();
			} catch (Error er) {
				System.err.println("Problem writing to log.");
				er.printStackTrace();
			}
		}
	}

	/**
	 * Log a message object with the DEBUG level.
	 * 
	 * @param message the message object to log.
	 */
	public void debug(String message) {
		this.log(DEBUG, message);
	}
	
	public void debug(Exception e) {
    	StringWriter sw = new StringWriter();
    	e.printStackTrace(new PrintWriter(sw));
    	String stacktrace = sw.toString();
    	
    	this.debug(stacktrace);
	}

	/**
	 * Convenience method to debug formatted strings. See
	 * {@link String#format(String, Object...)}
	 * 
	 * @param message the message object to log.
	 * @param args the tokens to format in the message
	 */
	public void debugf(String message, Object... args) {
		this.log(DEBUG, String.format(message, args));
	}

	/**
	 * Log a message object with the DEBUG level. Will print a stack trace of
	 * the Throwable.
	 * 
	 * @param message the message object to log.
	 * @param t the java.lang.Throwable
	 */
	public void debug(String message, Throwable t) {
		this.log(DEBUG, message, t);
	}

	/**
	 * Log a message object with the INFO level.
	 * 
	 * @param message the message object to log.
	 */
	public void info(String message) {
		this.log(INFO, message);
	}

	/**
	 * Log a message object with the INFO level. Will print a stack trace of the
	 * Throwable.
	 * 
	 * @param message the message object to log.
	 * @param t the java.lang.Throwable
	 */
	public void info(String message, Throwable t) {
		this.log(INFO, message, t);
	}

	/**
	 * Log a message object with the ERROR level.
	 * 
	 * @param message the message object to log.
	 */
	public void error(String message) {
		this.log(ERROR, message);
	}

	/**
	 * Log a message object with the ERROR level. Will print a stack trace of
	 * the Throwable.
	 * 
	 * @param message the message object to log.
	 * @param t the java.lang.Throwable
	 */
	public void error(String message, Throwable t) {
		this.log(ERROR, message, t);
	}

	/**
	 * Log a message object with the ERROR level. Will (only) print a stack
	 * trace of the Throwable
	 * 
	 * @param t the java.lang.Throwable
	 */
	public void error(Throwable t) {
		this.log(ERROR, t);
	}

	/**
	 * Log a message object with the WARN level. Will (only) print a stack trace
	 * of the Throwable
	 * 
	 * @param t the java.lang.Throwable
	 */
	public void warn(Throwable t) {
		this.log(WARN, t);
	}

	/**
	 * Log a message object with the WARN level.
	 * 
	 * @param message the message object to log.
	 */
	public void warn(String message) {
		this.log(WARN, message);
	}

	/**
	 * Log a message object with the WARN level. Will print a stack trace of the
	 * Throwable.
	 * 
	 * @param message the message object to log.
	 * @param t the java.lang.Throwable
	 */
	public void warn(String message, Throwable t) {
		this.log(WARN, message, t);
	}

	/**
	 * Log a message object with the FATAL level.
	 * 
	 * @param message the message object to log.
	 */
	public void fatal(String message) {
		this.log(FATAL, message);
	}

	/**
	 * Log a message object with the FATAL level. Will print a stack trace of
	 * the Throwable.
	 * 
	 * @param message the message object to log.
	 * @param t the java.lang.Throwable
	 */
	public void fatal(String message, Throwable t) {
		this.log(FATAL, message, t);
	}

	/**
	 * Log a message object with the FATAL level. Will (only) print a stack
	 * trace of the Throwable
	 * 
	 * @param t the java.lang.Throwable
	 */
	public void fatal(Throwable t) {
		this.log(FATAL, t);
	}

	public void debug(Throwable cause) {
		
		this.log(DEBUG, cause);
	}
}
