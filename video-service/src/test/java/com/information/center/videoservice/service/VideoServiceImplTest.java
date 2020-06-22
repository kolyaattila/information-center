package com.information.center.videoservice.service;

import com.information.center.videoservice.converter.VideoConverter;
import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.repository.VideoRepository;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VideoServiceImplTest {

    private static final String ROOT_DIR = "videos";
    private static final String TOPIC_ID = "topicId";
    private static final String TITLE = "title";
    private static final String PATH = "path";
    private static final String CHAPTER = "chapter";
    private static final String DESCRIPTION = "description";
    private static final String EXTERNAL_ID = "ExternalId";
    private static final String NEW_CHAPTER = "updateChapter";
    private static final String VIDEO_DURATION = "3665.2344";
    private static final String EXPECTED_VIDEO_DURATION = "1:01:05";
    @Mock
    private FileServiceImpl fileService;
    @Mock
    private VideoRepository videoRepository;
    private VideoConverter videoConverter = Mappers.getMapper(VideoConverter.class);
    private VideoServiceImpl videoService;
    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;
    @Captor
    private ArgumentCaptor<VideoEntity> videoEntityArgumentCaptor;

    @Before
    public void setUp() {
        videoService = new VideoServiceImpl(videoRepository, videoConverter, fileService);
    }

    @Test
    public void create_expectedProperRepoCall() throws IOException {
        videoService.create(createVideoRequest());

        verify(fileService).createFolder(stringArgumentCaptor.capture());
        verify(videoRepository).save(videoEntityArgumentCaptor.capture());
        assertEquals(ROOT_DIR + "/" + CHAPTER +"/"+TOPIC_ID, stringArgumentCaptor.getValue());
        assertsForVideoEntity(videoEntityArgumentCaptor.getValue());
    }

    @Test
    public void update_expectedProperRepoCall() throws IOException {
        when(videoRepository.findByExternalId(any())).thenReturn(Optional.of(createVideoEntity()));

        videoService.update(createVideoDto());

        verify(fileService).createFolder(stringArgumentCaptor.capture());
        assertEquals(ROOT_DIR + "/" + NEW_CHAPTER + "/" + TOPIC_ID, stringArgumentCaptor.getValue());
        verify(fileService).deleteByPath(stringArgumentCaptor.capture());
        assertEquals(ROOT_DIR + "/" + CHAPTER + "/" + TOPIC_ID + "/"+ EXTERNAL_ID + ".mp4", stringArgumentCaptor.getValue());
        verify(videoRepository).save(videoEntityArgumentCaptor.capture());
        assertsForVideoEntity(videoEntityArgumentCaptor.getValue());
    }

    @Test
    public void delete_expectedProperCall() throws Exception {
        videoService.delete(EXTERNAL_ID);
        verify(fileService).deleteByExternalId(stringArgumentCaptor.capture());
        assertEquals(EXTERNAL_ID, stringArgumentCaptor.getValue());

    }

    @Test
    public void findById_expectedResponse() {
        when(videoRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(createVideoEntity()));
        var response = videoService.findByExternalId(EXTERNAL_ID);
        assertsForVideoResponse(response);
    }

    @Test
    public void create_expectedOnlySeconds() throws IOException {
        var videoRequest = createVideoRequest();
        videoRequest.setVideoDuration("45");
        videoService.create(videoRequest);
        verify(videoRepository).save(videoEntityArgumentCaptor.capture());
        assertEquals("0:45", videoEntityArgumentCaptor.getValue().getVideoDuration());

    }
    @Test
    public void create_expectedMinutesAndSeconds() throws IOException {
        var videoRequest = createVideoRequest();
        videoRequest.setVideoDuration("65");
        videoService.create(videoRequest);
        verify(videoRepository).save(videoEntityArgumentCaptor.capture());
        assertEquals("1:05", videoEntityArgumentCaptor.getValue().getVideoDuration());

    }
    @Test
    public void create_expectedMinutesSecondsAndHours() throws IOException {
        var videoRequest = createVideoRequest();
        videoRequest.setVideoDuration("3715");
        videoService.create(videoRequest);
        verify(videoRepository).save(videoEntityArgumentCaptor.capture());
        assertEquals("1:01:55", videoEntityArgumentCaptor.getValue().getVideoDuration());

    }

    private VideoRequest createVideoRequest() {
        var video = new VideoRequest();
        video.setTopicExternalId(TOPIC_ID);
        video.setTitle(TITLE);
        video.setCourseExternalId(CHAPTER);
        video.setDescription(DESCRIPTION);
        video.setVideoDuration(VIDEO_DURATION);
        video.setFile(new MockMultipartFile("files", "filename.txt", "text/plain", "hello".getBytes(StandardCharsets.UTF_8)));
        return video;
    }

    private VideoDto createVideoDto() {
        var video = new VideoDto();
        video.setExternalId(EXTERNAL_ID);
        video.setTopicExternalId(TOPIC_ID);
        video.setTitle(TITLE);
        video.setPath(PATH);
        video.setCourseExternalId(NEW_CHAPTER);
        video.setDescription(DESCRIPTION);
        video.setVideoDuration(VIDEO_DURATION);
        video.setFile(new MockMultipartFile("files", "filename.txt", "text/plain", "hello".getBytes(StandardCharsets.UTF_8)));
        return video;
    }

    private VideoEntity createVideoEntity() {
        return new VideoEntity(1, EXTERNAL_ID, PATH, TITLE, DESCRIPTION, CHAPTER, TOPIC_ID, EXPECTED_VIDEO_DURATION, new Date());
    }

    private void assertsForVideoEntity(VideoEntity videoEntity) {
        assertEquals(DESCRIPTION, videoEntity.getDescription());
        assertEquals(TITLE, videoEntity.getTitle());
        assertEquals(EXPECTED_VIDEO_DURATION, videoEntity.getVideoDuration());
    }

    private void assertsForVideoResponse(VideoResponse videoResponse) {
        assertEquals(DESCRIPTION, videoResponse.getDescription());
        assertEquals(TITLE, videoResponse.getTitle());
        assertEquals(CHAPTER, videoResponse.getCourseExternalId());
        assertEquals(EXPECTED_VIDEO_DURATION, videoResponse.getVideoDuration());
    }
}
