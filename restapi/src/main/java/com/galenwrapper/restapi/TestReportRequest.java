package com.galenwrapper.restapi;

import com.galenframework.reports.model.LayoutReport;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestReportRequest {
  private List<com.galenframework.reports.model.LayoutReport> LayoutReport;
  private String ReportPath;
}
