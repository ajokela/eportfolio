/* $Name:  $ */
/* $Id: Mailer.java,v 1.5 2011/03/17 19:15:30 ajokela Exp $ */
package org.portfolio.util;

import javax.mail.MessagingException;

/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/util/Mailer.java,v 1.5 2011/03/17 19:15:30 ajokela Exp $
 * $Revision: 1.5 $
 * $Date: 2011/03/17 19:15:30 $
 */

public interface Mailer {
   public void sendMail(String msg, String subject, String to, String from)
         throws MessagingException;
   
   public void sendMail(String msg, String subject, String to, String from, String[] cc)
   throws MessagingException;
   
   public void sendMail(String msg, String subject, String[] to, String from, String []cc)
   throws MessagingException;
}
