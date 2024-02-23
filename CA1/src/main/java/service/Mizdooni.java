package service;

import exceptions.ExampleInAppException;

public class Mizdooni {

    public void ExampleAppFeature(int exampleArg) throws ExampleInAppException {
        if(exampleArg > 0){
            throw new ExampleInAppException();
        }
    }
}