package com.information.center.questionservice.repository;

import com.information.center.questionservice.entity.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {
    Optional<Answer> findByExternalId(String externalId);

    Page<Answer> findAll(Pageable pageable);
}
