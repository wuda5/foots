package com.cdqckj.gmis.pay.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.ChargeRecordMapper;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeRefundResDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.pay.annotation.OrderNoValid;
import com.cdqckj.gmis.pay.annotation.WxBillIndex;
import com.cdqckj.gmis.pay.dao.WxBillMapper;
import com.cdqckj.gmis.pay.dao.WxPayMapper;
import com.cdqckj.gmis.pay.dao.WxRefundMapper;
import com.cdqckj.gmis.pay.dto.WxBillPageDTO;
import com.cdqckj.gmis.pay.dto.WxPayDTO;
import com.cdqckj.gmis.pay.dto.WxPayPageDTO;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import com.cdqckj.gmis.pay.enumeration.BillReturnCodeEnum;
import com.cdqckj.gmis.pay.enumeration.ContrastStateEnum;
import com.cdqckj.gmis.pay.enumeration.RefundStateEnum;
import com.cdqckj.gmis.pay.enumeration.TradeStateEnum;
import com.cdqckj.gmis.pay.service.WxPayService;
import com.cdqckj.gmis.pay.strategy.PayContext;
import com.cdqckj.gmis.pay.util.BillUtil;
import com.cdqckj.gmis.pay.util.RefundDecryptUtil;
import com.cdqckj.gmis.pay.util.WxPayAppConfig;
import com.cdqckj.gmis.systemparam.dao.PayInfoMapper;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.cdqckj.gmis.systemparam.enumeration.InterfaceModeEnum;
import com.cdqckj.gmis.utils.DateUtils;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.jgroups.util.Util.byteArrayToHexString;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class WxPayServiceImpl extends SuperServiceImpl<WxPayMapper, WxPay> implements WxPayService {

    private static final String SANDBOX_ALISERVERURL = "https://openapi.alipaydev.com/gateway.do";
    private static final String ALISERVERURL = "https://openapi.alipay.com/gateway.do";
    private static final String FORMAT = "json";
    private static final String CHARSET = "utf-8";
    private static final String[] WEIXINWAY = {"SCANCODE","MICROPAY"};
    private final String[] PAYWAY = {"WECHAT","ALIPAY"};
    private final List<String> SUCCESS_CODE = Arrays.asList("SUCCESS", "00");
    private final List<String> FAIL_CODE = Arrays.asList("FAIL", "01");

    /**
     * 用户微信中的条码或者二维码信息开头数字集合
     */
    private final List<Integer> WEIXIN_AUTH_CODE = Arrays.asList(10, 11, 12, 13, 14, 15);
    /**
     * 用户支付宝中的条码或者二维码信息开头数字集合
     */
    private final List<Integer> ALIPAY_AUTH_CODE = Arrays.asList(25, 26, 27, 28, 29, 30);

    @Autowired
    public PayInfoMapper payInfoMapper;
    @Autowired
    public WxRefundMapper refundMapper;
    @Autowired
    public WxBillMapper billMapper;
    @Autowired
    private WxPayAppConfig config;
    @Autowired
    private PayContext payContext;
    @Autowired
    private ChargeRecordMapper chargeRecordMapper;

    private WXPay wxpay = null;

    @Autowired
    public void getWxpay(){
        wxpay = new WXPay(config);
    }

    @OrderNoValid
    @Override
    public R<Map<String, String>> wechatScanCode(@Valid WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        Map<String, String> resultMap = payContext.wechatScanCode(payInfo, wxPaySaveDTO, request);
        return saveWxPay(wxPaySaveDTO, payInfo, resultMap);
    }

    @OrderNoValid
    @Override
    public R<Map<String, String>> wechatAppPay(@Valid WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        //成都支付通不支持app，暂时手动设置为本地支付
        payInfo.setInterfaceMode(InterfaceModeEnum.LOCAL_INTERFACE.getStatus());
        Map<String, String> resultMap = payContext.wechatScanCode(payInfo, wxPaySaveDTO, request);
        return saveWxPay(wxPaySaveDTO, payInfo, resultMap);
    }

    @OrderNoValid
    @Override
    public R<Map<String, String>> wechatMicropay(@Valid WxPay wxPaySaveDTO, HttpServletRequest request) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        Map<String, String> resultMap = payContext.wechatMicropay(payInfo, wxPaySaveDTO, request);
        return saveWxPay(wxPaySaveDTO, payInfo, resultMap);
    }

    /**
     * 微信退款申请
     * @param wxRefund
     * @return
     * @throws Exception
     */
    @Override
    public R<Map<String, String>> weixinRefund(WxRefund wxRefund) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        String orderNo = wxRefund.getOrderNumber();
        WxPay wxPayInfo = getWxPayByOrderNumber(orderNo);
        Map<String, String> resultMap = payContext.weixinRefund(payInfo, wxPayInfo, wxRefund);
        return saveRefund(wxRefund, wxPayInfo, resultMap);
    }

    /**
     * 根据订单编号获取订单
     * @param orderNo
     * @return
     */
    public WxPay getWxPayByOrderNumber(String orderNo) {
        WxPay wxPay = baseMapper.selectOne(Wraps.<WxPay>lbQ().eq(WxPay::getOrderNumber,orderNo));
        if(null==wxPay){
            throw new RuntimeException("订单不存在");
        }else if(!wxPay.getPayState().equals(TradeStateEnum.SUCCESS.getCode())){
            throw new RuntimeException("订单当前状态："+TradeStateEnum.getDesc(wxPay.getPayState())+",无法申请退款");
        }
        return baseMapper.selectOne(Wraps.<WxPay>lbQ().eq(WxPay::getOrderNumber,orderNo));
    }

    /**
     * 保存微信订单
     * @param wxPaySaveDTO
     * @param payInfo
     * @param resultMap
     * @return
     */
    public R<Map<String, String>> saveWxPay(WxPay wxPaySaveDTO, PayInfo payInfo, Map<String, String> resultMap) {
        if (resultMap != null) {
            String returnCode = InterfaceModeEnum.getType(payInfo.getInterfaceMode()).equals(InterfaceModeEnum.LOCAL_INTERFACE) ?
                    resultMap.get("return_code") : resultMap.get("result_code");
            if (null != returnCode) {
                if (SUCCESS_CODE.contains(returnCode)) {
                    save(wxPaySaveDTO);
                }
            }
            return R.success(resultMap);
        }
        return R.fail("下单失败");
    }

    /**
     * 保存退款申请单
     * @param wxRefund
     * @param wxPay
     * @param resultMap
     * @return
     */
    public R<Map<String, String>> saveRefund(WxRefund wxRefund, WxPay wxPay, Map<String, String> resultMap) {
        if (resultMap != null) {
            String returnCode = resultMap.get("result_code");
            if (null != returnCode && SUCCESS_CODE.contains(returnCode)) {
                wxPay.setId(null);
                BeanUtil.copyProperties(wxPay, wxRefund);
                if(resultMap.containsKey("refund_id")){
                    wxRefund.setRefundId(resultMap.get("refund_id"));
                }
                if(resultMap.containsKey("settlement_refund_fee")){
                    wxRefund.setSettlementRefundFee(new BigDecimal(resultMap.get("settlement_refund_fee")).divide(new BigDecimal("100")));
                }
                refundMapper.insert(wxRefund);
            }
            return R.success(resultMap);
        }
        return R.fail("申请退款失败");
    }

    @Override
    public R<String> alipayScanCode(WxPayDTO wxPayDTO) throws Exception {
        // 生成支付宝支付二维码链接
        WxPaySaveDTO wxPaySaveDTO = wxPayDTO.getWxPaySaveDTO();
        PayInfo payInfo = wxPayDTO.getPayInfo();
        String orderNumner = wxPaySaveDTO.getOrderNumber();//订单号
        String aliUrl = wxPaySaveDTO.getIsSandbox()==1?SANDBOX_ALISERVERURL:ALISERVERURL;
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, payInfo.getAppId(),
                payInfo.getMerchantPrivateKey(), FORMAT, CHARSET, payInfo.getAlipayPublicKey(), "RSA2");//
        AlipayTradePrecreateModel model=new AlipayTradePrecreateModel();
        model.setOutTradeNo(orderNumner);                   //商户订单号
        model.setSubject(wxPaySaveDTO.getOrderTitle());                    //订单标题
        model.setTotalAmount(wxPaySaveDTO.getPay().toString());                  //订单总金额
        AlipayTradePrecreateRequest request=new AlipayTradePrecreateRequest();
        request.setBizModel(model);
        AlipayTradePrecreateResponse alipayTradePrecreateResponse=alipayClient.execute(request);
        String content=alipayTradePrecreateResponse.getQrCode();
        if(content!=null){
            return R.success(content);
        }else{
            return R.fail(alipayTradePrecreateResponse.getSubMsg());
        }
    }

    @Override
    public R<IPage<WxBill>> queryWeixinBill(@RequestBody PageParams<WxBillPageDTO> wxPay)throws Exception {
        //LbqWrapper<WxBill> wraps = Wraps.<WxBill>lbQ().like(WxBill::getTransactionTime,wxPay.getModel().getBillDate());
        WxBillPageDTO model = wxPay.getModel();
        LbqWrapper<WxBill> wraps = Wraps.<WxBill>lbQ().orderByDesc(WxBill::getTransactionTime);
        if(null!=model.getContrastState()){
            wraps.eq(WxBill::getContrastState,model.getContrastState());
        }
        if(null!=model.getStartTime()){
            wraps.ge(WxBill::getTransactionDate,model.getStartTime());
        }
        if(null!=model.getEndTime()){
            wraps.le(WxBill::getTransactionDate,model.getEndTime());
        }
        if(StringUtils.isNotBlank(model.getCustomerName())){
            wraps.like(WxBill::getCustomerName,model.getCustomerName());
        }
        if(StringUtils.isNotBlank(model.getOrderNumber())){
            wraps.like(WxBill::getOrderNumber,model.getOrderNumber());
        }
        return R.success(billMapper.selectPage(wxPay.getPage(),wraps));
    }

    @Override
    public R<Boolean> queryAndSaveBill(@RequestBody String billDate) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        Map<String, String> resultMap = payContext.downloadWeixinBill(payInfo, billDate.replaceAll("-",""));
        String str= resultMap.get("data");
        String returnCode = resultMap.get("return_code");
        if(BillReturnCodeEnum.SUCCESS.name().equals(returnCode)){
            String newStr = str.replaceAll(",", "_"); // 去空格
            String[] tempStr = newStr.split("`"); // 数据分组
            String[] t = tempStr[0].split("_");// 分组标题
            int k = 1; // 纪录数组下标
            int j = tempStr.length / t.length; // 计算循环次数
            List<List<String>> list = new ArrayList<>(10);
            for (int i = 0; i < j; i++) {
                List<String> dataList = new ArrayList<>();
                for (int l = 0; l < t.length; l++) {
                    String repickStr = null;
                    //如果是最后列且是最后一行数据时，去除数据里的汉字
                    if((i == j - 1) && (l == t.length -1)){
                        String reg = "[\u4e00-\u9fa5]";//汉字的正则表达式
                        Pattern pat = Pattern.compile(reg);
                        Matcher mat=pat.matcher(tempStr[l + k]);
                        repickStr = mat.replaceAll("").replaceAll("_","");
                        //System.out.println(t[l] + ":" + repickStr);
                    }else{
                        //System.out.println(t[l] + ":" + tempStr[l + k].replaceAll("_",""));
                        repickStr = tempStr[l + k].replaceAll("_","");
                    }
                    dataList.add(repickStr);
                }
                list.add(dataList);
                k = k + t.length;
            }
            List<WxBill> billList = getBill(list);
            List<WxBill> saveList = new ArrayList<>();
            Map<String,List<WxBill>> billMmap = billList.stream().collect(Collectors.groupingBy(WxBill::getOrderNumber));
            List<WxPay> payList = baseMapper.selectList(Wraps.<WxPay>lbQ().like(WxPay::getCreateTime,billDate));
            if(payList.size()>0){
                List<String> orderList = payList.stream().map(WxPay::getOrderNumber).collect(Collectors.toList());
                List<ChargeRecord> chargeRecordList = chargeRecordMapper.selectList(Wraps.<ChargeRecord>lbQ().in(ChargeRecord::getChargeNo,orderList));
                Map<String,ChargeRecord> chargeMap = chargeRecordList.stream().collect(Collectors.toMap(ChargeRecord::getChargeNo, Function.identity()));
                for(Map.Entry<String,List<WxBill>> m:billMmap.entrySet()){
                    List<WxBill> billList1 = m.getValue();
                    String orderNum = m.getKey();
                    ChargeRecord record = chargeMap.get(orderNum);
                    WxBill bill = null;
                    LocalDate date = DateUtils.StringToDate(billList1.get(0).getTransactionTime().substring(0,10));
                    if(null!=record){
                        String chargeStatus = record.getChargeStatus();
                        ChargeStatusEnum chargeStatusEnum = ChargeStatusEnum.get(chargeStatus);
                        switch (chargeStatusEnum){
                            case UNCHARGE:
                                billList1 = billList1.stream().filter(b -> b.getTradeState().equals(TradeStateEnum.NOTPAY.getCode())).collect(Collectors.toList());
                                break;
                            case CHARGED:
                                billList1 = billList1.stream().filter(b -> b.getTradeState().equals(TradeStateEnum.SUCCESS.getCode())).collect(Collectors.toList());
                                break;
                            case CHARGE_FAILURE:
                                billList1 = billList1.stream().filter(b -> b.getTradeState().equals(TradeStateEnum.PAYERROR.getCode())).collect(Collectors.toList());
                                break;
                            default:
                                billList1.clear();
                                break;
                        }
                        bill = billList1.size()==0? null:billList1.get(0);
                        if(null!=bill){
                            bill.setTransactionDate(date);
                            BigDecimal actPay = record.getActualIncomeMoney();
                            BigDecimal wxPay = bill.getPay();
                            if(actPay.compareTo(wxPay)!=0){
                                bill.setContrastState(ContrastStateEnum.AMOUNT_DISCREPANCY.getCode());
                                bill.setContrastRemark(ContrastStateEnum.AMOUNT_DISCREPANCY.getDesc());
                            }else{
                                bill.setContrastState(ContrastStateEnum.NORMAL.getCode());
                            }
                            bill.setOrderPay(actPay);
                            bill.setCustomerCode(record.getCustomerCode());
                            bill.setCustomerName(record.getCustomerName());
                            chargeMap.remove(orderNum);
                            saveList.add(bill);
                        }
                    }else{
                        List<Integer> statsList = new ArrayList<>();
                        billList1.forEach(e ->{
                            if(!statsList.contains(e.getTradeState())){
                                e.setTransactionDate(date);
                                e.setContrastState(ContrastStateEnum.SYS_NON_EXISTENT.getCode());
                                e.setContrastRemark(ContrastStateEnum.SYS_NON_EXISTENT.getDesc());
                                statsList.add(e.getTradeState());
                                saveList.add(e);
                            }
                        });
                    }
                }
                if(chargeMap.size()>0){
                    for(Map.Entry<String,ChargeRecord> m:chargeMap.entrySet()){
                        ChargeRecord chargeRecord = m.getValue();
                        WxBill bill = new WxBill();
                        bill.setContrastState(ContrastStateEnum.WX_NON_EXISTENT.getCode());
                        bill.setContrastRemark(ContrastStateEnum.WX_NON_EXISTENT.getDesc());
                        bill.setOrderPay(chargeRecord.getActualIncomeMoney());
                        bill.setCustomerCode(chargeRecord.getCustomerCode());
                        bill.setCustomerName(chargeRecord.getCustomerName());
                        saveList.add(bill);
                    }
                }
                //billList = new ArrayList<WxBill>(billMmap.values());
                LbqWrapper<WxBill> wraps = Wraps.<WxBill>lbQ().like(WxBill::getTransactionTime,billDate);
                List<WxBill> existList = billMapper.selectList(wraps);
                int count = existList.size();
                int newCount = billMmap.values().size();
                if(count!=newCount){
                    if(count>0){
                        billMapper.deleteBatchIds(existList);
                    }
                    if(newCount>0){
                        billMapper.insertBatchSomeColumn(saveList);
                    }
                }
            }
            return R.success();
        }
        return R.success(false,resultMap.get("return_msg"));
    }

    public List<WxBill> getBill(List<List<String>> dataList) {
        try {
            List<WxBill> billList = new ArrayList<>(30);
            Field[] fields = WxBill.class.getDeclaredFields();
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            dataList.forEach(list ->{
                WxBill bill = new WxBill();
                for (Field field:fields) {
                    if (field.isAnnotationPresent(WxBillIndex.class)) {
                        field.setAccessible(true);
                        try {
                            WxBillIndex myAnnotation = field.getAnnotation(WxBillIndex.class);
                            int value = myAnnotation.index();
                            String type = field.getGenericType().toString();
                            String val = (String)list.get(value);
                            switch (type){
                                case "class java.lang.Integer":
                                    Integer vale = null;
                                    if(!val.isEmpty()){
                                        if(pattern.matcher(val).matches()){
                                            vale = Integer.valueOf(val);
                                        }else{
                                            vale = BillUtil.getType(val);
                                        }
                                        field.set(bill, vale);
                                    }
                                    break;
                                case "class java.time.LocalDateTime":
                                    if(!val.isEmpty()){
                                        field.set(bill, DateUtils.getStartTime(val));
                                    }
                                    break;
                                case "class java.math.BigDecimal":
                                    val = val.replace("%","");
                                    field.set(bill, new BigDecimal(val));
                                    break;
                                case "class java.lang.String":
                                    field.set(bill, val);
                                    break;
                                default:
                                    break;
                            }
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
                billList.add(bill);
            });
            return billList;
        } catch (Exception e) {
            e.printStackTrace();
        };
        return null;
    }

    @Override
    public R<String> alipayRefund(WxPayDTO wxPayDTO) throws AlipayApiException {
        WxPaySaveDTO wxPaySaveDTO = wxPayDTO.getWxPaySaveDTO();
        PayInfo payInfo = wxPayDTO.getPayInfo();
        String aliUrl = wxPaySaveDTO.getIsSandbox()==1?SANDBOX_ALISERVERURL:ALISERVERURL;
        AlipayClient alipayClient = new DefaultAlipayClient(aliUrl, payInfo.getAppId(),
                payInfo.getMerchantPrivateKey(), FORMAT, CHARSET, payInfo.getAlipayPublicKey(), "RSA2");//
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("trade_no","2020040222001425041434036943");
        map.put("refund_amount",0.01);
        String param = JSON.toJSONString(map);
        request.setBizContent(param);
        AlipayTradeRefundResponse response = alipayClient.execute(request);
        String fundChange = response.getFundChange();//    本次退款是否发生了资金变化
        System.out.println("本次退款是否发生了资金变化:"+fundChange);
        Date gmtRefundPay = response.getGmtRefundPay();  //退款时间
        System.out.println("退款时间："+gmtRefundPay);
        return R.success(fundChange);
    }

    @Override
    public boolean check(PayInfo payInfo,Map<String,String[]> requestParams) throws Exception{  //对return、notify参数进行验签
        Map<String,String> params = new HashMap<>();

        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }

            params.put(name, valueStr);
            System.out.println(name+" ==> "+valueStr);
        }

        return AlipaySignature.rsaCheckV1(params, payInfo.getAlipayPublicKey(),
                CHARSET, "RSA2"); //调用SDK验证签名
    }

    @Override
    public R<Map<String, String>> refundQuery(String refundNumber) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        return R.success(payContext.refundQuery(payInfo, refundNumber));
    }

    @Override
    public R<ChargeCompleteDTO> weixinNotify(Map<String, String> data) throws Exception {
        ChargeCompleteDTO dto = new ChargeCompleteDTO();
        Map<String,String> return_data = new HashMap<String,String>();
        boolean bool = isSignatureValid(data, config.getKey());
        dto.setChargeStatus(bool);
        if (!bool) {
            // 支付失败
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "签名失败");
            dto.setReturnXmlData(WXPayUtil.mapToXml(return_data));
            dto.setRemark("付款失败");
            return R.success(dto);
        } else {
            System.out.println("===============付款成功==============");
            return_data.put("return_code", "SUCCESS");
            return_data.put("return_msg", "OK");
            dto.setReturnXmlData(WXPayUtil.mapToXml(return_data));
            dto.setChargeNo(data.get("out_trade_no"));
            dto.setRemark("付款成功");
            LbqWrapper <WxPay> wrapper=new LbqWrapper<>();
            wrapper.eq(WxPay::getOrderNumber,data.get("out_trade_no"));
            WxPay wxPay = baseMapper.selectOne(wrapper);
            wxPay.setTransactionId(data.get("transaction_id"));
            if(data.containsKey("trade_state")){
                wxPay.setPayState(TradeStateEnum.match(data.get("trade_state")).getCode());
            }
            updateById(wxPay);
            //租户设置
            dto.setCode(BaseContextHandler.getTenant());
            return R.success(dto);
        }
    }

    @Override
    public R<RefundCompleteDTO> refundNotify(Map<String, String> data) throws Exception {
        log.info("===============退费回调成功==============");
        RefundCompleteDTO dto = new RefundCompleteDTO();
        log.info("加密字符串:"+data.get("req_info"));
        String str = RefundDecryptUtil.getRefundDecrypt(data.get("req_info"), config.getKey());
        Map<String,String> reqInfo = WXPayUtil.xmlToMap(str);
        dto.setReturnXmlData(data);
        dto.setRefundNo(reqInfo.get("out_refund_no"));

        if(reqInfo.containsKey("trade_state")){
            LbqWrapper <WxRefund> refundWrapper=new LbqWrapper<>();
            refundWrapper.eq(WxRefund::getRefundId,reqInfo.get("refund_id"));
            WxRefund refund = refundMapper.selectOne(refundWrapper);
            refund.setRefundStatus(TradeStateEnum.match(reqInfo.get("trade_state")).getCode());
            refundMapper.updateById(refund);
            LbqWrapper <WxPay> wrapper=new LbqWrapper<>();
            wrapper.eq(WxPay::getOrderNumber,reqInfo.get("out_trade_no"));
            WxPay wxPay = baseMapper.selectOne(wrapper);
            dto.setChargeNo(wxPay.getOrderNumber());
            if(reqInfo.get("trade_state").equals(RefundStateEnum.SUCCESS.name())){
                wxPay.setPayState(TradeStateEnum.REFUND.getCode());
                updateById(wxPay);
                log.info("微信支付退款成功");
                dto.setRefundStatus(true);
            }else{
                dto.setRefundStatus(false);
                dto.setRefundRemark("退款异常："+data.get("return_msg"));
            }
        }else{
            dto.setRefundStatus(false);
            dto.setRefundRemark("退款异常："+data.get("return_msg"));
        }
        //租户设置
        dto.setCode(BaseContextHandler.getTenant());
        return R.success(dto);
    }

    @Override
    public R<Map<String, String>> orderquery(WxPay wxPay) throws Exception {
        PayInfo payInfo = getTenant(PAYWAY[0]);
        Map<String, String> map = payContext.orderquery(payInfo, wxPay);
        LbqWrapper <WxPay> wrapper=new LbqWrapper<>();
        wrapper.eq(WxPay::getOrderNumber,wxPay.getOrderNumber());
        wxPay = baseMapper.selectOne(wrapper);
        if(null!=wxPay){
            if(!map.containsKey("result_code")){
                log.error("微信支付失败：无正常响应,"+ JSONUtils.toJSONString(map));
                throw BizException.wrap("微信支付失败：无正常响应");
            }
            if( "FAIL".equals(map.get("result_code"))){
                log.error("微信支付失败："+map.get("err_code_des"));
                throw BizException.wrap("微信支付失败："+map.get("err_code_des"));
            }
            Integer status = TradeStateEnum.match(map.get("trade_state")).getCode();
            if(!wxPay.getPayState().equals(status)){
                String transactionId = map.containsKey("transaction_id")? map.get("transaction_id"):null;
                wxPay.setTransactionId(transactionId)
                        .setPayState(status);
                updateById(wxPay);
            }
            return R.success(map);
        }else{
            return R.fail("未找到订单");
        }

    }

    @Override
    public R<Map<String, String>> reverse(WxPay wxPay) {
        return null;
    }

    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        if (!data.containsKey(WXPayConstants.FIELD_SIGN) ) {
            return false;
        }
        String sign = data.get(WXPayConstants.FIELD_SIGN);
        return WXPayUtil.generateSignature(data, key).equals(sign);
    }

    private PayInfo getTenant(String payWay) {
        //获取当前登录租户编号
        String tenant = BaseContextHandler.getTenant();
        //当前租户有效的微信支付信息
        LbqWrapper<PayInfo> wrapper = Wraps.<PayInfo>lbQ().eq(PayInfo::getPayCode, payWay)
                .eq(PayInfo::getCompanyCode, tenant)
                .eq(PayInfo::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        return payInfoMapper.selectOne(wrapper);
    }

    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname)){
                resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
            }else{
                resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
            }
        }
        catch (Exception exception) {
        }
        return resultString;
    }

}
