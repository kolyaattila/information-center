package com.information.center.courseservice.model.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequest {

    private String externalId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private boolean enable;
    private String image;
    private float price;
}
