package com.information.center.emailservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = {"com.information.center"})
@EntityScan(basePackages = {"com.information.center"})
@EnableDiscoveryClient
public class EmailServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmailServiceApplication.class, args);
  }
}
