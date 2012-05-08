/* $Name:  $ */
/* $Id: Person.java,v 1.47 2011/03/17 19:15:30 ajokela Exp $ */
package org.portfolio.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.model.community.Community;
import org.portfolio.util.Configuration;
import org.portfolio.util.LogService;
import org.portfolio.util.PortfolioConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.util.StringUtils;
import org.apache.commons.codec.binary.Base64;

@Configurable
public class Person extends ValidatorForm {

    private static final long serialVersionUID = 607931695481050316L;
    private static final String EMPTY_STRING = "";

    private LogService logService = new LogService(this.getClass());
    
    public static final Comparator<Person> FULL_NAME = new Comparator<Person>() {
        public int compare(Person o1, Person o2) {
            int result = o1.getLastname().compareToIgnoreCase(o2.getLastname());
            if (result == 0) {
                result = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
            }
            if (result == 0) {
                result = o1.getMiddlename().compareToIgnoreCase(o2.getMiddlename());
            }
            if (result == 0) {
                result = o1.getUsername().compareToIgnoreCase(o2.getUsername());
            }
            return result;
            
        }
    };

    public static final Comparator<Person> USER_NAME = new Comparator<Person>() {
        public int compare(Person o1, Person o2) {
            return o1.getUsername().compareToIgnoreCase(o2.getUsername());
        }
    };

    public static final Comparator<Person> LASTNAME = new Comparator<Person>() {
        public int compare(Person o1, Person o2) {
        	int result = o1.getLastname().compareToIgnoreCase(o2.getLastname());
        	
            if (result == 0) {
                result = o1.getFirstName().compareToIgnoreCase(o2.getFirstName());
            }
            if (result == 0) {
                result = o1.getMiddlename().compareToIgnoreCase(o2.getMiddlename());
            }
            if (result == 0) {
                result = o1.getUsername().compareToIgnoreCase(o2.getUsername());
            }
            if (result == 0) {
            	result = o1.getPersonId().compareToIgnoreCase(o2.getPersonId());
            }
            return result;
        	
        }
    };
    
    public enum UserType {
        GUEST, MEMBER, CMTY, ADMIN
    }
    
    public static final String GUEST = "GUEST";
    public static final String MEMBER= "MEMBER";
    public static final String CMTY  = "CMTY";
    public static final String ADMIN = "ADMIN";

    @Autowired
    private CommunityManager communityManager;

    private String username;
    private String firstName;
    private String middlename;
    private String lastname;
    private Date lastLogin;
    private long maxStorageSize;
    private String email;
    private UserType usertype;
    private String password;
    private String password2;
    private String campus;
    private boolean isNew = false;
    private String personId;
    private Date dateCreated;
    private EntryKey profilePictureKey;
    /* this is if the person represents a community */
    private Integer communityId;
    
    private String base64_username;
    
    private boolean is_enabled = true;
     
    public void setIsEnabled(boolean is_enabled) {
    	this.is_enabled = is_enabled;
    }
    
