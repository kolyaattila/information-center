package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.model.MessageResponse;
import com.information.center.accountservice.service.MessageService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController implements MessageEndpoint {

    private final MessageService messageService;

    @Override
    public ResponseEntity<?> createMessage(@Valid @RequestBody MessageRequest message) {
        try {
            messageService.createMessage(message);
        } catch (ServiceExceptions.InsertFailedException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public Page<MessageResponse> findAll(Pageable pageable) {
        return messageService.findAll(pageable);
    }
}
