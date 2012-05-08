/* $Name:  $ */
/* $Id: PortfolioMailer.java,v 1.14 2011/03/17 19:15:30 ajokela Exp $ */
package org.portfolio.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.util.StringUtils;

/**
 * This class is used to send emails from within the Portfolio application
 */
public class PortfolioMailer implements Mailer {

    private String smtpHost;

    public void sendMail(String msg, String subject, String to, String from) throws MessagingException {
        sendMailSMTP(new String[] { to }, subject, msg, from, null);
    }
    
    public void sendMail(String msg, String subject, String to, String from, String[] cc) throws MessagingException {
    	sendMailSMTP(new String[] { to }, subject, msg, from, cc);
    }
    
    public void sendMail(String msg, String subject, String[] to, String from, String[] cc) throws MessagingException {
    	sendMailSMTP(to, subject, msg, from, cc);
    }

    private synchronized void sendMailSMTP(String recipients[], String subject, String message, String from, String[] cc) throws MessagingException {
        Session session = createSession();

        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        
        msg.setContent(message, "text/plain");

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        
        msg.setRecipients(Message.RecipientType.TO, addressTo);

        if(cc != null) {
        	InternetAddress[] addressCC = new InternetAddress[cc.length];
        	
        	for(int i=0; i<cc.length; ++i) {
        		addressCC[i] = new InternetAddress(cc[i]);
        	}
        	
        	msg.setRecipients(Message.RecipientType.CC, addressCC);
        }
        
        Transport.send(msg);
    }

    private Session createSession() {
        Properties props = new Properties(System.getProperties());
        if (StringUtils.hasText(smtpHost)) {
            props.put("mail.smtp.host", smtpHost);
        }

        // avoid hang by setting some timeout.
        props.put("mail.smtp.timeout", "60000");
        props.put("mail.smtp.connectiontimeout", "60000");

        return Session.getInstance(props, null);
    }

    @RequiredInjection
    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }
}
