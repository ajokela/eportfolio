/* $Name:  $ */
/* $Id: PortfolioTagsJsonAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioTagManager;
import org.portfolio.model.PortfolioTag;

/**
 * Action class for Portfolio Tags
 * 
 * @author Vijay Rajagopal
 */
public class PortfolioTagsJsonAction extends BaseAction {

    private PortfolioTagManager portfolioTagManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/json");
        String method = request.getParameter("method");

        BigDecimal shareId = new BigDecimal(request.getParameter("shareId"));
        String tagText = request.getParameter("tag");
        String personId = getPersonId(request);

        if ("add".equals(method)) {
            if (!"".equals(tagText) && !portfolioTagManager.matchingTagExists(personId, shareId, tagText))
                portfolioTagManager.insert(new PortfolioTag(personId, shareId, tagText));
        } else if ("remove".equals(method)) {
            portfolioTagManager.delete(new PortfolioTag(personId, shareId, tagText));
        }

        List<PortfolioTag> tags = portfolioTagManager.getTagsForPersonPortfolio(getPersonId(request), shareId);
        writeJson(tags, response);
        return null;
    }

    public void setPortfolioTagManager(PortfolioTagManager portfolioTagManager) {
        this.portfolioTagManager = portfolioTagManager;
    }
}
