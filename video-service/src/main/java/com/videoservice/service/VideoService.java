package com.videoservice.service;

import com.videoservice.config.MicroserviceException;
import com.videoservice.converter.VideoConverter;
import com.videoservice.entity.Video;
import com.videoservice.model.VideoDto;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import com.videoservice.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    private final VideoConverter videoConverter;

//    private final Kafka kafka;

    public VideoResponse findByExternalId(String externalId) {
        return videoConverter.toResponse(findById(externalId));
    }

    public VideoResponse create(VideoRequest videoRequest) {
        Video video = videoConverter.toEntity(videoRequest);
        video.setExternalId(UUID.randomUUID().toString());
//
//        if (videoRequest != null) {
//
//            MessageChannel messageChannel = kafka.outboundGreetings();
//            messageChannel.send(MessageBuilder
//                    .withPayload(videoRequest)
//                    .build());
//        }
        return videoConverter.toResponse(videoRepository.save(video));
    }

    public void update(VideoDto videoDto) {

        Video video = findById(videoDto.getExternalId());
        Video videoPersistent = videoConverter.toEntity(videoDto);
        videoPersistent.setId(video.getId());
    videoRepository.save(videoPersistent);
    }

    public void delete(String externalId) {
        Video video = findById(externalId);
        videoRepository.delete(video);
    }
    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }
    public Video findById(String externalId){

        return videoRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("video",externalId));
    }
    }

