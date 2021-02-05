package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.calculate.AdjustPriceRecordService;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.charges.constant.ChargeMessageConstants;
import com.cdqckj.gmis.charges.dto.ChargeLoadDTO;
import com.cdqckj.gmis.charges.dto.ChargeLoadReqDTO;
import com.cdqckj.gmis.charges.dto.ChargeSceneLoadReqDTO;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.*;
import com.cdqckj.gmis.charges.service.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.operateparam.enumeration.ActivityScene;
import com.cdqckj.gmis.operateparam.service.GiveReductionConfService;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TollItemChargeFrequencyEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.FunctionSwitchPlusService;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.utils.DateUtils;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

//import com.cdqckj.gmis.biztemporary.service.AdjustPriceRecordService;

/**
 * 收费项加载
 *
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
@DS("#thread.tenant")
public class ChargeLoadServiceImpl extends SuperCenterServiceImpl implements ChargeLoadService {

    @Autowired
    GasmeterArrearsDetailService gasmeterArrearsDetailService;
    @Autowired
    CustomerSceneChargeOrderService customerSceneChargeService;
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
     CustomerAccountService customerAccountService;
    @Autowired
     CustomerAccountSerialService customerAccountSerialService;
    @Autowired
     CustomerEnjoyActivityRecordService customerEnjoyActivityRecordService;
    @Autowired
     GasMeterService gasMeterService;
    @Autowired
    GasMeterVersionService gasMeterVersionService;
    @Autowired
    GiveReductionConfService giveReductionConfService;
    @Autowired
    ChargeInsuranceDetailService chargeInsuranceDetailService;

    @Autowired
    AdjustPriceRecordService adjustPriceRecordService;

    @Autowired
    FunctionSwitchPlusService functionSwitchPlusService;

    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    CalculateService calculateService;

    @Autowired
    OtherFeeLoadService otherFeeLoadService;


    @Autowired
    CustomerService customerService;

    @Autowired
    GasMeterInfoService gasMeterInfoService;

    @Autowired
    RechargeRecordService rechargeRecordService;

    @Autowired
    ChargesValidService chargesValidService;

    /**
     * 固定场景费加载
     *
     * @return
     */
    public R<ChargeLoadDTO> thirdGasFeeChargeLoad(ChargeLoadReqDTO param) {

        ChargeLoadDTO loadDTO = new ChargeLoadDTO();
        loadDTO.setItemList(new ArrayList<>());

        GasMeter meter;
        R<GasMeter> r=baseValid(param.getGasMeterCode(),param.getCustomerCode());
        if(r.getIsError()){
            throw BizException.wrap(r.getMsg());
        }
        meter=r.getData();
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        loadGasFeeItem(meter,loadDTO,version);
        loadAccount(param.getCustomerCode(),loadDTO);

        BigDecimal total = new BigDecimal("0.00");
        for (ChargeItemRecord item : loadDTO.getItemList()) {
            if (item.getChargeItemMoney() != null) {
                total=total.add(item.getChargeItemMoney());
            }
        }
        //生成票据编号
        loadDTO.setManualInput(GmisSysSettingUtil.getInvoiceCodeRule().intValue()==0?true:false);
        loadDTO.setTotalMoney(total);
        return R.success(loadDTO);
    }

    /**
     * 固定场景费加载
     *
     * @return
     */
    public R<ChargeLoadDTO> sceneChargeLoad(ChargeSceneLoadReqDTO param) {
        ChargeLoadDTO loadDTO = new ChargeLoadDTO();
        loadDTO.setItemList(new ArrayList<>());
        loadDTO.setWaiteAppendItemList(new ArrayList<>());
        loadDTO.setRechargeActivityList(new ArrayList<>());
        GasMeter meter;
        R<GasMeter> r=baseValid(param.getGasMeterCode(),param.getCustomerCode());
        if(r.getIsError()){
            return R.fail(r.getMsg(),r.getDebugMsg());
        }else{
            meter=r.getData();
        }
        chargesValidService.validUncompleteCharge(param.getGasMeterCode(),param.getCustomerCode());

        //一般场景收费不会太多，10条以内且数据不大，所以这里直接出来过滤，便于以后扩展多个场景类型一起查询。
        Map<Long,ChargeItemRecord> items=loadSceneFee(param.getSceneType(),param.getBizNoOrId());
        loadDTO.getItemList().addAll(items.values());
//        for (Map.Entry<Long, ChargeItemRecord> item : items.entrySet()) {
//            if(item.getValue().getTollItemScene().equals(sceneType)){
//                loadDTO.getItemList().add(item.getValue());
//            }
//        }
        //生成缴费编号
        Boolean noSwitch=GmisSysSettingUtil.getInvoiceCodeRule().intValue()==0?true:false;
        Boolean isOpenInsurance=GmisSysSettingUtil.getInsuranceCodeRule().intValue()==1?true:false;
        loadDTO.setManualInput(noSwitch);
        loadDTO.setIsOpenInsurance(isOpenInsurance);
        //加载账户信息
        loadAccount(param.getCustomerCode(),loadDTO);
        //4.减免项,放在前面不加载
        loadReductItem(meter,loadDTO);
//        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        //换表拆表会存在燃气费，正常情况不应该有滞纳金,换表拆表前有缴清费用控制
        if(TolltemSceneEnum.DIS_METER.eq(param.getSceneType()) ||TolltemSceneEnum.CHANGE_METER.eq(param.getSceneType())){
            loadDTO.getItemList().addAll(loadGasFeeByArrearsId(param.getArrearsDetailIds()));
        }

        GasMeterInfo meterInfo=gasMeterInfoService.getByMeterAndCustomerCode(param.getGasMeterCode(),param.getCustomerCode());
        if(meterInfo!=null){
            loadDTO.setAccountMeterMoney(meterInfo.getGasMeterBalance());
        }
        BigDecimal total = new BigDecimal("0.00");
        for (ChargeItemRecord item : loadDTO.getItemList()) {
            if (item.getChargeItemMoney() != null) {
                total=total.add(item.getChargeItemMoney());
            }
        }
        //生成票据编号
        loadDTO.setManualInput(GmisSysSettingUtil.getInvoiceCodeRule().intValue()==0?true:false);
        loadDTO.setTotalMoney(total);
        return R.success(loadDTO);
    }
    
    /**
     * 收费项加载，将各种收费内容统一分类简化输出到页面（燃气费，滞纳金，场景费）
     * 将减免设置为收费项进行加载
     *
     * @param gasMeterCode
     * @param customerNo
     * @return
     */
    public R<ChargeLoadDTO> chargeLoad(String gasMeterCode, String customerNo) {
        GasMeter meter;
        R<GasMeter> baseR=baseValid(gasMeterCode,customerNo);
        if(baseR.getIsError()){
            return R.fail(baseR.getMsg(),baseR.getDebugMsg());
        }else{
            meter=baseR.getData();
        }
        ChargeLoadDTO loadDTO = new ChargeLoadDTO();
        GasMeterInfo meterInfo=gasMeterInfoService.getByMeterAndCustomerCode(gasMeterCode,customerNo);
        if(meterInfo!=null) {
            loadDTO.setAccountMeterMoney(meterInfo.getGasMeterBalance());
        }else{
            throw BizException.wrap("表具信息已不存在");
        }
        loadDTO.setItemList(new ArrayList<>());
        loadDTO.setWaiteAppendItemList(new ArrayList<>());
        loadDTO.setRechargeActivityList(new ArrayList<>());

        //加载账户信息
        loadAccount(customerNo,loadDTO);
//        if(r.getIsError()){
//            return R.fail(r.getMsg(),r.getDebugMsg());
//        }
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        if(OrderSourceNameEnum.IC_RECHARGE.eq(version.getOrderSourceName())){
            RechargeRecord rechargeRecord = rechargeRecordService.getOne(new LbqWrapper<RechargeRecord>()
                    .eq(RechargeRecord::getGasmeterCode, gasMeterCode)
                    .eq(RechargeRecord::getCustomerCode, customerNo)
                    .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue())
                    .eq(RechargeRecord::getMoneyFlowStatus, MoneyFlowStatusEnum.waite_deal.getCode())
            );
            if (rechargeRecord != null) {
                loadDTO.setHaveNoWriteCardRecord(true);
            }
        }

