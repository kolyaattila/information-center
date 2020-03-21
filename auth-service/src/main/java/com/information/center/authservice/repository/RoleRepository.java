package com.information.center.authservice.repository;

import com.information.center.authservice.entity.Role;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
