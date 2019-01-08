package top.jgao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jgao.annotation.MyLogger;
import top.jgao.basic.Result;
import top.jgao.persistence.request.WorkingDaysRequest;
import top.jgao.persistence.service.SolarCalendarService;

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
    @GetMapping("getWorkingDays")
    public Result getWorkingDays(WorkingDaysRequest workingDaysRequest) {
        Map map = solarCalendarService.getWorkingDays(workingDaysRequest);
        return Result.successResult(map);
    }
}
