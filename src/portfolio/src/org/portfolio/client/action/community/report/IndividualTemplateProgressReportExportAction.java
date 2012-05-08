/* $Name:  $ */
/* $Id: IndividualTemplateProgressReportExportAction.java,v 1.4 2010/10/27 19:24:56 ajokela Exp $ */
package org.portfolio.client.action.community.report;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.portfolio.client.action.BaseAction;
import org.portfolio.client.action.community.report.IndividualTemplateProgressReportAction.RowData;
import org.portfolio.model.Portfolio;
import org.portfolio.model.TemplateElementMapping;
import org.portfolio.model.Viewer;
import org.portfolio.model.assessment.Assessment;

/**
 * @author Matt Sheehan
 * 
 */
public class IndividualTemplateProgressReportExportAction extends BaseAction {
    
    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");

    @SuppressWarnings("unchecked")
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/vnd.ms-excel");
        String fileName = "IndividualTemplateProgressReport_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

        List<Portfolio> portfolios = (List<Portfolio>) request.getAttribute("portfolios");
        Map<Portfolio, List<TemplateElementMapping>> temMap = (Map<Portfolio, List<TemplateElementMapping>>) request
                .getAttribute("temMap");
        Map<Portfolio, RowData> portfolioMap = (Map<Portfolio, RowData>) request.getAttribute("portfolioMap");
        Map<TemplateElementMapping, List<RowData>> shareEntryMap = (Map<TemplateElementMapping, List<RowData>>) request
                .getAttribute("shareEntryMap");

        Workbook wb = createReport(portfolios, temMap, portfolioMap, shareEntryMap);

        wb.write(response.getOutputStream());
        return null;
    }

    private Workbook createReport(List<Portfolio> portfolios, Map<Portfolio, List<TemplateElementMapping>> temMap,
            Map<Portfolio, RowData> portfolioMap, Map<TemplateElementMapping, List<RowData>> shareEntryMap) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);

        int rowIndex = 0;
        {
            Row row = sheet.createRow(rowIndex++);
            String[] headings = { "Portfolio", "Assessable", "Evaluator", "Score", "Status" };
            int cellIndex = 0;
            for (String heading : headings) {
                HSSFRichTextString header = new HSSFRichTextString(heading);
                header.applyFont(bold);
                row.createCell(cellIndex++).setCellValue(header);
            }
        }

        for (Portfolio portfolio : portfolios) {
            if (portfolio.getAssessmentModelAssignment() != null) {
                RowData rowData = portfolioMap.get(portfolio);
                for (Viewer evaluator : rowData.getEvaluators()) {
                    Assessment assessment = rowData.getAssessments().get(evaluator.getPerson());
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(portfolio.getShareName());
                    row.createCell(1).setCellValue("entire portfolio");
                    row.createCell(2).setCellValue(evaluator.getPerson().getDisplayName());
                    if (assessment != null) {
                        row.createCell(3).setCellValue(assessment.getOverallScore());
                        row.createCell(4).setCellValue(assessment.getAssessmentType());
                    }
                }

            }
            List<TemplateElementMapping> tems = temMap.get(portfolio);
            for (TemplateElementMapping tem : tems) {
                List<RowData> dataList = shareEntryMap.get(tem);
                for (RowData rowData : dataList) {
                    for (Viewer evaluator : rowData.getEvaluators()) {
                        Assessment assessment = rowData.getAssessments().get(evaluator.getPerson());
                        Row row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(portfolio.getShareName());
                        row.createCell(1).setCellValue(tem.getElementTitle());
                        row.createCell(2).setCellValue(evaluator.getPerson().getDisplayName());
                        if (assessment != null) {
                            row.createCell(3).setCellValue(assessment.getOverallScore());
                            row.createCell(4).setCellValue(assessment.getAssessmentType());
                        }
                    }
                }
            }
        }

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
        return wb;
    }
}
