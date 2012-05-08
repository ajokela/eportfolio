/* $Name:  $ */
/* $Id: TemplateProgressSummaryReportExportAction.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
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
import org.portfolio.client.action.community.report.TemplateProgressSummaryReportAction.AggregateData;
import org.portfolio.model.Template;
import org.portfolio.model.TemplateElementMapping;

/**
 * @author Matt Sheehan
 * 
 */
public class TemplateProgressSummaryReportExportAction extends BaseAction {

    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");

    @SuppressWarnings("unchecked")
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/vnd.ms-excel");
        String fileName = "TemplateProgressSummaryReport_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

        Template template = (Template) request.getAttribute("template");
        List<TemplateElementMapping> elements = (List<TemplateElementMapping>) request.getAttribute("elements");
        List<AggregateData> portfolioData = (List<AggregateData>) request.getAttribute("portfolioData");
        Map<TemplateElementMapping, List<AggregateData>> portfolioElementMap = (Map<TemplateElementMapping, List<AggregateData>>) request
                .getAttribute("portfolioElementMap");

        Workbook wb = createReport(template, elements, portfolioData, portfolioElementMap);

        wb.write(response.getOutputStream());
        return null;
    }

    private Workbook createReport(Template template, List<TemplateElementMapping> elements, List<AggregateData> portfolioData,
            Map<TemplateElementMapping, List<AggregateData>> portfolioElementMap) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);

        int rowIndex = 0;
        {
            Row row = sheet.createRow(rowIndex++);
            HSSFRichTextString header1 = new HSSFRichTextString("Number of assessments");
            header1.applyFont(bold);
            row.createCell(1).setCellValue(header1);
            HSSFRichTextString header2 = new HSSFRichTextString("Average score");
            header2.applyFont(bold);
            row.createCell(2).setCellValue(header2);
        }

        if (template.getAssessmentModelAssignment() != null) {
            {
                Row row = sheet.createRow(rowIndex++);
                HSSFRichTextString rts = new HSSFRichTextString("entire portfolio");
                rts.applyFont(bold);
                row.createCell(0).setCellValue(rts);
            }
            for (AggregateData aggregateData : portfolioData) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(aggregateData.getPerson().getDisplayName());
                row.createCell(1).setCellValue(aggregateData.getNumAssessments());
                if (!aggregateData.getAverageScore().isNaN()) {
                    row.createCell(2).setCellValue(aggregateData.getAverageScore());
                }
            }
        }

        for (TemplateElementMapping templateElementMapping : elements) {
            {
                Row row = sheet.createRow(rowIndex++);
                HSSFRichTextString rts = new HSSFRichTextString(templateElementMapping.getElementTitle());
                rts.applyFont(bold);
                row.createCell(0).setCellValue(rts);
            }
            List<AggregateData> dataList = portfolioElementMap.get(templateElementMapping);
            if (dataList != null) {
                for (AggregateData aggregateData : dataList) {
                    Row row = sheet.createRow(rowIndex++);
                    row.createCell(0).setCellValue(aggregateData.getPerson().getDisplayName());
                    row.createCell(1).setCellValue(aggregateData.getNumAssessments());
                    if (!aggregateData.getAverageScore().isNaN()) {
                        row.createCell(2).setCellValue(aggregateData.getAverageScore());
                    }
                }
            }
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        return wb;
    }
}
