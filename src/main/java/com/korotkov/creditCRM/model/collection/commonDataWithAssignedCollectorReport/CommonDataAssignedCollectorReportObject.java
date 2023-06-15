package com.korotkov.creditCRM.model.collection.commonDataWithAssignedCollectorReport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommonDataAssignedCollectorReportObject {
    List<CommonDataWithAssignedCollectorReport> commonDataWithAssignedCollectorReportList;

    public CommonDataAssignedCollectorReportObject(List<CommonDataWithAssignedCollectorReport> list) {
        this.commonDataWithAssignedCollectorReportList = list;
    }

}
