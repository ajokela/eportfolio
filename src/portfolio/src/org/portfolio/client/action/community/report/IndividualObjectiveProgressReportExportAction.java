/* $Name:  $ */
/* $Id: IndividualObjectiveProgressReportExportAction.java,v 1.5 2010/11/04 21:08:53 ajokela Exp $ */
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
import org.portfolio.client.action.community.report.IndividualObjectiveProgressReportAction.RowData;
import org.portfolio.model.Portfolio;
import org.portfolio.model.ShareEntry;
import org.portfolio.model.Viewer;
import org.portfolio.model.assessment.Assessment;
import org.portfolio.model.assessment.Objective;

/**
 * @author Matt Sheehan
 * 
 */
public class IndividualObjectiveProgressReportExportAction extends BaseAction {

    private static final DateFormat dateFormat = new SimpleDateFormat("MMddyy_HHmmss");

    @SuppressWarnings("unchecked")
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("application/vnd.ms-excel");
        String fileName = "IndividualObjectiveProgressReport_" + dateFormat.format(new Date()) + ".xls";
        response.setHeader("Content-disposition", "attachment; filename=\"" + fileName + "\"");

        Objective objectiveSet = (Objective) request.getAttribute("objectiveSet");
        Map<Objective, List<RowData>> portfoliosMap = (Map<Objective, List<RowData>>) request.getAttribute("portfoliosMap");
        Map<Objective, List<RowData>> portfolioElementsMap = (Map<Objective, List<RowData>>) request.getAttribute("portfolioElementsMap");

        Workbook wb = createReport(objectiveSet, portfoliosMap, portfolioElementsMap);

        wb.write(response.getOutputStream());
        return null;
    }

    private Workbook createReport(Objective objectiveSet, Map<Objective, List<RowData>> portfoliosMap,
            Map<Objective, List<RowData>> portfolioElementsMap) {
        Workbook wb = new HSSFWorkbook();
        Sheet sheet = wb.createSheet();

        Font bold = wb.createFont();
        bold.setBoldweight(Font.BOLDWEIGHT_BOLD);

        int rowIndex = 0;
        {
            Row row = sheet.createRow(rowIndex++);
            String[] headings = { "Objective Name", "Assessable", "Evaluator", "Score", "Status" };
            int cellIndex = 0;
            for (String heading : headings) {
                HSSFRichTextString header = new HSSFRichTextString(heading);
                header.applyFont(bold);
                row.createCell(cellIndex++).setCellValue(header);
            }
        }
        for (Objective objective : objectiveSet.getFlatSubObjectivesList()) {
            List<RowData> dataList = portfoliosMap.get(objective);
            if (dataList != null) {
                for (RowData rowData : dataList) {
                    for (Viewer evaluator : rowData.getEvaluators()) {
                        Assessment assessment = rowData.getAssessments().get(evaluator.getPerson());
                        Row row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(objective.getName());
                        row.createCell(1).setCellValue(((Portfolio) rowData.getAssessable()).getShareName());
                        row.createCell(2).setCellValue(evaluator.getPerson().getDisplayName());
                        if (assessment != null) {
                            row.createCell(3).setCellValue(assessment.getScores()[rowData.getScoreIndex()]);
                            row.createCell(4).setCellValue(assessment.getAssessmentType());
                        }
                    }
                }
            }
            List<RowData> list = portfolioElementsMap.get(objective);
            if (list != null) {
                for (RowData rowData : list) {
                    for (Viewer evaluator : rowData.getEvaluators()) {
                        Assessment assessment = rowData.getAssessments().get(evaluator.getPerson());
                        Row row = sheet.createRow(rowIndex++);
                        row.createCell(0).setCellValue(objective.getName());
                        row.createCell(1).setCellValue(((ShareEntry) rowData.getAssessable()).getElement().getEntryName());
                        row.createCell(2).setCellValue(evaluator.getPerson().getDisplayName());
                        if (assessment != null) {
                            row.createCell(3).setCellValue(assessment.getScores()[rowData.getScoreIndex()]);
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
