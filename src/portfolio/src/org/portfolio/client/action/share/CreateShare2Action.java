/* $Name:  $ */
/* $Id: CreateShare2Action.java,v 1.16 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.ElementDataObject;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateCategory;
import org.portfolio.model.TemplateElementMapping;

public class CreateShare2Action extends BaseAction {

    private TemplateHome templateHome;
    private ShareEntryHome shareEntryHome;
    private PortfolioManager portfolioManager;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Portfolio share = (Portfolio) request.getSession().getAttribute("shareOrig");

        Map<String, String[]> elementMap = new HashMap<String, String[]>();
        List<ShareEntry> shareEntries = shareEntryHome.findByShareId(share.getShareId());
        for (ShareEntry shareEntry : shareEntries) {
            String[] strings = elementMap.get(shareEntry.getElementId());
            if (strings == null) {
                elementMap.put(shareEntry.getElementId(), new String[] { shareEntry.getEntryIndex().toString() });
            } else {
                List<String> asList = new ArrayList<String>(Arrays.asList(strings));
                asList.add(shareEntry.getEntryIndex().toString());
                elementMap.put(shareEntry.getElementId(), asList.toArray(new String[0]));
            }
        }
        request.setAttribute("elementMap", elementMap);

        Template template = templateHome.findTemplateById(share.getTemplateId());

        if (template != null) {
            List<TemplateCategory> topLevelCats = portfolioManager.getTopLevelCategoriesForCreate(share, true);

            stripOutEmptyTemplateElementMappings(topLevelCats);

            request.setAttribute("template", template);
            request.setAttribute("topLevelCats", topLevelCats);
        }
        request.getSession().setAttribute("share", share);
        
        getBackPage(request, response);
        
        return mapping.findForward("success");
    }

    private void stripOutEmptyTemplateElementMappings(List<TemplateCategory> topLevelCats) {
        for (Iterator<TemplateCategory> iterator = topLevelCats.iterator(); iterator.hasNext();) {
            TemplateCategory templateCategory = (TemplateCategory) iterator.next();
            List<TemplateElementMapping> templateElementDefs = templateCategory.getTemplateElementDefs();
            for (Iterator<TemplateElementMapping> iterator2 = templateElementDefs.iterator(); iterator2.hasNext();) {
                TemplateElementMapping templateElementMapping = (TemplateElementMapping) iterator2.next();
                Collection<ElementDataObject> instances = templateElementMapping.getInstances();
                if (instances == null || instances.isEmpty()) {
                    iterator2.remove();
                }
            }

        }
    }

    public void setTemplateHome(TemplateHome templateHome) {
        this.templateHome = templateHome;
    }

    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }
}
