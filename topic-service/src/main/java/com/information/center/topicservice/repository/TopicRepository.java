package com.information.center.topicservice.repository;

import com.information.center.topicservice.entity.TopicEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

  Optional<TopicEntity> findByExternalId(String externalId);

  Page<TopicEntity> findAll(Pageable pageable);
}
