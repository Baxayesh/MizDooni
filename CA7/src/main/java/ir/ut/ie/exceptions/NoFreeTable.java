package ir.ut.ie.exceptions;

public class NoFreeTable extends MizdooniUserException {

    public NoFreeTable() {
        super("There is no free table on chosen date");
    }
}
