package com.information.center.topicservice.service;

import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface TopicService {
    TopicResponse create(TopicRequest topicRequest);

    void update(TopicResponse topicResponse);

    TopicResponse findByExternalId(String externalId);

    Page<TopicResponse> findAll(Pageable pageable);

    void delete(String externalId);

    String getNameById(String externalId);
}
