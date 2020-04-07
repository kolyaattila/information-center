package com.information.center.topicservice.service;


import com.information.center.topicservice.converter.TopicConverter;
import com.information.center.topicservice.entity.TopicEntity;
import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import com.information.center.topicservice.repository.TopicRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopicServiceImpl implements TopicService{

    private final TopicConverter topicConverter;

    private final TopicRepository topicRepository;

    @Override
    public TopicResponse create(TopicRequest topicRequest) {

        var topic = topicConverter.toEntity(topicRequest);

        topic.setExternalId(UUID.randomUUID().toString());

        return topicConverter.toResponse(topicRepository.save(topic));
    }

    @Override
    public void update(TopicResponse topicResponse) {

        var topic = findById(topicResponse.getExternalId());
        var topicPersistent = topicConverter.toEntity(topicResponse, topic.getId());
        topicPersistent.setExternalId(topic.getExternalId());

        topicRepository.save(topicPersistent);
    }

    @Override
    public TopicResponse findByExternalId(String externalId) {

        return topicConverter.toResponse(findById(externalId));
    }

    @Override
    public Page<TopicResponse> findAll(Pageable pageable) {
        return topicRepository.findAll(pageable)
                .map(topicConverter::toResponse);
    }

    @Override
    public void delete(String externalId) {

        var topic = findById(externalId);
        topicRepository.delete(topic);
    }

    private TopicEntity findById(String externalId) {
        return topicRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("topic", externalId));
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

    @Override
    public String getNameById(String externalId) {
        var topic = topicRepository.findTopicByExternalId(externalId).orElseThrow(throwNotFoundItem("topic", externalId));
        return topic.getName();
    }
}
