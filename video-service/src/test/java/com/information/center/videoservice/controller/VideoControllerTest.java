package com.information.center.videoservice.controller;

import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.service.VideoService;
import com.information.center.videoservice.service.VideoServiceImpl;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

//@RunWith(MockitoJUnitRunner.class)
public class VideoControllerTest {

    private static final String EXTERNAL_ID = "ab9039bf-caf1-4d91-a8e2-2f0a2bec0a00";
    private static final String TOPIC_ID = "ab9039bf-caf1-4d91-a8e2-2f0a2bec0a00";
    @InjectMocks
    private VideoController videoController;
    @Mock
    private VideoServiceImpl videoService;
    private VideoResponse videoResponse;
    private VideoRequest videoRequest;

    @Before
    public void setUp() {
        videoRequest = VideoRequest.builder().build();
        videoResponse = VideoResponse.builder().build();
    }

//    @Test
//    public void create_expectResponse() throws IOException {
//        when(videoService.create(videoRequest)).thenReturn(videoResponse);
//
//        VideoResponse response = videoController
//                .create(videoRequest);
//
//        assertEquals(response, videoResponse);
//    }
//
//    @Test
//    public void findById_expectResponse() throws IOException {
//        when(videoService.findByExternalId(EXTERNAL_ID)).thenReturn(videoResponse);
//
//        VideoResponse response = videoController
//                .findById(EXTERNAL_ID);
//
//        assertEquals(response, videoResponse);
//    }
//
//    @Test
//    public void findAllByTopicId_expectResponse() throws IOException {
//        when(videoService.findAllByTopicId(TOPIC_ID)).thenReturn(new ArrayList<>());
//
//        List<VideoResponse> response = videoController
//                .findAllByTopicId(EXTERNAL_ID);
//
//        assertEquals(response, new ArrayList<>());
//    }
//
//    @Test
//    public void findAllByTopicId_expectedResponse() throws Exception {
//        when(videoService.findAllByTopicId(TOPIC_ID)).thenReturn(new ArrayList<>());
//        var response = videoController.findAllByTopicId(TOPIC_ID);
//        assertNotNull(response);
//    }
//
//    @Test
//    public void getFullVideo_expectedResponse() throws Exception {
//        when(videoService.getFullVideo(EXTERNAL_ID)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
//        var response = videoService.getFullVideo(EXTERNAL_ID);
//        assertEquals(response.getStatusCode(), HttpStatus.OK);
//    }
}
