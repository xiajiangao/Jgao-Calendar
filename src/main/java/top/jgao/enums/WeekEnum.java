package top.jgao.enums;

/**
 * @author JiangaoXia
 * @date 2019/1/7 16:00
 */
public enum WeekEnum {

    //Monday、Tuesday、Wednesday、Thursday、Friday、Saturday、Sunday
    Monday(1, "Monday", "星期一"),
    TUESDAY(2, "Tuesday", "星期二"),
    WEDNESDAY(3, "Wednesday", "星期三"),
    THURSDAY(4, "Thursday", "星期四"),
    FRIDAY(5, "Friday", "星期五"),
    SATURDAY(6, "Saturday", "星期六"),
    SUNDAY(0, "Sunday", "星期天");

    public static String getEnglish(int weekIndex) {
        for (WeekEnum a : WeekEnum.values()) {
            if (weekIndex == (a.index)) {
                return a.english;
            }
        }
        return null;
    }

    public static String getChinese(String english) {
        for (WeekEnum a : WeekEnum.values()) {
            if (english.equalsIgnoreCase(a.english)) {
                return a.chinese;
            }
        }
        return null;
    }

    WeekEnum(int index, String english, String chinese) {
        this.index = index;
        this.english = english;
        this.chinese = chinese;
    }

    private final int index;
    private final String english;
    private final String chinese;

    public int getIndex() {
        return index;
    }

    public String getEnglish() {
        return english;
    }

    public String getChinese() {
        return chinese;
    }
}
