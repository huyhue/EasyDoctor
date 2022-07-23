package fpt.edu.vn.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String objToString(Object obj) {
        if (obj == null)
            return "";
        return String.valueOf(obj);
    }

    public static Integer objToInt(Object obj) {
        if (obj == null)
            return 0;
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static Long objToLong(Object obj) {
        if (obj == null)
            return 0L;
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return 0L;
        }
    }

    public static Boolean objToBoolean(Object obj) {
        if (obj == null)
            return false;
        return Boolean.parseBoolean(obj.toString());
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }
}