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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.function.Supplier;

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
        String rootDirectory = "";
        String partialPath = rootDirectory + videoRequest.getChapter();
        String path = partialPath + "/" + video.getExternalId();
        video.setPath(path);

        if (videoRequest.getFile() != null) {

            boolean bool = createFolder(path);
            if (!bool) {
                //TOdO throw exception in else statement...
                new Exception();
            }
            saveVideo(videoRequest.getFile(),path);
        }
        return videoConverter.toResponse(videoRepository.save(video));
    }

    public void update(VideoDto videoDto) throws IOException {

        Video video = findById(videoDto.getExternalId());
        Video videoPersistent = videoConverter.toEntity(videoDto);
        String rootDirectory = "";
        String path = ""+videoDto.getChapter()+"/"+videoDto.getExternalId();
        videoPersistent.setId(video.getId());
        videoPersistent.setPath(path);
        if(videoDto.getChapter() != video.getChapter()){
            File f1 = new File(rootDirectory+video.getChapter());
            File f2 = new File(rootDirectory+videoDto.getChapter());
            boolean b = f1.renameTo(f2);
            //TODO THROW exception if b is not true...
        }
        if (videoDto.getFile() != null) {
            File file = new File(path+".mp4");
            if (file.exists()) {
                file.delete();
               saveVideo(videoDto.getFile(),path);
            }
        }
        videoRepository.save(videoPersistent);
    }

    public void delete(String externalId) {
        Video video = findById(externalId);
        File file = new File(video.getPath()+".mp4");
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

    private Boolean createFolder(String path) {
        File file = new File(path);
        boolean bool = file.mkdir();
        return bool;
    }

    private void saveVideo(MultipartFile file,String path) throws IOException {
        File convertFile = new File(path+".mp4");
        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

