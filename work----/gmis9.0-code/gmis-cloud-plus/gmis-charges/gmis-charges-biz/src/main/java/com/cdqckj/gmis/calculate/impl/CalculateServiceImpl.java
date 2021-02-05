package com.cdqckj.gmis.calculate.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.biztemporary.GasTypeChangeRecordBizApi;
import com.cdqckj.gmis.biztemporary.service.GasTypeChangeRecordService;
import com.cdqckj.gmis.biztemporary.vo.PriceChangeVO;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.calculate.domain.cal.CalMoneyFacade;
import com.cdqckj.gmis.calculate.vo.*;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.AccountSerialSceneEnum;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizSCode;
import com.cdqckj.gmis.common.enums.ConversionType;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.lot.BusinessBizApi;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.service.PenaltyService;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.operateparam.util.CalRoundUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.DataTypeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotService;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.cdqckj.gmis.charges.enums.ChargeStatusEnum.UNCHARGE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Log4j2
@Service
@DS("#thread.tenant")
public class CalculateServiceImpl extends SuperCenterServiceImpl implements CalculateService {

    @Autowired
    private UseGasTypeService useGasTypeService;
    @Autowired
    private PriceSchemeService priceSchemeService;
    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    private ReadMeterDataApi readMeterBizApi;
    @Autowired
    private ReadMeterDataService readMeterDataService;
    @Autowired
    private ReadMeterDataIotService readMeterDataIotService;
    @Autowired
    private GasmeterArrearsDetailService gasmeterArrearsDetailService;
    @Autowired
    private GasmeterSettlementDetailService gasmeterSettlementDetailService;
    @Autowired
    private CustomerAccountSerialService customerAccountSerialService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private PenaltyService penaltyService;
    @Autowired
    private I18nUtil i18nUtil;
    @Autowired
    private CalMoneyFacade calMoneyFacade;
    @Autowired
    private GasMeterVersionService gasMeterVersionService;
    private final Integer num = 2;
    @Autowired
    private BusinessBizApi bussinessBizApi;
    @Autowired
    private MeterFactoryCacheConfig meterFactoryCacheConfig;
    @Autowired
    private GasTypeChangeRecordService gasTypeChangeRecordService;
    @Autowired
    private GasMeterInfoSerialService gasMeterInfoSerialService;
    @Autowired
    private GasMeterInfoService gasMeterInfoService;
    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;
    @Autowired
    private RechargeRecordService rechargeRecordService;

    /**
     * 阶梯方案计费
     * @param calculateVO 阶梯方案
     * @return
     */
    @Override
    public R<SettlementVO> calculateLadderGasCost(CalculateVO calculateVO) {
        PriceScheme priceScheme = calculateVO.getPriceScheme();
        if(priceScheme==null){return R.fail(i18nUtil.getMessage("use.gas.type.not.empty"));}
        //本次结算历史用气量、当前用气量、历史加当前气量、燃气费
        BigDecimal hGas = calculateVO.getHistoryGas();
        BigDecimal cGas = calculateVO.getCurrentGas();
        BigDecimal hcGas = hGas.add(cGas);
        SettlementVO settlementVO = new SettlementVO();
        settlementVO.setMoney(new BigDecimal("0.0000"));
        BigDecimal maxParams = new BigDecimal(65535);
        //判断历史气量在哪个阶梯
        if(hGas.compareTo(priceScheme.getGas1())<=0) {
            //历史气量在一阶
            calculateLadderOneMoney(priceScheme,hcGas,hGas,settlementVO,cGas);
        }else if(priceScheme.getGas2()!=null&&(hGas.compareTo(priceScheme.getGas2())<=0
                ||(priceScheme.getGas2().compareTo(maxParams)>=0&&priceScheme.getGas3()==null))){
            calculateLadderTwoMoney(priceScheme,hcGas,hGas,settlementVO,cGas);
        }else if(priceScheme.getGas3()!=null&&(hGas.compareTo(priceScheme.getGas3())<=0
                ||(priceScheme.getGas3().compareTo(maxParams)>=0&&priceScheme.getGas4()==null))){
            calculateLadderThreeMoney(priceScheme,hcGas,hGas,settlementVO,cGas);
        }else if(priceScheme.getGas4()!=null&&(hGas.compareTo(priceScheme.getGas4())<=0
                ||(priceScheme.getGas4().compareTo(maxParams)>=0&&priceScheme.getGas5()==null))){
            calculateLadderFourMoney(priceScheme,hcGas,hGas,settlementVO,cGas);
        }else if(priceScheme.getGas5()!=null&&(hGas.compareTo(priceScheme.getGas5())<=0
                ||(priceScheme.getGas5().compareTo(maxParams)>=0&&priceScheme.getGas6()==null))){
            calculateLadderFiveMoney(priceScheme,hcGas,hGas,settlementVO,cGas);
        }else {
           calculateLadderSixMoney(priceScheme,hcGas,hGas,settlementVO,cGas);
        }
        return R.success(settlementVO);
    }

    /**
     * 固定方案计费
     * @param calculateVO 固定价格方案
     * @return
     */
    @Override
    public R<SettlementVO> calculateFixedGasCost(CalculateVO calculateVO) {
        PriceScheme priceScheme = calculateVO.getPriceScheme();
        if(priceScheme==null){return R.fail(i18nUtil.getMessage("price.scheme.not.empty"));}
        /**当前气量、当前固定价格**/
        BigDecimal cGas = calculateVO.getCurrentGas();
        BigDecimal p = priceScheme.getFixedPrice();
        BigDecimal money = new BigDecimal("0.0000");
        SettlementVO settlementVO = new SettlementVO();
        money = CalRoundUtil.bigDecimalRound(p.multiply(cGas),num);
        settlementVO.setMoney(money);
        settlementVO.setSettlementTime(LocalDate.now());
        return R.success(settlementVO);
    }

