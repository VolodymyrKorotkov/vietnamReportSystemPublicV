package com.korotkov.creditCRM.dao.collection.commonDataWithAssignedCollectorReport;

import com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport.CommonDataWithAssignedCollectorReport;

import java.util.List;

public interface CommonDataWithAssignedCollectorReportDao {
    List<CommonDataWithAssignedCollectorReport> getExportCommonDataWithAssignedCollectorList();
}
