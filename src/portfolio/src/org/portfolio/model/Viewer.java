/* $Name:  $ */
/* $Id: Viewer.java,v 1.22 2011/03/22 16:04:05 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import org.portfolio.dao.PortfolioHome;



public class Viewer implements Comparable<Viewer> {

	private PortfolioHome portfolioHome;
	
    public enum ViewType {
        VIEWER, OWNER;
    }

    private String shareId;
    private Date emailSentDate;
    private Date lastViewed;
    private Date dateShared;
    private boolean read;
    private boolean flagged;
    private Person person;
    private ViewType viewType = ViewType.VIEWER; // default
    private BigDecimal folderId;
    private Person owner;
    private boolean is_deleted = false;

    public Viewer(PortfolioHome portfolioHome) {
    	super();
    	this.portfolioHome = portfolioHome;
    }
    
    public void setIsDeleted(boolean is_deleted) {
    	this.is_deleted = is_deleted;
    }
    
    public boolean isDeleted() {
    	return is_deleted;
    }
    
    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isFlagged() {
        return flagged;
    }

    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public String getPersonId() {
        return person == null ? null : person.getPersonId();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getShareId() {
        return (shareId != null ? shareId : "");
    }

    public void setShareId(String shareId) {
    	
    	if(shareId != null) {
    		Portfolio portfolio = portfolioHome.findByShareId(shareId);
    		
    		if(portfolio != null) {
    			owner = portfolio.getPerson();
    		}
    		
    	}
    	
        this.shareId = shareId;
    }
    
    public Person getOwner() {
    	return owner;
    }

    public Date getEmailSentDate() {
        return emailSentDate;
    }

    public void setEmailSentDate(Date emailSentDate) {
        this.emailSentDate = emailSentDate;
    }

    public Date getLastViewed() {
        return lastViewed;
    }

    public void setLastViewed(Date lastViewed) {
        this.lastViewed = lastViewed;
    }

    private Object[] getEqualityIdentifiers() {
        return new Object[] { shareId, getPersonId() };
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Viewer && Arrays.equals(getEqualityIdentifiers(), ((Viewer) obj).getEqualityIdentifiers());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getEqualityIdentifiers());
    }

    public Date getDateShared() {
        return dateShared;
    }

    public void setDateShared(Date dateShared) {
        this.dateShared = dateShared;
    }

    public ViewType getViewType() {
        return viewType;
    }

    public void setViewType(ViewType viewType) {
        this.viewType = viewType;
    }

    public BigDecimal getFolderId() {
        return folderId;
    }

    public void setFolderId(BigDecimal folderId) {
        this.folderId = folderId;
    }

    public int compareTo(Viewer o) {
        return getPerson().getDisplayName().compareToIgnoreCase(o.getPerson().getDisplayName());
    }
}
