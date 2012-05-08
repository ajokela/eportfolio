/* $Name:  $ */
/* $Id: PortfolioEmailSender.java,v 1.5 2011/03/25 13:50:12 ajokela Exp $ */
package org.portfolio.bus;

import org.portfolio.model.Viewer;

import java.util.List;

public interface PortfolioEmailSender {

    void sendEmails(String shareId, boolean ccSelf, String msg);

    void sendEmails(String shareId, List<Viewer> viewers, boolean ccSelf, String msg, boolean sendEmail);

}
