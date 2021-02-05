package com.cdqckj.gmis.devicearchive.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel(value = "GasMeterTypeEnum", description = "黑名单状态")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum  GasMeterStatusEnum {

    Pre_doc("预建档",0),
    Uninstalled("待安装",1),
    Unopen("已安装",2),
    UnVentilation("待通气",3),
    Ventilated("已通气",4),
    Dismantle("拆除",5),
    UNVALID("无效",6);


    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "对应的代码")
    private Integer code;

    private GasMeterStatusEnum(String desc, Integer code){
        this.desc=desc;
        this.code=code;
    }
    public static GasMeterStatusEnum match(String val, GasMeterStatusEnum def) {
        for (GasMeterStatusEnum enm : GasMeterStatusEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static GasMeterStatusEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return name().equalsIgnoreCase(val);
    }

    public boolean eq(GasMeterStatusEnum val) {
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
