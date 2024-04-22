package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.MizdooniException;
import ir.ut.ie.exceptions.MizdooniInternalException;
import jakarta.servlet.http.HttpServletRequest;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {


    //TODO: change type of exceptins
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(HttpServletRequest req, Exception ex) {

        if(ex instanceof MizdooniException mizdooniException)
            if(mizdooniException instanceof MizdooniInternalException)
                return new ResponseEntity<>("Mizdooni internal error occurred",
                        mizdooniException.getHttpStatusCode());
            else
                return new ResponseEntity<>(mizdooniException.getMessage(), mizdooniException.getHttpStatusCode());

        if(ex instanceof ExecutionControl.NotImplementedException)
            return new ResponseEntity<>("Functionality not implemented yet", HttpStatus.NOT_IMPLEMENTED);


        //TODO: remove exception message from api output
        return new ResponseEntity<>("Unknown error occurred: %s, error type: %s".formatted(ex.getMessage(), ex.getClass().getName()),
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
