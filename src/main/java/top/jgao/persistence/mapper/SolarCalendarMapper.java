package top.jgao.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import top.jgao.config.db.MyMapper;
import top.jgao.persistence.domain.SolarCalendar;

import java.util.List;

public interface SolarCalendarMapper extends MyMapper<SolarCalendar> {
    List<Integer> selectWorkingDays(@Param("dateIntlist") List<Integer> dateIntlist);

    Integer selectExpectedWorkingDay(@Param("dateInt") Integer dateInt,
                                     @Param("lastDay") Integer lastDay,
                                     @Param("workingDays") Integer workingDays);

    Integer selectByDateInt(@Param("dateInt") Integer dateInt);
}