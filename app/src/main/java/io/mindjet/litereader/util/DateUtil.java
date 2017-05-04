package io.mindjet.litereader.util;

import android.text.format.DateFormat;

import java.util.Date;

/**
 * Date Utility
 * <p>
 * Created by Jet on 3/14/17.
 */

public class DateUtil {

    public static Date yesterday(Date date) {
        return new Date(date.getTime() - 24 * 60 * 60 * 1000);
    }

    public static String toFriendlyFormat(Date date) {
        long now = new Date().getTime();
        long target = date.getTime();
        long diff = now - target;
        int days = (int) Math.ceil(diff / (24 * 60 * 60 * 1000));
        switch (days) {
            case 1:
                return "昨天";
            case 2:
                return "两天前";
            default:
                return DateFormat.format("yyyy-MM-dd", date).toString() + " " + getWeekDay(date);
        }
    }

    public static String getWeekDay(Date date) {
        switch (date.getDay()) {
            case 0:
                return "星期日";
            case 1:
                return "星期一";
            case 2:
                return "星期二";
            case 3:
                return "星期三";
            case 4:
                return "星期四";
            case 5:
                return "星期五";
            case 6:
                return "星期六";
            default:
                return "星期一";
        }
    }

    /**
     * 判断给出的时间与当前时间的差距是否大于给出的分钟数
     */
    public static boolean timeLapsed(long time, long minutes) {
        long diff = System.currentTimeMillis() - time;
        return diff / 1000 / 60 >= minutes;
    }

}
