/* $Name:  $ */
/* $Id: UploadedMaterialFolder.java,v 1.10 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;

public class UploadedMaterialFolder {

    private BigDecimal entryId;
    private String personId;
    private String name;
    private BigDecimal parentId;
    private boolean collapsed = true;
    private boolean modifiable = true;

    public String getName() {
        return (name != null ? name : "");
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.math.BigDecimal getParentId() {
        return (parentId != null ? parentId : null);
    }

    public void setParentId(java.math.BigDecimal parentId) {
        this.parentId = parentId;
    }

    public boolean getCollapsed() {
        return collapsed;
    }

    public void setCollapsed(boolean collapsed) {
        this.collapsed = collapsed;
    }

    public boolean equals(Object obj) {
        UploadedMaterialFolder in = (UploadedMaterialFolder) obj;
        if (this.getName() == null && in.getName() != null)
            return false;
        else if (this.getName() != null && in.getName() == null)
            return false;
        else if (this.getName() != null && in.getName() != null && !this.getName().equals(in.getName()))
            return false;
        return super.equals(obj);
    }

    public boolean isModifiable() {
        return this.modifiable;
    }

    public void setModifiable(boolean modifiable) {
        this.modifiable = modifiable;
    }

    public BigDecimal getEntryId() {
        return entryId;
    }

    public void setEntryId(BigDecimal entryId) {
        this.entryId = entryId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

}
