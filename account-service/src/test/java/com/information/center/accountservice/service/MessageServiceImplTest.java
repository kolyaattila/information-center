package com.information.center.accountservice.service;

import com.information.center.accountservice.converter.MessageConverter;
import com.information.center.accountservice.entity.MessageEntity;
import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.model.MessageResponse;
import com.information.center.accountservice.repository.MessageRepository;
import exception.ServiceExceptions;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplTest {

    @InjectMocks
    private MessageServiceImpl messageService;
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private MessageConverter messageConverter;
    private MessageRequest messageRequest;
    private MessageEntity messageEntity;

    @Before
    public void setUp() {
        messageRequest = new MessageRequest();
        messageRequest.setEmail("email");
        messageRequest.setMessage("message");
        messageRequest.setName("name");

        messageEntity = new MessageEntity();
    }

    @Test
    public void testCreateMessage() {
        ArgumentCaptor<MessageEntity> capture = ArgumentCaptor.forClass(MessageEntity.class);
        when(messageConverter.toEntity(messageRequest)).thenReturn(messageEntity);
        when(messageRepository.existsByUid(anyString())).thenReturn(true).thenReturn(false);

        messageService.createMessage(messageRequest);

        verify(messageRepository).save(capture.capture());
        assertNotNull(capture.getValue().getUid());
    }

    @Test
    public void findAll() {
        when(messageRepository.findAllByOrderByCreatedDesc(PageRequest.of(0, 1))).thenReturn(new PageImpl<>(Collections.singletonList(new MessageEntity())));
        Page<MessageResponse> response = messageService.findAll(PageRequest.of(0, 1));
        assertEquals(1, response.getTotalElements());
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void testCreateMessage_expectInsertFailedException() {
        when(messageConverter.toEntity(messageRequest)).thenReturn(messageEntity);
        when(messageRepository.existsByUid(anyString())).thenReturn(true).thenReturn(false);
        when(messageRepository.save(messageEntity)).thenThrow(HibernateException.class);

        messageService.createMessage(messageRequest);
    }
}
