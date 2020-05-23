package com.galenwrapper.restapi;

import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenwrapper.layout.LayoutReportManager;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReportApi {

  @PostMapping(value = "/generateReport", consumes = "application/json")
  public String GenerateReport(@RequestBody TestReportRequest request)
    throws IOException {
    try {
      List<GalenTestInfo> tests = new LinkedList<>();
      for (LayoutMap map : request.getLayoutReport()) {
        GalenTestInfo test = GalenTestInfo.fromString(map.getTitle());
        test
          .getReport()
          .layout(
            LayoutReportManager.layoutReportsMap.get(map.getId()),
            map.getTitle()
          );
        tests.add(test);
      }
      new HtmlReportBuilder().build(tests, request.getReportPath());
      return "Report Generated Successfully at path:" + request.getReportPath();
    } catch (Exception e) {
      return e.getMessage();
    }
  }
}
