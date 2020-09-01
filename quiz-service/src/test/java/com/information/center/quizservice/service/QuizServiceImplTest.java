package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.AnswerConverter;
import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.converter.QuizConverter;
import com.information.center.quizservice.entity.*;
import com.information.center.quizservice.model.AnswerDto;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import com.information.center.quizservice.repository.QuestionRepository;
import com.information.center.quizservice.repository.QuizRepository;
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

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuizServiceImplTest {
	private static final String THIRD_QUESTION = "thirdQuestion";
	private static final String SECOND_ANSWER = "secondAnswer";
	private static final String FIRST_ANSWER = "firstAnswer";
	private static final String SECOND_QUESTION = "secondQuestion";
	private static final String FIRST_QUESTION = "firstQuestion";
	private static final String EXTERNAL_ID = "externalId";
	private static final String COURSE_EXTERNAL_ID = "courseExternalId";
	private static final String CHAPTER_EXTERNAL_ID = "chapterExternalId";
	private static final String SCHOOL_EXTERNAL_ID = "schoolExternalId";

	@InjectMocks
	private QuizServiceImpl quizService;
	@Mock
	private QuizRepository quizRepository;
	@Mock
	private QuestionRepository questionRepository;
	@Mock
	private SchoolRepository schoolRepository;
	@Captor
	private ArgumentCaptor<QuizEntity> captor;
	private QuizRequest quizRequest;
	private SchoolEntity schoolEntity;
	private QuestionEntity questionEntity;
	private QuizEntity quizEntity;

	@Before
	public void setUp() {
		QuizConverter quizConverter = Mappers.getMapper(QuizConverter.class);
		QuestionConverter questionConverter = Mappers.getMapper(QuestionConverter.class);
		AnswerConverter answerConverter = Mappers.getMapper(AnswerConverter.class);
		questionConverter.setAnswerConverter(answerConverter);
		quizService.setQuizConverter(quizConverter);
		quizService.setQuestionConverter(questionConverter);

		questionEntity = new QuestionEntity();
		schoolEntity = new SchoolEntity();
		schoolEntity.setExternalId(EXTERNAL_ID);

		quizEntity = new QuizEntity();
		quizEntity.setExternalId(EXTERNAL_ID);
		quizEntity.setType(QuizType.CHAPTER);
		quizEntity.setEnable(false);
		quizEntity.setCreated(new Date());
		quizEntity.setCourseExternalId(COURSE_EXTERNAL_ID);
		quizEntity.setChapterExternalId(CHAPTER_EXTERNAL_ID);
		quizEntity.setQuestions(Collections.singleton(questionEntity));
		quizEntity.setSchool(schoolEntity);

		quizRequest = QuizRequest.builder()
				.schoolExternalId(SCHOOL_EXTERNAL_ID)
				.questionIds(Arrays.asList("question1", "question2"))
				.type(QuizType.CHAPTER)
				.externalId(EXTERNAL_ID)
				.build();
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void testCreateQuiz_expectSchoolNotFound() {
		when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.empty());

		quizService.createQuiz(quizRequest);
	}

	@Test(expected = ServiceExceptions.WrongQuizType.class)
	public void testCreateQuiz_expectQuizWrongType() {
		quizRequest = QuizRequest.builder()
				.type(QuizType.EXAM).build();

		quizService.createQuiz(quizRequest);
	}

	@Test
	public void testCreateQuiz_expectQuizCreated() {
		when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
		when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
		when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());
		when(quizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		quizService.createQuiz(quizRequest);

		verify(quizRepository).save(captor.capture());
		QuizEntity response = captor.getValue();
		assertNotNull(response);
		assertEqualsEntity(response, quizRequest);
		assertTrue(response.getQuestions().contains(questionEntity));
		assertEquals(response.getSchool(), schoolEntity);
	}

	@Test(expected = ServiceExceptions.InsertFailedException.class)
	public void testCreateQuiz_expectQuizNotCreated() {
		when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
		when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
		when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());
		when(quizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
		when(quizRepository.save(any())).thenThrow(HibernateException.class);

		quizService.createQuiz(quizRequest);
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void testUpdateQuiz_expectNotFountException() {
		quizService.updateQuiz(quizRequest);
	}

	@Test
	public void testUpdateQuiz_expectQuizToBeUpdated() {
		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quizEntity));
		when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
		when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
		when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());

		quizService.updateQuiz(quizRequest);

		verify(quizRepository).save(captor.capture());
		QuizEntity response = captor.getValue();
		assertEquals(response.getSchool(), schoolEntity);
		assertTrue(response.getQuestions().contains(questionEntity));
		assertEqualsEntity(response, quizRequest);
	}

	@Test(expected = ServiceExceptions.InsertFailedException.class)
	public void testUpdateQuiz_expectInsertFailedException() {
		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quizEntity));
		when(schoolRepository.findByExternalId(SCHOOL_EXTERNAL_ID)).thenReturn(Optional.of(schoolEntity));
		when(questionRepository.findByExternalId("question1")).thenReturn(Optional.of(questionEntity));
		when(questionRepository.findByExternalId("question2")).thenReturn(Optional.empty());
		when(quizRepository.save(any())).thenThrow(HibernateException.class);

		quizService.updateQuiz(quizRequest);
	}

	@Test(expected = ServiceExceptions.WrongQuizType.class)
	public void testUpdateQuiz_expectQuizWrongType() {
		quizRequest = QuizRequest.builder().type(QuizType.EXAM).build();

		quizService.updateQuiz(quizRequest);
	}

	@Test
	public void testGetAll_expectEmptyList() {
		when(quizRepository.findAll()).thenReturn(Collections.emptyList());

		List<QuizDto> response = quizService.getAll();

		assertTrue(response.isEmpty());
	}

	@Test
	public void testGetAll_expectQuizDtoList() {
		when(quizRepository.findAll()).thenReturn(Collections.singletonList(quizEntity));

		List<QuizDto> response = quizService.getAll();

		assertFalse(response.isEmpty());
	}

	@Test
	public void getActiveQuizzesByCourseId() {
		when(quizRepository.findAllByCourseExternalIdAndEnable(COURSE_EXTERNAL_ID, true))
				.thenReturn(Collections.singletonList(quizEntity));

		List<QuizDto> response = quizService.getActiveQuizzesByCourseId(COURSE_EXTERNAL_ID);

		assertThat(response.get(0).getChapterExternalId(), is(quizEntity.getChapterExternalId()));
		assertThat(response.get(0).getExternalId(), is(quizEntity.getExternalId()));
		assertThat(response.get(0).getEnable(), is(quizEntity.getEnable()));
		assertThat(response.get(0).getCourseExternalId(), is(quizEntity.getCourseExternalId()));
		assertThat(response.get(0).getType(), is(quizEntity.getType()));
		assertThat(response.get(0).getSchoolExternalId(), is(schoolEntity.getExternalId()));
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void getActiveQuizById_expectException() {
		when(quizRepository.findByExternalIdAndEnable(EXTERNAL_ID, true)).thenReturn(Optional.empty());

		quizService.getActiveQuizById(EXTERNAL_ID);
	}

	@Test
	public void getActiveQuizById_expectQuizEntityWithNoCorrectAnswer() {
		quizEntity.setSchool(null);
		quizEntity.setQuestions(buildQuestionEntities());
		when(quizRepository.findByExternalIdAndEnable(EXTERNAL_ID, true)).thenReturn(Optional.of(quizEntity));

		QuizStartDto response = quizService.getActiveQuizById(EXTERNAL_ID);

		assertThat(response.getChapterExternalId(), is(quizEntity.getChapterExternalId()));
		assertThat(response.getExternalId(), is(quizEntity.getExternalId()));
		assertThat(response.getEnable(), is(quizEntity.getEnable()));
		assertThat(response.getCourseExternalId(), is(quizEntity.getCourseExternalId()));
		assertThat(response.getType(), is(quizEntity.getType()));
		assertNull(response.getSchoolExternalId());
		assertFalse(response.getQuestions()
				.stream()
				.map(QuestionDto::getAnswers)
				.flatMap(Collection::stream)
				.anyMatch(AnswerDto::isCorrect));
	}

	private List<QuestionEntity> buildQuestionEntities() {
		AnswerEntity firstAnswer = new AnswerEntity();
		firstAnswer.setExternalId(FIRST_QUESTION + FIRST_ANSWER);
		firstAnswer.setCorrect(true);

		AnswerEntity secondAnswer = new AnswerEntity();
		secondAnswer.setExternalId(FIRST_QUESTION + SECOND_ANSWER);

		QuestionEntity firstQuestion = new QuestionEntity();
		firstQuestion.setExternalId(FIRST_QUESTION);
		firstQuestion.setAnswers(Arrays.asList(firstAnswer, secondAnswer));

		AnswerEntity firstAnswer2 = new AnswerEntity();
		firstAnswer2.setExternalId(SECOND_QUESTION + FIRST_ANSWER);
		firstAnswer2.setCorrect(true);

		AnswerEntity secondAnswer2 = new AnswerEntity();
		secondAnswer2.setExternalId(SECOND_QUESTION + SECOND_ANSWER);

		QuestionEntity secondQuestion = new QuestionEntity();
		secondQuestion.setExternalId(SECOND_QUESTION);
		secondQuestion.setAnswers(Arrays.asList(firstAnswer2, secondAnswer2));

		AnswerEntity firstAnswer3 = new AnswerEntity();
		firstAnswer3.setExternalId(THIRD_QUESTION + FIRST_ANSWER);
		firstAnswer3.setCorrect(true);

		AnswerEntity secondAnswer3 = new AnswerEntity();
		secondAnswer3.setExternalId(THIRD_QUESTION + SECOND_ANSWER);

		QuestionEntity thirdQuestion = new QuestionEntity();
		thirdQuestion.setExternalId(THIRD_QUESTION);
		thirdQuestion.setAnswers(Arrays.asList(firstAnswer3, secondAnswer3));

		return Arrays.asList(firstQuestion, secondQuestion, thirdQuestion);
	}

	private void assertEqualsEntity(QuizEntity entity, QuizRequest quizRequest) {
		assertEquals(quizRequest.getChapterExternalId(), entity.getChapterExternalId());
		assertEquals(quizRequest.getCourseExternalId(), entity.getCourseExternalId());
		assertEquals(quizRequest.getEnable(), entity.getEnable());
		assertEquals(quizRequest.getType(), entity.getType());
	}
}
