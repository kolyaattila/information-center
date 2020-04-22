package com.information.center.questionservice.service;

import com.information.center.questionservice.client.TopicServiceClient;
import com.information.center.questionservice.converter.QuestionConverter;
import com.information.center.questionservice.entity.QuestionDifficulty;
import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.model.request.QuestionRequest;
import com.information.center.questionservice.repository.QuestionRepository;
import lombok.var;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    public static final String NAME = "name";
    public static final String QUESTION_EXTERNAL_ID = "questionExternalId";
    public static final String TOPIC_EXTERNAL_ID = "topicExternalId";
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private TopicServiceClient topicServiceClient;
    @Mock
    private AnswerService answerService;
    @Captor
    ArgumentCaptor<QuestionEntity> questionEntityArgumentCaptor;
    @Captor
    ArgumentCaptor<String> externalIdArgumentCaptor;
    private QuestionServiceImpl questionService;
    private QuestionConverter converter = Mappers.getMapper(QuestionConverter.class);

    @Before
    public void setUp() {
        questionService = new QuestionServiceImpl(questionRepository, topicServiceClient, converter, answerService);
        mocks();
    }

    @Test
    public void create_expectResponse() {
        questionService.create(createQuestion(), TOPIC_EXTERNAL_ID);
        verify(questionRepository).save(questionEntityArgumentCaptor.capture());
        assertsForQuestion(questionEntityArgumentCaptor.getValue());
    }

    @Test
    public void update_expectedResponse() {
        questionService.update(createQuestion());
        verify(questionRepository).save(questionEntityArgumentCaptor.capture());
        assertsForQuestion(questionEntityArgumentCaptor.getValue());
    }

    @Test
    public void findById_expectedResponse() {
        questionService.findById(QUESTION_EXTERNAL_ID);
        verify(questionRepository).findByExternalId(externalIdArgumentCaptor.capture());
        assertEquals(QUESTION_EXTERNAL_ID, externalIdArgumentCaptor.getValue());

    }

    @Test
    public void findAll_expectedResponse() {
        var response = questionService.findAll(PageRequest.of(2, 20));
        assertNotNull(response);
    }

    @Test
    public void findQuestionByTopicId_expectedResponse() {
        var response = questionService.findQuestionsByTopicId(TOPIC_EXTERNAL_ID, PageRequest.of(2, 20));
        assertNotNull(response);
    }

    private QuestionRequest createQuestion() {
        var question = new QuestionRequest();
        question.setExternalId(QUESTION_EXTERNAL_ID);
        question.setName(NAME);
        question.setQuestionDifficulty(QuestionDifficulty.EASY);
        question.setTopicExternalId(TOPIC_EXTERNAL_ID);
        question.setAnswers(new ArrayList<>());
        return question;
    }

    private void assertsForQuestion(QuestionEntity questionEntity) {
        assertEquals(NAME, questionEntity.getName());
        assertEquals(TOPIC_EXTERNAL_ID, questionEntity.getTopicExternalId());
        assertEquals(QuestionDifficulty.EASY, questionEntity.getQuestionDifficulty());
    }

    private void mocks() {
        when(questionRepository.findByExternalId(QUESTION_EXTERNAL_ID)).thenReturn(Optional.of(new QuestionEntity()));
        when(questionRepository.findAll(PageRequest.of(2, 20))).thenReturn(Page.empty());
        when(questionRepository.findQuestionsByTopicExternalId(TOPIC_EXTERNAL_ID, PageRequest.of(2, 20))).thenReturn(Page.empty());
    }
}
