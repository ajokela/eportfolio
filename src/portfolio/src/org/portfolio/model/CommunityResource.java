package org.portfolio.model;

import java.util.Date;
import java.util.List;

public class CommunityResource {
	
	private int id;
	private int community_id;
	private int entry_id;
	private int place;
	private Date created_at;
	private Date updated_at;
	private String resource_type;
	private boolean is_deleted;
	private String person_id;
	private String name;
	private EntryKey entryKey;
	private String encodedId;
	private Person owner;
	private String description;
	private long size;
	private String humanReadableSize;
	private String elementName;
	private List<String> tags;
	
	private static final int CR_SHORTNAME_LENGTH = 24;
	
	public void setId(int id) {
		this.id = id;
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setCommunityId(int community_id) {
		this.community_id = community_id;
	}
	
	public int getCommunityId() {
		return community_id;
	}
	
	public void setEntryId(int entry_id) {
		this.entry_id = entry_id;		
	}
	
	public int getEntryId() {
		return entry_id;
	}
	
	public void setPlace(int place) {
		this.place = place;
	}
	
	public int getPlace() {
		return place;
	}
	
	public void setCreatedAt(Date created_at) {
		this.created_at = created_at;
	}
	
	public Date getCreatedAt() {
		return created_at;
	}
	
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	public Date getUpdatedAt() {
		return updated_at;
	}
	
	public void setResourceType(String resource_type) {
		this.resource_type = resource_type;
	}
	
	public String getResourceType() {
		return resource_type;
	}
	
	public void setIsDeleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}

	public boolean isDeleted() {
		return is_deleted;
	}

	public void setPersonId(String person_id) {
		this.person_id = person_id;
	}

	public String getPersonId() {
		return person_id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setEntryKey(EntryKey entryKey) {
		
		this.encodedId = entryKey.getId();
		
		this.entryKey = entryKey;
	}

	public EntryKey getEntryKey() {
		return entryKey;
	}

	public void setEncodedId(String encodedId) {
		this.encodedId = encodedId;
	}

	public String getEncodedId() {
		return encodedId;
	}

	public void setOwner(Person owner) {
		this.owner = owner;
	}

	public Person getOwner() {
		return owner;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
	public String getShortName() {

		if(name != null) {
			
			if(name.length() > CR_SHORTNAME_LENGTH) {
				
				StringBuffer buff = new StringBuffer(name);
				
				int lastIndex = buff.lastIndexOf(".");
				String extension = "";
				
				if( lastIndex > 0) {
					extension = name.substring(lastIndex + 1, name.length());
				}
			
				return name.substring(0, CR_SHORTNAME_LENGTH - 1) + "..." + extension;
			}
			
			return name;
			
		}
		
		return "";
				
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getSize() {
		return size;
	}

	public void setHumanReadableSize(String humanReadableSize) {
		this.humanReadableSize = humanReadableSize;
	}

	public String getHumanReadableSize() {
		return humanReadableSize;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getElementName() {
		return elementName;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getTags() {
		return tags;
	}
}