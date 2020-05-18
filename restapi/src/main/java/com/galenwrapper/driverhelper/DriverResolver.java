package com.galenwrapper.driverhelper;

import com.galenwrapper.restapi.Request;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;
import org.springframework.web.bind.annotation.RequestBody;

public class DriverResolver {

  public RemoteWebDriver getDriverObject(@RequestBody Request request)
    throws MalformedURLException {
    RemoteWebDriver driver;
    if (isNewSession(request)) {
      driver = createNewDriver(request);
    } else driver = createDriverFromSession(request);
    return resizeDriver(driver, request);
  }

  private RemoteWebDriver resizeDriver(
    RemoteWebDriver driver,
    Request request
  ) {
    int width = 0;
    int height = 0;
    boolean shouldResize = false;
    if (request.getBrowserHeight() > 0) {
      shouldResize = true;
      height = request.getBrowserHeight();
    }
    if (request.getBrowserWidth() > 0) {
      width = request.getBrowserWidth();
      shouldResize = true;
    }
    if (shouldResize) driver
      .manage()
      .window()
      .setSize(new Dimension(width, height));
    return driver;
  }

  public boolean isNewSession(@RequestBody Request request) {
    return request.getSessionId() == null || request.getSessionId().equals("");
  }

  private RemoteWebDriver createNewDriver(Request request) {
    String browser = request.getBrowserType();
    WebDriver driver;

    switch (browser.toUpperCase()) {
      case "CHROME":
        System.setProperty("webdriver.chrome.driver", request.getDriverPath());
        driver = new ChromeDriver();
        break;
      case "IE":
      case "INTERNET EXPLORER":
        System.setProperty("webdriver.ie.driver", request.getDriverPath());
        driver = new InternetExplorerDriver();
        break;
      case "FIREFOX":
      default:
        System.setProperty("webdriver.gecko.driver", request.getDriverPath());
        driver = new FirefoxDriver();
        break;
    }
    driver.get(request.getUrl());
    return (RemoteWebDriver) driver;
  }

  private RemoteWebDriver createDriverFromSession(Request request)
    throws MalformedURLException {
    CommandExecutor executor = new HttpCommandExecutor(
      new URL(request.getUrl())
    ) {
      SessionId sessionId = new SessionId(request.getSessionId());

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
