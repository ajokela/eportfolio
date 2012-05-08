/* $Name:  $ */
/* $Id: DeletePortfoliosAction.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.model.Portfolio;
import org.portfolio.util.RequiredInjection;

/**
 * @author Matt Sheehan
 * 
 */
public class DeletePortfoliosAction extends BaseAction {

    private PortfolioManager portfolioManager;
    private PortfolioHome shareDefinitionHome;
    private ViewerHome viewerHome;
    

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String[] idParams = request.getParameterValues("ids");
        for (String id : idParams) {
            Portfolio portfolio = shareDefinitionHome.findByShareId(id);
            String personId = getPersonId(request);
            if (portfolio.getPersonId().equals(personId)) {
                portfolioManager.deletePortfolio(id);   
            } else {
                viewerHome.deleteByPersonIdShareId(personId, portfolio.getShareId());
            }
        }
        return null;
    }

    @RequiredInjection
    public void setPortfolioManager(PortfolioManager portfolioManager) {
        this.portfolioManager = portfolioManager;
    }

    @RequiredInjection
    public void setShareDefinitionHome(PortfolioHome shareDefinitionHome) {
        this.shareDefinitionHome = shareDefinitionHome;
    }

    @RequiredInjection
    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
    }
}
