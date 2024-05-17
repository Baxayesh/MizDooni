package ir.ut.ie.contracts;

import ir.ut.ie.exceptions.MizdooniException;
import ir.ut.ie.exceptions.MizdooniInternalException;
import lombok.Getter;

@Getter
public class Error {

    final String Message;
    final int StatusCode;
    final String ExceptionType;

    public Error(String message, int statusCode, String exceptionType) {
        Message = message;
        StatusCode = statusCode;
        ExceptionType = exceptionType;
    }



    public Error(MizdooniException ex){

        StatusCode = ex.getHttpStatusCode();
        ExceptionType = ex.getClass().getSimpleName();

        if(ex instanceof MizdooniInternalException){
            Message = "Mizdooni internal error occurred";
        }else {
            Message = ex.getMessage();
        }

    }



}
