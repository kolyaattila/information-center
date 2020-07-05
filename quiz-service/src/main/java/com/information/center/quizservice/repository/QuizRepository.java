package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<QuizEntity, Long>, ExternalIdRepository<QuizEntity> {

    boolean existsByExternalId(String externalId);

    Optional<QuizEntity> findByChapterExternalId(String externalId);

    Optional<QuizEntity> findByExternalId(String externalId);

    List<QuizEntity> findAllByCourseExternalIdAndEnable(String externalId, boolean enable);

    Optional<QuizEntity> findByExternalIdAndEnable(String externalId, boolean enable);

}
