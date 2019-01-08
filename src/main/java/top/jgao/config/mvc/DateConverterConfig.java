package top.jgao.config.mvc;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义日期转换器
 *
 * @author JiangaoXia
 * @date 2019/1/8 13:48
 */
@Slf4j
public class DateConverterConfig implements Converter<String, Date> {
    private static final List<String> formarts = new ArrayList<>(4);

    static {
        formarts.add("yyyy-MM-dd HH:mm:ss");
        formarts.add("yyyy/MM/dd HH:mm:ss");
        formarts.add("yyyy-MM");
        formarts.add("yyyy-MM-dd");
        formarts.add("yyyy-MM-dd HH:mm");
        formarts.add("yyyy/MM");
        formarts.add("yyyy/MM/dd");
        formarts.add("yyyy/MM/dd HH:mm");
    }

    @Override
    public Date convert(String s) {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        if (s.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(s, formarts.get(0));
        } else if (s.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}:\\d{1,2}$")) {
            return parseDate(s, formarts.get(1));
        } else if (s.matches("^\\d{4}-\\d{1,2}$")) {
            return parseDate(s, formarts.get(2));
        } else if (s.matches("^\\d{4}-\\d{1,2}-\\d{1,2}$")) {
            return parseDate(s, formarts.get(3));
        } else if (s.matches("^\\d{4}-\\d{1,2}-\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(s, formarts.get(4));
        } else if (s.matches("^\\d{4}/\\d{1,2}$")) {
            return parseDate(s, formarts.get(5));
        } else if (s.matches("^\\d{4}/\\d{1,2}/\\d{1,2}$")) {
            return parseDate(s, formarts.get(6));
        } else if (s.matches("^\\d{4}/\\d{1,2}/\\d{1,2} {1}\\d{1,2}:\\d{1,2}$")) {
            return parseDate(s, formarts.get(7));
        } else {
            throw new IllegalArgumentException("Invalid boolean value '" + s + "'");
        }
    }

    public Date parseDate(String dateStr, String format) {
        Date date = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat(format);
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {

        }
        return date;
    }
}
