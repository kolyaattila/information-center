package com.information.center.quizservice.repository;

import com.information.center.quizservice.entity.SchoolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<SchoolEntity, Long> {

    boolean existsByExternalId(String externalId);

    Optional<SchoolEntity> findByExternalId(String externalId);

}
