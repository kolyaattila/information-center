package com.information.center.questionservice.service;

import com.information.center.questionservice.converter.AnswerConverter;
import com.information.center.questionservice.entity.AnswerEntity;
import com.information.center.questionservice.entity.QuestionEntity;
import com.information.center.questionservice.model.request.AnswerRequest;
import com.information.center.questionservice.repository.AnswerRepository;
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

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceImplTest {
    private static final String QUESTION_EXTERNAL_ID = "questionExternalId";
    private static final boolean IS_CORRECT = true;
    private static final boolean CHECKED = true;
    private static final String NAME = "name";
    private static final String REASON = "reason";
    public static final String ANSWER_EXTERNAL_ID = "answerExternalId";

    private AnswerServiceImpl answerService;
    @Mock
    private AnswerRepository answerRepository;
    @Mock
    private QuestionRepository questionRepository;
    @Captor
    private ArgumentCaptor<AnswerEntity> answerEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<String> externalIdArgumentCapture;

    private AnswerConverter answerConverter = Mappers.getMapper(AnswerConverter.class);

    @Before
    public void setUp() {
        mocks();
        answerService = new AnswerServiceImpl(answerRepository, questionRepository, answerConverter);
    }

    @Test
    public void create_expectedResponse() throws Exception {
        answerService.create(createAnswer(), QUESTION_EXTERNAL_ID);
        verify(answerRepository).save(answerEntityArgumentCaptor.capture());
        assertsForAnswer(answerEntityArgumentCaptor.getValue());
    }

    @Test
    public void update_expectedRepositoryCall() throws Exception {
        answerService.update(createAnswer());
        verify(answerRepository).save(answerEntityArgumentCaptor.capture());
        assertsForAnswer(answerEntityArgumentCaptor.getValue());
    }

    @Test
    public void findByExternalId_expectedResponse() throws Exception {
        var response = answerService.findByExternalId(ANSWER_EXTERNAL_ID);
        assertNotNull(response);
    }

    @Test
    public void findAll_expectedResponse() throws Exception {
        var response = answerService.findAll(PageRequest.of(2, 20));
        assertNotNull(response);
    }

    @Test
    public void delete_expectedExternalId() throws Exception {
        answerService.delete(ANSWER_EXTERNAL_ID);
        verify(answerRepository).findByExternalId(externalIdArgumentCapture.capture());
        assertEquals(ANSWER_EXTERNAL_ID, externalIdArgumentCapture.getValue());
    }


    private AnswerRequest createAnswer() {
        var answer = new AnswerRequest();
        answer.setExternalId(ANSWER_EXTERNAL_ID);
        answer.setQuestionExternalId(QUESTION_EXTERNAL_ID);
        answer.setCorrect(IS_CORRECT);
        answer.setChecked(CHECKED);
        answer.setName(NAME);
        answer.setReason(REASON);
        return answer;
    }

    private void assertsForAnswer(AnswerEntity answerEntity) {
        assertEquals(answerEntity.getName(), NAME);
        assertEquals(answerEntity.getReason(), REASON);
    }

    private void mocks() {
        when(questionRepository.findByExternalId(QUESTION_EXTERNAL_ID)).thenReturn(Optional.of(new QuestionEntity()));
        when(answerRepository.findByExternalId(ANSWER_EXTERNAL_ID)).thenReturn(Optional.of(new AnswerEntity()));
        when(answerRepository.findAll(PageRequest.of(2, 20))).thenReturn(Page.empty());
    }

}
