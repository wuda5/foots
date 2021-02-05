package com.cdqckj.gmis.systemparam.dto;

import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * <p>
 * 实体类
 * 电子发票服务平台配置表
 * </p>
 *
 * @author gmis
 * @since 2020-11-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoicePlatConfigSaveDTO", description = "电子发票服务平台配置表")
public class InvoicePlatConfigSaveDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 公司编码
     */
    @ApiModelProperty(value = "公司编码", hidden = true)
    @Length(max = 32, message = "公司编码长度不能超过32")
    private String companyCode;
    /**
     * 公司名称
     */
    @ApiModelProperty(value = "公司名称", hidden = true)
    @Length(max = 100, message = "公司名称长度不能超过100")
    private String companyName;
    /**
     * 组织id
     */
    @ApiModelProperty(value = "组织id", hidden = true)
    private Long orgId;
    /**
     * 组织名称
     */
    @ApiModelProperty(value = "组织名称", hidden = true)
    @Length(max = 100, message = "组织名称长度不能超过100")
    private String orgName;
    /**
     * 电子发票服务平台编码
     */
    @ApiModelProperty(value = "电子发票服务平台编码")
    @Length(max = 30, message = "电子发票服务平台编码长度不能超过30")
    private String platCode;
    /**
     * 电子发票平台名称
     */
    @ApiModelProperty(value = "电子发票平台名称")
    @Length(max = 100, message = "电子发票平台名称长度不能超过100")
    private String platName;
    /**
     * 应用标识：发票服务平台授权的应用唯一标识
     */
    @ApiModelProperty(value = "应用标识：发票服务平台授权的应用唯一标识")
    @Length(max = 100, message = "应用标识：发票服务平台授权的应用唯一标识长度不能超过100")
    private String openId;
    /**
     * 应用密码：发票平台授权的密码
     */
    @ApiModelProperty(value = "应用密码：发票平台授权的密码")
    @Length(max = 100, message = "应用密码：发票平台授权的密码长度不能超过100")
    private String appSecret;
    /**
     * 其它参数：使用json格式保存
     */
    @ApiModelProperty(value = "其它参数：使用json格式保存")
    @Length(max = 500, message = "其它参数：使用json格式保存长度不能超过500")
    private String otherParam;
    /**
     * 请求路径
     */
    @ApiModelProperty(value = "请求路径")
    @Length(max = 100, message = "请求路径长度不能超过100")
    private String url;
    /**
     * 联系人
     */
    @ApiModelProperty(value = "联系人")
    @Length(max = 20, message = "联系人长度不能超过20")
    private String contacts;
    /**
     * 联系电话
     */
    @ApiModelProperty(value = "联系电话")
    @Length(max = 20, message = "联系电话长度不能超过20")
    private String telephone;
    /**
     * 附件名称
     */
    @ApiModelProperty(value = "附件名称")
    @Length(max = 30, message = "附件名称长度不能超过30")
    private String fileName;
    /**
     * 附件保存地址
     */
    @ApiModelProperty(value = "附件保存地址")
    @Length(max = 300, message = "附件保存地址长度不能超过100")
    private String fileUrl;
    /**
     * 是否启用状态 0：未启用, 1:启用
     */
    @ApiModelProperty(value = "是否启用状态 0：未启用, 1:启用")
    private Integer dataStatus;
    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer deleteStatus;

    @ApiModelProperty(value = "证书信息：当配置平台选择瑞宏网是填写")
    private InvoicePlatConfigKeyStore keyStore;


    public String getOtherParam() {
        return JSONUtil.toJsonStr(keyStore);
    }

}
