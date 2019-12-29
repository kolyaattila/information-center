package com.information.center.accountservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.exceptions.ServiceExceptions;
import com.information.center.accountservice.repository.SubscriptionRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(value = MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest {

  @Mock
  private SubscriptionRepository subscriptionRepository;
  @InjectMocks
  private SubscriptionServiceImpl subscriptionService;

  private SubscriptionEntity subscriptionEntity;

  @Before
  public void setUp() {
    subscriptionEntity = new SubscriptionEntity();
    subscriptionEntity.setEmail("email");
    subscriptionEntity.setFirstName("firstName");
    subscriptionEntity.setLastName("lastName");
  }

  @Test
  public void subscription() {
    subscriptionService.subscription(subscriptionEntity);

    verify(subscriptionRepository, times(1)).save(subscriptionEntity);
  }

  @Test(expected = ServiceExceptions.InsertFailedException.class)
  public void subscription_ExpectInsertFailedException() {
    when(subscriptionRepository.save(subscriptionEntity)).thenThrow(new ServiceExceptions.InsertFailedException());

    subscriptionService.subscription(subscriptionEntity);
  }

  @Test(expected = ServiceExceptions.InconsistentDataException.class)
  public void subscription_ExpectInconsistentDataException() {
    when(subscriptionRepository.findByEmail(subscriptionEntity.getEmail()))
        .thenReturn(Optional.of(subscriptionEntity));

    subscriptionService.subscription(subscriptionEntity);
  }
}
