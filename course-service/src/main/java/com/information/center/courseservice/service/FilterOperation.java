package com.information.center.courseservice.service;

import lombok.Getter;

@Getter
public enum FilterOperation {
    EQUALS(":");
    private String key;

    FilterOperation(String key) {
        this.key = key;
    }
}
