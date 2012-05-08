/* $Name:  $ */
/* $Id: ElementType.java,v 1.9 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model;

public class ElementType {

    public enum Category {
        PERSONAL("Personal"),
        EDUCATION("Education"),
        CAREER("Career"),
        SKILLS("Skills"),
        PROFESSIONAL("Professional"),
        RECOGNITION("Recognition");

        private String label;
        
        private Category(String label) {
            this.label = label;
        }
        
        @Override
        public String toString() {
            return label;
        }
    }

    // private LogService logService = new LogService(this.getClass());
    
    private String id;
    private String name;
    private String modelClassName;
    private String jspPrefix;
    private String viewJsp;
    private String editJsp;
    private Category category;
    private boolean allowsAttachments = true;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
    	
        return name;
    }

    public void setName(String name) {
    	
        this.name = name;
    }

    public String getModelClassName() {
        return modelClassName;
    }

    public void setModelClassName(String modelClassName) {
        this.modelClassName = modelClassName;
    }

    public String getViewJsp() {
        return viewJsp;
    }

    public void setViewJsp(String viewJsp) {
        this.viewJsp = viewJsp;
    }

    public String getEditJsp() {
        return editJsp;
    }

    public void setEditJsp(String editJsp) {
        this.editJsp = editJsp;
    }

    public String getJspPrefix() {
        return jspPrefix;
    }

    public void setJspPrefix(String jspPrefix) {
        this.jspPrefix = jspPrefix;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isAllowsAttachments() {
        return allowsAttachments;
    }

    public void setAllowsAttachments(boolean allowsAttachments) {
        this.allowsAttachments = allowsAttachments;
    }
    
    public String toString() {
    	String str = "";
    	
    	str += "ModelName: " + this.getModelClassName();
    	str += " | Name: " + this.getName();
    	str += " | id: " + this.getId();
    	
    	return str;
    }
}
