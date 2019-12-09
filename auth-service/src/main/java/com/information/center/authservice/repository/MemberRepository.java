package com.information.center.authservice.repository;

import com.information.center.authservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String name);
    Optional<Member> findByExternalId(String externalId);
}