    /**
     * 抄表数据计费
     * @param list 审核通过后的抄表数据
     * @return
     */
    @Override
    @CodeNotLost
    public R<List<ReadMeterData>> settlement(List<ReadMeterData> list,int type) {
        //后期配置放缓存
        if(list==null||list.size()==0){
            log.error("抄表数据为空不能进行计费");
            throw new  BizException(i18nUtil.getMessage("read.meter.data.not.empty"));
        }
        /**计费逻辑**/
        list.stream().forEach(e->{
            boolean isCycleClear = true;
            //用户账户
            CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
            if(null == account){throw new  BizException("客户:"+e.getCustomerName()+"未找到用户账户");}
            //if(null == account){return;}
            //气表档案
            GasMeter gasMeter = null;
            if(StringUtil.isNullOrBlank(e.getGasMeterCode())){
                gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode, e.getGasMeterCode())
                        .notIn(GasMeter::getDataStatus,5,6));
            }
            if(gasMeter==null&&!StringUtil.isNullOrBlank(e.getGasMeterNumber())){
                gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasMeterNumber, e.getGasMeterNumber())
                        .notIn(GasMeter::getDataStatus,5,6));
            }
            if(null == gasMeter){throw new  BizException("客户:"+e.getCustomerName()+"未找到表具");}
            //气表使用情况
            GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
            if(null == gasMeterInfo){throw new  BizException("客户:"+e.getCustomerName()+"未找到气表使用情况");}
            log.info("========================当前抄表数据=======================");
            log.info(JSON.toJSONString(e));
            //获取设备当前的气价方案信息
            UseGasType useGasType = useGasTypeService.getById(e.getUseGasTypeId());
            if(null == useGasType){throw new  BizException("客户:"+e.getCustomerName()+"未找到表具");}
            log.info("==========结算前周期累计用气量===========:"+gasMeterInfo.getCycleUseGas());
            //根据用气类型变更记录筛选方案
            PriceChangeVO priceChangeVO = new PriceChangeVO();
            priceChangeVO.setPriceId(gasMeterInfo.getPriceSchemeId());
            priceChangeVO.setGasMeterCode(e.getGasMeterCode());
            priceChangeVO.setRecordTime(e.getRecordTime());
            PriceScheme price = gasTypeChangeRecordService.getPriceSchemeByRecord(priceChangeVO);
            if(price!=null){
                useGasType = useGasTypeService.getById(price.getUseGasTypeId());
            }
            isCycleClear = false;
            if(null == price){
                //根据用气类型id查询价格方案
                price = priceSchemeService.queryPriceByTime(useGasType.getId(),e.getRecordTime());
                isCycleClear = true;
            }
            if(null == price){throw new  BizException("客户:"+e.getCustomerName()+"未找到价格方案");}
            log.info("========================当前价格方案=======================");
            log.info(JSON.toJSONString(price));
            BigDecimal cGas = e.getMonthUseGas();
            //判断用气类型
            if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
                log.info("=================抄表数据以固定方案计算=================");
                //固定价格
                CalculateVO calculateVO = new CalculateVO();
                calculateVO.setPriceScheme(price);
                calculateVO.setCurrentGas(cGas);
                calculateVO.setIsClear(false);
                SettlementVO settlementVO = calculateFixedGasCost(calculateVO).getData();
                //更新抄表数据、气表使用情况、新增结算数据
                GasmeterSettlementDetail gSDetail = updateCalculate(e,gasMeterInfo,price,useGasType,account,
                        gasMeter,settlementVO,calculateVO).getData();
                if(type==1){
                    //不做抵扣直接生成账单
                    createArrears(e,settlementVO,settlementVO.getMoney(),useGasType,price,type);
                    updateReadMeterData(e);
                }else{
                    //账户抵扣、欠费记录
                    accountWithhold(account,e,settlementVO,gSDetail,gasMeterInfo,gasMeter,useGasType,price);
                }
            }else if(PriceType.LADDER_PRICE.getCode().equals(useGasType.getPriceType())){
                log.info("=================抄表数据以阶梯方案计算=================");
                settlementCal(price,gasMeterInfo,gasMeter,price,account,e,useGasType,type,isCycleClear);
            }else if(PriceType.HEATING_PRICE.getCode().equals(useGasType.getPriceType())){
                //获取采暖方案
                PriceScheme priceHeating = priceSchemeService.queryPriceHeatingByTime(useGasType.getId(),e.getRecordTime());
                if(null == priceHeating){throw new  BizException("客户:"+e.getCustomerName()+"未找到采暖价格方案");}
                log.info("====================当前采暖价格方案===================");
                log.info(JSON.toJSONString(price));
                //采暖方案切换时间、抄表数据审核时间
                LocalDate heatingDate = priceHeating.getCycleStartTime();
                LocalDate reviewDate = e.getRecordTime();
                //判断抄表数据是否处于采暖季
                if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                    log.info("=================抄表数据处于采暖季，以采暖方案计算===============");
                    settlementCal(priceHeating,gasMeterInfo,gasMeter,price,account,e,useGasType,type,isCycleClear);
                }else{
                    log.info("===============抄表数据处于非采暖季，以非采暖方案计算===============");
                    settlementCal(price,gasMeterInfo,gasMeter,price,account,e,useGasType,type,isCycleClear);
                }
            }
        });
        return R.success(list);
    }

    /**
     * 抵扣退费
     * @param list
     * @return
     */
    @Override
    public R<List<ReadMeterData>> unSettlement(List<ReadMeterData> list) {
        List<Long> updateReadList = new ArrayList<>();
        List<GasmeterSettlementDetail> detailList = new ArrayList<>();
        List<GasmeterArrearsDetail> arrearsDetails = new ArrayList<>();
        list.stream().forEach(e->{
            //查询抄表是否有欠费并已收费
            LbqWrapper<GasmeterArrearsDetail> gadQuery =new LbqWrapper<>();
            gadQuery.eq(GasmeterArrearsDetail::getArrearsStatus,ChargeStatusEnum.CHARGED.getCode());
            gadQuery.eq(GasmeterArrearsDetail::getDataStatus,DataStatusEnum.NORMAL.getValue());
            if(!StringUtil.isNullOrBlank(e.getGasMeterCode())){
                gadQuery.eq(GasmeterArrearsDetail::getGasmeterCode,e.getGasMeterCode());
            }
            gadQuery.eq(GasmeterArrearsDetail::getReadmeterDataId,e.getId());
            List<GasmeterArrearsDetail> gasmeterArrearsDetails = gasmeterArrearsDetailService.list(gadQuery);
            if(gasmeterArrearsDetails!=null&&gasmeterArrearsDetails.size()>0){throw new BizException("存在欠费已交情况，请在柜台操作");}

            LbqWrapper<GasmeterArrearsDetail> gadQueryEx =new LbqWrapper<>();
            gadQueryEx.eq(GasmeterArrearsDetail::getDataStatus,DataStatusEnum.NORMAL.getValue());
            if(!StringUtil.isNullOrBlank(e.getGasMeterCode())){
                gadQueryEx.eq(GasmeterArrearsDetail::getGasmeterCode,e.getGasMeterCode());
            }
            gadQueryEx.eq(GasmeterArrearsDetail::getReadmeterDataId,e.getId());
            gadQueryEx.eq(GasmeterArrearsDetail::getArrearsStatus,UNCHARGE.getCode());
            List<GasmeterArrearsDetail> gasmeterArrearsDetailUnChange = gasmeterArrearsDetailService.list(gadQueryEx);
            if(gasmeterArrearsDetailUnChange!=null&&gasmeterArrearsDetailUnChange.size()>0){
                GasmeterArrearsDetail gasmeterArrearsDetail = gasmeterArrearsDetailUnChange.get(0);
                gasmeterArrearsDetail.setDataStatus(0);
                arrearsDetails.add(gasmeterArrearsDetail);
            }
            //用户账户
            CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
            //气表使用情况
            GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
            //查询结算明细
            LbqWrapper<GasmeterSettlementDetail> gasmeterSettlementDetail =new LbqWrapper<>();
            gasmeterSettlementDetail.eq(GasmeterSettlementDetail::getReadmeterDataId,e.getId());
            gasmeterSettlementDetail.eq(GasmeterSettlementDetail::getDataStatus,1);
            //gasmeterSettlementDetail.eq(GasmeterSettlementDetail::getReadmeterMonth,e.getReadMeterMonth().toString());
            List<GasmeterSettlementDetail> settlementDetails = gasmeterSettlementDetailService.list(gasmeterSettlementDetail);
            GasmeterSettlementDetail gasmeterSettle = settlementDetails.get(0);
            gasmeterSettle.setDataStatus(0);

            if(gasmeterSettle.getSettlementPreMoney()!=null&&gasmeterSettle.getSettlementAfterMoney()!=null){
                CustomerAccountSerial customerAccountSerial = new CustomerAccountSerial();
                customerAccountSerial.setAccountCode(account.getAccountCode());
                customerAccountSerial.setGasMeterCode(e.getGasMeterCode());
                customerAccountSerial.setCustomerCode(e.getCustomerCode());
                customerAccountSerial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
                customerAccountSerial.setBillType(AccountSerialSceneEnum.DEDUCTION_REFUND.getCode());
                if(gasmeterSettle.getGiveBackAfterMoney()!=null&&gasmeterSettle.getGiveBookPreMoney()!=null){
                    account.setGiveMoney(account.getGiveMoney().add(gasmeterSettle.getGiveBookPreMoney()
                            .subtract(gasmeterSettle.getGiveBackAfterMoney())));
                    customerAccountSerial.setGiveBookPreMoney(gasmeterSettle.getGiveBookPreMoney());
                    customerAccountSerial.setGiveBookAfterMoney(gasmeterSettle.getGiveBackAfterMoney());
                    customerAccountSerial.setGiveBookMoney(gasmeterSettle.getGiveBookPreMoney()
                            .subtract(gasmeterSettle.getGiveBackAfterMoney()));
                }
                if(gasmeterSettle.getSettlementAfterMoney()!=null&&gasmeterSettle.getSettlementPreMoney()!=null){
                    account.setAccountMoney(account.getAccountMoney().add(gasmeterSettle.getSettlementPreMoney()
                            .subtract(gasmeterSettle.getSettlementAfterMoney())));
                    customerAccountSerial.setBookPreMoney(gasmeterSettle.getSettlementAfterMoney());
                    customerAccountSerial.setBookAfterMoney(gasmeterSettle.getSettlementPreMoney());
                    customerAccountSerial.setBookMoney(gasmeterSettle.getSettlementPreMoney()
                            .subtract(gasmeterSettle.getSettlementAfterMoney()));
                }
                //新增账户流水
                customerAccountSerialService.save(customerAccountSerial);
                //账户更新
                customerAccountService.updateById(account);
            }

            //户表余额退费
            if(gasmeterSettle.getSettlementMeterPreMoney()!=null&&gasmeterSettle.getSettlementMeterAfterMoney()!=null){
                gasMeterInfo.setGasMeterBalance(gasMeterInfo.getGasMeterBalance().add(gasmeterSettle.getSettlementMeterPreMoney()
                        .subtract(gasmeterSettle.getSettlementMeterAfterMoney())));
            }
            //户表赠送抵扣退费
            if(gasmeterSettle.getGiveMeterAfterMoney()!=null&&gasmeterSettle.getGiveMeterPreMoney()!=null){
                gasMeterInfo.setGasMeterGive(gasmeterSettle.getGiveMeterPreMoney().subtract(gasmeterSettle.getGiveMeterAfterMoney()));
            }

            e.setSettlementTime(null);
            e.setSettlementUserId(null);
            e.setSettlementUserName(null);
            e.setChargeStatus(null);
            e.setProcessStatus(ProcessEnum.TO_BE_REVIEWED);

            updateReadList.add(e.getId());
            //更新结算明细
            detailList.add(gasmeterSettle);

            //气表使用情况更新
            ReadMeterData readMeterData = readMeterDataService.getPreviousData(e.getGasMeterCode(),e.getCustomerCode(),e.getRecordTime());
            if(readMeterData!=null){
                gasMeterInfo.setCycleUseGas(readMeterData.getCurrentCycleTotalUseGas());
            }else{
                gasMeterInfo.setCycleUseGas(e.getCycleTotalUseGas());
            }
            gasMeterInfo.setTotalUseGas(gasMeterInfo.getTotalUseGas().subtract(e.getMonthUseGas()));
            gasMeterInfo.setTotalUseGasMoney(gasMeterInfo.getTotalUseGasMoney().subtract(gasmeterSettle.getGasMoney()));
            gasMeterInfoService.updateById(gasMeterInfo);
        });
        if(updateReadList.size()>0){
            readMeterBizApi.updateDataRefund(updateReadList);
        }
        if(detailList.size()>0){
            gasmeterSettlementDetailService.updateBatchById(detailList);
        }
        if(arrearsDetails.size()>0){
            gasmeterArrearsDetailService.updateBatchById(arrearsDetails);
        }
        return R.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<List<ReadMeterData>> settlementNew(List<ReadMeterData> list) {
        if (list == null || list.size() == 0) {
            throw new  BizException(i18nUtil.getMessage("read.meter.data.not.empty"));
        }
        calMoneyFacade.settlementReal(list);
        return R.success(list);
    }

    /**
     * 结算逻辑
     * @param priceSchemeVO 计算价格方案
     * @param gasMeterInfo 表具使用情况
     * @param gasMeter 表具信息
     * @param price 价格方案
     * @param account 账户
     * @param e 抄表数据
     * @param useGasType 用气类型
     */
    private void settlementCal(PriceScheme priceSchemeVO,GasMeterInfo gasMeterInfo,
                               GasMeter gasMeter,PriceScheme price,CustomerAccount account,ReadMeterData e,
                               UseGasType useGasType,int type,boolean isCycleClear){
        //确认价格方案
        CalculateVO calculateVO = calculateCondition(priceSchemeVO,gasMeter,gasMeterInfo,e,useGasType,isCycleClear);
        //计算燃气费用
        SettlementVO settlementVO = calculateLadderGasCost(calculateVO).getData();
        //更新抄表数据、气表使用情况、新增结算数据
        GasmeterSettlementDetail gSDetail = updateCalculate(e,gasMeterInfo,price,useGasType,account,
                gasMeter,settlementVO,calculateVO).getData();
        if(type==1){
            //不做抵扣直接生成账单
            createArrears(e,settlementVO,settlementVO.getMoney(),useGasType,price,type);
            updateReadMeterData(e);
        }else{
            //账户抵扣、欠费记录
            accountWithhold(account,e,settlementVO,gSDetail,gasMeterInfo,gasMeter,useGasType,price);
        }
    }

    /**
     * 账户抵扣
     * @param account 账户
     * @param meterDataVO 抄表数据
     * @param settlementVO 结算结果
     * @param gasmeterSettlementDetail 抵扣前结算记录
     * @param gasMeterInfo 气表使用详情
     * @param gasMeter 气表
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeNotLost
    public R<CustomerAccount> accountWithhold(CustomerAccount account,ReadMeterData meterDataVO,SettlementVO settlementVO,
                                         GasmeterSettlementDetail gasmeterSettlementDetail,GasMeterInfo gasMeterInfo,
                                              GasMeter gasMeter,UseGasType useGasType,PriceScheme priceScheme) {
        BigDecimal initParam = new BigDecimal("0.0000");
        CustomerAccount accountPre = new CustomerAccount();
        BeanUtils.copyProperties(account,accountPre);
        //判断欠费金额是否结清，如果结清没必要进行账户抵扣了
        if(settlementVO.getMoney().compareTo(BigDecimal.ZERO)==0){
            meterDataVO.setChargeStatus(ChargeEnum.CHARGED);
            updateReadMeterData(meterDataVO);
            return R.success(account);
        }
        //客户账户抵扣
        if(account.getAccountMoney()==null||(account.getAccountMoney().compareTo(BigDecimal.ZERO)==0
                &&(account.getGiveMoney()==null||account.getGiveMoney().compareTo(BigDecimal.ZERO)==0))){
            //账户未充值直接生成欠费记录
            createArrears(meterDataVO,settlementVO,settlementVO.getMoney(),useGasType,priceScheme,0);
            //更新抄表状态
            meterDataVO.setChargeStatus(ChargeEnum.NO_CHARGE);
            updateReadMeterData(meterDataVO);
           return R.fail("账户未充值,不能进行抵扣!");
        }
        //账户余额小于计费后的金额
        if(account.getAccountMoney().compareTo(settlementVO.getMoney())<0){
            BigDecimal rtMoney = settlementVO.getMoney().subtract(account.getAccountMoney());
            //判断账户是否有赠送金额
            if(account.getGiveMoney()!=null){
                //判断赠送金额是否小于账户抵扣后的欠费金额
                if(account.getGiveMoney().compareTo(rtMoney)<0){
                    BigDecimal acMoney = rtMoney.subtract(account.getGiveMoney());
                    account.setAccountMoney(initParam);
                    account.setGiveMoney(initParam);
                    //更新账户、结算明细、新增账户流水
                    updateSettlementAccount(acMoney,gasmeterSettlementDetail,account,accountPre,gasMeter.getGasCode());
                    //账户未充值直接生成欠费记录
                    createArrears(meterDataVO,settlementVO,acMoney,useGasType,priceScheme,0);
                    //更新抄表状态
                    meterDataVO.setChargeStatus(ChargeEnum.NO_CHARGE);
                    updateReadMeterData(meterDataVO);
                }else{
                    //账户余额不足，赠送足够的情况
                    BigDecimal acMoney = account.getGiveMoney().subtract(rtMoney);
                    account.setAccountMoney(initParam);
                    account.setGiveMoney(acMoney);
                    //更新账户、结算明细、新增账户流水
                    updateSettlementAccount(initParam,gasmeterSettlementDetail,account,accountPre,gasMeter.getGasCode());
                    //更新抄表状态
                    meterDataVO.setChargeStatus(ChargeEnum.CHARGED);
                    //是否物联网表
                    updateReadMeterData(meterDataVO);
                }
            }else{
                //已欠费新增欠费记录
                account.setAccountMoney(initParam);
                //更新账户、结算明细、新增账户流水
                updateSettlementAccount(rtMoney,gasmeterSettlementDetail,account,accountPre,gasMeter.getGasCode());
                //账户未充值直接生成欠费记录
                createArrears(meterDataVO,settlementVO,rtMoney,useGasType,priceScheme,0);
                //更新抄表状态
                meterDataVO.setChargeStatus(ChargeEnum.NO_CHARGE);
                updateReadMeterData(meterDataVO);
            }
        }else{
            //未欠费
            BigDecimal rtMoney = account.getAccountMoney().subtract(settlementVO.getMoney());
            account.setAccountMoney(rtMoney);
            //更新账户、结算明细、新增账户流水
            updateSettlementAccount(initParam,gasmeterSettlementDetail,account,accountPre,gasMeter.getGasCode());
            //更新抄表状态
            meterDataVO.setChargeStatus(ChargeEnum.CHARGED);
            updateReadMeterData(meterDataVO);
        }
        return R.success(account);
    }

    /**
     * 滞纳金计算
     * @return
     */
    @Override
    public R<Boolean> calculatePenalty(String gasMeterCode) {
          return calculatePenaltyData(gasMeterCode,null);
    }

    /**
     * 换算接口
     * @param conversionVO 换算VO
     * @return
     */
    @Override
    public R<BigDecimal> conversion(ConversionVO conversionVO) {
        if(ConversionType.MONEY.eq(conversionVO.getConversionType())){
            return moneyToGas(conversionVO);
        }else if(ConversionType.GAS.eq(conversionVO.getConversionType())){
            return gasToMoney(conversionVO);
        }else{
            return R.fail(i18nUtil.getMessage("conversion.type.params.error"));
        }
    }

    /**
     * 气量换算成金额(不包括历史量的情况)
     * @param conversionVO 换算VO
     * @return
     */
    private R<BigDecimal> gasToMoney(ConversionVO conversionVO) {
        String gasMeterCode = conversionVO.getGasMeterCode();
        GasMeter gasMeter = gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode, conversionVO.getGasMeterCode())
                .notIn(GasMeter::getDataStatus,5,6));
        if(gasMeter==null){return R.fail(i18nUtil.getMessage("not.find.gas.meter"));}
        Long useGasTypeId = conversionVO.getUseGasTypeId();
        if(useGasTypeId==null){
            useGasTypeId = gasMeter.getUseGasTypeId();
        }
        BigDecimal conversionValue = conversionVO.getConversionValue();
        UseGasType useGasType = useGasTypeService.getById(useGasTypeId);
        BigDecimal money = new BigDecimal("0.0000");
        if(useGasType==null){return R.fail(i18nUtil.getMessage("not.find.use.gas.type"));}
        if(useGasType.getDataStatus()!=null&&useGasType.getDataStatus()==0)
        {return R.fail(i18nUtil.getMessage("use.gas.type.disable"));}
        LbqWrapper<CustomerGasMeterRelated> relatedLbqWrapper =new LbqWrapper<>();
        relatedLbqWrapper.eq(CustomerGasMeterRelated::getGasMeterCode,gasMeterCode);
        relatedLbqWrapper.eq(CustomerGasMeterRelated::getDataStatus,DataStatusEnum.NORMAL.getValue());
        CustomerGasMeterRelated customerGasMeterRelated = customerGasMeterRelatedService.getOne(relatedLbqWrapper);
        if(null==customerGasMeterRelated){throw new BizException("未找到客户气表关联关系");}
        //气表使用情况
        GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(gasMeterCode,customerGasMeterRelated.getCustomerCode());
        if(gasMeterInfo==null){return R.fail(i18nUtil.getMessage("not.find.meterinfo"));}
        PriceScheme price = priceSchemeService.queryRecentRecord(useGasTypeId);
        if(price==null){return R.fail(i18nUtil.getMessage("not.find.gas.price.scheme"));}
        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
            //固定价格气量换算成金额
            money = price.getFixedPrice().multiply(conversionValue);
        }else if(PriceType.LADDER_PRICE.getCode().equals(useGasType.getPriceType())){
            //阶梯气量换算成金额
            CalculateVO calculateVO = new CalculateVO();
            //判断是否启用人口递增
            PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
            //确定最终价格方案进行计算
            calculateVO.setPriceScheme(priceScheme);
            //是否调价、周期清零
            calculateVO = isCardChangePriceClear(price,calculateVO,gasMeterInfo);
            calculateVO.setCurrentGas(conversionVO.getConversionValue());
            //计算燃气费用
            SettlementVO settlementVO = calculateLadderGasCost(calculateVO).getData();
            money =  settlementVO.getMoney();
            if(calculateVO.getIsClear()) {
                gasMeterInfo.setCycleUseGas(BigDecimal.ZERO);
            }
        }else{
            //采暖价气量换算成金额
            PriceScheme priceHeating = priceSchemeService.queryRecentHeatingRecord(useGasTypeId);
            if(priceHeating==null){return R.fail(i18nUtil.getMessage("not.find.gas.price.scheme"));}
            //采暖方案切换时间、抄表数据审核时间
            LocalDate heatingDate = price.getCycleStartTime();
            LocalDate reviewDate = LocalDate.now();
            CalculateVO calculateVO = new CalculateVO();
            PriceScheme priceScheme = null;
            //判断抄表数据是否处于采暖季
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                //判断是否启用人口递增
                priceScheme = isPopulationAddPrice(priceHeating,gasMeter,useGasType);
            }else{
                priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
            }
            //确定最终价格方案进行计算
            calculateVO.setPriceScheme(priceScheme);
            //是否调价周期清零
            calculateVO = isCardChangePriceClear(price,calculateVO,gasMeterInfo);
            calculateVO.setCurrentGas(conversionVO.getConversionValue());
            //计算燃气费用
            SettlementVO settlementVO = calculateLadderGasCost(calculateVO).getData();
            money =  settlementVO.getMoney();
            if(calculateVO.getIsClear()) {
                gasMeterInfo.setCycleUseGas(BigDecimal.ZERO);
            }
        }
        gasMeterInfoService.updateById(gasMeterInfo);
        return R.success(CalRoundUtil.bigDecimalRound(money,num));
    }

    /**
     * 金额换算成气量
     * @param conversionVO 换算VO
     * @return
     */
    private R<BigDecimal> moneyToGas(ConversionVO conversionVO) {
        String gasMeterCode = conversionVO.getGasMeterCode();
        GasMeter gasMeter = gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode, gasMeterCode)
                .notIn(GasMeter::getDataStatus,5,6));
        if(gasMeter==null){return R.fail(i18nUtil.getMessage("not.find.gas.meter"));}
        Long useGasTypeId = conversionVO.getUseGasTypeId();
        if(useGasTypeId==null){
            useGasTypeId = gasMeter.getUseGasTypeId();
        }
        BigDecimal conversionValue = conversionVO.getConversionValue();
        UseGasType useGasType = useGasTypeService.getById(useGasTypeId);
        BigDecimal gas = new BigDecimal("0.0000");
        if(useGasType==null){return R.fail(i18nUtil.getMessage("not.find.use.gas.type"));}
        if(useGasType.getDataStatus()!=null&&useGasType.getDataStatus()==0)
        {return R.fail(i18nUtil.getMessage("use.gas.type.disable"));}
        LbqWrapper<CustomerGasMeterRelated> relatedLbqWrapper =new LbqWrapper<>();
        relatedLbqWrapper.eq(CustomerGasMeterRelated::getGasMeterCode,gasMeterCode);
        relatedLbqWrapper.eq(CustomerGasMeterRelated::getDataStatus,DataStatusEnum.NORMAL.getValue());
        CustomerGasMeterRelated customerGasMeterRelated = customerGasMeterRelatedService.getOne(relatedLbqWrapper);
        if(null==customerGasMeterRelated){throw new BizException("未找到客户气表关联关系");}
        //气表使用情况
        GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(gasMeterCode,customerGasMeterRelated.getCustomerCode());
        if(gasMeterInfo==null){return R.fail(i18nUtil.getMessage("not.find.meterinfo"));}
        PriceScheme price = priceSchemeService.queryRecentRecord(useGasTypeId);
        if(price==null){return R.fail(i18nUtil.getMessage("not.find.gas.price.scheme"));}
        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
            //固定价格金额换算成气量
            gas = conversionValue.divide(price.getFixedPrice(),2,BigDecimal.ROUND_HALF_UP);
        }else if(PriceType.LADDER_PRICE.getCode().equals(useGasType.getPriceType())){
            CalculateVO calculateVO = new CalculateVO();
            //判断是否多人口递增
            PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
            calculateVO = isCardChangePriceClear(price,calculateVO,gasMeterInfo);
            if(calculateVO.getIsClear()) {
                gasMeterInfo.setCycleUseGas(BigDecimal.ZERO);
            }
            gas = moneyLadderToGas(priceScheme,conversionValue,calculateVO.getHistoryGas());
        }else{
            //采暖价金额换算成气量
            CalculateVO calculateVO = new CalculateVO();
            PriceScheme priceHeat = priceSchemeService.queryRecentHeatingRecord(useGasTypeId);
            if(priceHeat==null){return R.fail(i18nUtil.getMessage("not.find.gas.price.scheme"));}
            //采暖方案切换时间、抄表数据审核时间
            LocalDate heatingDate = price.getCycleStartTime();
            LocalDate reviewDate = LocalDate.now();
            PriceScheme priceScheme = null;
            //判断是否多人口递增
            if(reviewDate.isAfter(heatingDate)||reviewDate.isEqual(heatingDate)){
                priceScheme = isPopulationAddPrice(priceHeat,gasMeter,useGasType);
            }else{
                priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
            }
            calculateVO = isCardChangePriceClear(price,calculateVO,gasMeterInfo);
            if(calculateVO.getIsClear()) {
                gasMeterInfo.setCycleUseGas(BigDecimal.ZERO);
            }
            gas =  moneyLadderToGas(priceScheme,conversionValue,calculateVO.getHistoryGas());
        }
        gasMeterInfoService.updateById(gasMeterInfo);
        return R.success(gas);
    }
    /**
     * 阶梯金额换算成气量
     * @param price 价格方案
     * @param money 需要换算的金额
     * @return
     */
    private BigDecimal moneyLadderToGas(PriceScheme price,BigDecimal money,BigDecimal cycleGas){
        //价格方案信息
        BigDecimal initParams = new BigDecimal("0.0000");
        BigDecimal maxParams = new BigDecimal(65535);
        BigDecimal g1 = price.getGas1();BigDecimal p1 = price.getPrice1();
        BigDecimal p2 = initParams;BigDecimal g2 = initParams;
        if(price.getGas2()!=null){g2 = price.getGas2();p2 = price.getPrice2();}
        BigDecimal g3 = initParams;BigDecimal p3 = initParams;
        if(price.getGas3()!=null){g3 = price.getGas3();p3 = price.getPrice3();}
        BigDecimal g4 = initParams;BigDecimal p4 = initParams;
        if(price.getGas4()!=null){g4 = price.getGas4();p4 = price.getPrice4();}
        BigDecimal g5 = initParams;BigDecimal p5 = initParams;
        if(price.getGas5()!=null){g5 = price.getGas5();p5 = price.getPrice5();}
        BigDecimal g6 = initParams;BigDecimal p6 = initParams;
        if(price.getGas6()!=null){g6 = price.getGas6();p6 = price.getPrice6();}

        BigDecimal m1 = p1.multiply(g1);BigDecimal m2 = p2.multiply((g2.subtract(g1)));
        BigDecimal m3 = p3.multiply((g3.subtract(g2)));BigDecimal m4 = p4.multiply((g4.subtract(g3)));
        BigDecimal m5 = p5.multiply((g5.subtract(g4)));BigDecimal m6 = p6.multiply((g6.subtract(g5)));
        BigDecimal gas = initParams;

        //计算历史金额
        BigDecimal hGas = cycleGas;

        BigDecimal hMoney = initParams;
        if(hGas.compareTo(g1)<=0||(g1.compareTo(maxParams)>=0&&g2.compareTo(initParams)==0)){
            hMoney = p1.multiply(hGas);
        }else if(hGas.compareTo(g2)<=0||(g2.compareTo(maxParams)>=0&&g3.compareTo(initParams)==0)){
            hMoney = p1.multiply(g1).add(p2.multiply((hGas.subtract(g1))));
        }else if(hGas.compareTo(g3)<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)){
            hMoney = p1.multiply(g1).add(m2).add(p3.multiply((hGas.subtract(g2))));
        }else if(hGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)){
            hMoney = p1.multiply(g1).add(m2).add(m3).add(p4.multiply((hGas.subtract(g3))));
        }else if(hGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)){
            hMoney = p1.multiply(g1).add(m2).add(m3).add(m4)
                    .add(p5.multiply((hGas.subtract(g4))));
        }else {
            hMoney = p1.multiply(g1).add(p2.multiply(g2)).add(p3.multiply(g3)).add(m4).add(p5.multiply(g5))
                    .add(p6.multiply((hGas.subtract(g5))));
        }
        BigDecimal tMoney = money.add(hMoney);
        if(tMoney.compareTo(m1)<=0||(g1.compareTo(maxParams)>=0&&g2.compareTo(initParams)==0)){
            gas = money.divide(p1,2,BigDecimal.ROUND_HALF_UP);
        }else if(tMoney.compareTo((m2.add(m1)))<=0||(g2.compareTo(maxParams)>=0&&g3.compareTo(initParams)==0)){
            BigDecimal gas1 = m1.divide(p1,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas2 = (tMoney.subtract(m1)).divide(p2,2,BigDecimal.ROUND_HALF_UP);
            gas = gas1.add(gas2).subtract(hGas);
        }else if(tMoney.compareTo((m2.add(m1).add(m3)))<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)){
            BigDecimal gas1 = m1.divide(p1,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas2 = m2.divide(p2,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas3 = (tMoney.subtract((m2.add(m1)))).divide(p3,2,BigDecimal.ROUND_HALF_UP);
            gas = gas1.add(gas2).add(gas3).subtract(hGas);
        }else if(tMoney.compareTo((m2.add(m1).add(m3).add(m4)))<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)){
            BigDecimal gas1 = m1.divide(p1,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas2 = m2.divide(p2,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas3 = m3.divide(p3,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas4 = (tMoney.subtract((m2.add(m1).add(m3)))).divide(p4,2,BigDecimal.ROUND_HALF_UP);
            gas = gas1.add(gas2).add(gas3).add(gas4).subtract(hGas);
        }else if(tMoney.compareTo((m2.add(m1).add(m3).add(m4).add(m5)))<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)){
            BigDecimal gas1 = m1.divide(p1,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas2 = m2.divide(p2,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas3 = m3.divide(p3,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas4 = m4.divide(p4,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas5 = (tMoney.subtract((m2.add(m1).add(m3).add(m4)))).divide(p5,2,BigDecimal.ROUND_HALF_UP);
            gas = gas1.add(gas2).add(gas3).add(gas4).add(gas5).subtract(hGas);
        }else{
            BigDecimal gas1 = m1.divide(p1,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas2 = m2.divide(p2,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas3 = m3.divide(p3,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas4 = m4.divide(p4,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas5 = m5.divide(p5,2,BigDecimal.ROUND_HALF_UP);
            BigDecimal gas6 = (tMoney.subtract((m2.add(m1).add(m3).add(m4).add(m5)))).divide(p6,2,BigDecimal.ROUND_HALF_UP);
            gas = gas1.add(gas2).add(gas3).add(gas4).add(gas5).add(gas6).subtract(hGas);
        }
        return gas;
    }
    /**
     * 阶梯计费-计算历史量小于一阶的情况
     * @param priceScheme 价格方案
     * @param hcGas 历史和当前气量之和
     * @param hGas 历史气量
     * @param settlementVO 计费后的结果
     * @param cGas 当前气量
     * @return
     */
    private SettlementVO calculateLadderOneMoney(PriceScheme priceScheme, BigDecimal hcGas,
                                                 BigDecimal hGas, SettlementVO settlementVO, BigDecimal cGas){
        //价格方案信息
        BigDecimal initParams = new BigDecimal("0.0000");
        BigDecimal maxParams = new BigDecimal(65535);
        BigDecimal g1 = priceScheme.getGas1();BigDecimal p1 = priceScheme.getPrice1();
        BigDecimal p2 = initParams;BigDecimal g2 = initParams;
        if(priceScheme.getGas2()!=null){g2 = priceScheme.getGas2();p2 = priceScheme.getPrice2();}
        BigDecimal g3 = initParams;BigDecimal p3 = initParams;
        if(priceScheme.getGas3()!=null){g3 = priceScheme.getGas3();p3 = priceScheme.getPrice3();}
        BigDecimal g4 = initParams;BigDecimal p4 = initParams;
        if(priceScheme.getGas4()!=null){g4 = priceScheme.getGas4();p4 = priceScheme.getPrice4();}
        BigDecimal g5 = initParams;BigDecimal p5 = initParams;
        if(priceScheme.getGas5()!=null){g5 = priceScheme.getGas5();p5 = priceScheme.getPrice5();}
        BigDecimal g6 = initParams;BigDecimal p6 = initParams;
        if(priceScheme.getGas6()!=null){g6 = priceScheme.getGas6();p6 = priceScheme.getPrice6();}
        BigDecimal money = new BigDecimal("0.0000");
        //判断当前用气量加上历史用气量落在哪个阶梯
        if(hcGas.compareTo(g1)<=0) {
            money = CalRoundUtil.bigDecimalRound(p1.multiply(cGas),num);
            settlementDetail(settlementVO,money,p1,cGas,initParams,initParams,initParams,initParams,initParams,
                             initParams,initParams,initParams,initParams,initParams,p1);
        }else if(hcGas.compareTo(g2)<=0||(g2.compareTo(maxParams)>=0&&g3.compareTo(initParams)==0)) {
            BigDecimal moneyG2 = (hcGas.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG2.add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,hcGas.subtract(g1),initParams,initParams,initParams,
                    initParams,initParams,initParams,initParams,initParams,p2);
        }else if(hcGas.compareTo(g3)<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)) {
            BigDecimal moneyG3 = (hcGas.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG3.add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(hcGas.subtract(g2)),initParams,
                    initParams,initParams,initParams,initParams,initParams,p3);
        }else if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            BigDecimal moneyG4 = (hcGas.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG4.add(moneyG3).add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(g3.subtract(g2)),p4,
                    (hcGas.subtract(g3)),initParams,initParams,initParams,initParams,p4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4).add(moneyG3).add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(hcGas.subtract(g4)),initParams,initParams,p5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4).add(moneyG3).add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6);
        }
        return settlementVO;
    }
    /**
     * 阶梯计费-计算历史量小于二阶的情况
     * @param priceScheme 价格方案
     * @param hcGas 历史和当前气量之和
     * @param hGas 历史气量
     * @param settlementVO 计费后的结果
     * @param cGas 当前气量
     * @return
     */
    private SettlementVO calculateLadderTwoMoney(PriceScheme priceScheme,BigDecimal hcGas,
                                               BigDecimal hGas,SettlementVO settlementVO,BigDecimal cGas){
        //价格方案信息
        BigDecimal initParams = new BigDecimal("0.0000");
        BigDecimal maxParams = new BigDecimal(65535);
        BigDecimal p2 = initParams;BigDecimal g2 = initParams;
        if(priceScheme.getGas2()!=null){g2 = priceScheme.getGas2();p2 = priceScheme.getPrice2();}
        BigDecimal g3 = initParams;BigDecimal p3 = initParams;
        if(priceScheme.getGas3()!=null){g3 = priceScheme.getGas3();p3 = priceScheme.getPrice3();}
        BigDecimal g4 = initParams;BigDecimal p4 = initParams;
        if(priceScheme.getGas4()!=null){g4 = priceScheme.getGas4();p4 = priceScheme.getPrice4();}
        BigDecimal g5 = initParams;BigDecimal p5 = initParams;
        if(priceScheme.getGas5()!=null){g5 = priceScheme.getGas5();p5 = priceScheme.getPrice5();}
        BigDecimal g6 = initParams;BigDecimal p6 = initParams;
        if(priceScheme.getGas6()!=null){g6 = priceScheme.getGas6();p6 = priceScheme.getPrice6();}
        BigDecimal money = new BigDecimal("0.0000");
        //判断当前用气量加上历史用气量落在哪个阶梯
        if(hcGas.compareTo(g2)<=0||(g2.compareTo(maxParams)>=0&&g3.compareTo(initParams)==0)) {
            money = CalRoundUtil.bigDecimalRound(p2.multiply(cGas),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,cGas,initParams,initParams,initParams,
                    initParams,initParams,initParams,initParams,initParams,p2);
        }else if(hcGas.compareTo(g3)<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)) {
            BigDecimal moneyG3 = (hcGas.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG3.add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(hcGas.subtract(g2)),initParams,
                    initParams,initParams,initParams,initParams,initParams,p3);
        }else if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            BigDecimal moneyG4 = (hcGas.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG4.add(moneyG3).add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(g3.subtract(g2)),p4,
                    (hcGas.subtract(g3)),initParams,initParams,initParams,initParams,p4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4).add(moneyG3).add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(hcGas.subtract(g4)),initParams,initParams,p5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4).add(moneyG3).add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6);
        }
        return settlementVO;
    }
    /**
     * 阶梯计费-计算历史量小于三阶的情况
     * @param priceScheme 价格方案
     * @param hcGas 历史和当前气量之和
     * @param hGas 历史气量
     * @param settlementVO 计费后的结果
     * @param cGas 当前气量
     * @return
     */
    private SettlementVO calculateLadderThreeMoney(PriceScheme priceScheme,BigDecimal hcGas,
                                               BigDecimal hGas,SettlementVO settlementVO,BigDecimal cGas){
        //价格方案信息
        BigDecimal initParams = new BigDecimal("0.0000");
        BigDecimal maxParams = new BigDecimal(65535);
        BigDecimal g3 = initParams;BigDecimal p3 = initParams;
        if(priceScheme.getGas3()!=null){g3 = priceScheme.getGas3();p3 = priceScheme.getPrice3();}
        BigDecimal g4 = initParams;BigDecimal p4 = initParams;
        if(priceScheme.getGas4()!=null){g4 = priceScheme.getGas4();p4 = priceScheme.getPrice4();}
        BigDecimal g5 = initParams;BigDecimal p5 = initParams;
        if(priceScheme.getGas5()!=null){g5 = priceScheme.getGas5();p5 = priceScheme.getPrice5();}
        BigDecimal g6 = initParams;BigDecimal p6 = initParams;
        if(priceScheme.getGas6()!=null){g6 = priceScheme.getGas6();p6 = priceScheme.getPrice6();}
        BigDecimal money = new BigDecimal("0.0000");
        //判断当前用气量加上历史用气量落在哪个阶梯
        if(hcGas.compareTo(g3)<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)) {
            money = CalRoundUtil.bigDecimalRound(p3.multiply(cGas),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,cGas,initParams,
                    initParams,initParams,initParams,initParams,initParams,p3);
        }else if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            BigDecimal moneyG4 = (hcGas.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(hGas)).multiply(p3);
            money = CalRoundUtil.bigDecimalRound(moneyG4.add(moneyG3),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,(g3.subtract(hGas)),p4,
                    (hcGas.subtract(g3)),initParams,initParams,initParams,initParams,p4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(hGas)).multiply(p3);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4).add(moneyG3),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,(g3.subtract(hGas)),p4,
                    (g4.subtract(g3)),p5,(hcGas.subtract(g4)),initParams,initParams,p5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(hGas)).multiply(p3);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4).add(moneyG3),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,(g3.subtract(hGas)),p4,
                    (g4.subtract(g3)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6);
        }
        return settlementVO;
    }
    /**
     * 阶梯计费-计算历史量小于四阶的情况
     * @param priceScheme 价格方案
     * @param hcGas 历史和当前气量之和
     * @param hGas 历史气量
     * @param settlementVO 计费后的结果
     * @param cGas 当前气量
     * @return
     */
    private SettlementVO calculateLadderFourMoney(PriceScheme priceScheme,BigDecimal hcGas,
                                                 BigDecimal hGas,SettlementVO settlementVO,BigDecimal cGas){
        //价格方案信息
        BigDecimal initParams = new BigDecimal("0.0000");
        BigDecimal maxParams = new BigDecimal(65535);
        BigDecimal g4 = initParams;BigDecimal p4 = initParams;
        if(priceScheme.getGas4()!=null){g4 = priceScheme.getGas4();p4 = priceScheme.getPrice4();}
        BigDecimal g5 = initParams;BigDecimal p5 = initParams;
        if(priceScheme.getGas5()!=null){g5 = priceScheme.getGas5();p5 = priceScheme.getPrice5();}
        BigDecimal g6 = initParams;BigDecimal p6 = initParams;
        if(priceScheme.getGas6()!=null){g6 = priceScheme.getGas6();p6 = priceScheme.getPrice6();}
        BigDecimal money = new BigDecimal("0.0000");
        //判断当前用气量加上历史用气量落在哪个阶梯
        if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            money = CalRoundUtil.bigDecimalRound(p4.multiply(cGas),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,p4,
                    cGas,initParams,initParams,initParams,initParams,p4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(hGas)).multiply(p4);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,p4,
                    (g4.subtract(hGas)),p5,(hcGas.subtract(g4)),initParams,initParams,p5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(hGas)).multiply(p4);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,p4,
                    (g4.subtract(hGas)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6);
        }
        return settlementVO;
    }
    /**
     * 阶梯计费-计算历史量小于五阶的情况
     * @param priceScheme 价格方案
     * @param hcGas 历史和当前气量之和
     * @param hGas 历史气量
     * @param settlementVO 计费后的结果
     * @param cGas 当前气量
     * @return
     */
    private SettlementVO calculateLadderFiveMoney(PriceScheme priceScheme,BigDecimal hcGas,
                                                BigDecimal hGas,SettlementVO settlementVO,BigDecimal cGas){
        //价格方案信息
        BigDecimal initParams = new BigDecimal("0.0000");
        BigDecimal maxParams = new BigDecimal(65535);
        BigDecimal g5 = initParams;BigDecimal p5 = initParams;
        if(priceScheme.getGas5()!=null){g5 = priceScheme.getGas5();p5 = priceScheme.getPrice5();}
        BigDecimal g6 = initParams;BigDecimal p6 = initParams;
        if(priceScheme.getGas6()!=null){g6 = priceScheme.getGas6();p6 = priceScheme.getPrice6();}
        BigDecimal money = new BigDecimal("0.0000");
        //判断当前用气量加上历史用气量落在哪个阶梯
        if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            money = CalRoundUtil.bigDecimalRound(p5.multiply(cGas),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,initParams,
                    initParams,p5,cGas,initParams,initParams,p5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(hGas)).multiply(p5);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,initParams,
                    initParams,p5,cGas,p6,(hcGas.subtract(g5)),p6);
        }
        return settlementVO;
    }
    /**
     * 阶梯计费-计算历史量小于六阶的情况
     * @param priceScheme 价格方案
     * @param hcGas 历史和当前气量之和
     * @param hGas 历史气量
     * @param settlementVO 计费后的结果
     * @param cGas 当前气量
     * @return
     */
    private SettlementVO calculateLadderSixMoney(PriceScheme priceScheme,BigDecimal hcGas,
                                                BigDecimal hGas,SettlementVO settlementVO,BigDecimal cGas){
        //价格方案信息
        BigDecimal g6 = priceScheme.getGas6();
        BigDecimal p6 = priceScheme.getPrice6();
        BigDecimal money = new BigDecimal("0.0000");
        BigDecimal initParams = new BigDecimal("0.0000");
        money = CalRoundUtil.bigDecimalRound(p6.multiply(cGas),num);
        settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,initParams,
                initParams,initParams,initParams,p6,cGas,p6);
        return settlementVO;
    }


    private PriceScheme isPopulationAddPrice(PriceScheme price,GasMeter gasMeter,UseGasType useGasType){
        //为1标识启用人口递增
        if(useGasType.getPopulationGrowth()==1){
            //根据气表人口数计算递增气量
            Integer cusBaseNum = useGasType.getPopulationBase();
            Integer cusNum = gasMeter.getPopulation();
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
     * 是否启用开户递减
     * @param price
     * @param gasMeter
     * @param useGasType
     * @return
     */
    private PriceScheme isOpenDecrement(PriceScheme price,GasMeter gasMeter,UseGasType useGasType){
        if(useGasType.getOpenDecrement()==1&&useGasType.getDecrement()!=null){
            LocalDateTime openDate = gasMeter.getOpenAccountTime();
            int month = (openDate.getYear() - price.getCycleEnableDate().getYear())*12
                    + (openDate.getMonthValue()-price.getCycleEnableDate().getMonthValue());
            int months = month%(price.getCycle());
            if(months>0){
                //设置每个阶梯的递减量
                if(price.getGas1().compareTo((useGasType.getDecrement().multiply(new BigDecimal(months))))>0){
                    price.setGas1(price.getGas1().subtract((useGasType.getDecrement().multiply(new BigDecimal(months)))));
                    BigDecimal disGas2 = (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(2));
                    if(price.getGas2()!=null&&price.getGas2().compareTo(disGas2)>0){
                        price.setGas2(price.getGas2().subtract(disGas2));
                    }
                    BigDecimal disGas3 = (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(3));
                    if(price.getGas3()!=null&&price.getGas3().compareTo(disGas3)>0){
                        price.setGas3(price.getGas3().subtract(disGas3));
                    }
                    BigDecimal disGas4 = (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(4));
                    if(price.getGas4()!=null&&price.getGas4().compareTo(disGas4)>0){
                        price.setGas4(price.getGas4().subtract(disGas4));
                    }
                    BigDecimal disGas5= (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(5));
                    if(price.getGas5()!=null&&price.getGas5().compareTo(disGas5)>0){
                        price.setGas5(price.getGas5().subtract(disGas5));
                    }
                    BigDecimal disGas6= (useGasType.getDecrement().multiply(new BigDecimal(months))).multiply(new BigDecimal(6));
                    if(price.getGas6()!=null&&price.getGas6().compareTo(disGas6)>0){
                        price.setGas6(price.getGas6().subtract(disGas6));
                    }
                }
                log.info("**********************开户按月递减后的价格方案**********************");
                log.info(JSON.toJSONString(price));
            }
        }
        return price;
    }
    /**
     * 设置阶梯计费需要的条件
     * @param price 价格方案
     * @param gasMeterInfo 气表使用情况
     * @param gasMeter 气表档案
     * @param meterDataVO 本次抄表数据
     * @param useGasType 用气类型
     * @return
     */
    private CalculateVO calculateCondition(PriceScheme price,GasMeter gasMeter,GasMeterInfo gasMeterInfo,
                                           ReadMeterData meterDataVO,UseGasType useGasType,boolean isCycleClear){
        CalculateVO calculateVO = new CalculateVO();
        //判断是否启用人口递增
        PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
        //判断是否启用开户递减
        PriceScheme priceSchemeLast = isOpenDecrement(priceScheme,gasMeter,useGasType);
        //确定最终价格方案进行计算
        calculateVO.setPriceScheme(priceSchemeLast);
        //是否调价、周期清零
        if(null!=meterDataVO.getLastReadTime()){
            calculateVO = isChangePriceClear(price,meterDataVO,calculateVO,gasMeterInfo,isCycleClear);
        }else{
            if(gasMeterInfo.getCycleUseGas()!=null){
                calculateVO.setHistoryGas(gasMeterInfo.getCycleUseGas());
            }else{
                calculateVO.setHistoryGas(BigDecimal.ZERO);
            }
            calculateVO.setIsClear(false);
        }
        calculateVO.setCurrentGas(meterDataVO.getMonthUseGas());
        return calculateVO;
    }

    /**
     * 更新抄表数据、气表使用情况、新增结算记录
     * @param meterDataVO 抄表数据
     * @param gasMeterInfo 气表使用情况
     * @param price 价格方案
     * @param useGasType 用气类型
     * @param account 用户账户
     * @param gasMeter 气表档案
     */
    @Override
    public R<GasmeterSettlementDetail> updateCalculate(ReadMeterData meterDataVO, GasMeterInfo gasMeterInfo,
                                                        PriceScheme price, UseGasType useGasType, CustomerAccount account,
                                                        GasMeter gasMeter, SettlementVO settlementVO,CalculateVO calculateVO){
        GasmeterSettlementDetail gSds = new GasmeterSettlementDetail();
        if(calculateVO.getIsClear()){
            meterDataVO.setCycleTotalUseGas(BigDecimal.ZERO);
            gasMeterInfo.setCycleUseGas(meterDataVO.getMonthUseGas());
            meterDataVO.setCurrentCycleTotalUseGas(meterDataVO.getMonthUseGas());
        }else{
            if(gasMeterInfo.getCycleUseGas()==null){
                meterDataVO.setCurrentCycleTotalUseGas(meterDataVO.getMonthUseGas());
                meterDataVO.setCycleTotalUseGas(BigDecimal.ZERO);
            }else{
                meterDataVO.setCurrentCycleTotalUseGas(gasMeterInfo.getCycleUseGas().add(meterDataVO.getMonthUseGas()));
                meterDataVO.setCycleTotalUseGas(gasMeterInfo.getCycleUseGas());
            }
            if(gasMeterInfo.getCycleUseGas()==null){gasMeterInfo.setCycleUseGas(meterDataVO.getMonthUseGas());}
            else{gasMeterInfo.setCycleUseGas(gasMeterInfo.getCycleUseGas().add(meterDataVO.getMonthUseGas()));}
        }
        /**更新结算后的抄表数据**/
        meterDataVO.setSettlementTime(LocalDateTime.now());
        meterDataVO.setSettlementUserId(BaseContextHandler.getUserId());
        meterDataVO.setSettlementUserName(BaseContextHandler.getName());
        meterDataVO.setGasOweCode(BizCodeNewUtil.getOweCodeEX(BizSCode.CHARGE));
        //meterDataVO.setCycleTotalUseGas(gasMeterInfo.getCycleUseGas());
        meterDataVO.setProcessStatus(ProcessEnum.SETTLED);

        /**更新气表使用情况**/
        //gasMeterInfo.setPriceSchemeId(price.getId());
        if(gasMeterInfo.getTotalUseGas()==null){gasMeterInfo.setTotalUseGas(meterDataVO.getMonthUseGas());}
        else{gasMeterInfo.setTotalUseGas(gasMeterInfo.getTotalUseGas().add(meterDataVO.getMonthUseGas()));}
        if(gasMeterInfo.getTotalUseGasMoney()==null){gasMeterInfo.setTotalUseGasMoney(settlementVO.getMoney());}
        else{gasMeterInfo.setTotalUseGasMoney(gasMeterInfo.getTotalUseGasMoney().add(settlementVO.getMoney()));}
        if(meterDataVO.getDataType()==0){
            gasMeterInfoService.updateById(gasMeterInfo);
        }
        /**新增结算记录**/
        gSds.setCustomerCode(meterDataVO.getCustomerCode());
        gSds.setSettlementNo(meterDataVO.getGasOweCode());
        gSds.setCustomerName(meterDataVO.getCustomerName());
        gSds.setGasmeterCode(meterDataVO.getGasMeterCode());
        if(StringUtils.isNotEmpty(meterDataVO.getGasMeterName())){gSds.setGasmeterName(meterDataVO.getGasMeterName());}
        gSds.setReadmeterDataId(meterDataVO.getId());
        gSds.setReadmeterMonth(meterDataVO.getReadMeterMonth().toString());
        gSds.setGas(meterDataVO.getMonthUseGas());
        gSds.setGasMoney(settlementVO.getMoney());
        gSds.setDataStatus(1);
        //gSds.setSettlementNo(BizCodeUtil.genBizDataCode());
        //设置人口浮动金额、价格
        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
            gSds.setPopulationFloatMoney(new BigDecimal(0));
            gSds.setPrice(price.getFixedPrice());
        }else{
//            if(useGasType.getPopulationGrowth()==1&&gasMeter.getPopulation()!=null&&gasMeter.getPopulation()>useGasType.getPopulationBase()){
//                CalculateVO calculateVOs = new CalculateVO();
//                calculateVOs.setPriceScheme(price);
//                calculateVOs.setHistoryGas(gasMeterInfo.getCycleUseGas());
//                calculateVOs.setCurrentGas(meterDataVO.getMonthUseGas());
//                SettlementVO sv = calculateLadderGasCost(calculateVOs).getData();
//                gSds.setPopulationFloatMoney(settlementVO.getMoney().subtract(sv.getMoney()));
//            }else{
//                gSds.setPopulationFloatMoney(new BigDecimal(0));
//            }
            //设置各个阶梯明细
            setLadderMoney(gSds,settlementVO);
        }
        //设置抵扣前账户余额
        if(account.getGiveMoney()!=null){gSds.setGiveBookPreMoney(account.getGiveMoney());}
        if(account.getAccountMoney()!=null){gSds.setSettlementPreMoney(account.getAccountMoney());}
        gSds.setBillingDate(LocalDate.now());
        gSds.setUseGasTypeId(useGasType.getId());
        gSds.setUseGasTypeName(useGasType.getUseGasTypeName());
        gSds.setLadderChargeModeId(price.getId());
        gasmeterSettlementDetailService.save(gSds);
        return R.success(gSds);
    }

    @Override
    public R<PriceScheme> queryPriceScheme(String meterCode, LocalDate activateDate) {
        if(StringUtil.isNullOrBlank(meterCode)){return R.fail(i18nUtil.getMessage("params.meter.code.is.not.empty"));}
        if(activateDate==null){return R.fail(i18nUtil.getMessage("params.time.is.not.empty"));}
        GasMeter gasMeter = gasMeterService.findGasMeterByCode(meterCode);
        if(gasMeter==null){return R.fail(i18nUtil.getMessage("not.find.gas.meter"));}
        UseGasType useGasType = useGasTypeService.getById(gasMeter.getUseGasTypeId());
        if(useGasType==null){return R.fail(i18nUtil.getMessage("not.find.use.gas.type"));}
        if(useGasType.getDataStatus()!=null&&useGasType.getDataStatus()==0)
        {return R.fail(i18nUtil.getMessage("use.gas.type.disable"));}
        PriceScheme price = null;
        if(PriceType.HEATING_PRICE.getCode().equals(useGasType.getPriceType())){
            PriceScheme priceHeating = priceSchemeService.queryPriceHeatingByTime(useGasType.getId(),activateDate);
            if(null == priceHeating){throw new  BizException("未找到采暖价格方案");}
            //采暖方案切换时间
            LocalDate heatingDate = priceHeating.getCycleStartTime();
            //判断抄表数据是否处于采暖季
            if(activateDate.isAfter(heatingDate)||activateDate.isEqual(heatingDate)){
                price = priceHeating;
            }else{
                price = priceSchemeService.queryRecentRecordByTime(gasMeter.getUseGasTypeId(),activateDate);
            }
        }else{
            price = priceSchemeService.queryRecentRecordByTime(gasMeter.getUseGasTypeId(),activateDate);
        }
        if(price==null){return R.fail(i18nUtil.getMessage("not.find.gas.price.scheme"));}
        //是否多人口调价
        PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
        return R.success(priceScheme);
    }

    @Override
    public R<PriceScheme> queryPriceScheme(String meterCode) {
        if(StringUtil.isNullOrBlank(meterCode)){return R.fail(i18nUtil.getMessage("params.meter.code.is.not.empty"));}
        GasMeter gasMeter = gasMeterService.findGasMeterByCode(meterCode);
        if(gasMeter==null){return R.fail(i18nUtil.getMessage("not.find.gas.meter"));}
        UseGasType useGasType = useGasTypeService.getById(gasMeter.getUseGasTypeId());
        if(useGasType==null){return R.fail(i18nUtil.getMessage("not.find.use.gas.type"));}
        if(useGasType.getDataStatus()!=null&&useGasType.getDataStatus()==0)
        {return R.fail(i18nUtil.getMessage("use.gas.type.disable"));}
        PriceScheme price = null;
        if(PriceType.HEATING_PRICE.getCode().equals(useGasType.getPriceType())){
            PriceScheme priceHeating = priceSchemeService.queryPreHeatingPriceScheme(useGasType.getId());
            if(null == priceHeating){throw new  BizException("未找到采暖价格方案");}
            //采暖方案切换时间
            LocalDate heatingDate = priceHeating.getCycleStartTime();
            LocalDate now = priceHeating.getStartTime();
            //判断抄表数据是否处于采暖季
            if(now.isAfter(heatingDate)||now.isEqual(heatingDate)){
                price = priceHeating;
            }else{
                price = priceSchemeService.queryPrePriceScheme(gasMeter.getUseGasTypeId());
            }
        }else{
            price = priceSchemeService.queryPrePriceScheme(gasMeter.getUseGasTypeId());
        }
        if(price==null){return R.fail(i18nUtil.getMessage("not.find.gas.price.scheme"));}
        //是否多人口调价
        PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
        return R.success(priceScheme);
    }


    @Override
    public R<Boolean> calculatePenaltyEX(String gasMeterCode, String executeDate) {
        return calculatePenaltyData(gasMeterCode,executeDate);
    }
    /**
     * 设置各个阶梯明细
     * @param settlementVO @param money
     * @param price1 @param gas1
     * @param price2 @param gas2
     * @param price3 @param gas3
     * @param price4 @param gas4
     * @param price5 @param gas5
     * @param price6 @param gas6
     * @return
     */
    private SettlementVO settlementDetail(SettlementVO settlementVO,BigDecimal money,BigDecimal price1,BigDecimal
                                          gas1,BigDecimal price2,BigDecimal gas2,
                                         BigDecimal price3,BigDecimal gas3,BigDecimal price4,BigDecimal gas4,
                                         BigDecimal price5,BigDecimal gas5,BigDecimal price6,BigDecimal gas6,BigDecimal currentPrice){
        settlementVO.setGas1(gas1);
        settlementVO.setPrice1(price1);
        settlementVO.setMoney1(CalRoundUtil.bigDecimalRound(price1.multiply(gas1),num));

        settlementVO.setGas2(gas2);
        settlementVO.setPrice2(price2);
        settlementVO.setMoney2(CalRoundUtil.bigDecimalRound(price2.multiply(gas2),num));

        settlementVO.setGas3(gas3);
        settlementVO.setPrice3(price3);
        settlementVO.setMoney3( CalRoundUtil.bigDecimalRound(price3.multiply(gas3),num));

        settlementVO.setGas4(gas4);
        settlementVO.setPrice4(price4);
        settlementVO.setMoney4( CalRoundUtil.bigDecimalRound(price4.multiply(gas4),num));

        settlementVO.setGas5(gas5);
        settlementVO.setPrice5(price5);
        settlementVO.setMoney5( CalRoundUtil.bigDecimalRound(price5.multiply(gas5),num));

        settlementVO.setGas6(gas6);
        settlementVO.setPrice6(price6);
        settlementVO.setMoney6( CalRoundUtil.bigDecimalRound(price6.multiply(gas6),num));

        settlementVO.setMoney(money);

        settlementVO.setSettlementTime(LocalDate.now());

        settlementVO.setCurrentPrice(currentPrice);
        return settlementVO;
    }

    /**
     * 设置结算记录各个阶梯明细
     * @param gSds 抵扣前结算记录
     * @param settlementVO 结算结果
     * @return
     */
    private GasmeterSettlementDetail setLadderMoney(GasmeterSettlementDetail gSds,SettlementVO settlementVO){
        gSds.setGas1(settlementVO.getGas1());
        gSds.setPrice1(settlementVO.getPrice1());
        gSds.setMoney1(settlementVO.getMoney1());

        gSds.setGas2(settlementVO.getGas2());
        gSds.setPrice2(settlementVO.getPrice2());
        gSds.setMoney2(settlementVO.getMoney2());

        gSds.setGas3(settlementVO.getGas3());
        gSds.setPrice3(settlementVO.getPrice3());
        gSds.setMoney3(settlementVO.getMoney3());

        gSds.setGas4(settlementVO.getGas4());
        gSds.setPrice4(settlementVO.getPrice4());
        gSds.setMoney4(settlementVO.getMoney4());

        gSds.setGas5(settlementVO.getGas5());
        gSds.setPrice5(settlementVO.getPrice5());
        gSds.setMoney5(settlementVO.getMoney5());

        gSds.setGas6(settlementVO.getGas6());
        gSds.setPrice6(settlementVO.getPrice6());
        gSds.setMoney6(settlementVO.getMoney6());
        return gSds;
    }

    /**
     * 生成欠费记录
     * @param meterDataVO 抄表数据
     * @param settlementVO 结算结果
     * @param arrearsMoney 欠费金额
     */
    private void createArrears(ReadMeterData meterDataVO,SettlementVO settlementVO,BigDecimal arrearsMoney,UseGasType useGasType
            ,PriceScheme priceScheme,int type){
        /**新增欠费记录**/
        GasmeterArrearsDetail gads = new GasmeterArrearsDetail();
        gads.setCustomerCode(meterDataVO.getCustomerCode());
        gads.setCustomerName(meterDataVO.getCustomerName());
        gads.setGasmeterCode(meterDataVO.getGasMeterCode());
        if(type==1){
            gads.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        }else{
            gads.setDataStatus(DataStatusEnum.NORMAL.getValue());
        }
        if(StringUtils.isNotEmpty(meterDataVO.getGasMeterName())){gads.setGasmeterName(meterDataVO.getGasMeterName());}
        gads.setReadmeterDataId(meterDataVO.getId());
        String monthStr = meterDataVO.getReadMeterMonth().toString();
        if(monthStr.length()==1){monthStr = "0"+monthStr;}
        gads.setReadmeterMonth(meterDataVO.getReadMeterYear()+"-"+monthStr);
        gads.setGas(meterDataVO.getMonthUseGas());
        gads.setGasMoney(settlementVO.getMoney());
        gads.setSettlementNo(meterDataVO.getGasOweCode());
        gads.setArrearsMoney(arrearsMoney);
        gads.setUseGasTypeId(useGasType.getId());
        gads.setUseGasTypeName(useGasType.getUseGasTypeName());
        gads.setArrearsStatus(ChargeStatusEnum.UNCHARGE.getCode());
        gads.setBillingDate(settlementVO.getSettlementTime());
        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
            gads.setPrice(priceScheme.getFixedPrice());
            gads.setIsLadderPrice(FALSE);
        }else{
            List<LadderDetailVO> ladderDetailList = setLadderDetail(settlementVO);
            gads.setLeadderPriceDetail(JSON.toJSONString(ladderDetailList));
            gads.setIsLadderPrice(TRUE);
        }
        gasmeterArrearsDetailService.save(gads);
    }

    private List<LadderDetailVO> setLadderDetail(SettlementVO settlementVO){
        List<LadderDetailVO> ladderDetailList = new ArrayList<>();
        for(int i=1;i<=6;i++){
            LadderDetailVO ladderDetailVO = new LadderDetailVO();
            switch (i){
                case 1:
                    if(settlementVO.getGas1().compareTo(new BigDecimal("0.00"))==0
                            ||settlementVO.getGas1().compareTo(new BigDecimal("0.0000"))==0){break;}
                    ladderDetailVO.setGas(settlementVO.getGas1());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(settlementVO.getPrice1());
                    ladderDetailVO.setTotalMoney(settlementVO.getMoney1());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 2:
                    if(settlementVO.getGas2().compareTo(new BigDecimal("0.00"))==0
                            ||settlementVO.getGas2().compareTo(new BigDecimal("0.0000"))==0){break;}
                    ladderDetailVO.setGas(settlementVO.getGas2());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(settlementVO.getPrice2());
                    ladderDetailVO.setTotalMoney(settlementVO.getMoney2());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 3:
                    if(settlementVO.getGas3().compareTo(new BigDecimal("0.00"))==0
                            ||settlementVO.getGas3().compareTo(new BigDecimal("0.0000"))==0){break;}
                    ladderDetailVO.setGas(settlementVO.getGas3());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(settlementVO.getPrice3());
                    ladderDetailVO.setTotalMoney(settlementVO.getMoney3());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 4:
                    if(settlementVO.getGas4().compareTo(new BigDecimal("0.00"))==0
                            ||settlementVO.getGas4().compareTo(new BigDecimal("0.0000"))==0){break;}
                    ladderDetailVO.setGas(settlementVO.getGas4());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(settlementVO.getPrice4());
                    ladderDetailVO.setTotalMoney(settlementVO.getMoney4());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 5:
                    if(settlementVO.getGas5().compareTo(new BigDecimal("0.00"))==0
                            ||settlementVO.getGas5().compareTo(new BigDecimal("0.0000"))==0){break;}
                    ladderDetailVO.setGas(settlementVO.getGas5());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(settlementVO.getPrice5());
                    ladderDetailVO.setTotalMoney(settlementVO.getMoney5());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 6:
                    if(settlementVO.getGas6().compareTo(new BigDecimal("0.00"))==0
                            ||settlementVO.getGas1().compareTo(new BigDecimal("0.0000"))==0){break;}
                    ladderDetailVO.setGas(settlementVO.getGas6());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(settlementVO.getPrice6());
                    ladderDetailVO.setTotalMoney(settlementVO.getMoney6());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                default:
                    break;
            }
        }
        return ladderDetailList;
    }

    /**
     * 更新账户、结算记录、新增账户抵扣流水
     * @param arrearsMoney 欠费金额
     * @param gasmeterSettlementDetail 抵扣前结算明细
     * @param customerAccount 抵扣后账户
     * @param accountPre 抵扣前账户
     */
    private void updateSettlementAccount(BigDecimal arrearsMoney,GasmeterSettlementDetail gasmeterSettlementDetail,
                                         CustomerAccount customerAccount,CustomerAccount accountPre,String gasMeterCode){
        /**更新账户表**/
        customerAccountService.updateById(customerAccount);
        /**新增账户抵扣流水**/
        CustomerAccountSerial accountSerial = new CustomerAccountSerial();
        accountSerial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
        accountSerial.setAccountCode(customerAccount.getAccountCode());
        accountSerial.setCustomerCode(customerAccount.getCustomerCode());
        accountSerial.setGasMeterCode(gasMeterCode);
        accountSerial.setBillType(AccountSerialSceneEnum.DEDUCTION.getCode());
        //账户余额抵扣
        accountSerial.setBookPreMoney(accountPre.getAccountMoney());
        accountSerial.setBookAfterMoney(customerAccount.getAccountMoney());
        accountSerial.setBookMoney(accountPre.getAccountMoney().subtract(customerAccount.getAccountMoney()));
        //赠送抵扣
        if(accountPre.getGiveMoney()!=null&&customerAccount.getGiveMoney()!=null){
            accountSerial.setGiveBookPreMoney(accountPre.getGiveMoney());
            accountSerial.setGiveBookAfterMoney(customerAccount.getGiveMoney());
            accountSerial.setGiveBookMoney(accountPre.getGiveMoney().subtract(customerAccount.getGiveMoney()));
        }
        customerAccountSerialService.save(accountSerial);
        /**更新结算记录**/
        gasmeterSettlementDetail.setArrearsMoney(arrearsMoney);
        if(accountPre.getGiveMoney()!=null&&customerAccount.getGiveMoney()!=null){
            gasmeterSettlementDetail.setGiveBackAfterMoney(customerAccount.getGiveMoney());
            gasmeterSettlementDetail.setGiveDeductionMoney(accountPre.getGiveMoney().
                    subtract(customerAccount.getGiveMoney()));
        }
        gasmeterSettlementDetail.setSettlementAfterMoney(customerAccount.getAccountMoney());
        gasmeterSettlementDetail.setPrechargeDeductionMoney(accountPre.getAccountMoney().
                subtract(customerAccount.getAccountMoney()));
        gasmeterSettlementDetailService.updateById(gasmeterSettlementDetail);
    }

    private void updateGasMeterInfo(GasMeterInfo gasMeterInfo){
        gasMeterInfoService.updateById(gasMeterInfo);
    }

    private void updateReadMeterData(ReadMeterData readMeterData){
        readMeterDataService.updateById(readMeterData);
    }

    /**
     * 滞纳金计算
     * @param gasMeterCode
     * @param executeDate
     * @return
     */
    private R<Boolean> calculatePenaltyData(String gasMeterCode,String executeDate){
        LbqWrapper<GasmeterArrearsDetail> gadQuery =new LbqWrapper<>();
        gadQuery.eq(GasmeterArrearsDetail::getArrearsStatus,ChargeStatusEnum.UNCHARGE.getCode());
        gadQuery.eq(GasmeterArrearsDetail::getDataStatus,DataStatusEnum.NORMAL.getValue());
        if(!StringUtil.isNullOrBlank(gasMeterCode)){
            gadQuery.eq(GasmeterArrearsDetail::getGasmeterCode,gasMeterCode);
        }
        List<GasmeterArrearsDetail> list = gasmeterArrearsDetailService.list(gadQuery);
        if(list==null||list.size()==0){
            log.info("滞纳金计算没找到欠费记录");
            return R.success();
        }
        List<GasmeterArrearsDetail> updateList = new ArrayList<>();
        List<Long> emptyList = new ArrayList<>();
        list.stream().forEach(e->{
            //获取滞纳金方案
            Penalty penalty = penaltyService.queryByUseGasId(e.getUseGasTypeId());
            if(penalty==null){
                emptyList.add(e.getId());
                return;
            }
            //滞纳金执行时间
            LocalDate ex =null;
            if(StringUtil.isNullOrBlank(executeDate)){
                ex = e.getBillingDate();
            }else{
                DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                ex = LocalDate.parse(executeDate,fomatter);
            }
            LocalDate eDate = ex.plusMonths(penalty.getExecuteMonth());
            DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String month = Integer.toString(eDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            String day = Integer.toString(penalty.getExecuteDay());
            if(day.length()==1){day = "0"+day;}
            LocalDate execute = LocalDate.parse(eDate.getYear()+"-"+month+"-"+day,fomatter);
            BigDecimal pMoney = new BigDecimal("0.0000");
            if(penalty.getActivationTime().isAfter(LocalDate.now())||execute.isAfter(LocalDate.now())){
                return;
            }
            //如果当前时间大于等于滞纳金执行时间就开始计算滞纳金
            if(penalty.getActivationTime().isAfter(execute)||penalty.getActivationTime().isEqual(execute)){
                 execute = penalty.getActivationTime();
            }
            long days= (LocalDate.now().toEpochDay()-execute.toEpochDay())+1;
            //是否复利
            BigDecimal rate = (penalty.getRate().divide(new BigDecimal(100),5,BigDecimal.ROUND_HALF_UP));
            if(penalty.getCompoundInterest()!=null&&penalty.getCompoundInterest()==1){
                double rs = Math.pow((rate.doubleValue()+1), days);
                double zlj = e.getArrearsMoney().doubleValue()*rs-e.getArrearsMoney().doubleValue();
                e.setLatepayAmount(CalRoundUtil.bigDecimalRound(BigDecimal.valueOf(zlj),num));
//                BigDecimal zlj = BigDecimal.ZERO;
//                for(int i=0;i<days;i++){
//                    zlj = zlj.add(e.getArrearsMoney().multiply(rate));
//                }
                pMoney = e.getLatepayAmount();
            }else{
                pMoney = CalRoundUtil.bigDecimalRound(e.getArrearsMoney().multiply(rate).multiply(BigDecimal.valueOf(days)),num);
                e.setLatepayAmount(pMoney);
            }
            //是否本金及上限
            if(penalty.getPrincipalCap()!=null&&penalty.getPrincipalCap()==1){
                if(pMoney.compareTo(e.getArrearsMoney())>=0){pMoney = e.getArrearsMoney();e.setLatepayAmount(e.getArrearsMoney());}
            }else{
                //固定上限
                if(pMoney.compareTo(penalty.getFixedCap())>=0){
                    pMoney = CalRoundUtil.bigDecimalRound(penalty.getFixedCap(),num);
                    e.setLatepayAmount(CalRoundUtil.bigDecimalRound(penalty.getFixedCap(),num));
                }
            }
            e.setLateFeeStartDate(execute);
            e.setLatepayDays(days);
            updateList.add(e);
        });
        if(updateList.size()>0){
            gasmeterArrearsDetailService.updateBatchById(updateList);
        }
        if(emptyList.size()>0){
            gasmeterArrearsDetailService.updateBathArrears(emptyList);
        }
        return R.success();
    }

    /**
     * 周期累积量是否清零
     * @param begin 周期开始时间
     * @param end 冻结时间
     * @param cycle 计费周期-单位月
     * @return
     */
    private Boolean isClear(LocalDate begin,LocalDate end,int cycle){
        int month = (end.getYear() - begin.getYear())*12 + (end.getMonthValue()-begin.getMonthValue());
        return (month%cycle==0)&&(begin.getDayOfMonth()==end.getDayOfMonth());
    }

    private LocalDate isClearEx(PriceScheme price,ReadMeterData meterData){
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String day = Integer.toString(price.getCycleEnableDate().getDayOfMonth());
        if(day.length()==1){day = "0"+day;}
        LocalDate begin = price.getCycleEnableDate();
        LocalDate end = meterData.getRecordTime();
        int cycle = price.getCycle();
        int months = (end.getYear() - price.getStartTime().getYear())*12 + (end.getMonthValue()-begin.getMonthValue());
        if((months%cycle==0)){
            String month = Integer.toString(meterData.getRecordTime().getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(meterData.getRecordTime().getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate;
        }else{
            String month = Integer.toString(meterData.getRecordTime().getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(meterData.getRecordTime().getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate.minusMonths((end.getMonthValue()-begin.getMonthValue()));
        }
    }

    private LocalDate isCardClearEx(PriceScheme price){
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String day = Integer.toString(price.getCycleEnableDate().getDayOfMonth());
        if(day.length()==1){day = "0"+day;}
        LocalDate begin = price.getCycleEnableDate();
        LocalDate end = LocalDate.now();
        int cycle = price.getCycle();
        int months = (end.getYear() - price.getStartTime().getYear())*12 + (end.getMonthValue()-begin.getMonthValue());
        if((months%cycle==0)){
            String month = Integer.toString(LocalDate.now().getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(LocalDate.now().getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate;
        }else{
            String month = Integer.toString(LocalDate.now().getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(LocalDate.now().getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate.minusMonths((end.getMonthValue()-begin.getMonthValue()));
        }
    }

    /**
     * 普表是否周期、调价清零
     * @param price
     * @param meterData
     * @param calculateVO
     * @param gasMeterInfo
     * @param isCycleClear
     * @return
     */
    private CalculateVO isChangePriceClear(PriceScheme price,ReadMeterData meterData,CalculateVO calculateVO,
                                           GasMeterInfo gasMeterInfo,boolean isCycleClear){
        int cycle = price.getCycle();
        LocalDate begin = price.getCycleEnableDate();
        LocalDate jfDate = meterData.getRecordTime();
        LocalDate  djDate = price.getStartTime();
        LocalDate cycleDate =  isClearEx(price,meterData);
        //是否周期清零
        if(meterData.getRecordTime().isBefore(cycleDate)){
            //调价清零
            calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,djDate);
        }else{
            calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,cycleDate);
            boolean isCycle = calculateVO.getIsClear();
            if(price.getPriceChangeIsClear()==1){
                //调价清零
                calculateVO = cycleClear(meterData,calculateVO,gasMeterInfo,isCycleClear,djDate);
            }
            boolean isChange = calculateVO.getIsClear();
            if(isCycle==true||isChange==true){
                calculateVO.setIsClear(true);
                calculateVO.setHistoryGas(BigDecimal.ZERO);
            }
        }
        return calculateVO;
    }


    private CalculateVO isCardChangePriceClear(PriceScheme price,CalculateVO calculateVO,
                                           GasMeterInfo gasMeterInfo){
        int cycle = price.getCycle();
        LocalDate begin = price.getCycleEnableDate();
        LocalDate  djDate = price.getStartTime();
        LocalDate cycleDate =  isCardClearEx(price);
        //是否周期清零
        calculateVO = cardCycleClear(calculateVO,gasMeterInfo,gasMeterInfo.getGasmeterCode(),gasMeterInfo.getCustomerCode(),cycleDate);
        boolean isCycle = calculateVO.getIsClear();
        if(price.getPriceChangeIsClear()==1){
            //调价清零
            calculateVO = cardCycleClear(calculateVO,gasMeterInfo,gasMeterInfo.getGasmeterCode(),gasMeterInfo.getCustomerCode(),djDate);
        }
        boolean isChange = calculateVO.getIsClear();
        if(isCycle==true||isChange==true){
            calculateVO.setIsClear(true);
            calculateVO.setHistoryGas(BigDecimal.ZERO);
        }
        return calculateVO;
    }

    /**
     * 周期
     * @return
     */
    private CalculateVO cycleClear(ReadMeterData meterData, CalculateVO calculateVO,
                               GasMeterInfo gasMeterInfo, boolean isCycleClear, LocalDate localDate){
        //是否调价清零
        LbqWrapper<ReadMeterData> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterData::getProcessStatus,ProcessEnum.SETTLED);
        wrapper.eq(ReadMeterData::getGasMeterCode,meterData.getGasMeterCode());
//        wrapper.eq(ReadMeterData::getCustomerCode,meterData.getCustomerCode());
        wrapper.lt(ReadMeterData::getRecordTime,meterData.getRecordTime());
        wrapper.ge(ReadMeterData::getRecordTime,localDate);
        List<ReadMeterData> list = readMeterDataService.list(wrapper);
        if(list==null||list.size()==0){
            if(isCycleClear){
                calculateVO.setIsClear(true);
            }else{
                calculateVO.setIsClear(false);
            }
            calculateVO.setHistoryGas(BigDecimal.ZERO);
        }else{
            BigDecimal cycleUseGas = null;
            if(null==gasMeterInfo.getCycleUseGas()){
                cycleUseGas = BigDecimal.ZERO;
            }else{
                cycleUseGas = gasMeterInfo.getCycleUseGas();
            }
            calculateVO.setHistoryGas(cycleUseGas);
            calculateVO.setIsClear(false);
        }
        return calculateVO;
    }

    private CalculateVO cardCycleClear(CalculateVO calculateVO,GasMeterInfo gasMeterInfo,
                                       String gasMeterCode,String customerCode,LocalDate cycleDate){
        LbqWrapper<RechargeRecord> rechageWrapper = new LbqWrapper<>();
        rechageWrapper.eq(RechargeRecord::getGasmeterCode,gasMeterCode);
//        rechageWrapper.eq(RechargeRecord::getCustomerCode,customerCode);
        rechageWrapper.eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        rechageWrapper.lt(RechargeRecord::getCreateTime,LocalDateTime.now());
        rechageWrapper.ge(RechargeRecord::getCreateTime,cycleDate);
        List<RechargeRecord> list = rechargeRecordService.list(rechageWrapper);
        if(list==null||list.size()==0){
            calculateVO.setIsClear(true);
            calculateVO.setHistoryGas(BigDecimal.ZERO);
        }else{
            BigDecimal cycleUseGas = null;
            if(null==gasMeterInfo.getCycleUseGas()){
                cycleUseGas = BigDecimal.ZERO;
            }else{
                cycleUseGas = gasMeterInfo.getCycleUseGas();
            }
            calculateVO.setHistoryGas(cycleUseGas);
            calculateVO.setIsClear(false);
        }
        return calculateVO;
    }

    /**
     * 调价清零
     * @param meterData
     * @param calculateVO
     * @param gasMeterInfo
     * @param isCycleClear
     * @param localDate
     * @return
     */
    private CalculateVO changePriceClear(ReadMeterData meterData,CalculateVO calculateVO,
                                   GasMeterInfo gasMeterInfo,boolean isCycleClear,LocalDate localDate){
        //是否调价清零
        LbqWrapper<ReadMeterData> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterData::getProcessStatus,ProcessEnum.SETTLED);
        wrapper.eq(ReadMeterData::getGasMeterCode,meterData.getGasMeterCode());
//        wrapper.eq(ReadMeterData::getCustomerCode,meterData.getCustomerCode());
        wrapper.lt(ReadMeterData::getRecordTime,meterData.getRecordTime());
        wrapper.ge(ReadMeterData::getRecordTime,localDate);
        List<ReadMeterData> list = readMeterDataService.list(wrapper);
        if(list==null||list.size()==0){
            //cycleClear(priceScheme);
            if(isCycleClear){
                calculateVO.setHistoryGas(BigDecimal.ZERO);
                gasMeterInfo.setCycleUseGas(BigDecimal.ZERO);
            }else{
                calculateVO.setHistoryGas(BigDecimal.ZERO);
            }
        }else{
            BigDecimal cycleUseGas = BigDecimal.ZERO;
            for(ReadMeterData data:list){
                cycleUseGas = cycleUseGas.add(data.getMonthUseGas());
            }
            calculateVO.setHistoryGas(cycleUseGas);
            gasMeterInfo.setCycleUseGas(cycleUseGas);
        }
        return calculateVO;
    }
}