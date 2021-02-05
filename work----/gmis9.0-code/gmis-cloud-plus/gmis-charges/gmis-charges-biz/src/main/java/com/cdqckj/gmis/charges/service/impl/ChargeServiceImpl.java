package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.authority.service.auth.UserService;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.charges.constant.ChargeMessageConstants;
import com.cdqckj.gmis.charges.dao.ChargeRecordMapper;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeDTO;
import com.cdqckj.gmis.charges.dto.ChargeResultDTO;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.*;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.BizSCode;
import com.cdqckj.gmis.common.enums.ConversionType;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.meterinfo.MeterAccountSerialSceneEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.invoice.enumeration.InvoiceTypeEnum;
import com.cdqckj.gmis.operateparam.enumeration.ActivityScene;
import com.cdqckj.gmis.operateparam.service.GiveReductionConfService;
import com.cdqckj.gmis.operateparam.service.PurchaseLimitService;
import com.cdqckj.gmis.operateparam.service.UseGasTypeService;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.BusinessHallService;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchPlusService;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.userarchive.service.PurchaseLimitCustomerService;
import com.cdqckj.gmis.utils.I18nUtil;
import com.google.common.collect.Lists;
import io.seata.common.util.CollectionUtils;
import lombok.extern.log4j.Log4j2;
import net.sf.cglib.core.Local;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 收费相关业务数据计算和校验以及数据存储
 * @author tp
 * @date 2020-09-04
 */
@Service
//@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Log4j2
@DS("#thread.tenant")
public class ChargeServiceImpl extends SuperCenterServiceImpl implements ChargeService {

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
    CustomerGasMeterRelatedService customerGasMeterRelatedService;
    @Autowired
    GasMeterInfoService gasMeterInfoService;

    @Autowired
    GasMeterInfoSerialService gasMeterInfoSerialService;

    @Autowired
    TollItemService tollItemService;
    @Autowired
    OtherFeeRecordService otherFeeRecordService;
    @Autowired
    CustomerSceneChargeOrderService customerSceneChargeOrderService;
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

    @Autowired
    ChargesValidService chargesValidService;

