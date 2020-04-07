package com.information.center.topicservice.controller;

import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import com.information.center.topicservice.service.TopicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopicController implements TopicEndpoint {

    private final TopicServiceImpl topicServiceImpl;

    @Override
    @PostMapping
    public TopicResponse create(@RequestBody TopicRequest topicResponse) {

        return topicServiceImpl.create(topicResponse);
    }

    @Override
    @GetMapping("/internal/{externalId}")
    public String getNameById(@PathVariable("externalId") String externalId) {
        return topicServiceImpl.getNameById(externalId);
    }

    @Override
    @PutMapping
    public void update(@RequestBody TopicResponse topicResponse) {
        topicServiceImpl.update(topicResponse);
    }

    @Override
    @GetMapping("/{externalId}")
    public TopicResponse findByExternalId(@PathVariable("externalId") String externalId) {
        return topicServiceImpl.findByExternalId(externalId);
    }

    @Override
    @GetMapping
    public Page<TopicResponse> findAll(Pageable pageable) {
        return topicServiceImpl.findAll(pageable);
    }

    @Override
    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId) {

        topicServiceImpl.delete(externalId);
    }
}