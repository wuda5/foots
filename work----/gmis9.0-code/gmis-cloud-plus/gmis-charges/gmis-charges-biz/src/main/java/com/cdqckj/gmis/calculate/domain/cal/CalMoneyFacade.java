package com.cdqckj.gmis.calculate.domain.cal;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.calculate.domain.util.BigDecimalUtil;
import com.cdqckj.gmis.calculate.vo.PriceSchemeVO;
import com.cdqckj.gmis.calculate.vo.SettlementVO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.service.CustomerAccountService;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/9/27 14:30
 * @remark: 请输入类说明
 */
@Component
@Log4j2
public class CalMoneyFacade {

    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    CalculateService calculateService;

    /**
     * @auth lijianguo
     * @date 2020/9/28 11:35
     * @remark 重构的计算所有的价格
     */
    public void settlementReal(List<ReadMeterData> list) {

        for (ReadMeterData e: list){
            //用户账户
            CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
            //气表档案
            GasMeter gasMeter = gasMeterBizApi.findGasMeterByCode(e.getGasMeterCode()).getData();
            //气表使用情况
            GasMeterInfo gasMeterInfo = gasMeterInfoBizApi.getByGasMeterCode(e.getGasMeterCode()).getData();
            log.info("========================当前抄表数据=======================");
            log.info(JSON.toJSONString(e));
            //获取设备当前的气价方案信息
            UseGasType useGasType = useGasTypeBizApi.get(e.getUseGasTypeId()).getData();
            log.info("========================当前用气类型=======================");
            log.info(JSON.toJSONString(useGasType));
            //根据用气类型id查询价格方案
            PriceScheme price = priceSchemeBizApi.queryRecentRecord(useGasType.getId());
            log.info("========================当前价格方案=======================");
            log.info(JSON.toJSONString(price));
            PriceSchemeVO priceSchemeVO = BeanUtil.toBean(price, PriceSchemeVO.class);

            // 开始计算价格 cGas当前 hGas历史
            BigDecimal cGas = e.getMonthUseGas();
            BigDecimal hGas;
            if(gasMeterInfo.getCycleUseGas() != null){
                hGas = gasMeterInfo.getCycleUseGas();
            }else {
                hGas = BigDecimalUtil.intZero();
            }
            // 按照用户阶梯递增的量
            BigDecimal gasGradientAdd = populationAddMoney(useGasType, gasMeter.getPopulation());
            // 采暖方案
            priceSchemeVO = getPriceSchemeVO(e, useGasType, priceSchemeVO);
            // 生成计算方案
            GasMoneyCal cal = new GasMoneyCal(hGas, cGas, useGasType.getPriceType(), priceSchemeVO);
            // 设置递增的数据
            cal.gasGradientAddForPeople(gasGradientAdd);
            // 计算出结果
            SettlementVO settlementVO = cal.calMoney();
            //更新抄表数据、气表使用情况、新增结算数据
//            GasmeterSettlementDetail gSDetail = calculateService
//
//            .updateCalculate(e,gasMeterInfo,price,useGasType,account,
//                    gasMeter,settlementVO).getData();
            //账户抵扣、欠费记录
//            calculateService.accountWithhold(account,e,settlementVO,gSDetail,gasMeterInfo,gasMeter,useGasType,price);
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/9/28 11:48
     * @remark 获取价格的方案 两种方案PriceSchemeVO 一种是默认的，一种是在在采暖区间的方案
     */
    private PriceSchemeVO getPriceSchemeVO(ReadMeterData e, UseGasType useGasType, PriceSchemeVO priceSchemeVO) {
        if(PriceType.HEATING_PRICE.getCode().equals(useGasType.getPriceType())){
            //获取采暖方案
            PriceScheme priceHeating = priceSchemeBizApi.queryRecentHeatingRecord(e.getUseGasTypeId());
            log.info("====================当前采暖价格方案===================");
            //采暖方案切换时间、抄表数据审核时间
            LocalDate heatingDate = priceHeating.getCycleStartTime();
            LocalDate reviewDate = e.getReviewTime().toLocalDate();
            //判断抄表数据是否处于采暖季
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)) {
                log.info("=================抄表数据处于采暖季，以采暖方案计算===============");
                priceSchemeVO = BeanUtil.toBean(priceHeating, PriceSchemeVO.class);
            }
        }
        return priceSchemeVO;
    }

    /**
     * @auth lijianguo
     * @date 2020/9/27 14:36
     * @remark 人口递增的价格人口递增的时候价格不一样 如果为空就不需要计算
     */
    private BigDecimal populationAddMoney(UseGasType useGasType, Integer useNum){
        //为1标识启用人口递增
        if(useGasType.getPopulationGrowth() == 1){
            //根据气表人口数计算递增气量
            Integer cusBaseNum = useGasType.getPopulationBase();
            if(useNum > cusBaseNum){
                BigDecimal cus = BigDecimal.valueOf(useNum - cusBaseNum);
                //每阶梯地增量
                return useGasType.getPopulationIncrease().multiply(cus);
            }else {
                return null;
            }
        }else {
            return null;
        }
    }
}
