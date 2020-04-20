package com.information.center.accountservice.repository;

import com.information.center.accountservice.entity.MessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    boolean existsByUid(String uid);
}
