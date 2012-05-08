/* $Name:  $ */
/* $Id: DateUtil.java,v 1.7 2011/03/14 19:37:44 ajokela Exp $ */
package org.portfolio.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {
	
    public static String relativeFormat(Date d) {
        if (d == null) {
            return "";
        }
        
        Calendar now = Calendar.getInstance();
        Calendar then = Calendar.getInstance();
        then.setTime(d);
        
        if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR) && now.get(Calendar.DAY_OF_YEAR) == then.get(Calendar.DAY_OF_YEAR)) {
            return String.format("%tl:%<tM %<tp", d);
        } else if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR)) {
            return new SimpleDateFormat("MMM d").format(d);
        } else {
            return new SimpleDateFormat("M/d/yy").format(d);
        }
    }
    public static List<String> processDates(String dateFromString, String dateToString) {
        
    	Date dateFrom = null;
        Date dateTo   = null;
        LogService logService = new LogService(DateUtil.class);
        
        if (dateFromString != null && dateFromString.trim().length() > 0 && !dateFromString.equalsIgnoreCase("none")) {
            try {
                dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(dateFromString);
            } catch (ParseException e) {
                
            	logService.debug(e);
            }
        } else {
            dateFrom = null;
        }            

        if (dateToString != null && dateToString.trim().length() > 0 && !dateToString.equalsIgnoreCase("none")) {
            try {
                dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(dateToString);
            } catch (ParseException e) {
                logService.debug(e);
            }
        } else {
            dateTo = null;
        }            

        if (dateFrom == null) {

        	dateFromString = null;
        }
        	
        if (dateTo == null) {
            
        	dateToString = null;
        }

        List<String> dates =  new ArrayList<String>();
        
        dates.add(dateFromString);
        dates.add(dateToString);
        
        return dates;
    }
}
