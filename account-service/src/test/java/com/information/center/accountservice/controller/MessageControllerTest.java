package com.information.center.accountservice.controller;

import com.information.center.accountservice.model.MessageRequest;
import com.information.center.accountservice.service.MessageService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class MessageControllerTest {

    private static final String MESSAGE = "message";
    private static final String NAME = "name";
    private static final String EMAIL = "Email";
    @InjectMocks
    private MessageController messageController;
    @Mock
    private MessageService messageService;
    private MessageRequest messageRequest;

    @Before
    public void setUp() {
        messageRequest = MessageRequest.builder()
                .email(EMAIL)
                .name(NAME)
                .message(MESSAGE)
                .build();
    }

    @Test
    public void testCreateMessage() {
        ResponseEntity<?> response = messageController.createMessage(messageRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test(expected = RestExceptions.BadRequest.class)
    public void testCreateMessage_expect() {
        doThrow(ServiceExceptions.InsertFailedException.class).when(messageService).createMessage(messageRequest);

        messageController.createMessage(messageRequest);
    }
}
