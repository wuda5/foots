package com.cdqckj.gmis.iot.qc.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "IotSubscribeUpdateDTO", description = "")
public class IotSubscribeUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * qnms3.0返回的licence
     */
    @ApiModelProperty(value = "qnms3.0返回的licence")
    @Length(max = 65535, message = "qnms3.0返回的licence长度不能超过65,535")
    private String licence;
    /**
     * 逻辑域id
     */
    @ApiModelProperty(value = "逻辑域id")
    @Length(max = 255, message = "逻辑域id长度不能超过255")
    private String domainId;
    /**
     * 是否订阅
     */
    @ApiModelProperty(value = "是否订阅")
    private Integer subscribe;
    /**
     * 通知类型
     */
    @ApiModelProperty(value = "通知类型")
    @Length(max = 255, message = "通知类型长度不能超过255")
    private String noticeType;
    /**
     * 通知地址
     */
    @ApiModelProperty(value = "通知地址")
    @Length(max = 255, message = "通知地址长度不能超过255")
    private String noticeUrl;
    /**
     * 通知缓存天数
     */
    @ApiModelProperty(value = "通知缓存天数")
    private Integer noticeCacheDay;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 255, message = "厂家名称长度不能超过255")
    private String factoryName;

    /**
     * 厂家编码
     */
    @ApiModelProperty(value = "厂家编码")
    @Length(max = 255, message = "厂家编码长度不能超过255")
    private String factoryCode;

    /**
     * appId
     */
    @ApiModelProperty(value = "appId")
    @Length(max = 255, message = "appId长度不能超过255")
    private String appId;

    /**
     * appSecret
     */
    @ApiModelProperty(value = "appSecret")
    private String appSecret;

    /**
     * 网关地址
     */
    @ApiModelProperty(value = "网关地址")
    @Length(max = 255, message = "appId长度不能超过255")
    private String gatewayUrl;

    /**
     * 租户code
     */
    @ApiModelProperty(value = "租户code")
    @Length(max = 255, message = "appId长度不能超过255")
    private String tenantCode;

    /**
     * 网关名称
     */
    @ApiModelProperty(value = "网关名称")
    @Length(max = 255, message = "网关名称长度不能超过255")
    private String gatewayName;

    /**
     * 网关版本
     */
    @ApiModelProperty(value = "网关版本")
    @Length(max = 255, message = "网关版本长度不能超过255")
    private String gatewayVersion;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 255, message = "备注长度不能超过500")
    private String remark;

    /**
     * 数据状态
     */
    @ApiModelProperty(value = "数据状态")
    private Integer dataStatus;
}
