package org.portfolio.model;

public class FileExt {

	private int id;
	private String ext;
	private String description;
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setExt(String ext) {
		this.ext = ext;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	
	public String getExt() {
		return ext;
	}
	
	public String getDescription() {
		return description;
	}
	
}