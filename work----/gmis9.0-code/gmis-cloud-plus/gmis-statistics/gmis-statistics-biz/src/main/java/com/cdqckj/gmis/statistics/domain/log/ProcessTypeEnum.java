package com.cdqckj.gmis.statistics.domain.log;


/**
 * @author: lijianguo
 * @time: 2020/10/28 11:23
 * @remark: 操作的类型
 */
public enum ProcessTypeEnum {

    PROCESS_INSERT( 1),
    PROCESS_UPDATE( 2);

    private Integer value;

    ProcessTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
