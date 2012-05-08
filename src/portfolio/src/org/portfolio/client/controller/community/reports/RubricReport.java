/* $Name:  $ */
/* $Id: RubricReport.java,v 1.3 2011/03/24 19:03:26 ajokela Exp $ */
package org.portfolio.client.controller.community.reports;


import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.portfolio.bus.TemplateManager;
import org.portfolio.bus.assessment.AssessmentManager;
import org.portfolio.bus.assessment.AssessmentModelManager;
import org.portfolio.bus.community.CommunityAuthorizationManager;
import org.portfolio.bus.community.CommunityManager;
import org.portfolio.client.RequestUtils;
import org.portfolio.client.controller.ApplicationController;
import org.portfolio.dao.PortfolioHome;
import org.portfolio.dao.ShareEntryHome;
import org.portfolio.dao.assessment.AssessmentModelAssignmentHome;
import org.portfolio.model.Person;
import org.portfolio.model.Portfolio;
import org.portfolio.model.PortfolioItemType;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Template;
import org.portfolio.model.assessment.AssessedObjective;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.AssessmentModel;
import org.portfolio.model.assessment.AssessmentModelAssignment;
import org.portfolio.model.community.Community;
import org.portfolio.util.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.portfolio.util.DateUtil;

import org.apache.commons.codec.binary.Base64;

@Controller
public class RubricReport extends ApplicationController {
	
    @Autowired private CommunityManager communityManager;
    @Autowired private CommunityAuthorizationManager communityAuthorizationManager;
    
	@Autowired private PortfolioHome portfolioHome;

	@Autowired private AssessmentManager assessmentManager;
	
	@Autowired private AssessmentModelManager assessmentModelManager;
	
	@Autowired private AssessmentModelAssignmentHome assessmentModelAssignmentHome;
	
	@Autowired private ShareEntryHome shareEntryHome;
	
	@Autowired private TemplateManager templateManager;
	
	private LogService logService = new LogService(this.getClass());
    
	private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");
	
	@RequestMapping("/community/reports/rubric/export/{communityid}/{rubric}/{template}/{dateFromString}/{dateToString}")
	public void exportRubricReport( @PathVariable("communityid") String communityId,
			@PathVariable("rubric") String rubricId,
			@PathVariable("template") String templateId,
			@PathVariable("dateFromString") String dateFromString,
			@PathVariable("dateToString") String dateToString,
			HttpServletRequest request,
			HttpServletResponse response) throws ParseException, IOException {
		
		if(dateFromString != null && dateToString != null) {
			
			byte[] dateFrom = Base64.decodeBase64(dateFromString);
			byte[] dateTo   = Base64.decodeBase64(dateToString);
			
			dateFromString = new String(dateFrom);
			dateToString   = new String(dateTo);

		}
 		
		if(dateFromString.contentEquals("-1")) {
			dateFromString = null;
		}
		
		if(dateToString.contentEquals("-1")) {
			dateToString = null;
		}
		
    	Person person = RequestUtils.getPerson(request);
		Community community = communityManager.getCommunityById(communityId);
    	
    	if (!communityAuthorizationManager.hasAssessmentCoordinatorAccess(person, community.getId())) {
    		// return "redirect:/community/" + communityId;
    	} else {
	    	
	    	//List<AssessmentModel> rubrics = communityManager.getCommunityAssessmentModels(communityId, "rubric");
	    	//List<Template>communityTemplates = communityManager.getAssessableCommunityTemplates(communityId);
	
			Map<String, Object>returnValues = buildReport(communityId, rubricId, "true", dateFromString, dateToString, templateId);
	    	
	    	if(rubricId != null && templateId != null) {
	    		
	    		if((Integer)returnValues.get("rId") > -1 && (Integer)returnValues.get("tId") > -1) {
	    			
	    			response.setContentType("application/vnd.ms-excel");
	    	        String fileName = "RubricReport_" + dateFormat.format(new Date()) + ".xls";
	    	        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");
	    	        
	    	        Workbook wb = createReport(returnValues);
	    	        
	    	        wb.write(response.getOutputStream());
	    			
					/*
					request.setAttribute("report", "true");
					
					request.setAttribute("rubric_id", (Integer)returnValues.get("rId"));
			    	request.setAttribute("template_id", (Integer)returnValues.get("tId"));
			
					request.setAttribute("global_portfolio_pMap", (Integer[][])returnValues.get("global_portfolio_pMap"));
					request.setAttribute("global_portfolio_fMap", (Integer[][])returnValues.get("global_portfolio_fMap"));
					
					request.setAttribute("portfolios", (List<Portfolio>)returnValues.get("portfolios"));
					request.setAttribute("portfolios_count", (Integer)returnValues.get("portfolios_count"));
					request.setAttribute("portfolios_without_count", (Integer)returnValues.get("portfolios_without_count"));
					request.setAttribute("portfolios_with_prelim_count", (Integer)returnValues.get("portfolios_with_prelim_count"));
					request.setAttribute("portfolios_with_final_count", (Integer)returnValues.get("portfolios_with_final_count"));
					request.setAttribute("portfolios_with_only_prelim_count", (Integer)returnValues.get("portfolios_with_only_prelim_count"));
					request.setAttribute("portfolios_with_prelim_and_final_count", (Integer)returnValues.get("portfolios_with_prelim_and_final_count"));
					request.setAttribute("assessedObjectivesScoresPrelimFinalPortfoliosMap", (Map<AssessedObjective, Map<String, Map<String, Map<Portfolio, Integer>>>>)returnValues.get("assessedObjectivesScoresPrelimFinalPortfoliosMap"));
					request.setAttribute("assessmentModel", (AssessmentModel)returnValues.get("assessmentModel"));
					request.setAttribute("objectives", (List<AssessedObjective>)returnValues.get("objectives"));
					request.setAttribute("assessmentModelCount", (Integer)returnValues.get("assessmentModelCount"));
					
					request.setAttribute("portfolioAssessmentsMap", (Map<Portfolio, Map<String, Integer[][]>>)returnValues.get("portfolioAssessmentsMap"));
	    			*/
	    		}
	    		
	    	}
	    	/*
	    	request.setAttribute("rubrics", rubrics);
	    	request.setAttribute("templates", communityTemplates);
	    	request.setAttribute("community", community);
	    	*/
	    	
    	}
        
	}
	
