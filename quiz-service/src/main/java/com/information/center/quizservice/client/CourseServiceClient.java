package com.information.center.quizservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "course-service", fallback = CourseServiceClientFallBack.class)
public interface CourseServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/topic/internal/{topicId}")
    String getTopicNameByTopicId(@PathVariable("topicId") String topicId);
}




