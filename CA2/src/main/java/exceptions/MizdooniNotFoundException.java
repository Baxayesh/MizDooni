package exceptions;

public class MizdooniNotFoundException extends MizdooniException {

    public MizdooniNotFoundException(String entityName){
        super("%s Not Found".formatted(entityName));
    }

}