	private Workbook createReport(Map<String, Object>returnValues) {
		HSSFWorkbook wb = new HSSFWorkbook();
        
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
        
        CellStyle hlink_style = wb.createCellStyle();
        Font hlink_font = wb.createFont();
        
        hlink_font.setUnderline(HSSFFont.U_SINGLE);
        hlink_font.setColor(HSSFColor.BLUE.index);
        hlink_style.setFont(hlink_font);
		
        
        final int ROW_ZERO = 0;
        final int COLUMN_ZERO = 0;
        final int COLUMN_ONE  = 1;
        
        int columns = COLUMN_ZERO;
        int rowIdx = ROW_ZERO;
        
        Row row = sheet.createRow(rowIdx);
        HSSFRichTextString header1;
        
        Template template = (Template)returnValues.get("template");
        AssessmentModel rubric = (AssessmentModel)returnValues.get("rubric");
        
    	header1 = new HSSFRichTextString("Template: " + template.getName());
	    header1.applyFont(bold);
	    row.createCell(COLUMN_ZERO).setCellValue(header1);
	    ++rowIdx;
	    
	    row = sheet.createRow(rowIdx);	    
    	header1 = new HSSFRichTextString("Rubric: " + rubric.getName());
	    header1.applyFont(bold);
	    row.createCell(COLUMN_ZERO).setCellValue(header1);
	    ++rowIdx;
	    ++rowIdx;
	    
	    row = sheet.createRow(rowIdx);
	    header1 = new HSSFRichTextString("Portfolios Matching Rubric & Template:");
	    row.createCell(COLUMN_ZERO).setCellValue(header1);
	    
	    columns++;
	    
	    row.createCell(COLUMN_ONE).setCellValue((Integer)returnValues.get("portfolios_count"));
	    ++rowIdx;
	    
	    row = sheet.createRow(rowIdx);
	    
	    header1 = new HSSFRichTextString("Portfolios with Preliminary and/or Final:");
	    
	    Cell cell = row.createCell(COLUMN_ZERO);
	    cell.setCellValue(header1);
	    
	    cell = row.createCell(COLUMN_ONE);
	    cell.setCellValue((Integer)returnValues.get("portfolios_with_prelim_and_final_count"));
	    
	    ++rowIdx;
	    row = sheet.createRow(rowIdx);
	    cell = row.createCell(COLUMN_ZERO);
	    header1 = new HSSFRichTextString("Portfolios with Preliminary:");
	    cell.setCellValue(header1);
	    cell = row.createCell(COLUMN_ONE);
	    cell.setCellValue((Integer)returnValues.get("portfolios_with_prelim_count"));

	    ++rowIdx;
	    row = sheet.createRow(rowIdx);
	    cell = row.createCell(COLUMN_ZERO);
	    header1 = new HSSFRichTextString("Portfolios with Final:");
	    cell.setCellValue(header1);
	    cell = row.createCell(COLUMN_ONE);
	    cell.setCellValue((Integer)returnValues.get("portfolios_with_final_count"));

	    ++rowIdx;
	    row = sheet.createRow(rowIdx);
	    cell = row.createCell(COLUMN_ZERO);
	    header1 = new HSSFRichTextString("Portfolios with Only Preliminary:");
	    cell.setCellValue(header1);
	    cell = row.createCell(COLUMN_ONE);
	    cell.setCellValue((Integer)returnValues.get("portfolios_with_only_prelim_count"));
	    
	    ++rowIdx;
	    ++rowIdx;
	    
	    row = sheet.createRow(rowIdx);
	    cell = row.createCell(COLUMN_ZERO);
	    
	    header1 = new HSSFRichTextString("Assessments Summary (Element Counts)");
	    header1.applyFont(bold);
	    cell.setCellValue(header1);
	    
	    HSSFCellStyle columnHeaderStyle =  wb.createCellStyle();
	    columnHeaderStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	    
	    int columnIdx = COLUMN_ONE;
	    
	    Row row2 = sheet.createRow(rowIdx+1);
	    
	    for(int i=0; i<rubric.getScoringModel().getScores().length; ++i) {
	    	
	    	CellRangeAddress region = new CellRangeAddress( rowIdx, rowIdx, columnIdx, columnIdx+1);
	    	sheet.addMergedRegion(region);
	    	
	    	header1 = new HSSFRichTextString(rubric.getScoringModel().getScores()[i]);
	    	header1.applyFont(bold);
	    	
	    	cell = row.createCell(columnIdx);
	    	cell.setCellValue(header1);
	    	cell.setCellStyle(columnHeaderStyle);
	    	
	    	Cell cell2 = row2.createCell(columnIdx);
	    	header1 = new HSSFRichTextString("Prelim");
	    	cell2.setCellValue(header1);
	    	cell2.setCellStyle(columnHeaderStyle);

	    	cell2 = row2.createCell(columnIdx+1);
	    	header1 = new HSSFRichTextString("Final");
	    	cell2.setCellValue(header1);
	    	cell2.setCellStyle(columnHeaderStyle);

	    	
	    	columnIdx += 2;
	    }
	    
	    ++rowIdx;
	    ++rowIdx;
	    columnIdx = COLUMN_ONE;
	    
	    @SuppressWarnings("unchecked")
		List<AssessedObjective>objectives = (List<AssessedObjective>)returnValues.get("objectives");
	    
	    Integer[][] global_portfolio_pMap = (Integer[][])returnValues.get("global_portfolio_pMap");
	    Integer[][] global_portfolio_fMap = (Integer[][])returnValues.get("global_portfolio_fMap");
	    
	    @SuppressWarnings("unchecked")
		Map<Portfolio, Map<String, Integer[][]>>portfolioAssessmentsMap = (Map<Portfolio, Map<String, Integer[][]>>)returnValues.get("portfolioAssessmentsMap");
	    
	    Integer z = 0;
	    
	    for(Iterator<AssessedObjective> i=objectives.iterator(); i.hasNext();) {
	    	AssessedObjective objective = i.next();
	    	
	    	row = sheet.createRow(rowIdx);
	    	header1 = new HSSFRichTextString(objective.getName());
	    	cell = row.createCell(COLUMN_ZERO);
	    	cell.setCellValue(header1);
	    	
	    	for(int j=0; j<rubric.getScoringModel().getScores().length; ++j) {
	    		
	    		cell = row.createCell(columnIdx);
	    		cell.setCellValue(global_portfolio_pMap[z][j]);
	    		
	    		cell = row.createCell(columnIdx+1);
	    		cell.setCellValue(global_portfolio_fMap[z][j]);
	    		
	    		columnIdx += 2;
	    	}
	    	
	    	columnIdx=1;
	    	++rowIdx;
	    	++z;
	    }
	    
	    ++rowIdx;
	    ++rowIdx;
	    
	    for(Iterator<Portfolio>p = portfolioAssessmentsMap.keySet().iterator(); p.hasNext();) {
	    	Portfolio portfolio = p.next();
	    	Map<String, Integer[][]>assessmentsMap = portfolioAssessmentsMap.get(portfolio);
	    	
	    	row = sheet.createRow(rowIdx);
	    	header1 = new HSSFRichTextString(portfolio.getPerson().getFullName() + " (" + portfolio.getShareName() + ")");
	    	cell = row.createCell(COLUMN_ZERO);
	    	header1.applyFont(bold);
	    	cell.setCellValue(header1);
	    	
	    	columnIdx = COLUMN_ONE;
	    	row2 = sheet.createRow(rowIdx+1);
	    	
		    for(int i=0; i<rubric.getScoringModel().getScores().length; ++i) {
		    	
		    	CellRangeAddress region = new CellRangeAddress( rowIdx, rowIdx, columnIdx, columnIdx+1);
		    	sheet.addMergedRegion(region);
		    	
		    	header1 = new HSSFRichTextString(rubric.getScoringModel().getScores()[i]);
		    	header1.applyFont(bold);
		    	
		    	cell = row.createCell(columnIdx);
		    	cell.setCellValue(header1);
		    	cell.setCellStyle(columnHeaderStyle);
		    	
		    	Cell cell2 = row2.createCell(columnIdx);
		    	header1 = new HSSFRichTextString("Prelim");
		    	cell2.setCellValue(header1);
		    	cell2.setCellStyle(columnHeaderStyle);

		    	cell2 = row2.createCell(columnIdx+1);
		    	header1 = new HSSFRichTextString("Final");
		    	cell2.setCellValue(header1);
		    	cell2.setCellStyle(columnHeaderStyle);
		    	
		    	columnIdx += 2;
		    }
	    	
	    	++rowIdx;
	    	++rowIdx;
	    	
		    z = 0;
		    
		    columnIdx = COLUMN_ONE;
		    
		    for(Iterator<AssessedObjective> i=objectives.iterator(); i.hasNext();) {
		    	AssessedObjective objective = i.next();
		    	
		    	row = sheet.createRow(rowIdx);
		    	header1 = new HSSFRichTextString(objective.getName());
		    	cell = row.createCell(COLUMN_ZERO);
		    	cell.setCellValue(header1);
		    	
		    	Integer[][] prelim = assessmentsMap.get("prelim");
		    	Integer[][] finale = assessmentsMap.get("final");
		    	
		    	for(int j=0; j<rubric.getScoringModel().getScores().length; ++j) {
		    		
		    		cell = row.createCell(columnIdx);
		    		cell.setCellValue(prelim[z][j]);
		    		
		    		cell = row.createCell(columnIdx+1);
		    		cell.setCellValue(finale[z][j]);
		    		
		    		columnIdx += 2;
		    	}
		    	
		    	columnIdx=COLUMN_ONE;
		    	++rowIdx;
		    	++z;
		    }

	    	
	    	++rowIdx;
	    	CellRangeAddress region = new CellRangeAddress( rowIdx, rowIdx, COLUMN_ZERO, rubric.getScoringModel().getScores().length);
	    	sheet.addMergedRegion(region);
	    	
	    	String url = "http://portfolio.umn.edu/portfolio/" + portfolio.getShareId();
	    	
	    	cell = sheet.createRow(rowIdx).createCell(COLUMN_ZERO);
	        cell.setCellValue(url);
	        HSSFHyperlink link = new HSSFHyperlink(HSSFHyperlink.LINK_URL);
	        link.setAddress(url);
	        cell.setHyperlink(link);
	        cell.setCellStyle(hlink_style);
	    	
	    	++rowIdx;
	    	++rowIdx;
	    }
	    
	    
	    for(int i=0; i<columns; ++i) {
        	sheet.autoSizeColumn(i);
        }
	    
        return wb;
	}
	
