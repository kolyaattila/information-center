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
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

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
  private String companyEmail;
  @Value("${COMPANY_NAME}")
  private String companyName;
  @Value("${COMPANY_SITE_URL}")
  private String companySiteUrl;
  @Value("${SUBSCRIPTION_CONFIRMATION_PATH}")
  private String subscriptionConfirmationPath;
  @Value("${UNSUBSCRIPTION_PATH}")
  private String unsubscriptionPath;

  private final JavaMailSender emailSender;
  private final ITemplateEngine templateEngine;

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
    helper.setFrom(companyEmail);

    emailSender.send(message);
  }

  private Map<String, Object> getModelsForSubscriptionEmail(EmailSubscriptionRequest mail) {
    Map<String, Object> map = new HashMap<>();
    map.put(CUSTOMER_NAME, mail.getFirstName() + " " + mail.getLastName());
    map.put(LOCATION, COMPANY_LOCATION);
    map.put(SIGNATURE, companySiteUrl);
    map.put(NAME, companyName);
    map.put(CUSTOMER_EMAIL, mail.getTo());
    map.put("logo", getLogo());
    map.put("companyUrl", companySiteUrl);
    map.put("uid", mail.getUid());
    map.put("unsubscriptionAction", unsubscriptionPath);
    map.put("mailConfirmationAction", subscriptionConfirmationPath);
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
