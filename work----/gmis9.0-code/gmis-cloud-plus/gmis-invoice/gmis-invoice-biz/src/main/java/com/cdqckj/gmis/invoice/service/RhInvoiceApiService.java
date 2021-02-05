package com.cdqckj.gmis.invoice.service;

import com.cdqckj.gmis.invoice.dto.rhapi.ResponseData;
import com.cdqckj.gmis.invoice.dto.rhapi.KeyStoreInfo;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.ChParams;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.CxParam;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.KpParam;

/**
 * 电子发票推送
 *
 * @author ASUS
 */
public interface RhInvoiceApiService {

    /**
     * 推送电子发票开票
     *
     * @param keyStoreInfo 签名需要的参数对象
     * @param kpParam   开票请求参数
     * @return
     * @throws Exception
     */
    public ResponseData pushInvoiceKp(KeyStoreInfo keyStoreInfo, KpParam kpParam) throws Exception;

    /**
     * 发票查询
     *
     * @param keyStoreInfo 签名需要的参数对象
     * @param cxParam   查询参数
     * @return
     * @throws Exception
     */
    public ResponseData pushInvoiceCx(KeyStoreInfo keyStoreInfo, CxParam cxParam) throws Exception;

    /**
     * 发票冲红
     *
     * @param keyStoreInfo 签名需要的参数对象
     * @param chParam   冲红参数
     * @return
     * @throws Exception
     */
    public ResponseData pushInvoiceCh(KeyStoreInfo keyStoreInfo, ChParams chParam) throws Exception;

}
