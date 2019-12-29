package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.SubscriptionRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/subscription")
public interface SubscriptionEndpoint {

  @PostMapping
  ResponseEntity<?> subscription(@Valid @RequestBody SubscriptionRequest subscriptionRequest);

}
