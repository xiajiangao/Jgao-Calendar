package top.jgao.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import top.jgao.config.db.MyMapper;
import top.jgao.persistence.domain.SolarCalendar;

public interface SolarCalendarMapper extends MyMapper<SolarCalendar> {
    Short selectWorkingDays(@Param("startInt") Integer startInt, @Param("endInt") Integer endInt);
}