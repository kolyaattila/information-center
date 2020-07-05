package com.information.center.courseservice.repository;

import com.information.center.courseservice.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long>, JpaSpecificationExecutor<CourseEntity> {

    Optional<CourseEntity> findByExternalId(String externalId);

    boolean existsByExternalId(String externalId);

    List<CourseEntity> findAllByEnable(boolean enable);

    @Query(nativeQuery = true,  value = "select c.* " +
                                        "from course c " +
                                        "JOIN (select sum(rating) as sum, count(id) as count, course_id" +
                                        "    from review" +
                                        "    group by course_id) i " +
                                        "on i.course_id = c.id " +
                                        "group by c.id " +
                                        "order by avg(i.sum/i.count) DESC " +
                                        "LIMIT 3;")
    List<CourseEntity> bestCourses();
}
