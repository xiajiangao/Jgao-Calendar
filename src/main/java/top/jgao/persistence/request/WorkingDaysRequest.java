package top.jgao.persistence.request;

import lombok.Data;

import java.util.Date;

/**
 * 获取工作日的请求参数类
 *
 * @author JiangaoXia
 * @date 2019/1/8 16:30
 */
@Data
public class WorkingDaysRequest {
    Date startDate;
    Date endDate;
    Integer startInt;
    Integer endInt;
    Boolean totalDays = false;
}
