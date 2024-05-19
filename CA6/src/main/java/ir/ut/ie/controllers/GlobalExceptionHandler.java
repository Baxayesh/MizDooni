package ir.ut.ie.controllers;

import ir.ut.ie.contracts.Error;
import ir.ut.ie.exceptions.MizdooniException;
import ir.ut.ie.exceptions.MizdooniUserException;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    Error createResponse(HttpServletRequest req, Exception ex){

        if(ex instanceof MizdooniException mizdooniException)
                return new Error(mizdooniException);

        if(ex instanceof ExecutionControl.NotImplementedException)
            return new Error(
                    "Functionality not implemented yet",
                    HttpStatus.NOT_IMPLEMENTED.value(),
                    "NotImplementedException");

        if(ex instanceof MethodArgumentNotValidException manve)
            return handleValidationExceptions(manve);

        return new Error(ex.getMessage(), 500, ex.getClass().getName());
    }


    @ExceptionHandler({
            MizdooniException.class,
            ExecutionControl.NotImplementedException.class,
            MethodArgumentNotValidException.class,
            Exception.class
    })
    public ResponseEntity<Error> handleError(HttpServletRequest req, Exception ex) {

        var response = createResponse(req, ex);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));

    }


    public Error handleValidationExceptions(MethodArgumentNotValidException ex) {
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

        return error;
    }
}
