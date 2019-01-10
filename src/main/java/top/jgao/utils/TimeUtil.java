package top.jgao.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static final String TIME_FORMAT_1_1 = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_FORMAT_1_2 = "yyyy-MM";
    public static final String TIME_FORMAT_1_3 = "yyyy-MM-dd";
    public static final String TIME_FORMAT_1_4 = "yyyy-MM-dd HH:mm";
    public static final String TIME_FORMAT_2_1 = "yyyy/MM/dd HH:mm:ss";
    public static final String TIME_FORMAT_2_2 = "yyyy/MM";
    public static final String TIME_FORMAT_2_3 = "yyyy/MM/dd";
    public static final String TIME_FORMAT_2_4 = "yyyy/MM/dd HH:mm";
    public static final String TIME_FORMAT_3_1 = "yyyyMMdd HH:mm:ss";
    public static final String TIME_FORMAT_3_3 = "yyyyMMdd";
    public static final String TIME_FORMAT_4_CHINA = "yyyy年MM月dd日";

    public static Date string2Date(String dateStr, String timeFormat) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFormat);
            return dateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间转换错误(" + timeFormat + ")：" + dateStr);
        }
    }

    public static String date2String(Date date, String timeFormat) {
        try {
            DateFormat dateFormat = new SimpleDateFormat(timeFormat);
            return dateFormat.format(date);
        } catch (Exception e) {
            throw new IllegalArgumentException("时间转换错误(" + timeFormat + ")：" + date);
        }
    }

    public static Date String2DateByUnknowFormat(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_1_1);
        } else if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_2_1);
        } else if (dateStr.matches("^\\d{6,8} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_3_1);
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_1_2);
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_1_3);
        } else if (dateStr.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_1_4);
        } else if (dateStr.matches("^\\d{4}/\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_2_2);
        } else if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_2_3);
        } else if (dateStr.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return string2Date(dateStr, TIME_FORMAT_2_4);
        } else {
            throw new IllegalArgumentException("时间转换错误：" + dateStr);
        }
    }

    /**
     * 获取两个时间之间的月份差,不足一个月忽略。
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonths(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        Calendar beginDate = Calendar.getInstance();
        beginDate.setTime(date1);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(date2);
        endDate.add(Calendar.SECOND, 1);
        if (endDate.equals(beginDate))
            return 0;
        if (beginDate.after(endDate)) {
            Calendar temp = beginDate;
            beginDate = endDate;
            endDate = temp;
        }
        if (endDate.get(Calendar.DAY_OF_MONTH) < beginDate.get(Calendar.DAY_OF_MONTH) && !isLastDayOfMonth(beginDate.getTime())
                && !isLastDayOfMonth(endDate.getTime()))
            flag = 1;
        if (endDate.get(Calendar.YEAR) > beginDate.get(Calendar.YEAR))
            iMonth = ((endDate.get(Calendar.YEAR) - beginDate.get(Calendar.YEAR)) * 12 + endDate.get(Calendar.MONTH)
                    - flag) - beginDate.get(Calendar.MONTH);
        else
            iMonth = endDate.get(Calendar.MONTH) - beginDate.get(Calendar.MONTH) - flag;
        return iMonth;
    }


    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        boolean flag = false;
        if (date1.after(date2)) {
            flag = true;
            Date temp = null;
            temp = date1;
            date1 = date2;
            date2 = temp;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);
        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        //同一年
        if (year1 != year2) {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                //闰年
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {
                    timeDistance += 366;
                } else {
                    //不是闰年
                    timeDistance += 365;
                }
            }
            if (flag) {
                return -(timeDistance + (day2 - day1));
            } else {
                return timeDistance + (day2 - day1);
            }
        } else {
            //不同年
            return day2 - day1;
        }
    }

    /**
     * 判断是否为月末
     *
     * @param date
     * @return
     */
    public static boolean isLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
        if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
            return true;
        }
        return false;
    }
}
