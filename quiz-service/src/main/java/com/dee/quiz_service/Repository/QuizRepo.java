package com.dee.quiz_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dee.quiz_service.Entity.Quiz;

public interface QuizRepo extends JpaRepository<Quiz, Integer>{

}
