package top.jgao.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtil {
    public static final String TIME_FORMAT_1 = "yyyyMMddHHmmss";

    public static final String TIME_FORMAT_2 = "yyyy-MM-dd HH:mm:ss";

    public static final String TIME_FORMAT_3 = "yyyy-MM-dd";

    public static final String TIME_FORMAT_3_CHINA = "yyyy年MM月dd日";

    private static DateFormat timeFormat(String timeFormat) {
        return new SimpleDateFormat(StringUtils.isEmpty(timeFormat) ? TIME_FORMAT_2 : timeFormat);
    }

    public static String time2String(long time) {
        Date dat = new Date(time);
        return time2String(dat);
    }

    public static String time2String() {
        return time2String(new Date());
    }

    public static String time2String(Date date) {
        return time2String(date, "");
    }

    public static String time2String(long time, String timeFormat) throws ParseException {
        return time2String(time2Date(time), timeFormat);
    }

    public static String time2String(Date date, String timeFormat) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        DateFormat format = timeFormat(timeFormat);
        return format.format(gc.getTime());
    }

    public static long String2time(String dateStr) throws ParseException {
        return String2time(dateStr, TIME_FORMAT_2);
    }

    public static Date String2Date(String dateStr) {
        return String2Date(dateStr, TIME_FORMAT_2);
    }

    public static Date String2Date(String dateStr, String timeFormat) {
        Date date = null;
        if (!StringUtils.isEmpty(dateStr)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(
                        StringUtils.isEmpty(timeFormat) ? "yyyy-MM-dd HH:mm:ss" : timeFormat);
                format.setLenient(false);
                date = format.parse(dateStr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return date;
    }

    public static long String2time(String dateStr, String timeFormat) {
        Date date = String2Date(dateStr, timeFormat);
        return date == null ? 0 : date.getTime();
    }

    public static Date time2Date(long time) throws ParseException {
        return time2Date(time, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date time2Date(long time, String timeFormat) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(timeFormat);
        String d = format.format(time);
        format.setLenient(false);
        return format.parse(d);
    }

    public static int getYearOfDate(Date p_date) {
        Calendar c = Calendar.getInstance();
        c.setTime(p_date);
        return c.get(Calendar.YEAR);
    }

    public static int getMonthOfDate(Date p_date) {
        Calendar c = Calendar.getInstance();
        c.setTime(p_date);
        return c.get(Calendar.MONTH) + 1;
    }

    public static int getDayOfDate(Date p_date) {
        Calendar c = Calendar.getInstance();
        c.setTime(p_date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取两个时间之间的月份差
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

    public static void main(String[] args) throws ParseException {

        // System.out.println(String2time("2016-07-12 15:00:00"));
        // System.out.println(String2time("2016-10-12 00:00:00"));
        // System.out.println(time2String(1468306800000l));
        /*
         * System.out.println(time2String(new Date(), "yyyyMMddHHmmSS"));
         * System.out.println(String2time("201611010847351", "yyyyMMddHHmmSS"));
         */
        Date date1 = String2Date("2018-05-31 00:00:00");
        Date date2 = String2Date("2019-05-30 23:59:59");
        System.err.println(differentDays(date1, date2));//394
//        System.out.println(getMonths(date1, date2));
//        Calendar objCalendarDate1 = Calendar.getInstance();
//        objCalendarDate1.setTime(date1);
//        int day = objCalendarDate1.get(Calendar.DAY_OF_MONTH);
//        System.out.println(day);
//        // System.out.println(getSurplusPeriods(date1,date2));
//        System.out.println(time2String(new Date(), TIME_FORMAT_3_CHINA));
    }
}
