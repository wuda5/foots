package com.cdqckj.gmis.common.domain.tenant;

/**
 * @author: lijianguo
 * @time: 2020/9/29 17:04
 * @remark: 执行多租户的代码
 */
public interface RealProcess<T> {

    /**
     * @auth lijianguo
     * @date 2020/9/29 17:04
     * @remark 处理每个租户的具体实现每个人的业务逻辑
     */
    T realProcess();
}
