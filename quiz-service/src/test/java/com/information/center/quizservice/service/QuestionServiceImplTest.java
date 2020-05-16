package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.AnswerConverter;
import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.entity.AnswerEntity;
import com.information.center.quizservice.entity.QuestionDifficulty;
import com.information.center.quizservice.entity.QuestionEntity;
import com.information.center.quizservice.entity.SchoolEntity;
import com.information.center.quizservice.model.AnswerDto;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.AnswerRequest;
import com.information.center.quizservice.model.request.FilterQuestionRequest;
import com.information.center.quizservice.model.request.QuestionRequest;
import com.information.center.quizservice.repository.QuestionRepository;
import com.information.center.quizservice.repository.SchoolRepository;
import exception.ServiceExceptions;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionServiceImplTest {

    private static final String QUESTION_EXTERNAL_ID = "questionExternalId";
    private static final String SCHOOL_EXTERNAL_ID = "schoolExternalId";
    private static final String EXTERNAL_ID = "externalId";
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerService answerService;
    @Mock
    private SchoolRepository schoolRepository;
    @Captor
    ArgumentCaptor<QuestionEntity> questionEntityArgumentCaptor;
    @Captor
    ArgumentCaptor<PageRequest> pageRequestArgumentCaptor;

    @Captor
    ArgumentCaptor<Specification> specificationArgumentCaptor;
    @InjectMocks
    private QuestionServiceImpl questionService;
    private QuestionRequest questionRequest;
    private AnswerRequest answerRequest;
    private AnswerEntity answerEntity;
    private QuestionEntity questionEntity;
    private FilterQuestionRequest filterQuestion;


    @Before
    public void setUp() {
        QuestionConverter questionConverter = Mappers.getMapper(QuestionConverter.class);
        questionConverter.setAnswerConverter(Mappers.getMapper(AnswerConverter.class));
        questionService.setQuestionConverter(questionConverter);

        answerRequest = AnswerRequest.builder().build();
        questionRequest = QuestionRequest.builder()
                .schoolExternalId(SCHOOL_EXTERNAL_ID)
                .externalId(EXTERNAL_ID)
                .answers(Collections.singletonList(answerRequest)).build();
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setExternalId(SCHOOL_EXTERNAL_ID);
        answerEntity = new AnswerEntity();
        questionEntity = new QuestionEntity();
        questionEntity.setAnswers(Collections.emptyList());
        questionEntity.setSchool(schoolEntity);

        filterQuestion = FilterQuestionRequest.builder()
                .pageNumber(2)
                .pageSize(10)
                .questionDifficulty(QuestionDifficulty.EASY)
                .courseExternalId("course")
                .verified(false)
                .externalId(EXTERNAL_ID)
                .questionNumber(423)
                .chapterExternalId("chapter")
                .name("name")
                .book("book")
                .build();
    }

    @Test
    public void create_expectResponse() {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setExternalId(SCHOOL_EXTERNAL_ID);

        when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
        when(answerService.saveEntity(eq(answerRequest), any())).thenReturn(answerEntity);
        when(questionRepository.save(any())).then(r -> r.getArgument(0));
        when(questionRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

        QuestionDto response = questionService.create(questionRequest);

        assertFalse(response.getExternalId().isEmpty());
        assertEquals(response.getBook(), questionRequest.getBook());
        assertEquals(response.getChapterExternalId(), questionRequest.getChapterExternalId());
        assertEquals(response.getCourseExternalId(), questionRequest.getCourseExternalId());
        assertEquals(response.getName(), questionRequest.getName());
        assertEquals(response.getQuestionDifficulty(), questionRequest.getQuestionDifficulty());
        assertEquals(response.getQuestionNumber(), questionRequest.getQuestionNumber());
        assertEquals(SCHOOL_EXTERNAL_ID, response.getSchoolExternalId());

        assertFalse(response.getAnswers().isEmpty());
        AnswerDto answerDto = response.getAnswers().get(0);
        assertEquals(answerDto.getKey(), answerRequest.getKey());
        assertEquals(answerDto.getName(), answerRequest.getName());
        assertEquals(answerDto.getReason(), answerRequest.getReason());
        assertEquals(answerDto.isCorrect(), answerRequest.isCorrect());
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void create_expectInsertFailedException() {
        SchoolEntity schoolEntity = new SchoolEntity();
        schoolEntity.setExternalId(SCHOOL_EXTERNAL_ID);

        when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
        when(questionRepository.save(any())).thenThrow(HibernateException.class);

        questionService.create(questionRequest);
    }

    @Test
    public void createWhenSchoolNotFound_ExpectExternalSchoolIdNull() {
        when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.empty());
        when(answerService.saveEntity(eq(answerRequest), any())).thenReturn(answerEntity);
        when(questionRepository.save(any())).then(r -> r.getArgument(0));

        QuestionDto response = questionService.create(questionRequest);

        assertNull(response.getSchoolExternalId());
    }


    @Test
    public void update_expectedResponse() {
        SchoolEntity schoolEntity = new SchoolEntity();

        when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
        when(questionRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(questionEntity));
        when(answerService.getUpdatedEntities(questionRequest.getAnswers(), questionEntity))
                .thenReturn(Collections.singletonList(answerEntity));

        questionService.update(questionRequest);

        verify(questionRepository).save(questionEntityArgumentCaptor.capture());
        QuestionEntity response = questionEntityArgumentCaptor.getValue();
        assertEquals(response.getBook(), questionRequest.getBook());
        assertEquals(response.getChapterExternalId(), questionRequest.getChapterExternalId());
        assertEquals(response.getCourseExternalId(), questionRequest.getCourseExternalId());
        assertEquals(response.getName(), questionRequest.getName());
        assertEquals(response.getQuestionDifficulty(), questionRequest.getQuestionDifficulty());
        assertEquals(response.getQuestionNumber(), questionRequest.getQuestionNumber());
        assertEquals(schoolEntity, response.getSchool());

        assertFalse(response.getAnswers().isEmpty());
        assertEquals(answerEntity, response.getAnswers().get(0));
    }

    @Test
    public void updateWhenSchoolNotFound_expectedSchoolIsNull() {
        when(questionRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(questionEntity));
        when(answerService.getUpdatedEntities(questionRequest.getAnswers(), questionEntity))
                .thenReturn(Collections.singletonList(answerEntity));

        questionService.update(questionRequest);

        verify(questionRepository).save(questionEntityArgumentCaptor.capture());
        QuestionEntity response = questionEntityArgumentCaptor.getValue();
        assertNull(response.getSchool());
    }

    @Test(expected = ServiceExceptions.InsertFailedException.class)
    public void update_expectedInsertFailedException() {
        when(questionRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(questionEntity));
        when(answerService.getUpdatedEntities(questionRequest.getAnswers(), questionEntity))
                .thenReturn(Collections.singletonList(answerEntity));
        when(questionRepository.save(any())).thenThrow(HibernateException.class);

        questionService.update(questionRequest);
    }


    @Test
    public void findById_expectedResponse() {
        when(questionRepository.findByExternalId(QUESTION_EXTERNAL_ID)).thenReturn(Optional.of(questionEntity));

        QuestionDto response = questionService.findByExternalId(QUESTION_EXTERNAL_ID);

        assertQuestion(questionEntity, response);
        assertTrue(response.getAnswers().isEmpty());
    }

    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void findById_expectedNotFoundException() {
        when(questionRepository.findByExternalId(QUESTION_EXTERNAL_ID)).thenReturn(Optional.empty());

        questionService.findByExternalId(QUESTION_EXTERNAL_ID);
    }

    @Test
    public void testDelete() {
        when(questionRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(questionEntity));

        questionService.delete(EXTERNAL_ID);

        verify(questionRepository).delete(questionEntity);
    }

    @Test(expected = ServiceExceptions.NotFoundException.class)
    public void delete_expectNotFound() {
        when(questionRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

        questionService.delete(EXTERNAL_ID);
    }

    @Test
    public void testFilterWithAllFilters() {
        when(questionRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl(Collections.singletonList(questionEntity)));

        Page<QuestionDto> pageResponse = questionService.filterQuestions(filterQuestion);

        verify(questionRepository).findAll(specificationArgumentCaptor.capture(), pageRequestArgumentCaptor.capture());
        PageRequest pageRequest = pageRequestArgumentCaptor.getValue();
        assertEquals(10, pageRequest.getPageSize());
        assertEquals(2, pageRequest.getPageNumber());
        assertQuestion(questionEntity, pageResponse.stream().findFirst().get());
    }

    @Test
    public void testFilterWithOneCondition() {
        FilterQuestionRequest filterQuestion = FilterQuestionRequest.builder()
                .pageNumber(3)
                .pageSize(5)
                .build();
        when(questionRepository.findAll(any(Specification.class), any(PageRequest.class)))
                .thenReturn(new PageImpl(Collections.singletonList(questionEntity)));

        Page<QuestionDto> pageResponse = questionService.filterQuestions(filterQuestion);

        verify(questionRepository).findAll(specificationArgumentCaptor.capture(), pageRequestArgumentCaptor.capture());
        PageRequest pageRequest = pageRequestArgumentCaptor.getValue();
        assertEquals(5, pageRequest.getPageSize());
        assertEquals(3, pageRequest.getPageNumber());
        assertQuestion(questionEntity, pageResponse.stream().findFirst().get());
    }

    private void assertQuestion(QuestionEntity entity, QuestionDto dto) {
        assertEquals(entity.getBook(), dto.getBook());
        assertEquals(entity.getChapterExternalId(), dto.getChapterExternalId());
        assertEquals(entity.getName(), dto.getName());
        assertEquals(entity.getQuestionDifficulty(), dto.getQuestionDifficulty());
        assertEquals(entity.getQuestionNumber(), dto.getQuestionNumber());
        assertEquals(entity.getSchool().getExternalId(), dto.getSchoolExternalId());
        assertEquals(entity.getCourseExternalId(), dto.getCourseExternalId());
    }
}
