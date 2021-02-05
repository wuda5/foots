package com.cdqckj.gmis.bizcenter.operation.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.operateparam.dto.PenaltySaveDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.UseGasTypeSaveDTO;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.vo.UseGasTypeVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface GaspriceService extends SuperCenterService {
    /**
     * 新增价格方案
     * @param priceSchemeSaveDTO
     * @return
     */
    R<PriceScheme> saveLadderPriceScheme(PriceSchemeSaveDTO priceSchemeSaveDTO);
    /**
     * 新增固定价格方案信息
     * @param priceSchemeSaveDTO
     * @return
     */
    R<PriceScheme> saveFixedPriceScheme(PriceSchemeSaveDTO priceSchemeSaveDTO);
    /**
     * 新增采暖价格方案信息
     * @param priceSchemeSaveDTO
     * @return
     */
    R<PriceScheme> saveHeatingPriceScheme(List<PriceSchemeSaveDTO> priceSchemeSaveDTO);
    /**
     * 新增滞纳金
     * @param penaltySaveDTO
     * @return
     */
    R<Penalty> savePenalty(PenaltySaveDTO penaltySaveDTO);
    R<Penalty> updatePenalty(PenaltyUpdateDTO penaltyUpdateDTO);
    R<List<PriceScheme>> getAllPriceScheme();

    /**
     * 新增用气类型
      * @param useGasTypeSaveDTO
     * @return
     */
    R<UseGasType> saveUseGasType(@RequestBody UseGasTypeSaveDTO useGasTypeSaveDTO);

    /**
     * 通过表具Code查询气价方案详情
     *
     * @param gasMeterCode 表具code
     * @return 气价方案详情
     */
    R<UseGasTypeVO> queryByGasMeterCode(String gasMeterCode);
}
