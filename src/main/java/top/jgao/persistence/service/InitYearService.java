package top.jgao.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import top.jgao.enums.DateTagEnum;
import top.jgao.enums.WeekEnum;
import top.jgao.persistence.domain.SolarCalendar;
import top.jgao.persistence.mapper.SolarCalendarMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class InitYearService {

    @Autowired
    private SolarCalendarMapper solarCalendarMapper;

    public void init(Integer year) throws ParseException {
        Assert.isTrue(year > 1970 && year < 2037, "年份超过限制");
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date parse = format.parse(year + "0101");
        Calendar calendar = Calendar.getInstance();
        Calendar calendarNext = Calendar.getInstance();
        calendar.setTime(parse);
        calendarNext.setTime(parse);
        calendarNext.add(Calendar.YEAR, 1);
        //查询当年是否已经初始化
        SolarCalendar record = new SolarCalendar();
        record.setDate(parse);
        int count = solarCalendarMapper.selectCount(record);
        Assert.isTrue(count == 0, "当年已经初始化");
        List<SolarCalendar> list = new ArrayList<>();
        int wordDays = 0;
        while (calendar.compareTo(calendarNext) < 0) {
            SolarCalendar solarCalendar = getNewSolarCalendar(calendar, wordDays);
            if (DateTagEnum.WORKING_DAY.getValue() == solarCalendar.getDateTag()) {
                wordDays++;
            }
            solarCalendar.setWorkingDays((short) wordDays);
            list.add(solarCalendar);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        solarCalendarMapper.insertList(list);
    }

    private SolarCalendar getNewSolarCalendar(Calendar calendar, int wordDays) {
        SolarCalendar solarCalendar = new SolarCalendar();
        solarCalendar.setDate(calendar.getTime());
        int weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        solarCalendar.setWeek(WeekEnum.getEnglish(weekIndex));
        if (weekIndex == 6 || weekIndex == 7) {
            solarCalendar.setDateTag(DateTagEnum.WEEKEND.getValue());
        } else {
            solarCalendar.setDateTag(DateTagEnum.WORKING_DAY.getValue());
        }
        return solarCalendar;
    }
}
