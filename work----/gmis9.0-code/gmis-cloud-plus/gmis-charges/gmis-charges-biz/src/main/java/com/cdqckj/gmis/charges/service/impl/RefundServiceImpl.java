package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.constant.ChargeMessageConstants;
import com.cdqckj.gmis.charges.dao.ChargeRefundMapper;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.*;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.BusinessHallService;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class RefundServiceImpl extends SuperServiceImpl<ChargeRefundMapper, ChargeRefund> implements RefundService {
    @Autowired
    GasMeterInfoService gasMeterInfoService;

    @Autowired
    GasMeterInfoSerialService gasMeterInfoSerialService;

    @Autowired
    GasMeterVersionService gasMeterVersionService;

    @Autowired
    GasMeterService gasMeterService;
    @Autowired
    ChargeRecordService chargeRecordService;
    @Autowired
    RechargeRecordService rechargeRecordService;
    @Autowired
    ChargeItemRecordService chargeItemRecordService;
    @Autowired
    CustomerAccountService customerAccountService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ChargeRefundService chargeRefundService;
    @Autowired
    OtherFeeRecordService otherFeeRecordService;
    @Autowired
    CustomerSceneChargeOrderService customerSceneChargeOrderService;
    @Autowired
    GasmeterArrearsDetailService gasmeterArrearsDetailService;
    @Autowired
    CustomerEnjoyActivityRecordService customerEnjoyActivityRecordService;

    @Autowired
    ChargeInsuranceDetailService chargeInsuranceDetailService;

    @Autowired
    CustomerAccountSerialService customerAccountSerialService;

    @Autowired
    BusinessHallService businessHallService;

    @Autowired
    UserService userService;

    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    RefundSerialService refundSerialService;
    @Autowired
    RefundCheckService refundCheckService;

    @Transactional(rollbackFor = Exception.class)
    public R<ChargeRefund> apply(RefundApplySaveReqDTO applyDTO) {
        RefundCheckDTO check=refundCheckService.validRefund(applyDTO.getChargeNo());
        if (!check.getIsApplyRefund()) {
            return R.fail(check.getNonRefund());
        }
        ChargeRecord chargeRecord=check.getChargeRecord();
        if(RefundMethodEnum.get(applyDTO.getRefundMethod())==null){
            throw BizException.wrap("不支持的退费方式");
        }
        ChargeRefund refundCheck= chargeRefundService.getOne(
                new LbqWrapper<ChargeRefund>()
                        .eq(ChargeRefund:: getChargeNo,chargeRecord.getChargeNo())
                .notIn(ChargeRefund:: getRefundStatus,
                        RefundStatusEnum.NONREFUND.getCode(),
                        RefundStatusEnum.CANCEL.getCode(),
                        RefundStatusEnum.REFUNDED.getCode())
        );
        if(refundCheck!=null){
            throw BizException.wrap("存在未完成的退费申请,不能重复申请");
        }
        if(ChargePayMethodEnum.CASH.eq(chargeRecord.getChargeMethodCode())){
            if(RefundMethodEnum.PAY_METHOD_REFUND.eq(applyDTO.getRefundMethod())){
                throw BizException.wrap("现金支付不能使用其他方式退费");
            }
        } else{
            if(RefundMethodEnum.CASH.eq(applyDTO.getRefundMethod())){
                throw BizException.wrap("暂时不支持非现金支付的收费单使用现金退费");
            }
        }
        //抵扣的往账户上加 。这里不做校验。
        ChargeRefund refund=new ChargeRefund()
//                .setRefundNo(BizCodeUtil.genRefundDataCode())
                .setRefundNo(BizCodeNewUtil.genRefundNo())
                //RefundStatus.APPLY.getCode(),正常应该是申请状态，然后审核,这里默认审核通过
                .setRefundStatus(RefundStatusEnum.APPLY.getCode())
                .setApplyUserId(BaseContextHandler.getUserId())
                .setApplyUserName(BaseContextHandler.getName())
                .setChargeMethodCode(chargeRecord.getChargeMethodCode())
                .setChargeMethodName(chargeRecord.getChargeMethodName())
                .setBackMethodCode(applyDTO.getRefundMethod())
                .setBackMethodName(RefundMethodEnum.get(applyDTO.getRefundMethod()).getDesc())
//                .setAuditRemark(null)
//                .setAuditUserId(BaseContextHandler.getUserId())
//                .setAuditUserName(BaseContextHandler.getName())
                //退款实际应该是实收-找零，其他不管是充值还是预存都应该一起退，已找零就不退了。
                .setBackAmount(chargeRecord.getActualIncomeMoney().subtract(chargeRecord.getGiveChange()))
                .setBackReason(applyDTO.getRefundReason())
                .setChargeUserId(chargeRecord.getCreateUserId())
                .setChargeUserName(chargeRecord.getCreateUserName())
                .setChargeNo(chargeRecord.getChargeNo())
                .setChargeTime(chargeRecord.getCreateTime())
                .setInvoiceNo(chargeRecord.getInvoiceNo())
                .setInvoiceType(chargeRecord.getInvoiceType())
                .setBusinessHallId(chargeRecord.getBusinessHallId())
                .setBusinessHallName(chargeRecord.getBusinessHallName())
                .setCustomerCode(chargeRecord.getCustomerCode())
                .setCustomerName(chargeRecord.getCustomerName())
                .setGasMeterCode(chargeRecord.getGasMeterCode())
                .setCustomerChargeNo(chargeRecord.getCustomerChargeNo())
                .setApplyTime(LocalDateTime.now())
                ;
        if(applyDTO.getSubmitAudit()){
            refund.setRefundStatus(RefundStatusEnum.WAITE_AUDIT.getCode());
        }
        refund.setWhetherNoCard(applyDTO.getWhetherNoCard());
        //冻结账户预存金额和户表充值金额（物联网三种表都需要冻结）
        frozenMeterAccount(chargeRecord);
        frozenAccount(chargeRecord,refund.getRefundNo());
        markChargeRecordRefund(chargeRecord);
       chargeRefundService.save(refund);
       return R.success(refund);
    }
    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> audit(RefundAuditSaveReqDTO auditDTO){
        ChargeRefund refund= chargeRefundService.getById(auditDTO.getRefundId());
        if(refund==null || !RefundStatusEnum.WAITE_AUDIT.eq(refund.getRefundStatus())){
            //异常进入
            return R.fail("异常进入，不存在退费记录或者审核状态不是待审核");
        }

        ChargeRefund updateDTO=new ChargeRefund();
        updateDTO.setAuditTime(LocalDateTime.now());
        if(auditDTO.getStatus()){
            updateDTO.setRefundStatus(RefundStatusEnum.REFUNDABLE.getCode());
        }else{
            //不予退费
            cancelRefundBackAmount(refund);
            updateDTO.setRefundStatus(RefundStatusEnum.NONREFUND.getCode());
        }
        updateDTO.setId(auditDTO.getRefundId());
        updateDTO.setAuditRemark(auditDTO.getAuditOpinion());
        updateDTO.setAuditUserId(BaseContextHandler.getUserId());
        updateDTO.setAuditUserName(BaseContextHandler.getName());
        return R.success(chargeRefundService.updateById(updateDTO));
    }

    @Transactional(rollbackFor = Exception.class)
    public R<Boolean> cancelRefund(Long refundId){
        ChargeRefund refund= chargeRefundService.getById(refundId);
        if(refund==null){
            throw BizException.wrap("未知操作");
        }
        if(RefundStatusEnum.NONREFUND.eq(refund.getRefundStatus())){
            throw BizException.wrap("该单据已完成，不予退费，不能继续操作");
        }
        if(RefundStatusEnum.REFUNDED.eq(refund.getRefundStatus())){
            throw BizException.wrap("已退费完成不能取消");
        }
        cancelRefundBackAmount(refund);

        ChargeRefund updateDTO=new ChargeRefund();
        updateDTO.setRefundStatus(RefundStatusEnum.CANCEL.getCode());
        updateDTO.setId(refund.getId());
        return R.success(chargeRefundService.updateById(updateDTO));
    }
    private void cancelRefundBackAmount(ChargeRefund refund){
        ChargeRecord chargeRecord= chargeRecordService.getOne( new LbqWrapper<ChargeRecord>()
                .eq(ChargeRecord:: getChargeNo,refund.getChargeNo())
                .eq(ChargeRecord:: getChargeStatus,ChargeStatusEnum.CHARGED.getCode())
                .eq(ChargeRecord:: getDataStatus,DataStatusEnum.NORMAL.getValue()));
        rebackChargeRecordRefund(chargeRecord);
        ChargeUtils.setNullFieldAsZero(BigDecimal.ZERO);
        if(chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO)>0){
            CustomerAccount account = customerAccountService.queryAccountByCharge(chargeRecord.getCustomerCode());
            CustomerAccountSerial saveDTO=refundSerialService.createCancelBackAndUnfreezeAccountSeria(account,chargeRecord);
            customerAccountSerialService.save(saveDTO);

            //账户金额变更
            CustomerAccount accountUpdateDTO = new CustomerAccount();
            BeanUtils.copyProperties(account, accountUpdateDTO);
            accountUpdateDTO.setId(account.getId());
            accountUpdateDTO.setAccountMoney(saveDTO.getBookAfterMoney());
            accountUpdateDTO.setGiveMoney(saveDTO.getGiveBookAfterMoney());
            Boolean udpate=customerAccountService.updateById(accountUpdateDTO);
            if(!udpate){
                throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX));
            }
        }
        //解冻户表退回资金到账户
        cancelBackAndUnfreezeMeterAccount(chargeRecord);
        refundSerialService.saveUnFreezeCustomerMeterAccountSerial(chargeRecord);
    }


    @Transactional(rollbackFor = Exception.class)
    public R<RefundResultDTO> refund(Long refundId) {
        ChargeRefund refund = chargeRefundService.getById(refundId);
        if (refund == null) {
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_REFUND_APPLY_LOAD_SERVICE_EX));
        }
        if (!RefundStatusEnum.REFUNDABLE.getCode().equals(refund.getRefundStatus())) {
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_UNREFUND_BIZ_LIMIT)+refund.getRefundStatus());
        }
        ChargeRecord chargeRecord= chargeRecordService.getOne( new LbqWrapper<ChargeRecord>()
                .eq(ChargeRecord:: getChargeNo,refund.getChargeNo())
                .eq(ChargeRecord:: getChargeStatus,ChargeStatusEnum.CHARGED.getCode())
                .eq(ChargeRecord:: getDataStatus,DataStatusEnum.NORMAL.getValue()));
        if(chargeRecord==null){
            throw BizException.wrap("收费记录不存在或已退费");
        }
        if(RefundMethodEnum.PAY_METHOD_REFUND.eq(refund.getBackMethodCode())){
            ChargeRefund update=new ChargeRefund();
            update.setId(refundId);
            update.setRefundStatus(RefundStatusEnum.THIRDPAY_REFUND.getCode());
            update.setRefundTime(LocalDateTime.now());
            chargeRefundService.updateById(update);
            RefundResultDTO refundResultDTO=new RefundResultDTO();
            refundResultDTO.setChargeRefund(chargeRefundService.getById(refund));
            return R.success(refundResultDTO);
        }else{
            RefundCompleteDTO refundCompleteDTO=new RefundCompleteDTO();
            refundCompleteDTO.setChargeNo(refund.getChargeNo());
            refundCompleteDTO.setRefundNo(refund.getRefundNo());
            refundCompleteDTO.setRefundStatus(true);
            return refundComplete(refundCompleteDTO);
        }
    }

    public ChargeRefund getByRefundNo(String refundNo) {
        ChargeRefund refund = chargeRefundService.getOne(Wraps.<ChargeRefund>lbQ().eq(ChargeRefund::getRefundNo,refundNo));
        return refund;
    }

    @Transactional(rollbackFor = Exception.class)
    public R<RefundResultDTO> refundComplete(RefundCompleteDTO refundInfo) {
        String refundNo=refundInfo.getRefundNo();
        Long rechargeId = null;
        ChargeRefund refund = chargeRefundService.getOne(Wraps.<ChargeRefund>lbQ().eq(ChargeRefund::getRefundNo,refundNo));

        if(!refundInfo.getRefundStatus()){
            ChargeRefund update=new ChargeRefund();
            update.setId(refund.getId());
            update.setRefundStatus(RefundStatusEnum.REFUND_ERR.getCode());
            update.setBackFinishTime(LocalDateTime.now());
            update.setBackResult(refundInfo.getRefundRemark());
            chargeRefundService.updateById(update);
            RefundResultDTO refundResultDTO=new RefundResultDTO();
            refundResultDTO.setChargeRefund(chargeRefundService.getById(refund));
            return R.success(refundResultDTO);
        }

        ChargeRecord chargeRecord= chargeRecordService.getOne( new LbqWrapper<ChargeRecord>()
                .eq(ChargeRecord:: getChargeNo,refund.getChargeNo())
                .eq(ChargeRecord:: getChargeStatus,ChargeStatusEnum.CHARGED.getCode())
                .eq(ChargeRecord:: getDataStatus,DataStatusEnum.NORMAL.getValue()));
        if(chargeRecord==null){
            throw BizException.wrap("收费记录不存在或已退费");
        }
        List<ChargeItemRecord> items=chargeItemRecordService.list(new LbqWrapper<ChargeItemRecord>()
                .eq(ChargeItemRecord::getChargeNo,refund.getChargeNo()));
        List<Long> otherIds = new ArrayList<>();
//            List<Long> activeIds = new ArrayList<>();
        List<Long> sceneIds = new ArrayList<>();
        Long insuranceId = null;
        List<Long> arrearIds = new ArrayList<>();
        List<Long> updates = new ArrayList<>();
        List<Long> adjIds=new ArrayList<>();

        //针对应有的所有收费项做状态还原。

        if (CollectionUtils.isNotEmpty(items)) {
            Long id;
            for (ChargeItemRecord item : items) {
                if(item.getChargeItemSourceId()!=null){
                    id=Long.parseLong(item.getChargeItemSourceId());
                }else{
                    id=null;
                }
                if (item.getChargeItemSourceCode().equals(ChargeItemEnum.GASFEE.getCode())) {
                    //燃气费直接修改欠费状态。对于滞纳金的计算会有影响，不过缴费前独立计算一次滞纳金
                    arrearIds.add(id);
                } else if (item.getChargeItemSourceCode().equals(ChargeItemEnum.SCENEFEE.getCode())) {
                    //判断是否完成业务订单修改，拦截处理消息队列，拦截逻辑
                    sceneIds.add(id);
                } else if (item.getChargeItemSourceCode().equals(ChargeItemEnum.REDUCTION.getCode())) {
                    //作废减免项, 赠送和减免
//                        activeIds.add(id);
                } else if (item.getChargeItemSourceCode().equals(ChargeItemEnum.RECHARGE.getCode())) {
                    //在申请单已拦截下发，所以这里直接作废充值记录
                    rechargeId = id;
                } else if (item.getChargeItemSourceCode().equals(ChargeItemEnum.INSURANCE.getCode())) {
                    //还原保险状态到上次购买状态
                    insuranceId = id;
                } else if (item.getChargeItemSourceCode().equals(ChargeItemEnum.OTHER.getCode())) {
                    //作废收费项记录，并还原周期收费项为上次收费截止日期。
                    if(id!=null){
                        otherIds.add(id);
                    }
                }else if(item.getChargeItemSourceCode().equals(ChargeItemEnum.ADJUST_PRICE.getCode())){
                    adjIds.add(id);
                }
            }

        }
        saveAccount(chargeRecord,refund);
        cancelRecharge(rechargeId);
        cancelInsurance(insuranceId);
        cancelOtherFeeItem(otherIds,chargeRecord.getChargeNo());
        restoreArrearsDetailStatus(arrearIds);
        Long repCardId=restoreSceneOrderStatus(sceneIds);
        cancelReductAndGive(refund.getChargeNo());
        cancelChargeItems(updates);
        cancelChargeRecord(chargeRecord);
        updateRefund(refund.getId(),RefundMethodEnum.PAY_METHOD_REFUND.eq(refund.getBackMethodCode()));
        refundSerialService.refundFrozenAccountSeria(refund.getRefundNo());
        refundSerialService.refundCustomerMeterAccountSerial(refund.getRefundNo());
        RefundResultDTO refundResultDTO=new RefundResultDTO();
        refundResultDTO.setChargeRefund(chargeRefundService.getById(refund));
        refundResultDTO.setChargeRecord(chargeRecord);
        refundResultDTO.setArrearIds(arrearIds);
        refundResultDTO.setSceneIds(sceneIds);
        refundResultDTO.setAdjIds(adjIds);
        dealLimit(chargeRecord);
        refundResultDTO.setRepCardId(repCardId);
        return R.success(refundResultDTO);
    }
    public void dealLimit(ChargeRecord record){
        try {
            BigDecimal del = record.getReceivableMoney();
            if(BaseContextHandler.getUserId()==null) return ;
            User user = userService.getById(BaseContextHandler.getUserId());
            if (user != null) {
                if (user.getPointOfSale() != null) {
                    //配额不等于空，计算结算额
                    User userUpdate = new User();
                    userUpdate.setId(user.getId());
                    if (user.getBalance() != null) {
                        userUpdate.setBalance(user.getBalance().add(del));
                    } else {
                        userUpdate.setBalance(user.getPointOfSale().add(del));
                    }
                    if (userUpdate.getBalance().compareTo(user.getPointOfSale()) > 0) {
                        userUpdate.setBalance(user.getPointOfSale());
                    }
                    userService.updateById(userUpdate);
                }
            }

            Long orgId = BaseContextHandler.getOrgId();
            BusinessHall businessHall = null;
            if (orgId != null) {
                List<BusinessHall> businessHalls = businessHallService.list(
                        new LbqWrapper<BusinessHall>().eq(BusinessHall::getOrgId, orgId)
                                .eq(BusinessHall::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue()));
                //有限额控制，分别校验营业厅余额和单笔限额
                if (CollectionUtils.isNotEmpty(businessHalls)) {
                    businessHall = businessHalls.get(0);
                }
            }
            if (businessHall != null) {
                if (businessHall.getPointOfSale() != null) {
                    //配额不等于空，计算结算额
                    BusinessHall bizHallUpdate = new BusinessHall();
                    bizHallUpdate.setId(businessHall.getId());
                    if (businessHall.getBalance() != null) {
                        bizHallUpdate.setBalance(businessHall.getBalance().add(del));
                    } else {
                        bizHallUpdate.setBalance(new BigDecimal(businessHall.getPointOfSale()).add(del));
                    }
                    if (bizHallUpdate.getBalance().compareTo(new BigDecimal(businessHall.getPointOfSale())) > 0) {
                        bizHallUpdate.setBalance(new BigDecimal(businessHall.getPointOfSale()));
                    }
                    businessHallService.updateById(bizHallUpdate);
                }
            }
        }catch (Exception e){
            log.error("个人及营业厅限额恢复异常",e);
        }
    }
    /**
     * 修改退单状态
     */
    private void updateRefund(Long refundId,boolean isThirdRefund){
        ChargeRefund refund= new ChargeRefund().setRefundStatus(RefundStatusEnum.REFUNDED.getCode())
                .setBackFinishTime(LocalDateTime.now());
        if(!isThirdRefund){
            refund.setRefundTime(LocalDateTime.now());
        }
        refund.setBackFinishTime(LocalDateTime.now());
        refund.setId(refundId);
        Boolean update=chargeRefundService.updateById(refund);
        if(!update){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_REFUND_SERVICE_EX));
        }
    }

    /**
     * 作废收费记录
     */
    private void cancelChargeRecord(ChargeRecord chargeRecord ){
        ChargeRecord update=new ChargeRecord();
        update.setId(chargeRecord.getId());
        update.setRefundStatus(RefundStatusEnum.REFUNDED.getCode());
        Boolean updateR=chargeRecordService.updateById(update);
        if(!updateR){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_CANCEL_CHARGERECORD_SERVICE_EX));
        }
    }


    /**
     * 申请成功后，标记收费记录已申请退费中
     */
    private void markChargeRecordRefund(ChargeRecord chargeRecord ){
        ChargeRecord update=new ChargeRecord();
        update.setId(chargeRecord.getId());
        update.setRefundStatus(RefundStatusEnum.APPLY.getCode());
        chargeRecordService.updateById(update);

    }


    /**
     * 还原收费记录中的退费状态
     */
    private void rebackChargeRecordRefund(ChargeRecord chargeRecord ){
        ChargeRecord update=new ChargeRecord();
        update.setId(chargeRecord.getId());
        update.setRefundStatus(RefundStatusEnum.NOMAL.getCode());
        chargeRecordService.updateById(update);

//        chargeRecordService.rebackRefundStatus(chargeRecord.getId());
    }

    /**
     * 附加费相关收费项作废
     * 新增的收费项，直接作废
     * @return
     */
    private void cancelOtherFeeItem(List<Long> otherIds,String chargeNo){
        if(CollectionUtils.isEmpty(otherIds)) return ;
        OtherFeeRecord updateEntity=new OtherFeeRecord()
                .setChargeStatus(ChargeStatusEnum.UNCHARGE.getCode());

        otherFeeRecordService.update(updateEntity,new LbqWrapper<OtherFeeRecord>()
                .eq(OtherFeeRecord::getChargeNo,chargeNo)
                .in(OtherFeeRecord::getTollItemId,otherIds)
        );
    }

    /**
     * 修改场景费用单状态，并且将待处理业务单数据放到消息队列，有场景收费才会修改状态。
     * @return
     */
    private Long restoreSceneOrderStatus(List<Long> sceneIds){
        if(CollectionUtils.isEmpty(sceneIds)) return null;
        List<CustomerSceneChargeOrder> updates=new ArrayList<>();
        List<CustomerSceneChargeOrder> sorders=customerSceneChargeOrderService.listByIds(sceneIds);
        CustomerSceneChargeOrder updateTemp;
        Long repCardId=null;
        for (CustomerSceneChargeOrder sorder : sorders) {
            updateTemp=new CustomerSceneChargeOrder();
            updateTemp.setId(sorder.getId());
            updateTemp.setChargeStatus(ChargeStatusEnum.UNCHARGE.getCode());
            //针对发卡的收费项特殊处理，如果是发卡，收费项直接作废，因为需要重新发卡，补卡退费也一样
            if(TolltemSceneEnum.ISSUE_CARD.eq(sorder.getTollItemScene()) ||
                    TolltemSceneEnum.CARD_REPLACEMENT.eq(sorder.getTollItemScene())
            ){
                updateTemp.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
                if(TolltemSceneEnum.CARD_REPLACEMENT.eq(sorder.getTollItemScene())){
                    repCardId=Long.parseLong(sorder.getBusinessNo());
                }
            }
            updates.add(updateTemp);
        }
        customerSceneChargeOrderService.updateBatchById(updates);
        return repCardId;
    }

    /**
     * 修改欠费明细，有欠费记录，去修改欠费记录明细状态。
     * @return
     */
    private void restoreArrearsDetailStatus(List<Long> arreasIds){
        if(CollectionUtils.isEmpty(arreasIds)) return ;
        List<GasmeterArrearsDetail> result= gasmeterArrearsDetailService.listByIds(arreasIds);
        if(CollectionUtils.isNotEmpty(result)){
            List<ReadMeterData> updateReadMeters=new ArrayList<>();
            ReadMeterData temp;
            for (GasmeterArrearsDetail entity : result) {
                entity.setArrearsStatus(ChargeStatusEnum.UNCHARGE.getCode());
                temp=new ReadMeterData().setChargeStatus(ChargeEnum.NO_CHARGE);
                temp.setId(entity.getReadmeterDataId());
                updateReadMeters.add(temp);
            }
            Boolean updateResult=gasmeterArrearsDetailService.updateBatchById(result);
            if(!updateResult){
                throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_ARREARSRECORD_SERVICE_EX));
            }

        }
    }

    /**
     * 作废减免和赠送记录
     * @return
     */
    private void cancelReductAndGive(String chargeNo){
        CustomerEnjoyActivityRecord updateEntity=new CustomerEnjoyActivityRecord()
                .setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        customerEnjoyActivityRecordService.update(updateEntity,new LbqWrapper<CustomerEnjoyActivityRecord>()
            .eq(CustomerEnjoyActivityRecord::getChargeNo,chargeNo)
        );
    }

    /**
     * 作废所有收费项明细
     * @return
     */
    private void cancelChargeItems(List<Long> ids){
        if(CollectionUtils.isEmpty(ids)) {
            return ;
        }
        List<ChargeItemRecord> updates=new ArrayList<>();
        ChargeItemRecord temp;
        for (Long id : ids) {
            temp=new ChargeItemRecord().setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            temp.setId(id);
            updates.add(temp);
        }
        Boolean update=chargeItemRecordService.updateBatchById(updates);
        if(!update){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_CANCEL_CHARTEITEM_SERVICE_EX));
        }
    }

    /**
     * 作废保险记录
     * @return
     */
    private void cancelInsurance(Long id){
        if(id==null) return ;
        ChargeInsuranceDetail updateEntity=new ChargeInsuranceDetail().setStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        updateEntity.setId(id);
        Boolean update=chargeInsuranceDetailService.updateById(updateEntity);

        if(!update){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_CANCEL_INSURANCE_SERVICE_EX));
        }
    }

    /**
     * 充值记录作废
     * @return
     */
    private void cancelRecharge(Long id){
        if(id==null) return ;
        RechargeRecord rechargeRecord=new RechargeRecord().setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
        rechargeRecord.setId(id);
        Boolean update=rechargeRecordService.updateById(rechargeRecord);
        if(!update){
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_CANCEL_RECHARGE_SERVICE_EX));
        }
    }
    /**
     * 冻结户表账户预存金额
     * @param record
     */
    private void frozenMeterAccount(ChargeRecord record){
        if(
                record.getRechargeMoney()!=null &&
                record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0
        ){
            GasMeter meter=gasMeterService.findGasMeterByCode(record.getGasMeterCode());
            GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
            GasMeterInfo meterInfo=gasMeterInfoService.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode());
            if(OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName()) ||
                    OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())){
                GasMeterInfo update=new GasMeterInfo();
                update.setGasMeterBalance(meterInfo.getGasMeterBalance().subtract(record.getRechargeMoney()));
                update.setGasMeterGive(meterInfo.getGasMeterGive().subtract(record.getRechargeGiveMoney()));
                refundSerialService.saveFreezeCustomerMeterAccountSerial(record,version,meterInfo);
                gasMeterInfoService.updateById(update);
            }

        }
    }

    /**
     * 取消退费解冻户表账户预存金额
     * @param record
     */
    private void cancelBackAndUnfreezeMeterAccount(ChargeRecord record){
        if(record.getRechargeMoney()!=null &&
                record.getRechargeMoney().compareTo(BigDecimal.ZERO)>0
        ){
            GasMeter meter=gasMeterService.findGasMeterByCode(record.getGasMeterCode());
            GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
            GasMeterInfo meterInfo=gasMeterInfoService.getByMeterAndCustomerCode(record.getGasMeterCode(),record.getCustomerCode());
            if(OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName()) ||
                    OrderSourceNameEnum.CENTER_RECHARGE.eq(version.getOrderSourceName())){
                GasMeterInfo update=new GasMeterInfo();
                update.setGasMeterBalance(meterInfo.getGasMeterBalance().add(record.getRechargeMoney()));
                update.setGasMeterGive(meterInfo.getGasMeterGive().add(record.getRechargeGiveMoney()));
                gasMeterInfoService.updateById(update);
            }
        }
    }
    /**
     * 冻结账户预存金额
     * @param chargeRecord
     */
    private void frozenAccount(ChargeRecord chargeRecord,String refundNo){
        BigDecimal zero = new BigDecimal("0.00");
        CustomerAccount account = customerAccountService.queryAccountByCharge(chargeRecord.getCustomerCode());
        if (chargeRecord.getPrechargeMoney().compareTo(zero) > 0) {
            //抵扣预存退费冻结流水
            CustomerAccountSerial saveDTO=refundSerialService.createFrozenAccountSeria(account,chargeRecord,refundNo);
            if(saveDTO.getBookAfterMoney().compareTo(zero)<0){
                throw new BizException(i18nUtil.getMessage(MessageConstants.G_ACCOUNT_INA));
            }
            customerAccountSerialService.save(saveDTO);

            //账户金额变更
            CustomerAccount accountUpdateDTO = new CustomerAccount();
            BeanUtils.copyProperties(account, accountUpdateDTO);
            accountUpdateDTO.setId(account.getId());
            accountUpdateDTO.setAccountMoney(saveDTO.getBookAfterMoney());
            accountUpdateDTO.setGiveMoney(saveDTO.getGiveBookAfterMoney());
            Boolean udpate=customerAccountService.updateById(accountUpdateDTO);
            if(!udpate){
                throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX));
            }
        }
    }


    /**
     * 有账户存入（包含零存）和抵扣 才会保存账户流水和修改账户
     * @return
     */
    private void saveAccount(ChargeRecord chargeRecord,ChargeRefund refund){
        CustomerAccount account = customerAccountService.queryAccountByCharge(chargeRecord.getCustomerCode());
        //退款金额退费流水
        CustomerAccountSerial saveDTO=null;
        if(RefundMethodEnum.BACK_ACCOUNT.eq(refund.getBackMethodCode())){
            if(refund.getBackAmount().compareTo(BigDecimal.ZERO)>0){
                saveDTO=refundSerialService.createBackAccountSeria(account,refund);
                account.setAccountMoney(account.getAccountMoney().add(refund.getBackAmount()));
                customerAccountSerialService.save(saveDTO);
            }
        }
        //预存的钱在申请冻结，如果实际退费成功，不做任何处理，如果取消退费才能解除冻结，返还账户使用
//            if(chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
//                CustomerAccountSerial saveDTO2=createUnfreezeAndCancelAccountSeria(account,chargeRecord);
//                account.setAccountMoney(account.getAccountMoney().add(refund.getBackAmount()));
//                customerAccountSerialService.save(saveDTO2);
//            }
        if (chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0 ) {
            //抵扣退回流水
            saveDTO=refundSerialService.createAccountRedSeria(account,chargeRecord);
            if(saveDTO.getBookAfterMoney().compareTo(BigDecimal.ZERO)<0){
                throw new BizException(i18nUtil.getMessage(MessageConstants.G_ACCOUNT_INA));
            }
            customerAccountSerialService.save(saveDTO);
        }
        if(saveDTO!=null){
            //账户金额变更
            CustomerAccount accountUpdateDTO = new CustomerAccount();
            BeanUtils.copyProperties(account, accountUpdateDTO);
            accountUpdateDTO.setId(account.getId());
            accountUpdateDTO.setAccountMoney(saveDTO.getBookAfterMoney());
            accountUpdateDTO.setGiveMoney(saveDTO.getGiveBookAfterMoney());
            Boolean udpate=customerAccountService.updateById(accountUpdateDTO);
            if(!udpate){
                throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_ACCOUNT_SERVICE_EX));
            }
        }
    }





}
