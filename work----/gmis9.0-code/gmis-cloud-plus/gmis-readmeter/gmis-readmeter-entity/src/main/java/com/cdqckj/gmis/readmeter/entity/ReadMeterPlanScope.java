package com.cdqckj.gmis.readmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 抄表任务分配
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
@TableName("cb_read_meter_plan_scope")
@ApiModel(value = "ReadMeterPlanScope", description = "抄表任务分配")
@AllArgsConstructor
public class ReadMeterPlanScope extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表计划id
     */
    @ApiModelProperty(value = "抄表计划id")
    @TableField("readmeter_plan_id")
    @Excel(name = "抄表计划id")
    private Long readmeterPlanId;

    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    @TableField("book_id")
    @Excel(name = "抄表册id")
    private Long bookId;

    /**
     * 抄表册名称
     */
    @ApiModelProperty(value = "抄表册名称")
    @Length(max = 30, message = "抄表册名称长度不能超过30")
    @TableField(value = "book_name", condition = LIKE)
    @Excel(name = "抄表册名称")
    private String bookName;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    @TableField("read_meter_user")
    @Excel(name = "抄表员id")
    private Long readMeterUser;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员")
    @Length(max = 100, message = "抄表员长度不能超过100")
    @TableField(value = "read_meter_user_name", condition = LIKE)
    @Excel(name = "抄表员")
    private String readMeterUserName;

    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    @TableField("total_read_meter_count")
    @Excel(name = "总户数")
    private Integer totalReadMeterCount;

    /**
     * 已抄数
     */
    @ApiModelProperty(value = "已抄数")
    @TableField("read_meter_count")
    @Excel(name = "已抄数")
    private Integer readMeterCount;

    /**
     * 已审数
     */
    @ApiModelProperty(value = "已审数")
    @TableField("review_count")
    @Excel(name = "已审数")
    private Integer reviewCount;

    /**
     * 结算数
     */
    @ApiModelProperty(value = "结算数")
    @TableField("settlement_count")
    @Excel(name = "结算数")
    private Integer settlementCount;

    /**
     * 抄表状态：-1:未抄表；1:抄表中；0:抄表完成
     */
    @ApiModelProperty(value = "抄表状态：-1:未抄表；1:抄表中；0:抄表完成")
    @TableField("data_status")
    @Excel(name = "抄表状态：-1:未抄表；1:抄表中；0:抄表完成")
    private Integer dataStatus;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer deleteStatus;

    @Builder
    public ReadMeterPlanScope(Long id, Long createUser, LocalDateTime createTime, Long updateUser, LocalDateTime updateTime, 
                    Long readmeterPlanId, Long bookId, Integer totalReadMeterCount, Integer readMeterCount, Integer reviewCount, 
                    Integer settlementCount, Integer dataStatus, String readMeterUserName, Integer deleteStatus, Long readMeterUser) {
        this.id = id;
        this.createUser = createUser;
        this.createTime = createTime;
        this.readMeterUserName = readMeterUserName;
        this.updateUser = updateUser;
        this.updateTime = updateTime;
        this.readmeterPlanId = readmeterPlanId;
        this.bookId = bookId;
        this.totalReadMeterCount = totalReadMeterCount;
        this.readMeterCount = readMeterCount;
        this.reviewCount = reviewCount;
        this.settlementCount = settlementCount;
        this.dataStatus = dataStatus;
        this.deleteStatus = deleteStatus;
        this.readMeterUser = readMeterUser;
    }

}
