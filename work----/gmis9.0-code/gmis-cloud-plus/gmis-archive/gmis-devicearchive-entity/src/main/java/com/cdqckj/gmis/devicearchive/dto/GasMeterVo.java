package com.cdqckj.gmis.devicearchive.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.devicearchive.enumeration.ArchiveSourceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterVo", description = "气表信息参数")
public class GasMeterVo {
    private Integer pageNo;
    private  Integer pageSize;

    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    private String gasCode;


    /**
     * 气表缴费编号
     */
    @ApiModelProperty(value = "气表缴费编号")
    private String customerChargeNo;


    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long gasMeterFactoryId;

    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId;

    @ApiModelProperty(value = "型号ID")
    private Long gasMeterModelId;

    /**
     * 表类型ID
     */
    @ApiModelProperty(value = "表类型ID")
    private Long gasMeterTypeId;



    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    private Integer direction;



    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;

    @ApiModelProperty(value = "详细地址供查询用")
    @Length(max = 100, message = "详细地址供查询用长度不能超过100")
    private String moreGasMeterAddress;


    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型code")
    private Long useGasTypeCode;



    /**
     * 调压箱ID
     */
    @ApiModelProperty(value = "调压箱编号")
    private String nodeNumber;

    /**
     * 通气状态
     */
    @ApiModelProperty(value = "通气状态")
    private Integer ventilateStatus;


    /**
     * 建档来源
     * #ArchiveSourceType{BATCH_CREATE_ARCHIVE:批量建档;IMPORT_ARCHIVE:导入建档;ARTIFICIAL_CREATE_ARCHIVE:人工建档;}
     */
    @ApiModelProperty(value = "建档来源")
    private ArchiveSourceType newSource;


    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;


    /**
     * 报装编号
     */
    @ApiModelProperty(value = "表身号")
    private String installNumber;



    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    private BigDecimal gasMeterBase;


    /**
     * 开户人ID
     */
    @ApiModelProperty(value = "开户人ID")
    private Long openAccountUserId;

    /**
     * 预建档 有效 拆除
     */
    @ApiModelProperty(value = "预建档 待安装 待开户 待通气 已通气 拆除")
    private Integer[] dataStatus;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark;

    /**
     * 表具类型
     */
    @ApiModelProperty(value = "表具类型")
    private List<String> orderSourceName;

    /**
     * 用气类型id
     */
    @ApiModelProperty(value = "用气类型id")
    private Long useGasTypeId;

    /**
     * 金额标志
     */
    @ApiModelProperty(value = "金额标志")
    private String amountMark;

    /**
     * 人工核算范围开始时间
     */
    @ApiModelProperty(value = "人工核算范围开始时间")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate checkStartTime;

    /**
     * 人工核算范围结束时间
     */
    @ApiModelProperty(value = "人工核算范围结束时间")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private LocalDate checkEndTime;


}
