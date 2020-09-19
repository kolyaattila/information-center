package com.information.center.courseservice.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private String externalId;
    private Date created;
    private String name;
    private String author;
    private String description;
    private boolean enable;
    private String image;
    private float price;
    private RatingDto rating;
    private List<TopicDto> topics = new ArrayList<>();
}
