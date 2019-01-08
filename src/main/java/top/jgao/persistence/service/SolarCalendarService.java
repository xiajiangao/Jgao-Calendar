package top.jgao.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jgao.persistence.mapper.SolarCalendarMapper;
import top.jgao.utils.TimeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class SolarCalendarService {

    @Autowired
    private SolarCalendarMapper solarCalendarMapper;

    public Map getWorkingDays(Date startDate, Date endDate) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String startFormat = format.format(startDate);
        String endFormat = format.format(endDate);
        startDate = format.parse(startFormat);
        endDate = format.parse(endFormat);
        Short workingDays = solarCalendarMapper.selectWorkingDays(startDate, endDate);
        int differentDays = TimeUtil.differentDays(startDate, endDate);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("workingDays", workingDays);
        resultMap.put("totalDays", differentDays);
        return resultMap;
    }
}
