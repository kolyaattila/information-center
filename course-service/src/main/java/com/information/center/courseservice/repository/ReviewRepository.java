package com.information.center.courseservice.repository;

import com.information.center.courseservice.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    boolean existsByExternalId(String externalId);

    List<ReviewEntity> findAllByCourseExternalId(String externalId);

}
