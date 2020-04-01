package com.information.center.authservice.repository;

import com.information.center.authservice.entity.PermissionEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<PermissionEntity, Long> {

  Optional<PermissionEntity> findByName(String name);
}
