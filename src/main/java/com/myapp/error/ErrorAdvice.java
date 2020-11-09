package com.myapp.error;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {

  @ResponseBody
  @ExceptionHandler(RecordNotFoundException.class)
  public ResponseEntity<Object> notFoundHandler(RecordNotFoundException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.NOT_FOUND.value());
    body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ResponseBody
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> badRequestHandler(BadRequestException ex) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("status", HttpStatus.BAD_REQUEST.value());
    body.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase());
    body.put("message", ex.getMessage());

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

}