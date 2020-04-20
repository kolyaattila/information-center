package com.information.center.accountservice.service;

import com.information.center.accountservice.model.MessageRequest;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void createMessage(MessageRequest messageRequest);
}
