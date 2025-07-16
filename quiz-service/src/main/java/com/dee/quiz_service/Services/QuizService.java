package com.dee.quiz_service.Services;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dee.quiz_service.Entity.utils.QuestionWrapper;
import com.dee.quiz_service.Entity.utils.Response;

public interface QuizService {

	ResponseEntity<String> createQuiz(String category, int numQ, String title);

	ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id);

	ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses);

}
