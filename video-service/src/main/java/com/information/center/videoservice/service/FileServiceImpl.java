package com.information.center.videoservice.service;

import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.repository.VideoRepository;
import exception.MicroserviceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileServiceImpl implements FileService {

    private final VideoRepository videoRepository;

    public void saveVideo(MultipartFile file, String path, String externalId) throws IOException {
        File convertFile = new File(path + "/" + externalId + ".mp4");

        if (!convertFile.createNewFile())
            throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't create directory on path " + convertFile.getName());


        try (FileOutputStream fOut = new FileOutputStream(convertFile)) {
            fOut.write(file.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createFolder(String path) {
        File file = new File(path);
        if (!file.exists()) {
            boolean bool = file.mkdir();
            if (!bool)
                throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't create directory on path " + path);
        }
    }

    public void deleteByExternalId(String externalId) throws Exception {
        VideoEntity video = findById(externalId);
        deleteByPath(video.getPath() + "/" + video.getExternalId() + ".mp4");
        deleteIfEmpty(new File(video.getPath()));
        videoRepository.delete(video);
    }

    public void deleteByPath(String path) {
        File file = new File(path);
        if (file.exists()) {
            if (!file.delete())
                throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't delete file on path " + path);
        }
    }


    public void renameFile(String pathF1, String pathF2) {
        File f1 = new File(pathF1);
        File f2 = new File(pathF2);
        if (!f1.renameTo(f2))
            throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't rename file " + pathF1 + " to " + pathF2);
    }


    public void deleteIfEmpty(File file) throws IOException {
        if (Objects.requireNonNull(file.list()).length == 0)
            if (!file.delete())
                throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't delete file" + file.getName());

    }

    public VideoEntity findById(String externalId) {

        return videoRepository.findByExternalId(externalId)
                .orElseThrow(throwNotFoundItem("video", externalId));
    }

    private Supplier<MicroserviceException> throwNotFoundItem(String item, String itemId) {
        return () -> new MicroserviceException(HttpStatus.NOT_FOUND,
                "Cannot find " + item + " by id " + itemId);
    }

}
