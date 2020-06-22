package com.information.center.courseservice.model.request;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilterCourseRequest {

    private String externalId;
    private String name;
    private boolean enable;
    private float price;
    private boolean priceSet;
    private int pageNumber;
    private int pageSize;

}
