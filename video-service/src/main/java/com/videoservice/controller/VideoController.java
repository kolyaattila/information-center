package com.videoservice.controller;

import com.videoservice.model.VideoDto;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import com.videoservice.service.VideoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("video")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @GetMapping("/{externalId}")
   public VideoResponse findById(@PathVariable("externalId") String externalId){
        return videoService.findByExternalId(externalId);
    }

    @PostMapping
    public VideoResponse create(@RequestBody VideoRequest videoRequest){
       return videoService.create(videoRequest);
    }

    @PutMapping
    public void update(@RequestBody VideoDto videoDto){
        videoService.update(videoDto);
    }

    @DeleteMapping("/{externalId}")
    public void delete(@PathVariable("externalId") String externalId){
        videoService.delete(externalId);
    }


}
