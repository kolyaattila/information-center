package com.information.center.courseservice.repository;

import com.information.center.courseservice.entity.VideoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface VideoRepository extends JpaRepository<VideoEntity, Long> {

    Optional<VideoEntity> findByExternalId(String externalId);

    @Query("select v from VideoEntity v join TopicEntity t on t.id = v.topic.id where t.externalId = ?1")
    List<VideoEntity> findAllByTopicExternalId(String topicId);

    @Query("select v from VideoEntity v  join TopicEntity t on v.topic.id = t.id join CourseEntity c on c.id = t.course.id where c.externalId = ?1")
    List<VideoEntity> findAllByCourseExternalId(String courseId);
}
