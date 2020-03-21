package com.information.center.accountservice.repository;

import com.information.center.accountservice.entity.Account;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

  boolean existsByUid(String uid);

  Optional<Account> findByUid(String uid);

  Optional<Account> findByUsername(String username);

}
