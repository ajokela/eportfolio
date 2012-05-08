/* $Name:  $ */
/* $Id: Link.java,v 1.6 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model;

public class Link extends ElementBase {

    /**
	 * 
	 */
	private static final long serialVersionUID = 7365742251324280410L;
	private String url;
    private String author;
    private String description;
    private int place;

    public Link() {
    	super();
    	setElementType(ElementBase.ELEMENT_TYPE_LINK);
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }
    
}
