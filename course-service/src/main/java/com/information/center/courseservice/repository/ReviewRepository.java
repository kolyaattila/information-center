package com.information.center.courseservice.repository;

import com.information.center.courseservice.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    boolean existsByExternalId(String externalId);

    List<ReviewEntity> findAllByCourseExternalId(String externalId);


    Optional<ReviewEntity> findByCourseExternalIdAndAccountExternalId(String courseExternalId, String userId);
}
