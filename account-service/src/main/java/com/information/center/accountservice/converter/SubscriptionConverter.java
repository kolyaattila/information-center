package com.information.center.accountservice.converter;

import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.model.SubscriptionRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionConverter {

  SubscriptionEntity toEntity(SubscriptionRequest request);

}
