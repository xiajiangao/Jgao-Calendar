package top.jgao.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jgao.persistence.mapper.SolarCalendarMapper;
import top.jgao.persistence.request.WorkingDaysRequest;
import top.jgao.utils.TimeUtil;

import java.util.*;

@Service
public class SolarCalendarService {

    @Autowired
    private SolarCalendarMapper solarCalendarMapper;

    public Map getWorkingDays(WorkingDaysRequest workingDaysRequest) {
        if (workingDaysRequest.getStartInt() == null) {
            workingDaysRequest.setStartInt(Integer.valueOf(TimeUtil.date2String(workingDaysRequest.getStartDate(), TimeUtil.TIME_FORMAT_3_3)));
        }
        if (workingDaysRequest.getEndInt() == null) {
            workingDaysRequest.setEndInt(Integer.valueOf(TimeUtil.date2String(workingDaysRequest.getEndDate(), TimeUtil.TIME_FORMAT_3_3)));
        }
        //大小兼容
        if (workingDaysRequest.getStartInt().compareTo(workingDaysRequest.getEndInt()) > 0) {
            Integer temp = workingDaysRequest.getStartInt();
            workingDaysRequest.setStartInt(workingDaysRequest.getEndInt());
            workingDaysRequest.setEndInt(temp);
        }
        Integer startYear = Integer.valueOf(workingDaysRequest.getStartInt().toString().substring(0, 4));
        Integer endYear = Integer.valueOf(workingDaysRequest.getEndInt().toString().substring(0, 4));
        List<Integer> dateIntlist = new ArrayList<>();
        dateIntlist.add(workingDaysRequest.getStartInt());
        while (startYear < endYear) {
            dateIntlist.add(Integer.valueOf(startYear + "1231"));
            startYear++;
        }
        dateIntlist.add(workingDaysRequest.getEndInt());
        //考虑跨年与不跨年(第二个减第一个，加上后面所有的)
        List<Integer> workingDaysList = solarCalendarMapper.selectWorkingDays(dateIntlist);
        Integer workingDays = workingDaysList.get(1) - workingDaysList.get(0);
        for (int i = 2; i < workingDaysList.size(); i++) {
            workingDays = workingDays + workingDaysList.get(i);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("workingDays", workingDays);
        if (workingDaysRequest.getTotalDays()) {
            if (workingDaysRequest.getStartDate() == null) {
                workingDaysRequest.setStartDate(TimeUtil.string2Date(workingDaysRequest.getStartInt().toString(), TimeUtil.TIME_FORMAT_3_3));
            }
            if (workingDaysRequest.getEndDate() == null) {
                workingDaysRequest.setEndDate(TimeUtil.string2Date(workingDaysRequest.getEndInt().toString(), TimeUtil.TIME_FORMAT_3_3));
            }
            int differentDays = TimeUtil.differentDays(workingDaysRequest.getStartDate(), workingDaysRequest.getEndDate());
            resultMap.put("totalDays", differentDays);
        }
        return resultMap;
    }

    public Integer expectedWorkingDay(Date date, Integer days) {
        List<Integer> dateIntlist = new ArrayList<>();
        Integer dateInt = Integer.valueOf(TimeUtil.date2String(date, TimeUtil.TIME_FORMAT_3_3));
        dateIntlist.add(dateInt);
        Integer yearInt = Integer.valueOf(dateInt.toString().substring(0, 4));
        Integer lastDay = Integer.valueOf(yearInt + "1231");
        dateIntlist.add(lastDay);
        List<Integer> workingDaysList = solarCalendarMapper.selectWorkingDays(dateIntlist);
        int lastTodate = workingDaysList.get(1) - workingDaysList.get(0);
        if (days.compareTo(lastTodate) == 0) {
            return lastDay;
        } else if (days.compareTo(lastTodate) < 0) {
            int workingDays = workingDaysList.get(0) + days;
            return solarCalendarMapper.selectExpectedWorkingDay(dateInt, lastDay, workingDays);
        } else {
            int differenceDays = days - lastTodate;
            return getNewYearDay(yearInt, differenceDays);
        }
    }

    private Integer getNewYearDay(Integer yearInt, int differenceDays) {
        yearInt++;
        Integer dateIntTemp = Integer.valueOf(yearInt + "1231");
        Integer lastDayTemp = solarCalendarMapper.selectByDateInt(dateIntTemp);
        if (lastDayTemp.compareTo(differenceDays) == 0) {
            return dateIntTemp;
        } else if (lastDayTemp.compareTo(differenceDays) > 0) {
            return solarCalendarMapper.selectExpectedWorkingDay(Integer.valueOf(yearInt + "0101"), dateIntTemp, differenceDays);
        } else {
            return getNewYearDay(yearInt, differenceDays - lastDayTemp);
        }
    }
}
