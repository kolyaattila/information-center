package com.information.center.questionservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "topic-service",fallback = TopicServiceClientFallBack.class)
public interface TopicServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/topic/internal/{topicId}")
    String getTopicNameByTopicId(@PathVariable("topicId") String topicId);
}




