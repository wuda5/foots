package com.cdqckj.gmis.iot.qc.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.entity.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;


/**
 * <p>
 * 实体类
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-10-12
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("qw_iot_subscribe")
@ApiModel(value = "IotSubscribe", description = "")
@AllArgsConstructor
public class IotSubscribe extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * qnms3.0返回的licence
     */
    @ApiModelProperty(value = "qnms3.0返回的licence")
    @Length(max = 65535, message = "qnms3.0返回的licence长度不能超过65535")
    @TableField("licence")
    @Excel(name = "qnms3.0返回的licence")
    private String licence;

    /**
     * 逻辑域id
     */
    @ApiModelProperty(value = "逻辑域id")
    @Length(max = 255, message = "逻辑域id长度不能超过255")
    @TableField(value = "domain_id", condition = LIKE)
    @Excel(name = "逻辑域id")
    private String domainId;

    /**
     * 是否订阅
     */
    @ApiModelProperty(value = "是否订阅")
    @TableField("subscribe")
    @Excel(name = "是否订阅")
    private Integer subscribe;

    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型")
    @Length(max = 255, message = "通知类型长度不能超过255")
    @TableField(value = "notice_type", condition = LIKE)
    @Excel(name = "通知类型")
    private String noticeType;

    /**
     * 通知地址
     */
    @ApiModelProperty(value = "通知地址")
    @Length(max = 255, message = "通知地址长度不能超过255")
    @TableField(value = "notice_url", condition = LIKE)
    @Excel(name = "通知地址")
    private String noticeUrl;

    /**
     * 通知缓存天数
     */
    @ApiModelProperty(value = "通知缓存天数")
    @TableField("notice_cache_day")
    @Excel(name = "通知缓存天数")
    private Integer noticeCacheDay;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 255, message = "厂家名称长度不能超过255")
    @TableField(value = "factory_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String factoryName;

    /**
     * 厂家编码
     */
    @ApiModelProperty(value = "厂家编码")
    @Length(max = 255, message = "厂家编码长度不能超过255")
    @TableField(value = "factory_code", condition = LIKE)
    @Excel(name = "厂家编码")
    private String factoryCode;

    /**
     * appId
     */
    @ApiModelProperty(value = "appId")
    @Length(max = 255, message = "appId长度不能超过255")
    @TableField(value = "app_id", condition = LIKE)
    @Excel(name = "appId")
    private String appId;

    /**
     * appSecret
     */
    @ApiModelProperty(value = "appSecret")
    @TableField(value = "app_secret", condition = LIKE)
    @Excel(name = "appSecret")
    private String appSecret;

    /**
     * 网关地址
     */
    @ApiModelProperty(value = "网关地址")
    @Length(max = 255, message = "appId长度不能超过255")
    @TableField(value = "gateway_url", condition = LIKE)
    @Excel(name = "网关地址")
    private String gatewayUrl;

    /**
     * 租户code
     */
    @ApiModelProperty(value = "租户code")
    @Length(max = 255, message = "appId长度不能超过255")
    @TableField(value = "tenant_code", condition = LIKE)
    @Excel(name = "租户code")
    private String tenantCode;

    /**
     * 网关名称
     */
    @ApiModelProperty(value = "网关名称")
    @Length(max = 255, message = "网关名称长度不能超过255")
    @TableField(value = "gateway_name", condition = LIKE)
    @Excel(name = "网关名称")
    private String gatewayName;

    /**
     * 网关版本
     */
    @ApiModelProperty(value = "网关版本")
    @Length(max = 255, message = "网关版本长度不能超过255")
    @TableField(value = "gateway_version", condition = LIKE)
    @Excel(name = "网关版本")
    private String gatewayVersion;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 255, message = "备注长度不能超过500")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    @TableField("data_status")
    @Excel(name = "数据状态")
    private Integer dataStatus;

    @Builder
    public IotSubscribe(Long id, String factoryName, String factoryCode, String appId, String appSecret, String gatewayUrl,String tenantCode,
                        String gatewayName, String gatewayVersion, String remark, Integer dataStatus,
                    String licence, String domainId, Integer subscribe, String noticeType, String noticeUrl, Integer noticeCacheDay) {
        this.id = id;
        this.licence = licence;
        this.domainId = domainId;
        this.subscribe = subscribe;
        this.noticeType = noticeType;
        this.noticeUrl = noticeUrl;
        this.noticeCacheDay = noticeCacheDay;
        this.appId = appId;
        this.appSecret = appSecret;
        this.dataStatus = dataStatus;
        this.factoryCode = factoryCode;
        this.factoryName = factoryName;
        this.gatewayName = gatewayName;
        this.gatewayUrl = gatewayUrl;
        this.gatewayVersion = gatewayVersion;
        this.remark = remark;
        this.tenantCode = tenantCode;
    }

}
