/* $Name:  $ */
/* $Id: DeleteGuestViewAction.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.dao.ViewerHome;
import org.portfolio.model.Portfolio;

public class DeleteGuestViewAction extends BaseAction {

	private ViewerHome viewerHome;

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String personId = getPersonId(request);
		Portfolio share = (Portfolio) form;
		viewerHome.deleteByPersonIdShareId(personId, share.getShareId());
		return mapping.findForward(FORWARD_SUCCESS);
	}

    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
    }
}
