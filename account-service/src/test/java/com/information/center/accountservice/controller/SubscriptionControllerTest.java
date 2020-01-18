package com.information.center.accountservice.controller;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.information.center.accountservice.converter.SubscriptionConverter;
import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.model.SubscriptionRequest;
import com.information.center.accountservice.service.SubscriptionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubscriptionControllerTest {

  private static final String URL_TEMPLATE = "/subscription";
  @Mock
  private SubscriptionService subscriptionService;
  @Mock
  private SubscriptionConverter subscriptionConverter;
  @InjectMocks
  private SubscriptionController subscriptionController;

  private MockMvc mockMvc;
  private SubscriptionRequest subscriptionRequest;
  private SubscriptionEntity subscriptionEntity;

  @Before
  public void setUp() {

    initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();

    subscriptionEntity = new SubscriptionEntity();
    subscriptionEntity.setEmail("email@email.com");
    subscriptionEntity.setFirstName("firstName");
    subscriptionEntity.setLastName("lastName");

    subscriptionRequest = SubscriptionRequest.builder().email("email@email.com")
        .firstName("firstName").lastName("lastName").build();
  }

  @Test
  public void subscription_expectStatusOk() throws Exception {
    mockMvc.perform(post(URL_TEMPLATE)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(subscriptionRequest)))
        .andExpect(status().isOk());
  }

  @Test
  public void subscriptionActivation_expectStatusOk() throws Exception {
    mockMvc.perform(post(URL_TEMPLATE + "/activation")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(335)))
        .andExpect(status().isOk());
  }

  @Test
  public void subscriptionActivation_expectStatusBadRequest() throws Exception {
    mockMvc.perform(post(URL_TEMPLATE + "/activation")
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void unsubscription_expectStatusOk() throws Exception {
    mockMvc.perform(post(URL_TEMPLATE + "/unsubscription")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(3525)))
        .andExpect(status().isOk());
  }

  private <T> String asJSONString(T anObject) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer();
    return ow.writeValueAsString(anObject);
  }
}
