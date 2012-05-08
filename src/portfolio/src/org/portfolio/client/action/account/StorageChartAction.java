/* $Name:  $ */
/* $Id: StorageChartAction.java,v 1.7 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.account;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.bus.ElementDefinitionManager;
import org.portfolio.client.action.BaseAction;
import org.portfolio.dao.FileAccessor;
import org.portfolio.model.Person;
import org.portfolio.util.PortfolioConstants;

/**
 * Returns a chart (png image) showing the free space of the current user.
 * 
 * @author Matt Sheehan
 * 
 */
public class StorageChartAction extends BaseAction {

    private ElementDefinitionManager elementDefinitionManager;

    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        FileAccessor fileAccessor = elementDefinitionManager.findByElementId("020101").getFileAccessor();
        
        Person person = getPerson(request);
        long allocatedFileSpace = person.getMaxStorageSize();
        long availableFileSpace = fileAccessor.getFreeSpace(person);
        long usedFileSpace = allocatedFileSpace - availableFileSpace;

        double fractionUsed = usedFileSpace / (double) allocatedFileSpace;
        int percentUsed = (int) Math.ceil(100 * fractionUsed);
        if (percentUsed == 1) {
            // Makes the graph look better. Can't really see 1%.
            percentUsed = 2;
        }

        int pieParam1 = percentUsed;
        int pieParam2 = 100 - pieParam1;

        String label1 = formatSpaceUsage(usedFileSpace) + " Used";
        String label2 = formatSpaceUsage(availableFileSpace) + " Free";

        String uriString = String.format(
                "http://chart.apis.google.com/chart?cht=p3&chd=t:%s,%s&chs=200x80&chco=FF0000,00FF00&chdl=%s|%s",
                pieParam1,
                pieParam2,
                label1,
                label2);

        HttpMethod method = new GetMethod();
        method.setURI(new URI(uriString, false));
        new HttpClient().executeMethod(method);
        byte[] responseBody = method.getResponseBody();

        response.setContentType("image/png");
        response.getOutputStream().write(responseBody);
        response.getOutputStream().flush();
        return null;
    }

    private static String formatSpaceUsage(long allocatedFileSpace) {
        DecimalFormat df = new DecimalFormat("##0.00");
        if (allocatedFileSpace >= PortfolioConstants.GIGABYTE) {
            return df.format(((double) allocatedFileSpace) / (PortfolioConstants.GIGABYTE)) + " GB";
        } else if (allocatedFileSpace >= PortfolioConstants.MEGABYTE) {
            return df.format(((double) allocatedFileSpace) / (PortfolioConstants.MEGABYTE)) + " MB";
        } else {
            return df.format(((double) allocatedFileSpace) / (PortfolioConstants.KILOBYTE)) + " KB";
        }
    }

    public void setElementDefinitionManager(ElementDefinitionManager elementDefinitionManager) {
        this.elementDefinitionManager = elementDefinitionManager;
    }
}
