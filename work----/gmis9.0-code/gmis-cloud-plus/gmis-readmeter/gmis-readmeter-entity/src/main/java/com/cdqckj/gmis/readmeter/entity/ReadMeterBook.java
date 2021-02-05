package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 抄表册
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_read_meter_book")
@ApiModel(value = "ReadMeterBook", description = "抄表册")
@AllArgsConstructor
public class ReadMeterBook extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码")
    @Length(max = 32, message = "公司编码长度不能超过32")
    @TableField(value = "company_code", condition = LIKE)
    @Excel(name = "公司编码")
    @ExcelSelf(name = "公司编码,Company code")
    private String companyCode;

    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称")
    @Length(max = 100, message = "公司名称长度不能超过100")
    @TableField(value = "company_name", condition = LIKE)
    @Excel(name = "公司名称")
    @ExcelSelf(name = "公司名称,Company name")
    private String companyName;

    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id")
    @TableField("org_id")
    //@Excel(name = "组织id")
    private Long orgId;

    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称")
    @Length(max = 100, message = "组织名称长度不能超过100")
    @TableField(value = "org_name", condition = LIKE)
    @Excel(name = "组织名称")
    @ExcelSelf(name = "组织名称,organization name")
    private String orgName;

    /**
     * 所属区域id
     */
    @ApiModelProperty(value = "所属区域id")
    @TableField("community_id")
    //@Excel(name = "所属区域id")
    private Long communityId;

    /**
     * 所属区域名称
     */
    @ApiModelProperty(value = "所属区域名称")
    @Length(max = 100, message = "所属区域名称长度不能超过100")
    @TableField(value = "community_name", condition = LIKE)
    @Excel(name = "所属区域名称")
    @ExcelSelf(name = "所属区域名称,community name")
    private String communityName;

    /**
     * 抄表册编号
     */
    @ApiModelProperty(value = "抄表册编号")
    @Length(max = 10, message = "抄表册编号长度不能超过10")
    @TableField(value = "book_code", condition = LIKE)
    @Excel(name = "抄表册编号")
    @ExcelSelf(name = "抄表册编号,statistical forms code")
    private String bookCode;

    /**
     * 抄表册名称
     */
    @ApiModelProperty(value = "抄表册名称")
    @Length(max = 30, message = "抄表册名称长度不能超过30")
    @TableField(value = "book_name", condition = LIKE)
    @Excel(name = "抄表册名称")
    @ExcelSelf(name = "抄表册名称,statistical forms name")
    private String bookName;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @TableField(value = "read_meter_user")
    //@Excel(name = "抄表员id")
    private Long readMeterUser;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Length(max = 100, message = "抄表员名称长度不能超过100")
    @TableField(value = "read_meter_user_name", condition = LIKE)
    @Excel(name = "抄表员名称")
    @ExcelSelf(name = "抄表员名称,read Meter userName")
    private String readMeterUserName;

    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    @TableField("total_read_meter_count")
    @Excel(name = "总户数")
    @ExcelSelf(name = "总户数,Total Households")
    private Integer totalReadMeterCount;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("book_status")
    //@Excel(name = "状态")
    private Integer bookStatus;

    /**
     * 被未完成抄表计划引用次数
     */
    @ApiModelProperty(value = "被未完成抄表计划引用次数")
    @TableField("cited_count")
    //@Excel(name = "被未完成抄表计划引用次数")
    private Integer citedCount;

    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    @Length(max = 100, message = "说明长度不能超过100")
    @TableField(value = "remark", condition = LIKE)
    @Excel(name = "说明")
    @ExcelSelf(name = "说明,explain")
    private String remark;

    /**
     * 删除标识 1：存在 0：删除
     */
    @ApiModelProperty(value = "删除标识 0：存在 1：删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Builder
    public ReadMeterBook(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, Integer deleteStatus,
                    String companyCode, String companyName, Long orgId, String orgName, String bookCode, Long communityId, String communityName,
                    String bookName, Long readMeterUser, String readMeterUserName, Integer totalReadMeterCount,
                         Integer bookStatus, String remark, Integer citedCount) {
        this.id = id;
        this.communityId = communityId;
        this.communityName = communityName;
        this.createUser = createUser;
        this.createTime = createTime;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.bookCode = bookCode;
        this.bookName = bookName;
        this.readMeterUser = readMeterUser;
        this.readMeterUserName = readMeterUserName;
        this.totalReadMeterCount = totalReadMeterCount;
        this.bookStatus = bookStatus;
        this.citedCount = citedCount;
        this.remark = remark;
        this.deleteStatus = deleteStatus;
    }

}
