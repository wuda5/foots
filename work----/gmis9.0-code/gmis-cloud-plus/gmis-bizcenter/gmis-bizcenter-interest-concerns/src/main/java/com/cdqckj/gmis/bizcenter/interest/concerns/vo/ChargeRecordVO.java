package com.cdqckj.gmis.bizcenter.interest.concerns.vo;

import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * 缴费记录页面展示对象
 *
 * @author hp
 */
@Data
public class ChargeRecordVO extends ChargeRecord {

    /**
     * 缴费项明细
     */
    @ApiModelProperty("缴费项明细")
    List<ChargeItemRecord> chargeItemList;

}
