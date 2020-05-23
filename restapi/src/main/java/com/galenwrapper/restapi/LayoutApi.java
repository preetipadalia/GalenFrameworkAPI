package com.galenwrapper.restapi;

import com.galenframework.api.Galen;
import com.galenframework.reports.model.LayoutReport;
import com.galenwrapper.driverhelper.DriverResolver;
import com.galenwrapper.restapi.helpers.ReportHelper;
import com.galenwrapper.restapi.helpers.RequestHelper;
import com.galenwrapper.restapi.helpers.ResultHelper;
import java.io.IOException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LayoutApi {
  DriverResolver driverResolver;
  RequestHelper requestHelper;
  ResultHelper resultHelper;
  ReportHelper reportHelper;

  @PostMapping(value = "/checkLayout", consumes = "application/json")
  public Result CheckLayout(@RequestBody Request request) throws IOException {
    try {
      RemoteWebDriver driver = driverResolver.getDriverObject(request);
      LayoutReport rep;
      if (isSectionFilterRequested(request)) {
        rep = checkLayoutWithSectionFilter(request, driver);
      } else {
        rep = checkLayoutWithTags(request, driver);
      }
      if (isReportingEnabled(request)) reportHelper.generateGalenReport(
        request,
        rep
      );
      if (driverResolver.isNewSession(request)) driver.close();
      return resultHelper.getResultObject(rep);
    } catch (Exception e) {
      Result result = getExceptionResult(e);
      return result;
    }
  }

  //  @PostMapping(value = "/generateReport", consumes = "application/json")
  //  public ReportResult GenerateReport(@RequestBody TestReportRequest request)
  //    throws IOException {
  //    try {
  //      List<GalenTestInfo> tests = new LinkedList<>();
  //      for (LayoutReport rep : request.getLayoutReport()) {
  //        GalenTestInfo testInfo = GalenTestInfo.fromString(rep.getTitle());
  //        tests.add(testInfo);
  //        testInfo.getReport().layout(rep, rep.getTitle());
  //      }
  //      new HtmlReportBuilder().build(tests, request.getReportPath());
  //      return new ReportResult(
  //        "Successfully created the report at:" + request.getReportPath()
  //      );
  //    } catch (Exception e) {
  //      return new ReportResult(e.getMessage());
  //    }
  //  }

  private Result getExceptionResult(Exception e) {
    Result result = new Result();
    result.ExceptionMessage = e.getMessage();
    return result;
  }

  private boolean isReportingEnabled(@RequestBody Request request) {
    return (
      request.isReportEnabled() &&
      request.getReportPath() != null &&
      !request.getReportPath().equals("")
    );
  }

  private boolean isSectionFilterRequested(@RequestBody Request request) {
    return request.getSectionFilter() != null;
  }

  private LayoutReport checkLayoutWithTags(
    @RequestBody Request request,
    RemoteWebDriver driver
  )
    throws IOException {
    LayoutReport rep;
    rep =
      Galen.checkLayout(
        driver,
        request.getSpecPath(),
        request.getIncludedTags()
      );
    return rep;
  }

  private LayoutReport checkLayoutWithSectionFilter(
    @RequestBody Request request,
    RemoteWebDriver driver
  )
    throws IOException {
    LayoutReport rep;
    rep =
      Galen.checkLayout(
        driver,
        request.getSpecPath(),
        requestHelper.getSectionFilterObject(request.getSectionFilter()),
        requestHelper.getPropertiesObject(request.getProperties())
      );
    return rep;
  }

  public LayoutApi() {
    driverResolver = new DriverResolver();
    requestHelper = new RequestHelper();
    resultHelper = new ResultHelper();
    reportHelper = new ReportHelper();
  }
}
