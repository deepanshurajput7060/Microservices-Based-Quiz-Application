package com.dee.Question_Service.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dee.Question_Service.Entity.utils.QuestionDto;
import com.dee.Question_Service.Entity.utils.QuestionWrapper;
import com.dee.Question_Service.Entity.utils.Response;
import com.dee.Question_Service.Services.QuestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag (
	name = "APIs for Questions microservice",
	description = "CRUD Operations")

@RestController
@RequestMapping("/Questions")
@Validated
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@Operation (
			summary = "Get-All Questions REST API",
			description = "REST API to Return all questions available",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
	@GetMapping("/getAll")
	public ResponseEntity<List<QuestionDto>> getAllQuestions() {
		return questionService.getAllQuestions();
	}
	
	@Operation (
			summary = "Get-Questions-by-Category REST API",
			description = "REST API to Return all questions with a given Category",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
	@GetMapping("/{category}")
	public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(@PathVariable String category ) {
		return questionService.getQuestionsByCategory(category);
	}
	
	@Operation (
			summary = "Add Questions REST API",
			description = "REST API to Add question",
			responses = {
					@ApiResponse (responseCode = "201", description = "HTTP Status CREATED")
			})
	@PostMapping("/add")
	public ResponseEntity<String> addQuestion(@Valid @RequestBody QuestionDto questionDto) {
		return questionService.addQuestion(questionDto);
	}
	
	@Operation (
			summary = "DELETE Questions REST API",
			description = "REST API to delete question",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
		return questionService.deleteQuestion(id);
	}
	
	@Operation (
			summary = "Get-Question-for-Quiz REST API",
			description = "REST API to Generate questions for quiz",
			responses = {
			        @ApiResponse(responseCode = "200", description = "Questions fetched"),
			        @ApiResponse(responseCode = "404", description = "Category not found")
			    })
	@GetMapping("/generate/{categoryName}/{numQuestions}")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz
            (@PathVariable String categoryName, @PathVariable Integer numQuestions ){
        return questionService.getQuestionsForQuiz(categoryName, numQuestions);
    }

	@Operation (
			summary = "Get-Question-from-Id REST API",
			description = "REST API to Get question from quiz generated questions",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@Valid @RequestBody List<Integer> questionIds){
        return questionService.getQuestionsFromId(questionIds);
    }
	
	@Operation (
			summary = "Get-score Question REST API",
			description = "REST API to return quiz Score",
			responses = {
					@ApiResponse (responseCode = "200", description = "HTTP Status OK")
			})
	@PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@Valid @RequestBody List<Response> responses)
    {
        return questionService.getScore(responses);
    }
}
