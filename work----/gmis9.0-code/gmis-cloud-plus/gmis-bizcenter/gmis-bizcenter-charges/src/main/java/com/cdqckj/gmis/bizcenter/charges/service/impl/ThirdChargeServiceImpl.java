package com.cdqckj.gmis.bizcenter.charges.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.authority.api.LivingExpensesBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayBody;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayParam;
import com.cdqckj.gmis.bizcenter.charges.dto.AlipayReturnDto;
import com.cdqckj.gmis.bizcenter.charges.dto.RcvblDet;
import com.cdqckj.gmis.bizcenter.charges.enumeration.AlipayRequestCodeEnum;
import com.cdqckj.gmis.bizcenter.charges.service.*;
import com.cdqckj.gmis.bizcenter.iot.service.BusinessService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.ChangeMeterService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.RemoveMeterService;
import com.cdqckj.gmis.biztemporary.ChangeMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.RemoveMeterRecordBizApi;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.dto.CardInfoUpdateDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.charges.api.*;
import com.cdqckj.gmis.charges.constant.ChargeMessageConstants;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.*;
import com.cdqckj.gmis.charges.enums.*;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.Order;
import com.cdqckj.gmis.invoice.enumeration.InvoiceTypeEnum;
import com.cdqckj.gmis.operateparam.FunctionSwitchBizApi;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotUpdateDTO;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.tenant.entity.LivingExpenses;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.I18nUtil;
import io.seata.core.context.RootContext;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收费相关业务数据计算和校验以及数据存储
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
public class ThirdChargeServiceImpl extends SuperCenterServiceImpl implements ThirdChargeService{



    @Autowired
    ChargeBizApi chargeBizApi;

    @Autowired
    TollItemBizApi tollItemBizApi;

    @Autowired
    ChargeRecordBizApi chargeRecordBizApi;

    @Autowired
    ChargeItemRecordBizApi chargeItemRecordBizApi;

    @Autowired
    GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    @Autowired
    CustomerAccountBizApi customerAccountBizApi;

    @Autowired
    RechargeRecordBizApi rechargeRecordBizApi;

    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;

    @Autowired
    CardInfoBizApi cardInfoBizApi;

    @Autowired
    UseGasTypeBizApi useGasTypeBizApi;

    @Autowired
    BizOrderService bizOrderService;

    @Autowired
    ReadMeterDataIotApi readMeterDataIotApi;

    @Autowired
    GasMeterInfoBizApi gasMeterInfoBizApi;
    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @Autowired
    FunctionSwitchBizApi functionSwitchBizApi;

    @Autowired
    BusinessService businessService;

    @Autowired
    ChangeMeterService changeMeterService;
    @Autowired
    RemoveMeterService removeMeterService;

    @Autowired
    ChangeMeterRecordBizApi changeMeterRecordBizApi;
    @Autowired
    RemoveMeterRecordBizApi removeMeterRecordBizApi;
//
//    @Autowired
//    LivingExpenses LivingExpenses;

    @Autowired
    ChargePayServiceImpl chargePayServiceImpl;
    @Autowired
    ChargeSendOrderService chargeSendOrderService;

    @Autowired
    ChargeRefundService chargeRefundService;

    @Autowired
    RefundService refundService;

    @Autowired
    CustomerBizApi customerBizApi;

    @Autowired
    ChargeService chargeService;

    @Autowired
    LivingExpensesBizApi livingExpensesBizApi;
    /**
     * 支付宝生活缴费-欠费查询
     * @return
     */
    public R<AlipayBody> alipayChargeLoad(String customerChargeNo,String acctOrgNo) {
        //获取租户
        telentSelector(acctOrgNo);

        CustomerGasMeterRelated related=customerGasMeterRelatedBizApi.findRelatedInfoByChargeNo(customerChargeNo).getData();
        if(related==null){
            log.error("支付宝（生活缴费）查询欠费接口的缴费编号不存在："+customerChargeNo);
            return R.success(AlipayBody.builder().rtnCode(AlipayRequestCodeEnum.THE_NUMBER_IS_ILLEGAL.getCode()).build());
        }
        String gasMeterCode=related.getGasMeterCode();
        String customerCode=related.getCustomerCode();
        GasMeter meter = gasMeterBizApi.findGasMeterByCode(gasMeterCode).getData();
        if(meter==null){
//            throw BizException.wrap("户表信息不存在");
            log.error("支付宝（生活缴费）查询的气表档案信息不存在，归属到缴费编号异常类：{},缴费编号：{}",gasMeterCode,customerChargeNo);
            return R.success(AlipayBody.builder().rtnCode(AlipayRequestCodeEnum.THE_NUMBER_IS_ILLEGAL.getCode()).build());
        }
        //IC卡表要判断，物联网表不用，只用在收费内部判断充值记录即可，IC卡表不判断，充值后可能引起不必要的麻烦，退费，清零等。
        GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        if(!OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())  && !OrderSourceNameEnum.READMETER_PAY.eq(version.getOrderSourceName())){
//            throw BizException.wrap("该户表暂时不支持网络平台支付");
            log.error("支付宝（生活缴费）该户表暂时不支持网络平台支付，归属到缴费编号异常类：{}，户表属于 {} 类型的表",customerChargeNo,OrderSourceNameEnum.get(version.getOrderSourceName()).getDesc());
            return R.success(AlipayBody.builder().rtnCode(AlipayRequestCodeEnum.THE_NUMBER_IS_ILLEGAL.getCode()).build());
        }

