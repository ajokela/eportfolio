/* $Name:  $ */
/* $Id: SaveElementSelectionsAction.java,v 1.8 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.RedirectingActionForward;
import org.portfolio.dao.wizard.WizardElementDefinitionHome;
import org.portfolio.dao.wizard.WizardElementInstanceHome;
import org.portfolio.model.wizard.WizardElementDefinition;
import org.portfolio.util.RequiredInjection;

public class SaveElementSelectionsAction extends BaseAction {

    private WizardElementInstanceHome wizardElementInstanceHome;
    private WizardElementDefinitionHome wizardElementDefinitionHome;

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        if (isCancelled(request)) {
            return mapping.findForward(FORWARD_SUCCESS);
        }

        String[] entryIds = getEntryIds(request);
        String wedIdString = request.getParameter("wedId");

        int wedId = Integer.parseInt(wedIdString);
        WizardElementDefinition wed = wizardElementDefinitionHome.findById(wedId);
        for (String entryId : entryIds) {
            wizardElementInstanceHome.insert(wed.getId(), new BigDecimal(entryId), getPersonId(request));
        }
        return new RedirectingActionForward("/guide/" + wed.getWizardId());
    }

    private String[] getEntryIds(HttpServletRequest request) {
        String[] entryIds = null;
        String[] elementChecks = request.getParameterValues("elementCheck");
        if (elementChecks != null) {
            entryIds = new String[elementChecks.length];
            for (int i = 0; i < entryIds.length; i++) {
                entryIds[i] = elementChecks[i].split("_")[1];
            }
        }
        return entryIds;
    }

    @RequiredInjection
    public void setWizardElementInstanceHome(WizardElementInstanceHome wizardElementInstanceHome) {
        this.wizardElementInstanceHome = wizardElementInstanceHome;
    }

    @RequiredInjection
    public void setWizardElementDefinitionHome(WizardElementDefinitionHome wizardElementDefinitionHome) {
        this.wizardElementDefinitionHome = wizardElementDefinitionHome;
    }
}
