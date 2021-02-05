package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.charges.service.BizOrderService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargePayService;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
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
import com.cdqckj.gmis.charges.api.ChargeBizApi;
import com.cdqckj.gmis.charges.api.ChargeRecordBizApi;
import com.cdqckj.gmis.charges.api.RechargeRecordBizApi;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargePayMethodEnum;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.common.enums.AmountMarkEnum;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.enumeration.SendCardState;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.iot.qc.vo.RechargeVO;
import com.cdqckj.gmis.iot.qc.vo.UpdateBalanceVO;
import com.cdqckj.gmis.iot.qc.vo.ValveControlVO;
import com.cdqckj.gmis.operateparam.FunctionSwitchBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.readmeter.ReadMeterDataIotApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotUpdateDTO;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.utils.DateUtils;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.common.collect.Lists;
import io.seata.common.util.CollectionUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 收费支付封装
 * @author tp
 * @date 2020-09-04
 */
@Service
@Log4j2
public class ChargePayServiceImpl  implements ChargePayService {


    @Autowired
    PayBizApi payBizApi;

    /**
     * 柜台收费加载
     * @return
     */
    public void pay(ChargeResultDTO resultDTO,ChargeDTO infoDTO) {
        ChargeRecord record=resultDTO.getChargeRecord();
        if (ChargePayMethodEnum.WECHATPAY.eq(infoDTO.getChargeRecord().getChargeMethodCode())) {
            wechatMicropay(record,infoDTO,resultDTO);
        }
    }

