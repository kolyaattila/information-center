package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.SolvedQuizEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SolvedQuizRepository extends CrudRepository<SolvedQuizEntity, Long>, ExternalIdRepository<SolvedQuizEntity> {

    Optional<SolvedQuizEntity> findByExternalId(String externalId);

}
