package com.galen.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration("com.galen.restapi")
public class RestapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestapiApplication.class, args);
  }

}
