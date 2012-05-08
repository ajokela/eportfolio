package org.portfolio.model;

import java.util.Date;

public class Whiteboard {
	private int id;
	private int community_id;
	private String person_id;
	private String session_id;
	private String color;
	private int offsetx1;
	private int offsetx2;
	private int offsety1;
	private int offsety2;
	private int length;
	private Date created_at;
	private Date updated_at;
	private boolean is_deleted;
	private int grouping;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setCommunityId(int community_id) {
		this.community_id = community_id;
	}
	
	public void setPersonId(String person_id) {
		this.person_id = person_id;
	}
	
	public void setSessionId(String session_id) {
		this.session_id = session_id;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public void setOffsetX1(int x1) {
		offsetx1 = x1;
	}
	
	public void setOffsetX2(int x2) {
		offsetx2 = x2;
	}
	
	public void setOffsetY1(int y1) {
		offsety1 = y1;
	}
	
	public void setOffsetY2(int y2) {
		offsety2 = y2;
	}
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public void setCreatedAt(Date created_at) {
		this.created_at = created_at;
	}
	
	public void setUpdatedAt(Date updated_at) {
		this.updated_at = updated_at;
	}
	
	public void setIsDeleted(boolean is_deleted) {
		this.is_deleted = is_deleted;
	}
	
	public void setGrouping(int grouping) {
		this.grouping = grouping;
	}
	
	///////////////////////////////////////////////////////////////////
	
	public int getId() {
		return id;
	}
	
	public int getCommunityId() {
		return community_id;
	}
	
	public String getPersonId() {
		return person_id;
	}
	
	public String getSessionId() {
		return session_id;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getOffsetX1() {
		return offsetx1;
	}
	
	public int getOffsetX2() {
		return offsetx2;
	}
	
	public int getOffsetY1() {
		return offsety1;
	}
	
	public int getOffsetY2() {
		return offsety2;
	}
	
	public int getLength() {
		return length;
	}
	
	public Date getUpdatedAt() {
		return updated_at;
	}
	
	public Date getCreatedAt() {
		return created_at;
	}
	
	public boolean isDeleted() {
		return is_deleted;
	}
	
	public int getGrouping() {
		return grouping;
	}
} 
