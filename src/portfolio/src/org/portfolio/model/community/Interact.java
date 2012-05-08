package org.portfolio.model.community;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Interact {
	private int id;
	private int communityId;
	private String type;
	private TYPE typ;
	private String val;
	private int place;
	private Date createdAt;
	private Date updatedAt;
	private boolean is_google_calendar = false;
	private boolean isDeleted;
	
	public Interact() {
		Date d = new Date();
		
		createdAt = d;
		updatedAt = d;
	}
	
	public enum TYPE {
		FACEBOOK("FACEBOOK", "Facebook Group"),
		LINKEDIN("LINKEDIN", "LinkedIn Group"),
		TWITTER("TWITTER", "Twitter Feed"),
		MOODLE("MOODLE", "Moodle Page"),
		CALENDAR("CALENDAR", "Community Calendar");
		
		private String label;
		private String longForm;
        
        private TYPE(String label, String longForm) {
            this.label = label;
            this.longForm = longForm;
        }
        
        @Override
        public String toString() {
            return label;
        }

        public String toLongForm() {
        	return longForm;
        }
    };
	
	public static List<String>getTypesPretty() {
		TYPE[] types = TYPE.values();
		List<String> strings = new ArrayList<String>();
		
		for(int i=0; i<types.length; ++i) {
			strings.add(_getTypePretty(types[i]));
		}
		
		Collections.sort(strings);
		
		return strings;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setCommunityId(String communityId) {
		setCommunityId(Integer.valueOf(communityId));
	}
	
	public void setCommunityId(int communityId) {
		this.communityId = communityId;
	}
	
	public int getCommunityId() {
		return communityId;
	}
	
	public void setType(TYPE type) throws Exception {
		setType(type.toString());
		this.typ = type;
	}
	
	public void setType(String type) throws Exception {
		type = type.toUpperCase();
		this.type = type;
		if(type.contentEquals(TYPE.FACEBOOK.toString())) {
			this.typ = TYPE.FACEBOOK;
		}
		else if(type.contentEquals(TYPE.TWITTER.toString())) {
			this.typ = TYPE.TWITTER;
		}
		else if(type.contentEquals(TYPE.LINKEDIN.toString())) {
			this.typ = TYPE.LINKEDIN;
		}
		else if(type.contentEquals(TYPE.MOODLE.toString())) {
			this.typ = TYPE.MOODLE;
		}
		else if(type.contentEquals(TYPE.CALENDAR.toString())) {
			this.typ = TYPE.CALENDAR;
		}
		else {
			throw new Exception("Unknown Type: " + type);
		}
	}
	
	public String getType_string() {
		return type;
	}
	
	public String getTypePretty() {
		return _getTypePretty(getType());
	}
	
	public String getLongType() {
		return typ.toLongForm();
	}
	
	private static String _getTypePretty(TYPE type) {
		String firstLetter = type.toString().substring(0,1).toUpperCase();
		String remainder   = type.toString().substring(1).toLowerCase();
		
		return firstLetter + remainder;
	}
	
	public TYPE getType() {
		return typ;
	}
	
	public void setVal(String val) {
		this.val = val;
	}
	
	public String getVal() {
		return val;
	}

	public void setPlace(int place) {
		this.place = place;
	}

	public int getPlace() {
		return place;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setIsGoogleCalendar(boolean is_google_calendar) {
		this.is_google_calendar = is_google_calendar;
	}

	public boolean isGoogleCalendar() {
		
		if(getVal() != null) {
			if(getVal().toLowerCase().contains("google")) {
				is_google_calendar = true;
			}
		}
		
		return is_google_calendar;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public boolean isDeleted() {
		return isDeleted;
	}
	
	
}