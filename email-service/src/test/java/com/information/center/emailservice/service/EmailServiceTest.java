package com.information.center.emailservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

  public static final String EMAIL_ADDRESS = "EMAIL_ADDRESS";
  public static final String COMPANY_NAME = "COMPANY_NAME";
  public static final String COMPANY_SITE_URL = "COMPANY_SITE_URL";
  public static final String SUBSCRIPTION_CONFIRMATION_PATH = "SUBSCRIPTION_CONFIRMATION_PATH";
  public static final String UNSUBSCRIPTION_PATH = "UNSUBSCRIPTION_PATH";
  private static final String SUBSCRIPTION_TEMPLATE_NAME = "email_subscription";
  @InjectMocks
  private EmailService emailService;
  @Mock
  private JavaMailSender emailSender;
  @Mock
  private ITemplateEngine templateEngine;
  private EmailSubscriptionRequest emailSubscriptionRequest;
  private MimeMessage message;


  @Before
  public void setUp() {
    ReflectionTestUtils.setField(emailService, "companyEmail", EMAIL_ADDRESS);
    ReflectionTestUtils.setField(emailService, "companyName", COMPANY_NAME);
    ReflectionTestUtils.setField(emailService, "companySiteUrl", COMPANY_SITE_URL);
    ReflectionTestUtils.setField(emailService, "unsubscriptionPath", UNSUBSCRIPTION_PATH);
    ReflectionTestUtils.setField(emailService, "subscriptionConfirmationPath",
        SUBSCRIPTION_CONFIRMATION_PATH);

    message = mock(MimeMessage.class);
    emailSubscriptionRequest = EmailSubscriptionRequest.builder()
        .to("to@email.com")
        .firstName("firstName")
        .lastName("lastName")
        .uid("uid").build();
  }

  @Test
  public void subscriptionEmail() throws MessagingException {
    when(emailSender.createMimeMessage()).thenReturn(message);
    when(templateEngine.process(eq(SUBSCRIPTION_TEMPLATE_NAME), any(Context.class)))
        .thenReturn("html");

    emailService.subscriptionEmail(emailSubscriptionRequest);

    verify(emailSender).send(message);
  }
}
