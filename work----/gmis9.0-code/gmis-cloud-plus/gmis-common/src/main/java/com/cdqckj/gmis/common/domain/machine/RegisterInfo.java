package com.cdqckj.gmis.common.domain.machine;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: lijianguo
 * @time: 2020/11/25 13:21
 * @remark: 注册信息
 */
@Data
public class RegisterInfo implements Serializable {

    /** 注册的IP **/
    private String iP;

    /** 注册的端口 **/
    private String port;

    /** 注册的注册的时间戳 **/
    private Long time;

    /** 注册的注册的编号 **/
    private Integer num;

}
