package com.information.center.videoservice.service;

import com.information.center.videoservice.converter.VideoConverter;
import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.model.VideoDto;
import com.information.center.videoservice.model.VideoRequest;
import com.information.center.videoservice.model.VideoResponse;
import com.information.center.videoservice.repository.VideoRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    private final VideoConverter videoConverter;

    private final FileServiceImpl fileService;

    @Override
    public VideoResponse findByExternalId(String externalId) {
        return videoConverter.toResponse(findById(externalId));
    }

    @Override
    public VideoResponse create(VideoRequest videoRequest) throws IOException {
        VideoEntity video = videoConverter.toEntity(videoRequest);
        video.setExternalId(UUID.randomUUID().toString());
        video.setVideoDuration(convertSecondsToTime(videoRequest.getVideoDuration()));
        String rootDirectory = "videos/";
        String path = rootDirectory + videoRequest.getCourseExternalId() + "/" + videoRequest.getTopicExternalId();
        video.setPath(path);
        if (videoRequest.getFile() != null) {
            var videoResponse = videoConverter.toResponse(videoRepository.save(video));
            fileService.createFolder(path);
            fileService.saveVideo(videoRequest.getFile(), path, video.getExternalId());
            return videoResponse;
        }
        throw new MicroserviceException(HttpStatus.BAD_REQUEST, "no video to be saved");
    }

    @Override
    public void update(VideoDto videoDto) throws IOException {

        VideoEntity video = findById(videoDto.getExternalId());
        VideoEntity videoPersistent = videoConverter.toEntity(videoDto);
        String videoExternalId = video.getExternalId();
        String rootDirectory = "videos";
//        String path = rootDirectory + "/" + videoDto.getChapter();
        videoPersistent.setId(video.getId());
//        videoPersistent.setPath(path);
//        String videoOldName = createVideoPath(rootDirectory, video.getChapter(), videoExternalId);

//        if (!videoDto.getChapter().equals(video.getChapter())) {
//            fileService.createFolder(path);
//        }
        if (videoDto.getFile() != null) {
            if (videoDto.getFile().getBytes().length != 0) {
                videoPersistent.setVideoDuration(convertSecondsToTime(videoDto.getVideoDuration()));
//                fileService.deleteByPath(videoOldName);
//                fileService.saveVideo(videoDto.getFile(), path, video.getExternalId());
            }
        } else {
//            String videoNewName = createVideoPath(rootDirectory, videoDto.getChapter(), videoExternalId);
//            fileService.renameFile(videoOldName, videoNewName);
        }
//        if (!video.getChapter().equals(videoDto.getChapter()))
//            fileService.deleteIfEmpty(new File(video.getPath()));
        videoRepository.save(videoPersistent);
    }

    @Override
    public void delete(String externalId) throws Exception {
        fileService.deleteByExternalId(externalId);
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

    @Override
    public VideoEntity findById(String externalId) {

        return videoRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("video", externalId));
    }

    @Override
    public ResponseEntity<UrlResource> getFullVideo(String externalId) throws MalformedURLException {

        var videoDetails = videoRepository.findByExternalId(externalId).orElseThrow(throwNotFoundItem("video", externalId));
        File file = new File(videoDetails.getPath() + "/" + videoDetails.getExternalId());
        UrlResource video = new UrlResource("file:///" + file.getAbsolutePath() + ".mp4");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(video);
    }

    @Override
    public List<VideoResponse> findAllByTopicId(String topicId) {
        return videoRepository.findAllByTopicExternalId(topicId).stream().map(videoConverter::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<VideoResponse> findAllByCourseId(String courseId) {
        return videoRepository.findAllByCourseExternalId(courseId).stream().map(videoConverter::toResponse).collect(Collectors.toList());
    }


    private String createVideoPath(String root, String chapter, String externalId) {
        return root + "/" + chapter + "/" + externalId + ".mp4";
    }

    private String convertSecondsToTime(String totalSeconds) {
        String finalTime = "";
        if(totalSeconds.contains("."))
            totalSeconds = totalSeconds.substring(0,totalSeconds.indexOf("."));
        int secondsCount = Integer.parseInt(totalSeconds);
        long seconds = secondsCount % 60;
        secondsCount -= seconds;
        long minutesCount = secondsCount / 60;
        long minutes = minutesCount % 60;
        minutesCount -= minutes;
        long hoursCount = minutesCount / 60;

        if (hoursCount != 0)
            finalTime = "" + hoursCount;
        if (String.valueOf(minutes).length() == 1 && hoursCount != 0)
            finalTime += ":0" + minutes;
        else
            finalTime += minutes;
        if (String.valueOf(seconds).length() == 1)
            finalTime += ":0" + seconds;
        else
            finalTime += ":" + seconds;

        return finalTime;
    }

}

