package com.information.center.accountservice.client;

import com.information.center.accountservice.model.EmailSubscriptionRequest;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "email-service", fallback = EmailServiceClientFallback.class)
public interface EmailServiceClient {

  @RequestMapping(method = RequestMethod.POST, value = "/email-service/email/sendemail", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  ResponseEntity<?> sendSubscriptionEmail(@RequestBody List<EmailSubscriptionRequest> emails);

}
