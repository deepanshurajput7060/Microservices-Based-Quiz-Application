package com.dee.Question_Service.Controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.dee.Question_Service.Entity.utils.QuestionDto;
import com.dee.Question_Service.Entity.utils.QuestionWrapper;
import com.dee.Question_Service.Entity.utils.Response;
import com.dee.Question_Service.Services.QuestionService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest (value = QuestionController.class)
public class QuestionControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    @Autowired
    private ObjectMapper objectMapper;
    
    
    @Test
    void testGetAllQuestions() throws Exception {
    	
    	List<QuestionDto> questions = List.of(new QuestionDto());
    	
    	Mockito.when(questionService.getAllQuestions())
    			.thenReturn(ResponseEntity.ok(questions));
		
    	mockMvc.perform(get("/Questions/getAll"))
        		.andExpect(status().isOk());
    	
    }
    
    @Test
    void testGetQuestionsByCategory() throws Exception {
        List<QuestionDto> questions = List.of(new QuestionDto());
        Mockito.when(questionService.getQuestionsByCategory("Java")).thenReturn(ResponseEntity.ok(questions));

        mockMvc.perform(get("/Questions/Java"))
                .andExpect(status().isOk());
    }

    @Test
    void testAddQuestion() throws Exception {
        QuestionDto questionDto = new QuestionDto();
        
        Mockito.when(questionService.addQuestion(any()))
        		.thenReturn(ResponseEntity.ok("Question added"));
        
        mockMvc.perform(post("/Questions/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(questionDto)))
        		.andExpect(status().isOk());
        
    }

    @Test
    void testDeleteQuestion() throws Exception {
    	Mockito.when(questionService.deleteQuestion(1)).thenReturn(ResponseEntity.ok("Deleted"));

        mockMvc.perform(delete("/Questions/delete/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetQuestionsForQuiz() throws Exception {
        List<Integer> questionIds = List.of(1, 2, 3);
        Mockito.when(questionService.getQuestionsForQuiz("Java", 3)).thenReturn(ResponseEntity.ok(questionIds));

        mockMvc.perform(get("/Questions/generate/Java/3"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetQuestionsFromId() throws Exception {
        List<QuestionWrapper> questions = List.of(new QuestionWrapper());
        List<Integer> ids = List.of(1, 2, 3);

        Mockito.when(questionService.getQuestionsFromId(anyList())).thenReturn(ResponseEntity.ok(questions));

        mockMvc.perform(post("/Questions/getQuestions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ids)))
                .andExpect(status().isOk());
    }

    @Test
    void testGetScore() throws Exception {
        List<Response> responses = List.of(new Response());
        Mockito.when(questionService.getScore(anyList())).thenReturn(ResponseEntity.ok(3));

        mockMvc.perform(post("/Questions/getScore")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(responses)))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }
    
}

















