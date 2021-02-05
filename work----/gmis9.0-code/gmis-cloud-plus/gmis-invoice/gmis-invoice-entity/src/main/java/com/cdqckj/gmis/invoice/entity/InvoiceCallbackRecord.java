package com.cdqckj.gmis.invoice.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 电子发票回调记录表
 * </p>
 *
 * @author gmis
 * @since 2020-11-12
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("gt_invoice_callback_record")
@ApiModel(value = "InvoiceCallbackRecord", description = "电子发票回调记录表")
@AllArgsConstructor
public class InvoiceCallbackRecord extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 租户code
     */
    @ApiModelProperty(value = "租户code")
    @Length(max = 10, message = "租户code长度不能超过10")
    @TableField(value = "tenant", condition = LIKE)
    @Excel(name = "租户code")
    private String tenant;

    /**
     * 回调的平台编号
     */
    @ApiModelProperty(value = "回调的平台编号")
    @Length(max = 50, message = "回调的平台编号长度不能超过50")
    @TableField(value = "plat_code", condition = LIKE)
    @Excel(name = "回调的平台编号")
    private String platCode;

    /**
     * 回调的平台名称
     */
    @ApiModelProperty(value = "回调的平台名称")
    @Length(max = 100, message = "回调的平台名称长度不能超过100")
    @TableField(value = "plat_name", condition = LIKE)
    @Excel(name = "回调的平台名称")
    private String platName;

    /**
     * 回调内容
     */
    @ApiModelProperty(value = "回调内容")
    @Length(max = 65535, message = "回调内容长度不能超过65535")
    @TableField("callback_content")
    @Excel(name = "回调内容")
    private String callbackContent;

    /**
     * 回调时间
     */
    @ApiModelProperty(value = "回调时间")
    @TableField("callback_date")
    @Excel(name = "回调时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime callbackDate;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("delete_status")
    @Excel(name = "状态")
    private Integer deleteStatus;


    @Builder
    public InvoiceCallbackRecord(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
                                 String tenant, String platCode, String platName, String callbackContent, LocalDateTime callbackDate, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.tenant = tenant;
        this.platCode = platCode;
        this.platName = platName;
        this.callbackContent = callbackContent;
        this.callbackDate = callbackDate;
        this.deleteStatus = deleteStatus;
    }

}
