package com.videoservice.service;

import com.videoservice.converter.VideoConverter;
import com.videoservice.entity.Video;
import com.videoservice.model.VideoDto;
import com.videoservice.model.VideoRequest;
import com.videoservice.model.VideoResponse;
import com.videoservice.repository.VideoRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    private final VideoConverter videoConverter;

    public VideoResponse findByExternalId(String externalId) {
        return videoConverter.toResponse(findById(externalId));
    }

    public VideoResponse create(VideoRequest videoRequest) throws IOException {
        Video video = videoConverter.toEntity(videoRequest);
        video.setExternalId(UUID.randomUUID().toString());
        String rootDirectory = "videos/";
        String path = rootDirectory + videoRequest.getChapter();
        createFolder(path);
        video.setPath(path);
        if (videoRequest.getFile() != null) {
            createFolder(path);
            saveVideo(videoRequest.getFile(), path, video.getExternalId());
        }
        return videoConverter.toResponse(videoRepository.save(video));
    }

    public void update(VideoDto videoDto) throws IOException {

        Video video = findById(videoDto.getExternalId());
        Video videoPersistent = videoConverter.toEntity(videoDto);
        String videoExternalId = video.getExternalId();
        String rootDirectory = "videos/";
        String path = rootDirectory + videoDto.getChapter();
        videoPersistent.setId(video.getId());
        videoPersistent.setPath(path);
        File videoOldName = new File(rootDirectory + "/" + video.getChapter() + "/" + videoExternalId + ".mp4");

        if (!videoDto.getChapter().equals(video.getChapter())) {
            File chapterFolder = new File(path);
            createFolder(chapterFolder.getPath());
        }
        if (videoDto.getFile().getBytes().length != 0) {
            videoOldName.delete();
            File file = new File(path + "/" + videoExternalId + ".mp4");
            saveVideo(videoDto.getFile(), path, video.getExternalId());
        } else {
            File videoNewName = new File(rootDirectory + "/" + videoDto.getChapter() + "/" + videoExternalId + ".mp4");
            renameFile(videoOldName, videoNewName);
        }
        if (!video.getChapter().equals(videoDto.getChapter()))
            deleteIfEmpty(new File(video.getPath()));
        videoRepository.save(videoPersistent);
    }

    private void renameFile(File f1, File f2) {
        f1.renameTo(f2);
    }

    public void delete(String externalId) {
        Video video = findById(externalId);
        File file = new File(video.getPath() + "/" + video.getExternalId() + ".mp4");
        if (file.exists()) {
            file.delete();
        }
        videoRepository.delete(video);
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

    public Video findById(String externalId) {

        return videoRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("video", externalId));
    }

    private void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            boolean bool = file.mkdir();
            if (!bool)
                throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't create directory on path " + path);
        }
    }

    private void deleteIfEmpty(File file) throws IOException {
        if (Objects.requireNonNull(file.list()).length == 0)
            file.delete();
    }


    private void saveVideo(MultipartFile file, String path, String externalId) throws IOException {
        File convertFile = new File(path + "/" + externalId + ".mp4");

        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<UrlResource> getFullVideo(String externalId) throws MalformedURLException {

        var videoDetails = videoRepository.findByExternalId(externalId).orElseThrow(throwNotFoundItem("video", externalId));
        File file = new File(videoDetails.getPath() + "/" + videoDetails.getExternalId());
        UrlResource video = new UrlResource("file:///" + file.getAbsolutePath() + ".mp4");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(video);
    }

    public List<VideoResponse> findAllByTopicId(String topicId) {
        return videoRepository.findAllByTopicId(topicId).stream().map(videoConverter::toResponse).collect(Collectors.toList());
    }
}

