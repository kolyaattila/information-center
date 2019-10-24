package com.information.center.topicservice.repository;

import com.information.center.topicservice.entity.Topic;
import com.information.center.topicservice.model.response.TopicResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,Long> {

    Optional<Topic> findByExternalId(String externalId);

    Page<Topic> findAll(Pageable pageable);
}
