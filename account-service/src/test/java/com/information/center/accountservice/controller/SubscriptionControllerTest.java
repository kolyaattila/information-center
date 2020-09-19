package com.information.center.accountservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.information.center.accountservice.converter.SubscriptionConverter;
import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.model.SubscriptionRequest;
import com.information.center.accountservice.service.SubscriptionService;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest
public class SubscriptionControllerTest {

  private static final String URL_TEMPLATE = "/account/subscription";
  @InjectMocks
  private SubscriptionController subscriptionController;
  @Mock
  private SubscriptionService subscriptionService;
  @Mock
  private SubscriptionConverter subscriptionConverter;

  private MockMvc mockMvc;
  private SubscriptionRequest subscriptionRequest;
  private SubscriptionEntity subscriptionEntity;

  @Before
  public void setUp() {

    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(subscriptionController).build();

    subscriptionEntity = new SubscriptionEntity();
    subscriptionEntity.setEmail("email@email.com");
    subscriptionEntity.setFirstName("firstName");
    subscriptionEntity.setLastName("lastName");

    subscriptionRequest = SubscriptionRequest.builder()
        .email("email@email.com")
        .firstName("firstName")
        .lastName("lastName")
        .build();
  }

  @Test
  public void subscription_expectStatusOk() throws Exception {

    mockMvc.perform(post(URL_TEMPLATE)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(subscriptionRequest)))
        .andExpect(status().isOk());
  }

  @Test
  public void subscription_isBadRequest() throws Exception {
    when(subscriptionConverter.toEntity(any())).thenReturn(subscriptionEntity);
    doThrow(InconsistentDataException.class).when(subscriptionService)
        .subscription(subscriptionEntity);

    mockMvc.perform(post(URL_TEMPLATE)
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(subscriptionRequest)))
        .andExpect(status().isBadRequest());
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
  public void subscriptionActivationExpectStatusBadRequest_ServiceThrowException()
      throws Exception {
    doThrow(InsertFailedException.class).when(subscriptionService).subscriptionActivation("335");

    mockMvc.perform(post(URL_TEMPLATE + "/activation")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(335)))
        .andExpect(status().isBadRequest());
  }

  @Test
  public void unsubscription_expectStatusOk() throws Exception {
    mockMvc.perform(post(URL_TEMPLATE + "/unsubscription")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(3525)))
        .andExpect(status().isOk());
  }

  @Test
  public void unsubscription_isBadRequest() throws Exception {
    doThrow(InsertFailedException.class).when(subscriptionService).unsubscription("3525");

    mockMvc.perform(post(URL_TEMPLATE + "/unsubscription")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .content(asJSONString(3525)))
        .andExpect(status().isBadRequest());
  }

  private <T> String asJSONString(T anObject) throws Exception {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    ObjectWriter ow = mapper.writer();
    return ow.writeValueAsString(anObject);
  }
}
