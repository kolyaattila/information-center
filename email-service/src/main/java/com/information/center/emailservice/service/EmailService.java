package com.information.center.emailservice.service;

import com.information.center.emailservice.model.EmailSubscriptionRequest;
import java.nio.charset.StandardCharsets;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EmailService {

  private final JavaMailSender emailSender;
  private final SpringTemplateEngine templateEngine;

  public void sendSimpleMessage(EmailSubscriptionRequest mail) throws MessagingException {
    MimeMessage message = emailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message,
        MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
        StandardCharsets.UTF_8.name());

    helper.addAttachment("logo.png", new ClassPathResource("images/logo.png"));

    Context context = new Context();
    context.setVariables(mail.getModel());
    String html = templateEngine.process("email-template", context);

    helper.setTo(mail.getTo());
    helper.setText(html, true);
    helper.setSubject(mail.getSubject());
    helper.setFrom(mail.getFrom());

    emailSender.send(message);
  }
}