    @SuppressWarnings("unchecked")
	@RequestMapping("/community/reports/rubric/{communityid}")
    public String rubricReport( @PathVariable("communityid") String communityId,
								@RequestParam(value="rubric", required=false) String rubricId,
								@RequestParam(value="report", required=false) String report,
								@RequestParam(value="dateFromString", required=false) String dateFromString,
								@RequestParam(value="dateToString", required=false) String dateToString,
								@RequestParam(value="template", required=false) String templateId,
    							HttpServletRequest request
    							) throws ParseException {

    	Person person = RequestUtils.getPerson(request);
		Community community = communityManager.getCommunityById(communityId);
    	
    	if (!communityAuthorizationManager.hasAssessmentCoordinatorAccess(person, community.getId())) {
    		return "redirect:/community/" + communityId;
    	}
    	
    	List<AssessmentModel> rubrics = communityManager.getCommunityAssessmentModels(communityId, "rubric");
    	List<Template>communityTemplates = communityManager.getAssessableCommunityTemplates(communityId);

		Map<String, Object>returnValues = buildReport(communityId, rubricId, report, dateFromString, dateToString, templateId);
    	
    	if(rubricId != null && templateId != null) {
    		
    		if((Integer)returnValues.get("rId") > -1 && (Integer)returnValues.get("tId") > -1) {
				
    			if(dateToString == null || dateToString.contentEquals("None")) {
    				dateToString = "-1";
    			}
    			
    			if(dateFromString == null || dateFromString.contentEquals("None")) {
    				dateFromString = "-1";
    			}
    			
    			request.setAttribute("dateFromString", dateFromString);
    			request.setAttribute("dateToString", dateToString);
    			
				request.setAttribute("report", "true");
				
				request.setAttribute("rubric_id", (Integer)returnValues.get("rId"));
		    	request.setAttribute("template_id", (Integer)returnValues.get("tId"));
		
				request.setAttribute("global_portfolio_pMap", (Integer[][])returnValues.get("global_portfolio_pMap"));
				request.setAttribute("global_portfolio_fMap", (Integer[][])returnValues.get("global_portfolio_fMap"));
				
				request.setAttribute("portfolios", (List<Portfolio>)returnValues.get("portfolios"));
				request.setAttribute("portfolios_count", (Integer)returnValues.get("portfolios_count"));
				request.setAttribute("portfolios_without_count", (Integer)returnValues.get("portfolios_without_count"));
				request.setAttribute("portfolios_with_prelim_count", (Integer)returnValues.get("portfolios_with_prelim_count"));
				request.setAttribute("portfolios_with_final_count", (Integer)returnValues.get("portfolios_with_final_count"));
				request.setAttribute("portfolios_with_only_prelim_count", (Integer)returnValues.get("portfolios_with_only_prelim_count"));
				request.setAttribute("portfolios_with_prelim_and_final_count", (Integer)returnValues.get("portfolios_with_prelim_and_final_count"));
				request.setAttribute("assessedObjectivesScoresPrelimFinalPortfoliosMap", (Map<AssessedObjective, Map<String, Map<String, Map<Portfolio, Integer>>>>)returnValues.get("assessedObjectivesScoresPrelimFinalPortfoliosMap"));
				request.setAttribute("assessmentModel", (AssessmentModel)returnValues.get("assessmentModel"));
				request.setAttribute("objectives", (List<AssessedObjective>)returnValues.get("objectives"));
				request.setAttribute("assessmentModelCount", (Integer)returnValues.get("assessmentModelCount"));
				
				request.setAttribute("portfolioAssessmentsMap", (Map<Portfolio, Map<String, Integer[][]>>)returnValues.get("portfolioAssessmentsMap"));
    		
    		}
    	}
    	
    	request.setAttribute("rubrics", rubrics);
    	request.setAttribute("templates", communityTemplates);
    	request.setAttribute("community", community);
    	
    	
    	return "community/report/rubric";
    }

