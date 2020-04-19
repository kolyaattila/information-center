package com.information.center.accountservice.service;

import com.information.center.accountservice.converter.MessageConverter;
import com.information.center.accountservice.entity.MessageEntity;
import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.repository.MessageRepository;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final MessageConverter converter;

    @Override
    public void createMessage(MessageRequest messageRequest) {
        MessageEntity entity = converter.toEntity(messageRequest);
        entity.setUid(getUid());
        try {
            messageRepository.save(entity);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException("Can not save the message");
        }
    }


    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (messageRepository.existsByUid(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }
}
