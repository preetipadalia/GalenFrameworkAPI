package com.galenwrapper.restapi.helpers;

import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenwrapper.restapi.Request;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;

public class ReportHelper {

  public void generateGalenReport(
    @RequestBody Request request,
    LayoutReport rep
  )
    throws IOException {
    List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
    // Creating an object that will contain the information about the test
    GalenTestInfo test = GalenTestInfo.fromString(request.getTestTitle());
    // Adding layout report to the test report
    test.getReport().layout(rep, request.getReportTitle());
    tests.add(test);
    // Exporting all test reports to html
    new HtmlReportBuilder().build(tests, request.getReportPath());
  }
}
