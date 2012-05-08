/* $Name:  $ */
/* $Id: PortfolioTagConstants.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.tags;

import org.portfolio.util.PortfolioConstants;

/*
* $Header: /opt/UMN-src/portfolio/src/org/portfolio/client/tags/PortfolioTagConstants.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $
* $Revision: 1.3 $
* $Date: 2010/10/27 19:24:56 $
*
 * =====================================================================
*
* Copyright (c) 2003, 2001, 1999, 1996
*    by the University of Minnesota, Web Development.
* All rights reserved.
*
* Contact Web Development at 1300 S. 2nd Street, Suite 660 WBOB,
*                            Minneapolis, MN 55454.
* Web Development is a unit of the Office of Information Technology (OIT).
*
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL THE UNIVERSITY OF MINNESOTA OR
* ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
* OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
*
* It is agreed that the below parties will embark upon an effort to
* transform the existing University of Minnesota Portfolio code base
* into a viable open source product. It is necessary to document the
* agreement between the three principle parties:
* the University of Minnesota, The RSmart Group, and the University
* of Delaware.
*
* The following basic tenets will govern the initial effort:
* .    Each party will provide resources to achieve the development
*      goals.
* .    Each party will provide a good faith effort to further the
*      adoption of the code base in the higher education market.
* .    All work that is performed in the initial development period
*     will be placed into the open source realm.
* .    No disclosure of code or intellectual property will occur
*     outside of the partnership during the development period.
* .    No redistribution of source or binary code may take place
*      during the development period outside of the above tenets.
*/


/**
 * org.portfolio.tags.PortfolioTagConstants.
 *
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.3 $ $Date: 2010/10/27 19:24:56 $
 * @since x.x
 */
public interface PortfolioTagConstants extends PortfolioConstants {

    /**
     * For getTextArea() - set wrap = off
     */
    public static final int NOWRAP = 0;
    /**
     * For getTextArea() - set wrap = virtual
     */
    public static final int VIRTUALWRAP = 1;
    /**
     * For getTextArea() - set wrap = physical
     */
    public static final int PHYSICALWRAP = 2;
    /**
     * edit mode - default behavior for fields
     */
    public static final int EDIT = 0;
    /**
     * field is disabled, but displays like a form field
     */
    public static final int DISABLED = 1;
    /**
     * prints value as text
     */
    public static final int TEXTONLY = 2;
    /**
     * password field
     */
    public static final int PASSWORD = 3;
    /**
     * readonly field
     */
    public static final int READONLY = 4;
    /**
     * space (non-breaking)
     */
    public static final String SPACE = "&nbsp;";
    /**
     * year only date field
     */
    public static final int YEAR = 1;
    /**
     * month/year date field
     */
    public static final int MONTH = 2;
    /**
     * day/month/year date field
     */
    public static final int DAY = 4;
    /**
     * for getDateString
     */
    public static final int FORMAT_TO_MINUTE = 1;
    /**
     * for getDateString
     */
    public static final int FORMAT_TO_DAY = 2;
    /**
     * for getDateString
     */
    public static final int FORMAT_TO_MONTH = 3;
    /**
     * for getDateString
     */
    public static final int FORMAT_TO_YEAR = 4;
    /**
     * An array of months
     */
    public static final String MONTHS[] = {"January", "February", "March",
                                              "April", "May", "June",
                                              "July", "August", "September",
                                              "October", "November", "December"};


    /** Default value for year */
    public static final String YEAR_DEFAULT = "YYYY";
    /** Default valud for day */
    public static final String DAY_DEFAULT = "DD";
    /** Default value for month */
    public static final String MONTH_DEFAULT = "-1";
    /**
     * Designates that there is not a maxlength on this field
     */
    public static final int NO_MAXIMUM_LENGTH = Integer.MIN_VALUE;

    /**
     * Default message for file size
     */
    public static final String DEFAULT_FILE_SIZE_MSG = "n/a";

    /** one kilobyte */
    public static final int KILOBYTE = 1024;

    /** One megabyte */
    public static final int MEGABYTE = 1024 * KILOBYTE;

    /** One gigabyte */
    public static final int GIGABYTE = 1024 * MEGABYTE;

}
