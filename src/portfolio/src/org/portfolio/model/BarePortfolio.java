package org.portfolio.model;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Comparator;

public class BarePortfolio {
	private BigInteger id;
	private Timestamp updated_at;
	private String author;
	private String title;
	
	public static final Comparator<BarePortfolio>DATE_ORDER = new Comparator<BarePortfolio>() {
		public int compare(BarePortfolio p1, BarePortfolio p2) {
			if(p2 == null) {
				return 1;
			}
			else {
				if(p2.getUpdatedAt() == null) {
					return 1;
				}
			}

			if(p1 == null) {
				return -1;
			}
			else {
				if(p1.getUpdatedAt() == null) {
					return -1;
				}
			}
						
			return p1.getUpdatedAt().compareTo(p2.getUpdatedAt());
		}
	};

	public static final Comparator<BarePortfolio>TITLE_ORDER = new Comparator<BarePortfolio>() {
		public int compare(BarePortfolio p1, BarePortfolio p2) {
			if(p2 == null) {
				return 1;
			}
			else {
				if(p2.getTitle() == null) {
					return 1;
				}
			}

			if(p1 == null) {
				return -1;
			}
			else {
				if(p1.getTitle() == null) {
					return -1;
				}
			}
						
			return p1.getTitle().compareToIgnoreCase(p2.getTitle());
		}
	};
	
	public static final Comparator<BarePortfolio>AUTHOR_ORDER = new Comparator<BarePortfolio>() {
		public int compare(BarePortfolio p1, BarePortfolio p2) {
			if(p2 == null) {
				return 1;
			}
			else {
				if(p2.getAuthor() == null) {
					return 1;
				}
			}

			if(p1 == null) {
				return -1;
			}
			else {
				if(p1.getAuthor() == null) {
					return -1;
				}
			}
						
			return p1.getAuthor().compareToIgnoreCase(p2.getAuthor());
		}
	};
	
	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Timestamp getUpdatedAt() {
		return updated_at;
	}

	public void setUpdatedAt(Timestamp updated_at) {
		this.updated_at = updated_at;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public final String toString() {
		String str = "";
		
		if(id != null) {
			str += "ShareId: " + id.toString() + " | ";
		}
		
		if(title != null) {
			str += "Title: " + title + " | ";
		}
		
		if(author != null) {
			str += "Author: " + author + " | ";
		}
		
		if(updated_at != null) {
			str += "Updated At: " + updated_at.toString() + " | ";
		}
		
		return str;
	}
	
}