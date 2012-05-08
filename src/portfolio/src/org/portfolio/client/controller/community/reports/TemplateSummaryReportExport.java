/* $Name:  $ */
/* $Id: TemplateSummaryReportExport.java,v 1.6 2011/02/09 19:40:32 ajokela Exp $ */
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
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.portfolio.bus.PortfolioManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ViewerHome;
import org.portfolio.dao.template.TemplateElementMappingHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.Viewer;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.community.Community;
import org.portfolio.model.community.CommunityRoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.portfolio.util.LogService;

/**
 * @author Alex C. Jokela <ajokela@d.umn.edu>
 * 
 */
@Controller
public class TemplateSummaryReportExport extends ApplicationController {

	@Autowired private AssessmentManager assessmentManager;
	@Autowired private CommunityManager communityManager;
	@Autowired private PortfolioHome shareDefinitionHome;
	@Autowired private TemplateElementMappingHome templateElementMappingHome;
	@Autowired private PortfolioManager portfolioManager;
	@Autowired private ViewerHome viewerHome;
	@Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    
	private LogService logService = new LogService(this.getClass());
	
	private SummaryObject so;

	private Map<Person, List<Portfolio>> portfoliosToStudent;

	@SuppressWarnings("unused")
	private Map<Template, List<Portfolio>> portfoliosToTemplateMap;
    private Map<Portfolio, List<TemplateElementMapping>> temMap;
    private Map<Portfolio, RowData> portfolioMap;
    private Map<TemplateElementMapping, List<RowData>> shareEntryMap;
    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");
	
    public void templateProgress(String communityId, String group, String dateFromString, String dateToString, String templateId, boolean detailed, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
    	response.setContentType("application/vnd.ms-excel");
        String fileName = "CommunitySummaryReport_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
    	
    	Person person = RequestUtils.getPerson(request);
        
        Community community = communityManager.getCommunityById(communityId);
        
        List<Person> members = communityManager.getMembers(communityId, CommunityRoleType.MEMBER);
        
        if (! communityAuthorizationManager.hasCommunityCoordinatorAccess(person, community.getId())) {
        	
        	members = new ArrayList<Person>();
        	members.add(person);
        	
        }

    	so = new SummaryObject(assessmentManager, shareDefinitionHome, templateElementMappingHome, portfolioManager, viewerHome, communityManager);      
    	so.report(dateFromString, dateToString, group, communityId, members, templateId, request, detailed);

    	portfoliosToStudent = so.getPortfoliosToStudents();
    	portfoliosToTemplateMap = so.getPortfoliosToTemplates();
    	portfolioMap = so.getPortfolioMap();
    	temMap = so.getTemMap();
    	shareEntryMap = so.getShareEntryMap();
    	
    	Workbook wb = createReport();
    	
    	wb.write(response.getOutputStream());
    	    	
    }
    
    //@RequestMapping(method=RequestMethod.GET, value={"/community/reports/templateSummaryReport/{communityid}/{group}/{template}/{dateFromString}/{dateToString}/detailed"})
    @RequestMapping(method=RequestMethod.GET, value={"/community/reports/templateSummaryReportExport/{communityid}/detailed/{group}/{template}/{dateFromString}/{dateToString}/export"})
    public String templateProgressDetailed( @PathVariable("communityid") String communityId,
    										@PathVariable(value="group") String group,
    										@PathVariable(value="dateFromString") String dateFromString,
    										@PathVariable(value="dateToString") String dateToString,
    										@PathVariable(value="template") String templateId,
    							HttpServletRequest request,
    							HttpServletResponse response)  {
    	
    	try {
			templateProgress(communityId, group, dateFromString, dateToString, templateId, true, request, response);
		} catch (ParseException e) {
			
		} catch (IOException e) {
			logService.debug(e);
		}

        return null;
    }

