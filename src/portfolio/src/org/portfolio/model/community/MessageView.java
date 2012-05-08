/* $Name:  $ */
/* $Id: MessageView.java,v 1.1 2010/11/23 14:29:11 ajokela Exp $ */
package org.portfolio.model.community;

import java.util.Date;



/**
 * 
 * @author Alex Jokela
 * 
 */
public class MessageView  {

	private int id;
	private Date created_at;
	private Date updated_at;
	private String person_id;
	private int message_id;
	private String status;
	
	public MessageView() {
		
		setDates();
	}
	
	public MessageView(boolean skipId) {
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
	
	public void setPersonId(String p) {
		person_id = p;
	}
	
	public String getPersonId() {
		return person_id;
	}
	
	public void setMessageId(int m) {
		message_id = m;
	}
	
	public int getMessageId() {
		return message_id;
	}
	
	public void setStatus(String s) {
		status = s;
	}
	
	public String getStatus() {
		return status;
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
	

}
