/* $Name:  $ */
/* $Id: Name.java,v 1.17 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

public class Name extends ElementBase {

    private static final long serialVersionUID = 4769778891816013135L;

    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
	
    public String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getFirstName() {
        return (firstName != null ? firstName : EMPTY_STRING);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return (middleName != null ? middleName : EMPTY_STRING);
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return (lastName != null ? lastName : EMPTY_STRING);
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTitle() {
        return (title != null ? title : EMPTY_STRING);
    }

    public void setTitle(String title) {
        this.title = title ;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if (request.getParameter("viewfile.x") != null) return errors;

        //Entry name length (required)
        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Type of name"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Type of name", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        //Check title length
        if(title != null && title.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            title = title.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("title", new ActionMessage("error.lengthTooLong", "Title", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }
        
        //Check first name length
        if(firstName != null && firstName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            firstName = firstName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("firstName", new ActionMessage("error.lengthTooLong", "First name", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Check middle name length
        if(middleName != null && middleName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            middleName = middleName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("middleName", new ActionMessage("error.lengthTooLong", "Middle name", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        //Check last name length
        if(lastName != null && lastName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            lastName = lastName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("lastName", new ActionMessage("error.lengthTooLong", "Last name", PortfolioConstants.FIFTY_CHARS_DESC));
        }
        
        return errors;
    }
}
