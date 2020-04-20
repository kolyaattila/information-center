package com.information.center.accountservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class MessageRequest {

    @Tolerate
    public MessageRequest() {
    }

    @NotBlank
    @Length(max = 50)
    private String name;
    @NotBlank
    @Length(max = 100)
    private String email;
    @NotBlank
    @Length(max = 1000)
    private String message;
}
