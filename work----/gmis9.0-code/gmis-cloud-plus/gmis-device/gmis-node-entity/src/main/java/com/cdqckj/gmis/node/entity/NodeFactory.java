package com.cdqckj.gmis.node.entity;

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
 * 
 * </p>
 *
 * @author gmis
 * @since 2020-07-21
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pt_node_factory")
@ApiModel(value = "NodeFactory", description = "")
@AllArgsConstructor
public class NodeFactory extends Entity<Long> {

    private static final long serialVersionUID = 1L;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "node_facotry_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String nodeFacotryName;

    /**
     * 厂家编号
     */
    @ApiModelProperty(value = "厂家编号")
    @Length(max = 10, message = "厂家编号长度不能超过10")
    @TableField(value = "node_facotry_code", condition = LIKE)
    @Excel(name = "厂家编号")
    private String nodeFacotryCode;

    /**
     * RTU:rtu设备
     *             FLOWMETER:流量计
     */
    @ApiModelProperty(value = "RTU:rtu设备")
    @Length(max = 100, message = "RTU:rtu设备长度不能超过100")
    @TableField(value = "facotry_use_node_type", condition = LIKE)
    @Excel(name = "RTU:rtu设备")
    private String facotryUseNodeType;

    /**
     * 启用
     * 	禁用
     */
    @ApiModelProperty(value = "启用")
    @TableField("node_factory_status")
    @Excel(name = "启用")
    private Integer nodeFactoryStatus;


    @Builder
    public NodeFactory(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String nodeFacotryName, String nodeFacotryCode, String facotryUseNodeType, Integer nodeFactoryStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.nodeFacotryName = nodeFacotryName;
        this.nodeFacotryCode = nodeFacotryCode;
        this.facotryUseNodeType = facotryUseNodeType;
        this.nodeFactoryStatus = nodeFactoryStatus;
    }

}
