package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.FieldIsRequired;
import ir.ut.ie.exceptions.NotAValidDatetime;
import ir.ut.ie.exceptions.NotAValidNumber;
import ir.ut.ie.service.Mizdooni;
import ir.ut.ie.service.MizdooniProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public abstract class MizdooniController {

    protected Mizdooni service;

    public void setService(Mizdooni mizdooni) {
        service = mizdooni;
    }

    public MizdooniController() {
        service = MizdooniProvider.GetInstance();
    }

    String getRequiredField(Map<String, String> request, String fieldName) throws FieldIsRequired {
        var field = request.get(fieldName);

        if(field == null || field.isEmpty())
            throw new FieldIsRequired(fieldName);

        return field;
    }

    LocalDate toDate(String field, String fieldName) throws NotAValidDatetime {
        try {
            return LocalDate.parse(field);
        } catch (DateTimeParseException ex){
            throw new NotAValidDatetime(fieldName);
        }
    }

    LocalDateTime toDatetime(String field, String fieldName) throws NotAValidDatetime {
        try {
            return LocalDateTime.parse(field, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
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

    double toNumber(String field, String fieldName) throws NotAValidNumber{
        try {
            return Double.parseDouble(field);
        } catch (NumberFormatException ex){
            throw new NotAValidNumber(fieldName);
        }
    }


    int getRequiredIntField(Map<String, String> request, String fieldName) throws FieldIsRequired, NotAValidNumber {
        return toInt(getRequiredField(request, fieldName), fieldName);
    }

    double getRequiredNumberField(Map<String, String> request, String fieldName) throws FieldIsRequired, NotAValidNumber {
        return toNumber(getRequiredField(request, fieldName), fieldName);
    }
}


