package com.dee.Question_Service.Entity.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema (
		name = "Question",
		description = "Schema to hold questions information")
public class QuestionDto {
	
	@NotBlank (message = "This field can not be null or empty")
	@Size (min = 5, max = 500, message = "Length should be between 5 to 500 character")
    private String questionTitle;
	
	@NotBlank (message = "This field can not be null or empty")
	@Size (min = 1, max = 50, message = "Length should be between 1 to 50 character")
    private String option1;
	
	@NotBlank (message = "This field can not be null or empty")
	@Size (min = 1, max = 50, message = "Length should be between 1 to 50 character")
    private String option2;
	
	@NotBlank (message = "This field can not be null or empty")
	@Size (min = 1, max = 50, message = "Length should be between 1 to 50 character")
    private String option3;
	
	@NotBlank (message = "This field can not be null or empty")
	@Size (min = 1, max = 50, message = "Length should be between 1 to 50 character")
    private String option4;
	
	@NotBlank (message = "This field can not be null or empty")	
	@Pattern(regexp = "^[1-4]{1}$", message = "Right answer must be one of: 1, 2, 3, or 4")
    private String rightAnswer;
	
	@NotBlank(message = "Difficulty level must not be null")
	@Pattern(regexp = "EASY|MEDIUM|HARD", message = "Must be EASY, MEDIUM, or HARD")
    private String difficultylevel;
	
	@NotBlank(message = "Category must not be null")
	@Pattern(regexp = "EDUCATION|SPORTS|POLITICS", message = "Must be EDUCATION, SPORTS, or POLITICS")
    private String category;
}

