package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.SolvedQuizEntity;
import org.springframework.data.repository.CrudRepository;

public interface SolvedQuizRepository extends CrudRepository<SolvedQuizEntity, Long> {
}
