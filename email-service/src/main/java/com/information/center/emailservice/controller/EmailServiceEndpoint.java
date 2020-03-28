package com.information.center.emailservice.controller;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import java.security.Principal;
import javax.validation.Valid;
import model.WrapperValidList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public interface EmailServiceEndpoint {

  @PostMapping("sendemail")
  ResponseEntity<?> sendSubscriptionEmail(
      @Valid @RequestBody WrapperValidList<EmailSubscriptionRequest> emails, Principal principal);

}
