package com.information.center.courseservice.controller;

import com.information.center.courseservice.model.request.TopicRequest;
import com.information.center.courseservice.model.response.TopicResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/topic")
public interface TopicEndpoint {

    @PostMapping
    TopicResponse create(@RequestBody @Valid TopicRequest topicResponse);

    @PutMapping
    void update(@RequestBody @Valid TopicRequest topicRequest);

    @GetMapping("/{externalId}")
    TopicResponse findByExternalId(@PathVariable("externalId") String externalId);

    @GetMapping
    List<TopicResponse> findAll();

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId);
}
