package ir.ut.ie.controllers;

import ir.ut.ie.contracts.Error;
import ir.ut.ie.exceptions.MizdooniException;
import ir.ut.ie.exceptions.MizdooniNotAuthenticatedException;
import ir.ut.ie.exceptions.MizdooniNotFoundException;
import ir.ut.ie.exceptions.MizdooniUserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MizdooniException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleUserError(MizdooniException ex){
        return new Error(ex).getResponse();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleValidationError(MethodArgumentNotValidException ex){
        var error = new Error(
                "Some request fields are invalid (see details)",
                HttpStatus.BAD_REQUEST.value(),
                MizdooniUserException.class.getSimpleName()
        );

        ex.getBindingResult().getAllErrors().forEach(invalidField -> {
            String fieldName = ((FieldError) invalidField).getField();
            String errorMessage = invalidField.getDefaultMessage();
            error.getDetails().put(fieldName, errorMessage);
        });

        return error.getResponse();
    }
}
