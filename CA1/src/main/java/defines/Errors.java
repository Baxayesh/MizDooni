package defines;

public class Errors {

    public static final String NOT_EXISTENT_USER = "User Does Not Exists";

    public static  final  String NOT_EXISTENT_RESTAURANT = "Restaurant Does Not Exists";

    public static final String NOT_EXISTENT_TABLE = "Table Does Not Exists";

    public static final String NOT_EXISTENT_RESERVE = "Reserve Not Found";

    public static final  String TABLE_IS_ALREADY_RESERVED = "There Is An Active Reserve On Chosen Table/Time";

    public static final String TIME_IS_NOT_ROUND = "Input Time Is Not Round (Time Should Just Contain Hour)";

    public static final String TIME_BELONGS_TO_PAST = "Input Time Belongs To Past";

    public static final String NOT_IN_WORK_HOUR = "Input Time Is Not In Restaurant Work Hours";

    public static final String CANCELING_EXPIRED_RESERVE = "Cannot Cancel A Reserve From Past";

    public static final String CANCELING_CANCELED_RESERVE  = "Reserve Is Already Canceled";

    public static final String SCORE_OUT_OF_RANGE = "Score Is Not In Valid Score Range";
}