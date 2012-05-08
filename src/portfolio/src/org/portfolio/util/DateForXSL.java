/* $Name:  $ */
/* $Id: DateForXSL.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * Created on Jan 10, 2005
 *
 */
package org.portfolio.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author G.R.Svenddal This class overrides the toString() method to format those Dates that are
 *         really just Month/Year or Month/Year/Day
 * 
 * Note for purists: this *is* a Date, it only modifies the toString(), a convenience method that's
 * not really supposed to be used in normal operation, as our Element=>XML converter does.
 * 
 *
 * Date objects where only M/D/Y or M/Y are actually valid. Rewrite the models and redo the XSL (
 * engine and/or templates ) to use either N>1 fields or something like this class - G.R.S.
 * 
 */
public class DateForXSL extends Date {

	private static final long serialVersionUID = 4736915319567673905L;
	private final boolean noDay;

	private static final DateFormat formatWithDays = new SimpleDateFormat("MMMMM d, yyyy");
	private static final DateFormat formatWithoutDays = new SimpleDateFormat("MMMMM yyyy");
	

	public DateForXSL(long date, boolean suppressDays) {
		super( date );
		this.noDay = suppressDays;
	}

	public String toString() {
		return noDay ? formatWithoutDays.format(this) : formatWithDays.format( this );
	}
}
