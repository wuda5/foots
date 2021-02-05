package com.cdqckj.gmis.devicearchive.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.devicearchive.enumeration.ArchiveSourceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 实体类
 * 气表档案
 * </p>
 *
 * @author gmis
 * @since 2020-08-14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "GasMeterUpdateDTO", description = "气表档案")
public class GasMeterUpdateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @NotNull(message = "id不能为空", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 气表表号
     */
    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    private String gasCode;


    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    private Long gasMeterFactoryId;

    /**
     * 型号ID
     */
    @ApiModelProperty(value = "型号ID")
    private Long gasMeterModelId;


    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    private Long gasMeterVersionId;


    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    private String direction;
    /**
     * 省code
     */
    @ApiModelProperty(value = "省code")
    private String provinceCode;
    /**
     * 市code
     */
    @ApiModelProperty(value = "市code")
    private String cityCode;
    /**
     * 区code
     */
    @ApiModelProperty(value = "区code")
    private String areaCode;

    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    private Long streetId;
    /**
     * 小区ID
     */
    @ApiModelProperty(value = "小区ID")
    private Long communityId;

    /**
     * 小区name
     */
    @ApiModelProperty(value = "小区名称")
    private String communityName;
    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    private String gasMeterAddress;

    @ApiModelProperty(value = "详细安装地址供查询用")
    @Length(max = 100, message = "详细安装地址供查询用长度不能超过100")
    private String moreGasMeterAddress;
    /**
     * 合同编号
     */
    @ApiModelProperty(value = "合同编号")
    @Length(max = 30, message = "合同编号长度不能超过30")
    private String contractNumber;
    /**
     * 用气类型ID
     */
    @ApiModelProperty(value = "用气类型编号")
    private String useGasTypeCode;

    @ApiModelProperty(value = "用气类型编号")
    private Long useGasTypeId;

    @ApiModelProperty(value = "报装编号")
    private String installNumber;

    @ApiModelProperty(value = "报装时间")
    private LocalDateTime installTime;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    private String useGasTypeName;
    /**
     * 使用人口
     */
    @ApiModelProperty(value = "使用人口")
    private Integer population;
    /**
     * 调压箱ID
     */
    @ApiModelProperty(value = "调压箱ID")
    @Length(max = 32, message = "调压箱ID长度不能超过32")
    private String nodeNumber;
    /**
     * 通气状态
     */
    @ApiModelProperty(value = "通气状态")
    private Integer ventilateStatus;
    /**
     * 通气人ID
     */
    @ApiModelProperty(value = "通气人ID")
    private Long ventilateUserId;
    /**
     * 通气人名称
     */
    @ApiModelProperty(value = "通气人名称")
    @Length(max = 100, message = "通气人名称长度不能超过100")
    private String ventilateUserName;
    /**
     * 通气时间
     */
    @ApiModelProperty(value = "通气时间")
    private LocalDate ventilateTime;
    /**
     * 安全员ID
     */
    @ApiModelProperty(value = "安全员ID")
    private Long securityUserId;
    /**
     * 安全员名称
     */
    @ApiModelProperty(value = "安全员名称")
    @Length(max = 100, message = "安全员名称长度不能超过100")
    private String securityUserName;
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
     * 库存ID
     */
    @ApiModelProperty(value = "库存ID")
    private Long stockId;
    /**
     * 建档来源
     * #ArchiveSourceType{BATCH_CREATE_ARCHIVE:批量建档;IMPORT_ARCHIVE:导入建档;ARTIFICIAL_CREATE_ARCHIVE:人工建档;}
     */
    @ApiModelProperty(value = "建档来源")
    private ArchiveSourceType newSource;
    /**
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID")
    private Long fileId;
    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    private String gasMeterNumber;

    @ApiModelProperty(value = "表身号生成方式 0手动填写 1自动生成")
    @TableField("auto_generated")
    private Integer autoGenerated;

    /**
     * 计费精度
     */
    @ApiModelProperty(value = "计费精度")
    private Integer measurementAccuracy;
    /**
     * 银行代扣
     */
    @ApiModelProperty(value = "银行代扣")
    private Integer bankWithholding;
    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    private BigDecimal gasMeterBase;
    /**
     * 预建档时间
     */
    @ApiModelProperty(value = "预建档时间")
    private LocalDateTime preBuiltTime;
    /**
     * 预建档人ID
     */
    @ApiModelProperty(value = "预建档人ID")
    private Long preBuiltUserId;
    /**
     * 预建档人名称
     */
    @ApiModelProperty(value = "预建档人名称")
    @Length(max = 100, message = "预建档人名称长度不能超过100")
    private String preBuiltUserName;
    /**
     * 开户时间
     */
    @ApiModelProperty(value = "开户时间")
    private LocalDateTime openAccountTime;
    /**
     * 开户人
     */
    @ApiModelProperty(value = "开户人")
    @Length(max = 100, message = "开户人长度不能超过100")
    private String openAccountUserName;
    /**
     * 开户人ID
     */
    @ApiModelProperty(value = "开户人ID")
    private Long openAccountUserId;
    /**
     * 预建档 有效 拆除
     */
    @ApiModelProperty(value = "预建档 有效 拆除")
    private Integer dataStatus;
    /**
     * 检定人
     */
    @ApiModelProperty(value = "检定人")
    private String checkUser;
    /**
     * 检定时间
     */
    @ApiModelProperty(value = "检定时间")
    private LocalDate checkTime;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    private String remark;
    /**
     * 删除状态
     */
    @ApiModelProperty(value = "删除状态")
    private Long deleteStatus;


    @ApiModelProperty(value = "防盗扣编号")
    private String safeCode;


    @ApiModelProperty(value = "购买时间")
    private LocalDate buyTime;

    @ApiModelProperty(value = "删除状态")
    private Integer startID;


    @ApiModelProperty(value = "表具真实id")
    private Long gasMeterRealId ;


    @ApiModelProperty(value = "发卡状态")
    private String sendCardStauts;


    /**
     * 远程业务标志
     */
    @ApiModelProperty(value = "远程业务标志")
    @Length(max = 10000, message = "远程业务标志长度不能超过10000")
    private String remoteServiceFlag;

    @ApiModelProperty(value = "拆表标识")
    @Length(max = 32, message = "拆表标识长度不能超过32")
    private String removeGasmeter;
}
