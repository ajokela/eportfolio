/* $Name:  $ */
/* $Id: PortfolioFoldersAction.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.share;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.DispatchAction;
import org.portfolio.dao.SharedPortfolioFolderHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.model.SharedPortfolioFolder;
import org.portfolio.model.Viewer;

/**
 * @author Matt Sheehan
 * 
 */
public class PortfolioFoldersAction extends DispatchAction {

    private ViewerHome viewerHome;
    private SharedPortfolioFolderHome sharedPortfolioFolderHome;

    public ActionForward move(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String[] portfolioIdsParam = request.getParameterValues("portfolioIds");
        String folderIdParam = request.getParameter("folderId");

        SharedPortfolioFolder folder = sharedPortfolioFolderHome.findById(new BigDecimal(folderIdParam));
        String personId = getPersonId(request);

        for (String portfolioId : portfolioIdsParam) {
            Viewer viewer = viewerHome.findInstance(personId, portfolioId);
            viewer.setFolderId(folder.getFolderId());
            viewerHome.update(viewer);
        }
        // TODO move to mgr for transaction safety
        return null;
    }

    public ActionForward unfile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String[] portfolioIdsParam = request.getParameterValues("portfolioIds");
        String personId = getPersonId(request);

        for (String portfolioId : portfolioIdsParam) {
            Viewer viewer = viewerHome.findInstance(personId, portfolioId);
            viewer.setFolderId(null);
            viewerHome.update(viewer);
        }
        // TODO move to mgr for transaction safety
        return null;
    }

    public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/json");
        List<SharedPortfolioFolder> folders = sharedPortfolioFolderHome.findByPersonId(getPersonId(request));
        Collections.sort(folders);

        List<Map<String, String>> jsonResponse = new ArrayList<Map<String, String>>();

        for (SharedPortfolioFolder folder : folders) {
            Map<String, String> entry = new HashMap<String, String>();
            entry.put("name", folder.getFolderName());
            entry.put("id", folder.getFolderId().toString());
            jsonResponse.add(entry);
        }
        writeJson(jsonResponse, response);
        return null;
    }

    public ActionForward newFolder(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/json");
        String nameParam = request.getParameter("name");
        String[] portfolioIdsParam = request.getParameterValues("portfolioIds");
        String personId = getPersonId(request);

        SharedPortfolioFolder folder = new SharedPortfolioFolder();
        folder.setPersonId(personId);
        folder.setFolderName(nameParam);
        sharedPortfolioFolderHome.insert(folder);

        for (String portfolioId : portfolioIdsParam) {
            Viewer viewer = viewerHome.findInstance(personId, portfolioId);
            viewer.setFolderId(folder.getFolderId());
            viewerHome.update(viewer);
        }
        // TODO move to mgr for transaction safety
        writeJson(folder.getFolderId(), response);
        return null;
    }

    public void setViewerHome(ViewerHome viewerHome) {
        this.viewerHome = viewerHome;
    }

    public void setSharedPortfolioFolderHome(SharedPortfolioFolderHome sharedPortfolioFolderHome) {
        this.sharedPortfolioFolderHome = sharedPortfolioFolderHome;
    }
}
