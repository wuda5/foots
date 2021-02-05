package com.cdqckj.gmis.service.impl;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.charges.service.impl.ChargeServiceImpl;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.charges.api.ChargeBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.CustomerAccountBizApi;
import com.cdqckj.gmis.charges.api.CustomerSceneChargeOrderBizApi;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.ConversionType;
import com.cdqckj.gmis.constant.PayMessageConstant;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.entity.dto.CusFeesPayDTO;
import com.cdqckj.gmis.entity.vo.CusFeesPayVO;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.service.CusFeesService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户费用相关实现类
 * @auther hc
 * @date 2020/10/19
 */
@Log4j2
@Service("cusFeesService")
public class CusFeesServiceImpl implements CusFeesService {

    @Autowired
    private CustomerAccountBizApi customerAccountBizApi;

    @Autowired
    private ChargeBizApi chargeBizApi;

    @Autowired
    private PayBizApi payBizApi;

    @Autowired
    private CalculateApi calculateApi;

    @Autowired
    private CustomerSceneChargeOrderBizApi customerSceneChargeOrderBizApi;

    @Autowired
    private ChargeRecordBizApi chargeRecordBizApi;

    @Autowired
    private ChargeServiceImpl chargeService;

    /**
     * 燃气缴费
     * @param cusFeesPayDTO
     * @return
     */
    @Override
    @GlobalTransactional
    public R<CusFeesPayVO> generalCharges(CusFeesPayDTO cusFeesPayDTO, GasMeter gasMeter) {
        //返回数据集
        CusFeesPayVO resultData = new CusFeesPayVO();
        //加载缴费数据
        R<ChargeLoadDTO> chargeDataR = this.chargeLoad(cusFeesPayDTO.getCustomerCode(),cusFeesPayDTO.getGasCode());
        if(chargeDataR.getIsError() || null == chargeDataR.getData()){
            return R.fail("加载缴费数据失败");
        }
        ChargeLoadDTO chargeData = chargeDataR.getData();
        //验证应收金额是否正确 应缴 = 预存+欠费+充值金额/+(充气量换算成金额)
         BigDecimal tempPay;
         //换算金额
         BigDecimal conversionMoney = BigDecimal.ZERO;
         //实收金额
         BigDecimal actualMoney;

         //充气量，换算为金额
        if(cusFeesPayDTO.getRechargeGas().compareTo(BigDecimal.ZERO)>0){
            ConversionVO conversionVO = new ConversionVO();
            conversionVO.setConversionType(ConversionType.GAS);
            conversionVO.setConversionValue(cusFeesPayDTO.getRechargeGas());
            conversionVO.setGasMeterCode(cusFeesPayDTO.getGasCode());
            conversionVO.setUseGasTypeId(gasMeter.getUseGasTypeId());

            R<BigDecimal> decimalR = this.conversion(conversionVO);

            conversionMoney = decimalR.getData();
        }

        tempPay = chargeData.getTotalMoney()
                //预存
                .add(cusFeesPayDTO.getPrepay())
                //充值金额
                .add(cusFeesPayDTO.getRechargeMoney())
                //换算金额
                .add(conversionMoney);
        //减去账户余额
        //是否使用账户余额
        if(cusFeesPayDTO.getUseBalance()){

            actualMoney =  tempPay.subtract(chargeData.getAccountMoney());

            actualMoney = actualMoney.compareTo(BigDecimal.ZERO) == -1 ? BigDecimal.ZERO : tempPay;
        }else{
            actualMoney = tempPay;
        }
        if(tempPay.compareTo(cusFeesPayDTO.getPayable()) != 0){
            return R.fail(10032,"应交金额不正确："+tempPay);
        }

        /** 组装存帐数据 **/
        ChargeDTO saveData  = new ChargeDTO();
        saveData.setItemList(chargeData.getItemList());
        saveData.setCustomerCode(cusFeesPayDTO.getCustomerCode());
        saveData.setGasMeterCode(cusFeesPayDTO.getGasCode());
        //生成收费单
        saveData.setChargeRecord(dealChargeRecord(cusFeesPayDTO,chargeData,conversionMoney,actualMoney));
        R<ChargeResultDTO> chargeResult = chargeBizApi.charge(saveData);
        if(chargeResult.getIsError() || null == chargeResult){
            return R.fail("缴费失败");
        }

        ChargeRecord chargeRecord = chargeResult.getData().getChargeRecord();
        /**调起支付链接**/
        if(cusFeesPayDTO.getPayable().compareTo(BigDecimal.ZERO)>0){
            resultData.setPayFlag(true);
            //生成三方支付数据
            resultData.setPayData(this.buildPayData(chargeRecord,cusFeesPayDTO));

            log.info("燃气缴费调起三方支付："+resultData.getPayData().toString());
        }else{
            resultData.setPayFlag(false);

//            ChargeCompleteDTO infoDTO = new ChargeCompleteDTO();
//            infoDTO.setChargeNo(chargeRecord.getChargeNo());
//            infoDTO.setChargeStatus(true);
//            R<ChargeResultDTO> chargeResultR=chargeBizApi.chargeComplete(infoDTO);
//            if(chargeResultR.getIsError()){
//                return R.fail("账户扣费失败");
//            }
            ChargeCompleteDTO completeDTO = new ChargeCompleteDTO();
            completeDTO.setChargeNo(chargeRecord.getChargeNo());
            completeDTO.setChargeStatus(true);
           chargeService.chargeComplete(completeDTO);
//            if(booleanR.getIsError()||!booleanR.getData()){
//                return R.fail("账户扣费失败");
//            }
        }
        resultData.setPayMoney(cusFeesPayDTO.getPayable());
        resultData.setPayType(cusFeesPayDTO.getPayType());

        return R.success(resultData);
    }

