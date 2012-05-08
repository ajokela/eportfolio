/* $Name:  $ */
/* $Id: AbstractElementHome.java,v 1.6 2010/11/09 14:53:05 ajokela Exp $ */
package org.portfolio.dao;

import org.portfolio.model.ElementDataObject;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;

public abstract class AbstractElementHome extends AbstractDataHome implements ElementHome {

	public LogService logService = new LogService(this.getClass());	
	
	public AbstractElementHome() {
		super();
		
		String sysdate_parentheses = Configuration.get("portfolio.jdbc.sysdate_uses_parentheses");	
    	
		if(sysdate_parentheses == null) {
			sysdate_parentheses = System.getProperty("portfolio.jdbc.sysdate_uses_parentheses", "false");
		}
		
		if(sysdate_parentheses != null && sysdate_parentheses.contentEquals("true")) {
    		sysdate += "()";
    	}

	}
	
    public void store(ElementDataObject data) {
    	try {
    		throw new UnsupportedOperationException();
    	}
    	catch (Exception e) {
    		logService.debug(data.getClass().toString());
    		logService.debug(e);
    	}
    }

    public void remove(ElementDataObject data) {
    	try {
    		throw new UnsupportedOperationException();
    	}
    	catch (Exception e) {
    		logService.debug(data.getClass().toString());
    		logService.debug(e);
    	}
    }
}
