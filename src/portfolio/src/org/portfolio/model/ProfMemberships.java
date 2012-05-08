/* $Name:  $ */
/* $Id: ProfMemberships.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/ProfMemberships.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.7 $
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.client.tags.PortfolioTagConstants;
import org.portfolio.util.DateValidation;
import org.portfolio.util.PortfolioConstants;

/**
 * This class encapsulates ProfMemberships element object.
 *
 * @author      Jack Brown, University of Minnesota
 * @author	John Bush
 * @since	1.0
 * @version	$Revision: 1.7 $
 */
public class ProfMemberships extends ElementBase {

    private static final long serialVersionUID = 3516501181645837020L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getRole() {
        return (role != null ? role : EMPTY_STRING);
    }

    public void setRole(java.lang.String role) {
        this.role = role;
    }


    public java.lang.String getComments() {
        return (comments != null ? comments : EMPTY_STRING);
    }

    public void setComments(java.lang.String comments) {
        this.comments = comments;
    }

    public java.util.Date getMemberSince() {
        Date memberSince = null;
        if ( ( !getMemberSinceMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getMemberSinceYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getMemberSinceMonth() );
                int year = Integer.parseInt( getMemberSinceYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                memberSince = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return memberSince;
    }

    public void setMemberSince( java.util.Date memberSince ) {
        if ( memberSince != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( memberSince );

            setMemberSinceMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setMemberSinceYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }
    public String getMemberSinceMonth() {
        return ( memberSinceMonth != null ? memberSinceMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setMemberSinceMonth(String memberSinceMonth) {
        this.memberSinceMonth = ( memberSinceMonth != null ? memberSinceMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getMemberSinceYear() {
        return ( memberSinceYear != null ? memberSinceYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setMemberSinceYear(String memberSinceYear) {
        this.memberSinceYear = ( memberSinceYear != null ? memberSinceYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

// Added MemberUntil stuff BK 3/5/12
    public java.util.Date getMemberUntil() {
        Date memberUntil = null;
        if ( ( !getMemberUntilMonth().equals( PortfolioTagConstants.MONTH_DEFAULT ) ) &&
                ( !getMemberUntilYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) ) {
            try {
                // logService.debug( "getting month and year" );
                int month = Integer.parseInt( getMemberUntilMonth() );
                int year = Integer.parseInt( getMemberUntilYear() );

                Calendar cal = new GregorianCalendar( year, month, 1 );
                cal.setLenient(false);
                memberUntil = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            } catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return memberUntil;
    }

    public void setMemberUntil( java.util.Date memberUntil ) {
        if ( memberUntil != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( memberUntil );

            setMemberUntilMonth( String.valueOf( cal.get( Calendar.MONTH ) ) );
            setMemberUntilYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }
    public String getMemberUntilMonth() {
        return ( memberUntilMonth != null ? memberUntilMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public void setMemberUntilMonth(String memberUntilMonth) {
        this.memberUntilMonth = ( memberUntilMonth != null ? memberUntilMonth : PortfolioTagConstants.MONTH_DEFAULT );
    }

    public String getMemberUntilYear() {
        return ( memberUntilYear != null ? memberUntilYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setMemberUntilYear(String memberUntilYear) {
        this.memberUntilYear = ( memberUntilYear != null ? memberUntilYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

// Added Present member stuff BK 3/5/12
    public java.lang.String getPresentMember() {
        return (presentMember != null ? presentMember : EMPTY_STRING);
    }

    public void setPresentMember(java.lang.String presentMember) {
        this.presentMember = presentMember;
    }
    
    
    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Institution / Organization"));
        } else if(entryName.trim().length() > PortfolioConstants.SIXTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.SIXTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Institution / Organization", PortfolioConstants.SIXTY_CHARS_DESC));
        }

        if ((description != null) && (description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            description = description.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("description", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        if ((role != null) && (role.length() > PortfolioConstants.FIFTY_WORDS)) {
            role = role.substring(0, PortfolioConstants.FIFTY_WORDS);
            errors.add("role", new ActionMessage("error.lengthTooLong", "Role(s)", PortfolioConstants.FIFTY_WORDS_DESC));
        }

        if ((comments != null) && (comments.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            comments = comments.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("comments", new ActionMessage("error.lengthTooLong", "Additional comments", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }

        if ( DateValidation.checkDate( getMemberSinceMonth(), getMemberSinceYear()) == DateValidation.FAILURE ) {
            errors.add( "memberSince", new ActionMessage( "error.date.invalid", "Member since" ) );
        }

//  BK 3/5/12
        if ( DateValidation.checkDate( getMemberUntilMonth(), getMemberUntilYear()) == DateValidation.FAILURE ) {
            errors.add( "memberUntil", new ActionMessage( "error.date.invalid", "Member until" ) );
        }
        
        return errors;
    }

    protected String memberSinceMonth = PortfolioTagConstants.MONTH_DEFAULT;
    protected String memberSinceYear  = PortfolioTagConstants.YEAR_DEFAULT;
    protected String memberUntilMonth = PortfolioTagConstants.MONTH_DEFAULT; //  BK 3/5/12
    protected String memberUntilYear  = PortfolioTagConstants.YEAR_DEFAULT; //  BK 3/5/12
    protected java.lang.String presentMember = null; //  BK 3/5/12
    protected java.lang.String description = null;
    protected java.lang.String role = null;
    protected java.lang.String comments = null;
}
