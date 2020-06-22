package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.request.TopicRequest;
import com.information.center.courseservice.model.response.TopicResponse;
import com.information.center.courseservice.service.TopicService;
import exception.RestExceptions.BadRequest;
import exception.ServiceExceptions.InsertFailedException;
import exception.ServiceExceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopicController implements TopicEndpoint {

    private final TopicService topicService;

    @Override
    public TopicResponse create(@RequestBody @Valid TopicRequest topicRequest) {
        try {
            return topicService.create(topicRequest);
        } catch (InsertFailedException | NotFoundException e) {
            e.printStackTrace();
            throw new BadRequest(e.getMessage());
        }
    }

    @Override
    public void update(@RequestBody @Valid TopicRequest topicRequest) {
        try {
            topicService.update(topicRequest);
        } catch (InsertFailedException | NotFoundException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    @Override
    public TopicResponse findByExternalId(@PathVariable("externalId") String externalId) {
        try {
            return topicService.findByExternalId(externalId);
        } catch (NotFoundException e) {
            throw new BadRequest(e.getMessage());
        }
    }

    @Override
    public List<TopicResponse> findAll() {
        return topicService.findAll();
    }

    @Override
    public void delete(@PathVariable("externalId") String externalId) {
        try {
            topicService.delete(externalId);
        } catch (NotFoundException e) {
            throw new BadRequest(e.getMessage());
        }
    }
}
