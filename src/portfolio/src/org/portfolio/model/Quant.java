/* $Name:  $ */
/* $Id: Quant.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/Quant.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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
 * This class encapsulates Quant element object.
 *
 * @author	John Bush
 * @author      Jack Brown
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class Quant extends ElementBase {

    private static final long serialVersionUID = 6975171128918895425L;
	
    public java.lang.String getEntryName() {
        return (entryName != null ? entryName : EMPTY_STRING);
    }

    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }

    public java.lang.String getElemAlgebra() {
        return (elemAlgebra != null ? elemAlgebra : EMPTY_STRING);
    }

    public void setElemAlgebra(java.lang.String elemAlgebra) {
        this.elemAlgebra = elemAlgebra;
    }

    public java.lang.String getColAlgebra() {
        return (colAlgebra != null ? colAlgebra : EMPTY_STRING);
    }

    public void setColAlgebra(java.lang.String colAlgebra) {
        this.colAlgebra = colAlgebra;
    }

    public java.lang.String getGeometry() {
        return (geometry != null ? geometry : EMPTY_STRING);
    }

    public void setGeometry(java.lang.String geometry) {
        this.geometry = geometry;
    }

    public java.lang.String getTrigonometry() {
        return (trigonometry != null ? trigonometry : EMPTY_STRING);
    }

    public void setTrigonometry(java.lang.String trigonometry) {
        this.trigonometry = trigonometry;
    }

    public java.lang.String getCalculus() {
        return (calculus != null ? calculus : EMPTY_STRING);
    }

    public void setCalculus(java.lang.String calculus) {
        this.calculus = calculus;
    }

    public java.lang.String getHigherMath() {
        return (higherMath != null ? higherMath : EMPTY_STRING);
    }

    public void setHigherMath(java.lang.String higherMath) {
        this.higherMath = higherMath;
    }

    public java.lang.String getStatistics() {
        return (statistics != null ? statistics : EMPTY_STRING);
    }

    public void setStatistics(java.lang.String statistics) {
        this.statistics = statistics;
    }

    public java.lang.String getAccounting() {
        return (accounting != null ? accounting : EMPTY_STRING);
    }

    public void setAccounting(java.lang.String accounting) {
        this.accounting = accounting;
    }

    public java.lang.String getLogic() {
        return (logic != null ? logic : EMPTY_STRING);
    }

    public void setLogic(java.lang.String logic) {
        this.logic = logic;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null) return errors;

        if ((entryName == null) || (entryName.trim().length() == 0)) {
            errors.add("entryName", new ActionMessage("error.required.missing", "Name of skills"));
        } else if(entryName.trim().length() > PortfolioConstants.FIFTY_CHARS) {
            entryName = entryName.trim().substring(0, PortfolioConstants.FIFTY_CHARS);
            errors.add("entryName", new ActionMessage("error.lengthTooLong", "Name of skills", PortfolioConstants.FIFTY_CHARS_DESC));
        }

        if ((elemAlgebra != null) && (elemAlgebra.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            elemAlgebra = elemAlgebra.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("elemAlgebra", new ActionMessage("error.lengthTooLong", "Elementary algebra", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((colAlgebra != null) && (colAlgebra.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            colAlgebra = colAlgebra.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("colAlgebra", new ActionMessage("error.lengthTooLong", "College algebra", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((geometry != null) && (geometry.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            geometry = geometry.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("geometry", new ActionMessage("error.lengthTooLong", "Geometry", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((trigonometry != null) && (trigonometry.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            trigonometry = trigonometry.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("trigonometry", new ActionMessage("error.lengthTooLong", "Trigonometry", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((calculus != null) && (calculus.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            calculus = calculus.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("calculus", new ActionMessage("error.lengthTooLong", "Calculus", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((higherMath != null) && (higherMath.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            higherMath = higherMath.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("higherMath", new ActionMessage("error.lengthTooLong", "Higher mathematics", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((statistics != null) && (statistics.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            statistics = statistics.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("statistics", new ActionMessage("error.lengthTooLong", "Statistics", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((accounting != null) && (accounting.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            accounting = accounting.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("accounting", new ActionMessage("error.lengthTooLong", "Accounting", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        if ((logic != null) && (logic.length() > PortfolioConstants.EIGHT_HUNDRED_WORDS)) {
            logic = logic.substring(0, PortfolioConstants.EIGHT_HUNDRED_WORDS);
            errors.add("logic", new ActionMessage("error.lengthTooLong", "Logic", PortfolioConstants.EIGHT_HUNDRED_WORDS_DESC));
        }
        return errors;
    }

    protected java.lang.String elemAlgebra = null;
    protected java.lang.String colAlgebra = null;
    protected java.lang.String geometry = null;
    protected java.lang.String trigonometry = null;
    protected java.lang.String calculus = null;
    protected java.lang.String higherMath = null;
    protected java.lang.String statistics = null;
    protected java.lang.String accounting = null;
    protected java.lang.String logic = null;
}
