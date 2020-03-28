package com.information.center.emailservice.controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import com.information.center.emailservice.service.EmailService;
import exception.RestExceptions.EmailSendException;
import javax.mail.MessagingException;
import model.WrapperValidList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceControllerTest {

  @InjectMocks
  private EmailServiceController emailServiceController;

  @Mock
  private EmailService emailService;
  private WrapperValidList<EmailSubscriptionRequest> emailSubscriptionRequestWrapperValidList;
  private EmailSubscriptionRequest emailSubscriptionRequest1;
  private EmailSubscriptionRequest emailSubscriptionRequest2;

  @Before
  public void setUp() {
    emailSubscriptionRequestWrapperValidList = new WrapperValidList<>();
    emailSubscriptionRequest1 = EmailSubscriptionRequest.builder().build();
    emailSubscriptionRequest2 = EmailSubscriptionRequest.builder().build();
    emailSubscriptionRequestWrapperValidList.add(emailSubscriptionRequest1);
    emailSubscriptionRequestWrapperValidList.add(emailSubscriptionRequest2);
  }

  @Test
  public void sent_expectEmailSent() {
    ResponseEntity<?> response = emailServiceController
        .sendSubscriptionEmail(emailSubscriptionRequestWrapperValidList, null);

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test(expected = EmailSendException.class)
  public void sendEmail_expectEmailSendException() throws MessagingException {
    doThrow(MessagingException.class).when(emailService)
        .subscriptionEmail(emailSubscriptionRequest2);

    emailServiceController.sendSubscriptionEmail(emailSubscriptionRequestWrapperValidList,null);
  }
}
