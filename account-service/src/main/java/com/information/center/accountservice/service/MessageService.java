package com.information.center.accountservice.service;

import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.model.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    void createMessage(MessageRequest messageRequest);

    Page<MessageResponse> findAll(Pageable pageable);

}
