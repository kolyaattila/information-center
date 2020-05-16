package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long>, JpaSpecificationExecutor<QuestionEntity> {

    Optional<QuestionEntity> findByExternalId(String externalId);


    Page<QuestionEntity> findAll(Pageable pageable);

    boolean existsByExternalId(String externalId);

    @Query(value = "SELECT * from public.question where topic_id =?1 order by random()", nativeQuery = true)
    Page<QuestionEntity> findQuestionsByTopicExternalId(String externalId, Pageable pageable);
}
