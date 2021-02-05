package com.cdqckj.gmis.readmeter.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "ReadMeterStatusEnum", description = "抄表数据状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ReadMeterStatusEnum {
//    数据状态（-1：未抄表；2-取消；0-已抄）

    /**
     * WAIT_READ="未抄表"
     */
    WAIT_READ("未抄表",-1),
    /**
     *READ
     */
    READ("已抄",0),

    /**
     * TERMINATION="已取消"
     */
    TERMINATION("已取消",2);




    @ApiModelProperty(value = "描述")
    private String desc;
    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private ReadMeterStatusEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }


    public static ReadMeterStatusEnum match(String val, ReadMeterStatusEnum def) {
        for (ReadMeterStatusEnum enm : ReadMeterStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static ReadMeterStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(ReadMeterStatusEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
