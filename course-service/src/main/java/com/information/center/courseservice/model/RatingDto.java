package com.information.center.courseservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private float average;
    private int fiveStarsReviews;
    private int fourStarsReviews;
    private int threeStarsReviews;
    private int twoStarsReviews;
    private int oneStarsReviews;
    private int numberOfReviews;
}
