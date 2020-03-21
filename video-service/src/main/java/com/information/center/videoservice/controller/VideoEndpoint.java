package com.information.center.videoservice.controller;

import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
public interface VideoEndpoint {

  @GetMapping("/{externalId}")
  VideoResponse findById(@PathVariable("externalId") String externalId);

  @PostMapping
  VideoResponse create(@RequestBody VideoRequest videoRequest);

  @PutMapping
  void update(@RequestBody VideoDto videoDto);

  @DeleteMapping("/{externalId}")
  void delete(@PathVariable("externalId") String externalId);
}
