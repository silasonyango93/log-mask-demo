package com.kcb.interview.silasonyango.books.config;

import com.kcb.interview.silasonyango.books.dto.GenericRequestResponseDto;
import com.kcb.interview.silasonyango.books.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAdviceConfig {

  @ExceptionHandler({ResourceNotFoundException.class})
  public final ResponseEntity<Object> handleResourceNotFoundException(
      ResourceNotFoundException exception) {

    var response = GenericRequestResponseDto.builder()
        .responseCode("NOT_FOUND")
        .responseMessage(exception.getMessage())
        .build();

    return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
  }
}
