package com.cdqckj.gmis.invoice.dto.rhapi;

import cn.hutool.json.JSONUtil;
import com.cdqckj.gmis.invoice.dto.rhapi.einvoice.ResultInvoice;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 接口返回数据
 *
 * @author ASUS
 */
@Data
@Builder
public class ResponseData {

    /**
     * 操作流水号
     */
    private String serialNo;
    /**
     * 响应发送时间。格式为yyyy-MM-dd HH:mm:ss
     */
    private String postTime;
    /**
     * 处理结果代码
     */
    private String code;
    /**
     * 处理结果消息
     */
    private String message;

    /**
     * 发票列表
     */
    List<ResultInvoice> invoices;
    /**
     * 开票或冲红时传入的扩展参数。
     */
    Map<String, String> extendedParams;

    /**
     * 非接口返回参数， 用于记录保存
     */
    String apiUrl;

    @Override
    public String toString() {
        return JSONUtil.toJsonStr(this);
    }
}
