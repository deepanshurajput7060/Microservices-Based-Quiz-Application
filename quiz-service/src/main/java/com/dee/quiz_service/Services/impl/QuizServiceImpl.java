package com.dee.quiz_service.Services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dee.quiz_service.Entity.Quiz;
import com.dee.quiz_service.Entity.utils.QuestionWrapper;
import com.dee.quiz_service.Entity.utils.Response;
import com.dee.quiz_service.Exceptions.ResourceNotFoundException;

import com.dee.quiz_service.Repository.QuizRepo;
import com.dee.quiz_service.Services.QuizService;
import com.dee.quiz_service.feign.QuizInterface;

@Service
public class QuizServiceImpl implements QuizService{
	
	@Autowired
	private QuizRepo quizRepo;
	
	@Autowired
	private QuizInterface quizInterface;

	@Override
	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		
		if (questions.size() < numQ) {
	        return new ResponseEntity<>("Not enough questions available.", HttpStatus.BAD_REQUEST);
	    }
				
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setCategory(category);
		quiz.setNumQ(numQ);
		quiz.setQuestionIds(questions);
		quizRepo.save(quiz);
		
		return new ResponseEntity<String> ("Quiz created successfully.", HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
		Quiz quiz = quizRepo.findById(id)
							.orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
		
		List<Integer> questionIds = quiz.getQuestionIds();
		
		List<QuestionWrapper> questionsForUser = quizInterface.getQuestionsFromId(questionIds).getBody();
		
		return new ResponseEntity<List<QuestionWrapper>> (questionsForUser, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Integer> submitQuiz(Integer id, List<Response> responses) {
		int score = quizInterface.getScore(responses).getBody();
		
		return new ResponseEntity<Integer> (score, HttpStatus.OK);
	}


	

}
