package com.cdqckj.gmis.common.domain.machine;

/**
 * @author: lijianguo
 * @time: 2020/11/26 8:45
 * @remark: 注册APP-统计同一个项目部署的台数
 */
public interface RegisterAppNum {

    /**
     * @auth lijianguo
     * @date 2020/11/26 8:45
     * @remark 获取app的code
     */
    RegisterInfo registerAppInfo(String projectName, EnvProperty envProperty);
}
