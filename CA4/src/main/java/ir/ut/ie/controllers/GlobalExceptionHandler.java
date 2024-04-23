package ir.ut.ie.controllers;

import ir.ut.ie.contracts.Error;
import ir.ut.ie.exceptions.MizdooniException;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
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

        return new Error(ex.getMessage(), 500, ex.getClass().getName());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleError(HttpServletRequest req, Exception ex) {

        var response = createResponse(req, ex);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatusCode()));

    }

}
