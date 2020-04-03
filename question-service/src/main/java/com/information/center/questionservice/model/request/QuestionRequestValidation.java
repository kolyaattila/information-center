package com.information.center.questionservice.model.request;

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
public class QuestionRequestValidation {

    List<QuestionResponse> questionResponses;

    private Date startDate;

    private String topicName;

}
