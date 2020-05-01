package com.information.center.quizservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionResponsePage {

    private Date startDate;

    private Page<QuestionResponse> questionResponses;
}
