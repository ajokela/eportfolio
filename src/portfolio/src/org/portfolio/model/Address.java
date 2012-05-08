/* $Name:  $ */
/* $Id: Address.java,v 1.14 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.util.PortfolioConstants;

public class Address extends ElementBase {

    private String street1;
    private String street2;
    private String street3;
    private String street4;
    
    private String city;
    private String state;
    private String zip;
    private String country;

    private static final long serialVersionUID = 4831314732637868636L;

    public String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(String entryName) {
        this.entryName = entryName;
    }

    public String getStreet1() {
        return (street1 != null ? street1 : EMPTY_STRING);
    }

    public void setStreet1(String street1) {
        this.street1 = street1;
    }

    public String getStreet2() {
        return (street2 != null ? street2 : EMPTY_STRING);
    }

    public void setStreet2(String street2) {
        this.street2 = street2;
    }

    public String getCity() {
        return (city != null ? city : EMPTY_STRING);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return (state != null ? state : EMPTY_STRING);
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return (zip != null ? zip : EMPTY_STRING);
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return (country != null ? country : EMPTY_STRING);
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null)
            return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Type of Address"));
        } else if (entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Type of Address", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // Street1 length
        if (street1 != null && street1.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            street1 = street1.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("street1", new ActionMessage("error.lengthTooLong", "Street Address 1", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // Street2 length
        if (street2 != null && street2.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            street2 = street2.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("street2", new ActionMessage("error.lengthTooLong", "Street Address 2", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        // City length
        if (city != null && city.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            city = city.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("city", new ActionMessage("error.lengthTooLong", "City", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        // State length
        if (state != null && state.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            state = state.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("state", new ActionMessage("error.lengthTooLong", "State", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        // Zip code length
        if (zip != null && zip.trim().length() > PortfolioConstants.TEN_CHARS) {
            zip = zip.trim().substring(0, PortfolioConstants.TEN_CHARS);
            errors.add("zip", new ActionMessage("error.lengthTooLong", "Zip / Postal Code", PortfolioConstants.TEN_CHARS_DESC));
        }

        // Country length
        if (country != null && country.trim().length() > PortfolioConstants.TWENTY_FIVE_CHARS) {
            country = country.trim().substring(0, PortfolioConstants.TWENTY_FIVE_CHARS);
            errors.add("country", new ActionMessage("error.lengthTooLong", "Country", PortfolioConstants.TWENTY_FIVE_CHARS_DESC));
        }

        return errors;
    }

    public String getStreet3() {
        return street3;
    }

    public void setStreet3(String street3) {
        this.street3 = street3;
    }

    public String getStreet4() {
        return street4;
    }

    public void setStreet4(String street4) {
        this.street4 = street4;
    }
}
