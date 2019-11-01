package com.information.center.questionservice.repository;

import com.information.center.questionservice.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long> {

    Optional<Question> findByExternalId(String externalId);

    Page<Question> findAll(Pageable pageable);

    Page<Question> findQuestionsByTopicExternalId(String externalId,Pageable pageable);
}