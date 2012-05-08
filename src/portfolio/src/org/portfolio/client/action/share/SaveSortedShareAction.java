/* $Name:  $ */
/* $Id: SaveSortedShareAction.java,v 1.19 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.RedirectingActionForward;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.util.LogService;

public class SaveSortedShareAction extends BaseAction {

    private static LogService logService = new LogService(SaveSortedShareAction.class);

    private ShareEntryHome shareEntryHome;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        /*
         * if "finish" then save changes and go back to list if "cancel" then go back to list if "next" then move on to step 4:
         * createShareSelectStyle if "back" (createShare2) then move back to step 2: createShare2
         */
        ActionErrors errors = new ActionErrors();
        Portfolio share = (Portfolio) form;

        String nextStep = request.getParameter("nextStep");

        if (!"createShare2".equals(nextStep)) {
            // update the sort order if "finished" or step 4 or after delete
            try {
                List<ShareEntry> shareEntries = shareEntryHome.findByShareId(share.getShareId());
                for (ShareEntry shareEntry : shareEntries) {
                    String sortOrderString = request.getParameter(shareEntry.getElementId() + ":" + shareEntry.getEntryIndex().toString());
                    shareEntry.setSortOrder(Integer.parseInt(sortOrderString));
                    shareEntryHome.store(shareEntry);
                }
            } catch (Exception e) {
                logService.error(e);
                errors.add("error.general", new ActionMessage("error.general", e.getMessage()));
                saveErrors(request, errors);
                logService.error("Error in getting share entries for share " + share.getShareName());
            }
        }

        if (nextStep.equals("finished")) {
            return new RedirectingActionForward("/portfolio/" + share.getShareId());
        }

        return mapping.findForward(nextStep);
    }

    public void setShareEntryHome(ShareEntryHome shareEntryHome) {
        this.shareEntryHome = shareEntryHome;
    }
}
