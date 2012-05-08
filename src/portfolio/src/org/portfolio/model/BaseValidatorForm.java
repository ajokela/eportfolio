/* $Name:  $ */
/* $Id: BaseValidatorForm.java,v 1.4 2010/11/04 21:08:53 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/BaseValidatorForm.java,v 1.4 2010/11/04 21:08:53 ajokela Exp $
 * $Revision: 1.4 $
 * $Date: 2010/11/04 21:08:53 $
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
 * Portions Copyright (c) 2003 the r-smart group, inc.
 *
 * Acknowledgements
 *
 * Special thanks to the OSPI Users and Contributors for their suggestions and
 * support.
 */
package org.portfolio.model;

import org.portfolio.util.LogService;
import org.apache.struts.validator.ValidatorForm;

import java.io.Serializable;

// extend this class if using the Validator framework on a form

abstract public class BaseValidatorForm extends ValidatorForm implements Serializable {
   /**
	 * 
	 */
	private static final long serialVersionUID = -1623817987025010917L;
	protected transient LogService logService = new LogService(this.getClass());
	protected String action;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}

