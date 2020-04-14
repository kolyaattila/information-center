package com.information.center.questionservice.repository;

import com.information.center.questionservice.entity.AnswerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    Optional<AnswerEntity> findByExternalId(String externalId);

    Page<AnswerEntity> findAll(Pageable pageable);

    boolean existsByExternalId(String externalId);
}
