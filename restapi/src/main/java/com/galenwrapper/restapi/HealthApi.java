package com.galenwrapper.restapi;

import com.galenframework.reports.model.LayoutReport;
import java.io.IOException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthApi {

  @GetMapping(value = "/checkHealth")
  public String CheckLayout() throws IOException {
    try {
      return "I am up";
    } catch (Exception e) {
      return e.getMessage();
    }
  }
}
