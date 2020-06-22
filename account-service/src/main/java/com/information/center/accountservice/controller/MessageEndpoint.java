package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.MessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public interface MessageEndpoint {

    @PostMapping("/message")
    ResponseEntity<?> createMessage(@Valid @RequestBody MessageRequest message);

}
