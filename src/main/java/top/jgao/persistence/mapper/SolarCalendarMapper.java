package top.jgao.persistence.mapper;

import org.apache.ibatis.annotations.Param;
import top.jgao.config.db.MyMapper;
import top.jgao.persistence.domain.SolarCalendar;

import java.util.Date;

public interface SolarCalendarMapper extends MyMapper<SolarCalendar> {
    Short selectWorkingDays(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}