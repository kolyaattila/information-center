package com.information.center.topicservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    private long id;

    private String name;

    private String externalId;

    @ManyToOne
    @JoinColumn
    private Question question;

    private boolean isCorrect;

    private String reason;
}
