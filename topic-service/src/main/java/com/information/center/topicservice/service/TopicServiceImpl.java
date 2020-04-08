package com.information.center.topicservice.service;


import com.information.center.topicservice.converter.TopicConverter;
import com.information.center.topicservice.entity.TopicEntity;
import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import com.information.center.topicservice.repository.TopicRepository;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopicServiceImpl implements TopicService {

    private final TopicConverter topicConverter;
    private final TopicRepository topicRepository;

    public TopicResponse create(TopicRequest topicRequest) {
        var topic = topicConverter.toEntity(topicRequest);
        topic.setExternalId(getUid());
        try {
            return topicConverter.toResponse(topicRepository.save(topic));
        } catch (Exception e) {
            throw new InsertFailedException("Can not create topic with extID:" + topic.getExternalId());
        }
    }

    public void update(TopicRequest topicRequest) {
        var topic = findById(topicRequest.getExternalId());
        var topicPersistent = topicConverter.toEntity(topicRequest, topic.getId());

        try {
            topicRepository.save(topicPersistent);
        } catch (Exception e) {
            throw new InsertFailedException(
                    "Can not update topic with extId: " + topicPersistent.getExternalId());
        }
    }

    public TopicResponse findByExternalId(String externalId) {
        return topicConverter.toResponse(findById(externalId));
    }

    public List<TopicResponse> findAll() {
        return topicRepository.findAll().stream()
                .map(topicConverter::toResponse)
                .collect(Collectors.toList());
    }

    public void delete(String externalId) {
        var topic = findById(externalId);
        topicRepository.delete(topic);
    }

    private TopicEntity findById(String externalId) {
        return topicRepository.findByExternalId(externalId).orElseThrow(throwNotFoundItem(externalId));
    }

    private Supplier<NotFoundException> throwNotFoundItem(String itemId) {
        return () -> new NotFoundException("Cannot find topic by id " + itemId);
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (topicRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }
}
