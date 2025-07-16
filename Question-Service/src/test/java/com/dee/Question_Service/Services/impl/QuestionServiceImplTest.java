package com.dee.Question_Service.Services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import com.dee.Question_Service.Entity.Question;
import com.dee.Question_Service.Entity.utils.QuestionDto;
import com.dee.Question_Service.Entity.utils.QuestionWrapper;
import com.dee.Question_Service.Repository.QuestionRepo;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

	@Mock
	private QuestionRepo questionRepo;
	
	@Mock
	private ModelMapper mapper;
	
	@InjectMocks
	private QuestionServiceImpl questionService;
	

	@Test
    void testGetAllQuestions() {
        List<Question> questionList = List.of(new Question());
        QuestionDto dto = new QuestionDto();
        when(questionRepo.findAll()).thenReturn(questionList);
        when(mapper.map(any(Question.class), eq(QuestionDto.class))).thenReturn(dto);

        ResponseEntity<List<QuestionDto>> response = questionService.getAllQuestions();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }
	
	@Test
	void testGetQuestionsByCategory() {
	    String category = "EDUCATION";
	    List<Question> questions = List.of(new Question());

	    when(questionRepo.findByCategory(category)).thenReturn(questions); 
	    when(mapper.map(any(Question.class), eq(QuestionDto.class))).thenReturn(new QuestionDto());

	    ResponseEntity<List<QuestionDto>> response = questionService.getQuestionsByCategory(category);

	    assertEquals(200, response.getStatusCodeValue());
	    assertEquals(1, response.getBody().size()); 
	}
	
	@Test
    void testAddQuestion_Duplicate() {
        QuestionDto dto = new QuestionDto();
        Question question = new Question();

        when(mapper.map(dto, Question.class)).thenReturn(question);
        doThrow(new DataIntegrityViolationException("Duplicate")).when(questionRepo).save(question);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> questionService.addQuestion(dto));
        assertTrue(exception.getMessage().contains("Duplicate question title"));
    }
	
    @Test
    void testDeleteQuestion() {
        ResponseEntity<String> response = questionService.deleteQuestion(1);
        verify(questionRepo).deleteById(1);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetQuestionsForQuiz() {
        List<Integer> ids = List.of(1, 2, 3);
        when(questionRepo.findRandomQuestionsByCategory("Java", 3)).thenReturn(ids);

        ResponseEntity<List<Integer>> response = questionService.getQuestionsForQuiz("Java", 3);
        assertEquals(3, response.getBody().size());
    }

    @Test
    void testGetQuestionsFromId() {
        Question q = new Question(1, "Q1", "A", "B", "C", "D", "A", "Java", null);
        when(questionRepo.findById(1)).thenReturn(Optional.of(q));

        ResponseEntity<List<QuestionWrapper>> response = questionService.getQuestionsFromId(List.of(1));
        assertEquals(1, response.getBody().size());
        assertEquals("Q1", response.getBody().get(0).getQuestionTitle());
    }
}













