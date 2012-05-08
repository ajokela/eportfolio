/* $Name:  $ */
/* $Id: SaveCreateShareAction.java,v 1.47 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.*;
import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.dao.template.TemplateHome;
import org.portfolio.model.Portfolio;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.util.RequiredInjection;

public class SaveCreateShareAction extends BaseAction {

    private TemplateHome templateHome;
    private PortfolioManager portfolioManager;
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Portfolio share = (Portfolio) form;

        logService.debug("saving share");

        // If on the second step of creating a portfolio (share), save the
        // elements selected based upon the presentation template
        if ("2".equals(request.getParameter("page"))) {
            Map<String, String[]> elementMap = new HashMap<String, String[]>();
            Template template = templateHome.findTemplateById(share.getTemplateId());
            // save mappings of which element entryIds/element instance
            for (TemplateElementMapping element : template.getElements()) {
                String[] values = request.getParameterValues("mapping" + element.getId());
                elementMap.put(element.getId(), values);
            }
            portfolioManager.addNewEntries(share, elementMap);
        }
        getBackPage(request, response);
        
        return saveShare(share, mapping, request);
    }

    protected ActionForward saveShare(Portfolio share, ActionMapping mapping, HttpServletRequest request) {
        String nextStep = request.getParameter("nextStep");
        HttpSession session = request.getSession();
        session.setAttribute("shareOrig", share);
        session.setAttribute("share", share);

        share.setPersonId(getPersonId(request));

        portfolioManager.savePortfolio(share);

        session.setAttribute("share", share);
        session.setAttribute("shareOrig", share);
        request.setAttribute("shareId", share.getShareId());

        if ("finished".equalsIgnoreCase(nextStep) && !isEmpty(share.getShareId())) {
            session.removeAttribute("share");
            session.removeAttribute("shareOrig");
            return new ActionRedirect("/portfolio/" + share.getShareId());
        }
        return mapping.findForward(isEmpty(nextStep) ? "viewShares" : nextStep);
    }

    @RequiredInjection
    public void setTemplateElementFormHome(TemplateElementMappingHome templateElementFormHome) {
    }

    @RequiredInjection
    public void setTemplateHome(TemplateHome templateHome) {
        this.templateHome = templateHome;
    }

    @RequiredInjection
    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
    }

    @RequiredInjection
    public void setDefHome(ElementDefinitionManager defHome) {
    }

    @RequiredInjection
    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

}
