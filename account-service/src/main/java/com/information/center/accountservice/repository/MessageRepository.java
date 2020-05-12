package com.information.center.accountservice.repository;

import com.information.center.accountservice.entity.MessageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends CrudRepository<MessageEntity, Long> {
    boolean existsByUid(String uid);

    Page<MessageEntity> findAllByOrderByCreatedDesc(Pageable pageable);

}
