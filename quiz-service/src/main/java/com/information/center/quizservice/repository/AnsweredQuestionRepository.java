package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.AnsweredQuestionEntity;
import org.springframework.data.repository.CrudRepository;

public interface AnsweredQuestionRepository extends CrudRepository<AnsweredQuestionEntity, Long>, ExternalIdRepository<AnsweredQuestionEntity> {

    boolean existsByExternalId(String externalId);

}
