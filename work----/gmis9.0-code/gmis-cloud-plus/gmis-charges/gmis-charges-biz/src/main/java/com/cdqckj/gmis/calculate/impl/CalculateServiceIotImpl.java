package com.cdqckj.gmis.calculate.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.biztemporary.GasTypeChangeRecordBizApi;
import com.cdqckj.gmis.biztemporary.service.GasTypeChangeRecordService;
import com.cdqckj.gmis.biztemporary.vo.PriceChangeVO;
import com.cdqckj.gmis.calculate.CalculateIotService;
import com.cdqckj.gmis.calculate.component.MeterIotCacheUtil;
import com.cdqckj.gmis.calculate.vo.*;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.charges.entity.GasmeterSettlementDetail;
import com.cdqckj.gmis.charges.enums.AccountSerialSceneEnum;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.service.CustomerAccountSerialService;
import com.cdqckj.gmis.charges.service.CustomerAccountService;
import com.cdqckj.gmis.charges.service.GasmeterArrearsDetailService;
import com.cdqckj.gmis.charges.service.GasmeterSettlementDetailService;
import com.cdqckj.gmis.common.constant.GMISIotConstant;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizSCode;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.meterinfo.MeterAccountSerialSceneEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.devicearchive.vo.MeterInfoVO;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.iot.qc.dto.*;
import com.cdqckj.gmis.iot.qc.entity.*;
import com.cdqckj.gmis.iot.qc.enumeration.AlramType;
import com.cdqckj.gmis.iot.qc.enumeration.CommandState;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
import com.cdqckj.gmis.iot.qc.enumeration.ExecuteState;
import com.cdqckj.gmis.iot.qc.vo.UpdateBalanceVO;
import com.cdqckj.gmis.iot.qc.vo.ValveControlVO;
import com.cdqckj.gmis.lot.*;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.PriceType;
import com.cdqckj.gmis.operateparam.service.PriceSchemeService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.operateparam.util.CalRoundUtil;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIotError;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ChargeIotEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotErrorService;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotService;
import com.cdqckj.gmis.readmeter.vo.SettlementArrearsVO;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.charges.enums.ChargeStatusEnum.UNCHARGE;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@Log4j2
@Service
@DS("#thread.tenant")
public class CalculateServiceIotImpl extends SuperCenterServiceImpl implements CalculateIotService {

    @Autowired
    private UseGasTypeService useGasTypeService;
    @Autowired
    private PriceSchemeService priceSchemeService;
    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    private ReadMeterDataApi readMeterBizApi;
    @Autowired
    private ReadMeterDataIotService readMeterDataIotService;
    @Autowired
    private ReadMeterDataIotErrorService readMeterDataIotErrorService;
    @Autowired
    private GasmeterArrearsDetailService gasmeterArrearsDetailService;
    @Autowired
    private GasmeterSettlementDetailService gasmeterSettlementDetailService;
    @Autowired
    private CustomerAccountSerialService customerAccountSerialService;
    @Autowired
    private CustomerAccountService customerAccountService;
    @Autowired
    private I18nUtil i18nUtil;
    @Autowired
    private GasMeterVersionService gasMeterVersionService;
    private final Integer num = 2;
    @Autowired
    private BusinessBizApi bussinessBizApi;
    @Autowired
    private IotAlarmBizApi iotAlarmBizApi;
    @Autowired
    private MeterFactoryCacheConfig meterFactoryCacheConfig;
    @Autowired
    private GasTypeChangeRecordService gasTypeChangeRecordService;
    @Autowired
    private GasMeterInfoSerialService gasMeterInfoSerialService;
    @Autowired
    private GasMeterInfoService gasMeterInfoService;
    @Autowired
    private MeterIotCacheUtil meterIotCacheUtil;
    @Autowired
    private IotGasMeterUploadDataBizApi iotGasMeterUploadDataBizApi;
    @Autowired
    private IotGasMeterCommandBizApi iotGasMeterCommandBizApi;
    @Autowired
    private IotGasMeterCommandDetailBizApi iotGasMeterCommandDetailBizApi;
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
    public R<List<ReadMeterDataIot>> settlement(List<ReadMeterDataIot> list,int type) {
        return settlementData(list,type,null);
    }

    /**
     * 预付费抵扣退费
     * @param list
     * @return
     */
    public R<List<ReadMeterDataIot>> unSettlement(List<ReadMeterDataIot> list) {
        List<Long> updateReadList = new ArrayList<>();
        List<GasmeterSettlementDetail> detailList = new ArrayList<>();
        list.stream().forEach(e->{
            //用户账户
            CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
            //气表使用情况
            GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
            //查询结算明细
            LbqWrapper<GasmeterSettlementDetail> gasmeterSettlementDetail =new LbqWrapper<>();
            gasmeterSettlementDetail.eq(GasmeterSettlementDetail::getReadmeterDataId,e.getId());
            gasmeterSettlementDetail.eq(GasmeterSettlementDetail::getDataStatus,1);
            gasmeterSettlementDetail.eq(GasmeterSettlementDetail::getReadmeterMonth,e.getReadMeterMonth().toString());
            List<GasmeterSettlementDetail> settlementDetails = gasmeterSettlementDetailService.list(gasmeterSettlementDetail);
            if(settlementDetails==null||settlementDetails.size()==0){return;}
            GasmeterSettlementDetail gasmeterSettle = settlementDetails.get(0);
            gasmeterSettle.setDataStatus(0);

            if(gasmeterSettle.getSettlementAfterMoney()!=null){
                CustomerAccountSerial customerAccountSerial = new CustomerAccountSerial();
                customerAccountSerial.setAccountCode(account.getAccountCode());
                customerAccountSerial.setGasMeterCode(e.getGasMeterCode());
                customerAccountSerial.setCustomerCode(e.getCustomerCode());
                customerAccountSerial.setOrgId(gasMeterInfo.getOrgId());
                customerAccountSerial.setOrgName(gasMeterInfo.getOrgName());
                customerAccountSerial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
                customerAccountSerial.setBillType(AccountSerialSceneEnum.DEDUCTION_REFUND.getCode());
                if(gasmeterSettle.getGiveBackAfterMoney()!=null){
                    if(gasmeterSettle.getGiveBookPreMoney()==null){gasmeterSettle.setGiveBookPreMoney(BigDecimal.ZERO);}
                    account.setGiveMoney(account.getGiveMoney().add(gasmeterSettle.getGiveBookPreMoney()
                            .subtract(gasmeterSettle.getGiveBackAfterMoney())));
                    customerAccountSerial.setGiveBookPreMoney(gasmeterSettle.getGiveBookPreMoney());
                    customerAccountSerial.setGiveBookAfterMoney(gasmeterSettle.getGiveBackAfterMoney());
                    customerAccountSerial.setGiveBookMoney(gasmeterSettle.getGiveBookPreMoney()
                            .subtract(gasmeterSettle.getGiveBackAfterMoney()));
                }
                if(gasmeterSettle.getSettlementAfterMoney()!=null){
                    if(gasmeterSettle.getSettlementPreMoney()==null){
                        gasmeterSettle.setSettlementPreMoney(BigDecimal.ZERO);
                    }
                    account.setAccountMoney(account.getAccountMoney().add(gasmeterSettle.getSettlementPreMoney()
                            .subtract(gasmeterSettle.getSettlementAfterMoney())));
                    customerAccountSerial.setBookPreMoney(gasmeterSettle.getSettlementPreMoney());
                    customerAccountSerial.setBookAfterMoney(gasmeterSettle.getSettlementAfterMoney());
                    customerAccountSerial.setBookMoney(gasmeterSettle.getSettlementPreMoney()
                            .subtract(gasmeterSettle.getSettlementAfterMoney()));
                }
                //新增账户流水
                customerAccountSerialService.save(customerAccountSerial);
                //账户更新
                customerAccountService.updateById(account);
            }

            //户表余额退费
            if(gasmeterSettle.getSettlementMeterAfterMoney()!=null){
                if(gasmeterSettle.getSettlementMeterPreMoney()==null){
                    gasmeterSettle.setSettlementMeterPreMoney(BigDecimal.ZERO);
                }
                //进行了户表账户抵扣，钱原路返回
                GasMeterInfoSerial meterInfoSerial= new GasMeterInfoSerial();
                meterInfoSerial.setGasMeterCode(e.getGasMeterCode());
                meterInfoSerial.setCustomerCode(e.getCustomerCode());
                meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
                meterInfoSerial.setOrgId(gasMeterInfo.getOrgId());
                meterInfoSerial.setOrgName(gasMeterInfo.getOrgName());
                meterInfoSerial.setSerialNo(BizCodeNewUtil.getGasMeterInfoSerialCode(BizSCode.CHARGE));
                meterInfoSerial.setBillType(MeterAccountSerialSceneEnum.DEDUCTION_REFUND.getCode());

                //户表赠送抵扣退费
                if(gasmeterSettle.getGiveMeterAfterMoney()!=null){
                    if(gasmeterSettle.getGiveMeterPreMoney()==null){gasmeterSettle.setGiveMeterPreMoney(BigDecimal.ZERO);}
                    gasMeterInfo.setGasMeterGive(gasMeterInfo.getGasMeterGive().add(gasmeterSettle.getGiveMeterPreMoney().subtract(gasmeterSettle.getGiveMeterAfterMoney())));
                    meterInfoSerial.setGiveBookPreMoney(gasmeterSettle.getGiveMeterPreMoney());
                    meterInfoSerial.setGiveBookAfterMoney(gasmeterSettle.getGiveMeterAfterMoney());
                    meterInfoSerial.setGiveBookMoney(gasmeterSettle.getGiveMeterPreMoney().subtract(gasmeterSettle.getGiveMeterAfterMoney()));
                }

                gasMeterInfo.setGasMeterBalance(gasMeterInfo.getGasMeterBalance().add(gasmeterSettle.getSettlementMeterPreMoney()
                        .subtract(gasmeterSettle.getSettlementMeterAfterMoney())));
                meterInfoSerial.setBookPreMoney(gasmeterSettle.getSettlementMeterAfterMoney());
                meterInfoSerial.setBookAfterMoney(gasmeterSettle.getSettlementMeterPreMoney());
                meterInfoSerial.setBookMoney(gasmeterSettle.getSettlementMeterPreMoney().subtract(gasmeterSettle.getSettlementMeterAfterMoney()));
                //新增户表账户流水
                gasMeterInfoSerialService.save(meterInfoSerial);
            }

            e.setSettlementTime(null);
            e.setSettlementUserId(null);
            e.setSettlementUserName(null);
            e.setChargeStatus(null);
            e.setProcessStatus(ProcessIotEnum.APPROVED);

            updateReadList.add(e.getId());

            //更新结算明细
            detailList.add(gasmeterSettle);

            //气表使用情况更新
            ReadMeterDataIot readMeterData = readMeterDataIotService.getPreviousData(e.getGasMeterCode(), e.getGasMeterNumber(),e.getCustomerCode(),e.getDataTime());
            if(readMeterData!=null){
                gasMeterInfo.setCycleUseGas(readMeterData.getCycleTotalUseGas());
            }else{
                gasMeterInfo.setCycleUseGas(e.getLastCycleTotalUseGas());
            }
            gasMeterInfo.setTotalUseGas(gasMeterInfo.getTotalUseGas().subtract(e.getMonthUseGas()));
            gasMeterInfo.setTotalUseGasMoney(gasMeterInfo.getTotalUseGasMoney().subtract(gasmeterSettle.getGasMoney()));
            gasMeterInfoService.updateById(gasMeterInfo);
        });
        if(updateReadList.size()>0){
            readMeterDataIotService.updateDataRefundIot(updateReadList);
        }
        if(detailList.size()>0){
            gasmeterSettlementDetailService.updateBatchById(detailList);
        }
        return R.success(list);
    }

