package com.example.administrator.chaoshen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * yyyy-MM-dd HH:mm:ss  to  时间戳
     *
     * @param date
     * @return
     */
    public static long timeToMills(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);//"yyyy/MM/dd HH:mm:ss"
        try {
            Date date1 = sdf.parse(date);
            return dateMillis(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 格式化时间
     *
     * @param milliseconds
     * @param format       yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFormatTime(long milliseconds, String format) {
        String dateString = "";
        Date date = new Date(milliseconds);
        //date.setTime(milliseconds);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static String getToday(){
        String dateString = "";
        Date date = new Date(System.currentTimeMillis());
        //date.setTime(milliseconds);
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            dateString = formatter.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }


    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    public static String date2TimeStamp2(String date_str, String format) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(format);

        Date date = sdf.parse(date_str);
        long ts = date.getTime();
        String res = String.valueOf(ts);
        return res;

    }


    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 返回时间搓
     *
     * @param date
     * @return
     */
    public static long dateMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * yyyy-MM-dd HH:mm:ss  to  时间戳
     *
     * @param date
     * @return
     */
    public static long timeToMills_2(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = sdf.parse(date);
            return dateMillis(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 将秒变为时分秒格式
     */

    // a integer to xx:xx:xx
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        int day = 0;
        if (time <= 0)
            return "00分00秒";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "分" + unitFormat(second) + "秒";
            } else {
                hour = minute / 60;
               /* if (hour > 99)
                    return "99:59:59";*/
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                if (hour < 24) {
                    timeStr = unitFormat(hour) + "时" + unitFormat(minute) + "分";//+ unitFormat(second)+"秒";
                } else {
                    day = hour / 24;
                    hour = hour % 24;
                    timeStr = day + "天" + unitFormat(hour) + "时";// + unitFormat(minute) + "分:" + unitFormat(second)+"秒";
                }
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }


}
