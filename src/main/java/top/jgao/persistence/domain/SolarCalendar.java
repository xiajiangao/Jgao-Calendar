package top.jgao.persistence.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SolarCalendar {
    private Integer id;

    private Date date;

    private String week;

    private Byte dateTag;

    private Short workingDays;

}