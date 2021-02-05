package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;

/**
 * 通知方式
 *
 * @author gmis
 */
@Data
@Builder
public class Notice {
    public static final String NOTICE_TYPE_SMS = "sms";
    public static final String NOTICE_TYPE_EMAIL = "email";
    /**
     * 通知类型
     */
    private String type;
    /**
     * 通知所需要的值（例如：通知类型为短信时，值为手机号码；通知类型为Email时，值为邮件地址。）
     */
    private String value;

}
