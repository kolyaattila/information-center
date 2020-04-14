package com.information.center.questionservice.service;

import com.information.center.questionservice.client.TopicServiceClient;
import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.entity.AnswerEntity;
import com.information.center.questionservice.entity.QuestionDifficulty;
import com.information.center.questionservice.model.request.QuestionRequestValidation;
import com.information.center.questionservice.model.response.AnswerResponse;
import com.information.center.questionservice.model.response.QuestionResponse;
import com.information.center.questionservice.repository.AnswerRepository;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionValidateServiceImplTest {

    private static final String TOPIC_EXTERNAL_ID = "topicExternalId";
    private static final String QUESTION_EXTERNAL_ID = "externalId";
    private static final String QUESTION_NAME = "questionName";
    private static final String TOPIC_ID = "topicId";
    private static final String REASON = "reason";
    private static final String ANSWER_EXTERNAL_ID = "answerExternalId";
    private static final String ANSWER_NAME = "answerName";
    private static final boolean IS_CORRECT = false;
    private static final boolean CHECKED = true;
    private static final String REASON_RETURNED = "reasonReturned";
    private static final boolean IS_CORRECT_RETURNED = true;
    @InjectMocks
    private QuestionValidateServiceImpl questionValidateService;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private TopicServiceClient topicServiceClient;
    private QuestionConverter questionConverter = Mappers.getMapper(QuestionConverter.class);

    @Before
    public void setUp() {
        questionValidateService = new QuestionValidateServiceImpl(answerRepository, questionConverter, new CrunchifyTimeDiff(), topicServiceClient);
    }

    @Test
    public void validate_expectedResponse() {
        when(answerRepository.findByExternalId(ANSWER_EXTERNAL_ID)).thenReturn(Optional.of(createAnswerEntity()));
        var response = questionValidateService.validate(createQuestionRequestValidation());
        assertNotNull(response.getEndDate());
        assertNotNull(response.getStartDate());
        assertNotNull(response.getTotalTime());
        assertEquals(TOPIC_EXTERNAL_ID, response.getTopicName());
        assertsForQuestion(response.getQuestionResponses().get(0));
        assertsForAnswer(response.getQuestionResponses().get(0).getAnswers().get(0));
    }

    private void assertsForQuestion(QuestionResponse questionResponse) {
        assertEquals(QUESTION_EXTERNAL_ID, questionResponse.getExternalId());
        assertEquals(QUESTION_NAME, questionResponse.getName());
        assertEquals(QuestionDifficulty.EASY, questionResponse.getQuestionDifficulty());
        assertEquals(TOPIC_ID, questionResponse.getTopicExternalId());
    }

    private void assertsForAnswer(AnswerResponse answerResponse) {
        assertEquals(REASON_RETURNED, answerResponse.getReason());
        assertEquals(IS_CORRECT_RETURNED, answerResponse.isCorrect());
        assertEquals(CHECKED, answerResponse.isChecked());
        assertEquals(ANSWER_EXTERNAL_ID, answerResponse.getExternalId());
        assertEquals(ANSWER_NAME, answerResponse.getName());
        assertEquals(QUESTION_EXTERNAL_ID, answerResponse.getQuestionExternalId());
    }

    private QuestionRequestValidation createQuestionRequestValidation() {
        var questionRequestValidation = new QuestionRequestValidation();
        questionRequestValidation.setQuestionResponses(createQuestionResponse());
        questionRequestValidation.setStartDate(new Date());
        questionRequestValidation.setTopicName(TOPIC_EXTERNAL_ID);
        return questionRequestValidation;
    }

    private List<QuestionResponse> createQuestionResponse() {
        List<QuestionResponse> questionResponses = new ArrayList<>();
        var question = new QuestionResponse();
        question.setExternalId(QUESTION_EXTERNAL_ID);
        question.setName(QUESTION_NAME);
        question.setQuestionDifficulty(QuestionDifficulty.EASY);
        question.setTopicExternalId(TOPIC_ID);
        question.setAnswers(createAnswerResponse());
        questionResponses.add(question);
        return questionResponses;
    }

    private List<AnswerResponse> createAnswerResponse() {
        List<AnswerResponse> answerResponses = new ArrayList<>();
        var answer = new AnswerResponse();
        answer.setReason(REASON);
        answer.setCorrect(IS_CORRECT);
        answer.setChecked(CHECKED);
        answer.setExternalId(ANSWER_EXTERNAL_ID);
        answer.setName(ANSWER_NAME);
        answer.setQuestionExternalId(QUESTION_EXTERNAL_ID);
        answerResponses.add(answer);
        return answerResponses;
    }

    private AnswerEntity createAnswerEntity() {
        var answer = new AnswerEntity();
        answer.setReason(REASON_RETURNED);
        answer.setCorrect(IS_CORRECT_RETURNED);
        return answer;
    }
}

