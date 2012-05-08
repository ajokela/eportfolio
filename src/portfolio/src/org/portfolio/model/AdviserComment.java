/* $Name:  $ */
/* $Id: AdviserComment.java,v 1.4 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model;

import java.util.Date;

public class AdviserComment {

    private int id;
    private Date dateCreated;
    private Date dateUpdated;
    private String text;
    private Person adviser;
    private Person advisee;
    

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Person getAdviser() {
        return adviser;
    }

    public void setAdviser(Person adviser) {
        this.adviser = adviser;
    }

    public Person getAdvisee() {
        return advisee;
    }

    public void setAdvisee(Person advisee) {
        this.advisee = advisee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
