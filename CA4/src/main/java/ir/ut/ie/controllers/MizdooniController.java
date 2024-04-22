package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.FieldIsRequired;
import ir.ut.ie.exceptions.NotAValidNumber;
import ir.ut.ie.service.Mizdooni;
import ir.ut.ie.service.MizdooniProvider;

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

    int getRequiredIntField(Map<String, String> request, String fieldName) throws FieldIsRequired, NotAValidNumber {

        return (int)getRequiredNumberField(request, fieldName);
    }

    double getRequiredNumberField(Map<String, String> request, String fieldName) throws FieldIsRequired, NotAValidNumber {
        var field = request.get(fieldName);

        if(field == null || field.isEmpty())
            throw new FieldIsRequired(fieldName);

        try {
            return Double.parseDouble(field);
        } catch (NumberFormatException ex){
            throw new NotAValidNumber(fieldName);
        }
    }
}


