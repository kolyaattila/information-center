package com.information.center.accountservice.service;

import com.information.center.accountservice.entity.SubscriptionEntity;
import org.springframework.stereotype.Service;

@Service
public interface SubscriptionService {

  void subscription(SubscriptionEntity subscriptionEntity);
}
