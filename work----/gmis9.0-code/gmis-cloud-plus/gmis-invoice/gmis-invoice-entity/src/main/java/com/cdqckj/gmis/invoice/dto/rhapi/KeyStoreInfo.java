package com.cdqckj.gmis.invoice.dto.rhapi;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 瑞宏网电子发票证书签名需要的参数
 *
 * @author ASUS
 */
@Data
public class KeyStoreInfo {

    /**
     * 电子发票平台分配的 appCode
     */
    private String appCode;
    @ApiModelProperty(value = "证书文件名")
    private String keyStoreName;
    @ApiModelProperty(value = "证书文件路径")
    private String keyStorePath;
    @ApiModelProperty(value = "证书别名")
    private String keyStoreAlias;
    @ApiModelProperty(value = "证书密码")
    private String keyStorePwd;
    
}
