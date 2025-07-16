package com.dee.quiz_service.Entity.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Schema (
		description = "Schema to hold Response information")
public class Response {
    private Integer id;
    private String response;
}
