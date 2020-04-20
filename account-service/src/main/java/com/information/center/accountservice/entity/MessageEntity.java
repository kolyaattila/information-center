package com.information.center.accountservice.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "Message")
@Getter
@Setter
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "message", nullable = false, length = 1000)
    private String message;

    @Column(name = "created", nullable = false)
    private Date created = new Date();

    @Column(name = "uid", nullable = false, unique = true)
    private String uid;
}
