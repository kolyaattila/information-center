package com.information.center.gateway;

import com.information.center.gateway.filter.CustomErrorFilter;
import java.util.Arrays;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class GatewayApplication {

  private final Log logger = LogFactory.getLog(getClass().getName());
  @Autowired
  private Environment env;

  public static void main(String[] args) {
    SpringApplication.run(GatewayApplication.class, args);
  }

  @Bean
  public FilterRegistrationBean corsFilter() {
    logger.info(
        String.format("Configure allowed origins %s ", env.getProperty("ALLOWED_ORIGINS")));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.addAllowedOrigin("*");
    config.setAllowedHeaders(Arrays
        .asList("Origin", "X-Requested-With", "Content-Type", "Accept", "Authorization", "Referer",
            "Sec-Fetch-Mode", "User-Agent"));
    config.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
    config.setExposedHeaders(
        Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
    config.setMaxAge(3600L);
    source.registerCorsConfiguration("/**", config);
    FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
    bean.setOrder(0);
    return bean;
  }
}
