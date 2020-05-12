package com.information.center.accountservice.client;


import com.information.center.accountservice.model.CreateAccountRequest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        "feign.hystrix.enabled=true"
})
public class AuthServiceClientFallBackTest {

    private static final String USERNAME = "username";
    @Autowired
    AuthServiceClient authServiceClient;

    @Rule
    public final OutputCapture outputCapture = new OutputCapture();
    private CreateAccountRequest createAccountRequest;

    @Before
    public void setUp() {
        outputCapture.reset();

        createAccountRequest = CreateAccountRequest.builder()
                .username("username")
                .password("password")
                .build();
    }

    @Test
    public void createUser_expectFallback() {
        ResponseEntity<?> response = authServiceClient.createUser(createAccountRequest);

        assertThat(response.getStatusCode(), is(SERVICE_UNAVAILABLE));
        outputCapture.expect(
                containsString(String.format("Error during creating user: %s", createAccountRequest)));
    }

    @Test
    public void deleteUser_expectFallback() {
        ResponseEntity<?> response = authServiceClient.deleteUser(USERNAME);

        assertThat(response.getStatusCode(), is(SERVICE_UNAVAILABLE));
        outputCapture.expect(
                containsString(String.format("Error during removing user: %s", USERNAME)));
    }
}
