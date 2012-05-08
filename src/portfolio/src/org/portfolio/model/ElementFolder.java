/* $Name:  $ */
/* $Id: ElementFolder.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;

public class ElementFolder implements Comparable<ElementFolder> {

    private BigDecimal id;
    private String personId;
    private String name;

    public BigDecimal getId() {
        return id;
    }

    public void setId(BigDecimal id) {
        this.id = id;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String folderName) {
        this.name = folderName;
    }

    public int compareTo(ElementFolder o) {
        return this.getName().compareToIgnoreCase(o.getName());
    }
}
