package com.cdqckj.gmis.common.domain.tenant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author: lijianguo
 * @time: 2020/10/8 10:03
 * @remark: 一个租户处理的结果
 */
@Data
@Log4j2
public class TenantResult<T> {

    @ApiModelProperty(value = "这个租户的处理结果,返回的数据")
    private T result;

    @ApiModelProperty(value = "这个租户的处理结果 1处理成功 0处理失败")
    private Boolean resultState;

    @ApiModelProperty(value = "租户编码")
    private String code;

    @ApiModelProperty(value = "租户的名称")
    private String name;

    @ApiModelProperty(value = "处理失败的信息")
    private String msg;

    /**
     * @auth lijianguo
     * @date 2020/10/8 10:16
     * @remark 默认成功
     */
    public TenantResult(String code,String name) {
        this.code = code;
        this.name = name;
        this.resultState = true;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 10:22
     * @remark 设置处理失败
     */
    public void setFailWithMsg(String msg){
        this.resultState = false;
        this.msg = msg;
    }
}
