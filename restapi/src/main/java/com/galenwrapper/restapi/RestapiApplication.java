package com.galenwrapper.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration("com.galenwrapper.restapi")
public class RestapiApplication {

  public static void main(String[] args) {
    SpringApplication.run(RestapiApplication.class, args);
  }
}
