package com.cdqckj.gmis.common.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 流程编码标识
 *
 * @author gmis
 * @date 2018/12/29
 */
@Getter
@JsonFormat(shape= JsonFormat.Shape.OBJECT)
@ApiModel(value= "BizCode", description= "流程编码标识")
public enum BizFCode {

    /**
     * 报装流程 编码标识
     */
    BZ("报装流程","gc_install_record","install_number",12),
    /**
     * 报装
     */
    BZ_ORDER("报装流程","gc_order_record","install_number",12),
    /**
     * 收费流程 编码标识
     */
    SF("收费流程","","",10),
    /**
     * 安检流程 编码标识
     */
    AJ("安检流程","gc_security_check_record","sc_no",10),
    /**
     * 运维流程 编码标识
     */
    YW("运维流程","","",10),
    /**
     * 开户流程 编码标识
     */
    KH("开户流程","gt_customer_scene_charge_order","scene_charge_no", 10),
    /**
     * 过户流程 编码标识
     */
    GH("过户流程","gt_customer_scene_charge_order","scene_charge_no", 10),
    /**
     * 开卡流程 编码标识
     */
    KK("开卡流程","gt_customer_scene_charge_order","scene_charge_no", 12),
    /**
     * 补卡流程 编码标识
     */
    BK("补卡流程","gt_customer_scene_charge_order","scene_charge_no", 10),
    /**
     * 换表流程 编码标识
     */
    HB("换表流程","gt_change_meter_record","change_meter_no", 12),
    /**
     * 拆表流程 编码标识
     */
    CB("拆表流程","gt_remove_meter_record","remove_meter_no", 12),
    /**
     * 代扣流程 编码标识
     */
    DK("代扣流程","gt_customer_scene_charge_order","scene_charge_no", 10),
    /**
     * 退费流程 编码标识
     */
    TF("退费流程","gt_customer_scene_charge_order","scene_charge_no", 10);

    @ApiModelProperty(value ="描述")
    private String desc;

    @ApiModelProperty(value ="表名")
    private String tableName;

    @ApiModelProperty(value ="列名")
    private String colName;

    @ApiModelProperty(value ="长度")
    private Integer length;

    BizFCode() {
    }

    BizFCode(String desc, String tableName, String colName, Integer length) {
        this.desc = desc;
        this.tableName = tableName;
        this.colName = colName;
        this.length = length;
    }

    public static DateType match(String val, DateType def) {
        for (DateType enm : DateType.values()) {
            if (enm.name().equalsIgnoreCase(val)) {
                return enm;
            }
        }
        return def;
    }

    public static DateType get(String val) {
        return match(val, null);
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(DateType val) {
        if (val ==null) {
            return false;
        }
        return eq(val.name());
    }

    @ApiModelProperty(value="编码")
    public String getCode() {
        return this.name();
    }
}
