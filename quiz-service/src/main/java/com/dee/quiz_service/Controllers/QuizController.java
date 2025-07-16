package com.dee.quiz_service.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dee.quiz_service.Entity.utils.QuestionWrapper;
import com.dee.quiz_service.Entity.utils.Response;
import com.dee.quiz_service.Services.QuizService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(
		name = "APIs for Quiz service",
		description = "CRUD Operations" )

@RestController
@RequestMapping ("/quiz")
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@Operation (
			summary = "Create Quiz Rest API",
			description = "Rest api to create a quiz",
			responses = {
					@ApiResponse (responseCode = "201", description = "HTTP Status CREATED")
			})
	@PostMapping ("/create/{category}/{numQ}/{title}")
	public ResponseEntity<String> createQuiz ( @PathVariable String category,
			@PathVariable int numQ, @PathVariable String title) {
		return quizService.createQuiz(category, numQ, title);
	}
	
	@Operation (
			summary = "Get-quiz-question Quiz Rest API",
			description = "Rest api to get questions for quiz",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
	@GetMapping ("/get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestion (@PathVariable Integer id) {
		return quizService.getQuizQuestion(id);
	}
	
	@Operation (
			summary = "Submit Quiz Rest API",
			description = "Rest api to Submit a quiz",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
	@PostMapping ("/submit/{id}")
	public ResponseEntity<Integer> submitQuiz ( @PathVariable Integer id,@Valid @RequestBody List<Response> responses) {
		return quizService.submitQuiz(id, responses);
	}
}
