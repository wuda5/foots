package com.cdqckj.gmis.iot.qc.enumeration;

import com.cdqckj.gmis.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@ApiModel(value = "NoticeTypeEnum", description = "通知類型-枚举")
@AllArgsConstructor
@NoArgsConstructor
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum NoticeTypeEnum implements BaseEnum {
    /**
     * 全部訂閱
     */
    AllNotice("全部訂閱"),
    /**
     * 設備訂閱
     */
    DeviceNotice("設備訂閱"),
    /**
     * 業務執行狀態數據
     */
    BusinessNotice("業務執行狀態數據");
    @ApiModelProperty(value = "描述")
    private String desc;


    public static NoticeTypeEnum match(String val, NoticeTypeEnum def) {
        for (NoticeTypeEnum enm : NoticeTypeEnum.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static NoticeTypeEnum get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(NoticeTypeEnum val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }

    @Override
    @ApiModelProperty(value = "编码", allowableValues = "AllNotice,DeviceNotice,BusinessNotice", example = "AllNotice")
    public String getCode() {
        return this.name();
    }
}
