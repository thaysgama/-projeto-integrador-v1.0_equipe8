package com.dh.digitalbooking.controllers;

import com.dh.digitalbooking.exceptions.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ResponseEntity<Object> exception(CategoryNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = CharacteristicNotFoundException.class)
    public ResponseEntity<Object> exception(CharacteristicNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = CityNotFoundException.class)
    public ResponseEntity<Object> exception(CityNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = ImageNotFoundException.class)
    public ResponseEntity<Object> exception(ImageNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = ProductNotFoundException.class)
    public ResponseEntity<Object> exception(ProductNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = RoleNotFoundException.class)
    public ResponseEntity<Object> exception(RoleNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<Object> exception(UserNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = BookingNotFoundException.class)
    public ResponseEntity<Object> exception(BookingNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

    @ExceptionHandler(value = InvalidDateException.class)
    public ResponseEntity<Object> exception(InvalidDateException exception) {
        return new ResponseEntity<>(exception.getMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }
}
