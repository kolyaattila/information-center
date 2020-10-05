package com.information.center.courseservice.service;

import com.information.center.courseservice.converter.VideoConverter;
import com.information.center.courseservice.entity.TopicEntity;
import com.information.center.courseservice.entity.VideoEntity;
import com.information.center.courseservice.model.VideoDto;
import com.information.center.courseservice.model.VideoRequest;
import com.information.center.courseservice.model.VideoResponse;
import com.information.center.courseservice.repository.TopicRepository;
import com.information.center.courseservice.repository.VideoRepository;
import exception.MicroserviceException;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoConverter videoConverter;
    private final AmazonClient amazonClient;
    private final TopicRepository topicRepository;

    @Override
    public VideoResponse findByExternalId(String externalId) {
        return videoConverter.toResponse(findById(externalId));
    }

    @Override
    public VideoResponse create(VideoRequest videoRequest) {
        VideoEntity video = videoConverter.toEntity(videoRequest);
        video.setTopic(getTopic(videoRequest));
        video.setExternalId(UUID.randomUUID().toString());
        video.setVideoDuration(convertSecondsToTime(videoRequest.getVideoDuration()));
        if (videoRequest.getFile() != null) {
            var filePath = amazonClient.uploadFile(videoRequest.getFile(), video.getExternalId());
            video.setPath(filePath);
            return videoConverter.toResponse(videoRepository.save(video));
        }
        throw new MicroserviceException(HttpStatus.BAD_REQUEST, "no video to be saved");
    }

    private TopicEntity getTopic(VideoRequest videoRequest) {
        return topicRepository.findByExternalId(videoRequest.getTopicExternalId())
                .orElseThrow(() -> new ServiceExceptions.NotFoundException(String.format("Topic with id %s not found", videoRequest.getTopicExternalId())));
    }

    @Override
    public void update(VideoDto videoDto) throws IOException {

        VideoEntity video = findById(videoDto.getExternalId());
        VideoEntity videoPersistent = videoConverter.toEntity(videoDto);
        videoPersistent.setId(video.getId());

        if (videoDto.getFile() != null && videoDto.getFile().getBytes().length != 0) {
            videoPersistent.setVideoDuration(convertSecondsToTime(videoDto.getVideoDuration()));
            amazonClient.deleteFileFromS3Bucket(video.getPath());
            var videoPath = amazonClient.uploadFile(videoDto.getFile(), video.getExternalId());
            videoPersistent.setPath(videoPath);
        }
        videoRepository.save(videoPersistent);
    }

    @Override
    public void delete(String externalId) {
        VideoEntity video = findById(externalId);
        videoRepository.delete(video);
        amazonClient.deleteFileFromS3Bucket(video.getPath());
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
    public List<VideoResponse> findAllByTopicId(String topicId) {
        return videoRepository.findAllByTopicExternalId(topicId).stream().map(videoConverter::toResponse).collect(Collectors.toList());
    }

    @Override
    public List<VideoResponse> findAllByCourseId(String courseId) {
        return videoRepository.findAllByCourseExternalId(courseId).stream().map(videoConverter::toResponse).collect(Collectors.toList());
    }

    private String convertSecondsToTime(String totalSeconds) {
        String finalTime = "";
        if (totalSeconds.contains("."))
            totalSeconds = totalSeconds.substring(0, totalSeconds.indexOf("."));
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

