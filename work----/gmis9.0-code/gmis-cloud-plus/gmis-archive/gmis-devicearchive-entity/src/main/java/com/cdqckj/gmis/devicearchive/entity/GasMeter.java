package com.cdqckj.gmis.devicearchive.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.devicearchive.enumeration.ArchiveSourceType;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

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
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("da_gas_meter")
@ApiModel(value = "GasMeter", description = "气表档案")
@AllArgsConstructor
public class GasMeter extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 气表表号
     */
    @ApiModelProperty(value = "气表表号")
    @Length(max = 32, message = "气表表号长度不能超过32")
    @TableField(value = "gas_code", condition = LIKE)
    private String gasCode;


    /**
     * 公司编号
     */
    @ApiModelProperty(value = "公司编号")
    @Length(max = 32, message = "公司编号长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    private String companyName;

    @ApiModelProperty(value = "报装编号")
    @Length(max = 100, message = "报装编号长度不能超过100")
    @TableField(value = "install_number", condition = LIKE)
    private String installNumber;



    @ApiModelProperty(value = "报装时间")
    @TableField(value = "install_time")
    private LocalDateTime installTime;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    private String orgName;

    /**
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @TableField("gas_meter_factory_id")
    @Excel(name = "厂家名称(必填)")
    private Long gasMeterFactoryId;


    /**
     * 版号ID
     */
    @ApiModelProperty(value = "版号ID")
    @TableField("gas_meter_version_id")
    @Excel(name = "版号名称(必填)")
    private Long gasMeterVersionId;


    /**
     * 型号id
     */
    @ApiModelProperty(value = "型号id")
    @TableField("gas_meter_model_id")
    @Excel(name = "型号名称(必填)")
    private Long gasMeterModelId;





    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    @TableField("direction")
    @Excel(name = "通气方向(必填)")
    private String direction;
    /**
     * 省ID
     */
    @ApiModelProperty(value = "省code")
    @TableField("province_code")
    private String provinceCode;
    /**
     * 市ID
     */
    @ApiModelProperty(value = "市code")
    @TableField("city_code")
    private String cityCode;

    /**
     * 区ID
     */
    @ApiModelProperty(value = "区code")
    @TableField("area_code")
    private String areaCode;

    /**
     * 街道ID
     */
    @ApiModelProperty(value = "街道ID")
    @TableField("street_id")
    private Long streetId;

    /**
     * 小区ID
     */
    @ApiModelProperty(value = "小区ID")
    @TableField("community_id")
    private Long communityId;

    /**
     * 小区ID
     */
    @ApiModelProperty(value = "小区名称")
    @TableField("community_name")
    private String communityName;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Length(max = 100, message = "安装地址长度不能超过100")
    @TableField(value = "gas_meter_address", condition = LIKE)
    private String gasMeterAddress;

    /**
     * 详细地址供查询用
     */
    @ApiModelProperty(value = "详细地址供查询用")
    @Length(max = 100, message = "详细地址供查询用长度不能超过100")
    @TableField(value = "more_gas_meter_address", condition = LIKE)
    private String moreGasMeterAddress;

    /**
     * 合同编号
     */
    @ApiModelProperty(value = "合同编号")
    @Length(max = 30, message = "合同编号长度不能超过30")
    @TableField(value = "contract_number", condition = LIKE)
    private String contractNumber;

    /**
     * 用气类型ID
     */

    @ApiModelProperty(value = "用气类型编号")
    @TableField("use_gas_type_code")
    private String useGasTypeCode;

    @ApiModelProperty(value = "用气类型id")
    @TableField("use_gas_type_id")
    private Long useGasTypeId;

    /**
     * 用气类型名称
     */
    @ApiModelProperty(value = "用气类型名称")
    @Length(max = 50, message = "用气类型名称长度不能超过50")
    @TableField(value = "use_gas_type_name", condition = LIKE)
    private String useGasTypeName;

    /**
     * 使用人口
     */
    @ApiModelProperty(value = "使用人口")
    @TableField("population")
    private Integer population;

    /**
     * 调压箱ID
     */
    @ApiModelProperty(value = "调压箱ID")
    @Length(max = 32, message = "调压箱ID长度不能超过32")
    @TableField(value = "node_number", condition = LIKE)
    private String nodeNumber;

    /**
     * 表具真实ID
     */
    @ApiModelProperty(value = "表具真实ID")
    @TableField("gas_meter_real_id")
    private Long gasMeterRealId;


    /**
     * 发卡状态
     */
    @ApiModelProperty(value = "发卡状态")
    @Length(max = 32, message = "发卡状态长度不能超过32")
    @TableField(value = "send_card_stauts", condition = LIKE)
    private String sendCardStauts;

    /**
     * 通气状态
     */
    @ApiModelProperty(value = "通气状态")
    @TableField("ventilate_status")
    private Integer ventilateStatus;

    /**
     * 通气人ID
     */
    @ApiModelProperty(value = "通气人ID")
    @TableField("ventilate_user_id")
    private Long ventilateUserId;

    /**
     * 通气人名称
     */
    @ApiModelProperty(value = "通气人名称")
    @Length(max = 100, message = "通气人名称长度不能超过100")
    @TableField(value = "ventilate_user_name", condition = LIKE)
    private String ventilateUserName;

    /**
     * 通气时间
     */
    @ApiModelProperty(value = "通气时间")
    @TableField("ventilate_time")
    private LocalDate ventilateTime;

    /**
     * 安全员ID
     */
    @ApiModelProperty(value = "安全员ID")
    @TableField("security_user_id")
    private Long securityUserId;


    /**
     * 安全员名称
     */
    @ApiModelProperty(value = "安全员名称")
    @Length(max = 100, message = "安全员名称长度不能超过100")
    @TableField(value = "security_user_name", condition = LIKE)
    private String securityUserName;

    /**
     * 经度
     */
    @ApiModelProperty(value = "经度")
    @TableField("longitude")
    private BigDecimal longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value = "纬度")
    @TableField("latitude")
    private BigDecimal latitude;

    /**
     * 库存ID
     */
    @ApiModelProperty(value = "库存ID")
    @TableField("stock_id")
    private Long stockId;

    /**
     * 建档来源
     * #ArchiveSourceType{BATCH_CREATE_ARCHIVE:批量建档;IMPORT_ARCHIVE:导入建档;ARTIFICIAL_CREATE_ARCHIVE:人工建档;}
     */
    @ApiModelProperty(value = "建档来源")
    @TableField("new_source")
    private ArchiveSourceType newSource;

    /**
     * 文件ID
     */
    @ApiModelProperty(value = "文件ID")
    @TableField("file_id")
    private Long fileId;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Length(max = 30, message = "表身号长度不能超过30")
    @TableField(value = "gas_meter_number", condition = LIKE)
    @Excel(name = "表身号(卡表，普表(有就必填，没有不填)，物联网必填)")
    private String gasMeterNumber;

    /**
     * 计费精度
     */
    @ApiModelProperty(value = "表身号生成方式 0手动填写 1自动生成")
    @TableField("auto_generated")
    private Integer autoGenerated;

    /**
     * 计费精度
     */
    @ApiModelProperty(value = "计费精度")
    @TableField("measurement_accuracy")
    private Integer measurementAccuracy;

    /**
     * 银行代扣
     */
    @ApiModelProperty(value = "银行代扣")
    @TableField("bank_withholding")
    private Integer bankWithholding;

    /**
     * 预建档时间
     */
    @ApiModelProperty(value = "预建档时间")
    @TableField("pre_built_time")
    private LocalDateTime preBuiltTime;

    /**
     * 预建档人ID
     */
    @ApiModelProperty(value = "预建档人ID")
    @TableField("pre_built_user_id")
    private Long preBuiltUserId;

    /**
     * 预建档人名称
     */
    @ApiModelProperty(value = "预建档人名称")
    @Length(max = 100, message = "预建档人名称长度不能超过100")
    @TableField(value = "pre_built_user_name", condition = LIKE)
    private String preBuiltUserName;

    /**
     * 开户时间
     */
    @ApiModelProperty(value = "开户时间")
    @TableField("open_account_time")
    private LocalDateTime openAccountTime;

    /**
     * 开户人
     */
    @ApiModelProperty(value = "开户人")
    @Length(max = 100, message = "开户人长度不能超过100")
    @TableField(value = "open_account_user_name", condition = LIKE)
    private String openAccountUserName;

    /**
     * 开户人ID
     */
    @ApiModelProperty(value = "开户人ID")
    @TableField("open_account_user_id")
    private Long openAccountUserId;

    /**
     * 预建档 有效 拆除
     */
    @ApiModelProperty(value = "预建档 有效 拆除")
    @TableField("data_status")
    private Integer dataStatus;

    /**
     * 检定人
     */
    @Excel(name = "检定人")
    @ExcelSelf(name = "检定人,checkUser")
    @ApiModelProperty(value = "检定人")
    @TableField("check_user")
    private String checkUser;

    /**
     * 检定时间
     */
    @Excel(name = "检定时间",isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat ="yyyy-MM-dd" ,type = 10)
    @ApiModelProperty(value = "检定时间")
    @TableField("check_time")
    private LocalDate checkTime;

    @Excel(name = "购买时间", isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat ="yyyy-MM-dd",type = 10)
    @ApiModelProperty(value = "购买时间")
    @TableField("buy_time")
    private LocalDate buyTime;

    @Excel(name = "防盗扣编号")
    @ApiModelProperty(value = "防盗扣编号")
    @Length(max = 100, message = "防盗扣编号长度不能超过30")
    @TableField(value = "safe_code", condition = LIKE)
    private String safeCode;

    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    @TableField("gas_meter_base")
    @Excel(name = "气表底数")
    private BigDecimal gasMeterBase;


    /**
     * 远程业务标志
     */
    @ApiModelProperty(value = "远程业务标志")
    @Length(max = 10000, message = "远程业务标志长度不能超过10000")
    @TableField(value = "remote_service_flag", condition = LIKE)
