package com.information.center.questionservice.readFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Answer {

    String value;
    boolean correct;
    boolean verified;
    int startIndex;
    int endIndex;
    String key;
    String book;
    int questionNumber;
}
