package top.jgao.config.mvc;

import org.springframework.core.convert.converter.Converter;
import top.jgao.utils.TimeUtil;

import java.util.Date;

/**
 * 自定义日期转换器
 *
 * @author JiangaoXia
 * @date 2019/1/8 13:48
 */
public class DateConverterConfig implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        try {
            return TimeUtil.string2Date(s, TimeUtil.TIME_FORMAT_1_1);
        } catch (Exception e) {
            return TimeUtil.String2DateByUnknowFormat(s);
        }
    }

}
