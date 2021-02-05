package com.cdqckj.gmis.gasmeter.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.base.entity.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * <p>
 * 实体类
 * 气表厂家
 * </p>
 *
 * @author gmis
 * @since 2020-08-24
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pt_gas_meter_factory")
@ApiModel(value = "GasMeterFactory", description = "气表厂家")
@AllArgsConstructor
public class GasMeterFactory extends Entity<Long>  {

    private static final long serialVersionUID = 1L;

    /**
     * 厂家名称
     */
    @ApiModelProperty(value = "厂家名称")
    @NotEmpty(message = "厂家名称不能为空")
    @Length(max = 100, message = "厂家名称长度不能超过100")
    @TableField(value = "gas_meter_factory_name", condition = LIKE)
    @Excel(name = "厂家名称")
    private String gasMeterFactoryName;

    /**
     * 厂家编号
     */
    @ApiModelProperty(value = "厂家编号")
    @NotEmpty(message = "厂家编号不能为空")
    @Length(max = 10, message = "厂家编号长度不能超过10")
    @TableField(value = "gas_meter_factory_code", condition = LIKE)
    @Excel(name = "厂家编号")
    private String gasMeterFactoryCode;


    /**
     * 厂家标识码
     */
    @ApiModelProperty(value = "厂家标识码:2、秦川")
//    @NotEmpty(message = "厂家标识码不能为空")
//    @Length(max = 10, message = "厂家标识码长度不能超过10")
    @TableField(value = "gas_meter_factory_mark_code", condition = EQUAL)
    @Excel(name = "厂家标识码")
    private String gasMeterFactoryMarkCode;

    /**
     * 厂家地址
     */
    @ApiModelProperty(value = "厂家地址")
    @NotEmpty(message = "厂家地址不能为空")
    @Length(max = 100, message = "厂家地址长度不能超过100")
    @TableField(value = "gas_meter_factory_address", condition = LIKE)
    @Excel(name = "厂家地址")
    private String gasMeterFactoryAddress;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    @Length(max = 11, message = "电话长度不能超过11")
    @TableField(value = "telphone", condition = LIKE)
    @Excel(name = "电话")
    private String telphone;

    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 10, message = "联系人长度不能超过10")
    @TableField(value = "contacts", condition = LIKE)
    @Excel(name = "联系人")
    private String contacts;

    /**
     * 传真号码
     */
    @ApiModelProperty(value = "传真号码")
    @Length(max = 20, message = "传真号码长度不能超过20")
    @TableField(value = "fax_number", condition = LIKE)
    @Excel(name = "传真号码")
    private String faxNumber;

    /**
     * 经理
     */
    @ApiModelProperty(value = "经理")
    @Length(max = 10, message = "经理长度不能超过10")
    @TableField(value = "manager", condition = LIKE)
    @Excel(name = "经理")
    private String manager;

    /**
     * 税务登记号
     */
    @ApiModelProperty(value = "税务登记号")
    @Length(max = 30, message = "税务登记号长度不能超过30")
    @TableField(value = "tax_registration_no", condition = LIKE)
    @Excel(name = "税务登记号")
    private String taxRegistrationNo;

    /**
     * 开户银行
     */
    @ApiModelProperty(value = "开户银行")
    @Length(max = 20, message = "开户银行长度不能超过20")
    @TableField(value = "bank", condition = LIKE)
    @Excel(name = "开户银行")
    private String bank;

    /**
     * 银行账号
     */
    @ApiModelProperty(value = "银行账号")
    @Length(max = 20, message = "银行账号长度不能超过20")
    @TableField(value = "bank_account", condition = LIKE)
    @Excel(name = "银行账号")
    private String bankAccount;

    /**
     * 普表
     *  ic卡智能燃气表
     *  物联网表
     *  流量计(普表)
     *  流量 计(ic卡表)
     *  流量计(物联网表)
     * 
     */
    @ApiModelProperty(value = "普表")
    @Length(max = 100, message = "普表长度不能超过100")
    @TableField(value = "gas_meter_type", condition = LIKE)
    @Excel(name = "普表")
    private String gasMeterType;

    /**
     * 厂家状态：（0：启用；1：禁用）
     */
    @ApiModelProperty(value = "厂家状态：（1：启用；0：禁用）")
    @NotNull(message = "厂家状态：（1：启用；0：禁用）不能为空")
    @TableField("gas_meter_factory_status")
    @Excel(name = "厂家状态：（1：启用；0：禁用）")
    private Integer gasMeterFactoryStatus;

    /**
     * 兼容参数
     */
    @ApiModelProperty(value = "兼容参数")
    @Length(max = 1000, message = "兼容参数长度不能超过1000")
    @TableField(value = "compatible_parameter", condition = LIKE)
    @Excel(name = "兼容参数")
    private String compatibleParameter;

    /**
     * 删除标识（0：存在；1-删除）
     */
    @ApiModelProperty(value = "删除标识（0：存在；1-删除）")
    @TableField("delete_status")
    @Excel(name = "删除标识（0：存在；1-删除）")
    private Integer deleteStatus;


    @Builder
    public GasMeterFactory(Long id, LocalDateTime createTime, Long createUser, LocalDateTime updateTime, Long updateUser, 
                    String gasMeterFactoryName, String gasMeterFactoryCode, String gasMeterFactoryAddress, String telphone, String contacts, 
                    String faxNumber, String manager, String taxRegistrationNo, String bank, String bankAccount, String gasMeterType, 
                    Integer gasMeterFactoryStatus, String compatibleParameter, Integer deleteStatus) {
        this.id = id;
        this.createTime = createTime;
        this.createUser = createUser;
        this.updateTime = updateTime;
        this.updateUser = updateUser;
        this.gasMeterFactoryName = gasMeterFactoryName;
        this.gasMeterFactoryCode = gasMeterFactoryCode;
        this.gasMeterFactoryAddress = gasMeterFactoryAddress;
        this.telphone = telphone;
        this.contacts = contacts;
        this.faxNumber = faxNumber;
        this.manager = manager;
        this.taxRegistrationNo = taxRegistrationNo;
        this.bank = bank;
        this.bankAccount = bankAccount;
        this.gasMeterType = gasMeterType;
        this.gasMeterFactoryStatus = gasMeterFactoryStatus;
        this.compatibleParameter = compatibleParameter;
        this.deleteStatus = deleteStatus;
    }
}
