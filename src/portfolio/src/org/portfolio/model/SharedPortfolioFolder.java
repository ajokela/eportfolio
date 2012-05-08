/* $Name:  $ */
/* $Id: SharedPortfolioFolder.java,v 1.7 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Model object representing Shared Portfolio Folder
 * 
 * @author Vijay Rajagopal
 */
public class SharedPortfolioFolder implements Comparable<SharedPortfolioFolder> {

    private BigDecimal folderId;
    private String personId;
    private String folderName;
    private Date createdDate;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getFolderId() {
        return folderId;
    }

    public void setFolderId(BigDecimal folderId) {
        this.folderId = folderId;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int compareTo(SharedPortfolioFolder o) {
        return getFolderName().compareToIgnoreCase(o.getFolderName());
    }
}
