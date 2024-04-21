package ir.ut.ie.exceptions;;
import static ir.ut.ie.defines.Errors.ALREADY_EXISTENT_TABLE;
public class TableAlreadyExists extends MizdooniUserException {
    public TableAlreadyExists(){
        super(ALREADY_EXISTENT_TABLE);
    }
}