        //是否存在未完成的支付订单
        ChargeRecord record=chargeBizApi.getUnCompleteChargeRecord(gasMeterCode,customerCode).getData();
        if(record!=null){
            log.error("支付宝（生活缴费）查询欠费接口,存在未完成收费：{},单号{}",customerChargeNo,record.getChargeNo());
            throw BizException.wrap("存在未完成收费单");
        }
        //是否存在未完成的支付订单
        ChargeRefund refund=chargeBizApi.getUnCompleteChargeRefund(gasMeterCode,customerCode).getData();
        if(refund!=null){
            log.error("支付宝（生活缴费）查询欠费接口,存在未完成退费：{},单号{}",customerChargeNo,record.getChargeNo());
            throw BizException.wrap("存在未完成退费单");
        }
        ChargeLoadReqDTO reqDTO=new ChargeLoadReqDTO();
        reqDTO.setCustomerCode(customerCode);
        reqDTO.setGasMeterCode(gasMeterCode);
        ChargeLoadDTO loadDTO= chargeBizApi.thirdGasFeeChargeLoad(reqDTO).getData();
        return R.success(createBody(loadDTO,customerCode,acctOrgNo,related.getCustomerChargeNo()));
    }
    private AlipayBody createBody(ChargeLoadDTO loadDTO,String customerCode,String acctOrgNo,String customerChargeNo){
        AlipayBody body=new AlipayBody();
        Customer customer=customerBizApi.findCustomerByCode(customerCode).getData();
        body.setAcctOrgNo(acctOrgNo);
        body.setAddr(customer.getContactAddress());
        body.setConsName(customer.getCustomerName());
        //这里要做转换
        body.setConsType(customer.getCustomerTypeCode());
        body.setConsNo(customerChargeNo);
        //资金编号
        body.setCapitalNo("");

        //单位编码 营业厅
        body.setOrgNo("1");
        //单位名称 营业厅名称
        body.setOrgName("测试营业厅");
        //账户余额
        if(loadDTO.getAccountMoney()==null){
            body.setPrepayAmt("0");
        }else{
            body.setPrepayAmt(loadDTO.getAccountMoney()
                    .multiply(new BigDecimal("100"))
                    .setScale(0,BigDecimal.ROUND_DOWN).toPlainString());
        }

        //欠费金额=应收-实收+本期滞纳金

        //合计欠费金额
        body.setTotalOweAmt("0");
        //合计应收金额（实际用气金额）
        body.setTotalRcvblAmt("0");
        //合计实收金额（实际用气金额-结算账户扣除金额，就是本系统欠费金额）
        body.setTotalRcvedAmt("0");
        //合计违约金（滞纳金）
        body.setTotalPenalty("0");
        body.setRecordCount("0");
        List<ChargeItemRecord> itemList=loadDTO.getItemList();
        BigDecimal totalOweAmt=BigDecimal.ZERO;
        BigDecimal totalRcvblAmt=BigDecimal.ZERO;
        BigDecimal totalRcvedAmt=BigDecimal.ZERO;
        BigDecimal totalPenalty=BigDecimal.ZERO;
        List<RcvblDet> details=new ArrayList<>();
        int count=0;
        if(CollectionUtils.isNotEmpty(itemList)){

            Map<String,ChargeItemRecord> lmap=new HashMap<>();
            for (ChargeItemRecord itemRecord : itemList) {
                if(ChargeItemEnum.LAYPAYFEE.eq(itemRecord.getChargeItemSourceCode())){
                    lmap.put(itemRecord.getChargeItemSourceId(),itemRecord);
                }
            }
            RcvblDet detailTemp;
            BigDecimal itemTotal;
            for (ChargeItemRecord itemRecord : itemList) {
                    if(ChargeItemEnum.GASFEE.eq(itemRecord.getChargeItemSourceCode())){
                        //统计燃气费条数
                        count++;
                        //合计欠费
                        totalOweAmt=totalOweAmt.add(itemRecord.getChargeItemMoney());
                        //实际用气金额： 合计应收
                        totalRcvblAmt=totalRcvblAmt.add(itemRecord.getUseGasMoney());
                        //合计实收
                        totalRcvedAmt=totalRcvedAmt.add(itemRecord.getChargeItemMoney());
                        detailTemp=new RcvblDet();
                        detailTemp.setAcctOrgNo(acctOrgNo);
                        detailTemp.setConsName(body.getConsName());
                        detailTemp.setConsNo(body.getConsNo());
                        detailTemp.setOrgName(body.getOrgName());
                        detailTemp.setOrgNo(body.getOrgNo());

                        itemTotal=itemRecord.getChargeItemMoney();
                        //应收
                        detailTemp.setRcvblAmt(amtM100Convert(itemRecord.getUseGasMoney()));
                        //实收
                        detailTemp.setRcvedAmt(amtM100Convert(itemRecord.getChargeItemMoney()));
                        if(lmap.containsKey(itemRecord.getChargeItemSourceId())){
                            detailTemp.setRcvblPenalty(amtM100Convert(lmap.get(itemRecord.getChargeItemSourceId()).getChargeItemMoney()));
                            itemTotal=itemTotal.add(lmap.get(itemRecord.getChargeItemSourceId()).getChargeItemMoney());
                        }else{
                            detailTemp.setRcvblPenalty("0");
                        }
                        //合计欠费
                        detailTemp.setOweAmt(amtM100Convert(itemTotal));
                        detailTemp.setRcvblYm(itemRecord.getChargeItemName().substring(0,7).replace("-",""));
                        detailTemp.setRcvblAmtId(itemRecord.getChargeItemSourceId());
                        detailTemp.setTPq(amtM100Convert(itemRecord.getChargeItemGas()));
                        details.add(detailTemp);
                    }else if(ChargeItemEnum.LAYPAYFEE.eq(itemRecord.getChargeItemSourceCode())){
                        //滞纳金
                        totalPenalty=totalPenalty.add(itemRecord.getChargeItemMoney());
                        //合计欠费
                        totalOweAmt=totalOweAmt.add(itemRecord.getChargeItemMoney());
                    }
            }
            //合计欠费金额
            body.setTotalOweAmt(amtM100Convert(totalOweAmt));
            //合计应收金额（实际用气金额）
            body.setTotalRcvblAmt(amtM100Convert(totalRcvblAmt));
            //合计实收金额（实际用气金额-结算账户扣除金额，就是本系统欠费金额）
            body.setTotalRcvedAmt(amtM100Convert(totalRcvedAmt));
            //合计违约金（滞纳金）
            body.setTotalPenalty(amtM100Convert(totalPenalty));
            body.setRecordCount(count+"");
        }else{
            body.setRtnCode(AlipayRequestCodeEnum.NO_RREARS.getCode());
        }


//        //账户余额
//        private String prepayAmt = "0";
//        //合计欠费金额
//        private String totalOweAmt = "0";
//        //合计应收金额
//        private String totalRcvblAmt = "0";
//        //合计实收金额
//        private String totalRcvedAmt = "0";
//        //合计违约金（滞纳金）
//        private String totalPenalty = "0";
//        //明细记录条数
//        private String recordCount = "0";
//        //明细记录项目
//        private List<RcvblDet> rcvblDet = new ArrayList<>();
        body.setRcvblDet(details);
        return body;
    }
