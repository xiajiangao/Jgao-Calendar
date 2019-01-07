package top.jgao.enums;

/**
 * @author JiangaoXia
 * @date 2019/1/7 16:31
 */
public enum DateTagEnum {

    //日期标记（1：工作日2：周末（不包含节假日）3：补班周末4：法定节假日）
    WORKING_DAY((byte) 1), WEEKEND((byte) 2), WORKING_WEEKEND((byte) 3), HOLIDAY((byte) 4);

    private final byte value;

    DateTagEnum(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }}
