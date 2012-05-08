/* $Name:  $ */
/* $Id: Publications.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Publications.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Publications element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	1.0
 * @version	$Revision: 1.7 $
 */
public class Publications extends ElementBase {

    private static final long serialVersionUID = 1662997670048003695L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getAuthor() {
        return (author != null ? author : EMPTY_STRING);
    }

    public void setAuthor(java.lang.String author) {
        this.author = author;
    }

    public java.lang.String getDescription() {
        return (description != null ? description : EMPTY_STRING);
    }

    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public java.lang.String getEditor() {
        return (editor != null ? editor : EMPTY_STRING);
    }

    public void setEditor(java.lang.String editor) {
        this.editor = editor;
    }

    public java.lang.String getCollTitle() {
        return (collTitle != null ? collTitle : EMPTY_STRING);
    }

    public void setCollTitle(java.lang.String collTitle) {
        this.collTitle = collTitle;
    }

    public java.lang.String getCollVol() {
        return (collVol != null ? collVol : EMPTY_STRING);
    }

    public void setCollVol(java.lang.String collVol) {
        this.collVol = collVol;
    }

    public java.lang.String getCollPage() {
        return (collPage != null ? collPage : EMPTY_STRING);
    }

    public void setCollPage(java.lang.String collPage) {
        this.collPage = collPage;
    }

    public java.lang.String getPubLoc() {
        return (pubLoc != null ? pubLoc : EMPTY_STRING);
    }

    public void setPubLoc(java.lang.String pubLoc) {
        this.pubLoc = pubLoc;
    }

    public java.lang.String getPubName() {
        return (pubName != null ? pubName : EMPTY_STRING);
    }

    public void setPubName(java.lang.String pubName) {
        this.pubName =  pubName;
    }

    public java.util.Date getPublDate() {
        Date publDate = null;
        if ( !getPublDateYear().equals( PortfolioTagConstants.YEAR_DEFAULT ) ) {
            try {
                // logService.debug( "getting month and year" );
                int year = Integer.parseInt( getPublDateYear() );

                Calendar cal = new GregorianCalendar( year, 0, 1 );
                cal.setLenient(false);
                publDate = new org.portfolio.util.DateForXSL(cal.getTime().getTime(),true);
            } catch ( NumberFormatException e ) {
                logService.debug( "Error parsing date ", e );
            }     catch ( IllegalArgumentException iae ) {
                logService.debug( "Invalid date caught", iae );
            }
        }
        return publDate;
    }

    public void setPublDate( java.util.Date publDate ) {
        if ( publDate  != null ) {
            GregorianCalendar cal = new GregorianCalendar();
            cal.setTime( publDate );

            setPublDateYear( String.valueOf( cal.get( Calendar.YEAR ) ) );
        }
    }

    public String getPublDateYear() {
        return ( publDateYear != null ? publDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public void setPublDateYear(String publDateYear) {
        this.publDateYear = ( publDateYear != null ? publDateYear : PortfolioTagConstants.YEAR_DEFAULT );
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Title of publication"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Title of publication", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        if ( ( author != null ) && ( author.length() > PortfolioConstants.ONE_HUNDRED_WORDS ) ) {
            author = author.substring( 0, PortfolioConstants.ONE_HUNDRED_WORDS );
            errors.add( "author", new ActionMessage( "error.lengthTooLong", "Author(s) of publication", PortfolioConstants.ONE_HUNDRED_WORDS_DESC ) );
        }

        if ( ( description != null ) && ( description.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS ) ) {
            description = description.substring( 0, PortfolioConstants.EIGHT_HUNDRED_WORDS );
            errors.add( "description", new ActionMessage( "error.lengthTooLong", "Description / Abstract", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ) );
        }

        if ( DateValidation.checkDate( getPublDateYear() ) == DateValidation.FAILURE ) {
            errors.add( "publDate", new ActionMessage( "error.date.invalid", "Year of publication" ) );
        }
        //Collection title length
        if(collTitle != null && collTitle.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            collTitle = collTitle.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("collTitle", new ActionMessage("error.lengthTooLong", "Title of collection", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Collection volume length
        if(collVol != null && collVol.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            collVol = collVol.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("collVol", new ActionMessage("error.lengthTooLong", "Volume and number of collection", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Collection page length
        if(collPage != null && collPage.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            collPage = collPage.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("collPage", new ActionMessage("error.lengthTooLong", "Page numbers", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Publisher name length
        if(pubName != null && pubName.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            pubName = pubName.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("pubName", new ActionMessage("error.lengthTooLong", "Name of publisher", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        //Publisher location length
        if(pubLoc != null && pubLoc.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            pubLoc = pubLoc.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("pubLoc", new ActionMessage("error.lengthTooLong", "Location of publisher", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        if ((editor != null) && (editor.length() > PortfolioConstants.FIFTY_WORDS)) {
            editor = editor.substring(0, PortfolioConstants.FIFTY_WORDS);
            errors.add("editor", new ActionMessage("error.lengthTooLong", "Editor(s) of Collection", PortfolioConstants.FIFTY_WORDS_DESC));
        }

        return errors;
    }

    private String publDateYear = PortfolioTagConstants.YEAR_DEFAULT;
    protected java.lang.String author = null;
    protected java.lang.String description = null;
    protected java.lang.String editor = null;
    protected java.lang.String collTitle = null;
    protected java.lang.String collVol = null;
    protected java.lang.String collPage = null;
    protected java.lang.String pubLoc = null;
    protected java.lang.String pubName = null;
}
