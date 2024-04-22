package ir.ut.ie.controllers;

import ir.ut.ie.exceptions.FieldIsRequired;
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
}


