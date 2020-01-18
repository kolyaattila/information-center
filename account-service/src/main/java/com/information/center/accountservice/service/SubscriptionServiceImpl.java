package com.information.center.accountservice.service;

import com.information.center.accountservice.client.EmailServiceClient;
import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.model.EmailSubscriptionRequest;
import com.information.center.accountservice.repository.SubscriptionRepository;
import exception.ServiceExceptions;
import exception.ServiceExceptions.InconsistentDataException;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionServiceImpl implements SubscriptionService {

  private final static Logger logger = Logger.getLogger(SubscriptionServiceImpl.class.getName());

  private final SubscriptionRepository subscriptionRepository;
  private final EmailServiceClient emailServer;

  @Override
  public void subscription(SubscriptionEntity subscriptionEntity) {

    subscriptionEntity.setCreated(new Date());
    subscriptionEntity.setUid(getUid());
    findSubscription(subscriptionEntity.getEmail());
    try {
      SubscriptionEntity entity = subscriptionRepository.save(subscriptionEntity);
      sendSubscriptionEmail(entity);
    } catch (Exception e) {
      throw new ServiceExceptions.InsertFailedException("Error subscription");
    }
  }

  @Override
  public void subscriptionActivation(String uid) {
    SubscriptionEntity subscriptionEntity = getSubscriptionEntity(uid);
    try {
      subscriptionEntity.setEmailValidation(true);
      subscriptionRepository.save(subscriptionEntity);
    } catch (Exception e) {
      throw new ServiceExceptions.InsertFailedException("Error subscription activation");
    }
  }

  @Override
  public void unsubscription(String uid) {
    SubscriptionEntity subscriptionEntity = getSubscriptionEntity(uid);
    try {
      subscriptionEntity.setUnsubscription(true);
      subscriptionRepository.save(subscriptionEntity);
    } catch (Exception e) {
      throw new ServiceExceptions.InsertFailedException("Error unsubscription ");
    }
  }

  private void sendSubscriptionEmail(SubscriptionEntity entity) {
    EmailSubscriptionRequest build = EmailSubscriptionRequest.builder().to(entity.getEmail())
        .firstName(entity.getFirstName())
        .lastName(entity.getLastName())
        .uid(entity.getUid())
        .build();

    emailServer.sendSubscriptionEmail(Collections.singletonList(build));
    logger.info("Subscription email has been sent.");
  }

  private void findSubscription(String email) {
    subscriptionRepository.findByEmail(email).ifPresent(item -> {
      throw new ServiceExceptions.InconsistentDataException("You already subscribed!");
    });
  }

  private String getUid() {
    String uid = UUID.randomUUID().toString();

    if (subscriptionRepository.existsByUid(uid)) {
      return getUid();
    }
    return uid;
  }

  private SubscriptionEntity getSubscriptionEntity(String uid) {
    return subscriptionRepository.findByUid(uid)
        .orElseThrow(() -> new InconsistentDataException("Not found subscription with uid " + uid));
  }

}
