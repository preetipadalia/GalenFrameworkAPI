package com.galenwrapper.restapi.helpers;

import com.galenframework.reports.model.LayoutReport;
import com.galenwrapper.layout.LayoutReportManager;
import com.galenwrapper.restapi.Result;

public class ResultHelper {

  public Result getResultObject(LayoutReport rep) {
    Result result = new Result();
    result.setErrors(rep.errors());
    result.setObjects(rep.getObjects());
    result.setIncludedTags(rep.getIncludedTags());
    result.setExcludedTags(rep.getExcludedTags());
    result.setValidationResults(rep.getValidationErrorResults());
    result.setExceptionMessage("no Exception");
    String id = LayoutReportManager.UpdateLayoutReportMap(rep);
    result.setId(id);
    result.setReport(rep);
    return result;
  }
}
