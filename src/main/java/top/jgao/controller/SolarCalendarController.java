package top.jgao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jgao.basic.Result;
import top.jgao.persistence.service.SolarCalendarService;

import java.text.ParseException;
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

    @GetMapping("getWorkingDays")
    public Result getWorkingDays(Date startDate, Date endDate) throws ParseException {
        Map map = solarCalendarService.getWorkingDays(startDate, endDate);
        return Result.successResult(map);
    }
}
