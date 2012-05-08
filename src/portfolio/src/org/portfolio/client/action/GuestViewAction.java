/* $Name:  $ */
/* $Id: GuestViewAction.java,v 1.10 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioSearch;
import org.portfolio.model.Person;
import org.portfolio.model.PortfolioSearchCriteria;
import org.portfolio.model.Portfolio;
import org.portfolio.util.RequiredInjection;

public class GuestViewAction extends BaseAction {

    private PortfolioSearch portfolioSearch;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Person person = getPerson(request);

        PortfolioSearchCriteria criteria = new PortfolioSearchCriteria(person.getPersonId());
        criteria.setSharedPortfoliosOnly(true);
        Set<Portfolio> portfolios = portfolioSearch.findByCriteria(criteria);

        request.setAttribute("folders", portfolios);
        return mapping.findForward("viewList");
    }

    @RequiredInjection
    public void setPortfolioSearch(PortfolioSearch portfolioSearch) {
        this.portfolioSearch = portfolioSearch;
    }
}
