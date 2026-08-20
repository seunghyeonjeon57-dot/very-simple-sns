package com.seunghyeon.verysimplesns.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(SimpleSnsException.class)
    public ResponseEntity<String> simpleSnsExceptionArgument(SimpleSnsException e){
      return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
  }
}
