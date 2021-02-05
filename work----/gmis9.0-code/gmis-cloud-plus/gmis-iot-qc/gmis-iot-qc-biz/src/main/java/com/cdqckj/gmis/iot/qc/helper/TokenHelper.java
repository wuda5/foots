package com.cdqckj.gmis.iot.qc.helper;

import com.cdqckj.gmis.base.IotR;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 登录辅助接口
 * @author: 秦川物联网科技
 * @date: 2020/10/13  10:54
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface TokenHelper {
    /**
     * 登录并返回token
     * @param licence
     * @return
     */
    public IotR login(String licence) throws Exception;
}
