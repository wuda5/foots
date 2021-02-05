package com.cdqckj.gmis.authority.entity.core;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;
import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_DATE_TIME_FORMAT;

/**
 * <p>
 * 实体类
 * 组织
 * </p>
 *
 * @author gmis
 * @since 2019-10-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("c_core_org")
@ApiModel(value = "Org", description = "组织")
public class Org extends TreeEntity<Org, Long> {

    private static final long serialVersionUID = 1L;

    @Excel(name = "名称", width = 50)
    protected String label;

    /**
     * 简称
     */
    @ApiModelProperty(value = "简称")
    @Length(max = 255, message = "简称长度不能超过255")
    @TableField(value = "abbreviation", condition = LIKE)
    @Excel(name = "简称", width = 30)
    private String abbreviation;


    /**
     * 树结构
     */
    @ApiModelProperty(value = "树结构")
    @Length(max = 255, message = "树结构长度不能超过255")
    @TableField(value = "tree_path", condition = LIKE)
    private String treePath;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    @TableField("status")
    @Excel(name = "状态", replace = {"启用_true", "禁用_false", "_null"})
    private Boolean status;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "描述长度不能超过255")
    @TableField(value = "describe_", condition = LIKE)
    @Excel(name = "描述", width = 50)
    private String describe;

    /**
     * 删除时间
     */
    @ApiModelProperty(value = "删除时间")
    @TableField("delete_time")
    @Excel(name = "删除时间", format = DEFAULT_DATE_TIME_FORMAT, width = 20)
    private LocalDateTime deleteTime;

    /**
     * 删除用户id
     */
    @ApiModelProperty(value = "删除用户id")
    @TableField("delete_user")
    @Excel(name = "删除用户id")
    private Long deleteUser;

    @ApiModelProperty(value = "是否营业厅")
    @TableField("is_business_hall")
    @Excel(name = "是否营业厅")
    private Integer isBusinessHall;

    @Builder
    public Org(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser,
               LocalDateTime deleteTime,Long deleteUser,
               String label, String abbreviation, Long parentId, String treePath, Integer sortValue,
               Boolean status, String describe, Integer isBusinessHall) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.label = label;
        this.abbreviation = abbreviation;
        this.parentId = parentId;
        this.treePath = treePath;
        this.sortValue = sortValue;
        this.status = status;
        this.describe = describe;
        this.deleteTime = deleteTime;
        this.deleteUser = deleteUser;
        this.isBusinessHall = isBusinessHall;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Org org = (Org) o;
        return Objects.equals(id, org.id) &&
                Objects.equals(label, org.label);
    }


}
