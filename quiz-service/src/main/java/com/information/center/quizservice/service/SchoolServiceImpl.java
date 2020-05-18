package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.SchoolConverter;
import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import com.information.center.quizservice.repository.SchoolRepository;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    private SchoolConverter converter;

    @Autowired
    public void setConverter(SchoolConverter converter) {
        this.converter = converter;
    }

    @Override
    public SchoolDto create(SchoolRequest schoolRequest) {
        SchoolEntity entity = converter.toEntity(schoolRequest);
        entity.setExternalId(getUid());

        return converter.toDto(getSave(entity));
    }

    @Override
    public void update(SchoolRequest schoolRequest) {
        SchoolEntity entity = findEntityByExternalId(schoolRequest.getExternalId());

        entity.setName(schoolRequest.getName());
        entity.setNumberOfQuestions(schoolRequest.getNumberOfQuestions());
        getSave(entity);
    }

    @Override
    public SchoolDto findByExternalId(String externalId) {
        return converter.toDto(findEntityByExternalId(externalId));
    }

    @Override
    public List<SchoolDto> findAll() {
        //enable
        return schoolRepository.findAll()
                .stream()
                .map(converter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(String externalId) {
        SchoolEntity entity = findEntityByExternalId(externalId);
        schoolRepository.delete(entity);
    }

    private SchoolEntity findEntityByExternalId(String externalId) {
        return schoolRepository.findByExternalId(externalId)
                .orElseThrow(() -> new ServiceExceptions.NotFoundException("School not found by id " + externalId));
    }

    private SchoolEntity getSave(SchoolEntity entity) {
        try {
            return schoolRepository.save(entity);
        } catch (Exception e) {
            throw new ServiceExceptions.InsertFailedException(e.getMessage());
        }
    }

    private String getUid() {
        UUID uuid = UUID.randomUUID();
        if (schoolRepository.existsByExternalId(uuid.toString())) {
            return this.getUid();
        }
        return uuid.toString();
    }
}
