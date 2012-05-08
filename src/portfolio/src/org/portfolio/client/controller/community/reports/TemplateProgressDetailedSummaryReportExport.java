/* $Name:  $ */
/* $Id: TemplateProgressDetailedSummaryReportExport.java,v 1.2 2010/12/21 21:10:44 ajokela Exp $ */
package org.portfolio.client.controller.community.reports;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.client.controller.community.reports.TemplateProgressDetailedSummaryReport.AssessmentData;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.model.Person;
import org.portfolio.model.assessment.Assessment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Alex Jokela
 * 
 */

@Controller
public class TemplateProgressDetailedSummaryReportExport extends ApplicationController {

    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");
    @Autowired private CommunityManager communityManager;
    @Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    
    @Autowired private PortfolioHome shareDefinitionHome;
    @Autowired private AssessmentManager assessmentManager;
	

    @RequestMapping("/community/reports/templateProgressDetailedSummaryReport/export/{communityid}/{templateId}/{dateFrom}/{dateTo}")
    public String templateProgressDetailed( @PathVariable("communityid") String communityId,
    										@PathVariable("templateId") String templateId,
    										@PathVariable("dateFrom") String dateFromString,
    										@PathVariable("dateTo") String dateToString,
    							HttpServletRequest request,
    							HttpServletResponse response) throws ParseException, IOException {

    	response.setContentType("application/vnd.ms-excel");
        String fileName = "TemplateProgressDetailedSummaryReport_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
        
        TPDSReport rport = new TPDSReport(communityId, true, request, communityManager, communityAuthorizationManager, shareDefinitionHome, assessmentManager);
    	
    	rport.generate(templateId, dateFromString, dateToString);
    	
        Workbook wb = createReport(rport.getPersonAssessmentMap(), rport.getFinalAssessmentNumber(), rport.getPrelimAssessmentNumber());

        wb.write(response.getOutputStream());
        
        return null;
    }

    private Workbook createReport(Map<Person, AssessmentData> data, int final_assess, int prelim_assess) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        int columns = 0;

        int rowIndex = 0;
        {
            int offset = 0;
            int i=0;
        	Row row = sheet.createRow(rowIndex++);
            HSSFRichTextString header1 = new HSSFRichTextString("Name");
            header1.applyFont(bold);
            row.createCell(offset).setCellValue(header1);
            offset++;
            
            
            for(i=0; i<prelim_assess; i++) {
            
	            HSSFRichTextString header = new HSSFRichTextString("Prelim Assessment " + Integer.toString(i+1));
	            header.applyFont(bold);
	            row.createCell(offset).setCellValue(header);
	            offset++;
	            
            }
            
            for(i=0; i<final_assess; i++) {

            	HSSFRichTextString header = new HSSFRichTextString("Final Assessment " + Integer.toString(i+1));
	            header.applyFont(bold);
	            row.createCell(offset).setCellValue(header);
	            offset++;
	            
            }
         
            columns = offset;
        }
        
        for(Iterator<Person> i=data.keySet().iterator(); i.hasNext();) {
        	int offset = 0;
        	Person p = i.next();
        	AssessmentData d = data.get(p);
        	
            Row row = sheet.createRow(rowIndex++);
            HSSFRichTextString rts = new HSSFRichTextString(p.getDisplayName());            
            row.createCell(offset).setCellValue(rts);
  
            offset++;
            
            for(int j=0; j<prelim_assess; j++) {
            	
            	List<Assessment> ass = d.getPrelimAssessments();
            	Assessment a = null;
            	
            	if(!ass.isEmpty()) {
            		a = ass.get(j);
            	}
            	
            	HSSFRichTextString rt = new HSSFRichTextString(Integer.toString( a != null ? a.getOverallQuantifiedScore() : 0));
            	
            	row.createCell(offset).setCellValue(rt);
            	
            	offset++;
            }
            
            for(int j=0; j<final_assess; j++) {
            	
            	List<Assessment> ass = d.getFinalAssessments();
            	Assessment a = null;
            	
            	if(!ass.isEmpty()) {
            		a = ass.get(j);
            	}

            	HSSFRichTextString rt = new HSSFRichTextString(Integer.toString(a != null ? a.getOverallQuantifiedScore() : 0));
            	
            	row.createCell(offset).setCellValue(rt);
            	
            	offset++;
            }

        }

        
        for(int i=1; i<=columns; i++) {
        	sheet.autoSizeColumn(i);
        }
        
        return wb;
    }
    
    public List<String> processDates(String dateFromString, String dateToString) {
        
    	Date dateFrom = null;
        Date dateTo   = null;
        
        if (dateFromString != null && dateFromString.trim().length() > 0 && !dateFromString.equalsIgnoreCase("none")) {
            try {
                dateFrom = new SimpleDateFormat("MM/dd/yyyy").parse(dateFromString);
            } catch (ParseException e) {
                
            }
        } else {
            dateFrom = null;
        }            

        if (dateToString != null && dateToString.trim().length() > 0 && !dateToString.equalsIgnoreCase("none")) {
            try {
                dateTo = new SimpleDateFormat("MM/dd/yyyy").parse(dateToString);
            } catch (ParseException e) {
                
            }
        } else {
            dateTo = null;
        }            

        if (dateFrom == null) {

        	dateFromString = null;
        }
        	
        if (dateTo == null) {
            
        	dateToString = null;
        }

        List<String> dates =  new ArrayList<String>();
        
        dates.add(dateFromString);
        dates.add(dateToString);
        
        return dates;
    }
    

}
