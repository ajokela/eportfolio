/* $Name:  $ */
/* $Id: PortfolioPrincipal.java,v 1.5 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import java.security.Principal;

public interface PortfolioPrincipal extends Principal, java.io.Serializable {
    public void setName(String newName);

    public void setAttribute(String key, Object value);

    public Object getAttribute(String key);
}
