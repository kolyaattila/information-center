package com.information.center.topicservice.controller;

import com.information.center.topicservice.model.request.TopicRequest;
import com.information.center.topicservice.model.response.TopicResponse;
import com.information.center.topicservice.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public TopicResponse create(@RequestBody TopicRequest topicResponse) {

        return topicService.create(topicResponse);
    }
    @GetMapping("/internal/{externalId}")
    public String getNameById(@PathVariable("externalId")String externalId){
       return topicService.getNameById(externalId);
    }

    @PutMapping
    public void update(@RequestBody TopicResponse topicResponse) {
        topicService.update(topicResponse);
    }

    @GetMapping("/{externalId}")
    public TopicResponse findByExternalId(@PathVariable("externalId") String externalId) {
        return topicService.findByExternalId(externalId);
    }
    @GetMapping
    public Page<TopicResponse> findAll(Pageable pageable) {
        return topicService.findAll(pageable);
    }

    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId) {

        topicService.delete(externalId);
    }
}
