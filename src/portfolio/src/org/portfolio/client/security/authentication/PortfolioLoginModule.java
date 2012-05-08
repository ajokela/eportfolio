/* $Name:  $ */
/* $Id: PortfolioLoginModule.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.security.authentication;

import javax.security.auth.spi.LoginModule;

public interface PortfolioLoginModule extends LoginModule {
    
    PortfolioPrincipal getPrincipal(String name);
}
