package com.cdqckj.gmis.iot.qc.vo;

import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 设备开户vo
 * @author: 秦川物联网科技
 * @date: 2020/10/21  10:22
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@ApiModel(value = "OpenAccountVO", description = "开户数据")
public class OpenAccountVO implements Serializable {

    /**
     * 逻辑域id
     */
    @ApiModelProperty(value = "厂家唯一标识,不用传")
    private String domainId;
    /**
     * 气表编码(表钢号)
     */
    @ApiModelProperty(value = "气表编码",required = true)
    private String gasMeterNumber;
    /**
     * 用气类型id
      */
    @ApiModelProperty(value = "用气类型id",required = true)
    private Long useGasTypeId;
    /**
     * 用气类型编号
     */
    @ApiModelProperty(value = "用气类型编号",required = true)
    private Integer useGasTypeNum;
    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称",required = true)
    private String useGasTypeName;
    /**
     * 价格id
     */
    @ApiModelProperty(value = "价格id",required = true)
    private Long priceId;
    /**
     * 价格号
     */
    @ApiModelProperty(value = "价格号",required = true)
    private Integer priceNum;
    /**
     * 价格id
     */
    @ApiModelProperty(value = "价格id",required = true)
    private Integer cycle;
    /**
     * 调价生效时间
     */
    @ApiModelProperty(value = "调价生效时间",required = true)
    private LocalDate startTime;
    /**
     * 方案结束
     */
    @ApiModelProperty(value = "方案结束时间",required = true)
    private LocalDate endTime;
    /**
     * 调价是否清零
     */
    @ApiModelProperty(value = "调价是否清零",required = true)
    private Integer priceChangeIsClear;
    /**
     * 起始月
     */
    @ApiModelProperty(value = "起始月",required = true)
    private Integer startMonth;
    /**
     * 结算日
     */
    @ApiModelProperty(value = "结算日",required = true)
    private Integer settlementDay ;
    /**
     * 气表厂家编码
     */
    @ApiModelProperty(value = "气表厂家编码",required = true)
    private String gasMeterFactoryCode;
    /**
     * 报警量
     */
    @ApiModelProperty(value = "报警量")
    private Integer alarmAmount;
    /**
     * 是否调价清零
     */
    @ApiModelProperty(value = "是否调价清零")
    private Integer isClear;
    /**
     * 充值金额
     */
    @ApiModelProperty(value = "充值金额")
    private Double rechargeAmount;
    /**
     * 充值次数
     */
    @ApiModelProperty(value = "充值次数")
    private Integer rechargeTimes;
    /**
     *价格方案信息
     */
    @ApiModelProperty(value = "价格方案信息")
    private List<PriceVO> tiered;

    @ApiModelProperty(value = "指令数据(不需要穿)",required = false)
    private IotGasMeterCommand iotGasMeterCommand;
    @ApiModelProperty(value = "指令详情数据(不需要穿)",required = false)
    private IotGasMeterCommandDetail iotGasMeterCommandDetail;
}
