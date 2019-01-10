package top.jgao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jgao.annotation.MyLogger;
import top.jgao.basic.Result;
import top.jgao.persistence.request.WorkingDaysRequest;
import top.jgao.persistence.service.SolarCalendarService;

import java.util.Date;
import java.util.Map;

/**
 * @author JiangaoXia
 * @date 2019/1/8 9:43
 */

@RestController
public class SolarCalendarController {

    @Autowired
    private SolarCalendarService solarCalendarService;

    @MyLogger("查询节假日")
    @PostMapping("getWorkingDays")
    public Result getWorkingDays(WorkingDaysRequest workingDaysRequest) {
        Map map = solarCalendarService.getWorkingDays(workingDaysRequest);
        return Result.successResult(map);
    }

    @MyLogger("预期工作日")
    @PostMapping("expectedWorkingDay")
    public Result expectedWorkingDay(Date date, Integer days) {
        solarCalendarService.expectedWorkingDay(date, days);
        return Result.successResult();
    }
}
