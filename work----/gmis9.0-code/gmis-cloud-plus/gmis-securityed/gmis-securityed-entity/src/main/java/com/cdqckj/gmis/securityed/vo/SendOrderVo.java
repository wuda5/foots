package com.cdqckj.gmis.securityed.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "SendOrderVo", description = "派单参数")
public class SendOrderVo {
    /**
     * 安检编号
     */
    @ApiModelProperty(value = "安检编号")
    @Length(max = 32, message = "安检编号长度不能超过32")
    private String scNo;

    /**
     * 派工人ID
     */
    @ApiModelProperty(value = "派工人ID")
    private Long distributionUserId;
    /**
     * 派工人名称
     */
    @ApiModelProperty(value = "派工人名称")
    @Length(max = 100, message = "派工人名称长度不能超过100")
    private String distributionUserName;
    /**
     * 派人时间
     */
    @ApiModelProperty(value = "派人时间")
    private LocalDateTime distributionTime;


    /**
     * 安检员ID
     */
    @ApiModelProperty(value = "安检员ID")
    private Long securityCheckUserId;

    /**
     * 安检员名称
     */
    @ApiModelProperty(value = "安检员名称")
    @Length(max = 100, message = "安检员名称长度不能超过100")
    private String securityCheckUserName;

    /**
     * 安检时间
     */
    @ApiModelProperty(value = "安检时间")
    private LocalDateTime securityCheckTime;

    /**
     * 安检内容
     */
    @ApiModelProperty(value = "安检内容")
    @Length(max = 1000, message = "安检内容长度不能超过1000")
    private String securityCheckContent;

    /**
     * 业务类型
     *             1  报装工单
     *             2  安检工单
     *             3  运维工单
     *             4  客服工单
     */
    @ApiModelProperty(value = "业务类型")
    private Integer businessType;

    /**
     * 业务订单编号
     */
    @ApiModelProperty(value = "业务订单编号")
    @Length(max = 32, message = "业务订单编号长度不能超过32")
    private String businessOrderNumber;

    /**
     * 紧急程度
     *             normal:正常
     *             urgent:紧急
     *             very_urgent:非常紧急
     */
    @ApiModelProperty(value = "紧急程度")
    @Length(max = 20, message = "紧急程度长度不能超过20")
    private String urgency;

    /**
     * 工单内容
     */
    @ApiModelProperty(value = "工单内容")
    @Length(max = 1000, message = "工单内容长度不能超过1000")
    private String workOrderDesc;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    private String receiveUserMobile;

    /**
     * 工单取消原因：转单，拒单，退单原因描述
     */
    @ApiModelProperty(value = "工单取消原因：转单，拒单，退单原因描述")
    @Length(max = 1000, message = "工单取消原因：转单，拒单，退单原因描述长度不能超过1000")
    private String invalidDesc;
}
