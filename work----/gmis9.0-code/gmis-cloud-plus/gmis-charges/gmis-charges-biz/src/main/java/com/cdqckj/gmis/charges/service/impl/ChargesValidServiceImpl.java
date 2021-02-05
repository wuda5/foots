package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.base.*;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.charges.constant.ChargeMessageConstants;
import com.cdqckj.gmis.charges.dao.ChargeRecordMapper;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.*;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.operateparam.entity.PurchaseLimit;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.operateparam.enumeration.ActivityScene;
import com.cdqckj.gmis.operateparam.service.GiveReductionConfService;
import com.cdqckj.gmis.operateparam.service.PurchaseLimitService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.BusinessHallService;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchPlusService;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.userarchive.service.PurchaseLimitCustomerService;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.common.util.CollectionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;
@Service
@Log4j2
@DS("#thread.tenant")
public class ChargesValidServiceImpl extends SuperCenterServiceImpl implements ChargesValidService {


    @Autowired
    GasmeterArrearsDetailService gasmeterArrearsDetailService;
    @Autowired
    ChargeItemRecordService chargeItemRecordService;
    @Autowired
    RechargeRecordService rechargeRecordService;
    @Autowired
    ChargeInsuranceDetailService chargeInsuranceDetailService;
    @Autowired
    CustomerSceneChargeOrderService customerSceneChargeService;

    @Autowired
    GasMeterInfoService gasMeterInfoService;

    @Autowired
    TollItemService tollItemService;
    //    @Autowired
//    TollItemCycleLastChargeRecordService tollItemCycleLastChargeRecordService;
    @Autowired
    CustomerSceneChargeOrderService customerSceneChargeOrderService;
    @Autowired
    TollItemChargeRecordService tollItemChargeRecordService;
    @Autowired
    ChargeRecordService chargeRecordService;

    @Autowired
    ChargeRefundService chargeRefundService;

    @Autowired
    ChargeRecordMapper chargeRecordMapper;
    @Autowired
    CustomerAccountService customerAccountService;
    @Autowired
    CustomerAccountSerialService customerAccountSerialService;
    @Autowired
    CustomerEnjoyActivityRecordService customerEnjoyActivityRecordService;
    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    GiveReductionConfService giveReductionConfService;
    @Autowired
    BusinessHallService businessHallService;
    @Autowired
    ChargeLoadService chargeLoadService;
    @Autowired
    OtherFeeLoadService otherFeeLoadService;
    @Autowired
    UseGasTypeService useGasTypeService;
    @Autowired
    CustomerService customerService;

    @Autowired
    PurchaseLimitService purchaseLimitService;

    @Autowired
    PurchaseLimitCustomerService purchaseLimitCustomerService;

    @Autowired
    CalculateService calculateService;

    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    ReadMeterDataService readMeterDataService;
    @Autowired
    UserService userService;
    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    FunctionSwitchPlusService functionSwitchPlusService;

    /**
     * 校验
     * @return
     */
    public R<Boolean> baseValid(ChargeRecord chargeRecord,GasMeter meter,Customer customer,ChargeDTO chargeDTO){
        setCommonParams(chargeRecord);
        if(StringUtils.isBlank(chargeRecord.getChargeChannel())){
            chargeRecord.setChargeChannel(ChargeChannelEnum.GT.getCode());
        }
        ChargeUtils.setNullFieldAsZero(chargeRecord);
        if(ChargeUtils.isHasNagate(chargeRecord)){
            log.info("参数存在负数");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_NAGATE));
        }

        if(chargeRecord.getReceivableMoney().compareTo(BigDecimal.ZERO)==0){
            if(CollectionUtils.isEmpty(chargeDTO.getItemList()) &&
                    chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)==0 &&
                    chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO)==0 &&
                    chargeRecord.getInsurancePremium().compareTo(BigDecimal.ZERO)==0
            ){
                log.info("无收费内容");
                throw BizException.wrap("无收费内容");
            }
//            log.info("应收不能为0，没有收费内容");
//            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_RECEIVEMONEY_ZERO));
        }
