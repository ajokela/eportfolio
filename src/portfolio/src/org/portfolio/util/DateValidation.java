/* $Name:  $ */
/* $Id: DateValidation.java,v 1.11 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.util;

import org.portfolio.client.tags.PortfolioTagConstants;

import java.util.Calendar;
import java.util.GregorianCalendar;

/*
* $Header: /opt/UMN-src/portfolio/src/org/portfolio/util/DateValidation.java,v 1.11 2010/11/04 21:08:53 ajokela Exp $
* $Revision: 1.11 $
* $Date: 2010/11/04 21:08:53 $
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
 * This helper class performs date validation.<br /><br />
 *
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.11 $ $Date: 2010/11/04 21:08:53 $
 * @since 1.0.0 Beta 1
 */
public class DateValidation implements PortfolioTagConstants {
    /** Check passed */
    public static final int SUCCESS = 0;
    /** Checked failed */
    public static final int FAILURE = 1;
    /** Field Required but missing */
    public static final int MISSING_REQUIRED = 2;

    private static final String FIRST_DAY_OF_MONTH = "1";
    private static final String JANUARY = String.valueOf( Calendar.JANUARY );

    /** The class logger */
    private static LogService logService = new LogService( DateValidation.class );


    /**
     *  <p>This method checks to see if a date passed in is valid.
     * Note that the date fields are String values. Valid months are 0-11 (Jan-Dec).
     * Year values must be >1000, and day must be 1-31.
     *  @param strMonth the month (Expressed a String) of the year.
     *  @param strDate  the day (Expressed a String) of the month.
     *  @param strYear  the year(Expressed a String). Must be a 4 digit year.
     *  @param required whether or not the date field is required.
     *  @return SUCCESS, FAILURE, or MISSING_REQUIRED
     */
    public static int checkDate( String strMonth,
                                 String strDate,
                                 String strYear,
                                 boolean required ) {

        // If required, all fields must have non-default value
        if ( ( required ) &&
                ( ( strMonth == null ) ||
                ( strMonth.equalsIgnoreCase( MONTH_DEFAULT ) ||
                ( strDate == null ) ||
                strDate.equalsIgnoreCase( DAY_DEFAULT )  ||
                strYear == null ||
                strYear.equalsIgnoreCase( YEAR_DEFAULT ) ) ) ) {
            return MISSING_REQUIRED;
            // if !required, they may all be null or default values
        } else if ( ( !required ) &&
                    ( ( ( strMonth == null ) || ( strMonth.equalsIgnoreCase( MONTH_DEFAULT ) ) ) &&
                      ( ( strDate == null ) || ( strDate.equalsIgnoreCase( DAY_DEFAULT ) ) ) &&
                      ( ( strYear == null ) || ( strYear.equalsIgnoreCase( YEAR_DEFAULT ) ) )
                    )
                  ) {

        	return SUCCESS;
        }  else {
            logService.debug("Starting the date check.");
            // set the nulls to valid values
            if ( strMonth == null )
               strMonth = JANUARY;
            if ( strDate == null )
               strDate = FIRST_DAY_OF_MONTH;

            // However, all dates require at least a year, so let us start the checks
            if ( strYear == null ) {
                return FAILURE;
            }

            int month = 0;
            int date = 0;
            int year = 0;


            // Strip out leading zeros from the year
            if ( ( strYear != null ) && ( strYear.startsWith( "0" ) ) ) {
                logService.debug( "Stripping leading zeros.");
                while ( strYear.startsWith( "0" ) ) {
                    strYear = strYear.substring( 1 );
                }
            }

            // Now, to start with checking for year size
            if ( ( strYear != null ) &&
                    ( strYear.length() != 4 ) )
                return FAILURE;


            // First, the string values must be parsed into valid integers
            // Return if it fails to parse
            try {
                if  ( strMonth != null )  {
                   month = Integer.parseInt( strMonth );
                } else {
                    month = 0 ;
                }
                
                date = 1;
                
                // if a date is not specified, supply a default day of 1
                if ( strDate != null  ) {
                    date = Integer.parseInt( strDate );
                }
                
                year = Integer.parseInt( strYear );

            } catch ( NumberFormatException nfe ) {
                return FAILURE;
            }

            // Next, add to a unlenient GregorianCalendar object

            GregorianCalendar cal = new GregorianCalendar();
            cal.setLenient( false );

            cal.set( year, month, date );

            // now, try and see if we get an exception if we try and get the values
            try {
                logService.debug ("month is " + cal.get( Calendar.MONTH ));
                logService.debug( "day is " + cal.get( Calendar.DAY_OF_MONTH ));
                logService.debug( "year is " + cal.get( Calendar.YEAR ));
            } catch ( IllegalArgumentException iae ) {
                // will be thrown on an invalid date
                return FAILURE;
            }

            return SUCCESS;
        }
    } // checkDate(month, date, year, required);

    /**
     *  <p>This method checks to see if a date passed in is valid.
     * Note that the date fields are String values. Valid months are 0-11 (Jan-Dec).
     * Year values must be >1000. This version assumes !required
     *  @param month the month (Expressed a String) of the year.
     *  @param date the day of the month.
     *  @param year  the year(Expressed a String). Must be a 4 digit year.
     *  @return SUCCESS, FAILURE, or MISSING_REQUIRED
     */
    public static int checkDate( String month, String date, String year ) {
        return checkDate( month, date, year, false );
    }
    /**
     *  <p>This method checks to see if a date passed in is valid.
     * Note that the date fields are String values. Valid months are 0-11 (Jan-Dec).
     * Year values must be >1000. Sets the day to 1.
     *  @param month the month (Expressed a String) of the year.
     *  @param year  the year(Expressed a String). Must be a 4 digit year.
     *  @param required whether or not the date field is required.
     *  @return SUCCESS, FAILURE, or MISSING_REQUIRED
     */
    public static int checkDate( String month,
                                 String year,
                                 boolean required ) {
        if ( required ) {
            return checkDate( month, FIRST_DAY_OF_MONTH, year, required );
        } else {
            return checkDate( month, null, year, required );
        }
    }

    /**
     *  <p>This method checks to see if a date passed in is valid.
     * Note that the date fields are String values. Valid months are 0-11 (Jan-Dec).
     * Year values must be >1000. This version assumes !required
     *  @param month the month (Expressed a String) of the year.
     *  @param year  the year(Expressed a String). Must be a 4 digit year.
     *  @return SUCCESS, FAILURE, or MISSING_REQUIRED
     */
    public static int checkDate( String month, String year ) {
        return checkDate( month, year, false );
    }


    /**
     *  <p>This method checks to see if a date passed in is valid.
     * Note that the date fields are String values.
     * Year values must be >1000. Defaults the month to January, and the day to 1.
     *  @param year  the year(Expressed a String). Must be a 4 digit year.
     *  @param required whether or not the date field is required.
     *  @return SUCCESS, FAILURE, or MISSING_REQUIRED
     */
    public static int checkDate( String year, boolean required ) {
        if ( required ) {
            return checkDate( JANUARY, year, required );
        } else {
            return checkDate( null, year, required );
        }
    }

    /**
     *  <p>This method checks to see if a date passed in is valid.
     * Note that the date fields are String values.
     * Year values must be >1000. Assumes !required.
     *  @return SUCCESS, FAILURE, or MISSING_REQUIRED
     */
    public static int checkDate( String year ) {
        return checkDate( year, false );
    }
}
