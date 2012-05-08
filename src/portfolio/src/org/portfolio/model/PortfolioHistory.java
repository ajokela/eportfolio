/* $Name:  $ */
/* $Id: PortfolioHistory.java,v 1.3 2011/03/24 20:51:29 ajokela Exp $ */
package org.portfolio.model;

import java.util.Date;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

import java.text.SimpleDateFormat;

import org.portfolio.dao.PersonHome;
import org.portfolio.util.LogService;

public class PortfolioHistory {
	
	private PersonHome personHome;
	
	@SuppressWarnings("unused")
	private final LogService logService = new LogService(this.getClass());
	
	public static final String SHARE_ADD_USER = "SHARE_ADD_USER";
	public static final String SHARE_DEL_USER = "SHARE_DEL_USER";
	public static final String SHARE_CREATE   = "SHARE_CREATE";
	public static final String SHARE_DELETE   = "SHARE_DELETE";
	public static final String SHARE_UPDATE   = "SHARE_UPDATE";
	public static final String SHARE_NOTIFY   = "SHARE_NOTIFY";
	public static final String SHARE_DEFAULT  = "UNKNOWN";
	
	public enum Action {
	     SHARE_ADD_USER,
	     SHARE_DEL_USER,
	     SHARE_CREATE,
	     SHARE_DELETE,
	     SHARE_UPDATE,
	     SHARE_NOTIFY,
	     SHARE_DEFAULT
	}
	
	private Integer id;
	private String person_id;
	private String share_id;
	private Action action;
	private Date   stamp;
	private String description;
	private String content;
	private Person person;
	private String batch_id;
	
	public PortfolioHistory(PersonHome personHome) {
		super();
		this.personHome = personHome;
		generateBatchId();
	}
	
	public String getBatchId() {
		return batch_id;
	}
	
	private void generateBatchId() {
		Checksum checksum = new CRC32();
		Date now = new Date();
		checksum.update(now.toString().getBytes(),0,now.toString().getBytes().length);
		batch_id = Long.toString(checksum.getValue());
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setPersonId(String person_id) {
		this.person_id = person_id;
		
		if(person_id != null) {
			person = personHome.findByPersonId(person_id);
		}
		
	}
	
	public String getPersonId() {
		return person_id;
	}
	
	public Person getPerson() {
		return person;
	}
	
	public void setShareId(String share_id) {
		this.share_id = share_id;
	}
	
	public String getShareId() {
		return share_id;
	}
	
	public void setAction(Action action) {
		this.action = action;
	}
	
	public Action getAction() {
		return action;
	}
	
	public void setAction(String action) {

		if (action.contentEquals(SHARE_ADD_USER)) {
			setAction(Action.SHARE_ADD_USER);
		}
		else if(action.contentEquals(SHARE_DEL_USER)) {
			setAction(Action.SHARE_DEL_USER);
		}
		else if(action.contentEquals(SHARE_CREATE)) {
			setAction(Action.SHARE_CREATE);
		}
		else if(action.contentEquals(SHARE_DELETE)) {
			setAction(Action.SHARE_DELETE);
		}
		else if(action.contentEquals(SHARE_UPDATE)) {
			setAction(Action.SHARE_UPDATE);
		}
		else if(action.contentEquals(SHARE_NOTIFY)) {
			setAction(Action.SHARE_NOTIFY);
		}
		else {
			setAction(Action.SHARE_DEFAULT);
		}
		
	}
	
	public String getActionString() {
		switch(action) {
			
		case SHARE_ADD_USER:
			return SHARE_ADD_USER;
			
		case SHARE_DEL_USER:
			return SHARE_DEL_USER;
			
		case SHARE_CREATE:
			return SHARE_CREATE;
			
		case SHARE_DELETE:
			return SHARE_DELETE;
			
		case SHARE_UPDATE:
			return SHARE_UPDATE;
			
		case SHARE_NOTIFY:
			return SHARE_NOTIFY;
		
		default:
			return SHARE_DEFAULT;
		}
	}
		
	public void setStamp(Date stamp) {
		this.stamp = stamp;
	}
	
	public Date getStamp() {
		
		return stamp;
	}
	
	public String getFormattedStamp() {
		
		SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
		return format.format(stamp);
		
	}
	
	public void setDescription(String description) {
		this.description = description; // + " [" + getBatchId() + "]";
	}
	
	public String getDescription() {
		return description;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
}