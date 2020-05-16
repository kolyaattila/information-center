package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import com.information.center.quizservice.service.SchoolService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SchoolController implements SchoolEndpoint {

    private final SchoolService schoolService;

    @Override
    public SchoolDto create(@Valid @RequestBody SchoolRequest schoolRequest) {
        try {
            return schoolService.create(schoolRequest);
        } catch (ServiceExceptions.InsertFailedException | ServiceExceptions.NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public void update(@Valid @RequestBody SchoolRequest schoolRequest) {
        try {
            schoolService.update(schoolRequest);
        } catch (ServiceExceptions.InsertFailedException | ServiceExceptions.NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public SchoolDto findByExternalId(String externalId) {
        try {
            return schoolService.findByExternalId(externalId);
        } catch (ServiceExceptions.NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }

    @Override
    public List<SchoolDto> findAll() {
        return schoolService.findAll();
    }

    @Override
    public void delete(String externalId) {
        try {
            schoolService.delete(externalId);
        } catch (ServiceExceptions.NotFoundException e) {
            throw new RestExceptions.BadRequest(e.getMessage());
        }
    }
}
