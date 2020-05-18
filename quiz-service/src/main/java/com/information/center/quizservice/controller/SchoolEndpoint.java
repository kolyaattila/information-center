package com.information.center.quizservice.controller;

import com.information.center.quizservice.model.SchoolDto;
import com.information.center.quizservice.model.request.SchoolRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
@RequestMapping("/school")
public interface SchoolEndpoint {

    @PostMapping
    SchoolDto create(@RequestBody @Valid SchoolRequest schoolRequest);

    @PutMapping
    void update(@RequestBody @Valid SchoolRequest schoolRequest);

    @GetMapping("/{externalId}")
    SchoolDto findByExternalId(@PathVariable("externalId") String externalId);

    @GetMapping
    List<SchoolDto> findAll();

    @DeleteMapping("/{externalId}")
    void delete(@PathVariable("externalId") String externalId);
}
