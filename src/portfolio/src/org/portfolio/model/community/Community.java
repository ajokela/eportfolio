/* $Name:  $ */
/* $Id: Community.java,v 1.21 2011/01/27 17:37:01 ajokela Exp $ */
package org.portfolio.model.community;

import java.util.Comparator;
import java.util.Date;

import org.portfolio.model.Person;

/**
 * 
 * @author Matt Sheehan
 * 
 */
public class Community implements Comparable<Community> {
    
    public enum CommunityType {
        ASSESSMENT("Assessment"), EPORTFOLIO("ePortfolio"), GENERAL("General");
        
        private final String label;

        private CommunityType(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }
    
	private Integer id;
	private String name;
	private String description;
	private String campusCode;
	private String contactEmailAddress;
	private Date dateCreated;
    private boolean privateCommunity;
    private boolean deleted;
    private CommunityType type = CommunityType.GENERAL; // default
    private String program;
    private Person person;
    
    public static final Comparator<Community> COMMUNITY_NAME = new Comparator<Community>() {
        public int compare(Community o1, Community o2) {
            
        	int result = o1.name.compareToIgnoreCase(o2.name);
            if (result == 0) {
                result = o1.campusCode.compareToIgnoreCase(o2.campusCode);
            }

            return result;
        }
    };
    
    public Integer getId() {
        return this.id;
    }

	public String getName() {
		return this.name;
	}

	public String getDescription() {
		return this.description;
	}

	public String getCampusCode() {
		return this.campusCode;
	}

	public String getCampusName() {
		if ("NONE".equals(campusCode)) {
			return "ePortfolio";
		}
		return "";
	}

	public String getContactEmailAddress() {
		return this.contactEmailAddress;
	}

    public void setId(Integer id) {
        this.id = id;
    }

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCampusCode(String campusCode) {
		this.campusCode = campusCode;
	}

	public void setContactEmailAddress(String contactEmailAddress) {
		this.contactEmailAddress = contactEmailAddress;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

    public boolean isPrivateCommunity() {
        return privateCommunity;
    }

    public void setPrivateCommunity(boolean privateCommunity) {
        this.privateCommunity = privateCommunity;
    }

	@Override
	public String toString() {
		return this.getName();
	}

	public int compareTo(Community object) {
		
		int result = getName().compareToIgnoreCase(object.getName()); // getCampusCode().compareTo(object.getCampusCode());
		
		if (result == 0) {
			result = getCampusCode().compareToIgnoreCase(object.getCampusCode());
		}
		return result;
	}

    public CommunityType getType() {
        return type;
    }

    public void setType(CommunityType communityType) {
        this.type = communityType;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Community && id.equals(((Community) obj).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
