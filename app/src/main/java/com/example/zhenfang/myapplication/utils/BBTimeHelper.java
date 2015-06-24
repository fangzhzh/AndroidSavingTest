package com.example.zhenfang.myapplication.utils;

import android.os.SystemClock;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with yanghx
 * User: yanghx
 * Date: 20/07/12
 * Time: PM05:46
 * yanghx (yang.hx@gmail.com)
 */
public class BBTimeHelper {
    public final static int DAY_IN_SEC = 86400;
    public final static int HOUR_IN_SEC = 3600;

    /**
     * @return time in seconds
     */
    public static int now() {
        long t = System.currentTimeMillis();
        return (int) (t / 1000);
    }

    /**
     * @return Seconds before/after GMT [-12*3600 12*3600]
     */
    public static int timezone() {
        TimeZone tz = TimeZone.getDefault();
        Date now = new Date();
        return tz.getOffset(now.getTime()) / 1000;
    }

    /**
     * @return If the two time stamp is in the same day,
     * This function ignored time zone, hence you may need to convert
     * The time stamp to the same timezone
     */
    public static boolean isInSameDay(int ts1, int ts2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
        return fmt.format(1000l * ts1).equals(fmt.format(1000l * ts2));
    }

    /**
     * @return time in million seconds
     */
    public static long millNow() {
        return System.currentTimeMillis();
    }

    public static long tickCount() {
        return SystemClock.elapsedRealtime();
    }


}
