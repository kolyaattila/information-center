package com.information.center.accountservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.information.center.accountservice.client.EmailServiceClient;
import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.repository.SubscriptionRepository;
import exception.ServiceExceptions;
import exception.ServiceExceptions.InconsistentDataException;
import exception.ServiceExceptions.InsertFailedException;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(value = MockitoJUnitRunner.class)
public class SubscriptionServiceImplTest {

  @Mock
  private SubscriptionRepository subscriptionRepository;
  @Mock
  private EmailServiceClient emailServer;
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
    when(subscriptionRepository.save(any())).then(it -> it.getArgument(0));
    when(subscriptionRepository.existsByUid(anyString())).thenReturn(false);
    when(emailServer.sendSubscriptionEmail(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

    subscriptionService.subscription(subscriptionEntity);

    verify(subscriptionRepository, times(1)).save(subscriptionEntity);
  }

  @Test(expected = ServiceExceptions.InsertFailedException.class)
  public void subscription_ExpectInsertFailedException() {
    when(subscriptionRepository.existsByUid(anyString())).thenReturn(false);
    when(subscriptionRepository.save(subscriptionEntity))
        .thenThrow(new ServiceExceptions.InsertFailedException());

    subscriptionService.subscription(subscriptionEntity);
  }

  @Test(expected = ServiceExceptions.InconsistentDataException.class)
  public void subscription_ExpectInconsistentDataException() {
    when(subscriptionRepository.existsByUid(anyString())).thenReturn(false);
    when(subscriptionRepository.findByEmail(subscriptionEntity.getEmail()))
        .thenReturn(Optional.of(subscriptionEntity));

    subscriptionService.subscription(subscriptionEntity);
  }

  @Test
  public void subscription_getUidCallTwoTimes() {
    when(subscriptionRepository.existsByUid(anyString())).thenReturn(true).thenReturn(false);
    when(emailServer.sendSubscriptionEmail(any())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
    when(subscriptionRepository.findByEmail(subscriptionEntity.getEmail()))
        .thenReturn(Optional.empty());
    when(subscriptionRepository.save(any())).then(it -> it.getArgument(0));

    subscriptionService.subscription(subscriptionEntity);
    verify(subscriptionRepository, times(2)).existsByUid(anyString());
  }

  @Test
  public void subscriptionActivation(){
    when(subscriptionRepository.findByUid(anyString())).thenReturn(Optional.of(subscriptionEntity));

    subscriptionService.subscriptionActivation("uid");

    verify(subscriptionRepository, times(1)).findByUid(anyString());
    verify(subscriptionRepository, times(1)).save(subscriptionEntity);
  }

  @Test(expected = InconsistentDataException.class)
  public void subscriptionActivation_ExpectInconsistentDataException(){
    when(subscriptionRepository.findByUid(anyString())).thenReturn(Optional.empty());

    subscriptionService.subscriptionActivation("uid");

    verify(subscriptionRepository, times(1)).findByUid(anyString());
    verify(subscriptionRepository, times(0)).save(any());
  }

  @Test(expected = ServiceExceptions.InsertFailedException.class)
  public void subscriptionActivation_ExpectInsertFailedException(){
    when(subscriptionRepository.findByUid(anyString())).thenReturn(Optional.of(subscriptionEntity));
    when(subscriptionRepository.save(any())).thenThrow(DuplicateKeyException.class);

    subscriptionService.subscriptionActivation("uid");

    verify(subscriptionRepository, times(1)).findByUid(anyString());
    verify(subscriptionRepository, times(1)).save(any());
  }

  @Test(expected = ServiceExceptions.InsertFailedException.class)
  public void unsubscription_ExpectInsertFailedException(){
    when(subscriptionRepository.findByUid(anyString())).thenReturn(Optional.of(subscriptionEntity));
    when(subscriptionRepository.save(any())).thenThrow(DuplicateKeyException.class);

    subscriptionService.unsubscription("uid");

    verify(subscriptionRepository, times(1)).findByUid(anyString());
    verify(subscriptionRepository, times(1)).save(any());
  }

  @Test(expected = ServiceExceptions.InconsistentDataException.class)
  public void unsubscription_ExpectInconsistentDataException(){
    when(subscriptionRepository.findByUid(anyString())).thenReturn(Optional.empty());

    subscriptionService.unsubscription("uid");

    verify(subscriptionRepository, times(1)).findByUid(anyString());
    verify(subscriptionRepository, times(0)).save(any());
  }

  @Test
  public void unsubscription(){
    when(subscriptionRepository.findByUid(anyString())).thenReturn(Optional.of(subscriptionEntity));

    subscriptionService.unsubscription("uid");

    verify(subscriptionRepository, times(1)).findByUid(anyString());
    verify(subscriptionRepository, times(1)).save(subscriptionEntity);
  }
}
