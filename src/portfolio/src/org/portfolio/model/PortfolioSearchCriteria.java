/* $Name:  $ */
/* $Id: PortfolioSearchCriteria.java,v 1.15 2011/01/12 18:03:58 ajokela Exp $ */
package org.portfolio.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PortfolioSearchCriteria {

	public static final int PORTFOLIOSEARCHCRITERIA_OBJECTS = 0x00;
	public static final int PORTFOLIOSEARCHCRITERIA_SIZE    = 0x01;
	public static final int PORTFOLIOSEARCHCRITERIA_BARE    = 0x02;
	
	public static final int PORTFOLIOSEARCHCRITERIA_ORDER_TITLE  = 0x10;
	public static final int PORTFOLIOSEARCHCRITERIA_ORDER_AUTHOR = 0x20;
	public static final int PORTFOLIOSEARCHCRITERIA_ORDER_DATE   = 0x30;
	
    private String shareName;
    private String templateId;
    private List<Template> templates = null;
    private Integer communityId;
    private List<String> tags;

    private String query;
    private String personId;
    private BigDecimal folderId;
    private Boolean filed; // null means don't care
    private Boolean flagged; // null means don't care
    private Boolean read; // null means don't care
    private boolean myPortfoliosOnly = false;
    private boolean sharedPortfoliosOnly = false;
    private boolean isPublic = false;
    private int     return_type = PORTFOLIOSEARCHCRITERIA_OBJECTS;
    private int     sort_by     = PORTFOLIOSEARCHCRITERIA_ORDER_DATE;
    private int     start       = -1;
    private int     end         = -1;

    public PortfolioSearchCriteria() {
    	
    }

    public PortfolioSearchCriteria(String personId) {
        this.personId = personId;
    }
    
    public PortfolioSearchCriteria setTemplates(List<Template> t) {
    	
    	this.templates = new ArrayList<Template>();
    	
    	this.templates.addAll(t);
    	
    	return this;
    }
    
    public void setReturnType(int rt) {
    	return_type = rt;
    }
    
    public int getReturnType() {
    	return return_type;
    }
    
    public List<Template> getTemplates() {
    	return this.templates;
    }

    public String getPersonId() {
        return personId;
    }

    public PortfolioSearchCriteria setPersonId(String personId) {
        this.personId = personId;
        return this;
    }
    
    public PortfolioSearchCriteria setIsPublic(boolean pub) {
    	this.isPublic = pub;
    	return this;
    }
    
    public boolean isPublic() {
    	return this.isPublic;
    }

    public String getShareName() {
        return shareName;
    }

    public PortfolioSearchCriteria setShareName(String shareName) {
        this.shareName = shareName;
        return this;
    }

    public String getTemplateId() {
        return templateId;
    }

    public PortfolioSearchCriteria setTemplateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public PortfolioSearchCriteria setCommunityId(Integer communityId) {
        this.communityId = communityId;
        return this;
    }

    public BigDecimal getFolderId() {
        return this.folderId;
    }

    public PortfolioSearchCriteria setFolderId(BigDecimal folderId) {
        this.folderId = folderId;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public PortfolioSearchCriteria setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public PortfolioSearchCriteria addTag(String value) {
        if (this.tags == null) {
            this.tags = new ArrayList<String>();
        }
        tags.add(value);
        return this;
    }

    public Boolean getFiled() {
        return filed;
    }

    public PortfolioSearchCriteria setFiled(Boolean filed) {
        this.filed = filed;
        return this;
    }

    public PortfolioSearchCriteria setFlagged(Boolean flagged) {
        this.flagged = flagged;
        return this;
    }

    public Boolean getFlagged() {
        return this.flagged;

    }

    public PortfolioSearchCriteria setRead(Boolean read) {
        this.read = read;
        return this;
    }

    public Boolean getRead() {
        return this.read;
    }

    public boolean isMyPortfoliosOnly() {
        return myPortfoliosOnly;
    }

    public PortfolioSearchCriteria setMyPortfoliosOnly(boolean myPortfoliosOnly) {
        this.myPortfoliosOnly = myPortfoliosOnly;
        return this;
    }

    public boolean isSharedPortfoliosOnly() {
        return sharedPortfoliosOnly;
    }

    public PortfolioSearchCriteria setSharedPortfoliosOnly(boolean sharedPortfoliosOnly) {
        this.sharedPortfoliosOnly = sharedPortfoliosOnly;
        return this;
    }

    public String getQuery() {
        return query;
    }

    public PortfolioSearchCriteria setQuery(String query) {
        this.query = query;
        return this;
    }

	public int getSortBy() {
		return sort_by;
	}

	public void setSortBy(int sort_by) {
		this.sort_by = sort_by;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

}
