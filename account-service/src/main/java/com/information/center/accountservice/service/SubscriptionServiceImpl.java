package com.information.center.accountservice.service;

import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.repository.SubscriptionRepository;
import exception.ServiceExceptions;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionServiceImpl implements SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;

  @Override
  public void subscription(SubscriptionEntity subscriptionEntity) {

    subscriptionEntity.setCreated(new Date());
    findSubscription(subscriptionEntity.getEmail());
    try {
      subscriptionRepository.save(subscriptionEntity);
    } catch (Exception e) {
      throw new ServiceExceptions.InsertFailedException("Error subscription");
    }
  }

  private void findSubscription(String email) {
    subscriptionRepository.findByEmail(email).ifPresent(item -> {
      throw new ServiceExceptions.InconsistentDataException("Already subscripted");
    });
  }

}
