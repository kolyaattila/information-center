package com.information.center.videoservice.service;

import com.information.center.videoservice.entity.VideoEntity;
import com.information.center.videoservice.repository.VideoRepository;
import exception.MicroserviceException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {

    private static final String EXTERNAL_ID = "externalId";
    private static final String NEW_FOLDER = "newFolder";
    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();
    private FileServiceImpl fileService;
    @Mock
    VideoRepository videoRepository;

    @Before
    public void setUp() {
        fileService = new FileServiceImpl(videoRepository);
    }

    @Test
    public void createVideo_expectedCreatedMp4File() throws IOException {
        File path = tempFolder.getRoot();
        MultipartFile mockMultipartFile = new MockMultipartFile("user-file", "dor.txt",
                "text/plain", "test data".getBytes());
        fileService.saveVideo(mockMultipartFile, path.getPath(), EXTERNAL_ID);
        assertTrue(Files.exists(Paths.get(path.getPath() + "/" + EXTERNAL_ID + ".mp4")));
    }

    @Test
    public void createFolder_expectedCreatedFolder() throws Exception {
        File path = tempFolder.getRoot();
        fileService.createFolder(path.getPath() + "/" + NEW_FOLDER);
        assertTrue(Files.exists(Paths.get(path.getPath() + "/" + NEW_FOLDER)));
    }

    @Test
    public void deleteByExternalId_expectedDeletedFile() throws Exception {
        File path = tempFolder.getRoot();
        createFile(path.getPath() + "/" + EXTERNAL_ID + ".mp4");
        when(videoRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(createVideoEntity(path.getPath())));
        assertTrue(Files.exists(Paths.get(path.getPath() + "/" + EXTERNAL_ID + ".mp4")));
        fileService.deleteByExternalId(EXTERNAL_ID);
        assertFalse(Files.exists(Paths.get(path.getPath() + "/" + EXTERNAL_ID + ".mp4")));
    }

    @Test
    public void deleteByPath_expectedDeletedFile() throws IOException {
        File path = tempFolder.getRoot();
        createFile(path.getPath() + "/" + EXTERNAL_ID + ".mp4");
        assertTrue(Files.exists(Paths.get(path.getPath() + "/" + EXTERNAL_ID + ".mp4")));
        fileService.deleteByPath(path.getPath() + "/" + EXTERNAL_ID + ".mp4");
        assertFalse(Files.exists(Paths.get(path.getPath() + "/" + EXTERNAL_ID + ".mp4")));
    }

    @Test
    public void renameFile_expectedFileMoved() throws IOException {
        File path = tempFolder.getRoot();
        createFile(path.getPath() + "/" + EXTERNAL_ID + ".mp4");
        fileService.createFolder(path.getPath() + "/" + NEW_FOLDER);
        fileService.renameFile(path.getPath() + "/" + EXTERNAL_ID + ".mp4", path.getPath() + "/" + NEW_FOLDER + "/" + EXTERNAL_ID + ".mp4");
        assertTrue(Files.exists(Paths.get(path.getPath() + "/" + NEW_FOLDER + "/" + EXTERNAL_ID + ".mp4")));
    }

    @Test
    public void deleteIfEmpty_expectedFolderDeleted() throws Exception {
        File path = tempFolder.getRoot();
        fileService.createFolder(path.getPath() + "/" + NEW_FOLDER);
        fileService.deleteIfEmpty(new File(path.getPath() + "/" + NEW_FOLDER));
        assertFalse(Files.exists(Paths.get(path.getPath() + "/" + NEW_FOLDER)));
    }

    private void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.createNewFile())
            throw new MicroserviceException(HttpStatus.BAD_REQUEST, "can't create directory on path " + path);
    }

    private VideoEntity createVideoEntity(String path) {
        return new VideoEntity(1, EXTERNAL_ID, path, "", "", "", "", "", new Date());
    }


}
