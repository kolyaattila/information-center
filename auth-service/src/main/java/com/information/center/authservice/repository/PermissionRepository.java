package com.information.center.authservice.repository;

import com.information.center.authservice.entity.Permission;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends CrudRepository<Permission, Long> {

  Optional<Permission> findByName(String name);
}
