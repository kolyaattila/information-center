package com.information.center.videoservice.service;

import com.information.center.videoservice.entity.VideoEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public interface FileService {

    void saveVideo(MultipartFile file, String path, String externalId) throws IOException;

    void createFolder(String path);

    void deleteByExternalId(String externalId) throws Exception;

    void deleteByPath(String path);

    void renameFile(String pathF1, String pathF2);

    void deleteIfEmpty(File file) throws IOException;

    VideoEntity findById(String externalId);
}
