/* $Name:  $ */
/* $Id: CreateShare2aAction.java,v 1.19 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.ElementManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.template.TemplateCategoryHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.util.RequiredInjection;

public class CreateShare2aAction extends BaseAction {

    private TemplateCategoryHome templateCategoryHome;
    private ShareEntryHome shareEntryHome;
    private TemplateElementMappingHome templateElementMappingHome;
    private ElementManager elementManager;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
    	
    	Person person = RequestUtils.getPerson(request);
    	
        Portfolio share = (Portfolio) request.getSession().getAttribute("shareOrig");

        Template template = share.getTemplate();
        List<TemplateCategory> topLevelCats = templateCategoryHome.find(template.getId());
        for (TemplateCategory topLevelCat : topLevelCats) {
            List<TemplateCategory> subcats = topLevelCat.getSubcategories();
            for (TemplateCategory subcat : subcats) {
                // Get share entries ordered by sort_order
                List<ShareEntry> shareEntries = shareEntryHome.findByShareIdCategoryId(share.getShareId(), subcat.getId());
                for (ShareEntry shareEntry : shareEntries) {
                    TemplateElementMapping templateElementMapping = templateElementMappingHome.findById(shareEntry.getElementId());
                    ElementDataObject element = elementManager.findElementInstance(
                            templateElementMapping.getElementDefinition().getId(),
                            share.getPersonId(),
                            shareEntry.getEntryIndex(), false, person.getPersonId());
                    shareEntry.setElement(element);
                    shareEntry.setTemplateElementMapping(templateElementMapping);
                }
                subcat.setShareEntries(shareEntries);
            }
        }

        request.setAttribute("template", template);
        request.setAttribute("topLevelCats", topLevelCats);
        request.getSession().setAttribute("share", share);
        
        getBackPage(request, response);
        
        return mapping.findForward("success");
    }

    @RequiredInjection
    public void setTemplateCategoryHome(TemplateCategoryHome templateCategoryHome) {
        this.templateCategoryHome = templateCategoryHome;
    }

    @RequiredInjection
    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }

    @RequiredInjection
    public void setTemplateElementMappingHome(TemplateElementMappingHome templateElementMappingHome) {
        this.templateElementMappingHome = templateElementMappingHome;
    }

    public void setElementManager(ElementManager elementManager) {
        this.elementManager = elementManager;
    }
}
