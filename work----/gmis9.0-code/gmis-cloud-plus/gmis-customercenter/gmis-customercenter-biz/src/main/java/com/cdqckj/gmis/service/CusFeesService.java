package com.cdqckj.gmis.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.charges.dto.ChargeLoadDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.entity.dto.CusFeesPayDTO;
import com.cdqckj.gmis.entity.vo.CusFeesPayVO;

import java.math.BigDecimal;
import java.util.Map;

public interface CusFeesService {

    /**
     * 普表缴费
     * @auther hc
     * @return
     * @param cusFeesPayDTO
     * @param gasMeter
     */
    R<CusFeesPayVO> generalCharges(CusFeesPayDTO cusFeesPayDTO, GasMeter gasMeter);

    /**
     * 加载缴费数据
     * @auther
     * @param cusCode
     * @param gascode
     * @return
     */
    R<ChargeLoadDTO> chargeLoad(String cusCode, String gascode);

    /**
     * 订单查询
     * @author hc
     * @param orderNumber 订单号
     * @return
     */
    R<Map<String, String>> orderquery(String orderNumber);

    /**
     * 气量金额换算
     * @param conversionVO
     * @return
     */
    R<BigDecimal> conversion(ConversionVO conversionVO);
}
