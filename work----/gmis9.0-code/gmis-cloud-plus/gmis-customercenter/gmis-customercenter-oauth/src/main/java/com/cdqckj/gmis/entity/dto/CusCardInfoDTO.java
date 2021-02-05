package com.cdqckj.gmis.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CusCardInfoDTO {

    /**
     * 全卡数据
     */
    @NotEmpty
    private String encryptCardInfo;

    /**
     * 卡类型
     */
    private Integer cardType;
}
