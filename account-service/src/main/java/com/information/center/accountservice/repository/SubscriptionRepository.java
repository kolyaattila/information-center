package com.information.center.accountservice.repository;

import com.information.center.accountservice.entity.SubscriptionEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends CrudRepository<SubscriptionEntity, Long> {

  Optional<SubscriptionEntity> findByEmail(String email);
}
