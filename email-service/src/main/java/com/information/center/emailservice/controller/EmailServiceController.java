package com.information.center.emailservice.controller;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import com.information.center.emailservice.service.EmailService;
import exception.RestExceptions.EmailSendException;
import java.util.List;
import javax.mail.MessagingException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailServiceController implements EmailServiceEndpoint {

  private final EmailService emailService;

  @Override
  public ResponseEntity<?> sendSubscriptionEmail(
      @Valid @RequestBody List<EmailSubscriptionRequest> emails) {
    emails.forEach(mail -> {
      try {
        emailService.subscriptionEmail(mail);
      } catch (MessagingException e) {
        throw new EmailSendException(e.getMessage());
      }
    });

    return new ResponseEntity<>(HttpStatus.OK);
  }

}
