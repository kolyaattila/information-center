package com.videoservice.service;


import com.videoservice.config.Kafka;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GreetingsListener {
    @StreamListener(Kafka.INPUT)
    public void handleGreetings(@Payload VideoRequest videoRequest) {
        log.info("Received greetings: {}", videoRequest);
    }
}