/*    @Excel(name = "远程业务标志")*/
    private String remoteServiceFlag;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;

    @ApiModelProperty(value = "拆表标识")
    @Length(max = 32, message = "拆表标识长度不能超过32")
    @TableField(value = "remove_gasmeter", condition = LIKE)
    private String removeGasmeter;

    @ApiModelProperty(value = "删除状态")
    @TableField("delete_status")
    private Integer deleteStatus;


    @ApiModelProperty(value = "是否发卡")
    @TableField("start_ID")
    private Integer startID;

    @TableField(exist = false)
    private String domainId;
    @TableField(exist = false)
    private String bizNoOrId;

    @Builder
    public GasMeter(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime,
                    String gasCode, String companyCode, String companyName, Long orgId, String orgName, LocalDateTime installTime,
                    Long gasMeterFactoryId,Long gasMeterModelId, Long gasMeterVersionId,
                    String direction,String provinceCode, String cityCode, String areaCode, Long streetId, Long communityId, String communityName, String gasMeterAddress, String moreGasMeterAddress,
                    String contractNumber, Long useGasTypeId, String useGasTypeCode ,String useGasTypeName, Integer population, String nodeNumber, Integer ventilateStatus,
                    Long ventilateUserId, String ventilateUserName, LocalDate ventilateTime, Long securityUserId, String securityUserName, BigDecimal longitude,
                    BigDecimal latitude, Long stockId, ArchiveSourceType newSource, Long fileId, String gasMeterNumber,
                    Integer measurementAccuracy, Integer bankWithholding, BigDecimal gasMeterBase, LocalDateTime preBuiltTime,
                    Long preBuiltUserId, String preBuiltUserName, LocalDateTime openAccountTime, String openAccountUserName, Long openAccountUserId, Integer dataStatus,
                    String checkUser, String remoteServiceFlag,LocalDate buyTime,LocalDate checkTime, String remark, Integer deleteStatus,String installNumber,String safeCode,Integer startID, String removeGasmeter) {
        this.id = id;
        this.installNumber=installNumber;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.gasCode = gasCode;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.installTime=installTime;
        this.gasMeterFactoryId = gasMeterFactoryId;

        this.gasMeterModelId=gasMeterModelId;

        this.gasMeterVersionId = gasMeterVersionId;

        this.direction = direction;

        this.provinceCode = provinceCode;
        this.cityCode = cityCode;
        this.areaCode = areaCode;
        this.streetId = streetId;
        this.communityId = communityId;
        this.communityName = communityName;
        this.gasMeterAddress = gasMeterAddress;
        this.moreGasMeterAddress=moreGasMeterAddress;
        this.contractNumber = contractNumber;
        this.useGasTypeCode=useGasTypeCode;
        this.useGasTypeId = useGasTypeId;
        this.useGasTypeName = useGasTypeName;
        this.population = population;
        this.nodeNumber = nodeNumber;
        this.ventilateStatus = ventilateStatus;
        this.ventilateUserId = ventilateUserId;
        this.ventilateUserName = ventilateUserName;
        this.ventilateTime = ventilateTime;
        this.securityUserId = securityUserId;
        this.securityUserName = securityUserName;
        this.longitude = longitude;
        this.latitude = latitude;
        this.stockId = stockId;
        this.newSource = newSource;
        this.fileId = fileId;
        this.gasMeterNumber = gasMeterNumber;
        this.measurementAccuracy = measurementAccuracy;
        this.bankWithholding = bankWithholding;
        this.gasMeterBase = gasMeterBase;
        this.preBuiltTime = preBuiltTime;
        this.preBuiltUserId = preBuiltUserId;
        this.preBuiltUserName = preBuiltUserName;
        this.openAccountTime = openAccountTime;
        this.openAccountUserName = openAccountUserName;
        this.openAccountUserId = openAccountUserId;
        this.dataStatus = dataStatus;
        this.buyTime=buyTime;
        this.checkUser = checkUser;
        this.checkTime = checkTime;
        this.remark = remark;
        this.deleteStatus = deleteStatus;
        this.safeCode=safeCode;
        this.startID=startID;
        this.remoteServiceFlag=remoteServiceFlag;
        this.removeGasmeter = removeGasmeter;
    }

}
