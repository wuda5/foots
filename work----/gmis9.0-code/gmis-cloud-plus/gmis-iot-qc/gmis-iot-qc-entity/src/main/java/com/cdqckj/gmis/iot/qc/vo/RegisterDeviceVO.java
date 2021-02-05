package com.cdqckj.gmis.iot.qc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 设备阀控vo
 * @author: 秦川物联网科技
 * @date: 2020/10/26  10:28
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "RegisterDeviceVO", description = "注册设备")
public class RegisterDeviceVO implements Serializable {
    /**
     * 表钢号
     */
    @ApiModelProperty(value = "表钢号")
    private String  gasMeterNumber;
    /**
     * 价格id
     */
    @ApiModelProperty(value = "控制模式")
    private String controlMode;
    /**
     * 厂家id
     */
    @ApiModelProperty(value = "厂家id")
    private Long gasMeterFactoryId;
    /**
     * 设备版号
     */
    @ApiModelProperty(value = "版号")
    private String versionNo;
    /**
     * 型号规格
     */
    @ApiModelProperty(value = "型号规格")
    private String specification;
    /**
     * 客户编号
      */
    @ApiModelProperty(value = "客户编号")
    private String customerCode;
    /**
     * 客户姓名
     */
    @ApiModelProperty(value = "客户姓名")
    private String customerName;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    private String telephone;
    /**
     * 客户类型
     */
    @ApiModelProperty(value = "客户类型")
    private Integer customerType;
    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    private BigDecimal baseNumber;
    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    private String direction;
    /**
     * 安装时间
     */
    @ApiModelProperty(value = "安装时间")
    private Long installationTime;
    /**
     * 点火时间
    */
    @ApiModelProperty(value = "点火时间")
    private Long ignitionTime;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    private BigDecimal longitude;
    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    private BigDecimal latitude;
    /**
     * 设备类型
     */
    @ApiModelProperty(value = "设备类型(0-气表，1-流量计，2-水表)")
    private Integer deviceType;
    /**
     * 逻辑域id
     */
    private String domainId;
}
