/* $Name:  $ */
/* $Id: PortfolioFlagAction.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.dao.ViewerHome;
import org.portfolio.model.Viewer;

/**
 * @author Matt Sheehan
 * 
 */
public class PortfolioFlagAction extends DispatchAction {

    private ViewerHome viewerHome;
    
    public ActionForward flag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String portfolioIdParam = request.getParameter("portfolioId");
        
        Viewer viewer = viewerHome.findInstance(getPersonId(request), portfolioIdParam);
        viewer.setFlagged(true);
        viewerHome.update(viewer);
        
        return null;
    }

    public ActionForward unflag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String portfolioIdParam = request.getParameter("portfolioId");
        
        Viewer viewer = viewerHome.findInstance(getPersonId(request), portfolioIdParam);
        viewer.setFlagged(false);
        viewerHome.update(viewer);
        
        return null;
    }

    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
    }

}
