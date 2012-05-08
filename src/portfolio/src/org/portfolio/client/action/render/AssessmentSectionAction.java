/* $Name:  $ */
/* $Id: AssessmentSectionAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.render;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class AssessmentSectionAction extends BaseAction {

    private PortfolioHome shareDefinitionHome;
    private PortfolioManager portfolioManager;
    private ShareEntryHome shareEntryHome;
    private AssessmentManager assessmentManager;
    
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Person person = getPerson(request);
        
        String shareIdParam = request.getParameter("shareId");
        String shareEntryIdParam = request.getParameter("shareEntryId");

        Object assessmentParent;
        PortfolioItemType portfolioItemType;
        AssessmentModelAssignment assessmentModelAssignment;
        String entryId;

        Portfolio portfolio;

        if (isEmpty(shareEntryIdParam)) {
            portfolioItemType = PortfolioItemType.PORTFOLIO;
            portfolio = shareDefinitionHome.findByShareId(shareIdParam);
            assessmentParent = portfolio;
            assessmentModelAssignment = portfolio.getAssessmentModelAssignment();
            entryId = portfolio.getShareId();
        } else {
            portfolioItemType = PortfolioItemType.PORTFOLIO_ELEMENT;
            ShareEntry shareEntry = shareEntryHome.findByShareEntryId(Integer.parseInt(shareEntryIdParam));
            assessmentParent = shareEntry;
            portfolio = shareDefinitionHome.findByShareId(shareEntry.getShareId());
            assessmentModelAssignment = shareEntry.getAssessmentModelAssignment();
            entryId = shareEntry.getId().toString();
            
        }

        String shareId = portfolio.getShareId();
        List<Assessment> assessmentList = assessmentManager.findAssessmentsByItemIdAndType(Integer.parseInt(entryId), portfolioItemType);
        String viewerRole = portfolioManager.getViewerRole(portfolio, person);
        
        List<Assessment> cleanAssessmentList = new ArrayList<Assessment>();
        
        for(Iterator<Assessment> i = assessmentList.iterator(); i.hasNext();) {
        	Assessment a = i.next();
        	
        	if (a.getEvaluator().equals(person) || person.equals(portfolio.getPerson())) {
        		cleanAssessmentList.add(a);
        	}
        	
        }
        
        
        String ownerId = portfolio.getPersonId();
        boolean hasFinalScore = anyFinal(person, assessmentList);
        
        request.setAttribute("shareId", shareId);
        request.setAttribute("assessmentParent", assessmentParent);
        request.setAttribute("portfolioItemType", portfolioItemType.toString());
        request.setAttribute("assessmentModelAssignment", assessmentModelAssignment);
        request.setAttribute("assessmentList", cleanAssessmentList);
        request.setAttribute("viewerRole", viewerRole);
        request.setAttribute("entryId", entryId);
        request.setAttribute("ownerId", ownerId);
        request.setAttribute("hasFinalScore", hasFinalScore);
        
        return mapping.findForward("success");
    }

    private boolean anyFinal(Person person, List<Assessment> assessmentList) {
        for (Assessment assessment : assessmentList) {
            if (assessment.getEvaluator().equals(person) && assessment.isFinal()) {
                return true;
            }
        }
        return false;
    }

    @RequiredInjection
    public void setShareDefinitionHome(PortfolioHome shareDefinitionHome) {
        this.shareDefinitionHome = shareDefinitionHome;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }

    public void setAssessmentManager(AssessmentManager assessmentManager) {
        this.assessmentManager = assessmentManager;
    }

}
