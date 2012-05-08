/* $Name:  $ */
/* $Id: TipDataHome.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.dao;

import org.portfolio.model.TipData;

/**
 * @author Matt Sheehan
 *
 */
public interface TipDataHome {

	/**
	 * <p>
	 * Returns a random tip for the specified JSP page name or <code>null</code>
	 * if no tips are present for that page. Tips are cached in lists by page
	 * name. The algorithm for radomness is <pre>
	       int index = (int) (Math.random() * tipList.size())
	 * </pre> where tipList is the list of tips for the specified JSP page.</p>
	 * 
	 * @param pageName the JSP page name for which to return tip data
	 * @return a (random) tip for the specified JSP page name
	 */
	TipData findRandomByPageName(String pageName);
}
