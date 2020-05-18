package com.information.center.quizservice.entity;

import lombok.Getter;

@Getter
public enum QuestionDifficulty {
    EASY,
    MEDIUM,
    HARD;

    public static QuestionDifficulty getEnum(String value) {
        switch (value) {
            case "EASY":
                return EASY;
            case "MEDIUM":
                return MEDIUM;
            case "HARD":
                return HARD;
            default:
                return null;
        }
    }
}
