/* $Name:  $ */
/* $Id: CreateShareAddTagsAction.java,v 1.6 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.PortfolioTagManager;
import org.portfolio.client.action.BaseAction;

import edu.umn.web.json.JsonSerializer;

/**
 * @author Matt Sheehan
 * 
 */
public class CreateShareAddTagsAction extends BaseAction {

    private PortfolioTagManager tagManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        JsonSerializer jsonSerializer = new JsonSerializer();
        Set<String> tags = tagManager.getTagNamesForPerson(getPersonId(request));
        request.setAttribute("portfolioTags", jsonSerializer.toJsonString(tags));
        
        return mapping.findForward("success");
    }

    public void setTagMgr(PortfolioTagManager tagMgr) {
        this.tagManager = tagMgr;
    }
}