    /**
     * 收费相关操作入口
     * @param infoDTO
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @CodeNotLost
    public R<ChargeResultDTO> charge(ChargeDTO infoDTO) {
        ChargeRecord chargeRecord = infoDTO.getChargeRecord();
        chargeRecord.setGasMeterCode(infoDTO.getGasMeterCode());
        GasMeter meter=infoDTO.getMeter();
        Customer customer=customerService.getOne(new LbqWrapper<Customer>()
                .eq(Customer::getCustomerCode,infoDTO.getCustomerCode()));

        //基本信息校验
        R<Boolean> valid=chargesValidService.baseValid(chargeRecord,meter,customer,infoDTO);
        if(valid.getIsError()){
            return R.fail(valid.getMsg(),valid.getDebugMsg());
        }
        Boolean useAccountDec=infoDTO.getUseAccountDec()==null?true:infoDTO.getUseAccountDec();
        Map<Long, CustomerEnjoyActivityRecord> reductionMaps=new HashMap<>();
        List<Long> reductionTollIds=getReductionItemIds(infoDTO.getItemList());
        //加载见面项用于校验
        if(CollectionUtils.isNotEmpty(reductionTollIds)){
            reductionMaps.putAll(chargeLoadService.loadGiveReduction(meter,ActivityScene.CHARGE_DE,reductionTollIds));
        }
        //收费项校验，先加载加载燃气费,用于校验
        List<ChargeItemRecord> gasFeeList = getGasFeeList(chargeRecord, infoDTO.getItemList(),infoDTO.getArrearsDetailIds());
        //未知收费项校验
        valid=chargesValidService.unknownItemValid(meter,infoDTO.getItemList(),reductionMaps,gasFeeList);
        if(valid.getIsError()){
            return R.fail(valid.getMsg(),valid.getDebugMsg());
        }
        //充值校验
        preChargeValid(chargeRecord,infoDTO,gasFeeList);
        //账户信息查询和抵扣校验。账户查询要加行锁,如果是三方支付需要加数据锁，根据表字段进行加锁。
        CustomerAccount account = customerAccountService.queryAccountByCharge(infoDTO.getCustomerCode());
        //针对开户过户的收费独立处理，建立关系，账户等
        account=dealOpenOrTransAccount(infoDTO,chargeRecord,customer,account);
        ChargeUtils.setNullFieldAsZero(account);
        //账户校验
        valid=chargesValidService.accountValid(chargeRecord,account,useAccountDec);
        if(valid.getIsError()){
            return R.fail(valid.getMsg(),valid.getDebugMsg());
        }

        //设置基本数据
        chargeRecord.setChargePreMoney(account.getAccountMoney());
        chargeRecord.setGiveBookPreMoney(account.getGiveMoney());
        chargeRecord.setChargeNo(BizCodeNewUtil.genChargeNo());
        chargeRecord.setRefundStatus(RefundStatusEnum.NOMAL.getCode());
        chargeRecord.setCustomerCode(customer.getCustomerCode());
        chargeRecord.setCustomerName(customer.getCustomerName());
        chargeRecord.setCreateUserId(BaseContextHandler.getUserId());
        chargeRecord.setDataStatus(DataStatusEnum.NORMAL.getValue());
        chargeRecord.setPayTime(LocalDateTime.now());
        //需要统计购气量，所以这里要转换处理。
        transMoneyToGas(chargeRecord,meter);
        //发票编号
        invoiceNoValidAndSet(chargeRecord);
        //账户余额收入支出相关变化计算,并生成了缴费编号
        calculAccountDeduction(chargeRecord,account);
        //现金直接结算记录完成状态，第三方支付回调时再处理状态。
        //现金支付
        DataStatusEnum dataStatus=DataStatusEnum.NORMAL;
        if (!ChargePayMethodEnum.CASH.getCode().equals(chargeRecord.getChargeMethodCode())) {
            //第三方支付需要等待回调，记录未支付
            dataStatus=DataStatusEnum.NOT_AVAILABLE;
            chargeRecord.setChargeStatus(ChargeStatusEnum.UNCHARGE.getCode());
        } else {
            chargeRecord.setPayRealTime(LocalDateTime.now());
            chargeRecord.setChargeStatus(ChargeStatusEnum.CHARGED.getCode());
            chargeRecord.setSummaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode());
        }
        List<CustomerEnjoyActivityRecord> reductions=getReductions(infoDTO.getItemList(),reductionMaps);
//        List<TollItemChargeRecord> otherItems=getOtherItems(chargeRecord,customer,infoDTO.getItemList(),tollItemMaps);

        saveData(chargeRecord,customer,meter,account,infoDTO,dataStatus,reductions);
        return R.success(createChargeResult(chargeRecord,infoDTO.getItemList(),infoDTO.getGasMeterInfo()));
    }

    private CustomerAccount dealOpenOrTransAccount(ChargeDTO infoDTO,ChargeRecord chargeRecord,Customer customer,CustomerAccount account){
        //账户信息查询和抵扣校验。账户查询要加行锁,如果是三方支付需要加数据锁，根据表字段进行加锁。
        DataStatusEnum dataStatusEnum=DataStatusEnum.NORMAL;
        if(!ChargePayMethodEnum.CASH.eq(infoDTO.getChargeRecord().getChargeMethodCode())) {
            dataStatusEnum=DataStatusEnum.NOT_AVAILABLE;
        }
        if (infoDTO.getScene() != null &&
                (TolltemSceneEnum.OPEN_ACCOUNT.eq(infoDTO.getScene()) ||
                        TolltemSceneEnum.TRANSFER.eq(infoDTO.getScene())
                )
        ) {
            if (StringUtils.isBlank(chargeRecord.getCustomerChargeNo())) {
                throw BizException.wrap("开户时，过户时需传入客户缴费编号");
            }
            if (account == null) {
                log.info("==============开户创建账户信息====================");
                return createAccount(customer,dataStatusEnum);
            }
        } else {
            CustomerGasMeterRelated query = CustomerGasMeterRelated.builder().customerCode(chargeRecord.getCustomerCode())
                    .gasMeterCode(chargeRecord.getGasMeterCode())
                    .dataStatus(DataStatusEnum.NORMAL.getValue()).build();
            List<CustomerGasMeterRelated> list = customerGasMeterRelatedService.findCustomerAndGasMeterList(query);
            if (list.size() > 1) {
                throw BizException.wrap("客户表具关系数据异常出现多条记录");
            }
            chargeRecord.setCustomerChargeNo(list.get(0).getCustomerChargeNo());
        }
        return account;
    }

    private void transMoneyToGas(ChargeRecord chargeRecord,GasMeter meter){
        if(chargeRecord.getRechargeMoney()!=null && chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
            if(chargeRecord.getRechargeGas().compareTo(BigDecimal.ZERO)==0){
                R<BigDecimal> calculateR=calculateService.conversion(new ConversionVO()
                        .setConversionType(ConversionType.MONEY)
                        .setConversionValue(chargeRecord.getRechargeMoney())
                        .setGasMeterCode(meter.getGasCode())
                        .setUseGasTypeId(meter.getUseGasTypeId()))
                        ;
                chargeRecord.setRechargeGas(calculateR.getData());
            }
        }
    }

    private CustomerAccount createAccount(Customer customer,DataStatusEnum dataStatusEnum){
        CustomerAccount customerAccount = CustomerAccount
                .builder()
                .companyCode(BaseContextHandler.getTenantId())
                .companyName(BaseContextHandler.getTenantName())
                .orgId(BaseContextHandler.getOrgId())
                .orgName(BaseContextHandler.getOrgName())
                .accountCode(BizCodeNewUtil.genAccountDataCode())
                .customerCode(customer.getCustomerCode())
                .customerName(customer.getCustomerName())
                .accountMoney(BigDecimal.ZERO)
                .giveMoney(BigDecimal.ZERO)
                .status(dataStatusEnum.getValue())
                .build();
        customerAccountService.save(customerAccount);
        return  customerAccount;
    }
    private void preChargeValid(ChargeRecord chargeRecord,ChargeDTO infoDTO,List<ChargeItemRecord> gasFeeList){
        if(chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO)>0) {
            int mcount = 0;
            if (infoDTO.getItemList() != null) {
                for (ChargeItemRecord chargeItemRecord : infoDTO.getItemList()) {
                    if (ChargeItemEnum.GASFEE.eq(chargeItemRecord.getChargeItemSourceCode())) {
                        mcount++;
                    }
                }
            }
            int mcount2 = 0;
            for (ChargeItemRecord chargeItemRecord : gasFeeList) {
                if (ChargeItemEnum.GASFEE.eq(chargeItemRecord.getChargeItemSourceCode())) {
                    mcount2++;
                }
            }
            if (mcount2 >mcount){
                throw BizException.wrap("燃气费不是全部缴纳不能预存");
            }
        }
    }

    private void invoiceNoValidAndSet(ChargeRecord chargeRecord){
        if (!InvoiceTypeEnum.RECEIPT.eq(chargeRecord.getChargeMethodCode())) {
            return ;
        }
        //生成缴费编号
        Boolean noSwitch=GmisSysSettingUtil.getInvoiceCodeRule().intValue()==0?true:false;
        if(noSwitch){
            //校验唯一
            if(StringUtils.isBlank(chargeRecord.getInvoiceNo())){
                throw BizException.wrap("票据编号必填");
            }else{
                ChargeRecord check=chargeRecordService.getOne(new LbqWrapper<ChargeRecord>()
                        .eq(ChargeRecord::getInvoiceNo,chargeRecord.getInvoiceNo())
                        .eq(ChargeRecord::getDataStatus,DataStatusEnum.NORMAL.getValue())
                );
                if(check!=null){
                    if(ChargeStatusEnum.CHARGED.eq(chargeRecord.getInvoiceNo()) ||
                            ChargeStatusEnum.CHARGING.eq(chargeRecord.getInvoiceNo())
                    ){
                        throw BizException.wrap("票据编号已使用");
                    }
                }
            }
        }else{
            //自动生成
            chargeRecord.setInvoiceNo(BizCodeNewUtil.genReceiptNoDataCode());
//            chargeRecord.setInvoiceNo(BizCodeUtil.genReceiptNoDataCode(BizCodeUtil.BILL_NO));
        }
    }

    /**
     * 扣减个人和营业厅限额
     * @param chargeRecord
     */
    public void dealLimit(ChargeRecord chargeRecord){
        try {
            BigDecimal del = chargeRecord.getReceivableMoney();
            User user = chargeRecordMapper.getUserById(BaseContextHandler.getUserId());
            if (user != null && user.getPointOfSale() != null) {
                //配额不等于空，计算结算额
                User userUpdate = new User();
                userUpdate.setId(user.getId());
                if (user.getBalance() != null) {
                    userUpdate.setBalance(user.getBalance().subtract(del));
                } else {
                    userUpdate.setBalance(user.getPointOfSale().subtract(del));
                }
                if (userUpdate.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                    userUpdate.setBalance(BigDecimal.ZERO);
                }
                userService.updateById(userUpdate);
            }
            BusinessHall businessHall= loadBusinessHall();
            if (businessHall != null && businessHall.getPointOfSale() != null) {
                //配额不等于空，计算结算额
                BusinessHall bizHallUpdate = new BusinessHall();
                bizHallUpdate.setId(businessHall.getId());
                if (businessHall.getBalance() != null) {
                    bizHallUpdate.setBalance(businessHall.getBalance().subtract(del));
                } else {
                    bizHallUpdate.setBalance(new BigDecimal(businessHall.getPointOfSale()).subtract(del));
                }
                if (bizHallUpdate.getBalance().compareTo(BigDecimal.ZERO) < 0) {
                    bizHallUpdate.setBalance(BigDecimal.ZERO);
                }
                businessHallService.updateById(bizHallUpdate);
            }
        }catch (Exception e){
            log.error("个人及营业厅限额扣减异常",e);
        }
    }

