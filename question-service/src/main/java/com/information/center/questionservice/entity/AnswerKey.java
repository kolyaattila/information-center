package com.information.center.questionservice.entity;

public enum AnswerKey {
    A("A."),
    B("B."),
    C("C."),
    D("D."),
    E("E.");
    private String value;

    AnswerKey(String value) {
        this.value = value;
    }

    public static AnswerKey getKey(String key) {
        switch (key) {
            case "A.":
                return A;
            case "B.":
                return B;
            case "C.":
                return C;
            case "D.":
                return D;
            case "E.":
                return E;
            default:
                return A;

        }
    }
}
