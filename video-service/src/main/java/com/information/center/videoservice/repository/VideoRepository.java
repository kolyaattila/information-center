package com.information.center.videoservice.repository;

import com.information.center.videoservice.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoRepository extends JpaRepository<Video, Long > {

    Optional<Video> findByExternalId(String externalId);

}
