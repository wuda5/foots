package com.cdqckj.gmis.pay.service;

import com.alipay.api.AlipayApiException;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.ChargeCompleteDTO;
import com.cdqckj.gmis.pay.dto.WxPayDTO;
import com.cdqckj.gmis.pay.dto.WxPaySaveDTO;
import com.cdqckj.gmis.pay.entity.WxPay;
import com.cdqckj.gmis.systemparam.entity.PayInfo;
import com.github.wxpay.sdk.WXPay;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-06-04
 */
public interface WxConfService {
    /**
     * 图片上传
     * @return
     */
    R<String> uploadImage(MultipartFile simpleFile) throws Exception ;
    /**
     * 下载证书
     */
    void certDownload() throws Exception;

}
