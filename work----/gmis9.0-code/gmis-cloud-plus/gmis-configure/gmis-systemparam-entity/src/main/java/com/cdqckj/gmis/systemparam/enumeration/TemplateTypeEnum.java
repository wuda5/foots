package com.cdqckj.gmis.systemparam.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum TemplateTypeEnum {
    /**
     * 单据模板
     */
    BILL_TEMPLATE("BILL_TEMPLATE", "单据模板",TemplateTypeEnum.Type.Bill.class),
    /**
     * 票据模板
     */
    TICKET_TEMPLATE("TICKET_TEMPLATE", "票据模板",TemplateTypeEnum.Type.Ticket.class),
    /**
     * 发票模板
     */
    INVOICE_TEMPLATE("INVOICE_TEMPLATE", "发票模板",TemplateTypeEnum.Type.Invoice.class);  //枚举常量必须写在最前面，否则会报错

    private String code;
    private String desc;
    public TemplateTypeEnum.Type[] getValues(){
        return values;
    }
    public static Map<String, String> map = new HashMap<>();

    public Map<String, String> getMap(){
        map.clear();
        for(TemplateTypeEnum.Type d:values){
            map.put(d.getCode(),d.getDescription());
        }
        return map;
    }

    private TemplateTypeEnum.Type[] values;
    TemplateTypeEnum(String code, String desc, Class<? extends TemplateTypeEnum.Type> kind){
        this.code = code;
        this.desc = desc;
        this.values=kind.getEnumConstants();
    }

    public interface Type{      //使用interface将子枚举类型组织起来
        enum Bill implements TemplateTypeEnum.Type {
            PAYMENT_NOTICE("PAYMENT_NOTICE","缴费通知单"),
            NOTICE_OF_ARREARS("NOTICE_OF_ARREARS","欠费通知单"),
            SALES_OF_GOODS("SALES_OF_GOODS","商品销售"),
            INSTALLATION_WORK_ORDER("INSTALLATION_WORK_ORDER","报装工单"),
            CUSTOMER_WORK_ORDER("CUSTOMER_WORK_ORDER","客户工单"),
            SECURITY_WORK_ORDER("SECURITY_WORK_ORDER","安检工单"),
            Node_work_order("NODE_WORK_ORDER","节点工单"),
            GOODS_DELIVERY_WORK_ORDER("GOODS_DELIVERY_WORK_ORDER","商品送货工单"),
            COMMODITY_INSTALLATION_WORK_ORDER("COMMODITY_INSTALLATION_WORK_ORDER","商品安装工单");
            private String code;
            private String description;
            Bill(String code,String desciption){
                this.code=code;
                this.description=desciption;
            }
            @Override
            public String getCode(){
                return code;
            }
            @Override
            public String getDescription(){
                return description;
            }
        }
        enum Ticket implements TemplateTypeEnum.Type {
            GAS_BILL("GAS_BILL","燃气收费票据"),
            COMMODITY_CHARGE_BILL("COMMODITY_CHARGE_BILL","商品收费票据"),
            SCENARIO_CHARGE_TICKET("SCENARIO_CHARGE_TICKET","场景收费票据"),
            ACCESSORY_FEE_BILL("ACCESSORY_FEE_BILL","附件费票据"),
            PREMIUM_NOTE("PREMIUM_NOTE","保险费票据");
            private String code;
            private String description;
            Ticket(String code,String desciption){
                this.code=code;
                this.description=desciption;
            }
            @Override
            public String getCode(){
                return code;
            }
            @Override
            public String getDescription(){
                return description;
            }
        }
        enum Invoice implements TemplateTypeEnum.Type {
            VAT_SPECIAL_INVOICE("VAT_SPECIAL_INVOICE","增值税专票"),
            GENERAL_VAT_BILL_GAS("GENERAL_VAT_BILL_GAS","增值税普票（燃气费）"),
            GENERAL_VAT_BILL_OTHERS ("GENERAL_VAT_BILL_OTHERS","增值税普票（场景收费项、附加费）"),
            GENERAL_VAT_BILL_INSURANCE("GENERAL_VAT_BILL_INSURANCE","增值税普票（保险费）"),
            ELECTRONIC_INVOICE("ELECTRONIC_INVOICE","电子发票");
            private String code;
            private String description;
            Invoice(String code,String desciption){
                this.code=code;
                this.description=desciption;
            }
            @Override
            public String getCode(){
                return code;
            }
            @Override
            public String getDescription(){
                return description;
            }
        }
        String getCode();
        String getDescription();
    }


}
