package com.information.center.accountservice.client;

import static org.hamcrest.CoreMatchers.containsString;

import com.information.center.accountservice.model.EmailSubscriptionRequest;
import java.util.Collections;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
    "feign.hystrix.enabled=true"
})
public class EmailServiceClientFallbackTest {

  @Autowired
  private EmailServiceClient emailServiceClient;

  @Rule
  public final OutputCapture outputCapture = new OutputCapture();

  private EmailSubscriptionRequest emailSubscriptionRequest;

  @Before
  public void setup() {
    outputCapture.reset();

    emailSubscriptionRequest = EmailSubscriptionRequest.builder().uid("uid")
        .lastName("LastName")
        .firstName("firstName")
        .to("to")
        .build();
  }

  @Test
  public void testUpdateStatisticsWithFailFallback() {
    emailServiceClient.sendSubscriptionEmail(Collections.singletonList(emailSubscriptionRequest));

    outputCapture.expect(containsString("Error during sending emails for account: uid"));
  }
}
