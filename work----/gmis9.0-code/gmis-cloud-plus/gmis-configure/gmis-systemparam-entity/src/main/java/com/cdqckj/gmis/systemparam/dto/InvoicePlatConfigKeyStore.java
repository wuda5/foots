package com.cdqckj.gmis.systemparam.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 发票平台配置瑞宏签名证书对象
 *
 * @author ASUS
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "InvoicePlatConfigKeyStore", description = "发票平台配置瑞宏签名证书对象")
public class InvoicePlatConfigKeyStore implements Serializable {

    private static final long serialVersionUID = 1L;

    private String appId;
    @ApiModelProperty(value = "附件表Id")
    private Long attachmentId;
    @ApiModelProperty(value = "证书文件名")
    private String keyStoreName;
    @ApiModelProperty(value = "证书上传地址")
    private String keyStorePath;
    @ApiModelProperty(value = "证书别名")
    private String keyStoreAlias;
    @ApiModelProperty(value = "证书密码")
    private String keyStorePwd;
}
