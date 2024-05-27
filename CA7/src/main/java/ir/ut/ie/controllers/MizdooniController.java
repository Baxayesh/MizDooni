package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.NotAValidDatetime;
import ir.ut.ie.exceptions.NotAValidNumber;
import ir.ut.ie.service.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


@Setter
@Getter
public abstract class MizdooniController {

    @Autowired
    protected Mizdooni mizdooni;
    @Autowired
    protected AuthenticationService authenticationService;

    LocalDate toDate(String field, String fieldName) throws NotAValidDatetime {
        try {
            return LocalDate.parse(field);
        } catch (DateTimeParseException ex){
            throw new NotAValidDatetime(fieldName);
        }
    }

    int toInt(String field, String fieldName) throws NotAValidNumber{
        try {
            return Integer.parseInt(field);
        } catch (NumberFormatException ex){
            throw new NotAValidNumber(fieldName);
        }
    }

    UserDetails getCurrentUser(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}



