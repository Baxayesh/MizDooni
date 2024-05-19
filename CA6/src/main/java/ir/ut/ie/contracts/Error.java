package ir.ut.ie.contracts;

import ir.ut.ie.exceptions.MizdooniException;
import ir.ut.ie.exceptions.MizdooniInternalException;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Error {

    final String Message;
    final int StatusCode;
    final String ExceptionType;
    final Map<String, String> Details;

    public Error(String message, int statusCode, String exceptionType) {
        Message = message;
        StatusCode = statusCode;
        ExceptionType = exceptionType;
        Details = new HashMap<>();
    }



    public Error(MizdooniException ex){

        StatusCode = ex.getHttpStatusCode();
        ExceptionType = ex.getClass().getSimpleName();
        Details = Map.of();

        if(ex instanceof MizdooniInternalException){
            Message = "Mizdooni internal error occurred";
        }else {
            Message = ex.getMessage();
        }

    }



}
