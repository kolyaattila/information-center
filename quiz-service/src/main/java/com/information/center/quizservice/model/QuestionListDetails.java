package com.information.center.quizservice.model;

import com.information.center.quizservice.model.response.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionListDetails {

    private Page<QuestionResponse> questionResponseList;

    private Date startDate;

    private String topicName;

}