    private Map<String, Object>buildReport(String communityId, String rubricId, String report, String dateFromString, String dateToString, String templateId) throws ParseException {
    	
    	Integer rId = -1;
    	Integer tId = -1;
    	
    	Map<String, Object>returnValues = new LinkedHashMap<String, Object>();
    	
    	if(rubricId != null) {
    		
    		try {
    			rId = Integer.parseInt(rubricId);
    		}
    		catch (Exception e) {
    			logService.debug(e);
    		}
    		
    	}
    	
    	if(templateId != null) {
    		try {
    			tId = Integer.parseInt(templateId);
    		}
    		catch (Exception e) {
    			logService.debug(e);
    		}
    	}
    	
    	returnValues.put("rId", rId);
    	returnValues.put("tId", tId);
    	
    	logService.debug("====> TemplateId: " + tId + " | rubricId: " + rId );
    	
    	if(rId > -1 && tId > -1) {
    		
    		Map<Template, String>templates = new HashMap<Template, String>();
    		
    		Map<AssessedObjective, Map<String, Map<String, Map<Portfolio, Integer>>>>assessedObjectivesScoresPrelimFinalPortfoliosMap = new LinkedHashMap<AssessedObjective, Map<String, Map<String, Map<Portfolio, Integer>>>>();
    		
    		List<String> dates = new ArrayList<String>();
    		
    		if(dateFromString != null && dateToString != null) {
    			
    			if(dateFromString.contentEquals("-1")) {
    				dateFromString = "None";
    			}
    			
    			if(dateToString.contentEquals("-1")) {
    				dateToString = "None";
    			}
    			
    			dates = DateUtil.processDates(dateFromString, dateToString);
    		}
    		else {
    			dates.add(null);
    			dates.add(null);
    		}
    		
    		
    		AssessmentModel assessmentModel = assessmentModelManager.getAssessmentModelById(rId);
    		Template template = templateManager.getTemplateById(templateId);
    		
    		List<AssessmentModelAssignment> assessmentModelAssignments = assessmentModelAssignmentHome.findByAssessmentModel(assessmentModel, PortfolioItemType.PORTFOLIO);
    		
    		List<Portfolio> portfolios = new ArrayList<Portfolio>();
    		
    		for(Iterator<AssessmentModelAssignment>i=assessmentModelAssignments.iterator(); i.hasNext();) {
    			AssessmentModelAssignment ama = i.next();
    			Portfolio p = portfolioHome.findByShareId(String.valueOf(ama.getAssessedItemId()));
    			
    			if(p != null) {
    				portfolios.add(p);
    			}
    			
    		}
    		
    		List<Portfolio> pfolios = portfolioHome.findByAssessmentModelId(rId, dates.get(0), dates.get(1));
    		List<Portfolio> pf_elements = portfolioHome.findByAssessmentModelAndTypeId(rId, PortfolioItemType.PORTFOLIO_ELEMENT, dates.get(0), dates.get(1));
    		
    		portfolios.addAll(pfolios);
    		portfolios.addAll(pf_elements);
    		
    		logService.debug("===> Found " + portfolios.size() + " portfolios (PreCleaning)");
    		
    		List<Portfolio>cleanP = new ArrayList<Portfolio>();
    		
    		for(Iterator<Portfolio> i = portfolios.iterator(); i.hasNext();) {
    			Portfolio p = i.next();
    			
    			if(p.getTemplateId().contentEquals(templateId)) {
    				logService.debug("Template Match Found - " + p.getShareName() + "(" + p.getShareId() + ")");
    				cleanP.add(p);
    			}
    			else {
    				// logService.debug("Template Mismatch Found - " + p.getShareName() + "(" + p.getShareId() + ") - " + p.getTemplateId() + " v. " + tId);
    			}
    		}
    		
    		logService.debug("===> Portfolios list has been cleaned...");
    		
    		portfolios.clear();
    		portfolios.addAll(cleanP);
    		
    		Map<String, Integer>uniquePortfolios = new LinkedHashMap<String, Integer>();
    		Map<Portfolio, Map<String, Integer[][]>>portfolioAssessmentsMap = new LinkedHashMap<Portfolio, Map<String, Integer[][]>>();
    		
    		for(Iterator<Portfolio>m=portfolios.iterator(); m.hasNext();) {
    			Portfolio p = m.next();
    			
    			if(uniquePortfolios.get(p.getShareId()) == null) {
    				uniquePortfolios.put(p.getShareId(), 1);
    			}
    			
    		}
    		
    		Integer portfolios_count = uniquePortfolios.size();
    		
    		logService.debug("===> portfolios_count: " + portfolios_count);
    		
    		portfolios.clear();

    		for(Iterator<String>s = uniquePortfolios.keySet().iterator(); s.hasNext();) {
    			String str = s.next();
    			Portfolio p = portfolioHome.findByShareId(str);
    			portfolios.add(p);
    		}
    		
    		Collections.sort(portfolios, Portfolio.OWNER_LASTNAME);
    		
    		Integer assessmentModelCount = assessmentModel.getScoringModel().getScores().length;
    		
    		List<AssessedObjective> objectives = assessmentModel.getAssessedObjectives();
    		
    		Map<String, Integer>posMap = new LinkedHashMap<String, Integer>();
    		
    		String[] prelimFinal = new String[2];
    		prelimFinal[0] = "prelim";
    		prelimFinal[1] = "final";
    		
    		logService.debug("===> Found " + objectives.size() + " Assessed Objectives...");
    		logService.debug("===> Found " + portfolios.size() + " portfolios");
    		
    		for(Integer z=0; z<objectives.size(); ++z) {
	    		
    			Map<String, Map<String, Map<Portfolio, Integer>>>scoresPrelimFinalPortfoliosMap = new LinkedHashMap<String, Map<String, Map<Portfolio, Integer>>>();
    			
	    		for(int j=0; j<assessmentModel.getScoringModel().getScores().length; ++j) {
	    			Map<String, Map<Portfolio, Integer>> prelimFinalPortfoliosMap = new LinkedHashMap<String, Map<Portfolio, Integer>>();
	    			
	    			posMap.put(assessmentModel.getScoringModel().getScores()[j], j);
	    			
	    			for(int q=0; q<prelimFinal.length; ++q) {
	    				Map<Portfolio, Integer>portfoliosMap = new LinkedHashMap<Portfolio, Integer>();
	    				prelimFinalPortfoliosMap.put(prelimFinal[q], portfoliosMap);
	    			}
	    			
	    			scoresPrelimFinalPortfoliosMap.put(assessmentModel.getScoringModel().getScores()[j], prelimFinalPortfoliosMap);
	    		}
	    		
	    		assessedObjectivesScoresPrelimFinalPortfoliosMap.put(objectives.get(z), scoresPrelimFinalPortfoliosMap);
    		}
    		
    		Integer portfolios_without_count = 0;
    		Integer portfolios_with_final_count = 0;
    		Integer portfolios_with_prelim_count = 0;
    		Integer portfolios_with_prelim_and_final_count = 0;
    		Integer portfolios_with_only_prelim_count = 0;
    		
            Integer[][] global_portfolio_fMap = new Integer[objectives.size()][];
            Integer[][] global_portfolio_pMap = new Integer[objectives.size()][];
            
            for(Integer z=0; z<objectives.size(); ++z) {
                
                global_portfolio_fMap[z] = new Integer[assessmentModel.getScoringModel().getScores().length];
                global_portfolio_pMap[z] = new Integer[assessmentModel.getScoringModel().getScores().length];
                
                for(int y=0; y<assessmentModel.getScoringModel().getScores().length; ++y) {
                    global_portfolio_fMap[z][y] = 0;
                    global_portfolio_pMap[z][y] = 0;
                }
                
            }

    		
    		for(Iterator<Portfolio> i = portfolios.iterator(); i.hasNext();) {
    			Portfolio p = i.next();
    			List<Assessment> assessments = new ArrayList<Assessment>();
    			
    			boolean has_assessment = false;
    			
    			if(p.getTemplate()!= null && !p.getTemplate().isDeleted()) {
	    			
	    			templates.put(p.getTemplate(), "true");
	    			
	    			assessments = assessmentManager.findLatestPortfolioAssessments(p.getShareId(), dates.get(0), dates.get(1));

	    			if(portfolioAssessmentsMap.get(p) == null) {
	    				
	    				portfolioAssessmentsMap.put(p, null);
	    				
	    			}
	    			
	                Integer[][] portfolio_fMap = new Integer[objectives.size()][];
	                Integer[][] portfolio_pMap = new Integer[objectives.size()][];
	                
	                for(Integer z=0; z<objectives.size(); ++z) {
	                    
	                    portfolio_fMap[z] = new Integer[assessmentModel.getScoringModel().getScores().length];
	                    portfolio_pMap[z] = new Integer[assessmentModel.getScoringModel().getScores().length];
	                    
	                    for(int y=0; y<assessmentModel.getScoringModel().getScores().length; ++y) {
	                        portfolio_fMap[z][y] = 0;
	                        portfolio_pMap[z][y] = 0;
	                    }
	                    
	                }
	                

	                for(Iterator<Assessment>x=assessments.iterator(); x.hasNext();) {
	                	Assessment a = x.next();
	                	
	                	if(a.getId().compareTo(rId) == 0) {
		                	
		                	String scores = "";
		                	has_assessment = true;
		                	
		                	for(int o=0; o<a.getScores().length; o++) {
		                		scores += a.getScores()[o] + (o == a.getScores().length -1 ? "" : ", ");
		                		
		                		AssessedObjective objective = objectives.get(o);
		                		Map<String, Map<String, Map<Portfolio, Integer>>>scoresPrelimFinalPortfoliosMap = assessedObjectivesScoresPrelimFinalPortfoliosMap.get(objective);
		                		Map<String, Map<Portfolio, Integer>>prelimFinalPortfoliosMap = scoresPrelimFinalPortfoliosMap.get(a.getScores()[o]);
		                		
		                		Map<Portfolio, Integer>portfoliosMap;
		                		
		                		if(a.isFinal()) {
		                			// portfolio_fMap[Objective (Vertical)][Score Position (Horizontal)]
		                			portfolio_fMap[o][posMap.get(a.getScores()[o])]++;
		                			global_portfolio_fMap[o][posMap.get(a.getScores()[o])]++;
		                			
		                			portfoliosMap = prelimFinalPortfoliosMap.get("final");
		                		}
		                		else {
		                			portfolio_pMap[o][posMap.get(a.getScores()[o])]++;
		                			global_portfolio_pMap[o][posMap.get(a.getScores()[o])]++;
		                			
		                			portfoliosMap = prelimFinalPortfoliosMap.get("prelim");
		                		}
		                		
		                		
		                		portfoliosMap.put(p, 1);
		                		
		                	}
		                	
		                	logService.debug("===> " + p.getShareId() + " : " + p.getPerson().getFullName() + " : " + scores);
	                	
	                	}
	                	else {
	                		logService.debug("====> Rubric ID Mismatch: " + a.getId() + " v. " + rId);
	                	}
	                	
	                }
	    			
	    			// assessments.addAll(pAssessments);
	    			
	    			List<ShareEntry> portfolioElements = shareEntryHome.findByShareId(p.getShareId());
	    			
	                for(Iterator<ShareEntry> j = portfolioElements.iterator(); j.hasNext();) {
	                	
	                	ShareEntry shareEntry = j.next();
	                	
	                	logService.debug("shareEntry.getAssessmentList().size(): " + (shareEntry.getAssessmentList() == null ? 0 : shareEntry.getAssessmentList().size()) );
	                	
	                	if(shareEntry.getAssessmentModelAssignment() != null) {
		                	
		                	AssessmentModel am = shareEntry.getAssessmentModelAssignment().getAssessmentModel();
		                	
		                	if(am.getId().compareTo(rId) == 0) {
		                		has_assessment = true;
		                		
			                	List<Assessment> as = assessmentManager.findLatestPortfolioElementAssessments(shareEntry.getId(), dates.get(0), dates.get(1));
			                	// assessments.addAll(as);
			                	
			                	for(Iterator<Assessment>w=as.iterator(); w.hasNext();) {
			                		Assessment a = w.next();
			                		// assessmentShareEntryMap.put(ass, shareEntry);
			                		
				                	String scores = "";
				                	
				                	for(int o=0; o<a.getScores().length; o++) {
				                		scores += a.getScores()[o] + (o == a.getScores().length -1 ? "" : ", ");
				                		
				                		AssessedObjective objective = objectives.get(o);
				                		Map<String, Map<String, Map<Portfolio, Integer>>>scoresPrelimFinalPortfoliosMap = assessedObjectivesScoresPrelimFinalPortfoliosMap.get(objective);
				                		Map<String, Map<Portfolio, Integer>>prelimFinalPortfoliosMap;
				                		
				                		String token = "prelim";
				                		
				                		if(a.isFinal()) {
				                			// portfolio_fMap[Objective (Vertical)][Score Position (Horizontal)]
				                			portfolio_fMap[o][posMap.get(a.getScores()[o])]++;
				                			global_portfolio_fMap[o][posMap.get(a.getScores()[o])]++;
				                			
				                			prelimFinalPortfoliosMap = scoresPrelimFinalPortfoliosMap.get(a.getScores()[o]);
				                			token = "final";
				                			
				                		}
				                		else {
				                			portfolio_pMap[o][posMap.get(a.getScores()[o])]++;
				                			global_portfolio_pMap[o][posMap.get(a.getScores()[o])]++;
				                			
				                			prelimFinalPortfoliosMap = scoresPrelimFinalPortfoliosMap.get(a.getScores()[o]);
				                		}
				                	
				                		Map<Portfolio, Integer>portfoliosMap = prelimFinalPortfoliosMap.get(token);
				                		portfoliosMap.put(p, 1);
				                		
				                	}
				                	
				                	logService.debug("===> " + p.getShareId() + "|" + shareEntry.getId() + ": " + p.getPerson().getFullName() + " : " + scores);

			                	}
			                	
		                	}
		                	else {
		                		logService.debug("====> shareEntry Rubric ID Mismatch: " + am.getId() + " v. " + rId);
		                	}

	                	} else {
	                		logService.debug("===> shareEntry[" + shareEntry.getId() + "] - shareEntry.getAssessmentModelAssignment() is null");
	                	}
	                } 
	                
	                if(has_assessment) {
	                	boolean has_final_assessment = false;
	                	boolean has_prelim_assessment= false;
	                		                	
		                Map<String, Integer[][]>scoresMap = new LinkedHashMap<String, Integer[][]>();
		                
		                for(int u=0; u<objectives.size(); ++u) {
		                	
		                	for(int v=0; v<portfolio_fMap[u].length; ++v) {
		                		if(portfolio_fMap[u][v] > 0) {
		                			has_final_assessment = true;
		                			break;
		                		}
		                	}
		                	
		                	for(int v=0; v<portfolio_pMap[u].length; ++v) {
		                		if(portfolio_pMap[u][v] > 0) {
		                			has_prelim_assessment = true;
		                			break;
		                		}
		                	}
		                	
		                }
		                
		                if(has_final_assessment) {
		                	portfolios_with_final_count++;
		                }
		                
		                if(has_prelim_assessment) {
		                	portfolios_with_prelim_count++;
		                }
		                
		                if(!has_final_assessment && has_prelim_assessment) {
		                	portfolios_with_only_prelim_count++;
		                }
		                
		                scoresMap.put("final", portfolio_fMap);
		                scoresMap.put("prelim", portfolio_pMap);
	
		                portfolioAssessmentsMap.put(p, scoresMap);
		                
		                portfolios_with_prelim_and_final_count++;
	                
	                }
	                else {
	                	portfolios_without_count++;
	                }
	                
    			}
    		}
    		
    		returnValues.put("global_portfolio_pMap", global_portfolio_pMap);
    		returnValues.put("global_portfolio_fMap", global_portfolio_fMap);
    		
    		returnValues.put("portfolios", portfolios);
    		returnValues.put("portfolios_count", portfolios_count);
    		returnValues.put("portfolios_without_count", portfolios_without_count);
    		returnValues.put("portfolios_with_prelim_count", portfolios_with_prelim_count);
    		returnValues.put("portfolios_with_final_count", portfolios_with_final_count);
    		returnValues.put("portfolios_with_only_prelim_count", portfolios_with_only_prelim_count);
    		returnValues.put("portfolios_with_prelim_and_final_count", portfolios_with_prelim_and_final_count);
    		returnValues.put("assessedObjectivesScoresPrelimFinalPortfoliosMap", assessedObjectivesScoresPrelimFinalPortfoliosMap);
    		returnValues.put("assessmentModel", assessmentModel);
    		returnValues.put("rubric", assessmentModel);
    		returnValues.put("template", template);
    		returnValues.put("objectives", objectives);
    		returnValues.put("assessmentModelCount", assessmentModelCount);
    		
    		returnValues.put("portfolioAssessmentsMap", portfolioAssessmentsMap);
    		
    	}

    	return returnValues;
    }

}
