package com.information.center.quizservice.controller;

import com.information.center.quizservice.entity.QuizType;
import com.information.center.quizservice.model.QuizDto;
import com.information.center.quizservice.model.QuizStartDto;
import com.information.center.quizservice.model.request.QuizRequest;
import com.information.center.quizservice.model.request.QuizValidation;
import com.information.center.quizservice.service.QuestionValidateService;
import com.information.center.quizservice.service.QuizService;
import exception.RestExceptions;
import exception.ServiceExceptions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QuizControllerTest {

	private static final String CHAPTER_EXTERNAL_ID = "chapter";
	private static final String COURSE_EXTERNAL_ID = "course";
	private static final String SCHOOL_EXTERNAL_ID = "school";
	private static final String EXTERNAL_ID = "externalId";
	private static final String USER_EXTERNAL_ID = "userExternalId";
	private static final String DESCRIPTION = "description";
	@InjectMocks
	private QuizController quizController;
	@Mock
	private QuizService quizService;
	@Mock
	private QuestionValidateService questionValidateService;
	private QuizRequest quizRequest;
	private QuizDto quizDto;
	private QuizStartDto quizStartDto;
	private QuizValidation quizValidation;

	@Before
	public void setUp() {
		quizRequest = QuizRequest.builder()
				.chapterExternalId(CHAPTER_EXTERNAL_ID)
				.courseExternalId(COURSE_EXTERNAL_ID)
				.enable(true)
				.externalId(EXTERNAL_ID)
				.schoolExternalId(SCHOOL_EXTERNAL_ID)
				.type(QuizType.CHAPTER)
				.build();

		quizDto = QuizDto.builder()
				.chapterExternalId(CHAPTER_EXTERNAL_ID)
				.courseExternalId(COURSE_EXTERNAL_ID)
				.enable(true)
				.externalId(EXTERNAL_ID)
				.schoolExternalId(SCHOOL_EXTERNAL_ID)
				.type(QuizType.CHAPTER)
				.build();

		quizStartDto = QuizStartDto.builder()
				.chapterExternalId(CHAPTER_EXTERNAL_ID)
				.courseExternalId(COURSE_EXTERNAL_ID)
				.enable(true)
				.externalId(EXTERNAL_ID)
				.schoolExternalId(SCHOOL_EXTERNAL_ID)
				.type(QuizType.CHAPTER)
				.description(DESCRIPTION)
				.build();

		quizValidation = QuizValidation.builder()
				.externalId(EXTERNAL_ID)
				.note(3.3)
				.passed(false)
				.userExternalId(USER_EXTERNAL_ID)
				.build();
	}

	@Test
	public void testCreateQuiz() {
		ResponseEntity<?> response = quizController.createQuiz(quizRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(quizService).createQuiz(quizRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void testCreateQuizWhenServiceInsertFailedException_expectBadRequest() {
		doThrow(ServiceExceptions.InsertFailedException.class).when(quizService).createQuiz(quizRequest);

		quizController.createQuiz(quizRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void testCreateQuizWhenServiceNotFoundException_expectBadRequest() {
		doThrow(ServiceExceptions.NotFoundException.class).when(quizService).createQuiz(quizRequest);

		quizController.createQuiz(quizRequest);
	}

	@Test
	public void testUpdateQuiz() {
		ResponseEntity<?> response = quizController.updateQuiz(quizRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(quizService).updateQuiz(quizRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void testUpdateQuizWhenServiceInsertFailedException_expectBadRequest() {
		doThrow(ServiceExceptions.InsertFailedException.class).when(quizService).updateQuiz(quizRequest);

		quizController.updateQuiz(quizRequest);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void testUpdateQuizWhenService_expectBadRequest() {
		doThrow(ServiceExceptions.InsertFailedException.class).when(quizService).updateQuiz(quizRequest);

		quizController.updateQuiz(quizRequest);
	}

	@Test
	public void getActiveQuizByCourse_expectOneQuizDto() {
		when(quizService.getActiveQuizzesByCourseId(COURSE_EXTERNAL_ID)).thenReturn(Collections.singletonList(quizDto));

		List<QuizDto> response = quizController.getActiveQuizByCourse(COURSE_EXTERNAL_ID);

		assertTrue(response.contains(quizDto));
	}

	@Test
	public void getActiveQuiz_expectQuiz() {
		when(quizService.getActiveQuizById(EXTERNAL_ID)).thenReturn(quizStartDto);

		QuizStartDto response = quizController.getActiveQuiz(EXTERNAL_ID);

		assertEquals(quizStartDto, response);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void getActiveQuiz_expectException() {
		when(quizService.getActiveQuizById(EXTERNAL_ID)).thenThrow(ServiceExceptions.NotFoundException.class);

		quizController.getActiveQuiz(EXTERNAL_ID);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void validate_expectException() {
		when(questionValidateService.validate(quizValidation)).thenThrow(ServiceExceptions.NotFoundException.class);

		quizController.validate(quizValidation);
	}

	@Test
	public void validate_expectQuizValidation() {
		when(questionValidateService.validate(quizValidation)).thenReturn(quizValidation);

		QuizValidation response = quizController.validate(quizValidation);

		assertEquals(quizValidation, response);
	}

	@Test
	public void getResultQuiz_expectResultQuiz() {
		when(questionValidateService.getQuizValidation(EXTERNAL_ID)).thenReturn(quizValidation);

		QuizValidation response = quizController.getResultQuiz(EXTERNAL_ID);

		assertEquals(quizValidation, response);
	}

	@Test(expected = RestExceptions.BadRequest.class)
	public void getResultQuiz_expectException() {
		when(questionValidateService.getQuizValidation(EXTERNAL_ID)).thenThrow(ServiceExceptions.NotFoundException.class);

		quizController.getResultQuiz(EXTERNAL_ID);
	}

}
