package com.cdqckj.gmis.calculate;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.calculate.vo.CalculateVO;
import com.cdqckj.gmis.calculate.vo.SettlementVO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;

import java.util.List;

public interface CalculateIotService extends SuperCenterService {
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
    R<List<ReadMeterDataIot>> settlement(List<ReadMeterDataIot> list,int type);

    /**
     * 物联网表退费
      * @param list
     * @return
     */
    R<List<ReadMeterDataIot>> unSettlementEX(List<ReadMeterDataIot> list);
    /**
     * 账户抵扣
     * @param account
     * @param meterDataVO
     * @param settlementVO
     * @param gasmeterSettlementDetail
     * @return
     */
    R<CustomerAccount> accountWithhold(CustomerAccount account, ReadMeterDataIot meterDataVO, SettlementVO settlementVO,
                                       GasmeterSettlementDetail gasmeterSettlementDetail, GasMeterInfo gasMeterInfo,
                                       GasMeter gasMeter, UseGasType useGasType, PriceScheme priceScheme, GasMeterVersion gasMeterVersion,ReadMeterDataIot lastRecordDate);

    /**
     * 更新抄表数据、气表使用情况、新增结算记录
     * @param meterDataVO 抄表数据
     * @param gasMeterInfo 气表使用情况
     * @param price 价格方案
     * @param useGasType 用气类型
     * @param account 用户账户
     * @param gasMeter 气表档案
     */

    R<GasmeterSettlementDetail> updateCalculate(ReadMeterDataIot meterDataVO, GasMeterInfo gasMeterInfo,
                                                PriceScheme price, UseGasType useGasType, CustomerAccount account,
                                                GasMeter gasMeter, SettlementVO settlementVO,CalculateVO calculateVO);

    R<List<ReadMeterDataIot>> settlementIotEX(List<ReadMeterDataIot> list,int type,String executeDate);

    /**
     * 获取
     * @author hc
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    List<ReadMeterDataIot> findSettleData(String gasMeterCode, String customerCode);
}
