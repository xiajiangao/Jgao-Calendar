package top.jgao.persistence.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jgao.persistence.mapper.SolarCalendarMapper;
import top.jgao.persistence.request.WorkingDaysRequest;
import top.jgao.utils.TimeUtil;

import java.util.HashMap;
import java.util.Map;

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
        Short workingDays = solarCalendarMapper.selectWorkingDays(workingDaysRequest.getStartInt(), workingDaysRequest.getEndInt());
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
}
