package com.information.center.questionservice.model;

import com.information.center.questionservice.model.response.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseValidated {

    private Date startDate;

    private List<QuestionResponse> questionResponses;

    private Date endDate;

    private String totalTime;

    private String topicName;

}
