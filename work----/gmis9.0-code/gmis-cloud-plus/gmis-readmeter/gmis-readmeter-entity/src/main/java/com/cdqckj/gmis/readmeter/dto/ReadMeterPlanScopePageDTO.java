package com.cdqckj.gmis.readmeter.dto;

import java.time.LocalDateTime;

import cn.afterturn.easypoi.excel.annotation.Excel;
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
import lombok.Data;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import java.io.Serializable;

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
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ReadMeterPlanScopePageDTO", description = "抄表任务分配")
public class ReadMeterPlanScopePageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 抄表计划id
     */
    @ApiModelProperty(value = "抄表计划id")
    private Long readmeterPlanId;
    /**
     * 抄表册id
     */
    @ApiModelProperty(value = "抄表册id")
    private Long bookId;
    /**
     * 抄表册名称
     */
    @ApiModelProperty(value = "抄表册名称")
    @Length(max = 30, message = "抄表册名称长度不能超过30")
    private String bookName;

    /**
     * 抄表员id
     */
    @ApiModelProperty(value = "抄表员id")
    private Long readMeterUser;

    /**
     * 抄表员
     */
    @ApiModelProperty(value = "抄表员")
    @Length(max = 100, message = "抄表员长度不能超过30")
    private String readMeterUserName;
    /**
     * 总户数
     */
    @ApiModelProperty(value = "总户数")
    private Integer totalReadMeterCount;
    /**
     * 已抄数
     */
    @ApiModelProperty(value = "已抄数")
    private Integer readMeterCount;
    /**
     * 已审数
     */
    @ApiModelProperty(value = "已审数")
    private Integer reviewCount;
    /**
     * 结算数
     */
    @ApiModelProperty(value = "结算数")
    private Integer settlementCount;
    /**
     * 抄表状态：-1:未抄表；1:抄表中；0:抄表完成
     */
    @ApiModelProperty(value = "抄表状态：-1:未抄表；1:抄表中；0:抄表完成")
    private Integer dataStatus;

    /**
     * 删除标识
     */
    @ApiModelProperty(value = "删除标识")
    private Integer deleteStatus;

}
