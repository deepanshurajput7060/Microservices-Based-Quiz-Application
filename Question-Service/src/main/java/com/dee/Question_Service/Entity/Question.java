package com.dee.Question_Service.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
    private String questionTitle;
	
	@Column (nullable = false)
    private String option1;
	
	@Column (nullable = false)
    private String option2;
	
	@Column (nullable = false)
    private String option3;
	
	@Column (nullable = false)
    private String option4;
	
	@Column (nullable = false)
    private String rightAnswer;
    
	@Column (nullable = false)
    private String difficultylevel;
	
	@Column (nullable = false)
    private String category;
      
}
