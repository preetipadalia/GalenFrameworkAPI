package com.galen.restapi;

import com.galenframework.api.Galen;
import com.galenframework.reports.GalenTestInfo;
import com.galenframework.reports.HtmlReportBuilder;
import com.galenframework.reports.model.LayoutReport;
import com.galenframework.validation.ValidationResult;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LayoutApi {

  @PostMapping(value = "/checkLayout", consumes = "application/json")
  public Result CheckLayoutWithPost(@RequestBody Request request)
    throws IOException {
    RemoteWebDriver driver = createDriverFromSession(
      request.getSessionId(),
      new URL(request.getUrl())
    );
    List<GalenTestInfo> tests = new LinkedList<GalenTestInfo>();
    LayoutReport rep = Galen.checkLayout(
      driver,
      request.getSpecPath(),
      request.getIncludedTags()
    );
    Result result = getResultObject(rep);
    return result;
  }

  private Result getResultObject(LayoutReport rep) {
    Result result = new Result();
    result.Errors = rep.errors();
    result.Objects = rep.getObjects();
    result.IncludedTags = rep.getIncludedTags();
    result.ExcludedTags = rep.getExcludedTags();
    result.ValidationResults = rep.getValidationErrorResults();
    return result;
  }

  @PostMapping(value = "/checkLayoutWithReport", consumes = "application/json")
  public Result CheckLayoutAndReportGenerationWithPost(
    @RequestBody Request request
  )
    throws IOException {
    RemoteWebDriver driver = createDriverFromSession(
      request.getSessionId(),
      new URL(request.getUrl())
    );
    LayoutReport rep = Galen.checkLayout(
      driver,
      request.getSpecPath(),
      request.getIncludedTags()
    );
    generateGalenReport(request, rep);
    Result result = getResultObject(rep);
    return result;
  }

  private void generateGalenReport(
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

  @GetMapping("/checkLayout")
  public LayoutReport checkLayoutWithGet() throws IOException {
    System.setProperty(
      "webdriver.chrome.driver",
      "/Users/sachin/Preeti/chromedriver"
    );
    WebDriver webdriver = new ChromeDriver();

    // RemoteWebDriver driver=new RemoteWebDriver("", new DesiredCapabilities())
    webdriver.navigate().to("http://google.com");
    return Galen.checkLayout(
      webdriver,
      "/Users/sachin/Preeti/Specs/sample.spec",
      null
    );
  }

  private RemoteWebDriver createDriverFromSession(
    final String sId,
    URL command_executor
  ) {
    CommandExecutor executor = new HttpCommandExecutor(command_executor) {
      SessionId sessionId = new SessionId(sId);

      @Override
      public Response execute(Command command) throws IOException {
        Response response = null;
        if (command.getName() == "newSession") {
          response = new Response();
          response.setSessionId(sessionId.toString());
          response.setStatus(0);
          response.setValue(Collections.<String, String>emptyMap());

          try {
            Field commandCodec = null;
            commandCodec =
              this.getClass().getSuperclass().getDeclaredField("commandCodec");
            commandCodec.setAccessible(true);
            commandCodec.set(this, new W3CHttpCommandCodec());

            Field responseCodec = null;
            responseCodec =
              this.getClass().getSuperclass().getDeclaredField("responseCodec");
            responseCodec.setAccessible(true);
            responseCodec.set(this, new W3CHttpResponseCodec());
          } catch (NoSuchFieldException e) {
            e.printStackTrace();
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        } else {
          response = super.execute(command);
        }
        return response;
      }
    };

    return new RemoteWebDriver(executor, new DesiredCapabilities());
  }
}