//    private String amtConvert(BigDecimal amt){
//        return amt.multiply(amt.setScale(0,BigDecimal.ROUND_FLOOR).toPlainString();
//    }

    private String amtM100Convert(BigDecimal amt){
        return amt.multiply(new BigDecimal("100")).setScale(0,BigDecimal.ROUND_FLOOR).toPlainString();
    }
    /**
     * 支付宝生活缴费-缴费销账
     * @param alipayParam
     * @return
     */
    @CodeNotLost
    public R<AlipayBody> alipayCharge(AlipayParam alipayParam) {
        //获取租户
        telentSelector(alipayParam.getAcctOrgNo());
        AlipayBody body=new AlipayBody();
        List<RcvblDet> list=alipayParam.getRcvDetList();
        if(CollectionUtils.isEmpty(list)){
            log.info("支付宝（生活缴费）缴费接口：缴费销账数据为空");
            body.setRtnCode(AlipayRequestCodeEnum.PARAMETER_FORMAT_ERROR.getCode());
            return R.success(body);
        }

        List<RcvblDet> items=alipayParam.getRcvDetList();
        String customerChargeNo= items.get(0).getConsNo();

        CustomerGasMeterRelated related=customerGasMeterRelatedBizApi.findRelatedInfoByChargeNo(customerChargeNo).getData();
        if(related==null){
            log.error("支付宝（生活缴费）缴费接口：缴费编号不存在："+customerChargeNo);
            body.setRtnCode(AlipayRequestCodeEnum.PAY_BEYOND_THE_TIME_LIMIT.getCode());
//            return R.success(AlipayBody.builder().rtnCode(AlipayRequestCodeEnum.THE_NUMBER_IS_ILLEGAL.getCode()).build());
            return R.success(body);
        }
        String customerCode=related.getCustomerCode();
        String orgNo= items.get(0).getOrgNo();
        String orgName= items.get(0).getOrgName();
        List<Long> arrearIds=new ArrayList<>();
        for (RcvblDet item : items) {
            arrearIds.add(Long.parseLong(item.getRcvblAmtId()));
        }

        List<GasmeterArrearsDetail> arrearsDetails=gasmeterArrearsDetailBizApi.queryList(arrearIds).getData();
        if(CollectionUtils.isEmpty(arrearsDetails)){
            throw BizException.wrap("支付宝（生活缴费）缴费接口：未知欠费记录");
//            return R.success(AlipayBody.builder().rtnCode(AlipayRequestCodeEnum.THE_NUMBER_IS_ILLEGAL.getCode()).build());

        }
        CustomerAccount account=customerAccountBizApi.queryByParam(CustomerAccount.builder()
                .customerCode(customerCode).build()).getData();
        ChargeItemRecord itemTemp;
        ChargeItemRecordSaveDTO saveTemp;
        List<ChargeItemRecordSaveDTO> itemList=new ArrayList<>();
        TollItem tollItem=tollItemBizApi.query(
                TollItem.builder().sceneCode(TolltemSceneEnum.GAS_FEE.getCode()).build()).getData().get(0);

        BigDecimal totalMoney=BigDecimal.ZERO;
        for (GasmeterArrearsDetail arrearsDetail : arrearsDetails) {
            ChargeUtils.setNullFieldAsZero(arrearsDetail);
            if(ChargeStatusEnum.CHARGED.eq(arrearsDetail.getArrearsStatus())){
                log.info("支付宝（生活缴费）缴费接口：欠费单已缴纳缴费");
                body.setRtnCode(AlipayRequestCodeEnum.THE_BILL_HAS_BEEN_WRITTEN_OFF.getCode());
//            return R.success(AlipayBody.builder().rtnCode(AlipayRequestCodeEnum.THE_NUMBER_IS_ILLEGAL.getCode()).build());
                return R.success(body);
            }
//            String itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_LATEFEE);
            itemTemp=convertGasFeeToChargeItem(arrearsDetail,ChargeItemEnum.GASFEE,"燃气费",tollItem);
            saveTemp=BeanPlusUtil.copyProperties(itemTemp,ChargeItemRecordSaveDTO.class);
            itemList.add(saveTemp);
            totalMoney=totalMoney.add(arrearsDetail.getArrearsMoney());

            if (arrearsDetail.getLatepayAmount().compareTo(BigDecimal.ZERO) > 0){
//                itemName=i18nUtil.getMessage(ChargeMessageConstants.CHARGE_LOAD_INFO_LATEFEE);
                totalMoney=totalMoney.add(arrearsDetail.getLatepayAmount());
                itemTemp=convertGasFeeToChargeItem(arrearsDetail,ChargeItemEnum.LAYPAYFEE,"滞纳金",tollItem);
                saveTemp=BeanPlusUtil.copyProperties(itemTemp,ChargeItemRecordSaveDTO.class);
                itemList.add(saveTemp);
            }
        }
        //保存chargeRecord
        ChargeRecordSaveDTO chargeRecord=createChargeRecord(related,totalMoney,account);
        try {
            //临时处理，后续根据实际数据权限关系再来修改。
            chargeRecord.setBusinessHallId(Long.parseLong(orgNo));
            chargeRecord.setBusinessHallName(orgName);
        }catch (Exception e){}
        chargeRecordBizApi.save(chargeRecord);
        for (ChargeItemRecordSaveDTO itemRecordSaveDTO : itemList) {
            itemRecordSaveDTO.setChargeNo(chargeRecord.getChargeNo());
        }
        chargeItemRecordBizApi.saveList(itemList);
        gasmeterArrearsDetailBizApi.updateChargeStatusComplete(arrearIds);
        GasMeter meter = gasMeterBizApi.findGasMeterByCode(related.getGasMeterCode()).getData();
        GasMeterVersion version = gasMeterVersionBizApi.get(meter.getGasMeterVersionId()).getData();
        //处理远程抄表
        if (OrderSourceNameEnum.REMOTE_READMETER.eq(version.getOrderSourceName())) {
            R<Integer> r= readMeterDataIotApi.updateByGasOweCode(arrearIds);
            if (r.getIsError()) {
                throw new BizException(r.getMsg());
            }
        } else {
            //处理普表抄表
            R<Boolean> r = bizOrderService.updateReadMeterBizStatus(arrearIds, false);
            if (r.getIsError()) {
                throw new BizException(r.getMsg());
            }
        }
        body.setRtnCode(AlipayRequestCodeEnum.SUCCESS.getCode());
        return R.success(body);
    }

    private ChargeRecordSaveDTO createChargeRecord(CustomerGasMeterRelated related,BigDecimal recAmt,CustomerAccount account){
        ChargeRecordSaveDTO chargeRecord=new ChargeRecordSaveDTO();
        chargeRecord.setChargeNo(BizCodeNewUtil.genChargeNo());
        chargeRecord.setChargeChannel(ChargeChannelEnum.ALIPAY_SH.getCode());
        chargeRecord.setSummaryBillStatus(SummaryBillStatusEnum.UNBILL.getCode());
        chargeRecord.setPayRealTime(LocalDateTime.now());
        chargeRecord.setPayTime(LocalDateTime.now());
        chargeRecord.setDataStatus(DataStatusEnum.NORMAL.getValue());
        chargeRecord.setGasMeterCode(related.getGasMeterCode());
        chargeRecord.setCustomerChargeNo(related.getCustomerChargeNo());
        chargeRecord.setActualIncomeMoney(recAmt);
        chargeRecord.setChargeAfterMoney(account.getAccountMoney());
        chargeRecord.setChargePreMoney(account.getAccountMoney());
        chargeRecord.setChargeMethodCode(ChargePayMethodEnum.ALIPAY.getCode());
        chargeRecord.setChargeMethodName(ChargePayMethodEnum.ALIPAY.getDesc());
        chargeRecord.setChargeMoney(recAmt);
        chargeRecord.setChargeStatus(ChargeStatusEnum.CHARGED.getCode());
        chargeRecord.setCustomerCode(related.getCustomerCode());
        chargeRecord.setCustomerName(account.getCustomerName());

        chargeRecord.setGiveBookAfterMoney(account.getGiveMoney());
        chargeRecord.setGiveBookPreMoney(account.getGiveMoney());
//        chargeRecord.setGiveChange();
//        chargeRecord.setGiveDeductionMoney();
        chargeRecord.setInvoiceNo(null);
        chargeRecord.setInvoiceStatus(InvoiceStatusEnum.NOT_OPEN.getCode());
        chargeRecord.setInvoiceType(InvoiceTypeEnum.ELECTRONIC_INVOICE.getCode());
        chargeRecord.setReceiptStatus(ReceiptStatusEnum.NOT_OPEN.getCode());
        chargeRecord.setPayableMoney(recAmt);
//        chargeRecord.setPrechargeDeductionMoney();
//        chargeRecord.setPrechargeMoney();
        chargeRecord.setReceivableMoney(recAmt);
        chargeRecord.setRefundStatus(RefundStatusEnum.NOMAL.getCode());
        ChargeUtils.setNullFieldAsZero(chargeRecord);
        return chargeRecord;

    }

    private ChargeItemRecord convertGasFeeToChargeItem(GasmeterArrearsDetail arrear, ChargeItemEnum itemEnum,
                                                       String itemName, TollItem tollItem){
        ChargeItemRecord chargeItemRecord=new ChargeItemRecord().setMoneyMethod(MoneyMethodEnum.fixed.getCode())
                .setChargeItemSourceCode(itemEnum.getCode())
                .setChargeItemSourceName(itemEnum.getDesc())
                .setChargeItemName(arrear.getReadmeterMonth()+ I18nUtil.getMsg("月 ","M ") + itemName)
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

    private void telentSelector(String acctOrgNo){
        BaseContextHandler.setTenant(null);
        List<LivingExpenses> elist=livingExpensesBizApi.query(LivingExpenses.builder().alipayCode(acctOrgNo).build()).getData();
        if(CollectionUtils.isEmpty(elist)){
            throw BizException.wrap("为找到有效事业机构");
        }
        LivingExpenses livingExpenses=elist.get(0);
        BaseContextHandler.setTenant(livingExpenses.getCompanyCode());
    }

}
