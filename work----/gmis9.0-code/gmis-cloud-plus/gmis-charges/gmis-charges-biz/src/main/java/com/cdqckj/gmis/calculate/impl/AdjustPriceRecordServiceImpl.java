package com.cdqckj.gmis.calculate.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.biztemporary.constants.TemporyMessageConstants;
import com.cdqckj.gmis.calculate.AdjustPriceRecordService;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.calculate.component.CalculateConditionComponent;
import com.cdqckj.gmis.calculate.vo.CalculateVO;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.calculate.vo.SettlementVO;
import com.cdqckj.gmis.charges.dao.AdjustPriceRecordMapper;
import com.cdqckj.gmis.charges.entity.AdjustPrice;
import com.cdqckj.gmis.charges.entity.AdjustPriceRecord;
import com.cdqckj.gmis.charges.enums.AdjustPriceSourceEnum;
import com.cdqckj.gmis.charges.enums.AdjustPriceStateEnum;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.ConversionType;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>
 * 业务实现类
 * 调价补差记录
 * </p>
 *
 * @author songyz
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AdjustPriceRecordServiceImpl extends SuperServiceImpl<AdjustPriceRecordMapper, AdjustPriceRecord> implements AdjustPriceRecordService {

    @Autowired
    private CalculateService calculateService;
    @Autowired
    private PriceSchemeBizApi priceSchemeBizApi;
    @Autowired
    private UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private CalculateConditionComponent calculateConditionComponent;
    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    private GasMeterInfoService gasMeterInfoService;
    @Autowired
    I18nUtil i18nUtil;
    // 这是每批处理的大小
    private final static int BATCH_SIZE = 1000;
    private int size;

    @Override
    public void generateIotReadMeterData(AdjustPrice adjustPrice) {
        List<AdjustPriceRecord> adjustPriceRecords = new ArrayList<>();
        baseMapper.getIotReadMeterData(adjustPrice, resultContext->{
            AdjustPriceRecord resultObject = resultContext.getResultObject();
            adjustPriceRecords.add(resultObject);
            size++;
            if (size == BATCH_SIZE) {
                handle(adjustPriceRecords, true);
            }
        });
        handle(adjustPriceRecords, true);
    }

    @Override
    public void generateReadMeterData(AdjustPrice adjustPrice){
        List<AdjustPriceRecord> adjustPriceRecords = new ArrayList<>();
        baseMapper.getReadMeterData(adjustPrice, resultContext->{
            AdjustPriceRecord resultObject = resultContext.getResultObject();
            adjustPriceRecords.add(resultObject);
            size++;
            if (size == BATCH_SIZE) {
                handle(adjustPriceRecords, false);
            }
        });
        handle(adjustPriceRecords, false);
    }
    /**
     * 数据处理
     */
    private void handle(List<AdjustPriceRecord> adjustPriceRecords, boolean isIotReadMeter){
        try{
            saveReadMeterData(adjustPriceRecords, isIotReadMeter);
//            try {
//            log.info("租户编码"+BaseContextHandler.getTenant());
//            asynTask(adjustPriceRecords);
//        } catch (InterruptedException e) {
//            log.error("注册执行异常：", e);
//        }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 处理完每批数据后后将临时清空
            size = 0;
            adjustPriceRecords.clear();
        }
    }

    @Override
    public void generateRechargeData(AdjustPrice adjustPrice) {
        List<AdjustPriceRecord> adjustPriceRecords = new ArrayList<>();
         baseMapper.getRechargeData(adjustPrice, resultContext->{
             AdjustPriceRecord resultObject = resultContext.getResultObject();
             adjustPriceRecords.add(resultObject);
             size++;
             if (size == BATCH_SIZE) {
                 handleRechargeData(adjustPriceRecords);
             }
        });
        handleRechargeData(adjustPriceRecords);

    }

    @Override
    public int countIotReadMeterData(AdjustPrice adjustPrice) {
        return baseMapper.countIotReadMeterData(adjustPrice);
    }

    /**
     * 数据处理
     */
    private void handleRechargeData(List<AdjustPriceRecord> adjustPriceRecords){
        try{
            //抄表调价补差数据登记记录表
            saveRechargeData(adjustPriceRecords);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // 处理完每批数据后后将临时清空
            size = 0;
            adjustPriceRecords.clear();
        }
    }

    @Override
    public int countReadMeterData(AdjustPrice adjustPrice) {
        return baseMapper.countReadMeterData(adjustPrice);
    }

    @Override
    public int countRechargeData(AdjustPrice adjustPrice) {
        return baseMapper.countRechargeData(adjustPrice);
    }

    @Override
    public Page<AdjustPriceRecord> pageAdjustPrice(Integer pageNo, Integer pageSize, AdjustPrice params) {
        Page<AdjustPriceRecord> page = new Page<>(pageNo,pageSize);
        LbqWrapper<AdjustPriceRecord> wrapper = Wraps.lbQ();

        if(StringUtils.isNotBlank(params.getCustomerName())) {
            wrapper.like(AdjustPriceRecord::getCustomerName, params.getCustomerName());
        }
        if(StringUtils.isNotBlank(params.getGasCode())) {
            wrapper.eq(AdjustPriceRecord::getGasmeterCode, params.getGasCode());
        }
        if(StringUtils.isNotBlank(params.getCustomerChargeNo())) {
            wrapper.eq(AdjustPriceRecord::getCustomerChargeNo, params.getCustomerChargeNo());
        }
        if(StringUtils.isNotBlank(params.getGasMeterNumber())) {
            wrapper.eq(AdjustPriceRecord::getBodyNumber, params.getGasMeterNumber());
        }
        if(StringUtils.isNotBlank(String.valueOf(params.getCompensationState()))) {
            wrapper.eq(AdjustPriceRecord::getDataStatus, params.getCompensationState());
        }
        if(ObjectUtil.isNotNull(params.getUseGasTypeIds()) && params.getUseGasTypeIds().size() > 0) {
            wrapper.in(AdjustPriceRecord::getUseGasTypeId, params.getUseGasTypeIds());
        }
        if(params.getEndTime() != null ) {
            wrapper.le(AdjustPriceRecord::getCreateTime, params.getEndTime());
        }
        if(params.getStartTime() != null ) {
            wrapper.ge(AdjustPriceRecord::getCreateTime, params.getStartTime());
        }
         return baseMapper.selectPage(page,wrapper);
    }

    private ExecutorService executor = Executors.newFixedThreadPool(5);//固定线程池，可根据实际情况选择线程池
    public void asynTask(List<AdjustPriceRecord> resultObject, boolean isIotReadMeter) throws InterruptedException {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                String tenant = BaseContextHandler.getTenant();
                DynamicDataSourceContextHolder.push(tenant);
                //抄表调价补差数据登记记录表
                saveReadMeterData(resultObject, isIotReadMeter);
            }
        });

    }
    /**
     * 抄表调价补差数据登记记录表
     * @param resultObject
     */
    private void saveReadMeterData(List<AdjustPriceRecord> resultObject, boolean isIotReadMeter) {
        String settlementType;
        BigDecimal compensationMoney = BigDecimal.ZERO;
        BigDecimal freeAmount = BigDecimal.ZERO;
        BigDecimal useGasMoney = BigDecimal.ZERO;
        BigDecimal compensationGas = BigDecimal.ZERO;
        R<BigDecimal> calMoneyR;
        new ConversionVO();
        List<AdjustPriceRecord> adjustPriceRecords = new ArrayList<>();
        for(AdjustPriceRecord adjPrice : resultObject){
            //结算类型
            settlementType = adjPrice.getSettlementType();
            //当前价格
            BigDecimal currentPrice = null;
            //有效的气价方案
            PriceScheme priceScheme;
            R<SettlementVO> settlementVOR = null;
            try {
                R<UseGasType> useGasTypeR = useGasTypeBizApi.get(adjPrice.getUseGasTypeId());
                if(useGasTypeR.getIsError() || ObjectUtil.isNull(useGasTypeR.getData())){
                    throw new BizException("查询用气类型异常或用气类型不存在");
                }
                UseGasType useGasType = useGasTypeR.getData();
                priceScheme = getEffectivePriceScheme(useGasType, adjPrice.getGasmeterCode());
                if(useGasType.getPriceType().equals(PriceType.HEATING_PRICE.getCode()) ||
                        useGasType.getPriceType().equals(PriceType.LADDER_PRICE.getCode())){
                    //气表档案
                    List<Integer> gasMeterStatusList = new ArrayList<>();
                    gasMeterStatusList.add(GasMeterStatusEnum.Dismantle.getCode());
                    gasMeterStatusList.add(GasMeterStatusEnum.UNVALID.getCode());
                    GasMeter gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode, adjPrice.getGasmeterCode())
                                .notIn(GasMeter::getDataStatus, gasMeterStatusList));
                    if(null == gasMeter){
                        throw new BizException("客户:"+adjPrice.getCustomerName()+"未找到表具");
                    }
                    //气表使用情况
                    GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(adjPrice.getGasmeterCode(),adjPrice.getCustomerCode());
                    CalculateVO calculateVO = null;
                    if(!isIotReadMeter){
                        //普表抄表
                        ReadMeterData meterDataVO = ReadMeterData
                                .builder()
                                .monthUseGas(adjPrice.getCompensationGas())
                                .recordTime(adjPrice.getRecordTime().toLocalDate())
                                .gasMeterCode(adjPrice.getGasmeterCode())
                                .customerCode(adjPrice.getCustomerCode())
                                .build();
                        calculateVO = calculateConditionComponent.calculateCondition(priceScheme, gasMeter, gasMeterInfo, meterDataVO, useGasType, true);
                    }else{
                        //物联网抄表
                        ReadMeterDataIot meterDataVO = ReadMeterDataIot
                                .builder()
                                .monthUseGas(adjPrice.getCompensationGas())
                                .recordTime(Date.from(adjPrice.getRecordTime().atZone(ZoneId.systemDefault()).toInstant()))
                                .gasMeterCode(adjPrice.getGasmeterCode())
                                .customerCode(adjPrice.getCustomerCode())
                                .build();
                        calculateVO = calculateConditionComponent.calculateCondition(priceScheme, gasMeter, gasMeterInfo, meterDataVO, useGasType, true);
                    }
                    settlementVOR = calculateService.calculateLadderGasCost(calculateVO);
                }else if(useGasType.getPriceType().equals(PriceType.FIXED_PRICE.getCode())){
                    //固定价格
                    CalculateVO calculateVO = CalculateVO
                            .builder()
                            .priceScheme(priceScheme)
                            .currentGas(adjPrice.getCompensationGas())
                            .isClear(true)
                            .build();
                    settlementVOR = calculateService.calculateFixedGasCost(calculateVO);
                }
            } catch (Exception e) {
                log.error("查询补差价格异常，{}", e.getMessage(), e);
                throw new BizException("查询补差价格异常");
            } finally {
            }

            if(ObjectUtil.isNotNull(settlementVOR.getData())
                    && ObjectUtil.isNotNull(settlementVOR.getData().getMoney())) {
                useGasMoney = settlementVOR.getData().getMoney();
            }
            //气量表
            if(AmountMarkEnum.GAS.getCode().equals(settlementType)){
                freeAmount = adjPrice.getFreeAmount();
                if(ObjectUtil.isNull(freeAmount)){
                    freeAmount = BigDecimal.ZERO;
                }
                compensationMoney = useGasMoney.subtract(freeAmount).setScale(4, BigDecimal.ROUND_HALF_UP);
                adjPrice.setCompensationMoney(compensationMoney);
                adjPrice.setSource(AdjustPriceSourceEnum.READ_METER.getCode());
                adjPrice.setDataStatus(AdjustPriceStateEnum.WAIT_CACULATION.getCode());
                adjPrice.setCompensationPrice(settlementVOR.getData().getCompensationPrice());
            }
            //金额表
            else if(AmountMarkEnum.MONEY.getCode().equals(settlementType)){
                ConversionVO conversionVO = ConversionVO
                        .builder()
                        .conversionType(ConversionType.MONEY)
                        .gasMeterCode(adjPrice.getGasmeterCode())
                        .useGasTypeId(adjPrice.getUseGasTypeId())
                        .conversionValue(adjPrice.getFreeAmount())
                        .build();
                calMoneyR = calculateService.conversion(conversionVO);
                if (calMoneyR.getIsError()) {
                    log.error(i18nUtil.getMessage(TemporyMessageConstants.ABNORMAL_AMOUNT_CONVERSION_GAS_VOLUME));
                    throw new BizException(i18nUtil.getMessage(TemporyMessageConstants.ABNORMAL_AMOUNT_CONVERSION_GAS_VOLUME));
                }
                compensationGas = calMoneyR.getData();
                if(ObjectUtil.isNull(compensationGas)){
                    compensationGas = BigDecimal.ZERO;
                }
                freeAmount = adjPrice.getFreeAmount();
                if(ObjectUtil.isNull(freeAmount)){
                    freeAmount = BigDecimal.ZERO;
                }
                compensationMoney = useGasMoney.multiply(freeAmount).setScale(4, BigDecimal.ROUND_HALF_UP);
                adjPrice.setCompensationMoney(compensationMoney);
                adjPrice.setCompensationGas(compensationGas);
                adjPrice.setSource(AdjustPriceSourceEnum.READ_METER.getCode());
                adjPrice.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_CACULATION.getCode()));
                adjPrice.setCompensationPrice(settlementVOR.getData().getCompensationPrice());
            }
            adjustPriceRecords.add(adjPrice);
        }
        //抄表调价补差数据登记记录表
        super.saveBatch(adjustPriceRecords, 1000);
    }

    /**
     * 查询气价方案
     * @param useGasType
     * @param gasMeterCode
     */
    private PriceScheme getEffectivePriceScheme(UseGasType useGasType, final String gasMeterCode) {
        PriceScheme priceScheme = null;
        long useGasTypeId = useGasType.getId();
        if(useGasType.getPriceType().equals(PriceType.HEATING_PRICE.getCode())){
            PriceScheme priceSchemeHeating = priceSchemeBizApi.queryPriceHeatingByTime(useGasTypeId, LocalDate.now());
            LocalDate heatingDate = priceSchemeHeating.getCycleStartTime();
            LocalDate reviewDate = LocalDate.now();
            //判断抄表数据是否处于采暖季
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                log.info("=================抄表数据处于采暖季，以采暖方案计算===============");
                priceScheme = priceSchemeHeating;
            }else{
                log.info("===============抄表数据处于非采暖季，以非采暖方案计算===============");
                priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
            }
        }else {
            priceScheme = priceSchemeBizApi.queryPriceByTime(useGasTypeId, LocalDate.now());
        }
        //
        R<GasMeter> gasMeterR = gasMeterBizApi.findGasMeterByCode(gasMeterCode);
        if(gasMeterR.getIsError() || ObjectUtil.isNull(gasMeterR.getData())){
            throw new BizException("查询表具异常或者无该表具信息");
        }
        //启用人口递增后的价格方案
        priceScheme = isPopulationAddPriceNode(priceScheme,gasMeterR.getData().getPopulation(),useGasType);
        return priceScheme;
    }

    private PriceScheme isPopulationAddPriceNode(PriceScheme price,Integer population,UseGasType useGasType){
        //为1标识启用人口递增
        if(useGasType.getPopulationGrowth()==1){
            //根据气表人口数计算递增气量
            Integer cusBaseNum = useGasType.getPopulationBase();
            Integer cusNum = population;
            if(cusNum>cusBaseNum){
                BigDecimal cus = BigDecimal.valueOf(cusNum-cusBaseNum);
                //每阶梯地增量
                BigDecimal addGas = useGasType.getPopulationIncrease().multiply(cus);
                //设置每个阶梯的地增量
                price.setGas1(price.getGas1().add(addGas));
                if(price.getGas2()!=null){price.setGas2(price.getGas2().add(addGas.multiply(new BigDecimal(2))));}
                if(price.getGas3()!=null){price.setGas3(price.getGas3().add(addGas.multiply(new BigDecimal(3))));}
                if(price.getGas4()!=null){price.setGas4(price.getGas4().add(addGas.multiply(new BigDecimal(4))));}
                if(price.getGas5()!=null){price.setGas5(price.getGas5().add(addGas.multiply(new BigDecimal(5))));}
                if(price.getGas6()!=null){price.setGas6(price.getGas6().add(addGas.multiply(new BigDecimal(6))));}
                log.info("**********************启用人口递增后的价格方案**********************");
                log.info(JSON.toJSONString(price));
            }
        }
        return price;
    }

    /**
     * IC卡表(预付费IC_RECHARGE)、物联网预付费表(表端计费REMOTE_RECHARGE、不会抄表结算)
     * 充值调价补差数据登记记录表
     * @param resultObject
     */
    private void saveRechargeData(List<AdjustPriceRecord> resultObject) {
        BigDecimal compensationGas;
        BigDecimal compensationMoney;
        List<AdjustPriceRecord> adjustPriceRecords = new ArrayList<>();
        for(AdjustPriceRecord adjPrice : resultObject) {
            //气量表（充值记录表存充值气量）、金额表(充值记录表中存充值金额、充值气量)
            BigDecimal oldRechargeMoney = adjPrice.getFreeAmount();
            BigDecimal newRechargeMoney = BigDecimal.ZERO;
            try {
                ConversionVO conversionVO = ConversionVO
                        .builder()
                        .conversionType(ConversionType.GAS)
                        .gasMeterCode(adjPrice.getGasmeterCode())
                        .useGasTypeId(adjPrice.getUseGasTypeId())
                        .conversionValue(adjPrice.getCompensationGas())
                        .build();
                R<BigDecimal> calMoneyR = calculateService.conversion(conversionVO);
                if(calMoneyR.getIsError()){
                    throw new BizException("气量换算金额异常");
                }
                if(ObjectUtil.isNotNull(calMoneyR.getData())){
                    newRechargeMoney = calMoneyR.getData();
                }
            } catch (Exception e) {
                log.error("查询补差价格异常，{}", e.getMessage(), e);
                throw new BizException("查询补差价格异常");
            } finally {
            }
            compensationMoney = newRechargeMoney.subtract(oldRechargeMoney).setScale(4, BigDecimal.ROUND_HALF_UP);
            adjPrice.setCompensationMoney(compensationMoney);
            adjPrice.setSource(AdjustPriceSourceEnum.RECHARGE.getCode());
            adjPrice.setDataStatus(String.valueOf(AdjustPriceStateEnum.WAIT_CACULATION.getCode()));
            adjustPriceRecords.add(adjPrice);
        }
        //抄表调价补差数据登记记录表
        saveBatchSomeColumn(adjustPriceRecords);
    }



}