//        Map<Long,ChargeItemRecord>  oasfee =loadOpenAccountSceneFee(TolltemSceneEnum.OPEN_ACCOUNT.getCode()
//                ,gasMeterCode,customerNo);
//        //开户收费特殊处理
//        if(oasfee!=null && oasfee.size()>0){
//            loadDTO.setIsOpenAccount(true);
//            loadDTO.getItemList().addAll(oasfee.values());
//        }
        //1.后付费表加载燃气费
        loadGasFeeItem(meter,loadDTO,version);
        //2.场景费,改版后不存在场景收费了
        loadDTO.getItemList().addAll(loadUnchargeSceneFee(gasMeterCode,customerNo).values());
        //3.附加费，区分加载：按需收费 一次性和周期性收费
        Customer customer=customerService.getOne(new LbqWrapper<Customer>().eq(Customer::getCustomerCode,customerNo));
        loadOtherFeeItem(meter,customer,loadDTO);
        //4.减免项,一定放在燃气费和场景费附加费之后，需要根据收费项去加载减免项
        loadReductItem(meter,loadDTO);

        //5.调价补差收费项
        loadDTO.getItemList().addAll(loadAdjustPrice(gasMeterCode));

        //保险信息
        loadInsurance(meter,loadDTO);
        //充值优惠活动
        loadActivty(meter,loadDTO,version);
        //初始设置收费金额
        BigDecimal total = new BigDecimal("0.00");
        for (ChargeItemRecord item : loadDTO.getItemList()) {
            if (item.getChargeItemMoney() != null) {
                total=total.add(item.getChargeItemMoney());
            }
        }
        //生成票据编号
        loadDTO.setManualInput(GmisSysSettingUtil.getInvoiceCodeRule().intValue()==0?true:false);
        loadDTO.setTotalMoney(total);

        return R.success(loadDTO);
    }

    /**
     * 基本校验
     * @param gasMeterCode
     * @param customerNo
     * @return
     */
    private R<GasMeter> baseValid(String gasMeterCode,String customerNo){

        if (StringUtils.isBlank(gasMeterCode) && StringUtils.isBlank(customerNo)) {
            log.info("表具编号和客户编号不能为空");
            return R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_ERR_PARAM_VALID),"表具编号和客户编号不能为空");
        }
        GasMeter meter =gasMeterService.getOne(new LbqWrapper<GasMeter>().eq(GasMeter::getGasCode,gasMeterCode));
        GasMeterVersion version=gasMeterVersionService.getById(meter.getGasMeterVersionId());
        if (meter==null) {
            log.info("表具不可用");
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_NOT),"表具不可用");
        }
        if (version==null) {
            log.info("表具不可用");
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_NOT),"表具不可用");
        }
        if (version.getOrderSourceName()==null || meter.getUseGasTypeId()==null || meter.getOpenAccountTime()==null) {
            log.info("表具信息不完整");
            return R.fail(i18nUtil.getMessage(MessageConstants.G_METER_UNCOMPLETEINFO),"表具信息不完整");
        }
        return R.success(meter);
    }

    /**
     * 加载账户信息
     * @param customerNo
     * @param loadDTO
     * @return
     */
    private R<Boolean> loadAccount(String customerNo,ChargeLoadDTO loadDTO){

        //设置账户金额,这个可以单独接口查询，界面显示
        CustomerAccount account=customerAccountService.getOne(new LbqWrapper<CustomerAccount>()
                .eq(CustomerAccount::getCustomerCode,customerNo));
        if(account==null){
            loadDTO.setAccountMoney(BigDecimal.ZERO);
            return R.success(true);
//            return R.fail(i18nUtil.getMessage(MessageConstants.G_ACCOUNT_UNOPEN),"未开户");
        }
        ChargeUtils.setNullFieldAsZero(account);
        loadDTO.setAccountMoney(account.getAccountMoney().add(account.getGiveMoney()));
        return R.success(true);
    }

    private void loadOtherFeeItem(GasMeter meter, Customer customer,ChargeLoadDTO loadDTO){
        List<ChargeItemRecord> items=otherFeeLoadService.loadOtherFeeItem(meter,customer);
        for (ChargeItemRecord item : items) {
            if(TollItemChargeFrequencyEnum.ON_DEMAND.eq(item.getChargeFrequency())){
                loadDTO.getWaiteAppendItemList().add(item);
            }else{
                loadDTO.getItemList().add(item);
            }
        }


    }
    /**
     * 后付费表加载燃气费 和 减免项。
     * @param meter
     * @param loadDTO
     */
    private void loadGasFeeItem(GasMeter meter,ChargeLoadDTO loadDTO,GasMeterVersion version){
        if (!OrderSourceNameEnum.IC_RECHARGE.getCode().equals(version.getOrderSourceName()) &&
                !OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(version.getOrderSourceName()) &&
                !OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(version.getOrderSourceName())
        ) {
            //加载燃气费之前先刷新滞纳金
            R<Boolean> r= calculateService.calculatePenalty(meter.getGasCode());
            if(r.getIsError() || r.getData()==null || !r.getData()){
                throw BizException.wrap("滞纳金计算异常");
            }
            //燃气费
            loadDTO.getItemList().addAll(loadGasFee(meter.getGasCode()));
        }
    }

    /**
     * 后付费表加载燃气费 和 减免项。
     * @param meter
     * @param loadDTO
     */
    private void loadReductItem(GasMeter meter,ChargeLoadDTO loadDTO){
        //减免项,根据收费项加载减免项，根据场景加载减免项
        List<ChargeItemRecord> list=loadDTO.getItemList();
        Set<Long> tollItemIds=new HashSet<>();
        //这里只加载了非按需收费项的减免项,非固定金额的收费项不应该加载，无意义，输入1分钱不就完了吗？
        for (ChargeItemRecord item : list) {
            //非固定金额的收费项不应该加载，无意义，输入1分钱不就完了吗？
            if(item.getTollItemId()!=null && MoneyMethodEnum.fixed.eq(item.getMoneyMethod())){
                tollItemIds.add(item.getTollItemId());
            }
        }
        //可以扩展非按需收费的减免项
//        loadDTO.getWaiteAppendItemList()
        if(tollItemIds.isEmpty()){
            return ;
        }
        Collection<CustomerEnjoyActivityRecord> reductions = loadGiveReduction(meter, ActivityScene.CHARGE_DE, Lists.newArrayList(tollItemIds.iterator())).values();
        if (reductions != null) {
            for (CustomerEnjoyActivityRecord reduction : reductions) {
                loadDTO.getWaiteAppendItemList().add(convertReductionToChargeItem(reduction));
            }
        }
    }

    /**
     * 物联网表端结算表或者卡表 加载优惠活动
     * @param meter
     * @param loadDTO
     */
    private void loadActivty(GasMeter meter,ChargeLoadDTO loadDTO,GasMeterVersion version){
        if (OrderSourceNameEnum.IC_RECHARGE.getCode().equals(version.getOrderSourceName()) ||
                OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(version.getOrderSourceName()) ||
                OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(version.getOrderSourceName())
        ) {
            loadDTO.getRechargeActivityList().addAll(loadGiveReduction(meter, ActivityScene.RECHARGE_GIVE,null).values());
        }
    }


    /**
     * 加载燃气费和滞纳金收费项
     *
     * @param arrearsId
     */
    public List<ChargeItemRecord> loadGasFeeByArrearsId(List<Long> arrearsId) {
        if(arrearsId==null) return new ArrayList<>();
        List<ChargeItemRecord> list=new ArrayList<>();
        BigDecimal zero = new BigDecimal("0.00");
        TollItem tollItem=tollItemService.getOne(
                new LbqWrapper<TollItem>()
                        .eq(TollItem::getSceneCode,TolltemSceneEnum.GAS_FEE.getCode())
        );
        if(tollItem==null){
            log.error("系统未配置抄表收费项");
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_ERR_CONFIG_NO_READMETER_TOLL_ITEM));
        }
        List<GasmeterArrearsDetail> arrears = gasmeterArrearsDetailService.listByIds(arrearsId);
        for (GasmeterArrearsDetail arrear : arrears) {
            if (arrear.getArrearsMoney() != null && arrear.getArrearsMoney().compareTo(zero) > 0){
                String itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_GASFEE);
                list.add(convertGasFeeToChargeItem(arrear,ChargeItemEnum.GASFEE,itemName,tollItem));
            }
            if (arrear.getLatepayAmount() != null && arrear.getLatepayAmount().compareTo(zero) > 0){
                String itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_LATEFEE);
                list.add(convertGasFeeToChargeItem(arrear,ChargeItemEnum.LAYPAYFEE,itemName,tollItem));
            }
        }

        return list;
    }

    /**
     * 加载燃气费和滞纳金收费项
     *
     * @param gasMeterCode
     */
    public List<ChargeItemRecord> loadGasFee(String gasMeterCode) {
        List<ChargeItemRecord> list=new ArrayList<>();
        BigDecimal zero = new BigDecimal("0.00");
        TollItem tollItem=tollItemService.getOne(
                new LbqWrapper<TollItem>()
                        .eq(TollItem::getSceneCode,TolltemSceneEnum.GAS_FEE.getCode())
        );
        if(tollItem==null){
            log.error("系统未配置抄表收费项");
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_ERR_CONFIG_NO_READMETER_TOLL_ITEM));
        }
        List<GasmeterArrearsDetail> arrears = gasmeterArrearsDetailService.list(
                new LbqWrapper<GasmeterArrearsDetail>()
                        .eq(GasmeterArrearsDetail::getGasmeterCode,gasMeterCode)
                        .eq(GasmeterArrearsDetail::getArrearsStatus, ChargeStatusEnum.UNCHARGE.getCode())
                        .eq(GasmeterArrearsDetail::getDataStatus,DataStatusEnum.NORMAL.getValue())
                );

        if (arrears != null) {
            for (GasmeterArrearsDetail arrear : arrears) {
                if (arrear.getArrearsMoney() != null && arrear.getArrearsMoney().compareTo(zero) > 0){
                    String itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_GASFEE);
                    list.add(convertGasFeeToChargeItem(arrear,ChargeItemEnum.GASFEE,itemName,tollItem));
                }
                if (arrear.getLatepayAmount() != null && arrear.getLatepayAmount().compareTo(zero) > 0){
                    String itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_LATEFEE);
                    list.add(convertGasFeeToChargeItem(arrear,ChargeItemEnum.LAYPAYFEE,itemName,tollItem));
                }
            }
        }
        return list;
    }


    /**
     * 加载场景费 收费项
     * 每笔场景费都是一项
     *
     * @param scence
     */
    public Map<Long,ChargeItemRecord>  loadSceneFee(String scence,String bizNoOrId) {
        Map<Long,ChargeItemRecord> itemMap=new HashMap<>();
        List<CustomerSceneChargeOrder> sceneOrders = customerSceneChargeOrderService.loadSceneOrders(scence,bizNoOrId);
        if (sceneOrders != null) {
            for (CustomerSceneChargeOrder sceneOrder : sceneOrders) {
                itemMap.put(sceneOrder.getId(),convertSceneOrderToChargeItem(sceneOrder));
            }
        }
        return itemMap;
    }

    /**
     * 加载场景费 收费项
     * 每笔场景费都是一项
     *
     */
    public Map<Long,ChargeItemRecord>  loadOpenAccountSceneFee(String sceneCode,String gasMeterCode,String customerCode) {
        Map<Long,ChargeItemRecord> itemMap=new HashMap<>();
        CustomerSceneChargeOrder sceneOrder = customerSceneChargeOrderService.loadOpenAccountSceneOrders(sceneCode,gasMeterCode,customerCode);
        if (sceneOrder != null) {
            itemMap.put(sceneOrder.getId(),convertSceneOrderToChargeItem(sceneOrder));
        }
        return itemMap;
    }

    /**
     * 加载场景费 收费项
     * 每笔场景费都是一项
     *
     */
    public Map<Long,ChargeItemRecord>  loadUnchargeSceneFee(String gasMeterCode,String customerCode) {
        List<CustomerSceneChargeOrder>sceneOrders=customerSceneChargeOrderService.list(new LbqWrapper<CustomerSceneChargeOrder>()
                .eq(CustomerSceneChargeOrder::getGasmeterCode,gasMeterCode)
                .eq(CustomerSceneChargeOrder::getCustomerCode,customerCode)
                .eq(CustomerSceneChargeOrder::getChargeStatus,ChargeStatusEnum.UNCHARGE.getCode())
                .eq(CustomerSceneChargeOrder::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        Map<Long,ChargeItemRecord> itemMap=new HashMap<>();
        if(CollectionUtils.isNotEmpty(sceneOrders)){
            for (CustomerSceneChargeOrder sceneOrder : sceneOrders) {
                itemMap.put(sceneOrder.getId(),convertSceneOrderToChargeItem(sceneOrder));
            }
        }
        return itemMap;
    }



    /**
     * 收费减免项 活着充值优惠项加载
     * 开始当天和结束当天都生效的
     */
    public Map<Long,CustomerEnjoyActivityRecord> loadGiveReduction(GasMeter meter,
                                                                          ActivityScene scene,
                                                                          List<Long> tollItemIds
    ) {
        List<GiveReductionConf> list= giveReductionConfService.queryEffectiveGiveReduction(
                new GiveReductionQueryDTO().setActivityScene(scene)
                        .setUseGasTypeId(meter.getUseGasTypeId())
                        .setTollItemIds(tollItemIds)
        ).getData();
        CustomerEnjoyActivityRecord tempActivity;
        Map<Long,CustomerEnjoyActivityRecord> activityMap = new HashMap<>();
        if(list==null) return activityMap;
        for (GiveReductionConf giveReductionConf : list) {
            tempActivity = new CustomerEnjoyActivityRecord();
            BeanUtils.copyProperties(giveReductionConf, tempActivity);
            tempActivity.setId(null);
            tempActivity.setActivityId(giveReductionConf.getId());
            tempActivity.setTollItemId(giveReductionConf.getTollItemId());
            tempActivity.setTollItemScene(giveReductionConf.getTollItemScene());
            if (MoneyMethodEnum.fixed.getCode().equals(tempActivity.getActivityMoneyType())) {
                tempActivity.setTotalMoney(tempActivity.getActivityMoney());
            }
            activityMap.put(tempActivity.getActivityId(),tempActivity);
        }
        return activityMap;
    }

    /**
     * 加载保险信息
     * @param meter
     * @param loadDTO
     */
    public void loadInsurance(GasMeter meter,ChargeLoadDTO loadDTO){
        Boolean isOpenInsurance=GmisSysSettingUtil.getInsuranceCodeRule().intValue()==1?true:false;
        loadDTO.setIsOpenInsurance(isOpenInsurance);
        List<ChargeInsuranceDetail> insurances = chargeInsuranceDetailService.list(
                new LbqWrapper<ChargeInsuranceDetail>()
                .eq(ChargeInsuranceDetail::getGasmeterCode,meter.getGasCode())
                .eq(ChargeInsuranceDetail::getStatus,DataStatusEnum.NORMAL.getValue()));
        loadDTO.setIsBuyInsurance(false);
        loadDTO.setInsuranceIsExpire(false);
        if (insurances != null && insurances.size() > 0) {
            loadDTO.setIsBuyInsurance(true);
            insurances=insurances.stream().sorted(Comparator.comparing(ChargeInsuranceDetail::getInsuranceEndDate).reversed()).collect(Collectors.toList());
            LocalDate date=insurances.get(0).getInsuranceEndDate();

            loadDTO.setInsuranceExpireDate(DateUtils.formatAsDate(date.atTime(1,1)));
            if (date.isBefore(LocalDate.now())) {
                //已过期
                loadDTO.setInsuranceIsExpire(true);
            }else{
                //有没必要只有一个月以内的时候判断直接显示还剩多少天，这里无单位
                Period period=Period.between(LocalDate.now(),date);
                if(period.getYears()==0 && period.getMonths()==0){
                    loadDTO.setInsuranceExpireDate(period.getDays()+I18nUtil.getMsg("天",period.getDays()==1?" day":" days"));
                }
            }
        }
    }

    /**
     * 查询调价补差收费项
     * @param gasMeterCode
     * @return
     */
    public List<ChargeItemRecord> loadAdjustPrice(String gasMeterCode){
        List<AdjustPriceRecord> list=adjustPriceRecordService.list(new LbqWrapper<AdjustPriceRecord>()
                .eq(AdjustPriceRecord::getGasmeterCode,gasMeterCode)
                .eq(AdjustPriceRecord::getDataStatus, AdjustPriceStateEnum.WAIT_CHARGE.getCode())
        );

        TollItem tollItem=tollItemService.getOne(
                new LbqWrapper<TollItem>()
                        .eq(TollItem::getSceneCode,TolltemSceneEnum.GAS_COMPENSATION.getCode())
        );
        if(tollItem==null){
            log.error("系统未配置调价补差收费项");
            throw new BizException(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_ERR_CONFIG_NO_ADJUST_PRICE_TOLL_ITEM));
        }

        List<ChargeItemRecord> resultList=new ArrayList<>();
        for (AdjustPriceRecord adjustPriceRecord : list) {
            resultList.add(convertAdjustPriceChargeItem(adjustPriceRecord,ChargeItemEnum.ADJUST_PRICE,tollItem));
        }

//        ADJUST_PRICE_RECORD
        return resultList;
    }


    /**
     * 转换活动减免项为统一收费项内容
     * @param reduction
     * @return
     */
    private ChargeItemRecord convertReductionToChargeItem(CustomerEnjoyActivityRecord reduction){
        ChargeItemRecord chargeItemRecord= new ChargeItemRecord()
                .setMoneyMethod(reduction.getActivityMoneyType())
                .setChargeItemSourceCode(ChargeItemEnum.REDUCTION.getCode())
                .setChargeItemSourceName(ChargeItemEnum.REDUCTION.getDesc())
                .setChargeItemName(reduction.getActivityName())
                .setChargeItemSourceId(reduction.getActivityId().toString())
                .setChargeItemMoney(reduction.getTotalMoney())
                .setTollItemScene(reduction.getTollItemScene())
                .setTollItemId(reduction.getTollItemId())
                .setChargeFrequency(TollItemChargeFrequencyEnum.ON_DEMAND.getCode())
                .setIsReductionItem(true);
        setCommonParams(chargeItemRecord);
        return chargeItemRecord;
    }



    private ChargeItemRecord convertSceneOrderToChargeItem(CustomerSceneChargeOrder sceneOrder){
        List<TollItem> items=tollItemService.list(
                new LbqWrapper<TollItem>()
                        .eq(TollItem::getDataStatus,DataStatusEnum.NORMAL.getValue())
                        .eq(TollItem::getDeleteStatus,DeleteStatusEnum.NORMAL.getValue())
                        .ne(TollItem::getSceneCode,TolltemSceneEnum.OTHER.getCode())
        );
        TollItem itemTemp=null;
        if(sceneOrder.getTollItemId()!=null){
            if(items!=null){
                for (TollItem item : items) {
                    if(item.getId().equals(sceneOrder.getTollItemId())){
                        itemTemp=item;
                    }
                }
            }
        }
        ChargeItemRecord chargeItemRecord=new ChargeItemRecord()
                .setMoneyMethod(itemTemp==null?MoneyMethodEnum.fixed.getCode():itemTemp.getMoneyMethod())
                .setChargeItemSourceCode(ChargeItemEnum.SCENEFEE.getCode())
                .setChargeItemSourceName(ChargeItemEnum.SCENEFEE.getDesc())
                .setChargeItemName(sceneOrder.getTollItemName())
                .setChargeItemSourceId(sceneOrder.getId().toString())
                .setChargeItemMoney(sceneOrder.getTotalMoney())
                .setTollItemScene(sceneOrder.getTollItemScene())
                .setTollItemId(sceneOrder.getTollItemId())
                .setTotalCount(1)
                .setPrice(sceneOrder.getTotalMoney())
                .setChargeFrequency(sceneOrder.getTollItemFrequency())
                .setIsReductionItem(false);
        setCommonParams(chargeItemRecord);
        return chargeItemRecord;
    }

    private ChargeItemRecord convertGasFeeToChargeItem(GasmeterArrearsDetail arrear,ChargeItemEnum itemEnum,
                                                       String itemName,TollItem tollItem){
        ChargeItemRecord chargeItemRecord=new ChargeItemRecord().setMoneyMethod(MoneyMethodEnum.fixed.getCode())
                .setChargeItemSourceCode(itemEnum.getCode())
                .setChargeItemSourceName(itemEnum.getDesc())
                .setChargeItemName(arrear.getReadmeterMonth()+I18nUtil.getMsg("月 ","M ") + itemName)
                .setChargeItemSourceId(arrear.getId().toString()).setChargeItemGas(arrear.getGas())
                .setUseGasMoney(arrear.getGasMoney())
                .setChargeItemMoney(arrear.getArrearsMoney())
                .setChargeItemTime(arrear.getCreateTime())
                .setChargeFrequency(tollItem.getChargeFrequency())
                .setTollItemScene(tollItem.getSceneCode())
                .setTollItemId(tollItem.getId())
                .setTotalCount(1)
                .setPrice(arrear.getPrice())
                .setLeadderPriceDetail(arrear.getLeadderPriceDetail())
                .setIsReductionItem(false);
        String[] rmonth=arrear.getReadmeterMonth().split("-");
        LocalDateTime itemTime=LocalDateTime.of(Integer.parseInt(rmonth[0]),Integer.parseInt(rmonth[1]),1,0,0);
        chargeItemRecord.setChargeItemTime(itemTime);
        if(itemEnum.getCode().equals(ChargeItemEnum.LAYPAYFEE.getCode())){
            chargeItemRecord.setChargeItemMoney(arrear.getLatepayAmount());
        }
        setCommonParams(chargeItemRecord);
        return chargeItemRecord;
    }

    private ChargeItemRecord convertAdjustPriceChargeItem(AdjustPriceRecord adjustPriceRecord,ChargeItemEnum itemEnum,
                                                       TollItem tollItem){

        String itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_ADJUST_PRICE);
        if(AdjustPriceSourceEnum.READ_METER.getCode().intValue()==adjustPriceRecord.getSource().intValue()){
            //抄表
            itemName+=adjustPriceRecord.getRecordTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            +i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_READMETER_CHARGE);
        }else{
            //充值
            itemName+=adjustPriceRecord.getRechargeTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
            +i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_RECHARGE);
        }
        ChargeItemRecord chargeItemRecord=new ChargeItemRecord().setMoneyMethod(MoneyMethodEnum.fixed.getCode())
                .setChargeItemSourceCode(itemEnum.getCode())
                .setChargeItemSourceName(itemEnum.getDesc())
                .setChargeItemName(itemName)
                .setChargeItemSourceId(adjustPriceRecord.getId().toString())
                .setChargeItemGas(adjustPriceRecord.getCompensationGas())
                .setChargeItemMoney(adjustPriceRecord.getCompensationMoney())
                .setChargeItemTime(adjustPriceRecord.getCreateTime())
                .setChargeFrequency(TollItemChargeFrequencyEnum.ONE_TIME.getCode())
                .setTollItemScene(TolltemSceneEnum.GAS_COMPENSATION.getCode())
                .setTollItemId(tollItem==null?null:tollItem.getId())
                .setIsReductionItem(false);
        setCommonParams(chargeItemRecord);
        return chargeItemRecord;
    }
}