    public ChargeResultDTO createChargeResult(ChargeRecord chargeRecord,List<ChargeItemRecord> itemRecords,GasMeterInfo gasMeterInfo){
        ChargeResultDTO resultDTO=new ChargeResultDTO();
        resultDTO.setAmountMark(chargeRecord.getSettlementType());
        resultDTO.setOrderSourceName(chargeRecord.getChargeType());
        resultDTO.setGasMeterInfo(gasMeterInfo);
        Set<Long> arreasIds=new HashSet<>();
        List<Long> sceneIds=new ArrayList<>();
        List<Long> adjustPriceIds=new ArrayList<>();
        if(CollectionUtils.isNotEmpty(itemRecords)) {
            itemRecords.forEach(item -> {
                if (item.getChargeItemSourceCode().equals(ChargeItemEnum.GASFEE.getCode())) {
                    arreasIds.add(Long.parseLong(item.getChargeItemSourceId()));
                }
                if (item.getChargeItemSourceCode().equals(ChargeItemEnum.SCENEFEE.getCode())) {
                    sceneIds.add(Long.parseLong(item.getChargeItemSourceId()));
                }
                if (item.getChargeItemSourceCode().equals(ChargeItemEnum.ADJUST_PRICE.getCode())) {
                    adjustPriceIds.add(Long.parseLong(item.getChargeItemSourceId()));
                }
            });
        }
        resultDTO.setArrearIds(Lists.newArrayList(arreasIds.iterator()));
        resultDTO.setSceneIds(sceneIds);
        resultDTO.setChargeRecord(chargeRecord);
        resultDTO.setAdjustPriceIds(adjustPriceIds);
//        resultDTO.setChargeItemRecords(itemRecords);
        return resultDTO;
    }
    /**
     * 发起支付修改状态
     * @param chargeNo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Deprecated
    public R<Boolean> charging(String chargeNo){

        return R.success(true);
    }

    /**
     * 支付完成回调方法
     * @param dto
     */
    @Transactional(rollbackFor = Exception.class)
    @CodeNotLost
    public R<ChargeResultDTO> chargeComplete(ChargeCompleteDTO dto){
        String chargeNo=dto.getChargeNo();
        Boolean chargeStatus=dto.getChargeStatus();
        ChargeRecord chargeRecord=dto.getRecord();
        if(chargeRecord==null){
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_CHARGERECORD_SERVICE_EX));
        }

//        GasMeterInfo meterInfo=gasMeterInfoService.getByMeterAndCustomerCode(chargeRecord.getGasMeterCode(),chargeRecord.getCustomerCode());

        if(ChargeStatusEnum.CHARGED.eq(chargeRecord.getChargeStatus())){
            return R.success(createChargeResult(chargeRecord,null,dto.getGasMeterInfo()));
        }

