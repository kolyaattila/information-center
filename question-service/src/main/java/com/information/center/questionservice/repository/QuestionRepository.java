package com.information.center.questionservice.repository;

import com.information.center.questionservice.entity.QuestionEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

  Optional<QuestionEntity> findByExternalId(String externalId);

  Page<QuestionEntity> findAll(Pageable pageable);

  Page<QuestionEntity> findQuestionsByTopicExternalId(String externalId, Pageable pageable);
}
