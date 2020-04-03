package com.information.center.topicservice.controller;

import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
public interface TopicEndpoint {
    @PostMapping
    TopicResponse create(@RequestBody TopicRequest topicResponse);

    @GetMapping("/internal/{externalId}")
    String getNameById(@PathVariable("externalId") String externalId);

    @PutMapping
    void update(@RequestBody TopicResponse topicResponse);

    @GetMapping("/{externalId}")
    TopicResponse findByExternalId(@PathVariable("externalId") String externalId);

    @GetMapping
    Page<TopicResponse> findAll(Pageable pageable);

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId);
}
