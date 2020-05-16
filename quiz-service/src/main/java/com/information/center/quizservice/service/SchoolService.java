package com.information.center.quizservice.service;

import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SchoolService {
    SchoolDto create(SchoolRequest schoolRequest);

    void update(SchoolRequest schoolRequest);

    SchoolDto findByExternalId(String externalId);

    List<SchoolDto> findAll();

    void delete(String externalId);
}
