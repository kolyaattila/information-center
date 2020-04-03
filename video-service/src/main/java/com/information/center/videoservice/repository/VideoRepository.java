package com.information.center.videoservice.repository;

import com.information.center.videoservice.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    Optional<VideoEntity> findByExternalId(String externalId);

    List<VideoEntity> findAllByTopicId(String topicId);
}
