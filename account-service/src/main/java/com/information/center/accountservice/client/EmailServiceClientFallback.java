package com.information.center.accountservice.client;

import com.information.center.accountservice.model.EmailSubscriptionRequest;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceClientFallback implements EmailServiceClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceClientFallback.class);

  @Override
  public ResponseEntity<?> sendSubscriptionEmail(List<EmailSubscriptionRequest> emails) {
    LOGGER.error("Error during sending emails for account: {}",
        emails.stream().map(EmailSubscriptionRequest::getUid).toArray());
    return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
  }
}
