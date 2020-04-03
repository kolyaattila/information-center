package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.repository.AnswerRepository;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class QuestionValidateServiceImplTest {

    public static final String TOPIC_EXTERNAL_ID = "topicExternalId";
    public static final String TOPIC_NAME = "topicName";
    @InjectMocks
    private QuestionValidateServiceImpl questionValidateService;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private RestTemplate restTemplate;
    private QuestionConverter questionConverter = Mappers.getMapper(QuestionConverter.class);

    @Before
    public void setUp() {
        questionValidateService = new QuestionValidateServiceImpl(answerRepository, questionConverter, new CrunchifyTimeDiff(), restTemplate);
    }

    @Test
    public void validate_expectedResponse() {

        var response = questionValidateService.validate(createQuestionRequestValidation());
        assertNotNull(response);
    }

    private QuestionRequestValidation createQuestionRequestValidation() {
        var questionRequestValidation = new QuestionRequestValidation();
        questionRequestValidation.setQuestionResponses(new ArrayList<>());
        questionRequestValidation.setStartDate(new Date());
        questionRequestValidation.setTopicName(TOPIC_EXTERNAL_ID);
        return questionRequestValidation;
    }
}
