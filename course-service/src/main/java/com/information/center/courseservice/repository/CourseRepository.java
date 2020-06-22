package com.information.center.courseservice.repository;

import com.information.center.courseservice.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {

    Optional<CourseEntity> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);

    List<CourseEntity> findAllByEnable(boolean enable);
}
