package com.cdqckj.gmis.pay.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.charges.dto.ChargeRefundResDTO;
import com.cdqckj.gmis.charges.dto.RefundCompleteDTO;
import com.cdqckj.gmis.pay.PayBizApi;
import com.cdqckj.gmis.pay.dto.*;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.pay.entity.WxRefund;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Component
public class PayBizApiFallback implements PayBizApi {

    @Override
    public R<Map> getAllWxChangeDetial() {
        return R.timeout();
    }

    @Override
    public R<Map<String, String>> wechatScanCode(WxPay wxPay) {
        return null;
    }

    @Override
    public R<Map<String, String>> wechatApplets(WxPay wxPay) {
        return null;
    }

    @Override
    public R<Map<String, String>> wechatAppPay(WxPay wxPay) {
        return null;
    }

    @Override
    public R<Map<String, String>> wechatMicropay(WxPay wxPay) {
        return null;
    }

    @Override
    public R<String> alipayScanCode(WxPaySaveDTO wxPayDTO) {
        return R.timeout();
    }

    @Override
    public R<ChargeCompleteDTO> weixinNotify(String result) {
        return null;
    }

    @Override
    public R<Map<String, String>> orderquery(WxPay wxPay) {
        return null;
    }

    @Override
    public R<Page<WxBill>> queryWeixinBill(@RequestBody PageParams<WxBillPageDTO> params) throws Exception {
        return null;
    }

    @Override
    public R<Boolean> queryAndSaveBill(String billDate) throws Exception {
        return null;
    }

    @Override
    public R<Map<String, String>> weixinRefund(WxRefund wxRefund) {
        return null;
    }

    @Override
    public R<RefundCompleteDTO> refundNotify(String result) {
        return null;
    }

    @Override
    public R<Map<String, String>> refundQuery(WxRefundPageDTO refund) {
        return null;
    }

}
