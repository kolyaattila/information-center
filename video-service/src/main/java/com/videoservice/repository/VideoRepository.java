package com.videoservice.repository;

import com.videoservice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long > {

    Optional<Video> findByExternalId(String externalId);

    List<Video> findAllByTopicId(String topicId);

}
