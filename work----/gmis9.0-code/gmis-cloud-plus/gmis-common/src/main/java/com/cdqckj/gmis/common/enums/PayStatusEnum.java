package com.cdqckj.gmis.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * @author ASUS
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PayStatusEnum", description = "缴费流程-枚举")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PayStatusEnum {

    /**
     * 取消
     */
    CANCEL(0, "取消"),
    /**
     * 完成
     */
    SUCCESS(1, "完成"),
    /**
     * 待支付
     */
    WAIT_PAY(2, "待支付"),
    /**
     * 处理中
     */
    PROCESSING(3, "处理中");

    @ApiModelProperty(value = "code")
    private Integer code;
    @ApiModelProperty(value = "描述")
    private String desc;


    /**
     * 描述
     *
     * @return
     */
    public String getDesc() {
        return desc;
    }

    public Integer getCode() {
        return code;
    }
}
