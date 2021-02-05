package com.cdqckj.gmis.bizcenter.interest.concerns.vo;

import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.biztemporary.vo.RemoveMeterRecordVO;
import com.cdqckj.gmis.card.entity.CardRepGasRecord;
import com.cdqckj.gmis.card.vo.CardRepRecordVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 变更记录
 *
 * @author hp
 */
@Data
@ApiModel(value = "ChangeRecordVO", description = "变更记录")
public class ChangeRecordVO {

    /**
     * 补换卡记录列表
     */
    @ApiModelProperty(value = "补换卡记录列表")
    List<CardRepRecordVO> cardRepList;

    /**
     * 卡补气记录列表
     */
    @ApiModelProperty(value = "补换卡记录列表")
    List<CardRepGasRecord> cardRepGasList;

    /**
     * 换表记录
     */
    @ApiModelProperty(value = "换表记录列表")
    List<ChangeMeterRecordVO> changeMeterList;

    /**
     * 拆表记录
     */
    @ApiModelProperty(value = "拆表记录列表")
    List<RemoveMeterRecordVO> removeMeterList;

    /**
     * 拆表记录
     */
    @ApiModelProperty(value = "过户记录列表")
    List<GasMeterTransferAccountVO> transferAccountList;

    /**
     * 用气类型变更记录
     */
    @ApiModelProperty(value = "用气类型变更记录列表")
    List<GasTypeChangeRecord> gasTypeChangeList;

}
