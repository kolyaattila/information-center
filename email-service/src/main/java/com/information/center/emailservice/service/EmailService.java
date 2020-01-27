package com.information.center.emailservice.service;

import static java.util.Objects.requireNonNull;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

  private static final String SUBSCRIPTION_TEMPLATE_NAME = "email_subscription";
  private static final String COMPANY_LOCATION = "Oradea, Romania";
  private static final String CUSTOMER_NAME = "customerName";
  private static final String LOCATION = "companyLocation";
  private static final String SIGNATURE = "signature";
  private static final String SUBSCRIPTION_EMAIL_SUBJECT = "Email confirmation!";
  private static final String CUSTOMER_EMAIL = "customerEmail";
  private static final String NAME = "companyName";

  @Value("${EMAIL_ADDRESS}")
  private String COMPANY_EMAIL;
  @Value("${COMPANY_NAME}")
  private String COMPANY_NAME;
  @Value("${COMPANY_SITE_URL}")
  private String COMPANY_SITE_URL;
  @Value("${SUBSCRIPTION_CONFIRMATION_PATH}")
  private String SUBSCRIPTION_CONFIRMATION_PATH;
  @Value("${UNSUBSCRIPTION_PATH}")
  private String UNSUBSCRIPTION_PATH;

  private final JavaMailSender emailSender;
  private final SpringTemplateEngine templateEngine;

  public void subscriptionEmail(EmailSubscriptionRequest mail) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    Context context = new Context();
    context.setVariables(getModelsForSubscriptionEmail(mail));
    String html = templateEngine.process(SUBSCRIPTION_TEMPLATE_NAME, context);

    helper.setTo(mail.getTo());
    helper.setText(html, true);
    helper.setSubject(SUBSCRIPTION_EMAIL_SUBJECT);
    helper.setFrom(COMPANY_EMAIL);

    emailSender.send(message);
  }

  Map<String, Object> getModelsForSubscriptionEmail(EmailSubscriptionRequest mail) {
    Map<String, Object> map = new HashMap<>();
    map.put(CUSTOMER_NAME, mail.getFirstName() + " " + mail.getLastName());
    map.put(LOCATION, COMPANY_LOCATION);
    map.put(SIGNATURE, COMPANY_SITE_URL);
    map.put(NAME, COMPANY_NAME);
    map.put(CUSTOMER_EMAIL, mail.getTo());
    map.put("logo", getLogo());
    map.put("companyUrl", COMPANY_SITE_URL);
    map.put("uid", mail.getUid());
    map.put("unsubscriptionAction", UNSUBSCRIPTION_PATH);
    map.put("mailConfirmationAction", SUBSCRIPTION_CONFIRMATION_PATH);
    return map;
  }

  private String getLogo() {
    try {
      byte[] fileContent = FileUtils.readFileToByteArray(new File(
          requireNonNull(getClass().getClassLoader().getResource("images/logo.png")).getFile()));

      return Base64.getEncoder().encodeToString(fileContent);
    } catch (IOException e) {
      e.fillInStackTrace();
      return "";
    }
  }
}