package com.woori.myapp.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

//@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 일반적인 예외를 처리하는 핸들러
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleAllExceptions(Exception ex, WebRequest request) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                "GENERIC_ERROR",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        //log.debug(className, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 특정 커스텀 예외를 처리하는 핸들러
    @ExceptionHandler(CustomNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(CustomNotFoundException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                "NOT_FOUND_ERROR",
                HttpStatus.NOT_FOUND.value()
        );

        //log.debug(className, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // 또 다른 특정 예외 처리 (예: IllegalArgumentException)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                ex.getMessage(),
                "INVALID_ARGUMENT",
                HttpStatus.BAD_REQUEST.value()
        );
        //log.debug(className, ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
