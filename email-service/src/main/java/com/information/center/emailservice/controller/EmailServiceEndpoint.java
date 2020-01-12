package com.information.center.emailservice.controller;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email-service")
public interface EmailServiceEndpoint {

  @PostMapping("sendemail")
  ResponseEntity<?> sendSubscriptionEmail(
      @Valid @RequestBody List<EmailSubscriptionRequest> emails);

}
