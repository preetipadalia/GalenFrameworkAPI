package com.galenwrapper.restapi;

import com.galenframework.reports.model.LayoutReport;

class ResultHelper {

  Result getResultObject(LayoutReport rep) {
    Result result = new Result();
    result.Errors = rep.errors();
    result.Objects = rep.getObjects();
    result.IncludedTags = rep.getIncludedTags();
    result.ExcludedTags = rep.getExcludedTags();
    result.ValidationResults = rep.getValidationErrorResults();
    result.ExceptionMessage = "no Exception";
    result.Report = rep;
    return result;
  }
}