    /**
     * 生成支付链接
     * @auther hc
     * @param chargeRecord 支付数据
     * @param payDTO 支付主题
     * @return
     */
    private Map<String, String> buildPayData(ChargeRecord chargeRecord, CusFeesPayDTO payDTO) {

        Map<String, String>  payData = new HashMap<>();

        /** 阿里支付 **/
        if(ChargePayMethodEnum.ALIPAY.getCode().equals(payDTO.getPayType())){
            // TODO

            /** 微信支付 **/
        }else if(ChargePayMethodEnum.WECHATPAY.getCode().equals(payDTO.getPayType())){
            WxPay wxPaySaveDTO = new WxPay();
            wxPaySaveDTO.setBody(PayMessageConstant.payBody);
            wxPaySaveDTO.setDetail(PayMessageConstant.payDetail);
            wxPaySaveDTO.setOrderNumber(chargeRecord.getChargeNo());
            wxPaySaveDTO.setPay(chargeRecord.getReceivableMoney().setScale(2,BigDecimal.ROUND_HALF_UP));
            wxPaySaveDTO.setPayOpenid(payDTO.getThree_openId());

            R<Map<String, String>> mapR = payBizApi.wechatApplets(wxPaySaveDTO);
            payData = mapR.getData();
            payData.put("orderNumber",wxPaySaveDTO.getOrderNumber());
        }

        return payData;
    }

    @Override
    public R<ChargeLoadDTO> chargeLoad(String cusCode, String gascode) {

        return chargeBizApi.chargeLoad(ChargeLoadReqDTO.builder().gasMeterCode(gascode).customerCode(cusCode).build());
    }

    @Override
    public R<Map<String, String>> orderquery(String orderNumber) {

        WxPay wxPay = new WxPay();
        wxPay.setOrderNumber(orderNumber);
        return payBizApi.orderquery(wxPay);
    }

    @Override
    public R<BigDecimal> conversion(ConversionVO conversionVO) {

        return calculateApi.conversion(conversionVO);
    }

    /**
     * 生成缴费数据(普表)
     * @auther hc
     * @date 2020/10/28
     * @param chargeData 欠费单
     * @param payDTO 支付请求参数
     * @param actualMoney 实收金额
     * @return
     */
    private ChargeRecord dealChargeRecord(CusFeesPayDTO payDTO,ChargeLoadDTO chargeData,BigDecimal conversionMoney,BigDecimal actualMoney){

        ChargeRecord record = new ChargeRecord();
        ChargeUtils.setNullFieldAsZero(chargeData);
        record.setCustomerCode(payDTO.getCustomerCode());
        record.setGasMeterCode(payDTO.getGasCode());
        //收费渠道
        record.setChargeChannel(payDTO.getChargeChannel());
        //收费方式
        record.setChargeMethodCode(payDTO.getPayType());
        record.setChargeMethodName(ChargePayMethodEnum.get(payDTO.getPayType()).getDesc());


        //收费金额 -- 合计金额
        record.setChargeMoney(chargeData.getTotalMoney());

        //预存金额 -- 输入金额（普表收费）,物联网中心计费表
        record.setPrechargeMoney(payDTO.getPrepay());
        //应收金额 -- 计算的应缴金额
        record.setReceivableMoney(payDTO.getPayable());
        //实收金额 -- 计算的应缴金额
        record.setActualIncomeMoney(actualMoney);
        //充值金额
        record.setRechargeMoney(payDTO.getRechargeMoney());
        //充值气量
        record.setRechargeGas(payDTO.getRechargeGas());

        BigDecimal accountMoney=chargeData.getAccountMoney();
        BigDecimal pay = chargeData.getTotalMoney()
                .add(payDTO.getPrepay())
                .add(payDTO.getRechargeMoney())
                .add(conversionMoney);
        record.setPayableMoney(chargeData.getTotalMoney());
        //预存抵扣：-- 现在只有充值场景有抵扣，从账户扣除多少钱  账户>充值+欠费 ? 应缴=0，抵扣=充值+欠费 ：应缴=充值+欠费-账户，抵扣=账户全额
        if(payDTO.getUseBalance()) {
            if(accountMoney.compareTo(pay)>=0){
                record.setPrechargeDeductionMoney(pay);
            }else{
                record.setPrechargeDeductionMoney(accountMoney);
            }
        }
        return  record;
    }
}
