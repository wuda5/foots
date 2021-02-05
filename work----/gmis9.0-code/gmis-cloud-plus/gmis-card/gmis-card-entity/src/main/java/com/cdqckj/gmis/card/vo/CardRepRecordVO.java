package com.cdqckj.gmis.card.vo;

import com.cdqckj.gmis.card.entity.CardRepRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 关注点展示信息对象-补换卡记录
 *
 * @author hp
 */
@Data
public class CardRepRecordVO extends CardRepRecord {

    /**
     * 收费金额
     */
    @ApiModelProperty("收费金额")
    private BigDecimal totalMoney;

    /**
     * 表身号
     */
    @ApiModelProperty("表身号")
    private String gasMeterNumber;
}
