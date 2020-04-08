package com.information.center.topicservice.service;

import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {

    TopicResponse create(TopicRequest topicRequest);

    void update(TopicRequest topicRequest);

    TopicResponse findByExternalId(String externalId);

    List<TopicResponse> findAll();

    void delete(String externalId);

}
