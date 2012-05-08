/* $Name:  $ */
/* $Id: Photo.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;

public class Photo extends ElementBase {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal sizeKb;

    public BigDecimal getSizeKb() {
        return sizeKb;
    }

    public void setSizeKb(BigDecimal sizeKb) {
        this.sizeKb = sizeKb;
    }
}