//        BusinessHall businessHall=loadBusinessHall();//加载营业厅数据 写入字段使用
        List<ChargeItemRecord> chargeItems=new ArrayList<>();
        //加载已有的收费项
        chargeItems.addAll(chargeItemRecordService.list(
                new LbqWrapper<ChargeItemRecord>().eq(ChargeItemRecord::getChargeNo,chargeNo)
        ));
        Set<Long> arreasIds=new HashSet<>();
                List<Long> sceneIds=new ArrayList<>();
                chargeItems.forEach(item->{
                    if(item.getChargeItemSourceCode().equals(ChargeItemEnum.GASFEE.getCode())){
                        arreasIds.add(Long.parseLong(item.getChargeItemSourceId()));
            }
            if(item.getChargeItemSourceCode().equals(ChargeItemEnum.SCENEFEE.getCode())){
                sceneIds.add(Long.parseLong(item.getChargeItemSourceId()));
            }
        });
        Customer customer=customerService.getOne(new LbqWrapper<Customer>()
                .eq(Customer::getCustomerCode,chargeRecord.getCustomerCode()));
        CustomerAccount account = customerAccountService.queryAccountByCharge(chargeRecord.getCustomerCode());
        if(chargeStatus){
            if(DataStatusEnum.NOT_AVAILABLE.getValue()==account.getStatus().intValue()){
                CustomerAccount updateAccount=new CustomerAccount();
                updateAccount.setId(account.getId());
                updateAccount.setStatus(DataStatusEnum.NORMAL.getValue());
                customerAccountService.updateById(updateAccount);
                account.setStatus(DataStatusEnum.NORMAL.getValue());
            }
            List<ChargeItemRecord> saveItems=new ArrayList<>();
            chargeRecord.setChargeStatus(ChargeStatusEnum.CHARGED.getCode());
            chargeRecord.setSummaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode());
            //预存入账
            if(chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO) > 0){
                saveAccount(chargeRecord,account);
            }
            //抵扣在收费完成，那么收费的时候就保存了抵扣收费项。

            //保存充值记录
            ChargeItemRecord recharge=saveRecharge(chargeRecord,dto.getMeter(),customer);
            //保存保险
            ChargeItemRecord insurance= saveInsurance(chargeRecord,customer);
            if(insurance!=null){
                saveItems.add(insurance);
            }
            if(recharge!=null){
                saveItems.add(recharge);
            }
            //修改欠费记录状态
            updateArrearsDetailStatus(chargeItems);
            //赠送减免活动状态维护--一开始就只是记录，状态为禁用
            customerEnjoyActivityRecordService.update(new CustomerEnjoyActivityRecord()
                            .setDataStatus(DataStatusEnum.NORMAL.getValue()),
                    new LbqWrapper<CustomerEnjoyActivityRecord>().eq(CustomerEnjoyActivityRecord::getChargeNo,chargeNo)
            );
            //修改收费项明细状态
            chargeItemRecordService.update(new ChargeItemRecord()
                            .setDataStatus(DataStatusEnum.NORMAL.getValue()),
                    new LbqWrapper<ChargeItemRecord>().eq(ChargeItemRecord::getChargeNo,chargeNo)
            );
            if(saveItems.size()>0){
                //如果有保险和充值，需要独立存为收费项明细，不然退费就需要去充值记录表查询。
                chargeItemRecordService.saveBatchSomeColumn(saveItems);
                chargeItems.addAll(saveItems);
            }
            //保存附加费
            saveOtherFeeItem(chargeRecord,chargeItems);
            //修改业务费用单数据（场景业务状态，抄表数据等在聚合服务完成）
            updateSceneOrderStatus(chargeRecord,chargeItems);
            saveMeterUseInfo(chargeRecord,dto.getGasMeterInfo());
            chargeRecord.setPayRealTime(LocalDateTime.now());
        }else{
            chargeRecord.setChargeStatus(ChargeStatusEnum.CHARGE_FAILURE.getCode());

            chargeRecord.setPayErrReason(dto.getRemark());
            chargeRecord.setPayRealTime(LocalDateTime.now());
            //回退账户抵扣
            if(chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0) {
                reductionRebackAccount(chargeRecord,account);
            }
            //作废赠送减免活动 ，作废收费项明细--这里不做本身就是待启用状态
        }
        ChargeRecord update=new ChargeRecord();
        update.setId(chargeRecord.getId());
        update.setChargeStatus(chargeRecord.getChargeStatus());
        update.setPayErrReason(chargeRecord.getPayErrReason());
        update.setPayRealTime(chargeRecord.getPayRealTime());
        update.setRemark(chargeRecord.getRemark());
        update.setSummaryBillStatus(chargeRecord.getSummaryBillStatus());
        chargeRecordService.updateById(update);
        return R.success(createChargeResult(chargeRecord,chargeItems,dto.getGasMeterInfo()));
    }



    /**
     * 如果是第三方支付，待异步回调修改状态。
     * 只保存赠送减免--其他在完成状态时候保存
     */
    private void saveData(ChargeRecord chargeRecord,Customer customer,GasMeter meter,
                          CustomerAccount account,
                          ChargeDTO infoDTO,DataStatusEnum datastatus,
                          List<CustomerEnjoyActivityRecord> reductions
    ){
        BusinessHall businessHall=loadBusinessHall();
        chargeRecord.setBusinessHallId(getBusinessHallId(businessHall));
        chargeRecord.setBusinessHallName(getBusinessHallName(businessHall));
        //保存赠送和减免活动明细
        saveReductAndGive(infoDTO,chargeRecord,datastatus,reductions);



        List<ChargeItemRecord> itemRecords=infoDTO.getItemList();
        if(itemRecords==null) {
            itemRecords=new ArrayList<>();
        }
        if(chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO)>0){
            //生成预存收费项
            ChargeItemRecord chargeItem=createChargeItem(chargeRecord,ChargeItemEnum.ACCOUNT_DEC,
                    null);
            chargeItem.setDataStatus(datastatus.getValue());
            itemRecords.add(chargeItem);
        }
        if( chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO) > 0){
            //生成抵扣收费项
            ChargeItemRecord chargeItem=createChargeItem(chargeRecord,ChargeItemEnum.PRECHARGE,
                    null);
            chargeItem.setDataStatus(datastatus.getValue());
            itemRecords.add(chargeItem);
        }

        if (ChargePayMethodEnum.CASH.getCode().equals(chargeRecord.getChargeMethodCode())) {
            //保存充值记录
            ChargeItemRecord recharge=saveRecharge(chargeRecord,meter,customer);
            //保存保险
            ChargeItemRecord insurance= saveInsurance(chargeRecord,customer);
            if(insurance!=null){
                itemRecords.add(insurance);
            }
            if(recharge!=null){
                itemRecords.add(recharge);
            }

            //保存附加费
            saveOtherFeeItem(chargeRecord,infoDTO.getItemList());

            //修改欠费记录状态
            updateArrearsDetailStatus(itemRecords);

            //修改场景费用单状态
            updateSceneOrderStatus(chargeRecord,itemRecords);

            //现金支付，都应该保存
            if (chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0 ||
                    chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO) > 0
            ) {
                saveAccount(chargeRecord,account);
            }
            saveMeterUseInfo(chargeRecord,infoDTO.getGasMeterInfo());
        }else{
            //非现金支付，抵扣直接扣，预存不做任何操作，完成状态去做，都应该保存。完成失败将抵扣退回。
            if (chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0
            ) {
                saveAccount(chargeRecord,account);
            }
        }
        //保存所有收费项明细
        saveChargeItems(chargeRecord,customer,meter,itemRecords,datastatus);
        chargeRecord.setChargeMethodName(ChargePayMethodEnum.get(chargeRecord.getChargeMethodCode()).getDesc());
        saveChargeRecord(chargeRecord,businessHall,meter);
        //扣减限额中的余额
        dealLimit(chargeRecord);
    }

    /**
     * 保存收费记录
     */
    private void saveChargeRecord(ChargeRecord chargeRecord,BusinessHall businessHall,GasMeter meter){

        chargeRecord=chargeRecord.setBusinessHallId(getBusinessHallId(businessHall))
                .setBusinessHallName(getBusinessHallName(businessHall));
        chargeRecord.setInvoiceStatus(InvoiceStatusEnum.NOT_OPEN.getCode());
        chargeRecord.setReceiptStatus(ReceiptStatusEnum.NOT_OPEN.getCode());
        chargeRecordService.save(chargeRecord);
    }

    /**
     * 附加费相关收费项的储存
     * @return
     */
    private R<Boolean> saveOtherFeeItem(ChargeRecord chargeRecord,List<ChargeItemRecord> items){
        if(CollectionUtils.isNotEmpty(items)){
            List<OtherFeeRecord> updates=new ArrayList<>();
            OtherFeeRecord temp;
            for (ChargeItemRecord itemRecord : items) {
                if(ChargeItemEnum.OTHER.eq(itemRecord.getChargeItemSourceCode())){
                    //is not on_demind
                    if(StringUtils.isNotBlank(itemRecord.getChargeItemSourceId())){
                        temp=new OtherFeeRecord();
                        temp.setId(Long.parseLong(itemRecord.getChargeItemSourceId()));
                        temp.setChargeNo(chargeRecord.getChargeNo());
                        temp.setTotalChargeMoney(itemRecord.getChargeItemMoney());
                        temp.setChargeStatus(ChargeStatusEnum.CHARGED.getCode());
                        updates.add(temp);
                    }
                }
            }
            if(CollectionUtils.isNotEmpty(updates)){
                otherFeeRecordService.saveOrUpdateBatch(updates);
            }
        }
        return R.success(true);
    }

    /**
     * 修改场景费用单状态，并且将待处理业务单数据放到消息队列，有场景收费才会修改状态。
     * @return
     */
    private void updateSceneOrderStatus(ChargeRecord chargeRecord,List<ChargeItemRecord> itemRecords){
        if(CollectionUtils.isEmpty(itemRecords)){
            return ;
        }
        List<CustomerSceneChargeOrder> updates=new ArrayList();
        CustomerSceneChargeOrder updateTemp=new CustomerSceneChargeOrder();
        Long scenOrderId;
//        List<Long> sceneIds=new ArrayList();
        for (ChargeItemRecord itemRecord : itemRecords) {
            if(ChargeItemEnum.SCENEFEE.eq(itemRecord.getChargeItemSourceCode())){
                scenOrderId=Long.parseLong(itemRecord.getChargeItemSourceId());
                updateTemp.setId(scenOrderId);
                updateTemp.setChargeNo(chargeRecord.getChargeNo());
                updateTemp.setChargeStatus(ChargeStatusEnum.CHARGED.getCode());
                updateTemp.setTotalMoney(itemRecord.getChargeItemMoney());
                updateTemp.setChargeMoney(itemRecord.getChargeItemMoney());
                updateTemp.setDataStatus(DataStatusEnum.NORMAL.getValue());
                updates.add(updateTemp);
            }
        }
        if(CollectionUtils.isNotEmpty(updates)) {
            customerSceneChargeOrderService.updateBatchById(updates);
        }
//        int update = customerSceneChargeOrderService.updateChargeStatusComplete(sceneIds,chargeRecord.getChargeNo());
//        if (update != sceneIds.size()) {
//            throw  BizException.wrap(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_SCENEFEE_REOPER));
//        }
    }


    /**
     * 修改欠费明细，有欠费记录，去修改欠费记录明细状态。
     * @return
     */
    private void updateArrearsDetailStatus(List<ChargeItemRecord> itemRecords){
        if(CollectionUtils.isEmpty(itemRecords)){
            return ;
        }
        Set<Long> arreasIds=new HashSet<>();
        for (ChargeItemRecord itemRecord : itemRecords) {
            if(ChargeItemEnum.GASFEE.eq(itemRecord.getChargeItemSourceCode())){
                arreasIds.add(Long.parseLong(itemRecord.getChargeItemSourceId()));
            }
        }
        if (arreasIds.size() > 0) {
            Long[] ids = new Long[arreasIds.size()];
            arreasIds.toArray(ids);
            Integer update= gasmeterArrearsDetailService.updateChargeStatusComplete(Arrays.asList(ids));
            if (update != arreasIds.size()) {
                throw BizException.wrap(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_GASFEE_REOPER));
            }
        }
    }

    /**
     * 保存减免和赠送记录
     * @return
     */
    private R<Boolean> saveReductAndGive(ChargeDTO infoDTO,ChargeRecord chargeRecord,DataStatusEnum dataStatus,List<CustomerEnjoyActivityRecord> reductions){
        if(infoDTO.getActivityList()!=null) {
            reductions.addAll(infoDTO.getActivityList());
        }
        if (reductions.size() > 0) {
            for (CustomerEnjoyActivityRecord reduction : reductions) {
                reduction.setChargeNo(chargeRecord.getChargeNo());
                reduction.setDataStatus(dataStatus.getValue());
            }
            customerEnjoyActivityRecordService.saveBatchSomeColumn(reductions);
        }
        return R.success(true);
    }

    /**
     * 保存所有收费项明细
     * @return
     */
    private R<Boolean> saveChargeItems(ChargeRecord chargeRecord,Customer customer,GasMeter meter,List<ChargeItemRecord> chargeItems,DataStatusEnum dataStatus){
        for (ChargeItemRecord chargeItem : chargeItems) {
            chargeItem.setChargeNo(chargeRecord.getChargeNo());
            chargeItem.setCreateUserId(BaseContextHandler.getUserId());
            chargeItem.setCustomerCode(customer.getCustomerCode());
            chargeItem.setCustomerName(customer.getCustomerName());
            chargeItem.setGasmeterCode(meter.getGasCode());
//            chargeItem.setGasmeterName(meter.getGasMeterTypeName());
            chargeItem.setDataStatus(dataStatus.getValue());
            setCommonParams(chargeItem);
        }
        chargeItemRecordService.saveBatch(chargeItems);
        return R.success(true);
    }

    /**
     * 有保险 保险记录
     * @return
     */
    private ChargeItemRecord saveInsurance(ChargeRecord chargeRecord,Customer customer){
        if (chargeRecord.getInsurancePremium().compareTo(BigDecimal.ZERO) > 0) {
            ChargeInsuranceDetail detail=createChargeInsurance(chargeRecord,customer);
            chargeInsuranceDetailService.save(detail);
            return createChargeItem(chargeRecord,ChargeItemEnum.INSURANCE,
                    detail.getId().toString());
        }
        return null;
    }

    /**
     * 有充值才会保存充值记录
     * @return
     */
    private ChargeItemRecord saveRecharge(ChargeRecord chargeRecord,GasMeter meter,Customer customer){
        if (chargeRecord.getRechargeMoney()!=null && chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO) > 0) {
            RechargeRecord rechargeRecord=createRechargeRecord(chargeRecord,meter,customer);
            //如果是中心计费的物联网表，有充值需要独立处理，并且这个上表状态直接完成，退费可以退，通过表余额直接判断退款。
            if(OrderSourceNameEnum.REMOTE_READMETER.eq(chargeRecord.getChargeType())
                || OrderSourceNameEnum.CENTER_RECHARGE.eq(chargeRecord.getChargeType())
                    || OrderSourceNameEnum.REMOTE_RECHARGE.eq(chargeRecord.getChargeType())
            ){
                rechargeRecord.setChargeStatus(ChargeStatusEnum.CHARGED.getCode());
                rechargeRecord.setMoneyFlowStatus(MoneyFlowStatusEnum.success.getCode());
            }
            rechargeRecordService.save(rechargeRecord);
            saveCustomerMeterAccountSerial(chargeRecord,customer);
            return createChargeItem(chargeRecord,ChargeItemEnum.RECHARGE,
                    rechargeRecord.getId().toString());
        }
        return null;
    }

    private void saveCustomerMeterAccountSerial(ChargeRecord chargeRecord,Customer customer){
        if(chargeRecord.getRechargeMoney()==null || chargeRecord.getRechargeMoney().compareTo(BigDecimal.ZERO)==0){
            return ;
        }
        if(!OrderSourceNameEnum.REMOTE_READMETER.eq(chargeRecord.getChargeType())
                && !OrderSourceNameEnum.CENTER_RECHARGE.eq(chargeRecord.getChargeType())
        ){
           return ;
        }
        GasMeterInfo gasMeterInfo=gasMeterInfoService.getByMeterAndCustomerCode(chargeRecord.getGasMeterCode(),customer.getCustomerCode());
        ChargeUtils.setNullFieldAsZero(gasMeterInfo);
        GasMeterInfoSerial meterInfoSerial= new GasMeterInfoSerial();
        meterInfoSerial.setSerialNo(BizCodeNewUtil.getGasMeterInfoSerialCode(BizSCode.CHARGE));
        meterInfoSerial.setBillNo(chargeRecord.getChargeNo());
        meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
        meterInfoSerial.setCustomerCode(customer.getCustomerCode());
        meterInfoSerial.setGasMeterCode(chargeRecord.getGasMeterCode());
        meterInfoSerial.setGasMeterInfoId(gasMeterInfo.getId());
        meterInfoSerial.setBillType(MeterAccountSerialSceneEnum.PRECHARGE.getCode());
        meterInfoSerial.setGiveBookPreMoney(gasMeterInfo.getGasMeterGive());
        meterInfoSerial.setGiveBookPreMoney(chargeRecord.getRechargeGiveMoney());
        meterInfoSerial.setGiveBookAfterMoney(gasMeterInfo.getGasMeterGive().add(chargeRecord.getRechargeGiveMoney()));

        meterInfoSerial.setBookPreMoney(gasMeterInfo.getGasMeterBalance());
        meterInfoSerial.setBookAfterMoney(gasMeterInfo.getGasMeterBalance().add(chargeRecord.getRechargeMoney()));
        meterInfoSerial.setBookMoney(chargeRecord.getRechargeMoney());
        //新增户表账户流水
        gasMeterInfoSerialService.save(meterInfoSerial);
    }


    /**
     * 有账户存入（包含零存）和抵扣 才会保存账户流水和修改账户
     * @return
     */
    private R<Boolean> saveAccount(ChargeRecord chargeRecord,CustomerAccount account){
        customerAccountSerialService.save(createAccountSeria(chargeRecord,account));
        //账户金额变更
        CustomerAccount accountUpdate = new CustomerAccount();
    //            BeanUtils.copyProperties(account, accountUpdateDTO);
        accountUpdate.setId(account.getId());
        accountUpdate.setAccountMoney(chargeRecord.getChargeAfterMoney());
        accountUpdate.setGiveMoney(chargeRecord.getGiveBookAfterMoney());
        //这里可以根据入账前金额判断金额是否变更，如果变更修改肯定失败，类似版本号的概念。不排除ABA问题。
        Boolean update=customerAccountService.updateById(accountUpdate);
        if(!update){
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_ACCOUNT_MONEY_MODIFY));
        }
        return R.success(true);
    }

    /**
     * 抵扣退入账户
     * @return
     */
    private R<Boolean> reductionRebackAccount(ChargeRecord chargeRecord,CustomerAccount account){
        CustomerAccountSerial serial=createReBackAccountSeria(chargeRecord,account);
        customerAccountSerialService.save(serial);
        //账户金额变更
        CustomerAccount accountUpdate = new CustomerAccount();
        //            BeanUtils.copyProperties(account, accountUpdateDTO);
        accountUpdate.setId(account.getId());
        accountUpdate.setAccountMoney(serial.getBookAfterMoney());
        accountUpdate.setGiveMoney(serial.getGiveBookAfterMoney());
        Boolean update=customerAccountService.updateById(accountUpdate);
        if(!update){
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_SAVE_ERR_ACCOUNT_MONEY_MODIFY));
        }
        return R.success(true);
    }





    /**
     * 创建账户流水记录
     * @return
     */
    private CustomerAccountSerial createAccountSeria(ChargeRecord chargeRecord,CustomerAccount account){
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        serial.setBillNo(chargeRecord.getChargeNo());
        serial.setGasMeterCode(chargeRecord.getGasMeterCode());
//        serial.setGiveDeductionMoney(chargeRecord.getGiveDeductionMoney());
        if (chargeRecord.getPrechargeMoney().compareTo(BigDecimal.ZERO) > 0) {
            serial.setBillType(AccountSerialSceneEnum.PRECHARGE.getCode());
            serial.setBookMoney(chargeRecord.getPrechargeMoney());
            serial.setGiveBookMoney(BigDecimal.ZERO);
        } else {
            serial.setBillType(AccountSerialSceneEnum.DEDUCTION.getCode());
            serial.setBookMoney(chargeRecord.getPrechargeDeductionMoney());
            serial.setGiveBookMoney(chargeRecord.getGiveDeductionMoney());
        }
        serial.setBookAfterMoney(chargeRecord.getChargeAfterMoney());
        serial.setBookPreMoney(chargeRecord.getChargePreMoney());
        serial.setGiveBookAfterMoney(chargeRecord.getGiveBookAfterMoney());
        serial.setGiveBookPreMoney(chargeRecord.getGiveBookPreMoney());
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
        serial.setCustomerCode(chargeRecord.getCustomerCode());
        serial.setBusinessHallId(chargeRecord.getBusinessHallId());
        serial.setBusinessHallName(chargeRecord.getBusinessHallName());
        setCommonParams(serial);
        return serial;
    }

    /**
     * 创建抵扣回退账户流水
     * @return
     */
    private CustomerAccountSerial createReBackAccountSeria(ChargeRecord chargeRecord,CustomerAccount account){
        CustomerAccountSerial serial = new CustomerAccountSerial();
        serial.setAccountCode(account.getAccountCode());
        serial.setBillNo(chargeRecord.getChargeNo());
        serial.setGasMeterCode(chargeRecord.getGasMeterCode());
        serial.setBillType(AccountSerialSceneEnum.DEDUCTION_REFUND.getCode());
        serial.setBookMoney(chargeRecord.getPrechargeDeductionMoney());
        serial.setBookAfterMoney(account.getAccountMoney().add(chargeRecord.getPrechargeDeductionMoney()));
        serial.setBookPreMoney(account.getAccountMoney());
        serial.setGiveBookMoney(chargeRecord.getGiveDeductionMoney());
        serial.setGiveBookAfterMoney(account.getGiveMoney().add(chargeRecord.getGiveDeductionMoney()));
        serial.setGiveBookPreMoney(account.getGiveMoney());
        serial.setSerialNo(BizCodeNewUtil.getCustomerAccountCode(BizSCode.CHARGE));
//        serial.setSerialNo(BizCodeUtil.genSerialDataCode(BizSCode.CHARGE, BizCodeUtil.ACCOUNT_SERIAL));
        serial.setCustomerCode(chargeRecord.getCustomerCode());
        serial.setBusinessHallId(chargeRecord.getBusinessHallId());
        serial.setBusinessHallName(chargeRecord.getBusinessHallName());
        setCommonParams(serial);
        return serial;
    }

    /**
     * 创建保险明细记录
     * @return
     */
    private ChargeInsuranceDetail createChargeInsurance(ChargeRecord chargeRecord,Customer customer){
        ChargeInsuranceDetail insurance= new ChargeInsuranceDetail().setCustomerCode(customer.getCustomerCode())
                .setCustomerName(customer.getCustomerName()).setGasmeterCode(chargeRecord.getGasMeterCode())
//                .setGasmeterName(meter.getGasMeterTypeName())
                .setInsuranceContractNo(chargeRecord.getInsuranceContractNo()) //合同编号按道理说有就直接存，没有就界面获取，暂时不知道怎么来。
                .setInsuranceStartDate(chargeRecord.getInsuranceStartDate())
                .setInsuranceEndDate(chargeRecord.getInsuranceEndDate())
                .setInsurancePremium(chargeRecord.getInsurancePremium())
                .setChargeNo(chargeRecord.getChargeNo())
                .setStatus(DataStatusEnum.NORMAL.getValue())
                .setBusinessHallId(chargeRecord.getBusinessHallId())
                .setBusinessHallName(chargeRecord.getBusinessHallName());
        setCommonParams(insurance);
        return insurance;
    }
    /**
     * 创建充值记录
     * @return
     */
    private RechargeRecord createRechargeRecord(ChargeRecord chargeRecord,GasMeter meter,Customer customer){

        RechargeRecord rechargeRecord= new RechargeRecord().setCustomerCode(customer.getCustomerCode())
                .setCustomerName(customer.getCustomerName()).setGasmeterCode(meter.getGasCode())
//                .setGasmeterName(meter.getGasMeterTypeName())
                .setUseGasTypeId(meter.getUseGasTypeId())
                .setUseGasTypeName(meter.getUseGasTypeName()).setRechargeGas(chargeRecord.getRechargeGas())
                .setRechargeMoney(chargeRecord.getRechargeMoney()).setRechargeGiveGas(chargeRecord.getRechargeGiveGas())
                .setRechargeGiveMoney(chargeRecord.getRechargeGiveMoney()).setChargeNo(chargeRecord.getChargeNo())
                .setTotalMoney(chargeRecord.getChargeMoney().add(chargeRecord.getRechargeMoney()))
                .setUseGasTypeName(meter.getUseGasTypeName())
//                .setGasMeterTypeCode(meter.getGasMeterTypeCode())
//                .setGasMeterTypeName(meter.getGasMeterTypeName())
//                .setUseGasTypeId(meter.getUseGasTypeCode())
                .setTotalGas(chargeRecord.getRechargeGas().add(chargeRecord.getRechargeGiveGas()))
                .setMoneyFlowStatus(MoneyFlowStatusEnum.waite_deal.getCode())
                .setDataStatus(DataStatusEnum.NORMAL.getValue())
                .setChargeStatus(ChargeStatusEnum.CHARGED.getCode())
                .setCreateUserId(BaseContextHandler.getUserId())
                .setBusinessHallId(chargeRecord.getBusinessHallId())
                .setBusinessHallName(chargeRecord.getBusinessHallName());
        setCommonParams(rechargeRecord);
        return rechargeRecord;
    }
    /**
     * 创建收费项
     * @return
     */
    private ChargeItemRecord createChargeItem(ChargeRecord chargeRecord,ChargeItemEnum itemType,String sourceId){
        TollItem tollItem=null;
        Long tollId=null;
        String tollSceneCode=null;
        String moneyMethod=null;
        String frequency=null;
        ChargeItemRecord chargeItemRecord=new ChargeItemRecord();
        if(ChargeItemEnum.RECHARGE.eq(itemType.getCode())){
            //只有充值的会进入这里。
            tollItem=getTollItem(TolltemSceneEnum.GAS_FEE);
            chargeItemRecord.setChargeItemMoney(chargeRecord.getRechargeMoney());
            chargeItemRecord.setChargeItemGas(chargeRecord.getRechargeGas());
            chargeItemRecord.setRechargeGiveGas(chargeRecord.getRechargeGiveGas());
            chargeItemRecord.setRechargeGiveMoney(chargeRecord.getRechargeGiveMoney());
        }else if(ChargeItemEnum.INSURANCE.eq(itemType.getCode())){
            tollItem=getTollItem(TolltemSceneEnum.INSURANCE_FEE);
            chargeItemRecord.setChargeItemMoney(chargeRecord.getInsurancePremium());
        }else if(ChargeItemEnum.PRECHARGE.eq(itemType.getCode())){
            //预存的钱按燃气费税务配置控制税率
            tollItem=getTollItem(TolltemSceneEnum.GAS_FEE);
            chargeItemRecord.setChargeItemMoney(chargeRecord.getPrechargeMoney());
        }else if(ChargeItemEnum.ACCOUNT_DEC.eq(itemType.getCode())){
            //账户抵扣
            chargeItemRecord.setChargeItemMoney(chargeRecord.getPrechargeDeductionMoney());
        }
        if(tollItem!=null){
            tollId=tollItem.getId();
            tollSceneCode=tollItem.getSceneCode();
            moneyMethod=tollItem.getMoneyMethod();
            frequency=tollItem.getChargeFrequency();
        }
        chargeItemRecord=chargeItemRecord.setCustomerCode(chargeRecord.getCustomerCode())
                .setCustomerName(chargeRecord.getCustomerName()).setGasmeterCode(chargeRecord.getGasMeterCode())
                .setChargeItemName(itemType.getDesc())
                .setChargeItemSourceCode(itemType.getCode())
                .setChargeItemSourceName(itemType.getDesc())
                .setChargeItemSourceId(sourceId)
                .setChargeNo(chargeRecord.getChargeNo())
                .setMoneyMethod(moneyMethod)
                .setChargeFrequency(frequency)
                .setIsReductionItem(false)
                .setBusinessHallId(chargeRecord.getBusinessHallId())
                .setBusinessHallName(chargeRecord.getBusinessHallName())
                .setTollItemId(tollId)
                .setTollItemScene(tollSceneCode)
                ;
        setCommonParams(chargeItemRecord);
        return chargeItemRecord;
    }


    private List<CustomerEnjoyActivityRecord> getReductions(List<ChargeItemRecord> itemRecords,Map<Long, CustomerEnjoyActivityRecord> reductionMaps){
        List<CustomerEnjoyActivityRecord> list=new ArrayList<>();
        if(CollectionUtils.isEmpty(itemRecords)){
            return list;
        }
        for (ChargeItemRecord itemRecord : itemRecords) {
            if(ChargeItemEnum.REDUCTION.eq(itemRecord.getChargeItemSourceCode())){
                list.add(reductionMaps.get(Long.parseLong(itemRecord.getChargeItemSourceId())));
            }
        }
        return list;
    }




    /**
     * 账户抵扣计算
     */
    private void calculAccountDeduction(ChargeRecord chargeRecord,CustomerAccount account) {
        
        //抵扣金额前端自动计算，先校验抵扣是否计算正确： 抵扣=充值-实收； 再校验账户余额是否够
        if (chargeRecord.getPrechargeDeductionMoney().compareTo(BigDecimal.ZERO) > 0) {
            //记录抵扣  优先账户金额，账户金额够不做赠送金额处理,账户金额不够扣减账户赠送金额
            if (account.getAccountMoney().compareTo(chargeRecord.getPrechargeDeductionMoney()) >= 0) {
                //账户金额够
                //无赠送抵扣
                chargeRecord.setGiveDeductionMoney(BigDecimal.ZERO);
                //账户抵扣后金额：账户金额-抵扣额
                chargeRecord.setChargeAfterMoney(account.getAccountMoney().subtract(chargeRecord.getPrechargeDeductionMoney()));
                //赠送不变
                chargeRecord.setGiveBookAfterMoney(account.getGiveMoney());
            } else {
                BigDecimal preDe = chargeRecord.getPrechargeDeductionMoney();
                //账户金额不够，需要用到 账户赠送额。
                //赠送抵扣=抵扣总额-账户金额（优先使用账户，扣完账户）
                chargeRecord.setGiveDeductionMoney(preDe.subtract(account.getAccountMoney()));
                //界面传入是一个抵扣额，这里重置界面传入的抵扣额为实际账户抵扣。这里是指账户全部金额抵扣。
                chargeRecord.setPrechargeDeductionMoney(account.getAccountMoney());
                //账户抵扣后金额，扣完为0
                chargeRecord.setChargeAfterMoney(BigDecimal.ZERO);
                //账户赠送抵扣后金额=账户赠送金额-赠送抵扣金额
                chargeRecord.setGiveBookAfterMoney(account.getGiveMoney().subtract(chargeRecord.getGiveDeductionMoney()));
            }
        } else {
            //没有抵扣，可以零存和预存（同一个字段）
            chargeRecord.setChargeAfterMoney(account.getAccountMoney().add(chargeRecord.getPrechargeMoney()));
            //账户上的赠送不变
            chargeRecord.setGiveBookAfterMoney(account.getGiveMoney());
        }
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

    private Long getBusinessHallId(BusinessHall businessHall){
        if(businessHall==null) return null;
        return businessHall.getId();
    }

    private String getBusinessHallName(BusinessHall businessHall){
        if(businessHall==null) return null;
        return businessHall.getBusinessHallName();
    }


    private List<Long> getReductionItemIds(List<ChargeItemRecord> itemRecords){
        if(CollectionUtils.isEmpty(itemRecords)){
            return null;
        }
        List<Long> ids=new ArrayList<>();
        for (ChargeItemRecord chargeItemRecord : itemRecords) {
            if(ChargeItemEnum.REDUCTION.eq(chargeItemRecord.getChargeItemSourceCode())){
                ids.add(chargeItemRecord.getTollItemId());
            }
        }
        return ids;
    }

    /**
     * 处理未完成的充值记录
     * @param gasMeterCode
     * @return
     */
    public R<Boolean> dealUnCompleteRecord(String gasMeterCode){
        //如果有未完成的支付单在最开始就有校验。补卡，需要跳过该校验，应该在发起补卡的时候就处理掉该状态。如果补卡是补新用户卡，不补上次充值（上表），补上次充值（没上表）
        RechargeRecord rechargeRecord = rechargeRecordService.getOne(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getGasmeterCode, gasMeterCode)
                .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .in(RechargeRecord::getMoneyFlowStatus, MoneyFlowStatusEnum.waite_deal.getCode(),
                        MoneyFlowStatusEnum.waite_to_meter.getCode(),
                        MoneyFlowStatusEnum.deal_failure.getCode(),
                        MoneyFlowStatusEnum.meter_failure.getCode()
                )
        );
        if(rechargeRecord!=null){
            RechargeRecord update = new RechargeRecord();
            update.setId(rechargeRecord.getId());
            update.setMoneyFlowStatus(MoneyFlowStatusEnum.success.getCode());
            rechargeRecordService.updateById(update);
        }
        return R.success(true);
    }

    private TollItem getTollItem(TolltemSceneEnum penum){
        TollItem tollItem=tollItemService.getOne(
                new LbqWrapper<TollItem>()
                        .eq(TollItem::getSceneCode,penum.getCode())
                .eq(TollItem::getDeleteStatus,DeleteStatusEnum.NORMAL.getValue())
                .eq(TollItem::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        return tollItem;
    }

    private List<ChargeItemRecord> getGasFeeList(ChargeRecord chargeRecord ,List<ChargeItemRecord> itemRecords,List<Long> arrearsDetailIds){
        if(CollectionUtils.isNotEmpty(itemRecords)){
            for (ChargeItemRecord itemRecord : itemRecords) {
                if(ChargeItemEnum.GASFEE.eq(itemRecord.getChargeItemSourceCode())){
                    if(CollectionUtils.isNotEmpty(arrearsDetailIds)){
                        return chargeLoadService.loadGasFeeByArrearsId(arrearsDetailIds);
                    }
                    return chargeLoadService.loadGasFee(chargeRecord.getGasMeterCode());
                }
            }
        }
        return new ArrayList();
    }

    /**
     * 更新气表使用信息
     * @param record
     * @return
     */
    private GasMeterInfo saveMeterUseInfo(ChargeRecord record,GasMeterInfo info){
        if(info==null || record.getRechargeMoney()==null || record.getRechargeMoney().compareTo(BigDecimal.ZERO)==0){
            return null;
        }
        BigDecimal rechargeMoney=record.getRechargeMoney()==null?BigDecimal.ZERO:record.getRechargeMoney();
        BigDecimal rechargeGas=record.getRechargeGas()==null?BigDecimal.ZERO:record.getRechargeGas();

        BigDecimal giveMoney=record.getRechargeGiveMoney()==null?BigDecimal.ZERO:record.getRechargeGiveMoney();
        BigDecimal giveGas=record.getRechargeGiveGas()==null?BigDecimal.ZERO:record.getRechargeGiveGas();

        ChargeUtils.setNullFieldAsZero(info);
        GasMeterInfo updateDto=new GasMeterInfo();
        updateDto.setId(info.getId());
        //累计充值金额  所有充值的表都应该有
        updateDto.setTotalChargeMoney(info.getTotalChargeMoney().add(rechargeMoney));
        //累计充值气量 只有IC卡表且是气量表才会有
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())){
            updateDto.setTotalChargeGas(info.getTotalChargeGas().add(rechargeGas));
        }
        //总使用金额和总使用气量（包含赠送），IC卡表读取不到直接维护，普表和物联网表冒泡结算维护
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType())) {
            updateDto.setTotalUseGasMoney(info.getTotalUseGasMoney()
                    .add(rechargeMoney)
                    .add(giveMoney)
            );
            if(AmountMarkEnum.GAS.eq(record.getSettlementType())) {
                updateDto.setTotalUseGas(info.getTotalUseGas()
                        .add(rechargeGas)
                        .add(giveGas)
                );
            }
        }
        //远程抄表 和 中心预付费表 充值要记录表端余额用于结账和下发显示
        if(OrderSourceNameEnum.REMOTE_READMETER.eq(record.getChargeType()) ||
                OrderSourceNameEnum.CENTER_RECHARGE.eq(record.getChargeType())){
            updateDto.setGasMeterBalance(info.getGasMeterBalance().add(rechargeMoney));
            updateDto.setGasMeterGive(info.getGasMeterGive().add(giveMoney));
        }
        //周期使用气量（金额）物联网表通过上报更新周期累计，IC卡表且是气量表维护：并且购气阶梯换算会使用到，定时任务清零0
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())) {
            updateDto.setCycleUseGas(info.getCycleUseGas()
                    .add(rechargeGas)
                    .add(giveGas)
            );
        }

        //累计计算近三次充值
        BigDecimal chargeVal;
        //只有IC卡表气量表是气量，其他都是金额
        if(OrderSourceNameEnum.IC_RECHARGE.eq(record.getChargeType()) && AmountMarkEnum.GAS.eq(record.getSettlementType())) {
            chargeVal=rechargeGas.add(giveGas);
        }else{
            chargeVal=rechargeMoney.add(giveMoney);
        }
        if(!OrderSourceNameEnum.REMOTE_RECHARGE.eq(record.getChargeType()) ){
            if(info.getValue1().compareTo(BigDecimal.ZERO)!=0){
                updateDto.setValue1(chargeVal);//上次充值气量--其实是本次
                updateDto.setValue2(info.getValue1());//上上次充值气量
                updateDto.setValue3(info.getValue2());//上上上次充值气量
            }else{
                updateDto.setValue1(chargeVal);//上次充值气量--其实是本次
            }
            //累计充值上表次数
            int mcount=info.getTotalRechargeMeterCount()==null?0:info.getTotalRechargeMeterCount().intValue();
            updateDto.setTotalRechargeMeterCount(mcount+1);
        }
        //累计充值次数
        int count=info.getTotalChargeCount()==null?0:info.getTotalChargeCount().intValue();
        updateDto.setTotalChargeCount(count+1);


        //当前价格，表端计价方案号，应该定时任务获取后更新
        info.setCycleUseGas(updateDto.getCycleUseGas());
        info.setGasMeterBalance(updateDto.getGasMeterBalance());
        info.setCycleChargeGas(updateDto.getCycleChargeGas());
        info.setTotalUseGas(updateDto.getTotalUseGas());
        info.setTotalUseGasMoney(updateDto.getTotalUseGasMoney());
        info.setTotalChargeGas(updateDto.getTotalChargeGas());
        info.setTotalChargeMoney(updateDto.getTotalChargeMoney());
        info.setTotalRechargeMeterCount(updateDto.getTotalRechargeMeterCount());
        info.setTotalChargeCount(updateDto.getTotalRechargeMeterCount());//累计充值次数
        info.setValue1(updateDto.getValue1());//上次充值气量--其实是本次
        info.setValue2(updateDto.getValue2());//上上次充值气量
        info.setValue3(updateDto.getValue3());//上上上次充值气量
        gasMeterInfoService.updateById(updateDto);
        return info;
    }
}
