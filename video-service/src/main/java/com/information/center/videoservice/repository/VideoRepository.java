package com.information.center.videoservice.repository;

import com.information.center.videoservice.entity.VideoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

  Optional<VideoEntity> findByUid(String uid);

}
