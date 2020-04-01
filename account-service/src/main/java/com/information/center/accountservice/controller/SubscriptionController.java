package com.information.center.accountservice.controller;

import com.information.center.accountservice.converter.SubscriptionConverter;
import com.information.center.accountservice.entity.SubscriptionEntity;
import com.information.center.accountservice.model.SubscriptionRequest;
import com.information.center.accountservice.service.SubscriptionService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SubscriptionController implements SubscriptionEndpoint {

  private final SubscriptionService subscriptionService;
  private final SubscriptionConverter subscriptionConverter;

  @Override
  public ResponseEntity<?> subscription(
      @Valid @RequestBody SubscriptionRequest subscriptionRequest) {
    SubscriptionEntity subscriptionEntity = subscriptionConverter.toEntity(subscriptionRequest);

    try {
      subscriptionService.subscription(subscriptionEntity);
    } catch (ServiceExceptions.InconsistentDataException | ServiceExceptions.InsertFailedException e) {
      throw new RestExceptions.BadRequest(e.getMessage());
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> subscriptionActivation(@Valid @NotNull @RequestBody String uid) {
    try {
      subscriptionService.subscriptionActivation(uid);
    } catch (ServiceExceptions.InconsistentDataException | ServiceExceptions.InsertFailedException e) {
      throw new RestExceptions.BadRequest(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @Override
  public ResponseEntity<?> unsubscribe(@Valid @NotNull @RequestBody String uid) {
    try {
      subscriptionService.unsubscription(uid);
    } catch (ServiceExceptions.InconsistentDataException | ServiceExceptions.InsertFailedException e) {
      throw new RestExceptions.BadRequest(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
