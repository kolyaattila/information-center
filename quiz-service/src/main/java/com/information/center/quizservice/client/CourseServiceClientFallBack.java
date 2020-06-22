package com.information.center.quizservice.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CourseServiceClientFallBack implements CourseServiceClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceClientFallBack.class);

    @Override
    public String getTopicNameByTopicId(String topicId) {
        LOGGER.error("Error getting topic name by topicId: {}", topicId);
        return "";
    }
}
