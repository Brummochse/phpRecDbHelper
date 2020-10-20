package phpRecDB.helper.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {

    public static String convertMillisecondsToTimeStr(long millis) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }
}
