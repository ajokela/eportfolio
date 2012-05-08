/* $Name:  $ */
/* $Id: XssFilter.java,v 1.3 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.util;

/**
 * @author Matt Sheehan
 * 
 */
public class XssFilter {

    public String filter(String html) {
        // check all the attacks listed at http://ha.ckers.org/xss.html
        return html
        .replaceAll("(?i)<script.*?>.*?</script.*?>", "")
        .replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "")
        .replaceAll("(?i)<[^>]*?\\s+on.*?>.*?</.*?>","");
    }

}
