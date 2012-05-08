/* $Name:  $ */
/* $Id: ObjectiveProgressSummaryReportExportAction.java,v 1.3 2010/10/27 19:24:56 ajokela Exp $ */
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
import org.portfolio.client.action.community.report.ObjectiveProgressSummaryReportAction.AggregateData;
import org.portfolio.model.assessment.Objective;

/**
 * @author Matt Sheehan
 * 
 */
public class ObjectiveProgressSummaryReportExportAction extends BaseAction {

    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");

    @SuppressWarnings("unchecked")
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/vnd.ms-excel");
        String fileName = "ObjectiveProgressSummaryReport_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

        Objective objectiveSet = (Objective) request.getAttribute("objectiveSet");
        Map<Objective, List<AggregateData>> objectiveMap = (Map<Objective, List<AggregateData>>) request.getAttribute("objectiveMap");

        Workbook wb = createReport(objectiveSet, objectiveMap);

        wb.write(response.getOutputStream());
        return null;
    }

    private Workbook createReport(Objective objectiveSet, Map<Objective, List<AggregateData>> objectiveMap) {
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
            HSSFRichTextString header2 = new HSSFRichTextString("AverageScore");
            header2.applyFont(bold);
            row.createCell(2).setCellValue(header2);
        }

        for (Objective objective : objectiveSet.getFlatSubObjectivesList()) {
            {
                Row row = sheet.createRow(rowIndex++);
                HSSFRichTextString rts = new HSSFRichTextString(objective.getName());
                rts.applyFont(bold);
                row.createCell(0).setCellValue(rts);
            }

            List<AggregateData> list = objectiveMap.get(objective);
            if (list != null) {
                for (AggregateData aggregateData : list) {
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
