
/* $Name:  $ */
/* $Id: DateFieldTag.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.tags;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.jsp.tagext.TagSupport;

/**
 * Custom tag for creating date fields.
 * @see org.portfolio.util.DateValidation
 *
 * @author Jack Brown, University of Minnesota
 * @version $Revision: 1.6 $ $Date: 2010/11/04 21:08:53 $
 * @since 1.0.0 Beta 1
 */
@SuppressWarnings("unchecked")
public class DateFieldTag extends TagSupport
                          implements PortfolioTagConstants {

    private static final long serialVersionUID = -5900170044798492850L;

	// private static final LogService logService = new LogService(DateFieldTag.class);

    private String name = null;
    private String property = null;
    private List<String> ids = null;
    private String title = null;
    private Date date = null;
    private int flag = 0;
    private int format = 0;
    private String style = null;
    private String events = null;
    private int mode = EDIT;

    private static List<String> MONTHLIST = null;
    private static List<String> MONTH_VALUE_LIST = null;



    public void setName( String name ) {
        this.name = name;
    }

    public void setProperty( String property ) {
        this.property = property;
    }
    
    public void setIds( String ids ) {
    	if (ids != null) {
    		String[] parts = ids.split(",");
    		this.ids = new ArrayList<String>();
    		
    		for(int i=0; i<parts.length; ++i) {
    			this.ids.add(parts[i]);
    		}
    		
    	}
    }
    
    /*
    public void setIds( List<String> ids ) {
        this.ids = ids;
    }
	*/
    
    public void setTitle( String title ) {
        this.title = title;
    }

    public void setDate( Date date ) {
        this.date = date;
    }

    public void setFlag( int flag ) {
        this.flag = flag;
    }

    public void setStyle( String style ) {
        this.style = style;
    }

    public void setEvents( String events ) {
        this.events = events;
    }

    public void setMode( int mode ) {
        this.mode = mode;
    }

    public void setFormat( int format ) {
        this.format = format;
    }

    /**
     * <P>Creates an HTML "date field". This will have a drop down for the
     *    Month, a 4 character field for the year, and a 2 character field
     *    for the day of the month. The only difficulty for this will be that
     *    the three fields will need to be combined to be useful.
     * <P>The field names will be created as nameYear, nameMonth, nameDay
     * name	of the bean - must be of type ElementDataObject
     * property bean field.
     * ids	the IDs of the field(s) - should be in the order
     *			of YEAR, MONTH, DAY. Omit missing fields.
     * title	of the field
     * flag	either DAY, MONTH, YEAR
     * style	the name of the style class to use on the entire form<br/>
     *                   May be <code>null</code>
     * events	any Javascript events you wish to include, such as onSubmit
     * mode	flag of EDIT, DISABLED, TEXTONLY to determine method
     *                   of display
     * @return		a String representation of the field
     */
    public int doEndTag() {
        StringBuffer buff = new StringBuffer();

        Object bean = pageContext.findAttribute(name);

        // get the date.
        try {
           @SuppressWarnings("rawtypes")
           Class c = bean.getClass();
           @SuppressWarnings("rawtypes")
           Class[] cArray = null; 
           Method method = c.getMethod(getMethodName(property), cArray);
           Object[] oArray = null;
           this.setDate( (Date) method.invoke(bean, oArray));
        } catch ( Exception e ) {
            this.setDate(null);
        }

        GregorianCalendar cal = null;
        if ( date != null ) {
            cal = new GregorianCalendar();
            cal.setTime( date );
        }

        if ( mode == TEXTONLY ) {
            // logService.debug( "mode = TEXTONLY" );
            if ( cal == null ) {
                buff.append( SPACE );
            } else {
                if ( format == 0 ) { // unset
                    if ( flag == DAY ) {
                        buff.append( getDateString( date, FORMAT_TO_DAY ) );
                    } else if ( flag == MONTH ) {
                        buff.append( getDateString( date, FORMAT_TO_MONTH ) );
                    } else if ( flag == YEAR ) {
                        buff.append( getDateString( date, FORMAT_TO_YEAR ) );
                    }
                } else {
                    buff.append( getDateString( date , format ));
                }
            }
            // logService.debug("TEXTONLY output is : " + buff.toString());
        } else {
            // logService.debug("mode is EDIT");
            // create the fields
            if ( MONTHLIST == null ) {
                MONTHLIST = new ArrayList<String>();
                MONTHLIST.add( "Select a Month" );
                MONTHLIST.addAll( java.util.Arrays.asList( MONTHS ) );
                // logService.debug("MONTHLIST  = " + MONTHLIST);
            }
            if ( MONTH_VALUE_LIST == null ) {
                MONTH_VALUE_LIST = new ArrayList<String>();
                for  ( int i = -1; i < 12; i++ ) {
                    MONTH_VALUE_LIST.add ( String.valueOf( i ));
                }
            }

            int month = -1;
            int year = -1;
            int day = -1;

            String dayField = null;
            String monthField = null;
            String yearField = null;
            // logService.debug("FLAG = " + flag );

            // set up the day field
            if ( flag == DAY ) {;
                if ( cal != null ) {
                    day = cal.get ( Calendar.DAY_OF_MONTH );
                }
                StringBuffer dayBuff = new StringBuffer();
                dayBuff.append("<input type=\"text\" ").append("name=\"").append(property).append("Day\" ");
                dayBuff.append("size=\"2\" maxlength=\"2\"");
                if ( title != null ) {
                    dayBuff.append(" title=\"").append(title).append("\" ");
                }
                if ( ( ids != null ) && ( ids.size() > 2 ) && (ids.get(2) != null )) {
                    dayBuff.append(" id=\"").append((String)ids.get(2)).append("\" ");
                }
                dayBuff.append(" value=\"");
                if ( day < 0 ) {
                    dayBuff.append( DAY_DEFAULT );
                } else {
                    dayBuff.append( day );
                }
                dayBuff.append ("\"/> ");

                dayField = dayBuff.toString();
                // logService.debug ("dayField = " + dayField ) ;
            }
            // set up the month field
            if ( flag >= MONTH ) {
                ArrayList<String> selected = new ArrayList<String>();

               if ( cal != null ) {
                   month = cal.get ( Calendar.MONTH );
                   // logService.debug ("month = " + month );
                   selected.add ( MONTHS[month]);
                   // logService.debug("Selected =>  " + selected);
               }

                String monthId = null;
                if ( ( ids != null ) && ( ids.size() > 1 ) && ( ids.get(1) != null )) {
                    monthId = (String) ids.get(1);
                }

                monthField = getSelectBox( property + "Month",
                        monthId, title, MONTH_VALUE_LIST, MONTHLIST,  1, false,
                        selected, style, events, mode );
               // logService.debug("monthField =  \n" + monthField );
            }



            StringBuffer yearBuff = new StringBuffer();
            if ( cal != null ) {
                year = cal.get( Calendar.YEAR );
            }
            yearBuff.append("<input type=\"text\" size=\"4\" maxlength=\"4\" ");
            yearBuff.append(" name=\"").append(property).append("Year\"");
            if ( title != null )
                yearBuff.append( " title=\"" ).append( title ).append( "\" " );
            if ( style != null )
                yearBuff.append( " style=\"" ).append( style ).append( "\" " );
            if ( ( ids != null ) && ( ids.size() > 0 ) && ( ids.get( 0 ) != null ) ) {
                yearBuff.append( " id=\"" ).append( ( String ) ids.get( 0 ) ).append( "\" " );
            }
            yearBuff.append( " value=\"" );
            if ( year < 0 )
                yearBuff.append( YEAR_DEFAULT );
            else
                yearBuff.append( year );
            yearBuff.append( "\"/>" );
            yearField = yearBuff.toString();

            // logService.debug("yearField = " + yearField );

            // put them all together!
            // will use DD/MM/YYYY format
            if ( dayField != null ) {
                buff.append ( dayField );
            }
            if ( monthField != null ) {
                buff.append( monthField );
            }
            buff.append( yearField );

        }

            try {
                pageContext.getOut().println( buff.toString() );
            } catch ( IOException e ) {
                //logService.error( e );
            }

            return EVAL_PAGE;
    }

    /**
     * getDateString()
     *
     * @param ts - the timestamp to parse
     * @param flag - a flag that states which format type to use
     *              (FORMAT_TO_DAY, for example)
     * @return the date as a string interpretation
     */
    private String getDateString( Date ts, int flag ) {
        if ( ts == null ) return "";

        SimpleDateFormat formatter = null;

        if ( flag == FORMAT_TO_MINUTE )
            formatter = new SimpleDateFormat( "d MMM yyyy 'at' h:mm a '('z')'" );
        else if ( flag == FORMAT_TO_DAY )
            formatter = new SimpleDateFormat( "d MMM yyyy" );
        else if ( flag == FORMAT_TO_MONTH )
            formatter = new SimpleDateFormat( "MMM yyyy" );
        else if ( flag == FORMAT_TO_YEAR )
            formatter = new SimpleDateFormat( "yyyy" );
        return formatter.format( ts );
    }

    /**
     * <P> Creates an HTML select box.
     * <P> Note: The size of the valuelist and labellist lists must be identical.
     If they are not, the valuelist will be used for the labels.
     * <P> Currently does not support the "TEXTONLY" mode
     * @param name       the name for the select box
     * @param id		the ID of the field
     * @param title	of the field
     * @param size       The number of rows to be displayed
     * @param valuelist	The list of values (in sorted order)
     * @param labellist	The list of labels (in the same corresponding order as the values)
     * @param multiple   Will this allow for multiple selections?
     * @param selected   The value(s) of the selected rows
     * @param style	the name of the style class to use on the entire form<br/>
     *                   May be <code>null</code>
     * @param events	any Javascript events you wish to include, such as onSubmit
     * @param mode	flag of EDIT, DISABLED, TEXTONLY to determine method
     *                   of display (not yet implemented)
     * @return the select box as a String
     */
    public static String getSelectBox( String name,
                                       String id,
                                       String title,
                                       List<String> valuelist,
                                       List<String> labellist,
                                       int size,
                                       boolean multiple,
                                       List<String> selected,
                                       String style,
                                       String events,
                                       int mode ) {
        StringBuffer buff = new StringBuffer();

        if ( selected == null ) selected = new ArrayList<String>();
        if ( ( labellist == null ) || ( valuelist.size() != labellist.size() ) ) {
            labellist = valuelist;
        }

        // displays on the selected field, if one has been selected
        if ( mode == TEXTONLY ) {
            boolean afterFirst = false;
            // as we create a selected if one does not exist, this will be ok
            for ( int i = 0; i < selected.size(); i++ ) {
                if ( valuelist.contains( selected.get( i ) ) ) {
                    if ( afterFirst ) {
                        buff.append( ", " );
                    } else {
                        afterFirst = true;
                    }
                    buff.append( ( String ) labellist.get( valuelist.indexOf( selected.get( i ) ) ) );
                }
            }
        } else {
            buff.append( "<select name = \"" ).append( name ).append( "\" " );
            buff.append( "size = \"" ).append( size ).append( "\" " );
            if ( multiple ) {
                buff.append( "multiple = \"multiple\" " );
            }
            if ( mode == DISABLED ) {
                buff.append( " disabled " );
            }
            if ( style != null ) {
                buff.append( " class=\"" ).append( style ).append( "\" " );
            }
            if ( events != null ) {
                buff.append( " " ).append( events ).append( " " );
            }
            if ( id != null ) {
                buff.append( " id=\"" ).append( id ).append( "\" " );
            }
            if ( title != null ) {
                buff.append( " title=\"" ).append( title ).append( "\" " );
            }
            buff.append( ">\n" );

            // Now, create the various <code>option</code> fields


            Iterator<String> iterV = valuelist.iterator();
            Iterator<String> iterL = labellist.iterator();

            while ( iterV.hasNext() ) {
                // make one option line per value
                String lab = ( String ) iterL.next();
                String val = ( String ) iterV.next();

                buff.append( "<option value=\"" ).append( val ).append( "\"" );
                if ( selected.contains( lab ) ) {
                    buff.append( " selected" );
                }
                buff.append( ">" ).append( lab ).append( "</option>\n" );
            }

            buff.append( "</select>\n" );
        }
        return buff.toString();
    }

    public void release() {
        this.name = null;
        this.property = null;
        this.ids = null;
        this.title = null;
        this.date = null;
        this.flag = 0;
        this.style = null;
        this.events = null;
        this.mode = EDIT;
        super.release();
    }

   protected String getMethodName(String property){
      if (property == null || property.length() == 0){
         return "";
      }
      return "get" + property.substring(0,1).toUpperCase() + property.substring(1);
   }
}