    @RequestMapping(method=RequestMethod.GET, value={"/community/reports/templateSummaryReport/{communityid}/{group}/{template}/{dateFromString}/{dateToString}/summary"})
    public String templateProgressSummary( @PathVariable("communityid") String communityId,
    										@PathVariable(value="group") String group,
    										@PathVariable(value="dateFromString") String dateFromString,
    										@PathVariable(value="dateToString") String dateToString,
    										@PathVariable(value="template") String templateId,
    							HttpServletRequest request,
    							HttpServletResponse response)  {
    	
    	try {
			templateProgress(communityId, group, dateFromString, dateToString, templateId, false, request, response);
		} catch (ParseException e) {
			
		} catch (IOException e) {
			logService.debug(e);
		}

        return null;
    }

    
    private Workbook createReport() {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        int columns = 0;
        final int ROW_ZERO = 0;
        
        final int COLUMN_NAME = 0;
        final int COLUMN_PORT = 1;
        final int COLUMN_DATE = 2;
        final int COLUMN_ELEM = 3;
        final int COLUMN_EVAL = 4;
        final int COLUMN_PREL = 5;
        final int COLUMN_FINL = 6;
        
        int rowIdx = ROW_ZERO;
        
        Row row = sheet.createRow(ROW_ZERO);
        HSSFRichTextString header1;
        
    	header1 = new HSSFRichTextString("Name");
	    header1.applyFont(bold);
	    row.createCell(columns).setCellValue(header1);
	    columns++;

	    header1 = new HSSFRichTextString("Portfolio");
	    header1.applyFont(bold);
	    row.createCell(columns).setCellValue(header1);
	    columns++;

	    header1 = new HSSFRichTextString("Date");
	    header1.applyFont(bold);
	    row.createCell(columns).setCellValue(header1);
	    columns++;

	    if(so.getDetailed()) {
	    
		    header1 = new HSSFRichTextString("Element");
		    header1.applyFont(bold);
		    row.createCell(columns).setCellValue(header1);
		    columns++;
	    
	    }
	    
	    header1 = new HSSFRichTextString("Evaluator");
	    header1.applyFont(bold);
	    row.createCell(columns).setCellValue(header1);
	    columns++;

	    header1 = new HSSFRichTextString("Prelim");
	    header1.applyFont(bold);
	    row.createCell(columns).setCellValue(header1);
	    columns++;
		
	    header1 = new HSSFRichTextString("Final");
	    header1.applyFont(bold);
	    row.createCell(columns).setCellValue(header1);
	    columns++;
	    
	    rowIdx++;

	    Set<Person> keys = portfoliosToStudent.keySet();
	    
	    for(Iterator<Person> i = keys.iterator(); i.hasNext();) {
	    	
	    	Person p = i.next();
	    	
	    	List<Portfolio> portfolios = portfoliosToStudent.get(p);
	    	
	    	for(Iterator<Portfolio> j = portfolios.iterator(); j.hasNext();) {
	    		Portfolio pfolio = j.next();
	    		
	    		if(pfolio.getAssessmentModelAssignment() != null) {
	    			RowData rowData = portfolioMap.get(pfolio);

	    			for(Iterator<Viewer> k = rowData.getEvaluators().iterator(); k.hasNext();) {
	    				Viewer v = k.next();  				

	    				Row dataRow = sheet.createRow(rowIdx);
	    				
	    		    	dataRow.createCell(COLUMN_NAME).setCellValue(p.getDisplayName());
	    	    		dataRow.createCell(COLUMN_PORT).setCellValue(pfolio.getShareName());
	    	    		dataRow.createCell(COLUMN_DATE).setCellValue(pfolio.getDateModified());

	    				dataRow.createCell(COLUMN_EVAL).setCellValue(v.getPerson().getDisplayName());
	    				
	    				Assessment ass = rowData.getPrelimAssessments().get(v.getPerson());
	    				
	    				if(ass != null) {
	    					dataRow.createCell(COLUMN_PREL).setCellValue(ass.getOverallScore());
	    				}
	    				
	    				ass = rowData.getFinalAssessments().get(v.getPerson());

	    				if(ass != null) {
	    					dataRow.createCell(COLUMN_FINL).setCellValue(ass.getOverallScore());
	    				}
	    				
	    				rowIdx++;

	    				//////////////////////////////////////////////////////////////////////////

	    				if(so.getDetailed()) {
		    				
		    				List<TemplateElementMapping> temList = temMap.get(pfolio);
		    				
	    					for(Iterator<TemplateElementMapping> l = temList.iterator(); l.hasNext(); ) {
	    						TemplateElementMapping tem = l.next();
	    						
	    	    				////////////////////////////////////////////////////////////////////////////////////
	    	    				
	    						List<RowData> lrd = shareEntryMap.get(tem);
	    						
	    						for(Iterator<RowData> m = lrd.iterator(); m.hasNext();) {
	    							
	    							RowData rd = m.next();
	    							
	        	    				dataRow = sheet.createRow(rowIdx);
	        	    				
	        	    		    	dataRow.createCell(COLUMN_NAME).setCellValue(p.getDisplayName());
	        	    	    		dataRow.createCell(COLUMN_PORT).setCellValue(pfolio.getShareName());
	        	    	    		dataRow.createCell(COLUMN_DATE).setCellValue(pfolio.getDateModified());
	
	        	    				dataRow.createCell(COLUMN_EVAL).setCellValue(v.getPerson().getDisplayName());
	        	    				
	        	    				ass = rd.getPrelimAssessments().get(v.getPerson());
	        	    				
	        	    				if(ass != null) {
	        	    					dataRow.createCell(COLUMN_PREL).setCellValue(ass.getOverallScore());
	        	    				}
	        	    				
	        	    				ass = rd.getFinalAssessments().get(v.getPerson());
	
	        	    				if(ass != null) {
	        	    					dataRow.createCell(COLUMN_FINL).setCellValue(ass.getOverallScore());
	        	    				}
	        	    				
	        	    				dataRow.createCell(COLUMN_ELEM).setCellValue(tem.getElementTitle());
	        	    				
	        	    				rowIdx++;
	    							
	    						}
	    						
	    					}
	    				
	    				}
	    				
	    			}
	    			
	    		}
	    		
	    	}
	    	
	    }
	    
        for(int i=0; i<columns; i++) {
        	sheet.autoSizeColumn(i);
        }
        
        return wb;
    }

}