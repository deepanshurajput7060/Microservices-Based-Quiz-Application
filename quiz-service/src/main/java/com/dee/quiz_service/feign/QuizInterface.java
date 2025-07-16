package com.dee.quiz_service.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dee.quiz_service.Entity.utils.QuestionWrapper;
import com.dee.quiz_service.Entity.utils.Response;

@FeignClient(name = "QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("/Questions/generate/{categoryName}/{numQuestions}")
    ResponseEntity<List<Integer>> getQuestionsForQuiz(
            @PathVariable("categoryName") String categoryName,
            @PathVariable("numQuestions") Integer numQuestions);

    @PostMapping("/Questions/getQuestions")
    ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

    @PostMapping("/Questions/getScore")
    ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
}
