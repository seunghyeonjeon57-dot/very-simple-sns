package com.seunghyeon.verysimplesns.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> exceptionArgument(Exception e){
    log.error("에상치 못한 예외 발생",e);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버오류");
  }


  @ExceptionHandler(SimpleSnsException.class)
    public ResponseEntity<String> simpleSnsExceptionArgument(SimpleSnsException e){
      return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> methodArgumentNotValidException(MethodArgumentNotValidException e){
    String message = e.getBindingResult().getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.joining(", "));
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

  }


  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<String> dataIntegrityViolationException(DataIntegrityViolationException e){
    return ResponseEntity.status(HttpStatus.CONFLICT).body("이미 사용중인 값입니다.");
  }
}
