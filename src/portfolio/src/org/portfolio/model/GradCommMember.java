/* $Name:  $ */
/* $Id: GradCommMember.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/GradCommMember.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.13 $
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
 * This class encapsulates GradCommMember element object.
 * 
 * @author John Bush
 * @author Jack Brown, University of Minnesota
 * @since 0.0
 * @version $Revision: 1.13 $
 */
public class GradCommMember extends ElementBase {

	private static final long serialVersionUID = 2457743772140152021L;
	
	public java.lang.String getMasters() {
		return (masters != null ? masters : EMPTY_STRING);
	}

	public void setMasters(java.lang.String masters) {
		this.masters = masters;
        //logService.debug( "masters = " + masters );
	}

	public java.lang.String getDocPrelimWritten() {
		return (docPrelimWritten != null ? docPrelimWritten : EMPTY_STRING);
	}

	public void setDocPrelimWritten(java.lang.String docPrelimWritten) {
		this.docPrelimWritten = docPrelimWritten;
        //logService.debug( "prelimWritten = " + docPrelimWritten );
	}

	public java.lang.String getDocPrelimOral() {
		return (docPrelimOral != null ? docPrelimOral : EMPTY_STRING);
	}

	public void setDocPrelimOral(java.lang.String docPrelimOral) {
		this.docPrelimOral = docPrelimOral;
        //logService.debug( "prelimOral = " + docPrelimOral );
	}

	public java.lang.String getDocFinalOral() {
		return (docFinalOral != null ? docFinalOral : EMPTY_STRING);
	}

	public void setDocFinalOral(java.lang.String docFinalOral) {
		this.docFinalOral = docFinalOral;
        //logService.debug( "finalOral = " + docFinalOral );
	}

	public java.lang.String getFirstName() {
		return (firstName != null ? firstName : EMPTY_STRING);
	}

	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}

	public java.lang.String getEntryName() {
		return (entryName != null ? entryName : EMPTY_STRING);
	}

	public void setEntryName(java.lang.String entryName) {
		this.entryName = entryName;
	}

	public java.lang.String getAdviser() {
		return (adviser != null ? adviser : EMPTY_STRING);
	}

	public void setAdviser(java.lang.String adviser) {
		this.adviser = adviser;
	}

	public java.lang.String getMajorFieldChair() {
		return (majorFieldChair != null ? majorFieldChair : EMPTY_STRING);
	}

	public void setMajorFieldChair(java.lang.String majorFieldChair) {
		this.majorFieldChair = majorFieldChair;
	}

	public java.lang.String getMajorFieldMember() {
		return (majorFieldMember != null ? majorFieldMember : EMPTY_STRING);
	}

	public void setMajorFieldMember(java.lang.String majorFieldMember) {
		this.majorFieldMember = majorFieldMember;
	}

	public java.lang.String getMajorFieldReviewer() {
		return (majorFieldReviewer != null ? majorFieldReviewer : EMPTY_STRING);
	}

	public void setMajorFieldReviewer(java.lang.String majorFieldReviewer) {
		this.majorFieldReviewer = majorFieldReviewer;
	}

	public java.lang.String getSupportingFieldChair() {
		return (supportingFieldChair != null ? supportingFieldChair : EMPTY_STRING);
	}

	public void setSupportingFieldChair(java.lang.String supportingFieldChair) {
		this.supportingFieldChair = supportingFieldChair;
	}

	public java.lang.String getSupportingFieldMember() {
		return (supportingFieldMember != null ? supportingFieldMember : EMPTY_STRING);
	}

	public void setSupportingFieldMember(java.lang.String supportingFieldMember) {
		this.supportingFieldMember = supportingFieldMember;
	}

	public java.lang.String getSupportingFieldReviewer() {
		return (supportingFieldReviewer != null ? supportingFieldReviewer : EMPTY_STRING);
	}

	public void setSupportingFieldReviewer(java.lang.String supportingFieldReviewer) {
		this.supportingFieldReviewer = supportingFieldReviewer;
	}

	public java.lang.String getCampusAddress() {
		return (campusAddress != null ? campusAddress : EMPTY_STRING);
	}

	public void setCampusAddress(java.lang.String campusAddress) {
		this.campusAddress = campusAddress;
	}

	public java.lang.String getPhone() {
		return (phone != null ? phone : EMPTY_STRING);
	}

	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	public java.lang.String getEmailAddress() {
		return (emailAddress != null ? emailAddress : EMPTY_STRING);
	}

	public void setEmailAddress(java.lang.String emailAddress) {
		this.emailAddress = emailAddress;
	}
	

	// --------------------------------------------------------------------------
	// Methods dictated by implemented interfaces

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
		ActionErrors errors = new ActionErrors();
		if (request.getParameter("viewfile.x") != null)
			return errors;

		if ((entryName == null) || (entryName.trim().length() == 0)) {
			errors.add("entryName", new ActionMessage("error.required.missing", "Last name"));
		} else if (entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
			entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
			errors.add("entryName", new ActionMessage("error.lengthTooLong", "Last name", PortfolioConstants.FIFTY_CHARS_DESC));
		}

		// First name length
		if (firstName != null && firstName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
			firstName = firstName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
			errors.add("firstName", new ActionMessage("error.lengthTooLong", "First name", PortfolioConstants.FIFTY_CHARS_DESC));
		}

		// Campus address length
		if (campusAddress != null && campusAddress.trim().length() > PortfolioConstants.FIFTY_CHARS) {
			campusAddress = campusAddress.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
			errors.add("campusAddress", new ActionMessage("error.lengthTooLong", "Campus address", PortfolioConstants.FIFTY_CHARS_DESC));
		}

		// Phone length
		if (phone != null && phone.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
			phone = phone.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
			errors.add("phone", new ActionMessage("error.lengthTooLong", "Phone", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
		}

		// Email address length
		if (emailAddress != null && emailAddress.trim().length() > PortfolioConstants.FIFTY_CHARS) {
			emailAddress = emailAddress.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
			errors.add("emailAddress", new ActionMessage("error.lengthTooLong", "Email address", PortfolioConstants.FIFTY_CHARS_DESC));
		}

		return errors;
	}

	public void setIsRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public boolean isRemote() {
		return isRemote;
	}

	protected java.lang.String masters = null;
	protected java.lang.String docPrelimWritten = null;
	protected java.lang.String docPrelimOral = null;
	protected java.lang.String docFinalOral = null;
	protected java.lang.String firstName = null;
	protected java.lang.String adviser = null;
	protected java.lang.String majorFieldChair = null;
	protected java.lang.String majorFieldMember = null;
	protected java.lang.String majorFieldReviewer = null;
	protected java.lang.String supportingFieldChair = null;
	protected java.lang.String supportingFieldMember = null;
	protected java.lang.String supportingFieldReviewer = null;
	protected java.lang.String campusAddress = null;
	protected java.lang.String phone = null;
	protected java.lang.String emailAddress = null;
	protected boolean isRemote = false;
}
