//package com.information.center.quizservice.controller;
//
//import com.information.center.quizservice.entity.QuizType;
//import com.information.center.quizservice.model.request.QuizRequest;
//import com.information.center.quizservice.service.QuizService;
//import exception.RestExceptions;
//import exception.ServiceExceptions;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.doThrow;
//import static org.mockito.Mockito.verify;
//
//@RunWith(MockitoJUnitRunner.class)
//public class QuizControllerTest {
//
//    @InjectMocks
//    private QuizController quizController;
//    @Mock
//    private QuizService quizService;
//    private QuizRequest quizRequest;
//
//    @Before
//    public void setUp() {
//        quizRequest = QuizRequest.builder()
//                .chapterExternalId("chapter")
//                .courseExternalId("course")
//                .enable(true)
//                .externalId("externalId")
//                .schoolExternalId("school")
//                .type(QuizType.CHAPTER)
//                .build();
//    }
//
//    @Test
//    public void testCreateQuiz() {
//        ResponseEntity<?> response = quizController.createQuiz(quizRequest);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        verify(quizService).createQuiz(quizRequest);
//    }
//
//    @Test(expected = RestExceptions.BadRequest.class)
//    public void testCreateQuizWhenServiceInsertFailedException_expectBadRequest() {
//        doThrow(ServiceExceptions.InsertFailedException.class).when(quizService).createQuiz(quizRequest);
//
//        quizController.createQuiz(quizRequest);
//    }
//
//    @Test(expected = RestExceptions.BadRequest.class)
//    public void testCreateQuizWhenServiceNotFoundException_expectBadRequest() {
//        doThrow(ServiceExceptions.NotFoundException.class).when(quizService).createQuiz(quizRequest);
//
//        quizController.createQuiz(quizRequest);
//    }
//
//    @Test
//    public void testUpdateQuiz() {
//        ResponseEntity<?> response = quizController.updateQuiz(quizRequest);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//
//        verify(quizService).updateQuiz(quizRequest);
//    }
//
//    @Test(expected = RestExceptions.BadRequest.class)
//    public void testUpdateQuizWhenServiceInsertFailedException_expectBadRequest() {
//        doThrow(ServiceExceptions.InsertFailedException.class).when(quizService).updateQuiz(quizRequest);
//
//        quizController.updateQuiz(quizRequest);
//    }
//
//    @Test(expected = RestExceptions.BadRequest.class)
//    public void testUpdateQuizWhenService_expectBadRequest() {
//        doThrow(ServiceExceptions.InsertFailedException.class).when(quizService).updateQuiz(quizRequest);
//
//        quizController.updateQuiz(quizRequest);
//    }
//}
