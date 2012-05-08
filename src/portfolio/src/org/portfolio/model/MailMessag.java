/* $Name:  $ */
/* $Id: MailMessag.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $ */
/*
 * $Header: /opt/UMN-src/portfolio/src/org/portfolio/model/MailMessag.java,v 1.13 2010/10/27 19:24:57 ajokela Exp $
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


/**
 * This class encapsulates MailMessag element object.
 *
 * @author	John Bush
 * @since	0.0
 * @version	$Revision: 1.13 $
 */
public class MailMessag extends ElementBase {

    private static final long serialVersionUID = 4345730927239695450L;
	
    public java.lang.String getMessageName() {
        return (messageName != null ? messageName : EMPTY_STRING);
    }

    public void setMessageName(java.lang.String messageName) {
        this.messageName = messageName;
    }

    public java.lang.String getMessageText() {
        return (messageText != null ? messageText : EMPTY_STRING);
    }

    public void setMessageText(java.lang.String messageText) {
        this.messageText = messageText;
    }

    //--------------------------------------------------------------------------
    // Methods dictated by implemented interfaces

    public void setIsRemote(boolean isRemote) {
        this.isRemote = isRemote;
    }

    public boolean isRemote() {
        return isRemote;
    }

    public String toString() {
        StringBuffer buff = new StringBuffer();
        buff.append(super.toString()).append("\n");

        // for this instance.
        buff.append("messageName=").append(getMessageName()).append(",");
        buff.append("messageText=").append(getMessageText()).append(",");
        buff.append("is new? =>" + isNew());
        return buff.toString();
    }

    protected java.lang.String messageName = null;
    protected java.lang.String messageText = null;
    protected boolean isRemote = false;
}
