package com.information.center.quizservice.service;

import com.information.center.quizservice.converter.AnswerConverter;
import com.information.center.quizservice.converter.QuestionConverter;
import com.information.center.quizservice.entity.*;
import com.information.center.quizservice.model.AnswerDto;
import com.information.center.quizservice.model.QuestionDto;
import com.information.center.quizservice.model.request.QuizValidation;
import com.information.center.quizservice.repository.AnsweredQuestionRepository;
import com.information.center.quizservice.repository.QuizRepository;
import com.information.center.quizservice.repository.SolvedQuizRepository;
import exception.ServiceExceptions;
import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class QuestionValidateServiceImplTest {

	private static final String THIRD_QUESTION = "thirdQuestion";
	private static final String SECOND_ANSWER = "secondAnswer";
	private static final String FIRST_ANSWER = "firstAnswer";
	private static final String SECOND_QUESTION = "secondQuestion";
	private static final String FIRST_QUESTION = "firstQuestion";
	private static final String FIRST_ANSWERED_QUESTION_ENTITY = "firstAnsweredQuestionEntity";
	private static final String USER_EXTERNAL_ID = "userExternalId";
	private static final String EXTERNAL_ID = "externalId";
	private static final String COURSE_EXTERNAL_ID = "courseExternalId";
	private static final String CHAPTER_EXTERNAL_ID = "chapterExternalId";
	@InjectMocks
	private QuestionValidateServiceImpl questionValidateService;
	@Mock
	private QuizRepository quizRepository;
	@Mock
	private AnsweredQuestionRepository answeredQuestionRepository;
	@Mock
	private SolvedQuizRepository solvedQuizRepository;
	private QuestionConverter questionConverter;
	private SolvedQuizEntity solvedQuizEntity;
	private QuizEntity quiz;
	private QuizValidation quizValidation;

	@Before
	public void setUp() {
		AnswerConverter answerConverter = Mappers.getMapper(AnswerConverter.class);
		questionConverter = Mappers.getMapper(QuestionConverter.class);
		questionConverter.setAnswerConverter(answerConverter);
		questionValidateService.setQuestionConverter(questionConverter);

		quiz = new QuizEntity();
		quiz.setChapterExternalId(CHAPTER_EXTERNAL_ID);
		quiz.setCourseExternalId(COURSE_EXTERNAL_ID);
		quiz.setCreated(new Date());
		quiz.setEnable(true);
		quiz.setExternalId(EXTERNAL_ID);
		quiz.setType(QuizType.CHAPTER);

		solvedQuizEntity = new SolvedQuizEntity();
		solvedQuizEntity.setCreated(new Date());
		solvedQuizEntity.setExternalId(EXTERNAL_ID);
		solvedQuizEntity.setNote(3.0);
		solvedQuizEntity.setCompletedQuiz(false);
		solvedQuizEntity.setPassed(false);
		solvedQuizEntity.setUserExternalId(USER_EXTERNAL_ID);
		solvedQuizEntity.setQuiz(quiz);

		quizValidation = new QuizValidation();
		quizValidation.setExternalId(EXTERNAL_ID);
		quizValidation.setUserExternalId(USER_EXTERNAL_ID);
		quizValidation.setQuestions(Collections.emptyList());
	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void getQuizValidation_expectException() {
		when(solvedQuizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

		questionValidateService.getQuizValidation(EXTERNAL_ID);
	}

	@Test
	public void getQuizValidation_expectQuizValidationWithoutQuestions() {
		when(solvedQuizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(solvedQuizEntity));

		QuizValidation response = questionValidateService.getQuizValidation(EXTERNAL_ID);

		assertNotNull(response);
		assertThat(response.getNote(), is(solvedQuizEntity.getNote()));
		assertThat(response.getExternalId(), is(solvedQuizEntity.getExternalId()));
		assertThat(response.getUserExternalId(), is(solvedQuizEntity.getUserExternalId()));
		assertThat(response.isPassed(), is(solvedQuizEntity.isPassed()));
		assertTrue(response.getQuestions().isEmpty());
	}

	@Test
	public void getQuizValidation_expectOneQuestion() {
		when(solvedQuizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(solvedQuizEntity));
		solvedQuizEntity.setAnsweredQuestionEntities(buildAnsweredQuestionEntity());

		QuizValidation response = questionValidateService.getQuizValidation(EXTERNAL_ID);

		assertThat(response.getNote(), is(solvedQuizEntity.getNote()));
		assertThat(response.getExternalId(), is(solvedQuizEntity.getExternalId()));
		assertThat(response.getUserExternalId(), is(solvedQuizEntity.getUserExternalId()));
		assertThat(response.isPassed(), is(solvedQuizEntity.isPassed()));
		assertThat(response.getQuestions().size(), is(1));
		assertEquals(FIRST_ANSWERED_QUESTION_ENTITY + FIRST_QUESTION, response.getQuestions().get(0).getExternalId());
		assertThat(response.getQuestions().get(0).getAnswers().size(), is(2));

		assertTrue(response.getQuestions().get(0).getAnswers()
				.stream()
				.filter(answer -> answer.getExternalId().equals(FIRST_ANSWERED_QUESTION_ENTITY + FIRST_QUESTION + FIRST_ANSWER))
				.map(AnswerDto::isSelected)
				.findAny()
				.orElse(false));

		assertFalse(response.getQuestions().get(0).getAnswers()
				.stream()
				.filter(answer -> answer.getExternalId().equals(FIRST_ANSWERED_QUESTION_ENTITY + FIRST_QUESTION + SECOND_ANSWER))
				.map(AnswerDto::isSelected)
				.findAny()
				.orElse(false));

	}

	@Test(expected = ServiceExceptions.NotFoundException.class)
	public void validate_expectException() {
		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.empty());

		questionValidateService.validate(quizValidation);
	}

	@Test(expected = ServiceExceptions.InconsistentDataException.class)
	public void validate_whenMissingSomeQuestion_expectException() {
		quiz.setQuestions(buildQuestionEntities());

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		questionValidateService.validate(quizValidation);
	}

	@Test
	public void validate_whenNotAnswerAnyQuestion_expectToFail() {
		quiz.setQuestions(buildQuestionEntities());
		quizValidation.setQuestions(buildQuestionDtos());

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		QuizValidation validate = questionValidateService.validate(quizValidation);
		assertThat(validate.getQuestions().size(), is(3));
		assertThat(validate.getUserExternalId(), is(USER_EXTERNAL_ID));
		assertThat(validate.getNote(), is(0.0));
		assertFalse(validate.isPassed());
	}

	@Test(expected = ServiceExceptions.InsertFailedException.class)
	public void validate_whenCanNotSave_expectException() {
		quiz.setQuestions(buildQuestionEntities());
		quizValidation.setQuestions(buildQuestionDtos());

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);
		when(solvedQuizRepository.save(any())).thenThrow(HibernateException.class);

		questionValidateService.validate(quizValidation);
	}

	@Test
	public void validate_whenAnswerOneQuestionCorrect_expectToFail() {
		quiz.setQuestions(buildQuestionEntities());
		quizValidation.setQuestions(buildQuestionDtos());
		quizValidation.getQuestions().get(0).getAnswers().get(0).setSelected(true);

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		QuizValidation validate = questionValidateService.validate(quizValidation);
		assertThat(validate.getQuestions().size(), is(3));
		assertThat(validate.getUserExternalId(), is(USER_EXTERNAL_ID));
		assertThat(validate.getNote(), is(1.0));
		assertFalse(validate.isPassed());
	}

	@Test
	public void validate_whenAnswerTwoQuestionCorrect_expectToPass() {
		quiz.setQuestions(buildQuestionEntities());
		quizValidation.setQuestions(buildQuestionDtos());
		quizValidation.getQuestions().get(0).getAnswers().get(0).setSelected(true);
		quizValidation.getQuestions().get(1).getAnswers().get(0).setSelected(true);

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		QuizValidation validate = questionValidateService.validate(quizValidation);
		assertThat(validate.getQuestions().size(), is(3));
		assertThat(validate.getUserExternalId(), is(USER_EXTERNAL_ID));
		assertThat(validate.getNote(), is(2.0));
		assertTrue(validate.isPassed());
	}

	@Test
	public void validate_whenAnswerAllQuestionCorrect_expectToPass() {
		quiz.setQuestions(buildQuestionEntities());
		quizValidation.setQuestions(buildQuestionDtos());
		quizValidation.getQuestions().get(0).getAnswers().get(0).setSelected(true);
		quizValidation.getQuestions().get(1).getAnswers().get(0).setSelected(true);
		quizValidation.getQuestions().get(2).getAnswers().get(0).setSelected(true);

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		QuizValidation validate = questionValidateService.validate(quizValidation);
		assertThat(validate.getQuestions().size(), is(3));
		assertThat(validate.getUserExternalId(), is(USER_EXTERNAL_ID));
		assertThat(validate.getNote(), is(3.0));
		assertTrue(validate.isPassed());
	}

	@Test
	public void validate_whenSelectTwoInvalidAnswers_expectToFail() {
		quiz.setQuestions(buildQuestionEntities());
		quizValidation.setQuestions(buildQuestionDtos());
		quizValidation.getQuestions().get(0).getAnswers().get(0).setSelected(true);
		quizValidation.getQuestions().get(1).getAnswers().get(1).setSelected(true);
		quizValidation.getQuestions().get(2).getAnswers().get(1).setSelected(true);

		when(quizRepository.findByExternalId(EXTERNAL_ID)).thenReturn(Optional.of(quiz));
		when(solvedQuizRepository.existsByExternalId(anyString())).thenReturn(true).thenReturn(false);

		QuizValidation validate = questionValidateService.validate(quizValidation);
		assertThat(validate.getQuestions().size(), is(3));
		assertThat(validate.getUserExternalId(), is(USER_EXTERNAL_ID));
		assertThat(validate.getNote(), is(1.0));
		assertFalse(validate.isPassed());
	}

	private List<AnsweredQuestionEntity> buildAnsweredQuestionEntity() {
		AnswerEntity firstAnswer = new AnswerEntity();
		firstAnswer.setExternalId(FIRST_ANSWERED_QUESTION_ENTITY + FIRST_QUESTION + FIRST_ANSWER);

		AnswerEntity secondAnswer = new AnswerEntity();
		secondAnswer.setExternalId(FIRST_ANSWERED_QUESTION_ENTITY + FIRST_QUESTION + SECOND_ANSWER);

		QuestionEntity firstQuestion = new QuestionEntity();
		firstQuestion.setExternalId(FIRST_ANSWERED_QUESTION_ENTITY + FIRST_QUESTION);
		firstQuestion.setAnswers(Arrays.asList(firstAnswer, secondAnswer));

		AnsweredQuestionEntity first = new AnsweredQuestionEntity();
		first.setQuestionEntity(firstQuestion);
		first.setAnswerEntities(Collections.singleton(firstAnswer));
		first.setCreated(new Date());
		first.setExternalId(FIRST_ANSWERED_QUESTION_ENTITY);
		first.setQuestionEntity(firstQuestion);
		firstQuestion.setAnsweredQuestionEntity(Collections.singletonList(first));

		return Collections.singletonList(first);
	}

	private List<QuestionDto> buildQuestionDtos() {
		return buildQuestionEntities()
				.stream()
				.map(questionConverter::toDtoWithAnswers)
				.collect(Collectors.toList());
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
}

