package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    boolean existsByExternalId(String externalId);

    Optional<QuizEntity> findByChapterExternalId(String externalId);

}
