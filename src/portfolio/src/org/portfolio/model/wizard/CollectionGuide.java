/* $Name:  $ */
/* $Id: CollectionGuide.java,v 1.17 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.model.wizard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.portfolio.dao.wizard.CategoryHome;
import org.portfolio.dao.wizard.WizardTemplateHome;
import org.portfolio.model.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class CollectionGuide {

    public static Comparator<CollectionGuide> TITLE_COMPARATOR = new Comparator<CollectionGuide>() {
        public int compare(CollectionGuide o1, CollectionGuide o2) {
            if (o1.getTitle() == null) {
                return -1;
            }
            if (o2.getTitle() == null) {
                return 1;
            }
            return o1.getTitle().compareTo(o2.getTitle());
        }
    };

    @Autowired
    private WizardTemplateHome wizardTemplateHome;
    @Autowired
    private CategoryHome categoryHome;
    
    private CollectionGuideUserData userData;

    private Integer id;
    private Integer communityId;
    private String title;
    private String tip = "Need Assistance? Contact your campus Help Desk via phone or email portfolio@umn.edu";
    private String shareTip;
    private List<Category> categories;
    private List<Template> templates;
    private Date dateCreated;
    private boolean published;
    private boolean deleted;
    private String description;
    
    public void setCollectionGuideUserData(CollectionGuideUserData data) {
    	userData = data;
    }
    
    public CollectionGuideUserData getCollectionGuideUserData() {
    	return userData;
    }
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    /**
     * @return never null
     */
    public List<Category> getCategories() {
        if (this.categories == null) {
            this.categories = categoryHome.findTopLevelCategories(this.id);
        }
        return this.categories;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public void setShareTip(String shareTip) {
        this.shareTip = shareTip;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getCommunityId() {
        return this.communityId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTip() {
        return this.tip;
    }

    public String getShareTip() {
        return this.shareTip;
    }

    public List<Template> getTemplates() {
        if (this.templates == null) {
            this.templates = wizardTemplateHome.getTemplates(this.id);
        }
        return this.templates;
    }
    
    public List<WizardElementDefinition> getWizardElementDefinitions() {
        List<WizardElementDefinition> defs = new ArrayList<WizardElementDefinition>();
        List<Category> categories = getCategories();
        for (Category category : categories) {
            List<Category> subcategories = category.getCategories();
            for (Category subcategory : subcategories) {
                List<WizardElementDefinition> wizardElementDefinitions = subcategory.getWizardElementDefinitions();
                defs.addAll(wizardElementDefinitions);
            }
        }
        return defs;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return this.getTitle();
    }
}
