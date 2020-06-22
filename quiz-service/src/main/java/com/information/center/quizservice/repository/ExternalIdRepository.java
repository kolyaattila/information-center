package com.information.center.quizservice.repository;

public interface ExternalIdRepository<T> {

    boolean existsByExternalId(String externalId);
}
