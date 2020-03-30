package com.information.center.accountservice.client;

import com.information.center.accountservice.model.CreateAccountRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceClientFallBack implements AuthServiceClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthServiceClientFallBack.class);

  @Override
  public ResponseEntity<?> createUser(CreateAccountRequest user) {
    LOGGER.error("Error during creating user: {}", user);
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }
}
