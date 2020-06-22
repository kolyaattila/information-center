package com.information.center.courseservice.model.request;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequest {

    @NotBlank
    private String courseId;
    private String message;
    private String accountId;
    @Min(0)
    @Max(5)
    private int stars;


}