//      if(CollectionUtils.isEmpty(chargeDTO.getItemList())
//                && chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO)==0
//                && chargeRecord.getInsurancePremium().compareTo(BigDecimal.ZERO)==0
//                && chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)==0
//        ){
//            log.info("应收不能为0，没有收费内容");
//            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_RECEIVEMONEY_ZERO));
//        }

        //1.计算校验
        R<Boolean> valid=calculValid(chargeRecord);
        if(valid.getIsError()){
            return valid;
        }
        //2.支付方式校验
        valid=payMethodValid(chargeRecord);
        if(valid.getIsError()){
            return valid;
        }

        //3.表具客户基本信息校验
        valid=meterAndCustomerValid(chargeRecord,meter,customer);
        if(valid.getIsError()){
            return valid;
        }

        //4.操作员和营业厅限额校验
        valid=userAndBizHallLimitValid(chargeRecord);
        if(valid.getIsError()){
            return valid;
        }
        //5.表具用气类型限额和客户限额校验
        valid=meterUseGasTypeAndCustomerLimitValid(meter,customer,chargeRecord);
        if(valid.getIsError()){
            return valid;
        }
        //6.保险校验
        valid=   insuranceValid(chargeRecord,meter);
        if(valid.getIsError()){
            return valid;
        }
        //7.校验是否存在未完成的收费单或者退费单
        validUncompleteCharge(chargeRecord.getGasMeterCode(),chargeRecord.getCustomerCode());

        //8.充值相关计算和校验
        valid=rechargeValid(meter,chargeRecord,chargeDTO);
        if(valid.getIsError()){
            return R.fail(valid.getMsg(),valid.getDebugMsg());
        }

        //9.收费项相关计算校验
        valid=chargeItemCalculValid(chargeRecord,chargeDTO.getItemList());
        if(valid.getIsError()){
            return R.fail(valid.getMsg(),valid.getDebugMsg());
        }
        return valid;
    }

    public void validUncompleteCharge(String gasMeterCode,String customerCode){
        //7.校验是否存在未完成的支付收费单
        ChargeRecord checkObj=getUnCompleteChargeRecord(gasMeterCode,customerCode);
        if(checkObj!=null){
            log.error("存在未完成收费项");
            throw BizException.wrap(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CHARGE_UNCOMPLEMENT));
        }
        //8.校验是否存在未完成的退费单
        ChargeRefund refundObj=getUnCompleteChargeRefund(gasMeterCode,customerCode);
        if(refundObj!=null){
            log.error("存在未完成退费申请");
            throw BizException.wrap("存在未完成退费申请");
        }
    }

    public ChargeRecord getUnCompleteChargeRecord(String gasMeterCode,String customerCode){
        ChargeRecord checkObj=chargeRecordService.getOne(new LbuWrapper<ChargeRecord>()
                .eq(ChargeRecord::getGasMeterCode,gasMeterCode)
                .eq(ChargeRecord::getCustomerCode,customerCode)
                .eq(ChargeRecord::getDataStatus,DataStatusEnum.NORMAL.getValue())
                .eq(ChargeRecord::getChargeStatus,ChargeStatusEnum.UNCHARGE.getCode())
        );
        return checkObj;
    }
    public ChargeRefund getUnCompleteChargeRefund(String gasMeterCode,String customerCode){
        ChargeRefund refundObj=chargeRefundService.getOne(new LbuWrapper<ChargeRefund>()
                .eq(ChargeRefund::getGasMeterCode,gasMeterCode)
                .eq(ChargeRefund::getCustomerCode,customerCode)
                .in(ChargeRefund::getRefundStatus,RefundStatusEnum.WAITE_AUDIT.getCode(),
                        RefundStatusEnum.REFUND_ERR.getCode(),
                        RefundStatusEnum.APPLY.getCode(),
                        RefundStatusEnum.REFUNDABLE.getCode(),
                        RefundStatusEnum.THIRDPAY_REFUND.getCode()
                )
        );
        return refundObj;
    }
    /**
     * 基本计算校验
     * 涉及参数： 缴费金额，减免金额，收费金额，充值金额，充值气量，(优惠气量，优惠金额)，抵扣金额，应收金额，实收金额，预存金额，找零，保险费
     * 缴费金额-减免金额=收费金额，已在收费项校验中已处理。
     * 充值赠送（优惠相关）的在充值中校验中已处理。
     * 故：该方法主要校验， 收费金额，充值金额，抵扣金额，应收金额，实收金额，预存金额，找零，保险费
     * 应收金额=收费金额+充值金额+预存金额+保险费-抵扣金额
     * 实收金额=应收金额+找零 （如果找零放预存-也就是为0，那么实收金额=应收金额）
     * 预存金额和抵扣金额不应该同时存在，如果存入>=抵扣，实际就不会用上抵扣。如果存入小于抵扣，相当于抵扣额不应该抵扣那么多。故，要做计算和判断。
     * 如果有抵扣是否存在找零？正常情况可以的 需满足：找零<=实收
     * 如果有零存是否存在找零？一般来说，点零存是将所有零钱都存入到 预存账户，所以不应该还出现找零。这里有存入的概念，所以只要满足找零<=实收，可以出现不影响。
     * 非现金方式不应该存在找零
     *
     * @return
     */
    private R<Boolean> calculValid(ChargeRecord chargeRecord) {

        if(chargeRecord.getReductionMoney().compareTo(chargeRecord.getReceivableMoney())>0){
            throw BizException.wrap("抵扣金额不能大于应收金额");
        }

        if (chargeRecord.getReceivableMoney().compareTo(chargeRecord.getChargeMoney()
                .add(chargeRecord.getRechargeMoney())
                .add(chargeRecord.getPrechargeMoney()).add(chargeRecord.getInsurancePremium())) != 0) {
            log.info("应收金额!=收费金额+充值金额+预存金额+保险费");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_RECEIVABELMONEY));
        }
        if (chargeRecord.getActualIncomeMoney().compareTo(chargeRecord.getReceivableMoney()
                .add(chargeRecord.getGiveChange()).subtract(chargeRecord.getPrechargeDeductionMoney())) != 0
        ) {
            log.info("实收金额!=应收金额+找零金额-抵扣金额");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_ACTUALMONEY));
        }
        if (chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO) > 0
                && chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0) {
            log.info("抵扣金额和预存金额不能同时存在");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_REDUCTION_PRECHARGE_EXISTS));
        }
        if (chargeRecord.getGiveChange().compareTo(chargeRecord.getActualIncomeMoney()) > 0) {
            log.info("找零不能大于实收");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_GIVEMONEY_MORETHAN_ACTUALMONEY));
        }
        if (!ChargePayMethodEnum.CASH.getCode().equals(chargeRecord.getChargeMethodCode())
                && chargeRecord.getGiveChange().compareTo(BigDecimal.ZERO) != 0
        ) {
            log.info("非现金支付不能找零");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_PAYMETHOD_NOT_GIVEMONEY));
        }
        return R.success(true);
    }

    /**
     * 支付方式校验
     * @param chargeRecord
     * @return
     */
    private R<Boolean> payMethodValid(ChargeRecord chargeRecord){
        boolean hasMethod=false;
        for (ChargePayMethodEnum value : ChargePayMethodEnum.values()) {
            if(value.getCode().equals(chargeRecord.getChargeMethodCode())){
                hasMethod=true;
            }
        }
        if(!hasMethod){
            log.info("未知的支付方式");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_UNKNOWN_PAYMETHOD));
        }
        return R.success(true);
    }

    /**
     * 表具和客户基本信息校验
     * @return
     */
    private R<Boolean> meterAndCustomerValid(ChargeRecord chargeRecord,GasMeter meter,Customer customer){
        if (meter==null) {
            log.info("表具不可用");
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_NOT),"表具不可用");
        }
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        if(version==null){
            log.info("表具不可用");
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_NOT),"表具不可用");
        }
        chargeRecord.setChargeType(version.getOrderSourceName());
        chargeRecord.setSettlementType(version.getAmountMark());

        if (chargeRecord.getChargeType()==null || meter.getUseGasTypeId()==null ||chargeRecord.getSettlementType()==null) {
            log.info("表具信息不完善：收费类型，用气类型，结算类型");
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_UNCOMPLETEINFO),"表具信息不完整");
        }
        if(customer==null){
            log.info("未查询到客户信息");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CUSTOMER_VALID));
        }
        return R.success(true);
    }
    /**
     * 操作员和营业厅限额校验
     * @param chargeRecord
     * @return
     */
    private R<Boolean> userAndBizHallLimitValid(ChargeRecord chargeRecord){
        BigDecimal limitValidVal=chargeRecord.getActualIncomeMoney();
        //先校验限额，根据组织ID查询 未删除，且受限制的营业厅，如果没有记录不作限制校验
        BusinessHall businessHall=loadBusinessHall();
        if(businessHall!=null && businessHall.getRestrictStatus().intValue()== LimitStatusEnum.LIMIT.getValue()) {
            if (businessHall.getBalance() != null && businessHall.getBalance().compareTo(limitValidVal) <= 0) {
                log.info("营业厅操作余额不足");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_BIZHALL_MONEY_VALID));
            }
            if (businessHall.getSingleLimit() != null && businessHall.getSingleLimit().compareTo(limitValidVal) <= 0) {
                log.info("超过营业厅单笔限额");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_BIZHALL_MONEY_LIMIT));
            }
        }
        //验证操作员限额
        if(BaseContextHandler.getUserId()!=null) {
            User user = chargeRecordMapper.getUserById(BaseContextHandler.getUserId());
            if (user != null) {
                if (user.getPointOfSale() != null) {
                    if (user.getBalance().compareTo(limitValidVal) <= 0) {
                        log.info("个人操作余额不足");
                        return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_USER_MONEY_VALID));
                    }
                }
                if (user.getSingleLimit() != null) {
                    if (user.getSingleLimit().compareTo(limitValidVal) <= 0) {
                        log.info("超过个人单笔限额");
                        return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_USER_MONEY_LIMIT));
                    }
                }
            }
        }
        return R.success(true);
    }

    /**
     * 表具用气类型限额和配置的个人限额校验，用气类型相关限额校验
     * @return
     */
    private R<Boolean> meterUseGasTypeAndCustomerLimitValid(GasMeter meter,Customer customer,ChargeRecord chargeRecord){
        //校验用气类型上面的限额（充值气量的限额，充值金额的限额---针对充值的时候做限制）
        UseGasType useGasType=useGasTypeService.getById(meter.getUseGasTypeId());
        BigDecimal limitMoney=useGasType.getMaxChargeMoney();
        BigDecimal limitGas=useGasType.getMaxChargeGas();
        //如果有用户限额，用户限额，否则查看是否有用气类型配置限额，如果没有再直接使用用气类型上面的限额
        List<PurchaseLimitCustomer> limitCustomers= purchaseLimitCustomerService
                .list(new LbqWrapper<PurchaseLimitCustomer>()
                        .eq(PurchaseLimitCustomer::getGasMeterCode,meter.getGasCode())
                        .eq(PurchaseLimitCustomer::getCustomerCode,customer.getCustomerCode())
                        .eq(PurchaseLimitCustomer::getDeleteStatus,DeleteStatusEnum.NORMAL.getValue())
                );
        List<Long> limitIds = new ArrayList<>();
        //在限购名单中
        if(CollectionUtils.isNotEmpty(limitCustomers)){
            // 根据最小限额定 这里获取所有的配置方案ID
            limitIds.addAll(limitCustomers.stream().map(PurchaseLimitCustomer::getLimitId)
                    .collect(Collectors.toList()));
            //这个人被那些方案限购
            List<PurchaseLimit> allLimits= purchaseLimitService
                    .list(new LbqWrapper<PurchaseLimit>()
                            .in(PurchaseLimit::getId,limitIds)
                            .like(PurchaseLimit::getUseGasTypeId,"\""+meter.getUseGasTypeId()+"\"")
                            .eq(PurchaseLimit::getDataStatus,DataStatusEnum.NORMAL.getValue())
                    );
            //有限购方案
            if(CollectionUtils.isNotEmpty(allLimits)){
                //有限筛选出有个人气表独立配置的方案，如果没有个人气表独立配置方案，直接用用气类型配置方案
                //获取最小限制方案,判断金额表和气量表进行方方案计算
                Optional<PurchaseLimit> minLimit = allLimits.stream().collect(Collectors.minBy(Comparator.comparing(PurchaseLimit::getMaxChargeGas)));
                if(minLimit.isPresent()) {
                    PurchaseLimit limitObj=minLimit.get();
                    if(limitObj.getMaxChargeMoney()!=null){
                        if(limitMoney==null || limitObj.getMaxChargeMoney().compareTo(limitMoney)<0){
                            limitMoney=limitObj.getMaxChargeMoney();
                        }
                    }
    //                limitMoney=limitObj.getMaxChargeMoney();
    //                limitGas=limitObj.getMaxChargeGas();
                    if(limitObj.getMaxChargeGas()!=null){
                        if(limitGas==null || limitObj.getMaxChargeGas().compareTo(limitGas)<0){
                            limitGas=limitObj.getMaxChargeGas();
                        }
                    }
                }
            }
        }
        if(limitMoney!=null
                && chargeRecord.getSettlementType().equals(AmountMarkEnum.MONEY.getCode())
                && chargeRecord.getRechargeMoney().compareTo(limitMoney)>0
        ){
            log.info("充值金额超过用气类型限额");
            return R.fail("充值超过用气类型或者用户限额");
        }
        if(limitGas!=null
                && chargeRecord.getSettlementType().equals(AmountMarkEnum.GAS.getCode())
                && chargeRecord.getRechargeGas().compareTo(limitGas)>0
        ){
            log.info("充值气量超过用气类型限额");
            return R.fail("充值超过用气类型或者用户限额");
        }
        return R.success(true);
    }

    /**
     * 保险校验
     * @param chargeRecord
     * @return
     */
    private R<Boolean> insuranceValid(ChargeRecord chargeRecord,GasMeter meter){
        if(chargeRecord.getInsurancePremium().compareTo(BigDecimal.ZERO) > 0){
            if(chargeRecord.getInsuranceStartDate()==null || chargeRecord.getInsuranceEndDate()==null
                    || chargeRecord.getInsuranceStartDate().isBefore(LocalDate.now())
                    || chargeRecord.getInsuranceStartDate().isAfter(chargeRecord.getInsuranceEndDate())
                    || chargeRecord.getInsuranceStartDate().isEqual(chargeRecord.getInsuranceEndDate())
                    || (Period.between(chargeRecord.getInsuranceStartDate(),chargeRecord.getInsuranceEndDate()).getYears()==0&&
                    Period.between(chargeRecord.getInsuranceStartDate(),chargeRecord.getInsuranceEndDate()).getMonths()<1)
            ){
                log.info("保险日期范围不正确，不能小于当前，周期不能小于一个月");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_INSURANCE_DATEAREA));
            }else{
                List<ChargeInsuranceDetail> insurances = chargeInsuranceDetailService.list(
                        new LbqWrapper<ChargeInsuranceDetail>()
                                .eq(ChargeInsuranceDetail::getGasmeterCode,meter.getGasCode())
                                .eq(ChargeInsuranceDetail::getStatus,DataStatusEnum.NORMAL.getValue()));
                if(CollectionUtils.isNotEmpty(insurances)){
                    if(insurances.get(0).getInsuranceEndDate().isAfter(chargeRecord.getInsuranceStartDate())
                            || insurances.get(0).getInsuranceEndDate().isEqual(chargeRecord.getInsuranceStartDate())
                    ){
                        log.info("保险日期范围不能和上次保险周期范围重叠");
                        return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_INSURANCE_REPEAT));
                    }
                }
            }
        }
        return R.success(true);
    }


    /**
     * 充值及活动校验
     * @return
     */
    private R<Boolean> rechargeValid(GasMeter meter,ChargeRecord chargeRecord,ChargeDTO dto) {
        if(chargeRecord.getRechargeMoney()==null){
            chargeRecord.setRechargeMoney(BigDecimal.ZERO);
        }
        //后付费表直接返回
        if (OrderSourceNameEnum.READMETER_PAY.eq(chargeRecord.getChargeType()) &&
                (chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO) > 0
                        || chargeRecord.getRechargeGas().compareTo(BigDecimal.ZERO) > 0
                        || chargeRecord.getRechargeGiveGas().compareTo(BigDecimal.ZERO) > 0
                        || chargeRecord.getRechargeGiveMoney().compareTo(BigDecimal.ZERO) > 0
                        || CollectionUtils.isNotEmpty(dto.getActivityList()))
        ) {
            log.info("普表表不能存在充值数据");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_NORECHARGE_EXISTS));
        }
