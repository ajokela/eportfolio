/* $Name:  $ */
/* $Id: SaveQuickShareAction.java,v 1.21 2010/11/04 21:08:53 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.RedirectingActionForward;
import org.portfolio.bus.PortfolioEmailSender;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.TemplateCategory;

public class SaveQuickShareAction extends BaseAction {

    private ShareEntryHome shareEntryHome;
    private PortfolioManager portfolioManager;
    @SuppressWarnings("unused")
	private PortfolioEmailSender portfolioEmailSender;

    @SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        HttpSession session = request.getSession();

        boolean foundEntries = false;
        // set sort order
        List<TemplateCategory> topLevelCats = (List<TemplateCategory>) session.getAttribute("topLevelCats");
        for (TemplateCategory topLevelCat : topLevelCats) {
            for (TemplateCategory subcat : topLevelCat.getSubcategories()) {
                for (ShareEntry entry : subcat.getShareEntries()) {
                    foundEntries = true;
                    setOrder(request, entry);
                }
            }
        }

        // If no entries in the presentation give user an error
        if (!foundEntries) {
            errors.add("selection", new ActionMessage("error.saveShare.noSelections", "selection"));
            saveErrors(request, errors);
            return mapping.findForward("quickShare1");
        }

        getBackPage(request, response);
        
        return saveShare((Portfolio) form, mapping, request, topLevelCats);
    }

    private void setOrder(HttpServletRequest request, ShareEntry entry) {
        String sortOrderParam = "order" + entry.getElementId() + "-" + entry.getEntryIndex();
        String sortOrderString = request.getParameter(sortOrderParam);
        if (sortOrderString != null) {
            entry.setSortOrder(Integer.parseInt(sortOrderString));
        }
    }

    private ActionForward saveShare(Portfolio share, ActionMapping mapping, HttpServletRequest request, List<TemplateCategory> topLevelCats) {
        HttpSession session = request.getSession();

        // save share
        share.setPersonId(getPersonId(request));
        portfolioManager.savePortfolio(share);

        for (TemplateCategory topLevelCat : topLevelCats) {
            for (TemplateCategory subcat : topLevelCat.getSubcategories()) {
                List<ShareEntry> shareEntries = subcat.getShareEntries();
                for (ShareEntry entry : shareEntries) {
                    entry.setShareId(share.getShareId());
                    shareEntryHome.store(entry);
                    // save attachment sort order
                }
                subcat.setShareEntries(shareEntries);
            }
        }

        session.removeAttribute("topLevelCats");
        
        logService.debug("Redirecting to new Portfolio");
        
        return new RedirectingActionForward("/portfolio/" + share.getShareId());
    }

    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }

    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    public void setPortfolioEmailSender(PortfolioEmailSender portfolioEmailSender) {
        this.portfolioEmailSender = portfolioEmailSender;
    }
}
