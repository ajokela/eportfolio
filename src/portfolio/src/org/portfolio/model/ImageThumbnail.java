package org.portfolio.model;

import java.math.BigDecimal;
import java.util.Date;

// INSERT INTO element_definition ( element_id, element_name, element_count, table_name, sort_element_id, class_name, tree_filter_name, public_view) VALUES ('epf_thumbnail', 'Portfolio Thumbnail', 0, 'PORTFOLIO_THUMBNAILS', 0, 'org.portfolio.model.PortfolioThumbnail', 'Portfolio Thumbnail', 1);

public class ImageThumbnail extends FileElementBase {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8638441786945547586L;

	private int id;
	private int uploaded_material_id;
	private double percent_size;
	private long filesize;
	private String mimetype;
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	private Date updated_at;
	private Date created_at;
	private UploadedMaterial uploadedMaterial;
	private int width;
	private int height;
	
	public ImageThumbnail() throws Exception {
		super();
	}

	public void setId(int id) {
		this.setEntryId(BigDecimal.valueOf(id));
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUploaded_material_id(int uploaded_material_id) {
		this.uploaded_material_id = uploaded_material_id;
	}

	public int getUploaded_material_id() {
		return uploaded_material_id;
	}

	public void setPercent_size(double percent_size) {
		this.percent_size = percent_size;
	}

	public double getPercent_size() {
		return percent_size;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setMimeType(String mimetype) {
		this.mimetype = mimetype;
	}

	public String getMimeType() {
		return mimetype;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getX1() {
		return x1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getY1() {
		return y1;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getX2() {
		return x2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public int getY2() {
		return y2;
	}

	public void setUpdated_at(Date updated_at) {
		this.updated_at = updated_at;
	}

	public Date getUpdated_at() {
		return updated_at;
	}

	public void setCreated_at(Date created_at) {
		this.setDateCreated(created_at);
		this.created_at = created_at;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public void setUploadedMaterial(UploadedMaterial uploadedMaterial) {
		this.uploadedMaterial = uploadedMaterial;
	}

	public UploadedMaterial getUploadedMaterial() {
		return uploadedMaterial;
	}

	@Override
	public int getWidth() {
		
		return width;
	}

	@Override
	public int getHeight() {
		
		return height;
	}

	@Override
	public void setWidth(int width) {
		
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;		
	}

	@Override
	public boolean isImage() {
		
		return true;
	}
	
}