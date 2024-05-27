package ir.ut.ie.testUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomAssertions {


    public static void assertEquals(
            LocalDateTime expected,
            LocalDateTime given,
            double acceptableDifferenceInMillisecond
    ){
        assertTrue(
                expected.until(given, ChronoUnit.MILLIS) < acceptableDifferenceInMillisecond
        );
    }
}
