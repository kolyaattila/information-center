package com.information.center.questionservice.model;

import com.information.center.questionservice.model.response.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionListDetails {

    private Page<QuestionResponse> questionResponseList;

    private Date startDate;

    private String topicName;

}
