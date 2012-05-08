/* $Name:  $ */
/* $Id: ComputerSkills.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/ComputerSkills.java,v 1.8 2010/10/27 19:24:57 ajokela Exp $
 * $Revision: 1.8 $
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
 * This class encapsulates ComputerSkills element object.
 *
 * @author	John Bush
 * @author      Jack Brown, University of Minnesota
 * @since	0.0
 * @version	$Revision: 1.8 $
 */
public class ComputerSkills extends ElementBase {

    private static final long serialVersionUID = -4760045974516104214L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getGeneralOps() {
        return (generalOps != null ? generalOps : EMPTY_STRING);
    }

    public void setGeneralOps(java.lang.String generalOps) {
        this.generalOps = generalOps;
    }

    public java.lang.String getCommInt() {
        return (commInt != null ? commInt : EMPTY_STRING);
    }

    public void setCommInt(java.lang.String commInt) {
        this.commInt = commInt;
    }

    public java.lang.String getWordProc() {
        return (wordProc != null ? wordProc : EMPTY_STRING);
    }

    public void setWordProc(java.lang.String wordProc) {
        this.wordProc = wordProc;
    }

    public java.lang.String getSpreadsheet() {
        return (spreadsheet != null ? spreadsheet : EMPTY_STRING);
    }

    public void setSpreadsheet(java.lang.String spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public java.lang.String getDatabase() {
        return (database != null ? database : EMPTY_STRING);
    }

    public void setDatabase(java.lang.String database) {
        this.database = database;
    }

    public java.lang.String getGraphics() {
        return (graphics != null ? graphics : EMPTY_STRING);
    }

    public void setGraphics(java.lang.String graphics) {
        this.graphics = graphics;
    }

    public java.lang.String getApplications() {
        return (applications != null ? applications : EMPTY_STRING);
    }

    public void setApplications(java.lang.String applications) {
        this.applications = applications;
    }

    public java.lang.String getLanguages() {
        return (languages != null ? languages : EMPTY_STRING);
    }

    public void setLanguages(java.lang.String languages) {
        this.languages = languages;
    }

    public java.lang.String getExperience() {
        return (experience != null ? experience : EMPTY_STRING);
    }

    public void setExperience(java.lang.String experience) {
        this.experience = experience;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of skills"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of skills", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //General Operations length
        if(generalOps != null && generalOps.trim().length() > PortfolioConstants.FIFTEEN_CHARS) {
            generalOps = generalOps.trim().substring(0, PortfolioConstants.FIFTEEN_CHARS);
            errors.add("generalOps", new ActionMessage("error.lengthTooLong", "General computer operations", PortfolioConstants.FIFTEEN_CHARS_DESC));
        }

        //Communications-Internet length
        if(commInt != null && commInt.trim().length() > PortfolioConstants.FIFTEEN_CHARS) {
            commInt = commInt.trim().substring(0, PortfolioConstants.FIFTEEN_CHARS);
            errors.add("commInt", new ActionMessage("error.lengthTooLong", "Communication and internet" , PortfolioConstants.FIFTEEN_CHARS_DESC ));
        }

        //Word Processing length
        if(wordProc != null && wordProc.trim().length() > PortfolioConstants.FIFTEEN_CHARS) {
            wordProc = wordProc.trim().substring(0, PortfolioConstants.FIFTEEN_CHARS);
            errors.add("wordProc", new ActionMessage("error.lengthTooLong", "Word processing", PortfolioConstants.FIFTEEN_CHARS_DESC ));
        }

        //Spreadsheet length
        if(spreadsheet != null && spreadsheet.trim().length() > PortfolioConstants.FIFTEEN_CHARS) {
            spreadsheet = spreadsheet.trim().substring(0, PortfolioConstants.FIFTEEN_CHARS);
            errors.add("spreadsheet", new ActionMessage("error.lengthTooLong", "Spreadsheet", PortfolioConstants.FIFTEEN_CHARS_DESC ));
        }

        //Database length
        if(database != null && database.trim().length() > PortfolioConstants.FIFTEEN_CHARS) {
            database = database.trim().substring(0, PortfolioConstants.FIFTEEN_CHARS);
            errors.add("database", new ActionMessage("error.lengthTooLong", "Database", PortfolioConstants.FIFTEEN_CHARS_DESC ));
        }

        //Graphics length
        if(graphics != null && graphics.trim().length() > PortfolioConstants.FIFTEEN_CHARS) {
            graphics = graphics.trim().substring(0, PortfolioConstants.FIFTEEN_CHARS);
            errors.add("graphics", new ActionMessage("error.lengthTooLong","Graphics", PortfolioConstants.FIFTEEN_CHARS_DESC ));
        }

        if ((applications != null) && (applications.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            applications = applications.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("applications", new ActionMessage("error.lengthTooLong", "Applications", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((languages != null) && (languages.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            languages = languages.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("languages", new ActionMessage("error.lengthTooLong", "Languages", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        if ((experience != null) && (experience.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            experience = experience.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("experience", new ActionMessage("error.lengthTooLong", "Experience", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC ));
        }
        return errors;
    }

    public void setIsRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    public boolean isRemote() {
        return isRemote;
    }

    /**
     * <p>Returns a string representation of the object. In general, the
     * <code>toString</code> method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.</p>
     * <p>In the case of this object, it will show a conglomeration of the defining fields.</p>
     *
     * @return  a string representation of the object.
     */
    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString()).append("\n");

        // for this instance.
        buff.append("personId=").append(getPersonId()).append(",");
        buff.append("entryId=").append(getEntryId()).append(",");
        buff.append("entryName=").append(getEntryName()).append(",");
        buff.append("dateCreated=").append(getDateCreated()).append(",");
        buff.append("modifiedDate=").append(getModifiedDate()).append(",");
        buff.append("generalOps=").append(getGeneralOps()).append(",");
        buff.append("commInt=").append(getCommInt()).append(",");
        buff.append("wordProc=").append(getWordProc()).append(",");
        buff.append("spreadsheet=").append(getSpreadsheet()).append(",");
        buff.append("database=").append(getDatabase()).append(",");
        buff.append("graphics=").append(getGraphics()).append(",");
        buff.append("applications=").append(getApplications()).append(",");
        buff.append("languages=").append(getLanguages()).append(",");
        buff.append("experience=").append(getExperience()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }


    protected java.lang.String generalOps = null;
    protected java.lang.String commInt = null;
    protected java.lang.String wordProc = null;
    protected java.lang.String spreadsheet = null;
    protected java.lang.String database = null;
    protected java.lang.String graphics = null;
    protected java.lang.String applications = null;
    protected java.lang.String languages = null;
    protected java.lang.String experience = null;
    protected boolean isRemote = false;
}
