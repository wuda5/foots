package com.cdqckj.gmis.common.domain.excell.rule;

import com.cdqckj.gmis.common.domain.excell.ValidResult;

/**
 * @author: lijianguo
 * @time: 2020/9/30 9:20
 * @remark: 验证的规则--实现这个接口或者继承虚函数都可以
 */
public interface ValidRule {

    /**
     * @auth lijianguo
     * @date 2020/10/15 13:22
     * @remark 开始的列的编号
     * @return
     */
    Integer validColStart();

    /**
     * @auth lijianguo
     * @date 2020/10/12 16:40
     * @remark 验证的列数
     * @return
     */
    Integer validColSum();

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:20
     * @remark 验证的类型
     */
    int validType();

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:21
     * @remark 验证失败的说明
     */
     String validFailDesc();

    /**
     * @auth lijianguo
     * @date 2020/9/30 9:24
     * @remark 空的验证失败的说明
     */
     String validEmptyFailDesc();

    /**
     * @auth lijianguo
     * @date 2020/9/30 10:44
     * @remark 处理验证
     */
    void validProcess(ValidResult validResult);

}
