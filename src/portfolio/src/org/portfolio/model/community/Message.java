/* $Name:  $ */
/* $Id: Message.java,v 1.1 2010/11/23 14:29:11 ajokela Exp $ */
package org.portfolio.model.community;

import java.util.Date;

import org.portfolio.model.Person;


/**
 * 
 * @author Alex Jokela
 * 
 */

public class Message implements Comparable<Message>
{

	private int id;
	private int community_id;
	private String coord_id;
	private Person coord;
	private boolean deleted;
	private Date created_at;
	private Date updated_at;
	private Date started_at;
	private Date ended_at;
	private String message_txt;
	private String subject;
	private String message_txt_diff;
	private boolean read;
	
	
	public Message() {
		
		ended_at = null;
		started_at = null;

		setDates();
	}
	
	public Message(boolean skipId) {
		ended_at = null;
		started_at = null;
		setDates();
	}
	
	private void setDates() {
		Date d = new Date();
		
		created_at = d;
		updated_at = d;
	}
	
	public void setId(int i) {
		id = i;
	}
	
	public int getId() {
		return id;
	}

	public void setCommunityId(int i) {
		community_id = i;
	}
	
	public int getCommunityId() {
		return community_id;
	}
	
	public void setCoordinatorId(String i) {
		coord_id = i;
	}
	
	public void setCoordinator(Person c) {
		coord = c;
	}
	
	public Person getCoordinator() {
		
		return coord;
	}
	
	public String getCoordinatorId() {
		return coord_id;
	}
	
	public void setDeleted(boolean d) {
		deleted = d;
	}
	
	public boolean getDeleted() {
		return deleted;
	}
	
	public boolean isDeleted() {
		return getDeleted();
	}
	
	public void setCreatedAt(Date d) {
		created_at = d;
	}
	
	public Date getCreatedAt() {
		return created_at;
	}
	
	public void setUpdatedAt(Date d) {
		updated_at = d;
	}
	
	public Date getUpdatedAt() {
		return updated_at;
	}
	
	public void setStartedAt(Date d) {
		started_at = d;
	}
	
	public Date getStartedAt() {
		return started_at;
	}
	
	public void setEndedAt(Date d) {
		ended_at = d;
	}
	
	public Date getEndedAt() {
		return ended_at;
	}
	
	public void setMessageTxt(String msg) {
		message_txt = msg;
	}
	
	public String getMessageTxt() {
		return message_txt;
	}
	
	public void setMessageTxtDiff(String msg_diff) {
		message_txt_diff = msg_diff;
	}
	
	public String getMessageTxtDiff() {
		return message_txt_diff;
	}
	
	public void setSubject(String sub) {
		subject = sub;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setRead(boolean r) {
		read = r;
	}
	
	public boolean getRead() {
		return read;
	}
	
	public int compareTo(Message msg) {

		if(msg == null) {
			return 0;
		}
		
		if(this.getStartedAt() == null && this.getCreatedAt() == null) {
			return 0;
		}
		
		if(this.getStartedAt() != null && msg.getStartedAt() != null) {
			return this.getStartedAt().compareTo(msg.getStartedAt());
		}
		
		
		
		return 0;
	}
}