    private void wechatMicropay(ChargeRecord record,ChargeDTO infoDTO,ChargeResultDTO resultDTO){
        WxPay wxPay=new WxPay();
        if(StringUtils.isBlank(infoDTO.getAuthCode())){
            throw BizException.wrap("未收到扫描二维码");
        }
        wxPay.setAuthCode(infoDTO.getAuthCode());
        wxPay.setBody("柜台业务缴费");
        wxPay.setOrderNumber(record.getChargeNo());
        wxPay.setProductId(record.getChargeNo());
        //传给微信的时间，微信到过期时间后还没有收到客户付款就过期
        LocalDateTime startTime=LocalDateTime.now();
        wxPay.setTimeStart(DateUtils.format(startTime,"yyyyMMddHHmmss"));
        Long expireSecend=30L;
        if(infoDTO.getTestExpireSecend()!=null){
            expireSecend=Long.parseLong(infoDTO.getTestExpireSecend().toString());
        }

        LocalDateTime expireTime=startTime.plusSeconds(expireSecend);
        wxPay.setTimeExpire(DateUtils.format(expireTime,"yyyyMMddHHmmss"));
        wxPay.setPay(record.getActualIncomeMoney());
        if(CollectionUtils.isNotEmpty(infoDTO.getItemList())){
            String collect = infoDTO.getItemList().stream().map(r -> r.getChargeItemName()).collect(Collectors.joining("；"));
            wxPay.setDetail(collect);
        }else{
            wxPay.setDetail("");
        }
        resultDTO.setExpireSecend(expireSecend);
        R<Map<String, String>> r;
        try {
            r = payBizApi.wechatMicropay(wxPay);
        }catch (Exception e){
            throw BizException.wrap("发起支付异常");
        }
        Map<String,String> wxData=r.getData();
        if(wxData==null){
            //微信请求失败，异常回滚全局事务。
            resultDTO.setIsLoopRequest(false);
            resultDTO.setThirdPayStatus("EXCEPTION");
            log.error("支付服务发起微信支付失败:无异常且无参数返回 当前订单数据为：{}",JSONObject.toJSONString(record));
            resultDTO.setPayErrReason("未知异常");
        }
        log.info("微信支付响应数据：{}",JSONObject.toJSONString(wxData));
        Boolean returnCode = "SUCCESS".equals(wxData.getOrDefault("return_code",null));
        if(returnCode){
            Boolean resultCode = "SUCCESS".equals(wxData.getOrDefault("result_code",null));
            if(resultCode){
                //支付成功的，可以直接处理业务，不需要轮询。
                resultDTO.setIsLoopRequest(false);
                resultDTO.setThirdPayStatus("SUCCESS");
            }else{
                String errorCode= wxData.getOrDefault("err_code","");
                //商户系统等待5秒后调用【查询订单API】，查询支付实际交易结果；当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议10秒)重新查询支付结果，直到支付成功或超时(建议30秒)；

                if("SYSTEMERROR".equals(errorCode) || "BANKERROR".equals(errorCode)){
                    log.info("当返回结果为“SYSTEMERROR”时，商户系统可设置间隔时间(建议5秒)重新查询支付结果");
                    resultDTO.setIsLoopRequest(true);
                    resultDTO.setThirdPayStatus("UNKOWN");
                    //这里如果超时？就让前端轮询
                    try {
                        wait(5000);
                    } catch (Exception e) {
                    }
                    try {
                        //查询不能影响当前事务，毕竟可以重新查询
                        wxQuery(record.getChargeNo(), resultDTO);
                    }catch (Exception e){
                        log.error("",e);
                    }
                }else if("USERPAYING".equals(errorCode)){
                    log.info("当返回结果为“USERPAYING”时，商户系统可设置间隔时间(建议10秒)重新查询支付结果");
                    //这里如果超时？就让前端轮询
                    try {
                        wait(3000);
                    }catch (Exception e){}
                    resultDTO.setIsLoopRequest(true);
                    resultDTO.setIsWaiteUserPay(true);
                    try {
                        //查询不能影响当前事务，毕竟可以重新查询
                        wxQuery(record.getChargeNo(), resultDTO);
                    }catch (Exception e){
                        log.error("",e);
                    }
                }else{
                    //支付失败，直接处理业务，不需要轮询
                    resultDTO.setIsLoopRequest(false);
                    resultDTO.setThirdPayStatus("FAIL");
                    resultDTO.setPayErrReason("交易失败【"+errorCode+"】:"+wxData.getOrDefault("err_code_des","未知"));
                    try {
                        //查询不能影响当前事务，毕竟可以重新查询
                        wxQuery(record.getChargeNo(), resultDTO);
                    }catch (Exception e){
                        log.error("",e);
                    }
                }
            }
        }else{
            //微信请求失败，异常回滚全局事务。
            resultDTO.setIsLoopRequest(false);
            resultDTO.setThirdPayStatus("FAIL");
            //return_msg
            resultDTO.setPayErrReason(wxData.getOrDefault("return_msg","未知"));
//            log.error("服务异常：发起微信支付失败,参数校验或者网络异常：{}",JSONObject.toJSONString(record));
//            throw BizException.wrap("服务异常：发起微信支付失败,参数校验或者网络异常");
        }

//        SYSTEMERROR	接口返回错误	支付结果未知	系统超时	请立即调用被扫订单结果查询API，查询当前订单状态，并根据订单的状态决定下一步的操作。
//        PARAM_ERROR	参数错误	支付确认失败	请求参数未按指引进行填写	请根据接口返回的详细信息检查您的程序
//        ORDERPAID	订单已支付	支付确认失败	订单号重复	请确认该订单号是否重复支付，如果是新单，请使用新订单号提交
//        NOAUTH	商户无权限	支付确认失败	商户没有开通被扫支付权限	请开通商户号权限。请联系产品或商务申请
//        AUTHCODEEXPIRE	二维码已过期，请用户在微信上刷新后再试	支付确认失败	用户的条码已经过期	请收银员提示用户，请用户在微信上刷新条码，然后请收银员重新扫码。 直接将错误展示给收银员
//        NOTENOUGH	余额不足	支付确认失败	用户的零钱余额不足	请收银员提示用户更换当前支付的卡，然后请收银员重新扫码。建议：商户系统返回给收银台的提示为“用户余额不足.提示用户换卡支付”
//        NOTSUPORTCARD	不支持卡类型	支付确认失败	用户使用卡种不支持当前支付形式	请用户重新选择卡种 建议：商户系统返回给收银台的提示为“该卡不支持当前支付，提示用户换卡支付或绑新卡支付”
//        ORDERCLOSED	订单已关闭	支付确认失败	该订单已关	商户订单号异常，请重新下单支付
//        ORDERREVERSED	订单已撤销	支付确认失败	当前订单已经被撤销	当前订单状态为“订单已撤销”，请提示用户重新支付
//        BANKERROR	银行系统异常	支付结果未知	银行端超时	请立即调用被扫订单结果查询API，查询当前订单的不同状态，决定下一步的操作。
//        USERPAYING	用户支付中，需要输入密码	支付结果未知	该笔交易因为业务规则要求，需要用户输入支付密码。	等待5秒，然后调用被扫订单结果查询API，查询当前订单的不同状态，决定下一步的操作。
//        AUTH_CODE_ERROR	付款码参数错误	支付确认失败	请求参数未按指引进行填写	每个二维码仅限使用一次，请刷新再试
//        AUTH_CODE_INVALID	付款码检验错误	支付确认失败	收银员扫描的不是微信支付的条码	请扫描微信支付被扫条码/二维码
//        XML_FORMAT_ERROR	XML格式错误	支付确认失败	XML格式错误	请检查XML参数格式是否正确
//        REQUIRE_POST_METHOD	请使用post方法	支付确认失败	未使用post传递参数	请检查请求参数是否通过post方法提交
//        SIGNERROR	签名错误	支付确认失败	参数签名结果不正确	请检查签名参数和方法是否都符合签名算法要求
//        LACK_PARAMS	缺少参数	支付确认失败	缺少必要的请求参数	请检查参数是否齐全
//        NOT_UTF8	编码格式错误	支付确认失败	未使用指定编码格式	请使用UTF-8编码格式
//        BUYER_MISMATCH	支付帐号错误	支付确认失败	暂不支持同一笔订单更换支付方	请确认支付方是否相同
//        APPID_NOT_EXIST	APPID不存在	支付确认失败	参数中缺少APPID	请检查APPID是否正确
//        MCHID_NOT_EXIST	MCHID不存在	支付确认失败	参数中缺少MCHID	请检查MCHID是否正确
//        OUT_TRADE_NO_USED	商户订单号重复	支付确认失败	同一笔交易不能多次提交	请核实商户订单号是否重复提交
//        APPID_MCHID_NOT_MATCH	appid和mch_id不匹配	支付确认失败	appid和mch_id不匹配	请确认appid和mch_id是否匹配
//        INVALID_REQUEST	无效请求	支付确认失败	商户系统异常导致，商户权限异常、重复请求支付、证书错误、频率限制等	请确认商户系统是否正常，是否具有相应支付权限，确认证书是否正确，控制频率
//        TRADE_ERROR	交易错误	支付确认失败	业务错误导致交易失败、用户账号异常、风控、规则限制等	请确认帐号是否存在异常
        //SYSTEMERROR
    }
    public ChargeCompleteDTO wxQueryByWeb(WxPay wxPay) {
        ChargeCompleteDTO chargeCompleteDTO = new ChargeCompleteDTO();
        log.info("订单查询{}", JSONObject.toJSONString(wxPay));
        R<Map<String, String>> r = payBizApi.orderquery(wxPay);
        chargeCompleteDTO.setWxQueryData(r.getData());
        log.info("订单查询结果{}", JSONObject.toJSONString(r.getData()));
        String trateState = r.getData().getOrDefault("trade_state",null);
        if (trateState != null) {
            if ("SUCCESS".equals(trateState)) {
                chargeCompleteDTO.setChargeStatus(true);
                chargeCompleteDTO.setChargeNo(wxPay.getOrderNumber());
                return chargeCompleteDTO;
            } else if ("USERPAYING".equals(trateState) || "ACCEPT".equals(trateState)) {

            }else{
                chargeCompleteDTO.setChargeStatus(false);
                chargeCompleteDTO.setRemark("交易失败【"+trateState+"】:"+r.getData().getOrDefault("err_code_des",""));
                chargeCompleteDTO.setChargeNo(wxPay.getOrderNumber());
                return chargeCompleteDTO;
            }
        }
        return chargeCompleteDTO;
    }
    public ChargeCompleteDTO wxQueryByLoad(String chargeNo) {
        ChargeCompleteDTO chargeCompleteDTO = new ChargeCompleteDTO();
        WxPay wxPay = new WxPay();
        wxPay.setOrderNumber(chargeNo);
        log.info("订单查询{}", JSONObject.toJSONString(wxPay));
        R<Map<String, String>> r = payBizApi.orderquery(wxPay);
        if (r.getData() == null) {
            log.info("1.存在未完成的异常支付订单");
            throw BizException.wrap("存在未完成的异常支付订单");
        }
        log.info("订单查询结果{}", JSONObject.toJSONString(r.getData()));
        String trateState = r.getData().getOrDefault("trade_state",null);
        if (trateState != null) {
            if ("SUCCESS".equals(trateState)) {
                chargeCompleteDTO.setChargeStatus(true);
                chargeCompleteDTO.setChargeNo(chargeNo);
            } else if ("USERPAYING".equals(trateState) || "ACCEPT".equals(trateState)) {
                throw BizException.wrap("用户支付中，请稍后重试");
            } else {
                chargeCompleteDTO.setChargeStatus(false);
                chargeCompleteDTO.setRemark("交易失败【"+trateState+"】:"+r.getData().getOrDefault("err_code_des",""));
                chargeCompleteDTO.setChargeNo(chargeNo);
            }
        }else{
            log.info("2.存在未完成的异常支付订单");
            throw BizException.wrap("存在未完成的异常支付订单");
        }
        return chargeCompleteDTO;
    }
    private void wxQuery(String chargeNo,ChargeResultDTO resultDTO){
        try {
            WxPay wxPay = new WxPay();
            wxPay.setOrderNumber(chargeNo);
            log.info("订单查询{}", JSONObject.toJSONString(wxPay));
            R<Map<String, String>> r = payBizApi.orderquery(wxPay);
            if (r.getData() == null) {
                log.info("微信查询接口未查询到数据");
                return;
            }
            log.info("订单查询结果{}", JSONObject.toJSONString(r.getData()));
            String trateState = r.getData().getOrDefault("trade_state",null);
            if (trateState != null) {
                if ("SUCCESS".equals(trateState)) {
                    resultDTO.setIsLoopRequest(false);
                    resultDTO.setThirdPayStatus("SUCCESS");
                } else if ("USERPAYING".equals(trateState) || "ACCEPT".equals(trateState)) {
                    resultDTO.setIsLoopRequest(true);
                    resultDTO.setIsWaiteUserPay(true);
                    resultDTO.setThirdPayStatus("UNKOWN");
                } else {
                    resultDTO.setIsLoopRequest(false);
                    resultDTO.setPayErrReason("交易失败【"+trateState+"】:"+r.getData().getOrDefault("err_code_des","未知"));
                    resultDTO.setThirdPayStatus("FAIL");
                }
//            SUCCESS--支付成功
//            REFUND--转入退款
//            NOTPAY--未支付
//            CLOSED--已关闭
//            REVOKED--已撤销(刷卡支付)
//            USERPAYING--用户支付中
//            PAYERROR--支付失败(其他原因，如银行返回失败)
//            ACCEPT--已接收，等待扣款
            }
        }catch (Exception e){
            log.error("支付查询订单状态异常：",e);
        }

    }


}
