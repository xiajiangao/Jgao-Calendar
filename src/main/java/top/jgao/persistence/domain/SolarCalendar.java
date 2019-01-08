package top.jgao.persistence.domain;

import lombok.Data;

@Data
public class SolarCalendar {
    private Integer id;

    private Integer dateInt;

    private String week;

    private Byte dateTag;

    private Short workingDays;

}