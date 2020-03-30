package com.information.center.accountservice.repository;

import com.information.center.accountservice.entity.AccountEntity;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

  boolean existsByUid(String uid);

  Optional<AccountEntity> findByUid(String uid);

  Optional<AccountEntity> findByUsername(String username);

}
