package com.cdqckj.gmis.installed.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.cdqckj.gmis.base.entity.Entity;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 报装记录
 * </p>
 *
 * @author tp
 * @since 2020-11-05
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gc_install_record")
@ApiModel(value = "InstallRecord", description = "报装记录")
@AllArgsConstructor
public class InstallRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司CODE
     */
    @ApiModelProperty(value = "公司CODE")
    @Length(max = 32, message = "公司CODE长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司CODE")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    private String companyName;

    /**
     * 组织ID
     */
    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
    @Excel(name = "组织ID")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    private String orgName;

    /**
     * 报装编号
     */
    @ApiModelProperty(value = "报装编号")
    @Length(max = 30, message = "报装编号长度不能超过30")
    @TableField(value = "install_number", condition = LIKE)
    @Excel(name = "报装编号")
    private String installNumber;

    /**
     * 报装类型ID：数字类型（字典表ID）
     *             SFINSTALL:商福报装
     *             GYINSTALL:工业报装
     *             LSINSTALL:零散报装
     *             JZINSTALL:集中报装
     */
    @ApiModelProperty(value = "报装类型ID：数字类型（字典表ID）")
    @TableField("install_type_id")
    @Excel(name = "报装类型ID：数字类型（字典表ID）")
    private Long installTypeId;

    /**
     * 报装类型名称
     *             SFINSTALL:商福报装
     *             GYINSTALL:工业报装
     *             LSINSTALL:零散报装
     *             JZINSTALL:集中报装
     */
    @ApiModelProperty(value = "报装类型名称")
    @Length(max = 30, message = "报装类型名称长度不能超过30")
    @TableField(value = "install_type_name", condition = LIKE)
    @Excel(name = "报装类型名称")
    private String installTypeName;

    /**
     * 市CODE
     */
    @ApiModelProperty(value = "市CODE")
    @Length(max = 32, message = "市CODE长度不能超过32")
    @TableField(value = "city_code", condition = LIKE)
    @Excel(name = "市CODE")
    private String cityCode;

    /**
     * 市名称
     */
    @ApiModelProperty(value = "市名称")
    @Length(max = 32, message = "市名称长度不能超过32")
    @TableField(value = "city_name", condition = LIKE)
    @Excel(name = "市名称")
    private String cityName;

    /**
     * 区县CODE
     */
    @ApiModelProperty(value = "区县CODE")
    @Length(max = 32, message = "区县CODE长度不能超过32")
    @TableField(value = "area_code", condition = LIKE)
    @Excel(name = "区县CODE")
    private String areaCode;

    /**
     * 区县名称
     */
    @ApiModelProperty(value = "区县名称")
    @Length(max = 32, message = "区县名称长度不能超过32")
    @TableField(value = "area_name", condition = LIKE)
    @Excel(name = "区县名称")
    private String areaName;

    /**
     * 街道（乡镇）ID
     */
    @ApiModelProperty(value = "街道（乡镇）ID")
    @TableField("street_id")
    @Excel(name = "街道（乡镇）ID")
    private Long streetId;

    /**
     * 街道（乡镇）
     */
    @ApiModelProperty(value = "街道（乡镇）")
    @Length(max = 100, message = "街道（乡镇）长度不能超过100")
    @TableField(value = "street_name", condition = LIKE)
    @Excel(name = "街道（乡镇）")
    private String streetName;

    /**
     * 小区（村）ID
     */
    @ApiModelProperty(value = "小区（村）ID")
    @TableField("community_id")
    @Excel(name = "小区（村）ID")
    private Long communityId;

    /**
     * 小区（村）
     */
    @ApiModelProperty(value = "小区（村）")
    @Length(max = 100, message = "小区（村）长度不能超过100")
    @TableField(value = "community_name", condition = LIKE)
    @Excel(name = "小区（村）")
    private String communityName;

    /**
     * 楼栋（组）
     */
    @ApiModelProperty(value = "楼栋（组）")
    @TableField("storied_id")
    @Excel(name = "楼栋（组）")
    private Long storiedId;

    /**
     * 楼栋（组）
     */
    @ApiModelProperty(value = "楼栋（组）")
    @Length(max = 32, message = "楼栋（组）长度不能超过32")
    @TableField(value = "storied_name", condition = LIKE)
    @Excel(name = "楼栋（组）")
    private String storiedName;

    /**
     * 楼层
     */
    @ApiModelProperty(value = "楼层")
    @TableField("floor_num")
    @Excel(name = "楼层")
    private Integer floorNum;

    /**
     * 户数
     */
    @ApiModelProperty(value = "户数")
    @TableField("population")
    @Excel(name = "户数")
    private Integer population;

    /**
     * 详细地址备注
     */
    @ApiModelProperty(value = "详细地址备注")
    @Length(max = 1000, message = "详细地址备注长度不能超过1000")
    @TableField(value = "address_remark", condition = LIKE)
    @Excel(name = "详细地址备注")
    private String addressRemark;

    /**
     * 报装人
     */
    @ApiModelProperty(value = "报装人")
    @Length(max = 100, message = "报装人长度不能超过100")
    @TableField(value = "customer_name", condition = LIKE)
    @Excel(name = "报装人")
    private String customerName;

    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 11, message = "联系电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "联系电话")
    private String telphone;

    /**
     * 证件类型ID：数字类型(字典表ID)
     *             ICID:身份证
     *             BLID:营业执照
     *             
     */
    @ApiModelProperty(value = "证件类型ID：数字类型(字典表ID)")
    @TableField("id_type_id")
    @Excel(name = "证件类型ID：数字类型(字典表ID)")
    private Long idTypeId;

    /**
     * 证件类型ID：
     *             ICID:身份证
     *             BLID:营业执照
     */
    @ApiModelProperty(value = "证件类型ID：")
    @Length(max = 50, message = "证件类型ID：长度不能超过50")
    @TableField(value = "id_type_name", condition = LIKE)
    @Excel(name = "证件类型ID：")
    private String idTypeName;

    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    @Length(max = 30, message = "证件号码长度不能超过30")
    @TableField(value = "id_number", condition = LIKE)
    @Excel(name = "证件号码")
    private String idNumber;

    /**
     * 受理时间
     */
    @ApiModelProperty(value = "受理时间")
    @TableField("accept_time")
    @Excel(name = "受理时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime acceptTime;
    /**
     * 受理人名称
     */
    @ApiModelProperty(value = "受理人名称")
    @TableField(value = "accept_user", condition = LIKE)
    @Excel(name = "受理人id")
    private Long acceptUser;
    /**
     * 受理人名称
     */
    @ApiModelProperty(value = "受理人名称")
    @TableField(value = "accept_user_name", condition = LIKE)
    @Excel(name = "受理人名称")
    private String acceptUserName;

    /**
     * 踏勘人ID
     */
    @ApiModelProperty(value = "踏勘人ID")
    @TableField("step_on_user_id")
    @Excel(name = "踏勘人ID")
    private Long stepOnUserId;

    /**
     * 踏勘人名称
     */
    @ApiModelProperty(value = "踏勘人名称")
    @Length(max = 1000, message = "踏勘人名称长度不能超过1000")
    @TableField(value = "step_on_user_name", condition = LIKE)
    @Excel(name = "踏勘人名称")
    private String stepOnUserName;

    /**
     * 踏勘时间
     */
    @ApiModelProperty(value = "踏勘时间")
    @TableField("step_on_time")
    @Excel(name = "踏勘时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stepOnTime;

    /**
     * 安装方案设计人ID
     */
    @ApiModelProperty(value = "安装方案设计人ID")
    @TableField("design_user_id")
    @Excel(name = "安装方案设计人ID")
    private Long designUserId;

    /**
     * 安装方案设计人名称
     */
    @ApiModelProperty(value = "安装方案设计人名称")
    @Length(max = 100, message = "安装方案设计人名称长度不能超过100")
    @TableField(value = "design_user_name", condition = LIKE)
    @Excel(name = "安装方案设计人名称")
    private String designUserName;

    /**
     * 安装方案设计时间
     */
    @ApiModelProperty(value = "安装方案设计时间")
    @TableField("design_time")
    @Excel(name = "安装方案设计时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime designTime;

    /**
     * 审核人ID
     */
    @ApiModelProperty(value = "审核人ID")
    @TableField("review_user_id")
    @Excel(name = "审核人ID")
    private Long reviewUserId;

    /**
     * 审核人名称
     */
    @ApiModelProperty(value = "审核人名称")
    @Length(max = 100, message = "审核人名称长度不能超过100")
    @TableField(value = "review_user_name", condition = LIKE)
    @Excel(name = "审核人名称")
    private String reviewUserName;

    /**
     * 审核时间
     */
    @ApiModelProperty(value = "审核时间")
    @TableField("review_time")
    @Excel(name = "审核时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime reviewTime;

    /**
     * 审核意见
     */
    @ApiModelProperty(value = "审核意见")
    @Length(max = 100, message = "审核意见长度不能超过100")
    @TableField(value = "review_objection", condition = LIKE)
    @Excel(name = "审核意见")
    private String reviewObjection;

    /**
     * 安装收费金额
     */
    @ApiModelProperty(value = "安装收费金额")
    @TableField("money")
    @Excel(name = "安装收费金额")
    private BigDecimal money;

    /**
     * 安装收费人
     */
    @ApiModelProperty(value = "安装收费人")
    @TableField("install_charge_user")
    @Excel(name = "安装收费人")
    private Long installChargeUser;

    /**
     * 安装收费时间
     */
    @ApiModelProperty(value = "安装收费时间")
    @TableField("charge_time")
    @Excel(name = "安装收费时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime chargeTime;

    /**
     * 安装收费状态
     *             CHARGING: 缴费中
     *             CHARGED: 缴费完成
     *             CHARGE_FAILED: 缴费失败
     *             
     */
    @ApiModelProperty(value = "安装收费状态")
    @Length(max = 32, message = "安装收费状态长度不能超过32")
    @TableField(value = "charge_status", condition = LIKE)
    @Excel(name = "安装收费状态")
    private String chargeStatus;

    /**
     * 安装施工人ID
     */
    @ApiModelProperty(value = "安装施工人ID")
    @TableField("install_user_id")
    @Excel(name = "安装施工人ID")
    private Long installUserId;

    /**
     * 安装施工人名称
     */
    @ApiModelProperty(value = "安装施工人名称")
    @Length(max = 1000, message = "安装施工人名称长度不能超过1000")
    @TableField(value = "install_user_name", condition = LIKE)
    @Excel(name = "安装施工人名称")
    private String installUserName;

    /**
     * 安装施工时间
     */
    @ApiModelProperty(value = "安装施工时间")
    @TableField("install_time")
    @Excel(name = "安装施工时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime installTime;

    /**
     * 客服回访人ID
     */
    @ApiModelProperty(value = "客服回访人ID")
    @TableField("return_visit_user")
    @Excel(name = "客服回访人ID")
    private Long returnVisitUser;

    /**
     * 客服回访时间
     */
    @ApiModelProperty(value = "客服回访时间")
    @TableField("return_visit_time")
    @Excel(name = "客服回访时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime returnVisitTime;

    /**
     * 结单人ID
     */
    @ApiModelProperty(value = "结单人ID")
    @TableField("end_job_user_id")
    @Excel(name = "结单人ID")
    private Long endJobUserId;

    /**
     * 结单人名称
     */
    @ApiModelProperty(value = "结单人名称")
    @Length(max = 100, message = "结单人名称长度不能超过100")
    @TableField(value = "end_job_user_name", condition = LIKE)
    @Excel(name = "结单人名称")
    private String endJobUserName;

    /**
     * 结单时间
     */
    @ApiModelProperty(value = "结单时间")
    @TableField("end_job_time")
    @Excel(name = "结单时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime endJobTime;

    /**
     * 终止人ID
     */
    @ApiModelProperty(value = "终止人ID")
    @TableField("stop_user_id")
    @Excel(name = "终止人ID")
    private Long stopUserId;

    /**
     * 终止人名称
     */
    @ApiModelProperty(value = "终止人名称")
    @Length(max = 100, message = "终止人名称长度不能超过100")
    @TableField(value = "stop_user_name", condition = LIKE)
    @Excel(name = "终止人名称")
    private String stopUserName;

    /**
     * 终止时间
     */
    @ApiModelProperty(value = "终止时间")
    @TableField("stop_time")
    @Excel(name = "终止时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime stopTime;

    /**
     * 终止原因
     */
    @ApiModelProperty(value = "终止原因")
    @Length(max = 1000, message = "终止原因长度不能超过1000")
    @TableField(value = "stop_reason", condition = LIKE)
    @Excel(name = "终止原因")
    private String stopReason;

    /**
     * 报装状态
     *             0 待踏勘
     *             1 待提审
     *             2 待审核
     *             3 待收费
     *             4 待施工
     *             5 待验收
     *             6 待结单
     *             7 待回访
     *             8 已结单
     *             9 终止
     */
    @ApiModelProperty(value = "报装状态")
    @TableField("data_status")
    @Excel(name = "报装状态")
    private Integer dataStatus;

    /**
     * 工单状态
     *             0: 待接单
     *             1: 已接单
     *             2: 已拒单
     *             3: 已结单
     *             4: 已取消
     */
    @ApiModelProperty(value = "工单状态")
    @TableField("order_status")
    @Excel(name = "工单状态")
    private Integer orderStatus;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Length(max = 200, message = "备注长度不能超过200")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "备注")
    private String remark;


    @Builder
    public InstallRecord(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    String companyCode, String companyName, Long orgId, String orgName, String installNumber, 
                    Long installTypeId, String installTypeName, String cityCode, String cityName, String areaCode, String areaName, 
                    Long streetId, String streetName, Long communityId, String communityName, Long storiedId, String storiedName, 
                    Integer floorNum, Integer population, String addressRemark, String customerName, String telphone, Long idTypeId, 
                    String idTypeName, String idNumber, LocalDateTime acceptTime, Long stepOnUserId, String stepOnUserName, LocalDateTime stepOnTime, 
                    Long designUserId, String designUserName, LocalDateTime designTime, Long reviewUserId, String reviewUserName, LocalDateTime reviewTime, 
                    String reviewObjection, BigDecimal money, Long installChargeUser, LocalDateTime chargeTime, String chargeStatus, Long installUserId, 
                    String installUserName, LocalDateTime installTime, Long returnVisitUser, LocalDateTime returnVisitTime, Long endJobUserId, String endJobUserName, 
                    LocalDateTime endJobTime, Long stopUserId, String stopUserName, LocalDateTime stopTime, String stopReason, Integer dataStatus, 
                    Integer orderStatus, String remark) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.installNumber = installNumber;
        this.installTypeId = installTypeId;
        this.installTypeName = installTypeName;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.areaCode = areaCode;
        this.areaName = areaName;
        this.streetId = streetId;
        this.streetName = streetName;
        this.communityId = communityId;
        this.communityName = communityName;
        this.storiedId = storiedId;
        this.storiedName = storiedName;
        this.floorNum = floorNum;
        this.population = population;
        this.addressRemark = addressRemark;
        this.customerName = customerName;
        this.telphone = telphone;
        this.idTypeId = idTypeId;
        this.idTypeName = idTypeName;
        this.idNumber = idNumber;
        this.acceptTime = acceptTime;
        this.stepOnUserId = stepOnUserId;
        this.stepOnUserName = stepOnUserName;
        this.stepOnTime = stepOnTime;
        this.designUserId = designUserId;
        this.designUserName = designUserName;
        this.designTime = designTime;
        this.reviewUserId = reviewUserId;
        this.reviewUserName = reviewUserName;
        this.reviewTime = reviewTime;
        this.reviewObjection = reviewObjection;
        this.money = money;
        this.installChargeUser = installChargeUser;
        this.chargeTime = chargeTime;
        this.chargeStatus = chargeStatus;
        this.installUserId = installUserId;
        this.installUserName = installUserName;
        this.installTime = installTime;
        this.returnVisitUser = returnVisitUser;
        this.returnVisitTime = returnVisitTime;
        this.endJobUserId = endJobUserId;
        this.endJobUserName = endJobUserName;
        this.endJobTime = endJobTime;
        this.stopUserId = stopUserId;
        this.stopUserName = stopUserName;
        this.stopTime = stopTime;
        this.stopReason = stopReason;
        this.dataStatus = dataStatus;
        this.orderStatus = orderStatus;
        this.remark = remark;
    }

}
