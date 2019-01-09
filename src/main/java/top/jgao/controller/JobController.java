package top.jgao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.jgao.annotation.MyLogger;
import top.jgao.basic.Result;
import top.jgao.persistence.service.InitYearService;

/**
 * @author JiangaoXia
 * @date 2019/1/7 14:08
 */
@RestController
public class JobController {

    @Autowired
    private InitYearService initYearService;

    @MyLogger("初始化年数据")
    @PostMapping("/initYear")
    public Result initYear(@RequestParam("year") Integer year) {
        initYearService.init(year);
        return Result.successResult();
    }
}
