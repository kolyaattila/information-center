package com.information.center.accountservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class MessageResponse {

    private String email;

    private String name;

    private String message;

    private Date created;

    private String uid;
}