    /**
     * 物联网表后付费退费
     * @param list
     * @return
     */
    public R<List<ReadMeterDataIot>> unSettlementEX(List<ReadMeterDataIot> list) {
        List<Long> updateReadList = new ArrayList<>();
        List<GasmeterSettlementDetail> detailList = new ArrayList<>();
        List<GasmeterArrearsDetail> arrearsDetails = new ArrayList<>();

        Map<String, List<ReadMeterDataIot>> listMeterNo = list.stream().
                collect(Collectors.groupingBy(ReadMeterDataIot::getGasMeterCode));
        listMeterNo.forEach((k,v)->{
            GasMeter gasMeter = gasMeterService.findGasMeterByCode(k);
            GasMeterVersion version = gasMeterVersionService.getById(gasMeter.getGasMeterVersionId());
            if(OrderSourceNameEnum.REMOTE_READMETER.equals(version.getOrderSourceName())){
               //物联网中心计费后付费
                LbqWrapper<ReadMeterDataIot> params = new LbqWrapper<>();
                params.eq(ReadMeterDataIot::getGasMeterCode,k);
                params.eq(ReadMeterDataIot::getGasOweCode,v.get(0).getGasOweCode());
                List<ReadMeterDataIot> iotList = readMeterDataIotService.list(params);
                if(v.size()!=iotList.size()){throw new BizException("请选择物联网后付费结算编号为:"+v.get(0).getGasOweCode()+"的数据进行退费");}
                LbqWrapper<GasmeterArrearsDetail> gadQuery =new LbqWrapper<>();
                gadQuery.eq(GasmeterArrearsDetail::getArrearsStatus,ChargeStatusEnum.CHARGED.getCode());
                gadQuery.eq(GasmeterArrearsDetail::getDataStatus,DataStatusEnum.NORMAL.getValue());
                if(!StringUtil.isNullOrBlank(k)){
                    gadQuery.eq(GasmeterArrearsDetail::getGasmeterCode,k);
                }
                gadQuery.eq(GasmeterArrearsDetail::getSettlementNo,v.get(0).getGasOweCode());
                List<GasmeterArrearsDetail> gasmeterArrearsDetails = gasmeterArrearsDetailService.list(gadQuery);
                if(gasmeterArrearsDetails!=null&&gasmeterArrearsDetails.size()>0){throw new BizException("存在欠费已交情况，请在柜台操作");}

                LbqWrapper<GasmeterArrearsDetail> gadQueryEX =new LbqWrapper<>();
                gadQueryEX.eq(GasmeterArrearsDetail::getDataStatus,DataStatusEnum.NORMAL.getValue());
                if(!StringUtil.isNullOrBlank(k)){
                    gadQueryEX.eq(GasmeterArrearsDetail::getGasmeterCode,k);
                }
                gadQueryEX.eq(GasmeterArrearsDetail::getSettlementNo,v.get(0).getGasOweCode());
                gadQueryEX.eq(GasmeterArrearsDetail::getArrearsStatus,ChargeStatusEnum.UNCHARGE.getCode());
                List<GasmeterArrearsDetail> gasmeterArrearsDetailUnChange = gasmeterArrearsDetailService.list(gadQueryEX);
                if(gasmeterArrearsDetailUnChange!=null&&gasmeterArrearsDetailUnChange.size()>0){
                    GasmeterArrearsDetail gasmeterArrearsDetail = gasmeterArrearsDetailUnChange.get(0);
                    gasmeterArrearsDetail.setDataStatus(0);
                    arrearsDetails.add(gasmeterArrearsDetail);
                }
                //用户账户
                CustomerAccount account = customerAccountService.queryAccountByCharge(v.get(0).getCustomerCode());
                //气表使用情况
                GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(k,v.get(0).getCustomerCode());
                //查询账户抵扣明细记录
                LbqWrapper<CustomerAccountSerial> serialLbqWrapper = new LbqWrapper<>();
                serialLbqWrapper.eq(CustomerAccountSerial::getBillNo,v.get(0).getGasOweCode());
                serialLbqWrapper.eq(CustomerAccountSerial::getBillType,AccountSerialSceneEnum.DEDUCTION.getCode());
                CustomerAccountSerial accountSerial = customerAccountSerialService.getOne(serialLbqWrapper);
                if(null!=accountSerial){
                    //进行了账户抵扣，钱原路返回
                    CustomerAccountSerial customerAccountSerial = new CustomerAccountSerial();
                    customerAccountSerial.setAccountCode(account.getAccountCode());
                    customerAccountSerial.setGasMeterCode(v.get(0).getGasMeterCode());
                    customerAccountSerial.setCustomerCode(v.get(0).getCustomerCode());
                    customerAccountSerial.setOrgId(gasMeterInfo.getOrgId());
                    customerAccountSerial.setOrgName(gasMeterInfo.getOrgName());
                    customerAccountSerial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
                    customerAccountSerial.setBillType(AccountSerialSceneEnum.DEDUCTION_REFUND.getCode());

                    if(accountSerial.getGiveBookAfterMoney()!=null&&accountSerial.getGiveBookPreMoney()!=null){
                        account.setGiveMoney(account.getGiveMoney().add(accountSerial.getGiveBookPreMoney()
                                .subtract(accountSerial.getGiveBookAfterMoney())));
                        customerAccountSerial.setGiveBookPreMoney(accountSerial.getGiveBookPreMoney());
                        customerAccountSerial.setGiveBookAfterMoney(accountSerial.getGiveBookAfterMoney());
                        customerAccountSerial.setGiveBookMoney(accountSerial.getGiveBookPreMoney().subtract(accountSerial.getGiveBookAfterMoney()));
                    }
                    if(accountSerial.getBookAfterMoney()!=null&&accountSerial.getBookPreMoney()!=null){
                        account.setAccountMoney(account.getAccountMoney().add(accountSerial.getBookPreMoney()
                                .subtract(accountSerial.getBookAfterMoney())));
                        customerAccountSerial.setBookPreMoney(accountSerial.getBookPreMoney());
                        customerAccountSerial.setBookAfterMoney(accountSerial.getBookAfterMoney());
                        customerAccountSerial.setBookMoney(accountSerial.getBookPreMoney().subtract(accountSerial.getBookAfterMoney()));
                    }
                    //新增账户流水
                    customerAccountSerialService.save(customerAccountSerial);
                }
                //查询表账户抵扣明细记录
                LbqWrapper<GasMeterInfoSerial> meterParams = new LbqWrapper<>();
                meterParams.eq(GasMeterInfoSerial::getBillType,v.get(0).getGasOweCode());
                meterParams.eq(GasMeterInfoSerial::getBillType,AccountSerialSceneEnum.DEDUCTION.getCode());
                GasMeterInfoSerial gasMeterInfoSerial = gasMeterInfoSerialService.getOne(meterParams);
                if(null!=gasMeterInfoSerial){
                    //进行了户表账户抵扣，钱原路返回
                    GasMeterInfoSerial meterInfoSerial= new GasMeterInfoSerial();
                    meterInfoSerial.setGasMeterCode(v.get(0).getGasMeterCode());
                    meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
                    meterInfoSerial.setOrgId(gasMeterInfo.getOrgId());
                    meterInfoSerial.setOrgName(gasMeterInfo.getOrgName());
                    meterInfoSerial.setBillType(MeterAccountSerialSceneEnum.DEDUCTION_REFUND.getCode());
                    if(gasMeterInfoSerial.getGiveBookAfterMoney()!=null&&gasMeterInfoSerial.getGiveBookPreMoney()!=null){
                        gasMeterInfo.setGasMeterGive(gasMeterInfo.getGasMeterGive().add(gasMeterInfoSerial.getGiveBookPreMoney()
                                .subtract(gasMeterInfoSerial.getGiveBookAfterMoney())));
                        meterInfoSerial.setGiveBookPreMoney(gasMeterInfoSerial.getGiveBookPreMoney());
                        meterInfoSerial.setGiveBookAfterMoney(gasMeterInfoSerial.getGiveBookAfterMoney());
                        meterInfoSerial.setGiveBookMoney(gasMeterInfoSerial.getGiveBookPreMoney().subtract(gasMeterInfoSerial.getGiveBookAfterMoney()));
                    }
                    if(gasMeterInfoSerial.getBookAfterMoney()!=null&&gasMeterInfoSerial.getBookPreMoney()!=null){
                        gasMeterInfo.setGasMeterBalance(gasMeterInfo.getGasMeterBalance().add(gasMeterInfoSerial.getBookPreMoney()
                                .subtract(gasMeterInfoSerial.getBookAfterMoney())));
                        meterInfoSerial.setBookPreMoney(gasMeterInfoSerial.getBookPreMoney());
                        meterInfoSerial.setBookAfterMoney(gasMeterInfoSerial.getBookAfterMoney());
                        meterInfoSerial.setBookMoney(gasMeterInfoSerial.getBookPreMoney().subtract(gasMeterInfoSerial.getBookAfterMoney()));
                    }
                    //新增户表账户流水
                    gasMeterInfoSerialService.save(meterInfoSerial);
                }
                for(int i=0;i<iotList.size();i++){
                    iotList.get(i).setSettlementTime(null);
                    iotList.get(i).setSettlementUserId(null);
                    iotList.get(i).setSettlementUserName(null);
                    iotList.get(i).setChargeStatus(null);
                    iotList.get(i).setProcessStatus(ProcessIotEnum.TO_BE_REVIEWED);
                    iotList.get(i).setCycleTotalUseGas(null);
                }
                //账户更新
                customerAccountService.updateById(account);
                //更新户表账户
                gasMeterInfoService.updateById(gasMeterInfo);
                if(iotList.size()>0){
                    readMeterDataIotService.updateBatchById(iotList);
                }
                if(arrearsDetails.size()>0){
                    gasmeterArrearsDetailService.updateBatchById(arrearsDetails);
                }
            }else{
               unSettlement(v);
            }
        });
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
                               GasMeter gasMeter,PriceScheme price,CustomerAccount account,ReadMeterDataIot e,
                               UseGasType useGasType,GasMeterVersion gasMeterVersion,int type,boolean isCycleClear,ReadMeterDataIot lastRecordDate){
        //确认价格方案
        CalculateVO calculateVO = calculateCondition(priceSchemeVO,gasMeter,gasMeterInfo,e,useGasType,isCycleClear);
        //计算燃气费用
        SettlementVO settlementVO = calculateLadderGasCost(calculateVO).getData();
        e.setRelPriceId(priceSchemeVO.getId());
        e.setRelUseGasTypeId(useGasType.getId());
        gasMeterInfo.setCurrentPrice(settlementVO.getCurrentPrice());
        //是否后付费
        if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersion.getOrderSourceName())){
            //更新表数据
            updateCalculateIot(e,gasMeterInfo,price,useGasType,account,gasMeter,settlementVO,calculateVO);
        }else{
            //更新抄表数据、气表使用情况、新增结算数据
            GasmeterSettlementDetail gSDetail = updateCalculate(e,gasMeterInfo,price,useGasType,account,
                    gasMeter,settlementVO,calculateVO).getData();
            //账户抵扣、欠费记录
            accountWithhold(account,e,settlementVO,gSDetail,gasMeterInfo,gasMeter,useGasType,price,gasMeterVersion,lastRecordDate);
        }

    }

    /**
     * 预付费账户抵扣
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
    public R<CustomerAccount> accountWithhold(CustomerAccount account,ReadMeterDataIot meterDataVO,SettlementVO settlementVO,
                                         GasmeterSettlementDetail gasmeterSettlementDetail,GasMeterInfo gasMeterInfo,GasMeter gasMeter,
                                        UseGasType useGasType,PriceScheme priceScheme,GasMeterVersion gasMeterVersion,ReadMeterDataIot lastRecordDate) {
        BigDecimal initParam = new BigDecimal("0.0000");
        CustomerAccount accountPre = new CustomerAccount();
        BeanUtils.copyProperties(account,accountPre);

        GasMeterInfo infoPre = new GasMeterInfo();
        BeanUtils.copyProperties(gasMeterInfo,infoPre);
        //物联网中心计费预付费表抵扣
        settlementVO = accountWithIotHold(settlementVO, gasMeterInfo,gasMeterVersion,gasmeterSettlementDetail,
                account,meterDataVO);
        GasMeterInfo gasAccountPre = new GasMeterInfo();
        BeanUtils.copyProperties(gasMeterInfo,gasAccountPre);
        //判断欠费金额是否结清，如果结清没必要进行账户抵扣了
        if(settlementVO.getMoney().compareTo(BigDecimal.ZERO)==0){
            BigDecimal alarmMoney = BigDecimal.ZERO;
            //判断是否处于余额报警
            if(gasMeterInfo.getGasMeterBalance()!=null&&useGasType.getAlarmMoneyTwo()!=null&&useGasType.getAlarmMoney()!=null
                    &&gasMeterInfo.getGasMeterBalance().compareTo(useGasType.getAlarmMoneyTwo())<=0
                    &&gasMeterInfo.getGasMeterBalance().compareTo(useGasType.getAlarmMoney())>=0){
                //二级余额不足
                IotAlarmSaveDTO iotAlarm = new IotAlarmSaveDTO();
                iotAlarm.setDeviceId(meterDataVO.getGasMeterNumber());
                iotAlarm.setAlarmType(AlramType.InsufficientBalance2.getCode());
                iotAlarm.setAlarmContent(AlramType.InsufficientBalance2.getDesc());
                iotAlarmBizApi.save(iotAlarm);
                alarmMoney = useGasType.getAlarmMoneyTwo();
                //下发余额不足指令
            }else if(gasMeterInfo.getGasMeterBalance()!=null&&useGasType.getAlarmMoney()!=null
                    &&gasMeterInfo.getGasMeterBalance().compareTo(useGasType.getAlarmMoney())<=0
                    &&gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)>=0
                    &&infoPre.getGasMeterBalance().compareTo(useGasType.getAlarmMoney())>=0){
                //一级余额不足报警
                IotAlarmSaveDTO iotAlarm = new IotAlarmSaveDTO();
                iotAlarm.setDeviceId(meterDataVO.getGasMeterNumber());
                iotAlarm.setAlarmType(AlramType.InsufficientBalance1.getCode());
                iotAlarm.setAlarmContent(AlramType.InsufficientBalance1.getDesc());
                iotAlarmBizApi.save(iotAlarm);
                alarmMoney =useGasType.getAlarmMoney();
                //发送普通关阀指令
                if(meterDataVO.getId()==lastRecordDate.getId()){
                    valveOperate(gasMeter.getGasMeterNumber(),"00");
                }
            }
            if(meterDataVO.getId()==lastRecordDate.getId()){
                updateBalance(meterDataVO.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice(),alarmMoney);
            }
            meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
            updateReadMeterDataIot(meterDataVO);
            return R.success(account);
        }
        //客户账户抵扣
        if(account.getAccountMoney()==null||(account.getAccountMoney().compareTo(BigDecimal.ZERO)==0
                &&(account.getGiveMoney()==null||account.getGiveMoney().compareTo(BigDecimal.ZERO)==0))){
            //是否物联网表中心计费预付费
            if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
                if(gasMeterInfo.getGasMeterBalance()!=null){
                    gasMeterInfo.setGasMeterBalance(gasMeterInfo.getGasMeterBalance().add(settlementVO.getMoney().negate()));
                }else{
                    gasMeterInfo.setGasMeterBalance(settlementVO.getMoney().negate());
                }
                //更新账户
                updateMeterAccount(settlementVO.getMoney(),gasmeterSettlementDetail,gasMeterInfo,infoPre);
                if(gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)<0){
                    //新增流水
                    updateMeterAccountEX(gasMeterInfo.getGasMeterBalance(),meterDataVO,gasMeterInfo,gasAccountPre,gasMeter.getGasCode());
                }
                //更新抄表数据
                updateReadMeterDataIot(meterDataVO);
                // TODO 下发物联网表指令更新表端余额 和 关阀指令
                if(meterDataVO.getId()==lastRecordDate.getId()){
                    updateBalance(gasMeter.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice(),null);
                    valveOperate(gasMeter.getGasMeterNumber(),"01");
                }
            }
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
                    //更新抄表状态
                    meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
                    //是否物联网表中心计费预付费
                    if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
                        if(gasMeterInfo.getGasMeterBalance()!=null){
                            gasMeterInfo.setGasMeterBalance(gasMeterInfo.getGasMeterBalance().add(acMoney.negate()));
                        }else{
                            gasMeterInfo.setGasMeterBalance(acMoney.negate());
                        }
                        if(gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)<0){
                            //新增流水
                            updateMeterAccountEX(gasMeterInfo.getGasMeterBalance(),meterDataVO,
                                    gasMeterInfo,gasAccountPre,gasMeter.getGasCode());
                        }
                        // TODO 下发物联网表指令更新表端余额
                        if(meterDataVO.getId()==lastRecordDate.getId()){
                            updateBalance(gasMeter.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice(),null);
                            valveOperate(gasMeter.getGasMeterNumber(),"01");
                        }
                    }
                    updateGasMeterInfo(gasMeterInfo);
                    //更新账户、结算明细、新增账户流水
                    updateSettlementAccount(acMoney,gasmeterSettlementDetail,account,accountPre,gasMeterInfo.getGasmeterCode(),gasMeterInfo,infoPre);
                }else{
                    //账户余额不足，赠送足够的情况
                    BigDecimal acMoney = account.getGiveMoney().subtract(rtMoney);
                    account.setAccountMoney(initParam);
                    account.setGiveMoney(acMoney);
                    //更新抄表状态
                    meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
                    //是否物联网表
                    updateReadMeterDataIot(meterDataVO);
                    //更新表账户
                    gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                    updateGasMeterInfo(gasMeterInfo);
                    //下发更新余额指令
                    if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
                        // TODO 下发物联网表指令更新表端余额
                        if(meterDataVO.getId()==lastRecordDate.getId()){
                            updateBalance(gasMeter.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice(),null);
                        }
                    }
                    //更新账户、结算明细、新增账户流水
                    updateSettlementAccount(initParam,gasmeterSettlementDetail,account,accountPre,gasMeterInfo.getGasmeterCode(),gasMeterInfo,infoPre);
                }
            }else{
                //已欠费
                account.setAccountMoney(initParam);
                //是否物联网表中心计费预付费
                if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
                    if(gasMeterInfo.getGasMeterBalance()!=null){
                        gasMeterInfo.setGasMeterBalance(gasMeterInfo.getGasMeterBalance().add(rtMoney.negate()));
                    }else{
                        gasMeterInfo.setGasMeterBalance(rtMoney.negate());
                    }
                    updateGasMeterInfo(gasMeterInfo);
                    if(gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)<0){
                        //新增流水
                        updateMeterAccountEX(gasMeterInfo.getGasMeterBalance(),meterDataVO,
                                gasMeterInfo,gasAccountPre,gasMeter.getGasCode());
                    }
                    // TODO 下发物联网表指令更新表端余额
                    if(meterDataVO.getId()==lastRecordDate.getId()){
                        updateBalance(gasMeter.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice(),null);
                        valveOperate(gasMeter.getGasMeterNumber(),"01");
                    }
                }
                //更新账户、结算明细、新增账户流水
                updateSettlementAccount(rtMoney,gasmeterSettlementDetail,account,accountPre,gasMeterInfo.getGasmeterCode(),gasMeterInfo,infoPre);
            }
        }else{
            //未欠费
            BigDecimal rtMoney = account.getAccountMoney().subtract(settlementVO.getMoney());
            account.setAccountMoney(rtMoney);
            //更新抄表状态
            meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
            //是否物联网表
            updateReadMeterDataIot(meterDataVO);
            //更新表账户
            gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
            updateGasMeterInfo(gasMeterInfo);
            //下发更新余额指令
            if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
                if(meterDataVO.getId()==lastRecordDate.getId()){
                    updateBalance(gasMeter.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice(),null);
                }
            }
            //更新账户、结算明细、新增账户流水
            updateSettlementAccount(initParam,gasmeterSettlementDetail,account,accountPre,gasMeterInfo.getGasmeterCode(),gasMeterInfo,infoPre);
        }
        return R.success(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public R<BigDecimal> accountWithholdEX(CustomerAccount account,GasMeterInfo gasMeterInfo,ReadMeterDataIot meterDataVO,BigDecimal arrearMoney,
                                              BigDecimal useGas) {
        BigDecimal initParam = new BigDecimal("0.0000");
        CustomerAccount accountPre = new CustomerAccount();
        BeanUtils.copyProperties(account,accountPre);
        arrearMoney = accountWithIotHoldEX(arrearMoney, gasMeterInfo,
                account,meterDataVO);
        //更新账户
        if(meterDataVO.getDataType()==0){
            updateGasMeterInfo(gasMeterInfo);
        }
        //判断欠费金额是否结清，如果结清没必要进行账户抵扣了
        if(arrearMoney.compareTo(BigDecimal.ZERO)==0){
            meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
            return R.success(arrearMoney);
        }
        //客户账户抵扣
        if(account.getAccountMoney()==null||
                (account.getAccountMoney().compareTo(BigDecimal.ZERO)==0&&(account.getGiveMoney()==null||account.getGiveMoney().compareTo(BigDecimal.ZERO)==0))){
            return R.success(arrearMoney);
        }
        //账户余额小于计费后的金额
        if(account.getAccountMoney().compareTo(arrearMoney)<0){
            BigDecimal rtMoney = arrearMoney.subtract(account.getAccountMoney());
            //判断账户是否有赠送金额
            if(account.getGiveMoney()!=null){
                //判断赠送金额是否小于账户抵扣后的欠费金额
                if(account.getGiveMoney().compareTo(rtMoney)<0){
                    BigDecimal acMoney = rtMoney.subtract(account.getGiveMoney());
                    account.setAccountMoney(initParam);
                    account.setGiveMoney(initParam);
                    arrearMoney = acMoney;
                    //更新账户、新增账户流水
                    updateSettlementAccountEX(acMoney,meterDataVO,account,accountPre,gasMeterInfo.getGasmeterCode());
                }else{
                    //账户余额不足，赠送足够的情况
                    BigDecimal acMoney = account.getGiveMoney().subtract(rtMoney);
                    account.setAccountMoney(initParam);
                    account.setGiveMoney(acMoney);
                    //更新账户、新增账户流水
                    updateSettlementAccountEX(initParam,meterDataVO,account,accountPre,gasMeterInfo.getGasmeterCode());
                }
            }else{
                arrearMoney = rtMoney;
                //已欠费新增欠费记录
                account.setAccountMoney(initParam);
                //更新账户、结算明细、新增账户流水
                updateSettlementAccountEX(rtMoney,meterDataVO,account,accountPre,gasMeterInfo.getGasmeterCode());
                //是否物联网表中心计费预付费
            }
        }else{
            //未欠费
            BigDecimal rtMoney = account.getAccountMoney().subtract(arrearMoney);
            account.setAccountMoney(rtMoney);
            //更新账户、结算明细、新增账户流水
            updateSettlementAccountEX(initParam,meterDataVO,account,accountPre,gasMeterInfo.getGasmeterCode());
        }
        return R.success(arrearMoney);
    }

    /**
     * 物联网抵扣
     * @param settlementVO 结算后金额
     * @param gasMeterInfo 气表使用情况
     * @param gasMeterVersion 气表版号
     * @param gasmeterSettlementDetail 结算前疾苦
     * @param account 账户
     * @param meterDataVO 抄表数据
     * @return
     */
    public SettlementVO accountWithIotHold(SettlementVO settlementVO, GasMeterInfo gasMeterInfo,
                                           GasMeterVersion gasMeterVersion, GasmeterSettlementDetail gasmeterSettlementDetail,
                                           CustomerAccount account,ReadMeterDataIot meterDataVO){
        //是物联网中心计费且账户余额大于0
        if(gasMeterInfo.getGasMeterBalance()!=null
                &&gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)>0
                ||(gasMeterInfo.getGasMeterBalance()!=null&&gasMeterInfo.getGasMeterGive()!=null
                &&gasMeterInfo.getGasMeterGive().compareTo(BigDecimal.ZERO)>0)&&settlementVO.getMoney().compareTo(BigDecimal.ZERO)>0){
            GasMeterInfo accountPre = new GasMeterInfo();
            BeanUtils.copyProperties(gasMeterInfo,accountPre);
            meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
            //账户余额是否大于等于结算金额
            if(gasMeterInfo.getGasMeterBalance().compareTo(settlementVO.getMoney())>=0){
                //抵扣表账户余额
                gasMeterInfo.setGasMeterBalance((gasMeterInfo.getGasMeterBalance()
                        .subtract(settlementVO.getMoney())));
                /**新增账户抵扣流水**/
                updateMeterAccountEX(BigDecimal.ZERO,meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                //更新结算后金额
                //设置欠费
                settlementVO.setMoney(BigDecimal.ZERO);
                //更新账户、结算明细
                updateMeterAccount(BigDecimal.ZERO,gasmeterSettlementDetail,gasMeterInfo,accountPre);
                //中心计费预付费表下发更新余额指令
//                if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
//                    updateBalance(meterDataVO.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),settlementVO.getCurrentPrice());
//                }
            }else{
                BigDecimal dkMoney = (settlementVO.getMoney()
                        .subtract(gasMeterInfo.getGasMeterBalance()));
                //账户是否有赠送
                if(gasMeterInfo.getGasMeterGive()!=null){
                    //判断账户赠送金额是否大于抵扣后的金额
                    if(gasMeterInfo.getGasMeterGive().compareTo(dkMoney)>=0){
                        //抵扣表账户余额
                        gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                        gasMeterInfo.setGasMeterGive(gasMeterInfo.getGasMeterGive().subtract(dkMoney));
                        //更新结算后金额
                        //设置欠费
                        settlementVO.setMoney(BigDecimal.ZERO);
                        //更新账户、结算明细
                        updateMeterAccount(BigDecimal.ZERO,gasmeterSettlementDetail,gasMeterInfo,accountPre);
                        /**新增账户抵扣流水**/
                        updateMeterAccountEX(BigDecimal.ZERO,meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
//                        //中心计费预付费表下发更新余额指令
//                        if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeterVersion.getOrderSourceName())){
//                            updateBalance(meterDataVO.getGasMeterNumber(),BigDecimal.ZERO,settlementVO.getCurrentPrice());
//                        }
                    }else{
                        //抵扣表账户余额
                        gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                        gasMeterInfo.setGasMeterGive(BigDecimal.ZERO);
                        //更新结算后金额
                        //设置欠费
                        settlementVO.setMoney(dkMoney.subtract(gasMeterInfo.getGasMeterGive()));
                        //更新账户、结算明细
                        updateMeterAccount(settlementVO.getMoney(),gasmeterSettlementDetail,gasMeterInfo,accountPre);
                        /**新增账户抵扣流水**/
                        updateMeterAccountEX(settlementVO.getMoney(),meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                    }
                }else{
                    //设置欠费
                    settlementVO.setMoney(dkMoney);
                    //账户余额小于结算金额,先抵扣账户
                    gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                    //更新结算后金额
                    gasmeterSettlementDetail.setSettlementMeterAfterMoney(BigDecimal.ZERO);
                    //更新账户、结算明细
                    updateMeterAccount(settlementVO.getMoney(),gasmeterSettlementDetail,gasMeterInfo,accountPre);
                    /**新增账户抵扣流水**/
                    updateMeterAccountEX(settlementVO.getMoney(),meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                }
            }
        }else{
            meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
        }
        return settlementVO;
    }


    public BigDecimal accountWithIotHoldEX(BigDecimal arrerMoney, GasMeterInfo gasMeterInfo,
                                           CustomerAccount account,ReadMeterDataIot meterDataVO){
        //是物联网中心计费且账户余额大于0
        if(gasMeterInfo.getGasMeterBalance()!=null
                &&gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)>0
                ||(gasMeterInfo.getGasMeterBalance()!=null&&gasMeterInfo.getGasMeterGive()!=null
                &&gasMeterInfo.getGasMeterGive().compareTo(BigDecimal.ZERO)>0)){
            meterDataVO.setChargeStatus(ChargeIotEnum.NO_CHARGE);
            GasMeterInfo accountPre = new GasMeterInfo();
            BeanUtils.copyProperties(gasMeterInfo,accountPre);
            //账户余额是否大于等于结算金额
            if(gasMeterInfo.getGasMeterBalance().compareTo(arrerMoney)>=0){
                //抵扣表账户余额
                gasMeterInfo.setGasMeterBalance((gasMeterInfo.getGasMeterBalance().subtract(arrerMoney)));
                //设置欠费
                arrerMoney = BigDecimal.ZERO;
                /**新增账户抵扣流水**/
                updateMeterAccountEX(arrerMoney,meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                //更新抄表状态
                meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
            }else{
                BigDecimal dkMoney = (arrerMoney
                        .subtract(gasMeterInfo.getGasMeterBalance()));
                //账户是否有赠送
                if(gasMeterInfo.getGasMeterGive()!=null){
                    //判断账户赠送金额是否大于抵扣后的金额
                    if(gasMeterInfo.getGasMeterGive().compareTo(dkMoney)>=0){
                        //抵扣表账户余额
                        gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                        gasMeterInfo.setGasMeterGive(gasMeterInfo.getGasMeterGive().subtract(dkMoney));
                        //设置欠费
                        arrerMoney = BigDecimal.ZERO;
                        /**新增账户抵扣流水**/
                        updateMeterAccountEX(arrerMoney,meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                        //更新抄表状态
                        meterDataVO.setChargeStatus(ChargeIotEnum.CHARGED);
                    }else{
                        //抵扣表账户余额
                        gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                        gasMeterInfo.setGasMeterGive(BigDecimal.ZERO);
                        //设置欠费
                        arrerMoney = dkMoney.subtract(gasMeterInfo.getGasMeterGive());
                        /**新增账户抵扣流水**/
                        updateMeterAccountEX(arrerMoney,meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                        //更新抄表状态
                        meterDataVO.setChargeStatus(ChargeIotEnum.NO_CHARGE);
                    }
                }else{
                    //设置欠费
                    arrerMoney = dkMoney;
                    //账户余额小于结算金额,先抵扣账户
                    gasMeterInfo.setGasMeterBalance(BigDecimal.ZERO);
                    /**新增账户抵扣流水**/
                    updateMeterAccountEX(arrerMoney,meterDataVO,gasMeterInfo,accountPre,meterDataVO.getCustomerCode());
                    //更新抄表状态
                    meterDataVO.setChargeStatus(ChargeIotEnum.NO_CHARGE);
                }
            }
        }else{
            meterDataVO.setChargeStatus(ChargeIotEnum.NO_CHARGE);
        }
        return arrerMoney;
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
                    initParams,initParams,initParams,initParams,initParams,p1,1);
        }else if(hcGas.compareTo(g2)<=0||(g2.compareTo(maxParams)>=0&&g3.compareTo(initParams)==0)) {
            BigDecimal moneyG2 = (hcGas.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG2.add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,hcGas.subtract(g1),initParams,initParams,initParams,
                    initParams,initParams,initParams,initParams,initParams,p2,2);
        }else if(hcGas.compareTo(g3)<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)) {
            BigDecimal moneyG3 = (hcGas.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG3.add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(hcGas.subtract(g2)),initParams,
                    initParams,initParams,initParams,initParams,initParams,p3,3);
        }else if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            BigDecimal moneyG4 = (hcGas.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG4.add(moneyG3).add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(g3.subtract(g2)),p4,
                    (hcGas.subtract(g3)),initParams,initParams,initParams,initParams,p4,4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4).add(moneyG3).add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(hcGas.subtract(g4)),initParams,initParams,p5,5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(g1)).multiply(p2);
            BigDecimal moneyG1 = (g1.subtract(hGas)).multiply(p1);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4).add(moneyG3).add(moneyG2).add(moneyG1),num);
            settlementDetail(settlementVO,money,p1,(g1.subtract(hGas)),p2,(g2.subtract(g1)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6,6);
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
                    initParams,initParams,initParams,initParams,initParams,p2,2);
        }else if(hcGas.compareTo(g3)<=0||(g3.compareTo(maxParams)>=0&&g4.compareTo(initParams)==0)) {
            BigDecimal moneyG3 = (hcGas.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG3.add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(hcGas.subtract(g2)),initParams,
                    initParams,initParams,initParams,initParams,initParams,p3,3);
        }else if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            BigDecimal moneyG4 = (hcGas.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG4.add(moneyG3).add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(g3.subtract(g2)),p4,
                    (hcGas.subtract(g3)),initParams,initParams,initParams,initParams,p4,4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4).add(moneyG3).add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(hcGas.subtract(g4)),initParams,initParams,p5,5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(g2)).multiply(p3);
            BigDecimal moneyG2 = (g2.subtract(hGas)).multiply(p2);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4).add(moneyG3).add(moneyG2),num);
            settlementDetail(settlementVO,money,initParams,initParams,p2,(g2.subtract(hGas)),p3,(g3.subtract(g2)),p4,
                    (g4.subtract(g3)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6,6);
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
                    initParams,initParams,initParams,initParams,initParams,p3,3);
        }else if(hcGas.compareTo(g4)<=0||(g4.compareTo(maxParams)>=0&&g5.compareTo(initParams)==0)) {
            BigDecimal moneyG4 = (hcGas.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(hGas)).multiply(p3);
            money = CalRoundUtil.bigDecimalRound(moneyG4.add(moneyG3),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,(g3.subtract(hGas)),p4,
                    (hcGas.subtract(g3)),initParams,initParams,initParams,initParams,p4,4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(hGas)).multiply(p3);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4).add(moneyG3),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,(g3.subtract(hGas)),p4,
                    (g4.subtract(g3)),p5,(hcGas.subtract(g4)),initParams,initParams,p5,5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(g3)).multiply(p4);
            BigDecimal moneyG3 = (g3.subtract(hGas)).multiply(p3);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4).add(moneyG3),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,p3,(g3.subtract(hGas)),p4,
                    (g4.subtract(g3)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6,6);
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
                    cGas,initParams,initParams,initParams,initParams,p4,4);
        }else if(hcGas.compareTo(g5)<=0||(g5.compareTo(maxParams)>=0&&g6.compareTo(initParams)==0)) {
            BigDecimal moneyG5 = (hcGas.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(hGas)).multiply(p4);
            money = CalRoundUtil.bigDecimalRound(moneyG5.add(moneyG4),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,p4,
                    (g4.subtract(hGas)),p5,(hcGas.subtract(g4)),initParams,initParams,p5,5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(g4)).multiply(p5);
            BigDecimal moneyG4 = (g4.subtract(hGas)).multiply(p4);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5).add(moneyG4),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,p4,
                    (g4.subtract(hGas)),p5,(g5.subtract(g4)),p6,(hcGas.subtract(g5)),p6,6);
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
                    initParams,p5,cGas,initParams,initParams,p5,5);
        }else if(hcGas.compareTo(g6)<=0||g6.compareTo(maxParams)>=0) {
            BigDecimal moneyG6 = (hcGas.subtract(g5)).multiply(p6);
            BigDecimal moneyG5 = (g5.subtract(hGas)).multiply(p5);
            money = CalRoundUtil.bigDecimalRound(moneyG6.add(moneyG5),num);
            settlementDetail(settlementVO,money,initParams,initParams,initParams,initParams,initParams,initParams,initParams,
                    initParams,p5,cGas,p6,(hcGas.subtract(g5)),p6,6);
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
                initParams,initParams,initParams,p6,cGas,p6,6);
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
                                           ReadMeterDataIot meterDataVO,UseGasType useGasType,boolean isCycleClear){
        CalculateVO calculateVO = new CalculateVO();
        //判断是否启用人口递增
        PriceScheme priceScheme = isPopulationAddPrice(price,gasMeter,useGasType);
        //判断是否启用开户递减
        PriceScheme priceSchemeLast = isOpenDecrement(priceScheme,gasMeter,useGasType);
        //确定最终价格方案进行计算
        calculateVO.setPriceScheme(priceSchemeLast);
        //是否调价、周期清零
        if(meterDataVO.getIsFirst()==0){
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
    public R<GasmeterSettlementDetail> updateCalculate(ReadMeterDataIot meterDataVO, GasMeterInfo gasMeterInfo,
                                                        PriceScheme price, UseGasType useGasType, CustomerAccount account,
                                                        GasMeter gasMeter, SettlementVO settlementVO,CalculateVO calculateVO){
        GasmeterSettlementDetail gSds = new GasmeterSettlementDetail();
        if(calculateVO.getIsClear()){
            meterDataVO.setLastCycleTotalUseGas(BigDecimal.ZERO);
            gasMeterInfo.setCycleUseGas(meterDataVO.getMonthUseGas());
            meterDataVO.setCycleTotalUseGas(meterDataVO.getMonthUseGas());
        }else{
            if(gasMeterInfo.getCycleUseGas()==null){
                meterDataVO.setCycleTotalUseGas(meterDataVO.getMonthUseGas());
                meterDataVO.setLastCycleTotalUseGas(BigDecimal.ZERO);
            }else{
                meterDataVO.setCycleTotalUseGas(gasMeterInfo.getCycleUseGas().add(meterDataVO.getMonthUseGas()));
                meterDataVO.setLastCycleTotalUseGas(gasMeterInfo.getCycleUseGas());
            }
            if(gasMeterInfo.getCycleUseGas()==null){gasMeterInfo.setCycleUseGas(meterDataVO.getMonthUseGas());}
            else{gasMeterInfo.setCycleUseGas(gasMeterInfo.getCycleUseGas().add(meterDataVO.getMonthUseGas()));}
        }
        /**更新结算后的抄表数据**/
        meterDataVO.setSettlementTime(LocalDateTime.now());
        meterDataVO.setSettlementUserId(BaseContextHandler.getUserId());
        meterDataVO.setSettlementUserName(BaseContextHandler.getName());
        meterDataVO.setUseMoney(settlementVO.getMoney());
        meterDataVO.setProcessStatus(ProcessIotEnum.SETTLED);
        if (gasMeterInfo.getGasMeterBalance() != null) {
            gSds.setSettlementMeterPreMoney(gasMeterInfo.getGasMeterBalance());
        }

        /**更新气表使用情况**/
        //gasMeterInfo.setPriceSchemeId(price.getId());
        if(gasMeterInfo.getTotalUseGas()==null){gasMeterInfo.setTotalUseGas(meterDataVO.getMonthUseGas());}
        else{gasMeterInfo.setTotalUseGas(gasMeterInfo.getTotalUseGas().add(meterDataVO.getMonthUseGas()));}
        if(gasMeterInfo.getTotalUseGasMoney()==null){gasMeterInfo.setTotalUseGasMoney(settlementVO.getMoney());}
        else{gasMeterInfo.setTotalUseGasMoney(gasMeterInfo.getTotalUseGasMoney().add(settlementVO.getMoney()));}
        /**新增结算记录**/
        gSds.setCustomerCode(meterDataVO.getCustomerCode());
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
            gasMeterInfo.setCurrentPrice(price.getFixedPrice());
            gasMeterInfo.setCurrentLadder(1);
        }else{
            gasMeterInfo.setCurrentPrice(settlementVO.getCurrentPrice());
            gasMeterInfo.setCurrentLadder(settlementVO.getCurrentLadder());
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
        gSds.setOrgId(gasMeterInfo.getOrgId());
        gSds.setOrgName(gasMeterInfo.getOrgName());
        gasMeterInfoService.updateById(gasMeterInfo);
        gasmeterSettlementDetailService.save(gSds);
        updateReadMeterDataIot(meterDataVO);
        return R.success(gSds);
    }

    @Override
    public R<List<ReadMeterDataIot>> settlementIotEX(List<ReadMeterDataIot> list, int type,String executeDate) {
        return settlementData(list,type,executeDate);
    }

    @Override
    public List<ReadMeterDataIot> findSettleData(String gasMeterCode, String customerCode) {
        return readMeterDataIotService.findSettleData(gasMeterCode,customerCode);
    }

    public void updateCalculateIot(ReadMeterDataIot meterDataVO, GasMeterInfo gasMeterInfo,
                                                       PriceScheme price, UseGasType useGasType, CustomerAccount account,
                                                       GasMeter gasMeter, SettlementVO settlementVO,CalculateVO calculateVO){
        if(calculateVO.getIsClear()){
            meterDataVO.setLastCycleTotalUseGas(BigDecimal.ZERO);
            gasMeterInfo.setCycleUseGas(meterDataVO.getMonthUseGas());
            meterDataVO.setCycleTotalUseGas(meterDataVO.getMonthUseGas());
        }else{
            BigDecimal cycleUseGas = meterDataVO.getLastCycleTotalUseGas();
            //calculateVO.setHistoryGas(cycleUseGas);
            if(gasMeterInfo.getCycleUseGas()==null){
                meterDataVO.setCycleTotalUseGas(meterDataVO.getMonthUseGas());
                meterDataVO.setLastCycleTotalUseGas(BigDecimal.ZERO);
            }else{
                meterDataVO.setCycleTotalUseGas(gasMeterInfo.getCycleUseGas().add(meterDataVO.getMonthUseGas()));
                meterDataVO.setLastCycleTotalUseGas(gasMeterInfo.getCycleUseGas());
            }
            if(gasMeterInfo.getCycleUseGas()==null){gasMeterInfo.setCycleUseGas(meterDataVO.getMonthUseGas());}
            else{gasMeterInfo.setCycleUseGas(gasMeterInfo.getCycleUseGas().add(meterDataVO.getMonthUseGas()));}
        }
        /**更新结算后的抄表数据**/
        meterDataVO.setSettlementTime(LocalDateTime.now());
        meterDataVO.setSettlementUserId(BaseContextHandler.getUserId());
        meterDataVO.setSettlementUserName(BaseContextHandler.getName());

        /**更新气表使用情况**/
        //gasMeterInfo.setPriceSchemeId(price.getId());
        if(gasMeterInfo.getTotalUseGas()==null){gasMeterInfo.setTotalUseGas(meterDataVO.getMonthUseGas());}
        else{gasMeterInfo.setTotalUseGas(gasMeterInfo.getTotalUseGas().add(meterDataVO.getMonthUseGas()));}
        if(gasMeterInfo.getTotalUseGasMoney()==null){gasMeterInfo.setTotalUseGasMoney(settlementVO.getMoney());}
        else{gasMeterInfo.setTotalUseGasMoney(gasMeterInfo.getTotalUseGasMoney().add(settlementVO.getMoney()));}

        /**谁知抄表数据**/
        meterDataVO.setUseMoney(settlementVO.getMoney());
        meterDataVO.setCycleTotalUseGas(gasMeterInfo.getCycleUseGas());
        //meterDataVO.set

        //gSds.setSettlementNo(BizCodeUtil.genBizDataCode());
        //设置人口浮动金额、价格
        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
            meterDataVO.setFreeAmount(BigDecimal.ZERO);
            meterDataVO.setUnitPrice(price.getFixedPrice());
            gasMeterInfo.setCurrentPrice(price.getFixedPrice());
            gasMeterInfo.setCurrentLadder(1);
        }else{
            gasMeterInfo.setCurrentPrice(settlementVO.getCurrentPrice());
            gasMeterInfo.setCurrentLadder(settlementVO.getCurrentLadder());
        }
        meterDataVO.setChargeStatus(ChargeIotEnum.NO_CHARGE);
        meterDataVO.setProcessStatus(ProcessIotEnum.SETTLED);
        meterDataVO = setReadMeterDataLadder(settlementVO,meterDataVO);
        if(meterDataVO.getDataType()==0){
            gasMeterInfoService.updateById(gasMeterInfo);
        }
        readMeterDataIotService.updateById(meterDataVO);
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
                                         BigDecimal price5,BigDecimal gas5,BigDecimal price6,BigDecimal gas6,
                                          BigDecimal currentPrice,int currentLadder){
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
        settlementVO.setCurrentLadder(currentLadder);
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
    private void createArrears(ReadMeterDataIot meterDataVO,SettlementVO settlementVO,BigDecimal arrearsMoney,UseGasType useGasType
            ,PriceScheme priceScheme,GasMeterInfo gasMeterInfo){
        /**新增欠费记录**/
        GasmeterArrearsDetail gads = new GasmeterArrearsDetail();
        gads.setCustomerCode(meterDataVO.getCustomerCode());
        gads.setCustomerName(meterDataVO.getCustomerName());
        gads.setGasmeterCode(meterDataVO.getGasMeterCode());
        gads.setDataStatus(DataStatusEnum.NORMAL.getValue());
        if(StringUtils.isNotEmpty(meterDataVO.getGasMeterName())){gads.setGasmeterName(meterDataVO.getGasMeterName());}
        gads.setReadmeterDataId(meterDataVO.getId());
        gads.setReadmeterMonth(meterDataVO.getReadMeterYear()+"-"+meterDataVO.getReadMeterMonth().toString());
        gads.setGas(meterDataVO.getMonthUseGas());
        gads.setGasMoney(settlementVO.getMoney());
        gads.setArrearsMoney(arrearsMoney);
        gads.setUseGasTypeId(useGasType.getId());
        gads.setUseGasTypeName(useGasType.getUseGasTypeName());
        gads.setArrearsStatus(UNCHARGE.getCode());
        gads.setBillingDate(settlementVO.getSettlementTime());
        gads.setOrgId(gasMeterInfo.getOrgId());
        gads.setOrgName(gasMeterInfo.getOrgName());
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

    private BigDecimal createArrearsEX(ReadMeterDataIot meterDataVO,BigDecimal arrearsMoney,UseGasType useGasType
            ,PriceScheme priceScheme,BigDecimal useGas,int type,GasMeterInfo gasMeterInfo){
        BigDecimal currentPrice = BigDecimal.ZERO;
        /**新增欠费记录**/
        GasmeterArrearsDetail gads = new GasmeterArrearsDetail();
        gads.setCustomerCode(meterDataVO.getCustomerCode());
        gads.setCustomerName(meterDataVO.getCustomerName());
        gads.setGasmeterCode(meterDataVO.getGasMeterCode());
        if(type==1){gads.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());}
        else{gads.setDataStatus(DataStatusEnum.NORMAL.getValue());}
        if(StringUtils.isNotEmpty(meterDataVO.getGasMeterName())){gads.setGasmeterName(meterDataVO.getGasMeterName());}
        gads.setReadmeterDataId(meterDataVO.getId());
        String monthStr = meterDataVO.getReadMeterMonth().toString();
        if(monthStr.length()==1){monthStr = "0"+monthStr;}
        gads.setReadmeterMonth(meterDataVO.getReadMeterYear()+"-"+monthStr);
        gads.setGas(useGas);
        gads.setGasMoney(arrearsMoney);
        gads.setSettlementNo(meterDataVO.getGasOweCode());
        gads.setArrearsMoney(arrearsMoney);
        gads.setUseGasTypeId(useGasType.getId());
        gads.setUseGasTypeName(useGasType.getUseGasTypeName());
        gads.setArrearsStatus(UNCHARGE.getCode());
        gads.setOrgId(gasMeterInfo.getOrgId());
        gads.setOrgName(gasMeterInfo.getOrgName());
        gads.setBillingDate(meterDataVO.getSettlementTime().toLocalDate());
        if(PriceType.FIXED_PRICE.getCode().equals(useGasType.getPriceType())){
            gads.setPrice(priceScheme.getFixedPrice());
            gads.setIsLadderPrice(FALSE);
            currentPrice = priceScheme.getFixedPrice();
        }else{
            LadderVO ladderVO = setLadderDetailMeterNB21(meterDataVO);
            currentPrice = ladderVO.getCurrentPrice();
            List<LadderDetailVO> ladderDetailList = ladderVO.getList();
            gads.setLeadderPriceDetail(JSON.toJSONString(ladderDetailList));
            gads.setIsLadderPrice(TRUE);
        }
        gasmeterArrearsDetailService.save(gads);
        return currentPrice;
    }

    private ReadMeterDataIot setReadMeterDataLadder(SettlementVO settlementVO,ReadMeterDataIot readMeterDataIot){
        readMeterDataIot.setPrice1(settlementVO.getPrice1());
        readMeterDataIot.setGas1(settlementVO.getGas1());
        readMeterDataIot.setMoney1(settlementVO.getMoney1());

        readMeterDataIot.setPrice2(settlementVO.getPrice2());
        readMeterDataIot.setGas2(settlementVO.getGas2());
        readMeterDataIot.setMoney2(settlementVO.getMoney2());

        readMeterDataIot.setPrice3(settlementVO.getPrice3());
        readMeterDataIot.setGas3(settlementVO.getGas3());
        readMeterDataIot.setMoney3(settlementVO.getMoney3());

        readMeterDataIot.setPrice4(settlementVO.getPrice4());
        readMeterDataIot.setGas4(settlementVO.getGas4());
        readMeterDataIot.setMoney4(settlementVO.getMoney4());

        readMeterDataIot.setPrice5(settlementVO.getPrice5());
        readMeterDataIot.setGas5(settlementVO.getGas5());
        readMeterDataIot.setMoney5(settlementVO.getMoney5());

        readMeterDataIot.setPrice6(settlementVO.getPrice6());
        readMeterDataIot.setGas6(settlementVO.getGas6());
        readMeterDataIot.setMoney6(settlementVO.getMoney6());
        return readMeterDataIot;
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

    private LadderVO setLadderDetailMeterNB21(ReadMeterDataIot readMeterDataIot){
        LadderVO ladderVO = new LadderVO();
        List<LadderDetailVO> ladderDetailList = new ArrayList<>();
        ladderVO.setCurrentPrice(readMeterDataIot.getPrice1());
        for(int i=1;i<=6;i++){
            LadderDetailVO ladderDetailVO = new LadderDetailVO();
            switch (i){
                case 1:
                    if(readMeterDataIot.getGas1()!=null&&(readMeterDataIot.getGas1().compareTo(new BigDecimal("0.00"))==0
                            ||readMeterDataIot.getGas1().compareTo(new BigDecimal("0.0000"))==0)){break;}
                    ladderDetailVO.setGas(readMeterDataIot.getGas1());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(readMeterDataIot.getPrice1());
                    ladderDetailVO.setTotalMoney(readMeterDataIot.getMoney1());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 2:
                    if(readMeterDataIot.getGas2()!=null&&(readMeterDataIot.getGas2().compareTo(new BigDecimal("0.00"))==0
                            ||readMeterDataIot.getGas2().compareTo(new BigDecimal("0.0000"))==0))
                    {ladderVO.setCurrentPrice(readMeterDataIot.getPrice1());break;}
                    ladderDetailVO.setGas(readMeterDataIot.getGas2());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(readMeterDataIot.getPrice2());
                    ladderDetailVO.setTotalMoney(readMeterDataIot.getMoney2());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 3:
                    if(readMeterDataIot.getGas3()!=null&&(readMeterDataIot.getGas3().compareTo(new BigDecimal("0.00"))==0
                            ||readMeterDataIot.getGas3().compareTo(new BigDecimal("0.0000"))==0))
                    {ladderVO.setCurrentPrice(readMeterDataIot.getPrice2());break;}
                    ladderDetailVO.setGas(readMeterDataIot.getGas3());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(readMeterDataIot.getPrice3());
                    ladderDetailVO.setTotalMoney(readMeterDataIot.getMoney3());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 4:
                    if(readMeterDataIot.getGas4()!=null&&(readMeterDataIot.getGas4().compareTo(new BigDecimal("0.00"))==0
                            ||readMeterDataIot.getGas4().compareTo(new BigDecimal("0.0000"))==0))
                    {ladderVO.setCurrentPrice(readMeterDataIot.getPrice3());break;}
                    ladderDetailVO.setGas(readMeterDataIot.getGas4());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(readMeterDataIot.getPrice4());
                    ladderDetailVO.setTotalMoney(readMeterDataIot.getMoney4());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 5:
                    if(readMeterDataIot.getGas5()!=null&&(readMeterDataIot.getGas5().compareTo(new BigDecimal("0.00"))==0
                            ||readMeterDataIot.getGas5().compareTo(new BigDecimal("0.0000"))==0))
                    {ladderVO.setCurrentPrice(readMeterDataIot.getPrice4());break;}
                    ladderDetailVO.setGas(readMeterDataIot.getGas5());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(readMeterDataIot.getPrice5());
                    ladderDetailVO.setTotalMoney(readMeterDataIot.getMoney5());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                case 6:
                    if(readMeterDataIot.getGas6()!=null&&(readMeterDataIot.getGas6().compareTo(new BigDecimal("0.00"))==0
                            ||readMeterDataIot.getGas1().compareTo(new BigDecimal("0.0000"))==0))
                    {ladderVO.setCurrentPrice(readMeterDataIot.getPrice5());break;}else{
                        ladderVO.setCurrentPrice(readMeterDataIot.getPrice6());
                    }
                    ladderDetailVO.setGas(readMeterDataIot.getGas6());
                    ladderDetailVO.setLadder(i);
                    ladderDetailVO.setPrice(readMeterDataIot.getPrice6());
                    ladderDetailVO.setTotalMoney(readMeterDataIot.getMoney6());
                    ladderDetailList.add(ladderDetailVO);
                    break;
                default:
                    break;
            }
        }
        ladderVO.setList(ladderDetailList);
        return ladderVO;
    }
    /**
     * 更新账户、结算记录、新增账户抵扣流水
     * @param arrearsMoney 欠费金额
     * @param gasmeterSettlementDetail 抵扣前结算明细
     * @param customerAccount 抵扣后账户
     * @param accountPre 抵扣前账户
     */
    private void updateSettlementAccount(BigDecimal arrearsMoney,GasmeterSettlementDetail gasmeterSettlementDetail,
                                         CustomerAccount customerAccount,CustomerAccount accountPre,String gasMeterCode,
                                         GasMeterInfo gasMeterInfo,GasMeterInfo gasMeterInfoPre){
        /**更新账户表**/
        customerAccountService.updateById(customerAccount);
        /**新增账户抵扣流水**/
        CustomerAccountSerial accountSerial = new CustomerAccountSerial();
        accountSerial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));

        accountSerial.setAccountCode(customerAccount.getAccountCode());
        accountSerial.setCustomerCode(customerAccount.getCustomerCode());
        accountSerial.setGasMeterCode(gasMeterCode);
        accountSerial.setOrgId(gasMeterInfo.getOrgId());
        accountSerial.setOrgName(gasMeterInfo.getOrgName());
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
        gasmeterSettlementDetail.setSettlementMeterPreMoney(gasMeterInfoPre.getGasMeterBalance());
        gasmeterSettlementDetail.setSettlementMeterAfterMoney(gasMeterInfo.getGasMeterBalance());
        gasmeterSettlementDetail.setGiveMeterAfterMoney(gasMeterInfo.getGasMeterGive());
        gasmeterSettlementDetail.setGiveMeterPreMoney(gasMeterInfoPre.getGasMeterGive());
        gasmeterSettlementDetailService.updateById(gasmeterSettlementDetail);
    }

    private void updateSettlementAccountEX(BigDecimal arrearsMoney,ReadMeterDataIot readMeterDataIot,
                                         CustomerAccount customerAccount,CustomerAccount accountPre,String gasMeterCode){
        /**更新账户表**/
        customerAccountService.updateById(customerAccount);
        /**新增账户抵扣流水**/
        CustomerAccountSerial accountSerial = new CustomerAccountSerial();
//        accountSerial.setBillNo(BizCodeUtil.genSerialDataCode(BizSCode.SYS,BizCodeUtil.ACCOUNT_SERIAL));
        accountSerial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
        accountSerial.setBillNo(readMeterDataIot.getGasOweCode());
        //accountSerial.setBillNo();
        accountSerial.setAccountCode(customerAccount.getAccountCode());
        accountSerial.setCustomerCode(customerAccount.getCustomerCode());
        accountSerial.setOrgId(customerAccount.getOrgId());
        accountSerial.setOrgName(customerAccount.getOrgName());
        if(!StringUtil.isNullOrBlank(gasMeterCode)){
           accountSerial.setGasMeterCode(gasMeterCode);
        }
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
    }

    /**
     * 户表账户抵扣流水
     * @param arrearsMoney
     * @param readMeterDataIot
     * @param customerAccount
     * @param accountPre
     */
    private void updateMeterAccountEX(BigDecimal arrearsMoney,ReadMeterDataIot readMeterDataIot,
                                           GasMeterInfo customerAccount,GasMeterInfo accountPre,String customerCode){
        /**新增账户抵扣流水**/
        GasMeterInfoSerial accountSerial = new GasMeterInfoSerial();
        //accountSerial.setBillNo(BizCodeUtil.genSerialDataCode(BizSCode.SYS,BizCodeUtil.ACCOUNT_SERIAL));
        if(!StringUtil.isNullOrBlank(readMeterDataIot.getGasOweCode())){
            accountSerial.setBillNo(readMeterDataIot.getGasOweCode());
        }
        accountSerial.setSerialNo(BizCodeNewUtil.getGasMeterInfoSerialCode(BizSCode.CHARGE));
        accountSerial.setGasMeterInfoId(customerAccount.getId());
        accountSerial.setOrgId(customerAccount.getOrgId());
        accountSerial.setOrgName(customerAccount.getOrgName());
        accountSerial.setCompanyCode(BaseContextHandler.getTenantName());
        accountSerial.setCompanyCode(BaseContextHandler.getTenant());
        if(!StringUtil.isNullOrBlank(readMeterDataIot.getGasMeterCode())){
            accountSerial.setGasMeterCode(readMeterDataIot.getGasMeterCode());
        }
        accountSerial.setCustomerCode(readMeterDataIot.getCustomerCode());
        accountSerial.setBillType(MeterAccountSerialSceneEnum.DEDUCTION.getCode());
        //账户余额抵扣
        accountSerial.setBookPreMoney(accountPre.getGasMeterBalance());
        accountSerial.setBookAfterMoney(customerAccount.getGasMeterBalance());
        if(accountPre.getGasMeterBalance()!=null){
            accountSerial.setBookMoney(accountPre.getGasMeterBalance().subtract(customerAccount.getGasMeterBalance()));
        }else{
            accountSerial.setBookMoney(customerAccount.getGasMeterBalance());
        }
        //赠送抵扣
        if(accountPre.getGasMeterGive()!=null&&customerAccount.getGasMeterGive()!=null){
            accountSerial.setGiveBookPreMoney(accountPre.getGasMeterGive());
            accountSerial.setGiveBookAfterMoney(customerAccount.getGasMeterGive());
            accountSerial.setGiveBookMoney(accountPre.getGasMeterGive().subtract(customerAccount.getGasMeterGive()));
        }
        gasMeterInfoSerialService.save(accountSerial);
    }

    /**
     * 更新表端账户
     * @param arrearsMoney
     * @param gasmeterSettlementDetail
     * @param customerAccount
     * @param accountPre
     */
    private void updateMeterAccount(BigDecimal arrearsMoney,GasmeterSettlementDetail gasmeterSettlementDetail,
                                    GasMeterInfo gasMeterInfo,GasMeterInfo gasMeterInfoPre){
        //更新结算记录
        gasmeterSettlementDetail.setArrearsMoney(arrearsMoney);
        gasmeterSettlementDetail.setSettlementMeterPreMoney(gasMeterInfoPre.getGasMeterBalance());
        gasmeterSettlementDetail.setSettlementMeterAfterMoney(gasMeterInfo.getGasMeterBalance());
        gasmeterSettlementDetail.setGiveMeterAfterMoney(gasMeterInfo.getGasMeterGive());
        gasmeterSettlementDetail.setGiveMeterPreMoney(gasMeterInfoPre.getGasMeterGive());
        gasmeterSettlementDetailService.updateById(gasmeterSettlementDetail);
        //更新表账户
        gasMeterInfoService.updateById(gasMeterInfo);
    }

    private void updateGasMeterInfo(GasMeterInfo gasMeterInfo){
        gasMeterInfoService.updateById(gasMeterInfo);
    }

    private void updateReadMeterDataIot(ReadMeterDataIot readMeterData){
        readMeterDataIotService.updateById(readMeterData);
    }

    /**
     * 物联网表余额更新
      * @param gasMeterNumber 表身号
     * @param gasMeterBalance 表账户余额
     * @param currentPrice 当前价格
     */
    private void updateBalance(String gasMeterNumber,BigDecimal gasMeterBalance,BigDecimal currentPrice,BigDecimal alarmMoney){
        UpdateBalanceVO updateBalanceVO = new UpdateBalanceVO();
        updateBalanceVO.setBalance(gasMeterBalance);
        updateBalanceVO.setGasMeterNumber(gasMeterNumber);
        updateBalanceVO.setPrice(currentPrice);
        //判断是否有缓存
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        List<String> meterNoList = new ArrayList<>();
        meterNoList.add(gasMeterNumber);
        meterCacheVO.setMeterList(meterNoList);
        meterIotCacheUtil.matchMeterCache(meterCacheVO);
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(updateBalanceVO.getGasMeterNumber());
        log.info("中心计费物联网表:【{}】下发更新表端余额指令，domainId:【{}】", gasMeterNumber,domainId);
        if(domainId!=null){
            updateBalanceVO.setDomainId(domainId);
            IotGasMeterUploadData model = iotGasMeterUploadDataBizApi.queryActivateMeter(updateBalanceVO.getGasMeterNumber());
            UpdateBalanceAndPriceModel params = new UpdateBalanceAndPriceModel();
            params.setArchiveId(model.getArchiveId());
            params.setBusinessType(CommandType.DAILYSETTLEMENT.getCode());
            params.setDomainId(model.getDomainId());
            List<SetUpParamsterModel<SetUpParamsterValueModel>> list = new ArrayList<>();
            if(model.getGasMeterCode()!=null&&model.getCustomerCode()!=null&&null==updateBalanceVO.getPrice()){
                GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(model.getGasMeterCode(),model.getCustomerCode());
                if(null!=gasMeterInfo){
                    updateBalanceVO.setPrice(gasMeterInfo.getCurrentPrice());
                }
            }
            if(updateBalanceVO.getPrice()!=null){
                list.add(getParamsList("STATE_PRICE",updateBalanceVO.getPrice().toString()));
            }
            if(updateBalanceVO.getBalance()!=null){
                list.add(getParamsList("STATE_BALANCE_AMOUNT",updateBalanceVO.getBalance().toString()));
                if(updateBalanceVO.getAlarmMoney()!=null){
                    list.add(getParamsList("STATE_BALANCE_STATUS",updateBalanceVO.getBalance().compareTo(
                            updateBalanceVO.getAlarmMoney())<=0?"1":"0"));
                }
                list.add(getParamsList("STATE_OVERDRAFT",updateBalanceVO.getBalance()
                        .compareTo(BigDecimal.ZERO)<=0?"1":"0"));
            }
            params.setBusinessData(list);
            String sendData = JSONUtil.toJsonStr(params);
            CommondCaluteVO commandIotVO = defendCommand(updateBalanceVO.getGasMeterNumber(), model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                    CommandType.DAILYSETTLEMENT, sendData,null, ExecuteState.Success.getCodeValue(), null);
            updateBalanceVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
            updateBalanceVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
            try{
                bussinessBizApi.updatebalance(domainId,updateBalanceVO);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 欠费关阀
     * @param gasMeterNumber
     */
    private void valveOperate(String gasMeterNumber,String operateType){
        ValveControlVO valveControlVO = new ValveControlVO();
        valveControlVO.setGasMeterNumber(gasMeterNumber);
        valveControlVO.setControlCode(operateType);
        //判断是否有缓存
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        List<String> meterNoList = new ArrayList<>();
        meterNoList.add(gasMeterNumber);
        meterCacheVO.setMeterList(meterNoList);
        meterIotCacheUtil.matchMeterCache(meterCacheVO);
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(valveControlVO.getGasMeterNumber());
        log.info("中心计费预付费表:【{}】欠费下发关阀指令，domainId:【{}】", gasMeterNumber,domainId);
        if(domainId!=null){
            valveControlVO.setDomainId(domainId);
            IotGasMeterUploadData model = iotGasMeterUploadDataBizApi.queryActivateMeter(gasMeterNumber);
            ValveControlModel valveModel = new ValveControlModel();
            valveModel.setArchiveId(model.getArchiveId());
            valveModel.setDomainId(model.getDomainId());
            valveModel.setBusinessType(CommandType.VALVECONTROL.getCode());
            ValveControlBus bus = new ValveControlBus();
            bus.setControlCode(operateType);
            valveModel.setBusinessData(bus);
            String sendData = JSONObject.toJSONString(valveModel);
            if(GMISIotConstant.DEVICE_VALVE_CLOSE_CODE.equals(operateType)
                    ||(GMISIotConstant.DEVICE_VALVE_PRIVATE_CLOSE_CODE.equals(operateType))){
                CommondCaluteVO commandIotVO = defendCommand(gasMeterNumber, model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                        CommandType.CLOSEVALVE, sendData,null,ExecuteState.Success.getCodeValue(), null);
                valveControlVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
                valveControlVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
            }else if(GMISIotConstant.DEVICE_VALVE_CLOSE_OPEN.equals(operateType)){
                CommondCaluteVO commandIotVO = defendCommand(gasMeterNumber, model.getGasMeterCode(),model.getCustomerCode(),BaseContextHandler.getOrgId(),BaseContextHandler.getOrgName(),
                        CommandType.OPENVALVE, sendData,null,ExecuteState.Success.getCodeValue(), null);
                valveControlVO.setIotGasMeterCommand(commandIotVO.getIotGasMeterCommand());
                valveControlVO.setIotGasMeterCommandDetail(commandIotVO.getIotGasMeterCommandDetail());
            }
            try{
                bussinessBizApi.valueControl(domainId,valveControlVO);
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
    }

    /**
     * NB21中心计费后付费账单生成
     */
    private void updateNB21FArrearsMoney(List<ReadMeterDataIot> listData,String executeDate){
        DateTimeFormatter  fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String seDateStr = GmisSysSettingUtil.getSettlementDate();
        LocalDate settlementDate = null;
        if(StringUtil.isNullOrBlank(executeDate)){
            if(StringUtil.isNullOrBlank(seDateStr)){throw new BizException("后付费未设置结算日期，请先设置结算日期");}
            settlementDate=LocalDate.parse(GmisSysSettingUtil.getSettlementDate(),fomatter);
        }else{
            settlementDate=LocalDate.parse(executeDate,fomatter);
        }
        LocalDate nowDate = LocalDate.now();
        //到达账单生成日期，生成账单
        if(settlementDate.getDayOfMonth()==nowDate.getDayOfMonth()){
            if(listData==null||listData.size()==0){return;}
            List<ReadMeterDataIot> settlementList = new CopyOnWriteArrayList<>();
            Map<String, List<ReadMeterDataIot>> listMeterNo = listData.stream().
                    collect(Collectors.groupingBy(ReadMeterDataIot::getGasMeterCode));
            LocalDate monthDate = settlementDate.minusMonths(1);
            LocalDate startDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), 1);
            LocalDate endDate = monthDate.with(TemporalAdjusters.lastDayOfMonth());
            log.info("中心计费后付费账单统计时间区间:【{}】------【{}】",startDate,endDate);
            for(Map.Entry<String, List<ReadMeterDataIot>> entry:listMeterNo.entrySet()){
                SettlementArrearsVO arrearsVO = new SettlementArrearsVO();
                arrearsVO.setEndDate(endDate);
                arrearsVO.setStartDate(startDate);
                arrearsVO.setGasMeterCode(entry.getKey());
                List<ReadMeterDataIot> list = readMeterDataIotService.getSettlementArrears(arrearsVO);
                String serialNo = BizCodeNewUtil.getOweCode(BizSCode.CHARGE);
                if(list!=null&&list.size()>0){
                    BigDecimal arrearsMoney = BigDecimal.ZERO;
                    BigDecimal useGas = BigDecimal.ZERO;
                    int index = 0;
                    for(int i=0;i<list.size();i++){
                        if(index==0){
                            log.info("中心计费后付费账单统计开始数据:【{}】", JSONUtil.toJsonStr(list.get(i)));
                        }
                        if(index==(list.size()-1)){
                            log.info("中心计费后付费账单统计结束数据:【{}】", JSONUtil.toJsonStr(list.get(i)));
                        }
                        if(ProcessIotEnum.APPROVED.eq(list.get(i).getProcessStatus())){
                            throw new BizException(list.get(i).getGasMeterNumber()+"日期:"+list.get(i).getDataTime()+"记录未结算不能生成账单");
                        }
                        list.get(i).setGasOweCode(serialNo);
                        arrearsMoney = arrearsMoney.add(list.get(i).getUseMoney());
                        useGas = useGas.add(list.get(i).getMonthUseGas());
                        index ++;
                    }
                    ReadMeterDataIot e = list.get((list.size()-1));
                    //用户账户
                    CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
                    if(null == account){throw new  BizException("未找到用户账户");}
                    //气表账户
                    GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
                    PriceScheme price = priceSchemeService.getById(e.getRelPriceId());
                    if(null == price){throw new  BizException("未找到"+e.getGasMeterNumber()+"价格方案");}
                    UseGasType useGasType = useGasTypeService.getById(e.getRelUseGasTypeId());
                    if(null == useGasType){throw new  BizException("未找到"+e.getGasMeterNumber()+"用气类型");}
                    //抵扣
                    arrearsMoney = accountWithholdEX(account,gasMeterInfo,list.get(0),arrearsMoney,useGas).getData();
                    log.info("中心计费后付费账单统计时间区间:【{}】------【{}】欠费为:【{}】",startDate,endDate,arrearsMoney);
                    //查看前面月份是否有未生成的账单
                    beforeArrearsCreate(e,entry.getKey());
                    //欠费大于0生成账单
                    if(arrearsMoney!=null&&arrearsMoney.compareTo(BigDecimal.ZERO)>=0){
                        //查看是否有本月的后付费账单
                        if(!checkArrears(e.getGasMeterCode(),e.getCustomerCode(),e.getDataTime())){
                            //生成中心计费后付费账单
                            BigDecimal currentPrice = createArrearsEX(e,arrearsMoney,useGasType,price,useGas,0,gasMeterInfo);
                            list.stream().forEach(item->{
                                item.setIsCreateArrears(1);
                            });
                            readMeterDataIotService.updateBatchById(list);
                            //下发更新余额指令
                            if(gasMeterInfo.getGasMeterBalance()!=null){
                                updateBalance(e.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),currentPrice,null);
                            }
                        }
                    }
                }
            }
        }
    }

    private void createNB21FArrearsMoney(List<ReadMeterDataIot> list,String executeDate){
        if(list==null||list.size()==0){return;}
        DateTimeFormatter  fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String seDateStr = GmisSysSettingUtil.getSettlementDate();
        LocalDate settlementDate = null;
        if(StringUtil.isNullOrBlank(executeDate)){
            if(StringUtil.isNullOrBlank(seDateStr)){throw new BizException("后付费未设置结算日期，请先设置结算日期");}
            settlementDate=LocalDate.parse(GmisSysSettingUtil.getSettlementDate(),fomatter);
        }else{
            settlementDate=LocalDate.parse(executeDate,fomatter);
        }
        LocalDate nowDate = LocalDate.now();
        //到达账单生成日期，生成账单
        if(settlementDate.getDayOfMonth()==nowDate.getDayOfMonth()){
            LocalDate monthDate = list.get(0).getDataTime().toLocalDate();
            LocalDate startDate = LocalDate.of(monthDate.getYear(), monthDate.getMonthValue(), 1);
            LocalDate endDate = monthDate.with(TemporalAdjusters.lastDayOfMonth());
            String serialNo = BizCodeNewUtil.getOweCode(BizSCode.CHARGE);
            log.info("中心计费后付费账单统计时间区间:【{}】------【{}】",startDate,endDate);
            if(list!=null&&list.size()>0){
                BigDecimal arrearsMoney = BigDecimal.ZERO;
                BigDecimal useGas = BigDecimal.ZERO;
                int index = 0;
                boolean isCreate = true;
                for(int i=0;i<list.size();i++){
                    if(index==0){
                        log.info("中心计费后付费账单统计开始数据:【{}】", JSONUtil.toJsonStr(list.get(i)));
                    }
                    if(index==(list.size()-1)){
                        log.info("中心计费后付费账单统计结束数据:【{}】", JSONUtil.toJsonStr(list.get(i)));
                    }
                    if(ProcessIotEnum.APPROVED.eq(list.get(i).getProcessStatus())){
                        log.error(list.get(i).getGasMeterNumber()+"日期:"+list.get(i).getDataTime()+"记录未结算不能生成账单");
                        isCreate = false;
                        break;
                    }
                    list.get(i).setGasOweCode(serialNo);
                    arrearsMoney = arrearsMoney.add(list.get(i).getUseMoney());
                    useGas = useGas.add(list.get(i).getMonthUseGas());
                    index ++;
                }
                if(!isCreate){return;}
                ReadMeterDataIot e = list.get((list.size()-1));
                //用户账户
                CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
                if(null == account){log.error(e.getGasMeterNumber()+"欠费编号:"+e.getGasOweCode()+"生成账单时未找到用户账户");return;}
                //气表账户
                GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
                PriceScheme price = priceSchemeService.getById(e.getRelPriceId());
                if(null == price){log.error(e.getGasMeterNumber()+"欠费编号:"+e.getGasOweCode()+"生成账单时未找到价格方案");return;}
                UseGasType useGasType = useGasTypeService.getById(e.getRelUseGasTypeId());
                if(null == useGasType){log.error(e.getGasMeterNumber()+"欠费编号:"+e.getGasOweCode()+"生成账单时未找到用气类型");return;}
                //抵扣
                arrearsMoney = accountWithholdEX(account,gasMeterInfo,list.get(0),arrearsMoney,useGas).getData();
                log.info("中心计费后付费账单统计时间区间:【{}】------【{}】欠费为:【{}】",startDate,endDate,arrearsMoney);
                //欠费大于0生成账单
                if(arrearsMoney!=null&&arrearsMoney.compareTo(BigDecimal.ZERO)>=0){
                    //查看是否有本月的后付费账单
                    if(!checkArrears(e.getGasMeterCode(),e.getCustomerCode(),e.getDataTime())){
                        //生成中心计费后付费账单
                        BigDecimal currentPrice = createArrearsEX(e,arrearsMoney,useGasType,price,useGas,0,gasMeterInfo);
                        list.stream().forEach(item->{
                            item.setIsCreateArrears(1);
                        });
                        readMeterDataIotService.updateBatchById(list);
                        //下发更新余额指令
                        if(gasMeterInfo.getGasMeterBalance()!=null){
                            updateBalance(e.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),currentPrice,null);
                        }
                    }
                }
            }
        }
    }

    public void beforeArrearsCreate(ReadMeterDataIot meterDataIot,String gasMeterCode){
        //检查是否有以前月份未生成账单
        int months = checkArrearsMonth(meterDataIot.getGasMeterCode(),meterDataIot.getCustomerCode(),meterDataIot.getDataTime());
        if(months>1){
            for(int n=(months-1);n>0;n--){
                LocalDate cDate = meterDataIot.getDataTime().minusMonths(n).toLocalDate();
                LocalDate startDate = (LocalDate.of(cDate.getYear(), cDate.getMonthValue(), 1));
                LocalDate endDate = cDate.with(TemporalAdjusters.lastDayOfMonth());
                SettlementArrearsVO arrears = new SettlementArrearsVO();
                arrears.setEndDate(endDate);
                arrears.setStartDate(startDate);
                arrears.setGasMeterCode(gasMeterCode);
                List<ReadMeterDataIot> list = readMeterDataIotService.getSettlementArrears(arrears);
                String serialNo = BizCodeNewUtil.getOweCode(BizSCode.CHARGE);
                if(list!=null&&list.size()>0){
                    BigDecimal arrearsMoney = BigDecimal.ZERO;
                    BigDecimal useGas = BigDecimal.ZERO;
                    int index = 0;
                    for(int i=0;i<list.size();i++){
                        if(index==0){
                            log.info("中心计费后付费账单统计开始数据:【{}】", JSONUtil.toJsonStr(list.get(i)));
                        }
                        if(index==(list.size()-1)){
                            log.info("中心计费后付费账单统计结束数据:【{}】", JSONUtil.toJsonStr(list.get(i)));
                        }
                        if(ProcessIotEnum.APPROVED.eq(list.get(i).getProcessStatus())){
                            throw new BizException(list.get(i).getGasMeterNumber()+"日期:"+list.get(i).getDataTime()+"记录未结算不能生成账单");
                        }
                        list.get(i).setGasOweCode(serialNo);
                        arrearsMoney = arrearsMoney.add(list.get(i).getUseMoney());
                        useGas = useGas.add(list.get(i).getMonthUseGas());
                        index ++;
                    }
                    ReadMeterDataIot e = list.get((list.size()-1));
                    //用户账户
                    CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
                    if(null == account){throw new  BizException("未找到用户账户");}
                    //气表账户
                    GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
                    PriceScheme price = priceSchemeService.getById(e.getRelPriceId());
                    if(null == price){throw new  BizException("未找到"+e.getGasMeterNumber()+"价格方案");}
                    UseGasType useGasType = useGasTypeService.getById(e.getRelUseGasTypeId());
                    if(null == useGasType){throw new  BizException("未找到"+e.getGasMeterNumber()+"用气类型");}
                    //抵扣
                    arrearsMoney = accountWithholdEX(account,gasMeterInfo,list.get(0),arrearsMoney,useGas).getData();
                    log.info("中心计费后付费账单统计时间区间:【{}】------【{}】欠费为:【{}】",startDate,endDate,arrearsMoney);
                    if(arrearsMoney!=null&&arrearsMoney.compareTo(BigDecimal.ZERO)>=0){
                        //查看是否有本月的后付费账单
                        if(!checkArrears(e.getGasMeterCode(),e.getCustomerCode(),e.getDataTime())){
                            //生成中心计费后付费账单
                            BigDecimal currentPrice = createArrearsEX(e,arrearsMoney,useGasType,price,useGas,0,gasMeterInfo);
                            readMeterDataIotService.updateBatchById(list);
                            //下发更新余额指令
                            if(gasMeterInfo.getGasMeterBalance()!=null){
                                updateBalance(e.getGasMeterNumber(),gasMeterInfo.getGasMeterBalance(),currentPrice,null);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * NB21中心计费后付费场景业务账单生成
     * @param listData
     */
    private void createArrearsNB21FMoney(List<ReadMeterDataIot> listData){
        int index = 0;
        String serialNo = BizCodeNewUtil.getOweCode(BizSCode.CHARGE);
        BigDecimal arrearsMoney = BigDecimal.ZERO;
        BigDecimal useGas = BigDecimal.ZERO;
        for(int i=0;i<listData.size();i++){
            if(index==0){
                log.info("中心计费后付费账单统计开始数据:【{}】", JSONUtil.toJsonStr(listData.get(i)));
            }
            if(index==(listData.size()-1)){
                log.info("中心计费后付费账单统计结束数据:【{}】", JSONUtil.toJsonStr(listData.get(i)));
            }
            listData.get(i).setGasOweCode(serialNo);
            arrearsMoney = arrearsMoney.add(listData.get(i).getUseMoney());
            useGas = useGas.add(listData.get(i).getMonthUseGas());
            index ++;
        }
        ReadMeterDataIot e = listData.get((listData.size()-1));
        //用户账户
        CustomerAccount account = customerAccountService.queryAccountByCharge(e.getCustomerCode());
        if(null == account){throw new  BizException("未找到用户账户");}
        //气表账户
        GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(e.getGasMeterCode(),e.getCustomerCode());
        PriceScheme price = priceSchemeService.getById(e.getRelPriceId());
        if(null == price){throw new  BizException("未找到"+e.getGasMeterNumber()+"价格方案");}
        UseGasType useGasType = useGasTypeService.getById(e.getRelUseGasTypeId());
        if(null == useGasType){throw new  BizException("未找到"+e.getGasMeterNumber()+"用气类型");}
        //抵扣
        //arrearsMoney = accountWithholdEX(account,gasMeterInfo,listData.get(0),arrearsMoney,useGas).getData();
        if(arrearsMoney!=null&&arrearsMoney.compareTo(BigDecimal.ZERO)>=0){
            readMeterDataIotService.updateBatchById(listData);
            //生成中心计费后付费账单
            BigDecimal currentPrice = createArrearsEX(e,arrearsMoney,useGasType,price,useGas,1,gasMeterInfo);
        }
    }

    private R<List<ReadMeterDataIot>> settlementData(List<ReadMeterDataIot> list,int type,String executeDate){
        List<ReadMeterDataIot> listST= new ArrayList<>();
        List<ReadMeterDataIot> listCQ= new ArrayList<>();
        LocalDate exdate = (LocalDate.now().minusMonths(1)).with(TemporalAdjusters.lastDayOfMonth());
        //后期配置放缓存
        if(list==null||list.size()==0){
            listST = readMeterDataIotService.getArrearsRecord(exdate);
            //后付费账单处理
            createNB21FAccount(listST,listCQ,executeDate,type);
            log.error("抄表数据为空不能进行计费");
            return R.successDef();
        }
        Map<String, List<ReadMeterDataIot>> listMeterNo = list.stream().
                collect(Collectors.groupingBy(ReadMeterDataIot::getGasMeterNumber));
        /**计费逻辑**/
        listMeterNo.forEach((k,v)->{
            boolean isCycleClear = true;
            //处理场景未退费的情况
            cQBusinessEvent(v.get(0));
            //用户账户
            CustomerAccount account = customerAccountService.queryAccountByCharge(v.get(0).getCustomerCode());
            if(null == account){saveReadMeterError(v.get(0),"未找到用户账户");return;}
            //气表档案
            GasMeter gasMeter = null;
            if(StringUtil.isNullOrBlank(k)){
                gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode, v.get(0).getGasMeterCode())
                        .notIn(GasMeter::getDataStatus,5,6));
            }
            if(gasMeter==null&&!StringUtil.isNullOrBlank(k)){
                gasMeter = gasMeterService.getOne(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasMeterNumber, k)
                        .notIn(GasMeter::getDataStatus,5,6));
            }
            if(null == gasMeter){
                saveReadMeterError(v.get(0),"未找到表具档案");return;
            }
            GasMeterVersion gasMeterVersion = new GasMeterVersion();
            gasMeterVersion.setOrderSourceName(v.get(0).getMeterType());
            //气表使用情况
            GasMeterInfo gasMeterInfo = gasMeterInfoService.getByMeterAndCustomerCode(v.get(0).getGasMeterCode(),v.get(0).getCustomerCode());
            if(null == gasMeterInfo){
                saveReadMeterError(v.get(0),"未找到表具使用信息(户表账户)");return;
            }
            //获取设备当前的气价方案信息
            UseGasType useGasType = useGasTypeService.getById(v.get(0).getUseGasTypeId());
            if(null == useGasType){
                saveReadMeterError(v.get(0),"未找到用气类型");return;
            }
            for(ReadMeterDataIot e:v){
                Instant instant = e.getRecordTime().toInstant();
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDate recordDate = instant.atZone(zoneId).toLocalDate();
                if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(e.getMeterType())
                        &&e.getProcessStatus().eq(ProcessIotEnum.SETTLED)){
                    listCQ.add(e);continue;
                }
                log.info("==========结算前周期累计用气量===========:"+gasMeterInfo.getCycleUseGas());
                log.info("========================当前抄表数据=======================:"+JSON.toJSONString(e));
                //根据用气类型变更记录筛选方案
                PriceChangeVO priceChangeVO = new PriceChangeVO();
                priceChangeVO.setPriceId(gasMeterInfo.getPriceSchemeId());
                priceChangeVO.setGasMeterCode(e.getGasMeterCode());
                priceChangeVO.setRecordTime(recordDate);
                PriceScheme price = gasTypeChangeRecordService.getPriceSchemeByRecord(priceChangeVO);
                if(price!=null){
                    useGasType = useGasTypeService.getById(price.getUseGasTypeId());
                }
                isCycleClear = false;
                if(null == price){
                    //根据用气类型id查询价格方案
                    price = priceSchemeService.queryPriceByTime(useGasType.getId(),recordDate);
                    isCycleClear = true;
                }
                if(null == price){
                    saveReadMeterError(e,"未找到价格方案");continue;
                }
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
                    e.setRelPriceId(price.getId());
                    e.setRelUseGasTypeId(useGasType.getId());
                    gasMeterInfo.setCurrentPrice(settlementVO.getCurrentPrice());
                    if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(e.getMeterType())){
                        updateCalculateIot(e,gasMeterInfo,price,useGasType,account, gasMeter,settlementVO,calculateVO);
                    }else{
                        //更新抄表数据、气表使用情况、新增结算数据
                        GasmeterSettlementDetail gSDetail = updateCalculate(e,gasMeterInfo,price,useGasType,account,
                                gasMeter,settlementVO,calculateVO).getData();
                        //账户抵扣、欠费记录
                        accountWithhold(account,e,settlementVO,gSDetail,gasMeterInfo,gasMeter,useGasType,price,gasMeterVersion,v.get((v.size()-1)));
                    }
                }else if(PriceType.LADDER_PRICE.getCode().equals(useGasType.getPriceType())){
                    log.info("=================抄表数据以阶梯方案计算=================");
                    settlementCal(price,gasMeterInfo,gasMeter,price,account,e,useGasType,gasMeterVersion,type,isCycleClear,v.get((v.size()-1)));
                }else if(PriceType.HEATING_PRICE.getCode().equals(useGasType.getPriceType())){
                    //获取采暖方案
                    PriceScheme priceHeating = priceSchemeService.queryPriceHeatingByTime(useGasType.getId(),recordDate);
                    if(null == priceHeating){
                        saveReadMeterError(e,"未找到采暖价格方案");continue;
                    }
                    log.info("====================当前采暖价格方案===================");
                    log.info(JSON.toJSONString(price));
                    //采暖方案切换时间、抄表数据审核时间
                    LocalDate heatingDate = priceHeating.getCycleStartTime();
                    //判断抄表数据是否处于采暖季
                    if(recordDate.isAfter(heatingDate)||recordDate.isEqual(heatingDate)){
                        log.info("=================抄表数据处于采暖季，以采暖方案计算===============");
                        settlementCal(priceHeating,gasMeterInfo,gasMeter,price,account,e,useGasType,gasMeterVersion,type,isCycleClear,v.get((v.size()-1)));
                    }else{
                        log.info("===============抄表数据处于非采暖季，以非采暖方案计算===============");
                        settlementCal(price,gasMeterInfo,gasMeter,price,account,e,useGasType,gasMeterVersion,type,isCycleClear,v.get((v.size()-1)));
                    }
                }
                if(OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeterVersion.getOrderSourceName())){
                    listCQ.add(e);
                }
            }
        });
        listST = readMeterDataIotService.getArrearsRecord(exdate);
        //后付费账单处理
        createNB21FAccount(listST,listCQ,executeDate,type);
        return R.success(list);
    }

    /**
     * 生成后付费账单
     * @param listST
     * @param listCQ
     * @param executeDate
     * @param type
     */
    public void  createNB21FAccount(List<ReadMeterDataIot> listST,List<ReadMeterDataIot> listCQ,String executeDate,int type){
        if(type==0&&listST.size()>0){
            //是否中心后付费结算日
            Map<String, List<ReadMeterDataIot>> listMeterNo = listST.stream().
                    collect(Collectors.groupingBy(ReadMeterDataIot::getGasMeterCode));
            listMeterNo.forEach((k,v)->{
                Map<String, List<ReadMeterDataIot>> listMap = v.stream().collect(Collectors.groupingBy(item->DateUtil.format(item.getRecordTime(),"yyyy-MM")));
                listMap.forEach((ik,iv)->{
                    createNB21FArrearsMoney(iv,executeDate);
                });
            });
        }
        if(type==1&&listCQ.size()>0){
            //后付费场景收费生成账单
            Map<String, List<ReadMeterDataIot>> listMeterNo = listCQ.stream().
                    collect(Collectors.groupingBy(item->DateUtil.format(item.getRecordTime(),"yyyy-MM")));
            if(listMeterNo!=null&&listMeterNo.size()>0){
                listMeterNo.forEach((k,v)->{
                    createArrearsNB21FMoney(v);
                });
            }
        }
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

    private LocalDate isClearEx(PriceScheme price,ReadMeterDataIot meterData){
        DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String day = Integer.toString(price.getCycleEnableDate().getDayOfMonth());
        if(day.length()==1){day = "0"+day;}
        LocalDate begin = price.getCycleEnableDate();
        Instant instant = meterData.getRecordTime().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate recordDate = instant.atZone(zoneId).toLocalDate();
        LocalDate end = recordDate;
        int cycle = price.getCycle();
        int months = (end.getYear() - price.getStartTime().getYear())*12 + (end.getMonthValue()-begin.getMonthValue());

        if((months%cycle==0)){
            String month = Integer.toString(recordDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(recordDate.getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate;
        }else{
            String month = Integer.toString(recordDate.getMonthValue());
            if(month.length()==1){month = "0"+month;}
            LocalDate cycleDate = LocalDate.parse(recordDate.getYear()+"-"+month+"-"+day,fomatter);
            return cycleDate.minusMonths((end.getMonthValue()-begin.getMonthValue()));
        }
    }

    private CalculateVO isChangePriceClear(PriceScheme price,ReadMeterDataIot meterData,CalculateVO calculateVO,
                                           GasMeterInfo gasMeterInfo,boolean isCycleClear){
        int cycle = price.getCycle();
        LocalDate begin = price.getCycleEnableDate();
        LocalDate  djDate = price.getStartTime();
        LocalDate cycleDate =  isClearEx(price,meterData);
        Instant instant = meterData.getRecordTime().toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDate recordDate = instant.atZone(zoneId).toLocalDate();
        //是否周期清零
        if(recordDate.isBefore(cycleDate)){
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

    /**
     * 周期清零
     * @return
     */
    private CalculateVO cycleClear(ReadMeterDataIot meterData,CalculateVO calculateVO,
                                   GasMeterInfo gasMeterInfo,boolean isCycleClear,LocalDate localDate){
        //是否调价清零
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIot::getProcessStatus,ProcessEnum.SETTLED);
        wrapper.eq(ReadMeterDataIot::getGasMeterCode,meterData.getGasMeterCode());
//        wrapper.eq(ReadMeterDataIot::getCustomerCode,meterData.getCustomerCode());
        wrapper.lt(ReadMeterDataIot::getRecordTime,meterData.getRecordTime());
        wrapper.ge(ReadMeterDataIot::getRecordTime,localDate);
        List<ReadMeterDataIot> list = readMeterDataIotService.list(wrapper);
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

    /**
     * 处理场景抵扣退费
     */
    public void cQBusinessEvent(ReadMeterDataIot meterData){
        List<ReadMeterDataIot> cqList = readMeterDataIotService.getSettlementCQData(meterData.getGasMeterCode(),meterData.getCustomerCode());
        if(cqList!=null&&cqList.size()==1){
            //气表档案
            ReadMeterDataIot data = cqList.get(0);
            if(OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(data.getMeterType())){
                cqList = unSettlement(cqList).getData();
                data = cqList.get(0);
            }
            data.setDataType(0);
            data.setDataStatus(0);
            readMeterDataIotService.updateById(data);
        }
    }

    public boolean checkArrears(String gasCode,String customerCode,LocalDateTime sDate){
        GasmeterArrearsDetail arrearsParams = new GasmeterArrearsDetail();
        arrearsParams.setGasmeterCode(gasCode);
        arrearsParams.setCustomerCode(customerCode);
        String monthStr = Integer.toString(sDate.getMonthValue());
        if(monthStr.length()==1){monthStr="0"+monthStr;}
        String readMonth = sDate.getYear()+"-"+monthStr;
        arrearsParams.setReadmeterMonth(readMonth);
        return gasmeterArrearsDetailService.checkNB21Exits(arrearsParams);
    }

    public int checkArrearsMonth(String gasCode,String customerCode,LocalDateTime sDate){
        GasmeterArrearsDetail arrearsParams = new GasmeterArrearsDetail();
        arrearsParams.setGasmeterCode(gasCode);
        arrearsParams.setCustomerCode(customerCode);
        GasmeterArrearsDetail gs = gasmeterArrearsDetailService.checkArrearsMonth(arrearsParams);
        if(gs==null){return 0;}
        int months =(sDate.getYear()-Integer.parseInt(gs.getReadmeterMonth().split("-")[0]))*12+sDate.getMonthValue()-Integer.parseInt(gs.getReadmeterMonth().split("-")[1]);
        return months;
    }

    /**
     * 保存结算异常数据
     * @param readMeterDataIot
     */
    public void  saveReadMeterError(ReadMeterDataIot readMeterDataIot,String errMsg){
        readMeterDataIot.setReviewObjection(errMsg);
        log.error("客户:"+readMeterDataIot.getCustomerName()+errMsg);
        ReadMeterDataIotError error = BeanPlusUtil.toBean(readMeterDataIot,ReadMeterDataIotError.class);
        readMeterDataIotErrorService.save(error);
    }

    public SetUpParamsterModel<SetUpParamsterValueModel> getParamsList(String paraType,String value){
        SetUpParamsterModel<SetUpParamsterValueModel> paramsModel = new SetUpParamsterModel<>();
        paramsModel.setParaType(paraType);
        SetUpParamsterValueModel s = new SetUpParamsterValueModel();
        s.setValue(value);
        paramsModel.setParameter(s);
        return paramsModel;
    }

    /**
     * 指令维护
     * @param commandType 指令类型
     * @param commandData 指令信息
     * @param transactionNo 流水号
     * @param execStatus 执行状态
     */
    private CommondCaluteVO defendCommand(String gasMeterNumber, String gasMeterCode, String customerCode, Long orgId, String orgName,
                                          CommandType commandType, String commandData, String transactionNo, int execStatus, String error){
        CommondCaluteVO commandVO = new CommondCaluteVO();
        //保存指令信息
        IotGasMeterCommandSaveDTO command = new IotGasMeterCommandSaveDTO();
        command.setMeterNumber(gasMeterNumber);
        command.setCommandType(commandType);
        command.setCommandParameter(commandData);
        command.setGasMeterCode(gasMeterCode);
        command.setExecuteResult(execStatus);
        command.setTransactionNo(transactionNo);
        command.setOrgId(orgId);
        command.setOrgName(orgName);
        IotGasMeterCommand commData = iotGasMeterCommandBizApi.save(command).getData();
        commandVO.setIotGasMeterCommand(commData);
        //保存指令详情
        IotGasMeterCommandDetailSaveDTO commandDetail = new IotGasMeterCommandDetailSaveDTO();
        commandDetail.setTransactionNo(transactionNo);
        commandDetail.setCommandId(commData.getId());
        commandDetail.setCommandType(commandType);
        commandDetail.setExecuteTime(LocalDateTime.now());
        commandDetail.setCommandStatus(execStatus);
        commandDetail.setCommandParameter(commandData);
        commandDetail.setCustomerCode(customerCode);
        commandDetail.setGasMeterCode(gasMeterCode);
        commandDetail.setCommandStage((execStatus==0? CommandState.WaitSend.getCodeValue():
                execStatus));
        commandDetail.setMeterNumber(gasMeterNumber);
        commandDetail.setExecuteResult(execStatus);
        commandDetail.setErrorDesc(error);
        commandDetail.setOrgId(orgId);
        commandDetail.setOrgName(orgName);
        IotGasMeterCommandDetail iotGasMeterCommandDetail = iotGasMeterCommandDetailBizApi.save(commandDetail).getData();
        commandVO.setIotGasMeterCommandDetail(iotGasMeterCommandDetail);
        return commandVO;
    }
}