package com.information.center.topicservice.repository;

import com.information.center.topicservice.entity.Topic;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

  Optional<Topic> findByExternalId(String externalId);

  Page<Topic> findAll(Pageable pageable);

  Optional<Topic> findTopicByExternalId(String externalId);
}