//        if ((OrderSourceNameEnum.READMETER_PAY.eq(chargeRecord.getChargeType()) ||
//                OrderSourceNameEnum.REMOTE_READMETER.eq(chargeRecord.getChargeType()) ) &&
//                chargeRecord.getReductionMoney().compareTo(BigDecimal.ZERO) > 0
//        ) {
////            log.info("普表表不能存在充值数据");
//            return R.fail(i18nUtil.getMessage("后付费表不能使用账户抵扣"));
//        }
//        if(chargeRecord.getReductionMoney().compareTo(chargeRecord.getRechargeMoney())>0){
//            throw BizException.wrap("抵扣金额不能大于充值金额");
//        }
        if(chargeRecord.getRechargeMoney()==null){
            chargeRecord.setRechargeMoney(BigDecimal.ZERO);
        }
        if(chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO) == 0 && (chargeRecord.getRechargeGas().compareTo(BigDecimal.ZERO) > 0
                || chargeRecord.getRechargeGiveGas().compareTo(BigDecimal.ZERO) > 0
                || chargeRecord.getRechargeGiveMoney().compareTo(BigDecimal.ZERO) > 0
                || CollectionUtils.isNotEmpty(dto.getActivityList()))){
            log.info("金额气量换算不正确，金额没有存在气量或者赠送");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CONVERSION_VALID));
        }
        //只有IC卡表气量表有气量，其他都不应该有气量
        if(!(OrderSourceNameEnum.IC_RECHARGE.eq(chargeRecord.getChargeType()) &&
                AmountMarkEnum.GAS.eq(chargeRecord.getSettlementType())) &&
                (chargeRecord.getRechargeGas().compareTo(BigDecimal.ZERO) > 0
                        || chargeRecord.getRechargeGiveGas().compareTo(BigDecimal.ZERO) > 0)
        ){
            log.info("金额表，不能有气量相关参数设置");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_MONEY_METER_GAS_EXISTS));
        }

        //没有使用活动不能有赠送
        if (CollectionUtils.isEmpty(dto.getActivityList())) {
            chargeRecord.setRechargeGiveGas(BigDecimal.ZERO);
            chargeRecord.setRechargeGiveMoney(BigDecimal.ZERO);
        }

        if (!OrderSourceNameEnum.READMETER_PAY.eq(chargeRecord.getChargeType())){
            if(chargeRecord.getRechargeMoney()!=null && chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO) > 0) {
                //如果有未完成的支付单在最开始就有校验。补卡，需要跳过该校验，应该在发起补卡的时候就处理掉该状态。如果补卡是补新用户卡，不补上次充值（上表），补上次充值（没上表）
                RechargeRecord rechargeRecord = rechargeRecordService.getOne(new LbqWrapper<RechargeRecord>()
                        .eq(RechargeRecord::getGasmeterCode, chargeRecord.getGasMeterCode())
                        .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
                        .in(RechargeRecord::getMoneyFlowStatus, MoneyFlowStatusEnum.waite_deal.getCode(),
                                MoneyFlowStatusEnum.waite_to_meter.getCode(),
                                MoneyFlowStatusEnum.deal_failure.getCode(),
                                MoneyFlowStatusEnum.meter_failure.getCode()
                        )
                );
                if (rechargeRecord != null) {
                    //该表具存在未上表的充值记录，不能充值
                    log.error("存在未完成的充值记录，不能充值");
                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_RECHARGE_UNCOMPLEMENT));
                }
            }
        }
        if(OrderSourceNameEnum.CENTER_RECHARGE.eq(chargeRecord.getChargeType())){
            GasMeterInfo gasMeterInfo=gasMeterInfoService.getByMeterAndCustomerCode(chargeRecord.getGasMeterCode(),chargeRecord.getCustomerCode());
            if(gasMeterInfo!=null && gasMeterInfo.getGasMeterBalance()!=null && gasMeterInfo.getGasMeterBalance().compareTo(BigDecimal.ZERO)<0){
                if(chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO)>0){
                     if(chargeRecord.getRechargeMoney().compareTo(gasMeterInfo.getGasMeterBalance().negate())<0){
                        log.error("未充值或者充值金额不能冲正欠费，不能预存");
                        return R.fail("未充值或者充值金额不能冲正欠费，不能预存");
                    }
                }else{
                    if(chargeRecord.getRechargeMoney().compareTo(gasMeterInfo.getGasMeterBalance().negate())<0){
                        log.error("未充值或者充值金额不能冲正欠费，请完成所有欠费缴纳");
                        return R.fail("未充值或者充值金额不能冲正欠费，请完成所有欠费缴纳");
                    }
                }
            }
        }
        //如果是气量表需要换算校验，如果要校验需要注意 换算方式差异问题
        if (chargeRecord.getSettlementType().equals(AmountMarkEnum.GAS.getCode())) {
            //换算后比较
//                R<BigDecimal> calculateR=calculateApi.conversion(new ConversionVO()
//                        .setConversionType(ConversionType.GAS)
//                        .setConversionValue(chargeRecord.getRechargeGas())
//                        .setGasMeterCode(meter.getGasCode())
//                        .setUseGasTypeId(meter.getUseGasTypeId()))
//                        ;
//                if(calculateR.getIsError()){
//                    log.info("金额气量换算接口调用，服务异常");
//                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CONVERSION_SERVICE_EX),
//                            "金额气量换算接口调用，服务异常;"+calculateR.getDebugMsg());
//                }
//                BigDecimal gasTransManey =calculateR.getData();
//                if (gasTransManey.compareTo(chargeRecord.getRechargeMoney()) != 0) {
//                    log.info("金额气量换算不正确");
//                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CONVERSION_VALID));
//                }
        }
        return giveActiveValid(chargeRecord,meter,dto.getActivityList());
    }

    /**
     * 赠送活动计算校验
     * @param actList
     * @param chargeRecord
     * @return
     */
    private R<Boolean> giveActiveValid(ChargeRecord chargeRecord,GasMeter meter,List<CustomerEnjoyActivityRecord> actList){
        if(CollectionUtils.isNotEmpty(actList)) {
            //使用了活动独立校验活动
            Map<Long, CustomerEnjoyActivityRecord> loadMap = chargeLoadService
                    .loadGiveReduction(meter, ActivityScene.RECHARGE_GIVE, null);

            CustomerEnjoyActivityRecord loadTemp;
            BigDecimal countActivity = new BigDecimal("0.00");
            for (CustomerEnjoyActivityRecord record : actList) {
                loadTemp = loadMap.get(record.getActivityId());
                if (loadTemp == null) {
                    log.info("存在未知的活动项");
                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_UNKNOWN_ACTIVITY));
                }
                //只需要校验金额方式是否是固定金额，如果是校验金额是否改变，如果不是则不需要校验
                ChargeUtils.setNullFieldAsZero(loadTemp);
                ChargeUtils.setNullFieldAsZero(record);
                if (record.getActivityMoneyType().equals(MoneyMethodEnum.percent.getCode())) {
                    countActivity = countActivity.add(chargeRecord.getSettlementType().equals(AmountMarkEnum.GAS.getCode()) ?
                            chargeRecord.getRechargeGas().multiply(record.getActivityPercent()).divide(new BigDecimal("100"))
                                    .setScale(2, BigDecimal.ROUND_DOWN) :
                            chargeRecord.getRechargeMoney().multiply(record.getActivityPercent().divide(new BigDecimal("100"))
                                    .setScale(2, BigDecimal.ROUND_DOWN)));
                } else {
                    countActivity = countActivity.add(chargeRecord.getSettlementType().equals(AmountMarkEnum.GAS.getCode()) ?
                            record.getGiveGas() :
                            record.getActivityMoney());
                }
                record.setChargeNo(chargeRecord.getChargeNo());
            }
            if(OrderSourceNameEnum.IC_RECHARGE.eq(chargeRecord.getChargeType())){
                //如果界面做了活动计算可以进行校验 ，看是否计算一致，进行再一次设值，确保赠送要么金额要么气量
                if (chargeRecord.getSettlementType().equals(AmountMarkEnum.GAS.getCode())) {
                    if (countActivity.compareTo(chargeRecord.getRechargeGiveGas()) != 0) {
                        log.error("计算赠送活动结果不正确");
                        return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_ACTIVITY));
                    }
                    chargeRecord.setRechargeGiveGas(countActivity);
                    chargeRecord.setRechargeGiveMoney(BigDecimal.ZERO);
                } else {
                    if (countActivity.compareTo(chargeRecord.getRechargeGiveMoney()) != 0) {
                        log.error("计算赠送活动结果不正确");
                        return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_ACTIVITY));
                    }
                    chargeRecord.setRechargeGiveMoney(countActivity);
                    chargeRecord.setRechargeGiveGas(BigDecimal.ZERO);
                }
            }else{
                if (countActivity.compareTo(chargeRecord.getRechargeGiveMoney()) != 0) {
                    log.error("计算赠送活动结果不正确");
                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_ACTIVITY));
                }
                chargeRecord.setRechargeGiveMoney(countActivity);
                chargeRecord.setRechargeGiveGas(BigDecimal.ZERO);
            }


        }
        return R.success(true);
    }

    /**
     * 收费项计算校验
     * 首先校验  收费金额（待缴纳的欠费合计）=缴费金额(燃气场景附加费等合计)-减免金额（减免项合计）
     * 其次是否出现未知收费项(该校验可以直接拿掉的，为了减少事务回滚所以尽量多做校验)
     * @return
     */
    private R<Boolean> chargeItemCalculValid(ChargeRecord chargeRecord,List<ChargeItemRecord> chargeItems) {
        if (CollectionUtils.isEmpty(chargeItems)) {
            if (chargeRecord.getPayableMoney().compareTo(BigDecimal.ZERO) > 0 || chargeRecord.getChargeMoney().compareTo(BigDecimal.ZERO) > 0) {
                log.info("没有收费项，不应该存在收费金额");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_NOITEM_CHARGEFEE_EXISTS));
            }else{
                return R.success(true);
            }
        }
        BigDecimal countItem = new BigDecimal("0.00");
        BigDecimal countDeItem = new BigDecimal("0.00");
        //先循环一遍将计算做了并且针对据校验需要加载那些数据 做标识，这样可以减少数据库访问。（最开始设计加载所有可能的收费项再做校验）

        for (ChargeItemRecord chargeItem : chargeItems) {

            if(chargeItem.getChargeItemMoney()==null){
                chargeItem.setChargeItemMoney(BigDecimal.ZERO);
            }
            if (ChargeItemEnum.REDUCTION.getCode().equals(chargeItem.getChargeItemSourceCode())) {
                countDeItem = countDeItem.add(chargeItem.getChargeItemMoney());
            }else{
                countItem = countItem.add(chargeItem.getChargeItemMoney());
            }
        }
        //减免如果出现负数，怎么办？直接设置0，还是提示不能操作。当前方案：不能操作
        if (countItem.compareTo(BigDecimal.ZERO) < 0) {
            log.info("收费金额不能为负数");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_CHARGEFEE_NAGATE));
        }
        if (chargeRecord.getPayableMoney().compareTo(countItem) != 0) {
            log.info("收费项计算的金额不正确");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_CHARGEFEE_VALID));
        }
        if (countDeItem.compareTo(chargeRecord.getReductionMoney()) != 0) {
            log.info("减免金额计算不正确");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_CALCUL_REDUCTION_VALID));
        }
        //未知收费项校验可以省略？否则在转换数据的时候存在多次查询，或者将未知收费项放到最外层校验可以共用加载数据对象
        return R.success(true);
    }
    /**
     * 结合账户校验
     */
    public R<Boolean> accountValid(ChargeRecord chargeRecord,CustomerAccount account,Boolean useAccountDec) {

        if(account== null){
            return R.fail(i18nUtil.getMessage(MessageConstants.G_ACCOUNT_UNOPEN));
        }
        if (!useAccountDec) {
            //未使用抵扣，就不能出现抵扣额
            if (chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0) {
                log.error("未使用抵扣不能有抵扣金额");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_REDUCTION_UNUSE_MONEY_EXISTS));
            }
        } else {
            if (chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0 &&
                    chargeRecord.getPrechargeDeductionMoney().compareTo(account.getAccountMoney()
                            .add(account.getGiveMoney())) > 0) {
                log.error("账户余额不足");
                return R.fail(i18nUtil.getMessage(MessageConstants.G_ACCOUNT_INA));
            }
        }
        return R.success(true);
    }
    /**
     * 验证各种收费项是否是原始收费项，有没有异常的收费项。
     * 目前只根据ID做了校验
     * @return
     */
    public R<Boolean> unknownItemValid(GasMeter meter,
                                        List<ChargeItemRecord> chargeItems,
                                        Map<Long, CustomerEnjoyActivityRecord> reductsLoadMap,
                                        List<ChargeItemRecord> gasFeeList
    ){
        if(CollectionUtils.isEmpty(chargeItems)){
            return R.success(true);
        }
//        Boolean hasSceneFee=false;
        List<Long> tollItemIds=new ArrayList<>();
        for (ChargeItemRecord chargeItem : chargeItems) {
            if (chargeItem.getTollItemId()!=null && ChargeItemEnum.REDUCTION.getCode()
                    .equals(chargeItem.getChargeItemSourceCode())) {
                tollItemIds.add(chargeItem.getTollItemId());
            }
//            if (ChargeItemEnum.SCENEFEE.getCode().equals(chargeItem.getChargeItemSourceCode())) {
////                hasSceneFee=true;
//            }
        }
//        Map<Long,ChargeItemRecord> sceneLoadMap=new HashMap<>();
//        if(hasSceneFee){
//            sceneLoadMap.putAll(chargeLoadService.loadSceneFee(meter.getGasCode()));
//        }
        Set gasFeeLoadSet=new HashSet();
        if(CollectionUtils.isNotEmpty(gasFeeList)) {
            for (ChargeItemRecord item : gasFeeList) {
                gasFeeLoadSet.add(item.getChargeItemSourceId());
            }
            R<Boolean> r= itemRemoveValid(gasFeeList,chargeItems);
            if(r.getIsError()) return r;
        }
        String sourceCode;
        Long sourceId;

        for (ChargeItemRecord chargeItem : chargeItems) {
            if(chargeItem.getChargeItemSourceId()==null){
                sourceId=null;
            }else{
                sourceId=Long.parseLong(chargeItem.getChargeItemSourceId());
            }

            sourceCode=chargeItem.getChargeItemSourceCode();
            if (ChargeItemEnum.REDUCTION.getCode().equals(sourceCode) && !reductsLoadMap.containsKey(sourceId)) {
                log.info("存在未知收费项{}-{}", sourceCode, sourceId);
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_UNKNOWN_CHARGEITEM));
            }
            if ((ChargeItemEnum.GASFEE.getCode().equals(sourceCode) ||
                    ChargeItemEnum.LAYPAYFEE.getCode().equals(sourceCode)) && !gasFeeLoadSet.contains(sourceId.toString())
            ) {
                log.info("存在未知收费项{}-{}", sourceCode, sourceId);
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_UNKNOWN_CHARGEITEM));
            }

