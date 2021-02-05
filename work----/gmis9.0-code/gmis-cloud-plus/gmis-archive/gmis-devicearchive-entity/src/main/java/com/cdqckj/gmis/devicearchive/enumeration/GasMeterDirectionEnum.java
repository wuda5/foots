package com.cdqckj.gmis.devicearchive.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "GasMeterDirectionEnum", description = "通气方向")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum GasMeterDirectionEnum implements BaseEnum {

    LEFT("左进"),
    RIGHT("右进");


    //    @JsonValue
    @ApiModelProperty(value = "描述")
    private String desc;


    public static GasMeterDirectionEnum match(String val, GasMeterDirectionEnum def) {
        for (GasMeterDirectionEnum enm : GasMeterDirectionEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static GasMeterDirectionEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(GasMeterDirectionEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "LEFT,RIGHT", example = "LEFT")
    public String getCode() {
        return this.name();
    }
}
