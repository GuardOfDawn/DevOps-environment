package tool;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Author: stk
 * Date: 3/25/17
 * Time: 2:01 PM
 */
public class TimeTransformer {
    /**
     * Transform timestamp(millisecond) to date.
     *
     * @param time Millisecond
     * @return Date: yyyy-MM-dd HH:mm:ss
     */
    public static String timestamp2Date(String time) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Timestamp(Long.parseLong(time)));
    }

    /**
     * Transform time span(millisecond) to formatted string.
     *
     * @param time Millisecond
     * @return Time: d HH:mm:ss
     */
    public static String timeDiff2String(long time) {
        long second = time / 1000;
        long[] t = new long[]{0, 0, 0, 0};
        int[] divider = new int[]{0, 24, 60, 60};
        for (int i = 3; i > 0; i--) {
            t[i] = second % divider[i];
            second /= divider[i];
        }
        t[0] = second;
        return String.format("%d %02d:%02d:%02d", t[0], t[1], t[2], t[3]);
    }

    /**
     * Transform time span(millisecond) to formatted string.
     *
     * @param time Millisecond
     * @return Time: d HH:mm:ss
     */
    public static String timeDiff2String(String time) {
        return timeDiff2String(Long.parseLong(time));
    }
}
