package com.information.center.courseservice.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "amazon.properties")
public class AmazonProperties {
	private String endpointUrl;
	private String bucketName;
	private String accessKey;
	private String secretKey;
}
