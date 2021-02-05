package com.cdqckj.gmis.invoice.service.impl;

import cn.hutool.json.JSONUtil;
import com.cdqckj.gmis.invoice.dto.rhapi.KeyStoreInfo;
import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.ChParams;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.CxParam;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.KpParam;
import com.cdqckj.gmis.invoice.enumeration.ApiCmdNameEnum;
import com.cdqckj.gmis.invoice.service.RhInvoiceApiService;
import com.cdqckj.gmis.invoice.util.CertificateUtil;
import com.cdqckj.gmis.utils.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;


/**
 * 电子发票推送
 *
 * @author ASUS
 */
@Slf4j
@Service
public class RhInvoiceApiServiceImpl implements RhInvoiceApiService {

    @Value("${gmis.e-invoice.api.rh.invoice.url}")
    private String rhInvoiceApiUrl;
    @Value("${gmis.e-invoice.api.rh.order.url}")
    private String rhOrderApiUrl;

    /**
     * 推送电子发票
     */
    @Override
    public ResponseData pushInvoiceKp(KeyStoreInfo keyStoreInfo, KpParam kpParam) throws Exception {
        String postStr = JSONUtil.toJsonStr(kpParam);
        String response = doPostString(rhInvoiceApiUrl, ApiCmdNameEnum.INVOICE_KP.getCode(), keyStoreInfo, postStr);
        ResponseData responseData = JSONUtil.toBean(response, ResponseData.class);
        responseData.setApiUrl(rhInvoiceApiUrl);
        return responseData;
    }

    /**
     * 发票查询
     *
     * @param keyStoreInfo 证书签名需要的参数对象
     * @param cxParam      查询参数
     * @return
     * @throws Exception
     */
    @Override
    public ResponseData pushInvoiceCx(KeyStoreInfo keyStoreInfo, CxParam cxParam) throws Exception {
        String postStr = JSONUtil.toJsonStr(cxParam);
        String response = doPostString(rhInvoiceApiUrl, ApiCmdNameEnum.INVOICE_CX.getCode(), keyStoreInfo, postStr);
        ResponseData responseData = JSONUtil.toBean(response, ResponseData.class);
        responseData.setApiUrl(rhInvoiceApiUrl);
        return responseData;
    }

    /**
     * 发票冲红
     *
     * @param keyStoreInfo 证书签名需要的参数
     * @param chParam      冲红参数
     * @return
     * @throws Exception
     */
    @Override
    public ResponseData pushInvoiceCh(KeyStoreInfo keyStoreInfo, ChParams chParam) throws Exception {
        String postStr = JSONUtil.toJsonStr(chParam);
        String response = doPostString(rhInvoiceApiUrl, ApiCmdNameEnum.INVOICE_CH.getCode(), keyStoreInfo, postStr);
        ResponseData responseData = JSONUtil.toBean(response, ResponseData.class);
        responseData.setApiUrl(rhInvoiceApiUrl);
        return responseData;
    }


    /**
     * @param url       url地址
     * @param cmdName   操作命令
     * @param postParam 传参内容
     * @throws Exception
     */
    private String doPostString(String url, String cmdName, KeyStoreInfo keyStoreInfo, String postParam) throws Exception {
        String sign = CertificateUtil.signToBase64(postParam.getBytes(), keyStoreInfo.getKeyStorePath(),
                keyStoreInfo.getKeyStoreAlias(), keyStoreInfo.getKeyStorePwd());
        String urlAndParam = splicingUrl(url, keyStoreInfo.getAppCode(), cmdName, sign);
        log.debug("电子发票推送：" + urlAndParam);
        log.debug("推送参数：" + postParam);
        //rest请求
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpEntity<String> entity = new HttpEntity<>(postParam, headers);
        ResponseEntity<String> resp = restTemplate.exchange(new URI(urlAndParam), HttpMethod.POST, entity, String.class);
        log.debug("返回结果：" + resp.toString());
        return resp.getBody();
    }


    /**
     * 拼接URL
     *
     * @param url     url请求地址
     * @param appCode 应用code
     * @param cmdName 执行的操作命令名称
     * @param sign    证书签名
     * @return
     * @throws Exception
     */
    private String splicingUrl(String url, String appCode, String cmdName, String sign) throws Exception {

        StringBuffer sb = new StringBuffer();
        sb.append(url)
                .append("?appCode=").append(URLEncoder.encode(URLEncoder.encode(appCode, StrPool.UTF_8), StrPool.UTF_8))
                .append("&cmdName=").append(URLEncoder.encode(URLEncoder.encode(cmdName, StrPool.UTF_8), StrPool.UTF_8))
                .append("&sign=").append(URLEncoder.encode(URLEncoder.encode(sign, StrPool.UTF_8), StrPool.UTF_8));

        return sb.toString();
    }

}
