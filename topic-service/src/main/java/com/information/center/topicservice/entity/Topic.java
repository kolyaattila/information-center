package com.information.center.topicservice.entity;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Topic")
@Getter
@Setter
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "external_id")
    private String externalId;

    private Date created;

    private String name;
}
