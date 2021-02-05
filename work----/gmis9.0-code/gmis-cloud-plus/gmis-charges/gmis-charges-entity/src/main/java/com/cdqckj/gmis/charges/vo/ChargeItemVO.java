package com.cdqckj.gmis.charges.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hp
 */
@Data
public class ChargeItemVO {
    /**
     * 缴费次数
     */
    Integer count;
    /**
     * 最后缴费时间
     */
    LocalDateTime updateTime;
}
