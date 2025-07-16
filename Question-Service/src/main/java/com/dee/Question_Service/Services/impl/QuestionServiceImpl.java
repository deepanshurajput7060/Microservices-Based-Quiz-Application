package com.dee.Question_Service.Services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dee.Question_Service.Entity.Question;

import com.dee.Question_Service.Entity.utils.QuestionDto;
import com.dee.Question_Service.Entity.utils.QuestionWrapper;
import com.dee.Question_Service.Entity.utils.Response;
import com.dee.Question_Service.Exceptions.ResourceNotFoundException;
import com.dee.Question_Service.Repository.QuestionRepo;
import com.dee.Question_Service.Services.QuestionService;


@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	private QuestionRepo questionRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<List<QuestionDto>> getAllQuestions() {
		List<Question> questions = questionRepo.findAll();
		List<QuestionDto> questionDtos = questions.stream()
												.map(question -> modelMapper.map(question, QuestionDto.class))
												.collect(Collectors.toList());
												
		return new ResponseEntity<> (questionDtos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<QuestionDto>> getQuestionsByCategory(String category) {
		List<Question> questions = questionRepo.findByCategory(category);
		List<QuestionDto> questionDtos = questions.stream()
												.map(question -> modelMapper.map(question, QuestionDto.class))
												.collect(Collectors.toList());
		
		return new ResponseEntity<List<QuestionDto>> (questionDtos, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<String> addQuestion(QuestionDto questionDto) {
		Question question = modelMapper.map(questionDto, Question.class);
		try {
		    questionRepo.save(question);
		    return new ResponseEntity<String> ("Question added successfully", HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
		    throw new RuntimeException("Duplicate question title found", e);
		}
		
	}
	
	@Override
	public ResponseEntity<String> deleteQuestion(Integer id) {
		questionRepo.deleteById(id);
		String message = "Question : " + id +" deleted successfully ";
		return new ResponseEntity<String> (message, HttpStatus.OK);
	}	

	@Override
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
		List<Integer> questionIds = questionRepo.findRandomQuestionsByCategory(categoryName, numQuestions);
		return new ResponseEntity<List<Integer>> (questionIds, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {

        List<Question> questions = new ArrayList<>();
        
        for (Integer id: questionIds) {
        	questions.add(questionRepo.findById(id)
        			.orElseThrow(() -> new ResourceNotFoundException("Question", "id", id)));
        }
        
        List<QuestionWrapper> wrappers = questions.stream()
        	    .map(q -> modelMapper.map(q, QuestionWrapper.class))
        	    .collect(Collectors.toList());
        
		return new ResponseEntity<List<QuestionWrapper>> (wrappers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int score=0;
		
		for (Response response: responses) {
			Question question = questionRepo.findById(response.getId()).
					orElseThrow(() -> new ResourceNotFoundException("Question", "id", response.getId()));
			
			if (response.getResponse().equals(question.getRightAnswer())) {
				score++;
			}
		}
		return new ResponseEntity<Integer> (score, HttpStatus.OK);
	}

	
}
