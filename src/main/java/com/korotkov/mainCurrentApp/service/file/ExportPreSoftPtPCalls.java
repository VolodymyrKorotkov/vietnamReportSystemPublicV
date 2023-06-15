package com.korotkov.mainCurrentApp.service.file;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class ExportPreSoftPtPCalls extends AbstractXlsxView {
    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      Workbook workbook,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        //response config + redirect success
        response.setHeader("Content-Disposition", "attachment; filename=ExportClientDocs.xlsx");

        ExcelBuildReport.buildExcelExportPtpPreSoftResultedCallsReport(model,workbook);

    }
}
