package com.galenwrapper.restapi;

import com.galenframework.reports.model.LayoutReport;
import com.galenframework.reports.model.LayoutSection;
import com.galenframework.validation.ValidationResult;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
  public List<String> ExcludedTags;
  LayoutReport Report;
  List<ValidationResult> ValidationResults;
  int Errors;
  List<String> IncludedTags;
  java.util.Map<String, com.galenframework.reports.model.LayoutObjectDetails> Objects;
  String ExceptionMessage;
  String Id;
  String Title;

  public Result() {}
}