    public boolean isEnabled() {
    	return is_enabled;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
    
    public String getBase64Username() {
    	return base64_username;
    }

    public String getUsername() {
        return (username != null ? username : EMPTY_STRING);
    }

    public void setUsername(String username) {
        
    	this.username = username;
        
    	if(username != null) {
        	this.base64_username = new String(Base64.encodeBase64(username.getBytes()));
        }

    }

    public String getFirstName() {
        return (firstName != null ? firstName : EMPTY_STRING);
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddlename() {
        return (middlename != null ? middlename : EMPTY_STRING);
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return (lastname != null ? lastname : EMPTY_STRING);
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public boolean isNew() {
        return (getPersonId() == null || getPersonId().length() < 1 || isNew);
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getLastLogin() {
        return (lastLogin != null ? lastLogin : null);
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public long getMaxStorageSize() {
        return maxStorageSize;
    }

    public void setMaxStorageSize(long maxStorageSize) {
        this.maxStorageSize = maxStorageSize;
    }

    public String getEmail() {
        if (email != null) {
            return email;
        }

        if (username != null && username.trim().length() > 0) {
            // all guest users should have an email address, so we should assume
            // that if the address is blank, it's a U of M address
            return username + "@umn.edu";
        }

        return EMPTY_STRING;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUsertype() {
        return usertype;
    }
    
    public void setUsertype(String usertype) {
    	
   		this.usertype = UserType.GUEST;
    	
    	if(usertype.contentEquals(MEMBER)) {
    		this.usertype = UserType.MEMBER;
    	}
    	else if(usertype.contentEquals(ADMIN)) {
    		this.usertype = UserType.ADMIN;
    	}
    	
    	logService.debug("==> ");
    }
 
    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public String getPassword() {
        return (password != null ? password : EMPTY_STRING);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return (password2 != null ? password2 : EMPTY_STRING);
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getCampus() {
        return (campus != null ? campus : EMPTY_STRING);
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
    
    public String getUserTypeToString() {
    	
    	switch(usertype) {
    	
    	case MEMBER:
    		return "MEMBER";
    	
    	case GUEST:
    		return GUEST;
    		
    	case CMTY:
    		return CMTY;
    		
    	case ADMIN:
    		return ADMIN;
    	
    	default:
    			
    		return GUEST;
    	}
    }

    public static String getUserTypeToString(UserType usertype) {
    	
    	switch(usertype) {
    	
    	case MEMBER:
    		return "MEMBER";
    	
    	case GUEST:
    		return GUEST;
    		
    	case CMTY:
    		return CMTY;
    		
    	case ADMIN:
    		return ADMIN;
    	
    	default:
    			
    		return GUEST;
    	}
    }

    
    /**
     * Checks to see if the user is a guest (i.e., read only) user.
     * 
     * @return true if the user is a guest user
     * @since Rel 1.0.0 Alpha 2
     */
    public boolean isGuest() {
        return usertype == UserType.GUEST;
    }

    public boolean isMember() {
        return usertype == UserType.MEMBER;
    }

    public boolean isAdmin() {
        return usertype == UserType.ADMIN;
    }

    public long getMaximumFileSize() {
        return Configuration.getLong("repository.maxFileSize");
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();
        if (request.getParameter("viewfile.x") != null)
            return errors;

        if ((firstName == null) || (firstName.trim().length() == 0)) {
            errors.add("firstName", new ActionMessage("error.registration.firstName.required"));
        }
        if ((lastname == null) || (lastname.trim().length() == 0)) {
            errors.add("lastname", new ActionMessage("error.registration.lastname.required"));
        }
        if ((email == null) || (email.trim().length() == 0)) {
            errors.add("email", new ActionMessage("error.registration.email.required"));
        }
        if ((email != null) && (!validEmail(email))) {
            errors.add("email", new ActionMessage("error.registration.email.notValid"));
        }
        // added... guest users will not have a username
        if (Configuration.get("user.registration.usertype").equalsIgnoreCase(PortfolioConstants.MEMBER)) {
            if (username == null) {
                errors.add("username", new ActionMessage("error.registration.username.required"));
            }
            if ((username != null) && (!validUsename(username))) {
                errors.add("username", new ActionMessage("error.registration.username.notValid"));
            }
        }
        if (password == null) {
            errors.add("password", new ActionMessage("error.registration.password.required"));
        }
        if ((password != null) && (!validPassword(password))) {
            errors.add("password", new ActionMessage("error.registration.password.notValid"));
        }
        if ((password != null) && (!password.equals(password2))) {
            errors.add("password", new ActionMessage("error.registration.password.notMatch"));
        }
        return errors;
    }

    /**
     * Confirms that the username is valid
     * 
     * @param username
     * @return true if the username has a length > 6 characters, and less than 16
     */
    public static boolean validUsename(String username) {
        boolean valid = false;

        if (username != null) {
            valid = ((username.trim().length() > 5) && (username.length() < 17));
            valid = (valid && !username.equalsIgnoreCase("username"));
        }
        return valid;
    }

    /**
     * Validates the email address. Must have an @ and a . following it
     * 
     * @param email to validate
     * @return true if valid.
     */
    public static boolean validEmail(String email) {
        boolean valid = false;

        if (email != null) {
            valid = true;
            valid = (valid && email.trim().length() > 0);
            int atIndex = email.indexOf('@');
            valid = (valid && atIndex > 0);
            int dotIndex = email.indexOf('.', atIndex);
            valid = (valid && dotIndex > 0);
        }
        return valid;
    }

    /**
     * Validates the password. Must be longer than 6 characters, and not be "password
     * 
     * @param password to validate
     * @return true if the password is valid
     */
    public static boolean validPassword(String password) {
        boolean valid = false;
        if (password != null) {
            valid = true;
            valid = (valid && password.trim().length() > 5);
            valid = (valid && !password.equalsIgnoreCase("password"));
        }
        return valid;
    }

    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        if (StringUtils.hasText(firstName)) {
            fullName.append(firstName);
        }
        if (StringUtils.hasText(middlename)) {
            fullName.append(" ").append(middlename);
        }
        if (StringUtils.hasText(lastname)) {
            fullName.append(" ").append(lastname);
        }
        if (fullName.toString().trim().length() == 0) {
            fullName.append(email);
        }
        return fullName.toString().trim();
    }

    public String getDisplayName() {
        switch (getUsertype()) {
            case GUEST:
                return getEmail();
            default:
                return getFullName();
        }
    }
    
    public String getTruncatedDisplayName() {
    	
        switch (getUsertype()) {
        	case GUEST:
        		return getEmail();
        	default:
        		return ellipsize(getFullName(), 40);
        }
    	
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Person && ((Person) obj).personId.equals(this.personId);
    }

    @Override
    public int hashCode() {
        return this.personId.hashCode();
    }

    public List<Community> getCommunities() {
        // TODO this is used for the menu. how should i make cleaner?
        List<Community> communities = communityManager.getCommunitiesByPersonId(personId);
        Collections.sort(communities, Community.COMMUNITY_NAME);
        
        return communities;
    }

    public EntryKey getProfilePictureKey() {
        return profilePictureKey;
    }

    public void setProfilePictureKey(EntryKey profilePictureKey) {
        this.profilePictureKey = profilePictureKey;
    }

    public Integer getCommunityId() {
        return communityId;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }
    
	private final static String NON_THIN = "[^iIl1\\.,']";
	
	private static int textWidth(String str) {
		return (int) (str.length() - str.replaceAll(NON_THIN, "").length() / 2);
	}
	
	public static String ellipsize(String text, int max) {
	
		if (textWidth(text) <= max)
			return text;
	
		// Start by chopping off at the word before max
		// This is an over-approximation due to thin-characters...
		int end = text.lastIndexOf(' ', max - 3);
	
		// Just one long word. Chop it off.
		if (end == -1)
			return text.substring(0, max-3) + "...";
	
		// Step forward as long as textWidth allows.
		int newEnd = end;
		do {
			end = newEnd;
			newEnd = text.indexOf(' ', end + 1);
	
			// No more spaces.
			if (newEnd == -1) {
				newEnd = text.length();
			}
	
		} while (textWidth(text.substring(0, newEnd) + "...") < max);
	
		return text.substring(0, end) + "...";
	}
}

