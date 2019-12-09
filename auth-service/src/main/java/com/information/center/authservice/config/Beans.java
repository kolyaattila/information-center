package com.information.center.authservice.config;

//import com.information.center.authservice.security.JwtConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableAutoConfiguration
@Configuration
public class Beans {

//    @Bean
//    public JwtConfig jwtConfig() {
//        return new JwtConfig();
//    }
//
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//@Bean
//ServletWebServerFactory servletWebServerFactory(){
//    return new TomcatServletWebServerFactory();
//}
}
