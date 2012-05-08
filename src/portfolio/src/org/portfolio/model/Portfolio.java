/* $Name:  $ */
/* $Id: Portfolio.java,v 1.22 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.dao.PersonHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.dao.community.CommunityHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.Viewer.ViewType;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.community.Community;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
public class Portfolio extends ActionForm {

    private static final long serialVersionUID = 8236095691473356119L;
    private final LogService logService = new LogService(this.getClass());

    private static final String STOCK = "stock";
    
    public static final Comparator<Portfolio> DATE_MODIFIED_ORDER = new Comparator<Portfolio>() {
        public int compare(Portfolio def1, Portfolio def2) {
            return def2.getDateModified().compareTo(def1.getDateModified());
        }
    };
    
    public static final Comparator<Portfolio> DATE_ORDER = DATE_MODIFIED_ORDER;

    public static final Comparator<Portfolio> OWNER_LASTNAME = new Comparator<Portfolio>() {
        public int compare(Portfolio def1, Portfolio def2) {
            // return def2.getDateModified().compareTo(def1.getDateModified());
        	return def1.getPerson().getLastname().compareToIgnoreCase(def2.getPerson().getLastname());
        }
    };

    public static final Comparator<Portfolio> AUTHOR_ORDER = new Comparator<Portfolio>() {
        public int compare(Portfolio def1, Portfolio def2) {
            	
        	String name1 = def1.getPerson().getLastname();
        	
        	if(def1.getPerson().getMiddlename() != null) {
        		name1 += def1.getPerson().getMiddlename();
        	}

        	if(def1.getPerson().getFirstName() != null) {
        		name1 += def1.getPerson().getFirstName();
        	}
        	
        	name1 = name1.replaceAll("[^a-zA-Z0-9]", "");
        	
        	String name2 = def2.getPerson().getLastname();
        	
        	if(def2.getPerson().getMiddlename() != null) {
        		name2 += def2.getPerson().getMiddlename();
        	}

        	if(def2.getPerson().getFirstName() != null) {
        		name2 += def2.getPerson().getFirstName();
        	}
        	
        	name2 = name2.replaceAll("[^a-zA-Z0-9]", "");
        	
        	return name1.compareToIgnoreCase(name2);
        }
    };

    public static final Comparator<Portfolio> TITLE_ORDER = new Comparator<Portfolio>() {
        public int compare(Portfolio def1, Portfolio def2) {
            // return def2.getDateModified().compareTo(def1.getDateModified());
        	return def1.getShareName().compareToIgnoreCase(def2.getShareName());
        }
    };
    
    protected String personId;
    
    private Person person;
    private String shareId;
    private String shareName;
    private String elementName;
    private String shareDesc;
    private Date dateModified;
    private Date dateCreated;
    private Date dateExpire;
    private String templateId;
    private boolean publicView = false;
    private boolean quickShare = false;
    private int page = 1;
    private String theme;
    private String colorScheme;
    private String headerImageType;
    private String stockImage;
    private Template template;
    private Community community = null;
    private List<Viewer> viewers;
    private Map<String, Viewer> viewersMap;
    private List<String> tags = new ArrayList<String>();
    private AssessmentModelAssignment assessmentModelAssignment;
    private List<Assessment> assessments;
    private boolean is_enabled = true;
    private boolean is_deleted = false;
    private static final String historyString = "Effect.toggle('historyReport_ID', 'slide', { queue: 'end' });return false;";
    private String contents;

    private List<PortfolioHistory> history;
    
    private int contentChangeCount = -1;
    
    @Autowired
    private ViewerHome viewerHome;
    @Autowired
    private PersonHome personHome;
    @Autowired
    private TemplateHome templateHome;
    @Autowired
    private AssessmentModelAssignmentHome assessmentModelAssignmentHome;

    @Autowired
    private CommunityHome communityHome;
    
    @Autowired
    private AssessmentManager assessmentManager;
    
    public String getAjaxUserLookupString() {
    	return "";
    }
    
    public String getHistoryString() {
    	return historyString.replaceAll("ID", getShareId());
    }
    
    public List<PortfolioHistory> getHistory() {
    	return history;
    }
    
    public void setHistory(List<PortfolioHistory> history) {
    	this.history = history;
    }
    
    public boolean isDeleted() {
    	return is_deleted;
    }
    
    public void setIsDeleted(boolean is_deleted) {
    	this.is_deleted = is_deleted;
    }
    
    public boolean isEnabled() {
    	return is_enabled;
    }
    
    public void setIsEnabled(boolean is_enabled) {
    	this.is_enabled = is_enabled;
    	
    	if(!is_enabled) {

    		Calendar cal = Calendar.getInstance();

    		cal.add(Calendar.YEAR, -1);

    		setDateExpire(cal.getTime());
    		
    	}
    }
    
    public String getEntryId() {
        return getShareId();
    }

    public String getShareId() {
        return shareId != null ? shareId : "";
    }

    public void setShareId(String shareId) {
    	
        this.shareId = shareId;
    
        try {
        	setAssessments(assessmentManager.findLatestPortfolioAssessments(shareId, null, null));
        }
        catch(Exception e) {
        	logService.debug(e);
        }
        
    }

    public String getPersonId() {
        return personId != null ? personId : "";
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public boolean isNew() {
        return getDateCreated() == null;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getShareName() {
        return shareName != null ? shareName : "";
    }

    public void setShareName(String shareName) {
    	
    	if(shareName.length() > 50) {
    		shareName = shareName.substring(0, 50);
    	}
    	
        this.shareName = shareName;
    }

    public String getShareDesc() {
        return shareDesc != null ? shareDesc : "";
    }

    public void setShareDesc(String shareDesc) {
        this.shareDesc = shareDesc;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public Date getDateExpire() {
        return dateExpire;
    }

    public void setDateExpire(Date dateExpire) {
        this.dateExpire = dateExpire;
    }

    public String getDateExpireString() {
        return getDateExpire() == null ? "" : new SimpleDateFormat("MM/dd/yyyy").format(getDateExpire());
    }

    public void setDateExpireString(String dateExpire) {
        if (dateExpire != null && dateExpire.trim().length() > 0 && !dateExpire.equalsIgnoreCase("none")) {
            try {
                this.dateExpire = new SimpleDateFormat("MM/dd/yyyy").parse(dateExpire);
            } catch (ParseException e) {
                logService.debug(e);
            }
        } else {
            this.dateExpire = null;
        }
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
        
        if(templateId != null) {
        	try {
        		this.template = templateHome.findTemplateById(templateId);
        	}
        	catch (Exception e) {
        		logService.debug(e);
        	}
        }
        
    }

    public boolean isPublicView() {
        return publicView;
    }

    public void setPublicView(boolean publicView) {
        this.publicView = publicView;
    }
    
    public void setAssessments(List<Assessment> assessments) {
    	
    	if(assessments != null) {
    		Collections.sort(assessments, Assessment.ASSESSED_DATE_COMPARATOR);
    	}
    	
    	this.assessments = assessments;
    }
    
    public List<Assessment> getAssessments() {
    	return assessments;
    }

    public boolean isQuickShare() {
        return this.quickShare;
    }

    public void setQuickShare(boolean quickShare) {
        this.quickShare = quickShare;
    }

    public List<Viewer> getViewersObject() {
        if (viewers == null) {
            viewers = viewerHome.findByShareId(shareId);
            Collections.sort(viewers);
        }
        return viewers;
    }
    
    public void refreshViewers(){
        viewers = null;
    }

    public List<Viewer> getViewersByType(ViewType viewType) {
        List<Viewer> result = new ArrayList<Viewer>();
        for (Viewer viewer : getViewersObject()) {
            if (viewer.getViewType() == viewType) {
                result.add(viewer);
            }
        }
        return result;
    }

    public Viewer getOwnerViewer() {
        return getViewersByType(ViewType.OWNER).get(0);
    }

    /**
     * @return a list of viewers of type {@link ViewType#VIEWER}
     */
    public List<Viewer> getViewerViewers() {
        return getViewersByType(ViewType.VIEWER);
    }

    public Viewer getViewerByPersonId(String personId) {
        return getViewersMap().get(personId);
    }

    /**
     * personId --> Viewer
     */
    public Map<String, Viewer> getViewersMap() {
        if (viewersMap == null) {
            Map<String, Viewer> myMap = new HashMap<String, Viewer>();
            for (Viewer viewer : getViewersObject()) {
                myMap.put(viewer.getPersonId(), viewer);
            }
            viewersMap = myMap;
        }
        return viewersMap;
    }

    public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
        ActionErrors errors = new ActionErrors();

        if ((shareName == null) || (shareName.equals(" ")) || (shareName.length() < 1)) {
            errors.add("shareName", new ActionMessage("error.required.missing", "Title"));
            logService.debug("presentation name required...");
        }

        /*
        // Greater than 512 characters?
        if (shareDesc != null && shareDesc.length() > PortfolioConstants.ONE_HUNDRED_WORDS) {
            // truncating.
            shareDesc = shareDesc.substring(0, PortfolioConstants.ONE_HUNDRED_WORDS);
            errors.add("shareDesc", new ActionMessage("error.lengthTooLong", "Description", PortfolioConstants.ONE_HUNDRED_WORDS_DESC));
            logService.debug("presentation description too long...");
        }
         */

        if (page == 1) {
            if (request.getParameter("publicView") == null) {
                setPublicView(false);
            }
        }
        
        if (isPublicView() && getDateExpire() == null) {
        	
        	Calendar now = Calendar.getInstance();
        	Calendar expires = Calendar.getInstance();
        	
        	expires.set(now.get(Calendar.YEAR) + 1, now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        	
        	Date date = expires.getTime();
        	setDateExpire(date);
        	
            // errors.add("shareName", new ActionMessage("error.required.missing", "Expiration Date"));
        }

        return errors;
    }

    public void reset(ActionMapping mapping, javax.servlet.http.HttpServletRequest request) {
        super.reset(mapping, request);
        publicView = false;
    }

    public boolean isExpired() {
        return getDateExpire() != null && getDateExpire().before(new Date());
    }

    /**
     * @see org.portfolio.model.Portfolio#getTemplate()
     */
    public Template getTemplate() {
        if (template == null && getTemplateId() != null) {
            template = templateHome.findTemplateById(getTemplateId().toString());
        }

        if( template != null ) {
        	community = communityHome.findByCommunityId(template.getCommunityId());
        }
        
        return template;
    }
    
    public Community getCommunity() {
    	return community;
    }

    /**
     * Return the Person or null if not found/error
     */
    public Person getPerson() {
        if (person == null && personId != null) {
            person = personHome.findByPersonId(personId);
        }
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        this.personId = person.getPersonId();
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String colorScheme) {
        this.colorScheme = colorScheme;
    }

    public String getHeaderImageType() {
        return headerImageType;
    }

    public void setHeaderImageType(String headerImageType) {
        this.headerImageType = headerImageType;
    }

    public String getStockImage() {
        
        if (STOCK.equals(getHeaderImageType())) {
            return stockImage;
        }
        return null;
    }

    public void setStockImage(String stockImage) {
        this.stockImage = stockImage;
    }

    public AssessmentModelAssignment getAssessmentModelAssignment() {
        if (assessmentModelAssignment == null) {
            assessmentModelAssignment = assessmentModelAssignmentHome.findByAssessedItemIdAndPortfolioItemType(Integer
                    .parseInt(getShareId()), PortfolioItemType.PORTFOLIO);
        }
        return assessmentModelAssignment;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public boolean belongsTo(String personId) {
        return getViewerByPersonId(personId).getViewType() == ViewType.OWNER;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
        
        // logService.debug(contents);
        
        ++contentChangeCount;
    }
    
    public int getContentChangeCount() {
    	return contentChangeCount;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Portfolio && ((Portfolio) obj).shareId.equals(shareId);
    }

    @Override
    public int hashCode() {
        return shareId == null ? 0 : shareId.hashCode();
    }
}
