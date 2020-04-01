package com.information.center.questionservice.repository;

import com.information.center.questionservice.entity.AnswerEntity;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

  Optional<AnswerEntity> findByExternalId(String externalId);

  Page<AnswerEntity> findAll(Pageable pageable);
}