//            if (ChargeItemEnum.SCENEFEE.getCode().equals(sourceCode) && !sceneLoadMap.containsKey(sourceId)) {
//                log.info("存在未知收费项{}-{}", sourceCode, sourceId);
//                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_UNKNOWN_CHARGEITEM));
//            }
//            Map<Long,TollItemChargeRecord> otherLoadMap=chargeLoadService.loadTollItem(meter);
//            if (ChargeItemEnum.OTHER.getCode().equals(sourceCode)) {
//                Boolean hasItem=false;
//                for (ChargeItemRecord other : others) {
//                    if(other.getTollItemId().equals(sourceId)){
//                        hasItem=true;
//                        break;
//                    }
//                }
//                if(!hasItem){
//                    log.info("存在未知收费项{}-{}", sourceCode, sourceId);
//                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_UNKNOWN_CHARGEITEM));
//
//                }
//           }
        }
        return R.success(true);
    }


    /**
     * 验证燃气费收费项是否有移除，移除的是否是独立的燃气费或者独立（有的情况）的滞纳金 ，移除的是否是最新的而不是之前的。
     * @return
     */
    private R<Boolean> itemRemoveValid(List<ChargeItemRecord> gasFeeList,List<ChargeItemRecord> chargeItems){
        Map<String, ChargeItemRecord> gasFee=new HashMap<>();
        Map<String, ChargeItemRecord> lateFee=new HashMap<>();
        Map<Long, ChargeItemRecord> reductionItemMap=new HashMap<>();
        List<Long> reductionItemIds=new ArrayList<>();
        //除燃气费，滞纳金，减免项外
        Map<Long, ChargeItemRecord> otherChargeItemsMap=new HashMap<>();

        int gasFeeReCount=0;
        for (ChargeItemRecord chargeItem : chargeItems) {
            if(chargeItem.getChargeItemSourceCode().equals(ChargeItemEnum.GASFEE.getCode())){
                gasFee.put(chargeItem.getChargeItemSourceId(),chargeItem);
            }else if(chargeItem.getChargeItemSourceCode().equals(ChargeItemEnum.LAYPAYFEE.getCode())){
                lateFee.put(chargeItem.getChargeItemSourceId(),chargeItem);
            }else if(chargeItem.getChargeItemSourceCode().equals(ChargeItemEnum.REDUCTION.getCode())){
                //收费项ID如果已有减免项要提示
                if(!TolltemSceneEnum.GAS_FEE.getCode().equals(chargeItem.getTollItemScene())) {
                    if (reductionItemMap.containsKey(chargeItem.getTollItemId())) {
                        log.info("收费项已存在减免项（一个收费项只能使用一个减免项）。");
                        return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_REITEM_EXISTS));
                    } else {
                        reductionItemMap.put(chargeItem.getTollItemId(), chargeItem);
                    }
                    reductionItemIds.add(chargeItem.getTollItemId());
                }
            }else{
                //其他收费项，用于判断减免项是否有对应收费项
                otherChargeItemsMap.put(chargeItem.getTollItemId(), chargeItem);
            }

            if(ChargeItemEnum.REDUCTION.getCode().equals(chargeItem.getChargeItemSourceCode())
                    && TolltemSceneEnum.GAS_FEE.getCode().equals(chargeItem.getTollItemScene())
            ){
                gasFeeReCount++;
            }
        }
        //先校验是否有单独删除了滞纳金 或者收费项的内容
        Map<String, ChargeItemRecord> gasFeeLoad=new HashMap<>();
        Map<String, ChargeItemRecord> lateFeeLoad=new HashMap<>();
        for (ChargeItemRecord chargeItem : gasFeeList) {
            if(chargeItem.getChargeItemSourceCode().equals(ChargeItemEnum.GASFEE.getCode())){
                gasFeeLoad.put(chargeItem.getChargeItemSourceId(),chargeItem);
            }else if(chargeItem.getChargeItemSourceCode().equals(ChargeItemEnum.LAYPAYFEE.getCode())){
                lateFeeLoad.put(chargeItem.getChargeItemSourceId(),chargeItem);
            }
        }
        //校验是否存在只有滞纳金没有收费项
        for (Map.Entry<String, ChargeItemRecord> item : lateFee.entrySet()) {
            if(!gasFee.containsKey(item.getValue().getChargeItemSourceId())){
                log.info("同一笔欠费的燃气费和滞纳金必须同时收取");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_BIZ_GASFEE_LATEFEE_MASTTOGETHER));
            }
        }
        //校验是否存在本有滞纳金，但是只存在燃气费收费项，不存在滞纳金收费项
        for (Map.Entry<String, ChargeItemRecord> item : gasFee.entrySet()) {
            if(lateFeeLoad.containsKey(item.getKey())
                    && !lateFee.containsKey(item.getKey())){
                log.info("同一笔欠费的燃气费和滞纳金必须同时收取");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_BIZ_GASFEE_LATEFEE_MASTTOGETHER));
            }
        }
        //校验是否删除了较早的收费项，不是删除的最新的。 --这里只用根据燃气费收费项校验就可以了
        List<ChargeItemRecord> gasFeeDel=new ArrayList<>();
        for (String key : gasFeeLoad.keySet()) {
            if(!gasFee.containsKey(key)){
                gasFeeDel.add(gasFeeLoad.get(key));
            }
        }
        for (ChargeItemRecord item : gasFee.values()) {
            for (ChargeItemRecord delItem : gasFeeDel) {
                //收费项存在 删除项之后的，说明删除数据逻辑错误，只能删除最新的 202007 202008 只能删除202008
                if(item.getChargeItemTime().isAfter(delItem.getChargeItemTime())){
                    log.info("燃气费存在更早的未缴纳收费项");
                    return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_BIZ_GASSFEE_DATE_SEQUENCE));
                }
            }
        }
        if(gasFee.size()==0 && gasFeeReCount>1){
            log.info("燃气费减免项不能单独存在，且只能有一个。");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_GASFEE_REITEM_EXISTS));
            //这里可以校验减免金额是否满足减免后>=0
        }
        for (Long key : reductionItemIds) {
            if(!otherChargeItemsMap.containsKey(key)){
                log.info("存在未知的减免项");
                return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_PARAM_UNKNOWN_REDUCTION_ITEM));
            }
            //这里可以校验减免金额是否满足>=0
        }
        return R.success(true);
    }

    private BusinessHall loadBusinessHall(){
        Long orgId=BaseContextHandler.getOrgId();
        if(orgId!=null) {
            List<BusinessHall> businessHalls = businessHallService.list(
                    new LbqWrapper<BusinessHall>().eq(BusinessHall::getOrgId, orgId)
                            .eq(BusinessHall::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue()));
            //有限额控制，分别校验营业厅余额和单笔限额
            if (CollectionUtils.isNotEmpty(businessHalls)) {
                return businessHalls.get(0);
            }
        }
        return null;
    }

}
