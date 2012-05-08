/* $Name:  $ */
/* $Id: Language.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Language.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.14 $
 * $Date: 2010/10/27 19:24:57 $
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

package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates Language element object.
 * 
 * @author John Bush
 * @author Jack Brown
 * @since 0.0
 * @version $Revision: 1.14 $
 */
public class Language extends ElementBase {

	private static final long serialVersionUID = 7069861200691857125L;

	public java.lang.String getEntryName() {
		return (entryName != null ? entryName : EMPTY_STRING);
	}

	public void setEntryName(java.lang.String entryName) {
		this.entryName = entryName;
	}

	public java.lang.String getLevelorCode() {
		return (levelorCode != null ? levelorCode : EMPTY_STRING);
	}

	public void setLevelorCode(java.lang.String levelorCode) {
		this.levelorCode = levelorCode;
	}

	public java.lang.String getLevelwrCode() {
		return (levelwrCode != null ? levelwrCode : EMPTY_STRING);
	}

	public void setLevelwrCode(java.lang.String levelwrCode) {
		this.levelwrCode = levelwrCode;
	}

	public java.lang.String getExperience() {
		return (experience != null ? experience : EMPTY_STRING);
	}

	public void setExperience(java.lang.String experience) {
		this.experience = experience;
	}

	// --------------------------------------------------------------------------
	// Methods dictated by implemented interfaces

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (request.getParameter("viewfile.x") != null)
			return errors;

		if ((entryName == null) || (entryName.trim().length() == 0)) {
			errors.add("entryName", new ActionMessage("error.required.missing", "Language"));
		} else if (entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
			entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
			errors.add("entryName", new ActionMessage("error.lengthTooLong", "Language", PortfolioConstants.FIFTY_CHARS_DESC));
		}
		// TODO validate Oral and Written fluency level

		// corrects bug 35
		if ((experience != null) && (experience.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
			experience = experience.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
			errors.add("experience", new ActionMessage("error.lengthTooLong", "Experience", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
		}
		return errors;
	}

	public void setIsRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public String getOralFluencyLevel() {
		return getCodeDesription(getLevelorCode());
	}

	public String getWrittenFluencyLevel() {
		return getCodeDesription(getLevelwrCode());
	}
	
	private String getCodeDesription(String code) {
		if ("NO".equals(code)) {
			return "Novice";
		} else if ("IN".equals(code)) {
			return "Intermediate";
		} else if ("AD".equals(code)) {
			return "Advanced";
		} else if ("SU".equals(code)) {
			return "Superior";
		}
		return "";
	}

	protected java.lang.String levelorCode = null;
	protected java.lang.String levelwrCode = null;
	protected java.lang.String experience = null;
	protected boolean isRemote = false;
}
