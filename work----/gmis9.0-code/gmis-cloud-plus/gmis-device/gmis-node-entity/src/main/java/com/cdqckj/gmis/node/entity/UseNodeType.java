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
 * 节点类型管理
 * </p>
 *
 * @author gmis
 * @since 2020-07-27
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pt_company_use_node_type")
@ApiModel(value = "UseNodeType", description = "节点类型管理")
@AllArgsConstructor
public class UseNodeType extends Entity<Long> {

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
     * 厂家ID
     */
    @ApiModelProperty(value = "厂家ID")
    @TableField("node_factory_id")
    @Excel(name = "厂家ID")
    private Long nodeFactoryId;

    /**
     * 型号ID
     */
    @ApiModelProperty(value = "型号ID")
    @TableField("node_type_id")
    @Excel(name = "型号ID")
    private Long nodeTypeId;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "node_facotry_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String nodeFacotryName;

    /**
     * 型号名称
     */
    @ApiModelProperty(value = "型号名称")
    @Length(max = 50, message = "型号名称长度不能超过50")
    @TableField(value = "node_name", condition = LIKE)
    @Excel(name = "型号名称")
    private String nodeName;

    /**
     * 启用
     *             禁用
     */
    @ApiModelProperty(value = "启用")
    @TableField("use_status")
    @Excel(name = "启用")
    private Integer useStatus;


    @Builder
    public UseNodeType(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String companyCode, Long nodeFactoryId, Long nodeTypeId, String nodeFacotryName, String nodeName, Integer useStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.companyCode = companyCode;
        this.nodeFactoryId = nodeFactoryId;
        this.nodeTypeId = nodeTypeId;
        this.nodeFacotryName = nodeFacotryName;
        this.nodeName = nodeName;
        this.useStatus = useStatus;
    }

}
