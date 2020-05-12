package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.model.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public interface MessageEndpoint {

    @PostMapping("/message")
    ResponseEntity<?> createMessage(@Valid @RequestBody MessageRequest message);

    Page<MessageResponse> findAll(Pageable pageable);
}
