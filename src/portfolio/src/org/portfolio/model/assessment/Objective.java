/* $Name:  $ */
/* $Id: Objective.java,v 1.12 2010/10/27 19:24:57 ajokela Exp $ */
package org.portfolio.model.assessment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * This class represents an objective. The purpose of this class is to
 * encapsulate objectives used for assessment score entry.
 */
public class Objective {

    public static final Comparator<Objective> NAME_ORDER = new Comparator<Objective>() {
        public int compare(Objective o1, Objective o2) {
            return o1.getName().compareToIgnoreCase(o2.getName());
        }
    };

    protected Integer id;
    protected Integer parentId;
    protected Integer communityId;
    protected String name;
    protected String description;
    protected Integer order;
    protected List<Objective> subObjectives;
    protected Date dateCreated;
    protected Date dateModified;

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the parent's objectiveId or null if no parent.
     */
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public List<Objective> getFlatSubObjectivesList() {
        List<Objective> flatList = new ArrayList<Objective>();
        for (Objective subobj : getSubObjectives()) {
            flatList.add(subobj);
            flatList.addAll(subobj.getFlatSubObjectivesList());
        }
        return flatList;
    }

    /**
     * @return the list of subobjectives or an empty list if none set.
     */
    public List<Objective> getSubObjectives() {
        return subObjectives == null ? new ArrayList<Objective>() : subObjectives;
    }

    public void setSubObjectives(List<Objective> subObjectives) {
        this.subObjectives = subObjectives;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Objective && ((Objective) obj).id.equals(this.id);
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
