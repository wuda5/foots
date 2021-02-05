package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.Builder;
import lombok.Data;

/**
 * 开票中的订单数据
 *
 * @author ASUS
 */
@Data
@Builder
public class Order {

    /**
     * 是	50	订单编号
     */
    private String orderNo;
    /**
     * 否	100	消费者的用户名
     */
    private String account;
    /**
     * 否	250	货物配送地址
     */
    private String address;
    /**
     * 否	消费者电话号码。手机号码或区号-固定电话号码
     */
    private String tel;
    /**
     * 否  消费者电子邮件地址。电子邮件格式。
     */
    private String email;

}
