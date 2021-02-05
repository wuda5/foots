package com.cdqckj.gmis.calculate;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.calculate.vo.CalculateVO;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.calculate.vo.SettlementVO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CalculateService extends SuperCenterService {
    /**
     * 计算阶梯燃气费用
     * @param calculateVO
     * @return
     */
    R<SettlementVO> calculateLadderGasCost(CalculateVO calculateVO);

    /**
     * 计算固定燃气费用
     * @param calculateVO
     * @return
     */
    R<SettlementVO> calculateFixedGasCost(CalculateVO calculateVO);

    /**
     * 结算
     * @param list
     * @return
     */
    R<List<ReadMeterData>> settlement(List<ReadMeterData> list,int type);

    /**
     * 撤销结算
     * @param list
     * @return
     */
    R<List<ReadMeterData>> unSettlement(List<ReadMeterData> list);

    /**
     * @auth lijianguo
     * @date 2020/9/28 11:39
     * @remark 重写的计算方案
     */
    R<List<ReadMeterData>> settlementNew(List<ReadMeterData> list);

    /**
     * 账户抵扣
     * @param account
     * @param meterDataVO
     * @param settlementVO
     * @param gasmeterSettlementDetail
     * @return
     */
    R<CustomerAccount> accountWithhold(CustomerAccount account,ReadMeterData meterDataVO,SettlementVO settlementVO,
                                       GasmeterSettlementDetail gasmeterSettlementDetail,GasMeterInfo gasMeterInfo,
                                       GasMeter gasMeter,UseGasType useGasType,PriceScheme priceScheme);

    /**
     * 计算滞纳金
     * @return
     */
    R<Boolean> calculatePenalty(String gasMeterCode);

    /**
     * 换算接口
     * @param conversionVO 换算VO
     * @return
     */
    R<BigDecimal> conversion(ConversionVO conversionVO);

    /**
     * 更新抄表数据、气表使用情况、新增结算记录
     * @param meterDataVO 抄表数据
     * @param gasMeterInfo 气表使用情况
     * @param price 价格方案
     * @param useGasType 用气类型
     * @param account 用户账户
     * @param gasMeter 气表档案
     */
    R<GasmeterSettlementDetail> updateCalculate(ReadMeterData meterDataVO, GasMeterInfo gasMeterInfo,
                                                       PriceScheme price, UseGasType useGasType, CustomerAccount account,
                                                       GasMeter gasMeter, SettlementVO settlementVO,CalculateVO calculateVO);

    /**
     * 查询气价方案
     * @param meterCode
     * @param activateDate
     * @return
     */
    R<PriceScheme> queryPriceScheme(String meterCode, LocalDate activateDate);

    /**
     * 预调价
     * @param meterCode
     * @return
     */
    R<PriceScheme> queryPriceScheme(String meterCode);

    R<Boolean> calculatePenaltyEX(String gasMeterCode,String executeDate);
}
