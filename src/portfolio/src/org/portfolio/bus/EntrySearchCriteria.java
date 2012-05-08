/* $Name:  $ */
/* $Id: EntrySearchCriteria.java,v 1.13 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.bus;

import java.math.BigDecimal;

/**
 * @author Matt Sheehan
 */
public class EntrySearchCriteria {

	public final static int ENTRYSEARCHCRITERIA_OBJECTS = 0x00;
	public final static int ENTRYSEARCHCRITERIA_SIZE    = 0x01;
	
    private String query;
    private String entryName;
    private Integer communityId;
    private Integer wizardId;
    private Integer wizardElementId;
    private String[] elementIds;
    private String tag;
    private String[] systemTags;
    private final String personId;
    private String elementSourceId;
    private BigDecimal folderId;
    private Boolean filed; // null means don't care
    private String category;
    private int startRange = -1;
    private int endRange = -1;
    private int return_type = ENTRYSEARCHCRITERIA_OBJECTS;

    public EntrySearchCriteria(String personId) {
        this.personId = personId;
    }

    public final String getEntryName() {
        return entryName;
    }
    
    public final EntrySearchCriteria setEntryName(String entryName) {
        this.entryName = entryName;
        return this;
    }
    
    public final void setReturnType(int rt) {
    	return_type = rt;
    }
    
    public final int getReturnType() {
    	return return_type;
    }

    public final Integer getCommunityId() {
        return communityId;
    }

    public final EntrySearchCriteria setCommunityId(Integer communityId) {
        this.communityId = communityId;
        return this;
    }

    public final Integer getWizardId() {
        return wizardId;
    }

    public final EntrySearchCriteria setWizardId(Integer wizardId) {
        this.wizardId = wizardId;
        return this;
    }

    public final String[] getSystemTagIds() {
        return systemTags;
    }

    public final EntrySearchCriteria setSystemTags(String[] systemTags) {
        this.systemTags = systemTags;
        return this;
    }

    public final Integer getWizardElementId() {
        return wizardElementId;
    }

    public final EntrySearchCriteria setWizardElementId(Integer wizardElementId) {
        this.wizardElementId = wizardElementId;
        return this;
    }

    public final String[] getElementIds() {
        return elementIds;
    }

    public final EntrySearchCriteria setElementIds(String... elementIds) {
        this.elementIds = elementIds;
        return this;
    }

    public String getPersonId() {
        return personId;
    }

    public String getElementSourceId() {
        return elementSourceId;
    }

    public EntrySearchCriteria setElementSourceId(String elementSourceId) {
        this.elementSourceId = elementSourceId;
        return this;
    }

    public BigDecimal getFolderId() {
        return folderId;
    }

    public EntrySearchCriteria setFolderId(BigDecimal folderId) {
        this.folderId = folderId;
        return this;
    }

    public Boolean getFiled() {
        return filed;
    }

    public EntrySearchCriteria setFiled(Boolean filed) {
        this.filed = filed;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public EntrySearchCriteria setQuery(String query) {
        this.query = query;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public EntrySearchCriteria setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getCategory() {
        return category;
    }

    public EntrySearchCriteria setCategory(String category) {
        this.category = category;
        return this;
    }

	public void setStartRange(int startRange) {
		this.startRange = startRange;
	}

	public int getStartRange() {
		return startRange;
	}

	public void setEndRange(int endRange) {
		this.endRange = endRange;
	}

	public int getEndRange() {
		return endRange;
	}
}
