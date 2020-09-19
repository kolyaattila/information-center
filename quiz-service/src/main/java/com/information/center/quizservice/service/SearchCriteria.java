package com.information.center.quizservice.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchCriteria {
    private String key;
    private FilterOperation operation;
    private Object value;

    public SearchCriteria(String key, FilterOperation operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }
}
