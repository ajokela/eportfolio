/* $Name:  $ */
/* $Id: PortfolioTag.java,v 1.5 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;

public class PortfolioTag {

    private final String personId; // Owner of the portfolio Tag
    private final BigDecimal shareId;
    private final String tag;
    private final Date createdDate;

    public PortfolioTag(String personId, BigDecimal shareId, String tag, Date createdDate) {
        this.personId = personId;
        this.shareId = shareId;
        this.tag = tag;
        this.createdDate = createdDate;
    }

    public PortfolioTag(String personId, BigDecimal shareId, String tag) {
        this(personId, shareId, tag, new Date(System.currentTimeMillis()));
    }

    public String getPersonId() {
        return personId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public BigDecimal getShareId() {
        return shareId;
    }

    public String getTag() {
        return tag;
    }

    private Object[] getEqualityIdentifiers() {
        return new Object[] { personId, shareId, tag };
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PortfolioTag && Arrays.equals(((PortfolioTag) obj).getEqualityIdentifiers(), this.getEqualityIdentifiers());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getEqualityIdentifiers());
    }

}
