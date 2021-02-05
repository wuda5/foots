package com.cdqckj.gmis.iot.qc.qnms.exception;

import lombok.Data;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 远传系统运行过程中存在的异常
 * @author: 秦川物联网科技
 * @date: 2020/10/14 10:11
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class IotException extends RuntimeException {
    int code;
    public IotException(int code, String message){
        super(message);
        this.code = code;
    }
}
